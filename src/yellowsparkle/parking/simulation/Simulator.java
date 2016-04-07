package yellowsparkle.parking.simulation;

import yellowsparkle.parking.model.Garage;

public abstract class Simulator {
    // dummy comment
    public abstract void tick() throws ParkingException;

    public abstract void tick(int ticks) throws ParkingException;

    public abstract Garage getGarage();

    //return the size of the queue
    public abstract int queueLength();

    //this will remove all the cars from array and reset the tickcount to zero
    public abstract void reset();

    public abstract boolean canExit();

    //checks if the car can exit
    public abstract void setCanExit(boolean canExit);

    //keeps tracks of total ticks
    public abstract int getTickCount();

    public abstract int getSoldTickets();
}
