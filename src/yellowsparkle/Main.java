package yellowsparkle;

import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.model.GarageSimulator;

/**
 * Generic main starting class
 */
public class Main {
    public static GarageSimulator simulator;
    /**
     * Generic 'main' method
     * @param args commandline arguments
     */
    public static void main(String[] args) throws InterruptedException {

        simulator = new GarageSimulator(new Garage(5, 5, 5));
        GUI.main(args);

        while(!Constants.EXIT) {
            if (!Constants.PAUSE) {
                simulator.tick();
            }
            System.out.println("Main loop tick!");
            Thread.sleep(250);
        }
        System.exit(0);
    }
}
