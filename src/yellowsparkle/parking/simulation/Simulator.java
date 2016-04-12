package yellowsparkle.parking.simulation;

import yellowsparkle.parking.model.Garage;

/**
 * This is an abstract class
 */
public abstract class Simulator {
    /**
     * @throws ParkingException When the car has NULL input.
     */
    public abstract void tick() throws ParkingException;

    public abstract void tick(int ticks) throws ParkingException;


    public abstract Garage getGarage();

    /**
     * This will be used to keep track of the length of the queue
     * @return the size of the queue
     */
    public abstract int queueLength();

    /**
     * This will remove all the cars from array and reset the tickcount to zero
     */
    public abstract void reset();

    /**
     * This is a check that is being used to check if a car can exit the garage
     */
    public abstract boolean canExit();

    /**
     * @param canExit This gives a boolean value to let a check leave the garage
     */
    public abstract void setCanExit(boolean canExit);


    /**
     * This counts the total amount of ticks.
     * @return the tickcount
     */
    public abstract int getTickCount();

    /**
     * This counts the total amount of sold tickets
     * @return the total amount of the sold tickets
     */
    public abstract int getSoldTickets();
}