package yellowsparkle.parking.model;

import yellowsparkle.Main;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GarageImpl extends Garage {
    private HashMap<Position, ParkingSlot> parkingSlots;


    public GarageImpl(List<ParkingSlot> slots) {
        this.parkingSlots = new HashMap<>();
        slots.forEach(parkingSlot -> parkingSlots.put(parkingSlot.getPosition(), parkingSlot));
    }

    @Override
    public void forEach(Consumer<ParkingSlot> slotConsumer) {
        parkingSlots.values().forEach(slotConsumer);
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
    public int getTotalSpaces() {
        return parkingSlots.size();
    }

    @Override
    public int getUsedSpaces() {
        return (int) parkingSlots.values().stream().filter(parkingSlot -> !parkingSlot.isEmpty()).count();
    }

    @Override
    public Car addCar(Car car, Position position) {
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
