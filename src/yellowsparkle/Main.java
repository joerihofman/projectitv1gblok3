package yellowsparkle;

import yellowsparkle.parking.AdHocCar;
import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.model.GarageSimulator;

/**
 * Generic init starting class
 */
public class Main {
    public static GarageSimulator simulator;
    /**
     * Generic 'init' method
     * @param args commandline arguments
     */
    public static void main(String[] args) throws InterruptedException {

        simulator = new GarageSimulator(new Garage(5, 5, 5));
        GUI gui = GUI.init();

        while(!Constants.EXIT) {
            gui.tick();
            if (!Constants.PAUSE) {
                simulator.tick();
                simulator.queueCar(new AdHocCar());
                simulator.queueCar(new AdHocCar());
            }
            System.out.println("Main loop tick!");
            Thread.sleep(250);
        }
        System.exit(0);
    }
}
