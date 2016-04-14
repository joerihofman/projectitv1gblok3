package yellowsparkle.parking.simulation;

import yellowsparkle.parking.model.Garage;

/**
 * Abstract superclass for Simulators
 * @author ITV1G Group 1
 * @version 1.0
 */
public abstract class Simulator {
    /**
     * Update this simulator 1 tick
     */
    public abstract void tick();


    /**
     * Gets the garage used in this simulator
     * @return garage in the simulator
     */
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

    /**
     * Gets the cars entering per tick
     * @return Cars entering per tick
     */
    public abstract Integer getEntryPerTick();

    /**
     * Sets the cars entering per tick
     * @param i Cars to enter per tick
     */
    public abstract void setEntryPerTick(int i);

    /**
     * Spawns new cars
     * @param integer cars to spawn
     */
    public abstract void spawn(int integer);
}