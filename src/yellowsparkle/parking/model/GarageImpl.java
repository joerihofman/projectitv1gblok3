package yellowsparkle.parking.model;

import yellowsparkle.Main;
import yellowsparkle.parking.simulation.ParkingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class is made for implementing the garage
 * @author ITV1G Group 1
 * @version 1.0
 * @since 31/3/16
 */
public class GarageImpl extends Garage {
    private HashMap<Position, ParkingSlot> parkingSlots;
    private Predicate<Car> carPredicate;

    /**
     * This method is the constructor if not every value is known
     * @param slots         Parking spaces
     * @param carPredicate  Parking prediction of the car
     */
    public GarageImpl(List<ParkingSlot> slots, Predicate<Car> carPredicate) {
        this(slots, carPredicate, null);
    }

    /**
     * This method is the constructor if every value is known
     * @param slots         Parking spaces
     * @param carPredicate  Parking prediction of the car
     * @param subGarages    Sub garage parking spaces
     */
    public GarageImpl(List<ParkingSlot> slots, Predicate<Car> carPredicate, List<Garage> subGarages) {
        super(subGarages);
        this.carPredicate = carPredicate;
        this.parkingSlots = new HashMap<>();
        slots.forEach(parkingSlot -> parkingSlots.put(parkingSlot.getPosition(), parkingSlot));
    }

    /**
     * This method assigns a customer to a parking slot
     * @param slotConsumer Consumer for slots.
     */
    @Override
    public void forEach(Consumer<ParkingSlot> slotConsumer) {
        parkingSlots.values().forEach(slotConsumer);
    }

    /**
     * This method assigns every customer to parking slots
     * @param slotConsumer Consumer for slots
     */
    @Override
    public void forAll(Consumer<ParkingSlot> slotConsumer) {
        forEach(slotConsumer);
        subGarages.forEach(garage -> garage.forAll(slotConsumer));
    }

    /**
     * This method gets a random empty parking spot
     * @return the empty parking spot
     */
    @Override
    public ParkingSlot getRandomEmptyLocation() {
        List<ParkingSlot> emptyLocations = getEmptyLocations();
        if (emptyLocations.size() > 0) {
            return emptyLocations.get(Main.random.nextInt(emptyLocations.size()));
        } else {
            return null;
        }
    }

    /**
     * This method gets the next empty parking spot
     * @return the empty parking spot
     */
    @Override
    public List<ParkingSlot> getEmptyLocations() {
        return parkingSlots.values().stream().filter(ParkingSlot::isEmpty).collect(Collectors.toList());
    }

    /**
     * This method returns the total amount of spaces
     * @return the amount of spaces
     */
    @Override
    public Collection<ParkingSlot> getTotalSpaces() {
        return parkingSlots.values();
    }


    /**
     * This method gets the used parking spaces in a list
     * @return parkingSlots.values().stream().filter()  returns the used parking spaces
     */
    @Override
    public List<ParkingSlot> getUsedSpaces() {
        return parkingSlots.values().stream().filter(parkingSlot -> !parkingSlot.isEmpty()).collect(Collectors.toList());
    }

    /**
     * This method accepts a car
     * @param car the car
     * @return a boolean
     */
    @Override
    public boolean acceptsCar(Car car) {
        return carPredicate.test(car);
    }

    /**
     * This method can add a car in the garage
     * @param car      the car
     * @param position the position of the car in the garage
     * @return position of the car
     * @throws ParkingException
     */
    @Override
    public Car addCar(Car car, Position position) throws ParkingException {
        if (!carPredicate.test(car)) throw new ParkingException("Car not accepted!");
        return parkingSlots.get(position).setCar(car);
    }

    /**
     * This method removes a car from a parking spot
     * @param position the position of the car
     * @return a remove car command
     */
    @Override
    public Car removeCar(Position position) {
        return parkingSlots.get(position).removeCar();
    }

    /**
     * This method removes a car from the car list
     * @param car the car
     */
    @Override
    public void removeCar(Car car) {
        parkingSlots.values().stream().filter(parkingSlot -> parkingSlot.getCar() == car).forEach(ParkingSlot::removeCar);
    }

    /**
     * This method checks a key in the position hashmap
     * @param position the position
     * @return position
     */
    @Override
    public boolean hasPosition(Position position) {
        return parkingSlots.containsKey(position);
    }

    /**
     * This method gets a car from a list
     * @return a list
     */
    @Override
    public List<Car> getCars() {
        return parkingSlots.values().stream().map(ParkingSlot::getCar).collect(Collectors.toList());
    }

    /**
     * This method gets the parking slots from the garage
     * @return values
     */
    @Override
    public Collection<ParkingSlot> getParkingSlots() {
        return parkingSlots.values();
    }

    /**
     * This method removes a car from the list
     * @return a list
     */
    @Override
    public List<Car> removeCars() {
        return parkingSlots.values().stream().map(ParkingSlot::removeCar).collect(Collectors.toList());
    }

    /**
     * This method gets all parking slots
     * @return the slots
     */
    @Override
    public Collection<ParkingSlot> getParkingAllSlots() {
        List<ParkingSlot> slots = new ArrayList<>();
        forAll(slots::add);
        return slots;
    }
}
