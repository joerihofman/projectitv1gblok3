package yellowsparkle.parking.model;

import yellowsparkle.Main;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Garage {
    private HashMap<Position, ParkingSlot> parkingSlots;


    public Garage(List<ParkingSlot> slots) {
        this.parkingSlots = new HashMap<>();
        slots.forEach(parkingSlot -> parkingSlots.put(parkingSlot.getPosition(), parkingSlot));
    }

    public void forEach(Consumer<ParkingSlot> slotConsumer) {
        parkingSlots.values().forEach(slotConsumer);
    }

    public ParkingSlot getRandomEmptyLocation() {
        List<ParkingSlot> emptyLocations = getEmptyLocations();
        if (emptyLocations.size() > 0) {
            return emptyLocations.get(Main.random.nextInt(emptyLocations.size()));
        } else {
            return null;
        }
    }

    public List<ParkingSlot> getEmptyLocations() {
        return parkingSlots.values().stream().filter(ParkingSlot::isEmpty).collect(Collectors.toList());
    }

    public int totalSpaces() {
        return parkingSlots.size();
    }

    public int getUsedSpaces() {
        return (int) parkingSlots.values().stream().filter(parkingSlot -> !parkingSlot.isEmpty()).count();
    }

    public Car addCar(Car car, Position position) {
        return parkingSlots.get(position).setCar(car);
    }

    public Car removeCar(Position position) {
        return parkingSlots.get(position).removeCar();
    }

    public void removeCar(Car car) {
        parkingSlots.values().stream().filter(parkingSlot -> parkingSlot.getCar() == car).forEach(ParkingSlot::removeCar);
    }

    public boolean hasPosition(Position position) {
        return parkingSlots.containsKey(position);
    }

    public List<Car> getCars() {
        return parkingSlots.values().stream().map(ParkingSlot::getCar).collect(Collectors.toList());
    }

    public Collection<ParkingSlot> getParkingSlots() {
        return parkingSlots.values();
    }

    public List<Car> removeCars() {
        return parkingSlots.values().stream().map(ParkingSlot::removeCar).collect(Collectors.toList());
    }
}
