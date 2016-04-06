package yellowsparkle.parking.simulation;

import yellowsparkle.Main;
import yellowsparkle.parking.CarQueue;
import yellowsparkle.parking.Location;
import yellowsparkle.parking.model.Car;
import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.model.Ticket;
import yellowsparkle.parking.model.Ticket.TicketType;

import java.util.Date;

public class Simulator {
    private Garage garage;
    private final CarQueue entryQueue;
    private final CarQueue exitQueue;
    private boolean canExit = true;
    private int tickCount;
    private int entryPerTick = 1;
    private int exitPerTick = 3;
    private int ticketSold = 0;


    public Simulator(Garage garage) {
        this.garage = garage;
        entryQueue = new CarQueue();
        exitQueue = new CarQueue();
        tickCount = 0;

    }

    public void tick() throws ParkingException {
        Date now = new Date();
        System.out.println("TICK: " + tickCount);
        tickCount++;
        if (canExit) {
            for (int i = 0; i < exitPerTick; i++) {
                Car car = exitQueue.removeCar();
                if (car != null) garage.removeCar(car.getLocation());
            }
        }
        entryQueue.addCar(new Car(status -> {
            if (status == Car.Status.PARK && (Main.random.nextInt(32) < 4)) return Car.Status.EXIT_WAIT;
            else return status;
        },
        new Ticket(TicketType.getRandomTicket())));
        for (int i = 0; i < entryPerTick; i++) {
            if (garage.totalSpaces() > garage.getUsedSpaces()) {
                Car car = entryQueue.removeCar();
                if (car != null) {
                    boolean isValid = true;
                    Ticket[] tickets = car.getTickets();
                    for(Ticket ticket : tickets) {

                        Location spot = garage.getFirstEmptyLocation();
                        switch(ticket.getType()) {
                            case REGULAR:
                                if (spot != null) {
                                    ticket.setStart(new Date());
                                    garage.addCar(car, spot);
                                    ticketSold++;
                                } else {
                                    isValid = false;
                                }
                            break;
                            /**
                             * Commented out caused an error (null pointer exeption)
                             */
                            //case RESERVATION:
                            //    if (spot == null) {
                            //        throw new ParkingException("Reserved spot not available!");
                            //    }
                            //    if (ticket.isValid(now)) {
                            //        garage.addCar(car, spot);
                            //    } else {
                            //        isValid = false;
                            //    }
                            //    break;
                            case SUBSCRIPTION:
                                if (spot != null) {
                                    if (ticket.isValid(now)) {
                                        garage.addCar(car, spot);

                                    } else {
                                        isValid = false;
                                    }
                                    garage.addCar(car, spot);

                                } else {
                                    isValid = false;
                                }
                                break;
                            //case CORPORATE_PARKING:
                              //  System.out.println("Main loop tick!");
                               // break;
                        }
                    }
                    if (!isValid) {
                        car.setStatus(Car.Status.EXIT_QUEUE);
                        exitQueue.addCar(car);
                    }
                }
            }
        }
        garage.forEach(car -> {
            car.tick();
            if (car.getStatus() == Car.Status.EXIT_WAIT) {
                car.setStatus(Car.Status.EXIT_QUEUE);
                exitQueue.addCar(car);
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

    public void queueCar(Car car) {
        entryQueue.addCar(car);
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

    //Total parking spaces for Piechart
    public int totalParkingSpaces(){
        return garage.totalSpaces();
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
        return ticketSold;
    }
}
