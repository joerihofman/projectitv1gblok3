package yellowsparkle.parking.simulation;

import yellowsparkle.Main;
import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.parking.model.Car;
import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.model.Ticket;
import yellowsparkle.parking.model.Ticket.TicketType;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;

/**
 * Default implementation for Simulator
 * @author ITV1G Group 1
 * @version 1.0
 */
public class SimulatorImpl extends Simulator {
    private Garage garage;
    private final Deque<Car> entryQueue;
    private final Deque<ParkingSlot> exitQueue;
    private boolean canExit = true;
    private int tickCount;
    private int entryPerTick = 1;
    private int exitPerTick = 3;
    int ticketSold = 0;


    /**
     * constructor for SimulatorImpl
     * @param garage garage to simulate on
     */
    public SimulatorImpl(Garage garage) {
        this.garage = garage;
        entryQueue = new ArrayDeque<>();                        //Deque is for the queues to add/remove one from both sides
        exitQueue = new ArrayDeque<>();
        tickCount = 0;

    }

    /**
     * @inheritDoc
     */
    @Override
    public void tick() {
        tickCount++;

        if (canExit) {
            for (int i = 0; i < exitPerTick; i++) {
                ParkingSlot slot = exitQueue.pollFirst();
                if (slot != null) slot.removeCar();
            }
        }

        entryQueue.add(new Car(status -> {
            if (status == Car.Status.PARK && (Main.random.nextInt(128) < 4)) return Car.Status.EXIT_WAIT;
            else return status;
        }, new Ticket(TicketType.REGULAR)));

        for (int i = 0; i < entryPerTick; i++) {
            Car car = entryQueue.peekFirst();               //peekFirst retrieves the first car in the dequeue.
            if (car != null) {
                Garage targetGarage = this.garage.acceptingSubGarage(car);
                ParkingSlot randomEmptyLocation = targetGarage.getRandomEmptyLocation();
                if (randomEmptyLocation != null) {
                    randomEmptyLocation.setCar(car);
                    entryQueue.removeFirst();
                }
            }
        }

        garage.forEach(parkingSlot -> {
            if (!parkingSlot.isEmpty()) {
                parkingSlot.getCar().tick();
                if (parkingSlot.getCar().getStatus() == Car.Status.EXIT_WAIT) {     //When a car leaves the garages the status is changed to EXIT_WAIT. This is checked here and when it has this status the car will be put in the exit queue.
                    parkingSlot.getCar().setStatus(Car.Status.EXIT_QUEUE);
                    exitQueue.add(parkingSlot);                                     //The car will leave the garage and adds it in the exit queue
                }
            }
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public Garage getGarage() {
        return garage;
    }

    /**
     * @inheritDoc
     */    @Override
    public int queueLength(){
        return entryQueue.size();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void reset() {
        garage.removeCars(); // removes all cars
        tickCount = 0;       // Resets ticks and sold tickets to 0
        ticketSold = 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean canExit() {
        return canExit;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCanExit(boolean canExit) {
        this.canExit = canExit;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getTickCount() {
        return tickCount;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getSoldTickets() {
        return ticketSold;
    }
}
