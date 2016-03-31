package yellowsparkle;

import yellowsparkle.parking.AdHocCar;
import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.model.GarageSimulator;
import java.util.Random;



import java.awt.*;

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

        simulator = new GarageSimulator(new Garage(1, 5, 5));
        GUI gui = GUI.init();

        Random random = new Random();

        while(!Constants.EXIT) {
            gui.tick();
            if (!Constants.PAUSE) {
                simulator.tick();
                //adds a car each tick

                int r = random.nextInt(2);


                if (r == 1) {
                    simulator.queueCar(new AdHocCar(5));
                }
                if (r == 2) {
                    simulator.queueCar(new AdHocCar(5));
                    simulator.queueCar(new AdHocCar(6));
                }
            }
            System.out.println("Main loop tick!");
            Thread.sleep(250);
        }
        System.exit(0);
    }
}
