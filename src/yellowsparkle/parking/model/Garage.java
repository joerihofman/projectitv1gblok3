package yellowsparkle.parking.model;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public abstract class Garage {
    public abstract void forEach(Consumer<ParkingSlot> slotConsumer);

    public abstract ParkingSlot getRandomEmptyLocation();

    public abstract List<ParkingSlot> getEmptyLocations();

    public abstract int getTotalSpaces();

    public abstract int getUsedSpaces();

    public abstract Car addCar(Car car, Position position);

    public abstract Car removeCar(Position position);

    public abstract void removeCar(Car car);

    public abstract boolean hasPosition(Position position);

    public abstract List<Car> getCars();

    public abstract Collection<ParkingSlot> getParkingSlots();

    public abstract List<Car> removeCars();
}
