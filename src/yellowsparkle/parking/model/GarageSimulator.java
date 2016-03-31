package yellowsparkle.parking.model;

import yellowsparkle.parking.Car;
import yellowsparkle.parking.CarQueue;

// TODO: Rename to 'Simulator' once parking.Simulator is deprecated
public class GarageSimulator {
    private Garage garage;
    private final CarQueue entryQueue;
    private final CarQueue exitQueue;
    private boolean canExit = true;

    public GarageSimulator(Garage garage) {
        this.garage = garage;
        entryQueue = new CarQueue();
        exitQueue = new CarQueue();
    }

    public void tick() {
        if (canExit) {
            exitQueue.removeCar();
        }
        if (garage.totalSpaces() > garage.usedSpaces()) {
            Car car = entryQueue.removeCar();
            if (car != null) {
                garage.addCar(car, garage.getFirstEmptyLocation());
            }
        }
        garage.getCars().forEach(car -> {
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

    public boolean canExit() {
        return canExit;
    }

    public void setCanExit(boolean canExit) {
        this.canExit = canExit;
    }
}
