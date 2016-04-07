package yellowsparkle;

import yellowsparkle.gui.GUI;
import yellowsparkle.parking.SlotGenerator;
import yellowsparkle.parking.model.GarageImpl;
import yellowsparkle.parking.simulation.ParkingException;
import yellowsparkle.parking.simulation.SimulatorImpl;

import java.util.Random;

import static yellowsparkle.Globals.exit;
import static yellowsparkle.Globals.pause;
import static yellowsparkle.Globals.simulationThread;


/**
 * Generic init starting class
 */
public class Main {

    /**
     * Generic 'init' method
     * @param args commandline arguments
     */
    public static void main(String[] args) throws InterruptedException {
            //fill in the total floors, rows and places
        Globals.simulator = new SimulatorImpl(new GarageImpl(SlotGenerator.genericRectangular("Test", 6, 5)));
        Globals.gui = GUI.init();
        Globals.random = new Random();

        simulationThread = new Thread(new Loop());
        simulationThread.start();

        while(!exit) {
            Globals.gui.tick();
        }

        System.exit(0);
    }

    private static class Loop implements Runnable {
        @Override
        public void run() {
            while(!exit) {
                if (!pause) {
                    try {
                        Globals.simulator.tick();   // TODO: Deal with parking exceptions
                    } catch (ParkingException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    exit = true;
                }
            }
        }
    }

}
