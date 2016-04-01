package yellowsparkle.parking.model;

import yellowsparkle.Main;
import yellowsparkle.parking.AdHocCar;
import yellowsparkle.parking.Car;
import yellowsparkle.parking.CarQueue;

// TODO: Rename to 'Simulator' once parking.Simulator is deprecated
public class GarageSimulator {
    private Garage garage;
    private final CarQueue entryQueue;
    private final CarQueue exitQueue;
    private boolean canExit = true;
    private int tickCount;

    public GarageSimulator(Garage garage) {
        this.garage = garage;
        entryQueue = new CarQueue();
        exitQueue = new CarQueue();
        tickCount = 0;
    }

    public void tick() {
        System.out.println("TICK: " + tickCount);
        tickCount++;
        if (canExit) {
            exitQueue.removeCar();
        }
        entryQueue.addCar(new AdHocCar(Main.random.nextInt(10) + 10));
        if (garage.totalSpaces() > garage.getUsedSpaces()) {
            Car car = entryQueue.removeCar();
           if (car != null) {
                garage.addCar(car, garage.getFirstEmptyLocation());
           }
        } else {

        }
        garage.forEach(car -> {
            car.tick();
            if (car.getMinutesLeft() < 1) {
                exitQueue.addCar(car);
                garage.removeCar(car.getLocation());
            }
        });
    }

    public void tick(int ticks) {
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
