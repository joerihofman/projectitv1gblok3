package yellowsparkle.view;

import yellowsparkle.Main;
import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.view.types.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.function.Consumer;

public class GUI implements View, EntryQueueLengthAcceptor, TickCountAcceptor, UsedSlotListAcceptor, EmptySlotListAcceptor, SoldTicketCountAcceptor, TickCallback, ResetCallback {
    private JPanel panel1;
    private JButton buttonReset;
    private JButton buttonHundredsteps;
    private JButton buttonOnestep;
    private JLabel labelQueue;
    private JButton buttonPause;
    private ImagePanel imagePanel1;
    private JTabbedPane tabbedPane1;
    private JPanel simulatorPanel;
    private JLabel labelTicks;
    private JLabel labelTakenSpaces;
    private JLabel labelFreeSpaces;
    private JLabel labelSoldTickets;
    private JPanel pieChart;
    private int queueLength;
    private int tickCount;
    private List<ParkingSlot> usedSlotList;
    private List<ParkingSlot> emptySlotList;
    private int ticketCount;
    private Consumer<Integer> tickCallback;
    private Consumer<Void> resetCallBack;


    public GUI() {
        //Pause button
        buttonPause.addActionListener(e -> {
            if (Main.pause) {
                buttonPause.setText("Pause");
                Main.pause = false;
            } else {
                Main.pause = true;
                buttonPause.setText("Resume");
            }
        });

        //Reset button
        buttonReset.addActionListener(e -> {if (resetCallBack != null) resetCallBack.accept(null);});

        //One step button
        buttonOnestep.addActionListener(e -> {if (tickCallback != null) {tickCallback.accept(1);}});

        //100 steps button extra
        buttonHundredsteps.addActionListener(e -> {if (tickCallback != null) {tickCallback.accept(100);}});
    }

    public void tick() {
        labelQueue.setText("In the queue there are " + queueLength + " cars");
        labelTicks.setText("There have been " + tickCount + " ticks");
        labelTakenSpaces.setText("There are " + usedSlotList.size() + " spaces used");
        labelFreeSpaces.setText("There are " + emptySlotList.size() + " spaces empty");
        labelSoldTickets.setText("There are " + ticketCount + " normal tickets sold");
    }


    //TODO Three new alternative views, piechart or graph etc...;
    // Joeri is busy making an alternative view :( ;
    /*public void alternativeView() {
        labelQueue.setText("In the queue there are " + Main.simulator.queueLength() + " cars");
        labelTicks.setText("There have been " + Main.simulator.getTickCount() + " ticks");
    }*/



    public static GUI init() {
        GUI gui = new GUI();
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(gui.panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.exit = true;
            }
        });
        return gui;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        imagePanel1=new ImagePanel();
        Main.addView(imagePanel1);
        pieChart = new PieChart();
        Main.addView((View) pieChart);
    }

    @Override
    public void setEntryQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    @Override
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    @Override
    public void setUsedSlotList(List<ParkingSlot> usedSlotList) {
        this.usedSlotList = usedSlotList;
    }

    @Override
    public void setEmptySlotList(List<ParkingSlot> emptySlotList) {
        this.emptySlotList = emptySlotList;
    }

    @Override
    public void setSoldTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    @Override
    public void setTickCallback(Consumer<Integer> tickCallback) {
        this.tickCallback = tickCallback;
    }

    @Override
    public void setResetCallback(Consumer<Void> resetCallback) {
        this.resetCallBack = resetCallback;
    }
}
