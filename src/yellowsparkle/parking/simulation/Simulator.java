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
import java.util.Queue;

public class Simulator {
    private Garage garage;
    private final Deque<Car> entryQueue;
    private final Deque<ParkingSlot> exitQueue;
    private boolean canExit = true;
    private int tickCount;
    private int entryPerTick = 1;
    private int exitPerTick = 3;
    int ticketSold = 0;
    private int soldTickets;

    public Simulator(Garage garage) {
        this.garage = garage;
        entryQueue = new ArrayDeque<>();
        exitQueue = new ArrayDeque<>();
        tickCount = 0;

    }

    public void tick() throws ParkingException {
        Date now = new Date();
        System.out.println("TICK: " + tickCount);
        tickCount++;

        if (canExit) {
            for (int i = 0; i < exitPerTick; i++) {
                ParkingSlot slot = exitQueue.pollFirst();
                if (slot != null) slot.removeCar();
            }
        }

        entryQueue.add(new Car(status -> {
            if (status == Car.Status.PARK && (Main.random.nextInt(32) < 4)) return Car.Status.EXIT_WAIT;
            else return status;
        }, new Ticket(TicketType.REGULAR)));

        for (int i = 0; i < entryPerTick; i++) {
            if (garage.totalSpaces() > garage.getUsedSpaces()) {
                Car car = entryQueue.peekFirst();
                if (car != null) {
                    boolean isValid = true;
                    Ticket[] tickets = car.getTickets();
                    for(Ticket ticket : tickets) {
                        ParkingSlot spot = garage.getRandomEmptyLocation();
                        switch(ticket.getType()) {
                            case REGULAR:
                                if (spot != null) {
                                    ticket.setStart(new Date());
                                    spot.setCar(car);
                                    ticketSold++;
                                } else {
                                    isValid = false;
                                }
                            break;
                            case RESERVATION:
                            case SUBSCRIPTION:
                                if (spot != null) {
                                    if (ticket.isValid(now)) {
                                        spot.setCar(car);
                                    } else {
                                        isValid = false;
                                    }
                                } else {
                                    isValid = false;
                                }
                                break;
                            case CORPORATE_PARKING:
                                break;
                        }
                    }
                    if (isValid) {
                        entryQueue.removeFirst();
                    } else {
                        entryQueue.addFirst(car);
                    }
                }
            }
        }

        garage.forEach(parkingSlot -> {
            if (!parkingSlot.isEmpty()) {
                parkingSlot.getCar().tick();
                if (parkingSlot.getCar().getStatus() == Car.Status.EXIT_WAIT) {
                    parkingSlot.getCar().setStatus(Car.Status.EXIT_QUEUE);
                    exitQueue.add(parkingSlot);
                }
            }
        });
    }

    public void tick(int ticks) throws ParkingException {
        for (int i = 0; i < ticks; i++) {
            tick();
        }
    }

    public Garage getGarage() {
        return garage;
    }

    //return the size of the queue
    public int queueLength(){
        return entryQueue.size();
    }

    //this will remove all the cars from array and reset the tickcount to zero
    public void reset() {
        garage.removeCars();
        tickCount = 0;
        ticketSold = 0;
    }

    //Used parking spaces for the GUI
    public int usedParkingSpaces() {
        return garage.getUsedSpaces();
    }

    //Empty parking spaces for the GUI
    public int freeParkingSpaces() {
        return (garage.totalSpaces() - garage.getUsedSpaces());
    }

    public boolean canExit() {
        return canExit;
    }

    //checks if the car can exit
    public void setCanExit(boolean canExit) {
        this.canExit = canExit;
    }

    //keeps tracks of total ticks
    public int getTickCount() {
        return tickCount;
    }

    public int getSoldTickets() {
        return soldTickets;
    }
}
