package yellowsparkle.parking.simulation;

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
                exitQueue.removeCar();
            }
        }
        entryQueue.addCar(new Car(new Ticket(TicketType.REGULAR)));
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
                                } else {
                                    isValid = false;
                                }
                            break;
                            case RESERVATION:
                                if (spot == null) {
                                    throw new ParkingException("Reserved spot not available!");
                                }
                                if (ticket.isValid(now)) {
                                    garage.addCar(car, spot);
                                } else {
                                    isValid = false;
                                }
                                break;
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
                            case CORPORATE_PARKING:
                                break;
                        }
                    }
                    if (!isValid) {
                        exitQueue.addCar(car);
                    }
                }
            }
        }
        garage.forEach(Car::tick);
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
}
