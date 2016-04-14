package yellowsparkle.parking.model;

import yellowsparkle.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class GarageImpl extends Garage {
    private HashMap<Position, ParkingSlot> parkingSlots;
    private GaragePredicate carPredicate;

    public GarageImpl(List<ParkingSlot> slots, GaragePredicate carPredicate) {
        this(slots, carPredicate, null);
    }

    public GarageImpl(List<ParkingSlot> slots, GaragePredicate carPredicate, List<Garage> subGarages) {
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

    /**
     * @inheritDoc
     */
    @Override
    public void forAll(Consumer<ParkingSlot> slotConsumer) {
        forEach(slotConsumer);
        subGarages.forEach(garage -> garage.forAll(slotConsumer));
    }

    /**
     * @inheritDoc
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
     * @inheritDoc
     */
    @Override
    public List<ParkingSlot> getEmptyLocations() {
        return parkingSlots.values().stream().filter(ParkingSlot::isEmpty).collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<ParkingSlot> getTotalSpaces() {
        return parkingSlots.values();
    }


    /**
     * @inheritDoc
     */
    @Override
    public List<ParkingSlot> getUsedSpaces() {
        return parkingSlots.values().stream().filter(parkingSlot -> !parkingSlot.isEmpty()).collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean acceptsCar(Car car) {
        return carPredicate.test(car);
    }


    /**
     * @inheritDoc
     */
    @Override
    public boolean hasPosition(Position position) {
        return parkingSlots.containsKey(position);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Car> getCars() {
        return parkingSlots.values().stream().map(ParkingSlot::getCar).collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<ParkingSlot> getParkingSlots() {
        return parkingSlots.values();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeCars() {
        forEach(ParkingSlot::removeCar);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<ParkingSlot> getParkingAllSlots() {
        List<ParkingSlot> slots = new ArrayList<>();
        forAll(slots::add);
        return slots;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeAllCars() {
        forAll(ParkingSlot::removeCar);
    }

    /**
     * @inheritDoc
     */
    @Override
    public GaragePredicate getPredicate() {
        return carPredicate;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<ParkingSlot> getAllUsedSpaces() {
        List<ParkingSlot> usedSpaces = new ArrayList<>();
        forAll(parkingSlot -> {
            if (!parkingSlot.isEmpty()) {
                usedSpaces.add(parkingSlot);
            }
        });
        return usedSpaces;
    }
}
