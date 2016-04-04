package yellowsparkle;

import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.simulation.Simulator;
import yellowsparkle.parking.simulation.ParkingException;

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
        simulator = new Simulator(new Garage(1, 3, 5));
        GUI gui = GUI.init();

        random = new Random();

        while(!Constants.EXIT) {
            gui.tick();
            if (!Constants.PAUSE) {
                simulator.tick();   // TODO: Deal with parking exceptions
            }
            System.out.println("Main loop tick!");
            Thread.sleep(250);
        }
        System.exit(0);
    }

}
