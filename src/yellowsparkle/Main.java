package yellowsparkle;

import yellowsparkle.parking.SlotGenerator;
import yellowsparkle.parking.model.GarageImpl;
import yellowsparkle.parking.simulation.ParkingException;
import yellowsparkle.parking.simulation.Simulator;
import yellowsparkle.parking.simulation.SimulatorImpl;
import yellowsparkle.view.GUI;
import yellowsparkle.view.View;
import yellowsparkle.view.types.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;


/**
 * Generic init starting class
 */
public class Main {

    private static Simulator simulator;
    public static Random random;
    public static boolean exit;
    public static boolean pause;
    private static ArrayList<View> viewList;
    private static Consumer<Void> resetCallback = aVoid -> {if (simulator != null) simulator.reset();};
    private static Consumer<Integer> tickCallback = new Consumer<Integer>() {
        @Override
        public void accept(Integer integer) {
            if (simulator != null) try {
                simulator.tick(integer);
            } catch (ParkingException e) {
                e.printStackTrace();
            }
        }};

    /**
     * Generic 'init' method
     * @param args commandline arguments
     */
    public static void main(String[] args) throws InterruptedException {
        viewList = new ArrayList<>();
        simulator = new SimulatorImpl(new GarageImpl(SlotGenerator.genericRectangular("Test", 6, 5)));
        random = new Random();

        Thread simulationThread = new Thread(new Loop());
        simulationThread.start();

        addView(GUI.init());

        while(!exit) {
            viewList.forEach(view -> {
                if (view instanceof EmptySlotListAcceptor) {
                    ((EmptySlotListAcceptor) view).setEmptySlotList(simulator.getGarage().getEmptyLocations());
                }
                if (view instanceof EntryQueueLengthAcceptor) {
                    ((EntryQueueLengthAcceptor) view).setEntryQueueLength(simulator.queueLength());
                }
                if (view instanceof SoldTicketCountAcceptor) {
                    ((SoldTicketCountAcceptor) view).setSoldTicketCount(simulator.getSoldTickets());
                }
                if (view instanceof TickCountAcceptor) {
                    ((TickCountAcceptor) view).setTickCount(simulator.getTickCount());
                }
                if (view instanceof UsedSlotListAcceptor) {
                    ((UsedSlotListAcceptor) view).setUsedSlotList(simulator.getGarage().getUsedSpaces());
                }
                if (view instanceof ParkingSlotCollectionAcceptor) {
                    ((ParkingSlotCollectionAcceptor) view).setParkingSlotCollection(simulator.getGarage().getParkingSlots());
                }
                view.tick();
            });
        }
        System.exit(0);
    }

    public static void addView(View view) {
        viewList.add(view);
        if (view instanceof ResetCallback) {
            ((ResetCallback) view).setResetCallback(resetCallback);
        }
        if (view instanceof TickCallback) {
            ((TickCallback) view).setTickCallback(tickCallback);
        }
    }

    private static class Loop implements Runnable {

        @Override
        public void run() {
            while(!exit) {
                if (!pause) {
                    try {
                        simulator.tick();   // TODO: Deal with parking exceptions
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
