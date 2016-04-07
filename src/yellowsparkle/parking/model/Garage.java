package yellowsparkle.parking.model;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public abstract class Garage {

    public abstract void forEach(Consumer<ParkingSlot> slotConsumer);

    /**
    * A car needs a empty parking spot
    * To make it more realistic we give each car a random location
    */
    public abstract ParkingSlot getRandomEmptyLocation();

    /**
    * Gives back a list with empty parking slots in the garage
    */
    public abstract List<ParkingSlot> getEmptyLocations();

    /**
     *
     * @return parkingSlots.values();
     */
    public abstract Collection<ParkingSlot> getTotalSpaces();

    public abstract List<ParkingSlot> getUsedSpaces();

    public abstract Car addCar(Car car, Position position);

    public abstract Car removeCar(Position position);

    public abstract void removeCar(Car car);

    public abstract boolean hasPosition(Position position);

    public abstract List<Car> getCars();

    public abstract Collection<ParkingSlot> getParkingSlots();

    public abstract List<Car> removeCars();
}
