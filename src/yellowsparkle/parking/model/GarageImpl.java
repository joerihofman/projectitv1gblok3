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

public class GarageImpl extends Garage {
    private HashMap<Position, ParkingSlot> parkingSlots;
    private Predicate<Car> carPredicate;

    public GarageImpl(List<ParkingSlot> slots, Predicate<Car> carPredicate) {
        this(slots, carPredicate, null);
    }

    public GarageImpl(List<ParkingSlot> slots, Predicate<Car> carPredicate, List<Garage> subGarages) {
        super(subGarages);
        this.carPredicate = carPredicate;
        this.parkingSlots = new HashMap<>();
        slots.forEach(parkingSlot -> parkingSlots.put(parkingSlot.getPosition(), parkingSlot));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void forEach(Consumer<ParkingSlot> slotConsumer) {
        parkingSlots.values().forEach(slotConsumer);
    }

    @Override
    public void forAll(Consumer<ParkingSlot> slotConsumer) {
        forEach(slotConsumer);
        subGarages.forEach(garage -> garage.forAll(slotConsumer));
    }

    @Override
    public ParkingSlot getRandomEmptyLocation() {
        List<ParkingSlot> emptyLocations = getEmptyLocations();
        if (emptyLocations.size() > 0) {
            return emptyLocations.get(Main.random.nextInt(emptyLocations.size()));
        } else {
            return null;
        }
    }

    @Override
    public List<ParkingSlot> getEmptyLocations() {
        return parkingSlots.values().stream().filter(ParkingSlot::isEmpty).collect(Collectors.toList());
    }

    @Override
    public Collection<ParkingSlot> getTotalSpaces() {
        return parkingSlots.values();
    }

    @Override
    public List<ParkingSlot> getUsedSpaces() {
        return parkingSlots.values().stream().filter(parkingSlot -> !parkingSlot.isEmpty()).collect(Collectors.toList());
    }

    @Override
    public boolean acceptsCar(Car car) {
        return carPredicate.test(car);
    }

    @Override
    public Car addCar(Car car, Position position) throws ParkingException {
        if (!carPredicate.test(car)) throw new ParkingException("Car not accepted!");
        return parkingSlots.get(position).setCar(car);
    }

    @Override
    public Car removeCar(Position position) {
        return parkingSlots.get(position).removeCar();
    }

    @Override
    public void removeCar(Car car) {
        parkingSlots.values().stream().filter(parkingSlot -> parkingSlot.getCar() == car).forEach(ParkingSlot::removeCar);
    }

    @Override
    public boolean hasPosition(Position position) {
        return parkingSlots.containsKey(position);
    }

    @Override
    public List<Car> getCars() {
        return parkingSlots.values().stream().map(ParkingSlot::getCar).collect(Collectors.toList());
    }

    @Override
    public Collection<ParkingSlot> getParkingSlots() {
        return parkingSlots.values();
    }

    @Override
    public List<Car> removeCars() {
        return parkingSlots.values().stream().map(ParkingSlot::removeCar).collect(Collectors.toList());
    }
}
