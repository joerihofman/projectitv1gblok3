package yellowsparkle;

import yellowsparkle.parking.SlotGenerator;
import yellowsparkle.parking.model.GarageImpl;
import yellowsparkle.parking.simulation.Simulator;
import yellowsparkle.parking.simulation.ParkingException;
import yellowsparkle.parking.simulation.SimulatorImpl;

import java.util.Random;


/** 
 * Generic init starting class
 */
public class Main {

    public static Random random;

    public static Simulator simulator;

    /**
     * Generic 'init' method
     * @param args commandline arguments
     */
    public static void main(String[] args) throws InterruptedException, ParkingException {
            //fill in the total floors, rows and places
        simulator = new SimulatorImpl(new GarageImpl(SlotGenerator.genericRectangular("Test", 6, 5)));
        GUI gui = GUI.init();

        random = new Random();

        long startTime;
        long endTime;
        while(!Constants.EXIT) {
            startTime = System.nanoTime();
            gui.tick();
            if (!Constants.PAUSE) {
                simulator.tick();   // TODO: Deal with parking exceptions
            }
            endTime = System.nanoTime();
            System.out.println("Main loop tick: " + ((endTime - startTime)/1000) + " ms");
           Thread.sleep(250);
        }
        System.exit(0);
    }

}
