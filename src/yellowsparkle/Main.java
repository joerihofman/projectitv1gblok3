package yellowsparkle;

import yellowsparkle.parking.AdHocCar;
import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.model.GarageSimulator;
import java.util.Random;


/**
 * Generic init starting class
 */
public class Main {

    public static Random random;

    public static GarageSimulator simulator;
    /**
     * Generic 'init' method
     * @param args commandline arguments
     */

    public static void main(String[] args) throws InterruptedException {

            //fill in the total floors, rows and places
        simulator = new GarageSimulator(new Garage(1, 3, 5));
        GUI gui = GUI.init();

        random = new Random();

        while(!Constants.EXIT) {
            gui.tick();
            if (!Constants.PAUSE) {
                simulator.tick();
                }
            System.out.println("Main loop tick!");
            Thread.sleep(250);
        }
        System.exit(0);
    }

}
