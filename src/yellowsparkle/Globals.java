package yellowsparkle;

import yellowsparkle.gui.GUI;
import yellowsparkle.parking.simulation.Simulator;

import java.util.Random;

public class Globals {
    public static boolean pause = false;
    public static boolean exit = false;
    public static Random random;
    public static GUI gui;
    public static Simulator simulator;
    public static Thread simulationThread;
}
