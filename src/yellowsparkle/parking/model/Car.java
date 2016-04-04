package yellowsparkle.parking.model;

import yellowsparkle.parking.Location;

public class Car {

    private Location location;
    private Ticket[] tickets;

    /**
     * Constructor for objects of class Car
     */
    public Car(Ticket... tickets) {
        this.tickets = tickets;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void tick() {

    }

    public Ticket[] getTickets() {
        return tickets;
    }
}