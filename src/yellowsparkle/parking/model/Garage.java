package yellowsparkle.parking.model;

import yellowsparkle.parking.Car;
import yellowsparkle.parking.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

public class Garage {
    private int floors;
    private int rows;
    private int places;
    private Car[][][] cars;

    public Garage(int floors, int rows, int places) {
        assert (floors > 0 && rows > 0 && places > 0);
        cars = new Car[floors][rows][places];
    }

    public void forEach(Consumer<Car> carConsumer) {
        for (int floor = 0; floor < floors; floor++) {
            for (int row = 0; row < rows; row++) {
                for (int place = 0; place < places; place++) {
                    if (cars[floor][row][place] == null) {
                        carConsumer.accept(cars[floor][row][place]);
                    }
                }
            }
        }
    }

    public Location getFirstEmptyLocation() {
        for (int floor = 0; floor < floors; floor++) {
            for (int row = 0; row < rows; row++) {
                for (int place = 0; place < places; place++) {
                    if (cars[floor][row][place] == null) {
                        return new Location(floor, row, place);
                    }
                }
            }
        }
        return null;
    }

    public int totalSpaces() {
        return floors * rows * places;
    }

    public int usedSpaces() {
        int i = 0;
        for (Car[][] array1 : cars) {
            for (Car[] array2 : array1) {
                for (Car car : array2) {
                    if (car != null) i++;
                }
            }
        }
        return i;
    }

    public boolean addCar(Car car, Location location) {
        Car currentCar = getCarInSlot(location);
        if (currentCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            return true;
        } else {
            return false;
        }
    }

    public Car removeCar(Location location) {
        Car car = cars[location.getFloor()][location.getRow()][location.getPlace()];
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        return car;
    }

    public boolean validateLocation(Location location) {
        return (cars.length > location.getFloor()) &&
                (cars[location.getFloor()].length > location.getRow()) &&
                (cars[location.getFloor()][location.getRow()].length > location.getPlace());
    }

    public Car getCarInSlot(Location location) {
        if (!validateLocation(location)) throw new IllegalArgumentException("Invalid location!");
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public ArrayList<Car> getCars() {
        ArrayList<Car> list = new ArrayList<>();
        for (Car[][] array1 : cars) {
            for (Car[] array2 : array1) {
                Collections.addAll(list, array2);
            }
        }
        return list;
    }

    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        for (int floor = 0; floor < floors; floor++) {
            for (int row = 0; row < rows; row++) {
                for (int place = 0; place < places; place++) {
                    locations.add(new Location(floor, row, place));
                }
            }
        }
        return locations;
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
