package yellowsparkle;

import org.w3c.dom.Document;
import yellowsparkle.gui.types.controller.EntryPerTickCallback;
import yellowsparkle.gui.types.controller.ResetCallback;
import yellowsparkle.gui.types.controller.SpawnCallback;
import yellowsparkle.gui.types.controller.TickCallback;
import yellowsparkle.gui.types.view.*;
import yellowsparkle.parking.SlotUtils;
import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.model.GarageImpl;
import yellowsparkle.parking.model.GaragePredicate;
import yellowsparkle.parking.simulation.Simulator;
import yellowsparkle.parking.simulation.SimulatorImpl;
import yellowsparkle.gui.GUI;
import yellowsparkle.gui.View;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
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
    private static Loop loop;
    private static Consumer<Void> resetCallback = aVoid -> {if (simulator != null) simulator.reset();};
    private static Consumer<Integer> tickCallback = integer -> {
        if (loop != null) {
            loop.preparedTicks += integer;
        }
    };
    private static Consumer<Integer> entryPerTickCallback = integer -> simulator.setEntryPerTick(simulator.getEntryPerTick() + integer);
    private static Consumer<Integer> spawnCarCallback = integer -> simulator.spawn(integer);

    /**
     * Generic 'init' method
     * @param args commandline arguments
     */

    public static void main(String[] args) throws InterruptedException {
        viewList = new ArrayList<>();
        Garage garage = new GarageImpl(SlotUtils.genericRectangular("Test", 30, 25), new GaragePredicate(GaragePredicate.EnumType.ANY));
        simulator = new SimulatorImpl(garage);
        random = new Random();

        garage.addGarage(new GarageImpl(SlotUtils.genericRectangular("Test2", 6, 5, 1200, 0), new GaragePredicate(GaragePredicate.EnumType.CORPORATE_ONLY, "AHOLD")));

        try {
            Document test = SlotUtils.toXML(garage);
            DOMSource domSource = new DOMSource(test);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            System.out.println(writer.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        loop = new Loop();
        Thread simulationThread = new Thread(loop);
        simulationThread.start();

        addView(GUI.init());
        guiLoop();
        System.exit(0);
    }

    public static void guiLoop() {
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
                    ((ParkingSlotCollectionAcceptor) view).setParkingSlotCollection(simulator.getGarage().getParkingAllSlots());
                }
                if (view instanceof EntryPerTickAcceptor) {
                    ((EntryPerTickAcceptor) view).setEntryPerTick(simulator.getEntryPerTick());
                }
                view.tick();
            });
        }
    }

    public static void addView(View view) {
        viewList.add(view);
        if (view instanceof ResetCallback) {
            ((ResetCallback) view).setResetCallback(resetCallback);
        }
        if (view instanceof TickCallback) {
            ((TickCallback) view).setTickCallback(tickCallback);
        }
        if (view instanceof EntryPerTickCallback) {
            ((EntryPerTickCallback) view).setEntryPerTickCallback(entryPerTickCallback);
        }
        if (view instanceof SpawnCallback) {
            ((SpawnCallback) view).setSpawnCallback(spawnCarCallback);
        }
    }

    private static class Loop implements Runnable {
        private int preparedTicks;
        @Override
        public void run() {
            while(!exit) {
                if (!pause) {
                    simulator.tick();   // TODO: Deal with parking exceptions
                }
                try {
                    if (preparedTicks > 0) {
                        preparedTicks--;
                    } else {
                        Thread.sleep(250);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    exit = true;
                }
            }
        }
    }

}
