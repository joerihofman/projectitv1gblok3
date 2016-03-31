package yellowsparkle.parking.model;

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
        if (garage.totalSpaces() > garage.getUsedSpaces()) {
            Car car = entryQueue.removeCar();
            if (car != null) {
                garage.addCar(car, garage.getFirstEmptyLocation());
            }
        } else {
            throw new RuntimeException(garage.totalSpaces() + "\t" + garage.getUsedSpaces());
        }
        garage.forEach(car -> {
            car.tick();
            if (car.getMinutesLeft() < -1) exitQueue.addCar(car);
            garage.removeCar(car.getLocation());
        });
    }

    public void tick(int ticks) {
        for (int i = 0; i < ticks; i++) {
            tick();
        }
    }

    public void queueCar(Car car) {
        entryQueue.addCar(car);
    }

    public int queueLength(){
        return entryQueue.size();
    }

    public void reset() {
        garage.removeCars();
        tickCount = 0;
    }

    public boolean canExit() {
        return canExit;
    }

    public void setCanExit(boolean canExit) {
        this.canExit = canExit;
    }

    public int getTickCount() {
        return tickCount;
    }
}
