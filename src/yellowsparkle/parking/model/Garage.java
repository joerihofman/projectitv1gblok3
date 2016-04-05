package yellowsparkle.parking.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Garage {
    private HashMap<Position, ParkingSlot> parkingSlots;


    public Garage(ArrayList<ParkingSlot> slots) {
        this.parkingSlots = new HashMap<>();
        slots.forEach(parkingSlot -> parkingSlots.put(parkingSlot.getPosition(), parkingSlot));
    }

    public void forEach(Consumer<ParkingSlot> slotConsumer) {
        parkingSlots.values().forEach(slotConsumer);
    }

    public ParkingSlot getFirstEmptyLocation() {
        List<ParkingSlot> emptyLocations = getEmptyLocations();
        if (emptyLocations.size() > 0) {
            return emptyLocations.get(0);
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
        return (int) parkingSlots.values().stream().filter(ParkingSlot::isEmpty).count();
    }

    public Car addCar(Car car, Position position) {
        return parkingSlots.get(position).setCar(car);
    }

    public Car removeCar(Position position) {
        return parkingSlots.get(position).removeCar();
    }



    public boolean hasPosition(Position position) {
        return parkingSlots.containsKey(position);
    }

    public Car getCarInSlot(ParkingSlot parkingSlot) {
        if (!validateLocation(parkingSlot)) throw new IllegalArgumentException("Invalid parkingSlot!");
        return cars[parkingSlot.getFloor()][parkingSlot.getRow()][parkingSlot.getPlace()];
    }

    public ArrayList<Car> getCars() {
        ArrayList<Car> list = new ArrayList<>();
        for (Car[][] array1 : cars) {
            for (Car[] array2 : array1) {
                for (Car car : array2) {
                    if (car != null) {
                        list.add(car);
                    }
                }
            }
        }
        return list;
    }

    public ArrayList<ParkingSlot> getParkingSlots() {
        if (parkingSlots == null) {
            parkingSlots = new ArrayList<>();
            for (int floor = 0; floor < floors; floor++) {
                for (int row = 0; row < rows; row++) {
                    for (int place = 0; place < places; place++) {
                        parkingSlots.add(new ParkingSlot(floor, row, place));
                    }
                }
            }
        }
        return parkingSlots;
    }

    public ArrayList<Car> removeCars() {
        ArrayList<Car> oldCars = getCars();
        cars = new Car[floors][rows][places];
        return oldCars;
    }

    public int floorCount() {
        return floors;
    }

    public int rowCount() {
        return rows;
    }

    public int placeCount() {
        return places;
    }
}
