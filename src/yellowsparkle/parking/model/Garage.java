/**
 * @author ITV1G Group 1
 * @version 1.0
 * @since 31/3/16
 */

package yellowsparkle.parking.model;

import yellowsparkle.parking.simulation.ParkingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public abstract class Garage {

    protected List<Garage> subGarages;

    /**.
     *
     * @param subGarages
     */
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

    /**
     *
     * @param slotConsumer
     */
    public abstract void forAll(Consumer<ParkingSlot> slotConsumer);

    /**
     *
     * @param garageConsumer
     */
    public void forEachGarage(Consumer<Garage> garageConsumer) {
        subGarages.forEach(garageConsumer);
    }

    /**
     *
     * @param garage
     * @return
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
    */
    public abstract ParkingSlot getRandomEmptyLocation();

    /**
    * Gives back a list with empty parking slots in the garage
    */
    public abstract List<ParkingSlot> getEmptyLocations();

    /**
     * Returns the current value of parkingSlots
     * @return parkingSlots.values();
     */
    public abstract Collection<ParkingSlot> getTotalSpaces();

    /**
     *
     * @return
     */
    public abstract List<ParkingSlot> getUsedSpaces();

    /**
     *
     * @param garage
     */
    public void addGarage(Garage garage) {
        subGarages.add(garage);
    }

    /**
     *
     * @param garage
     */
    public void removeGarage(Garage garage) {
        subGarages.remove(garage);
    }

    /**
     *
     * @param car
     * @return
     */
    public abstract boolean acceptsCar(Car car);

    /**
     *
     * @param car
     * @param position
     * @return
     * @throws ParkingException
     */
    public abstract Car addCar(Car car, Position position) throws ParkingException;

    /**
     *
     * @param position
     * @return
     */
    public abstract Car removeCar(Position position);

    /**
     *
     * @param car
     */
    public abstract void removeCar(Car car);

    /**
     *
     * @param position
     * @return
     */
    public abstract boolean hasPosition(Position position);

    /**
     *
     * @return
     */
    public abstract List<Car> getCars();

    /**
     *
     * @return
     */
    public abstract Collection<ParkingSlot> getParkingSlots();

    /**
     *
     * @return
     */
    public abstract List<Car> removeCars();
}
