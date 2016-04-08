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

public class SimulatorImpl extends Simulator {
    private Garage garage;
    private final Deque<Car> entryQueue;
    private final Deque<ParkingSlot> exitQueue;
    private boolean canExit = true;
    private int tickCount;
    private int entryPerTick = 1;
    private int exitPerTick = 3;
    int ticketSold = 0;


    public SimulatorImpl(Garage garage) {
        this.garage = garage;
        entryQueue = new ArrayDeque<>();                        //Deque is for the queues to add/remove one from both sides
        exitQueue = new ArrayDeque<>();
        tickCount = 0;

    }

    @Override
    public void tick() throws ParkingException {
        Date now = new Date();                                  //gives a date with each that arrives
        System.out.println("TICK: " + tickCount);               //This is for in the command line to see at which tick we are.
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
        }, new Ticket(TicketType.REGULAR)));                 //For now all cars have a REGULAR ticket||||| cuz getRandomTicket breaks cuz of -Reservation-Subscription and coperate parking...

        /*
        * We use boolean isValid to make sure the parking ticket/subscription is still valid.
        * For now we don't use it but we will get there
        * */
        for (int i = 0; i < entryPerTick; i++) {
            if (garage.getTotalSpaces().size() > garage.getUsedSpaces().size()) {
                Car car = entryQueue.peekFirst();               //peekFirst retrieves the first car in the dequeue.
                if (car != null) {
                    boolean isValid = true;
                    Ticket[] tickets = car.getTickets();        //getTickets retrieves all the information about the ticket

                    /*
                   * We used a enum for the parking ticket
                   * Here it checks what kind of parking ticket the car has.
                   * 7-4-16 11.40 - All the cars has a "REGULAR" ticket for now. This will change.
                   * */
                    for(Ticket ticket : tickets) {               //Here it searches randomly for the first empty location in the parking garage
                        ParkingSlot spot = garage.getRandomEmptyLocation();
                        switch(ticket.getType()) {               //asks the type of ticket
                            case REGULAR:
                                if (spot != null) {
                                    ticket.setStart(new Date()); //When the car is parked this gives it a date to track how long it's parked
                                    spot.setCar(car);
                                    ticketSold++;                //Here it counts the "REGULAR" tickets sold. This is used in the GUI
                                } else {
                                    isValid = false;
                                }
                            break;
                            case RESERVATION:                   //Currently breaks it with random ticket type
                            case SUBSCRIPTION:                  //Currently breaks it with random ticket type
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
                            case CORPORATE_PARKING:             //Currently breaks it with random ticket type
                                break;
                        }
                    }
                    if (isValid) {
                        entryQueue.removeFirst(); //removes car from queueu
                    } else {
                        entryQueue.addFirst(car);
                    }
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

    @Override
    public void tick(int ticks) throws ParkingException {
        for (int i = 0; i < ticks; i++) {
            tick();
        }
    }

    @Override
    public Garage getGarage() {
        return garage;
    }

    //return the size of the queue
    @Override
    public int queueLength(){
        return entryQueue.size();
    }

    //this will remove all the cars from array and reset the tickcount to zero
    @Override
    public void reset() {
        garage.removeCars(); // removes all cars
        tickCount = 0;       // Resets ticks and sold tickets to 0
        ticketSold = 0;
    }

    @Override
    public boolean canExit() {
        return canExit;
    }

    //checks if the car can exit
    @Override
    public void setCanExit(boolean canExit) {
        this.canExit = canExit;
    }

    //keeps tracks of total ticks
    @Override
    public int getTickCount() {
        return tickCount;
    }

    //returns sold tickets.
    @Override
    public int getSoldTickets() {
        return ticketSold;
    }
}
