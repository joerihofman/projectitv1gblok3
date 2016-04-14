package yellowsparkle.parking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class is used for the garages
 * @author ITV1G Group 1
 * @version 1.0
 */
public abstract class Garage {

    protected List<Garage> subGarages;

    /**
     * This method creates the garage list
     * @param subGarages The subgarages
     */
    public Garage(List<Garage> subGarages) {
        if (subGarages == null) {
            this.subGarages = new ArrayList<>();
        } else {
            this.subGarages = subGarages;
        }
    }

    /**
     * Finds the deepest level garage a car is allowed into
     * @param car Car to find a garage for
     * @return Deepest level garage that accepts the garage
     */
    public Garage acceptingSubGarage(Car car) {
        final Garage[] g = new Garage[1];
        forEachGarage(garage -> {
            if (g[0] == null){
                g[0] = garage.acceptingSubGarage(car);
            }
        });
        if (g[0] != null) {
            return g[0];
        } else {
            if (acceptsCar(car) && getEmptyLocations().size() > 0) {
                return this;
            } else {
                return null;
            }
        }
    }

    /**
     * Iterates over slots in this garage
     * @param slotConsumer Consumer for slots.
     */
    public abstract void forEach(Consumer<ParkingSlot> slotConsumer);

    /**
     * Iterates over all slots in this garage
     * @param slotConsumer Consumer for slots
     */
    public abstract void forAll(Consumer<ParkingSlot> slotConsumer);

    /**
     * Iterates over each garage in the garage
     * @param garageConsumer Consumer for garage
     */
    public void forEachGarage(Consumer<Garage> garageConsumer) {
        subGarages.forEach(garageConsumer);
    }

    /**
     * This method checks if a garage is used in the garage
     * @param garage    the garage
     * @return boolean
     */
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
     * @see GarageImpl
    */
    public abstract ParkingSlot getRandomEmptyLocation();

    /**
    * Gives back a list with empty parking slots in the garage
    */
    public abstract List<ParkingSlot> getEmptyLocations();

    /**
     * Returns the current value of parkingSlots
     * @return parkingSlots.values()
     * @see GarageImpl
     */
    public abstract Collection<ParkingSlot> getTotalSpaces();

    /**
     * Activates the getUsedSpaces method
     * @see GarageImpl
     */
    public abstract List<ParkingSlot> getUsedSpaces();

    /**
     * This method adds a garage
     * @param garage garage
     */
    public void addGarage(Garage garage) {
        subGarages.add(garage);
    }

    /**
     * This method removes a garage
     * @param garage garage
     */
    public void removeGarage(Garage garage) {
        subGarages.remove(garage);
    }

    /**
     * This method accepts a car, if accepted
     * @param car   the car
     * @see GarageImpl
     */
    public abstract boolean acceptsCar(Car car);

    /**
     * This method activates the method hasPosition
     * @param position  the position
     * @see GarageImpl
     */
    public abstract boolean hasPosition(Position position);

    /**
     * This method activates the method getCars
     * @see GarageImpl
     */
    public abstract List<Car> getCars();

    /**
     * This method activates the method getParkingSlots
     * @see GarageImpl
     */
    public abstract Collection<ParkingSlot> getParkingSlots();

    /**
     * This method activates the method removeCars
     * @see GarageImpl
     */
    public abstract void removeCars();

    /**
     * This method gets all parking slots
     * @see GarageImpl
     */
    public abstract Collection<ParkingSlot> getParkingAllSlots();

    public abstract void removeAllCars();

    public abstract GaragePredicate getPredicate();
}
