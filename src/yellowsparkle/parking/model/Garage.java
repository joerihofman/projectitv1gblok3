package yellowsparkle.parking.model;

import yellowsparkle.parking.simulation.ParkingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public abstract class Garage {

    protected List<Garage> subGarages;

    public Garage(List<Garage> subGarages) {
        if (subGarages == null) {
            this.subGarages = new ArrayList<>();
        } else {
            this.subGarages = subGarages;
        }
    }

    /**
     * Iterates over slots in this garage
     * @param slotConsumer Consumer for slots.
     */
    public abstract void forEach(Consumer<ParkingSlot> slotConsumer);

    // Recursive foreach on subgarages
    public abstract void forAll(Consumer<ParkingSlot> slotConsumer);

    public void forEachGarage(Consumer<Garage> garageConsumer) {
        subGarages.forEach(garageConsumer);
    }

    public boolean containsGarage(Garage garage) {
        if (subGarages.contains(garage)) {
            return true;
        } else {
            final boolean[] returnValue = {false};      // Hacks be hacks, but they work.
            subGarages.forEach(g -> {if (g.containsGarage(garage)) returnValue[0] = true;});
            return returnValue[0];
        }
    }

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

    public void addGarage(Garage garage) {
        subGarages.add(garage);
    }

    public void removeGarage(Garage garage) {
        subGarages.remove(garage);
    }

    public abstract boolean acceptsCar(Car car);

    public abstract Car addCar(Car car, Position position) throws ParkingException;

    public abstract Car removeCar(Position position);

    public abstract void removeCar(Car car);

    public abstract boolean hasPosition(Position position);

    public abstract List<Car> getCars();

    public abstract Collection<ParkingSlot> getParkingSlots();

    public abstract List<Car> removeCars();
}
