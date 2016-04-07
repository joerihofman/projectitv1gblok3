package yellowsparkle.parking.model;

public class ParkingSlot {
    private Position position;
    private Car car;

    /**
     * Constructor for objects of class ParkingSlot
     */
    public ParkingSlot(Position position) {
        this.position = position;
    }

    /**
     * Convenience method for lambda usage
     * @return if the parking slot is empty.
     */
    public boolean isEmpty() {
        return car == null;
    }

    /**
     * Implement content equality.
     */
    public boolean equals(Object obj) {
        if(obj instanceof ParkingSlot) {
            ParkingSlot other = (ParkingSlot) obj;
            return other.getPosition().equals(position);
        } else {
            return false;
        }
    }

    /**
     * Return a string of the form floor,row,place.
     * @return A string representation of the location.
     */
    public String toString() {
        return "Parking:" + position;
    }

    /**
     * Getter for position
     * @return The position of the parking slot
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Getter for car on the parking slot
     * @return Current car on the parking slot
     */
    public Car getCar() {
        return car;
    }

    /**
     * Convenience method for removing a car from the slot
     * @return Car currently in the slot
     */
    public Car removeCar() {
        Car oldCar = car;
        car = null;
        return oldCar;
    }

    /**
     * Setter for car on the parking slot
     * @param car Car to set
     * @return Car that was on the slot
     */
    public Car setCar(Car car) {
        Car oldCar = this.car;
        this.car = car;
        car.setStatus(Car.Status.PARK);
        return oldCar;
    }
}