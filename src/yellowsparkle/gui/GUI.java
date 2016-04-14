/*
* Copyright (c) 2016, ITV1G Group 1 and/or its affiliates. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*
* This code is free software; you can redistribute it and/or modify it
* under the terms of the GNU General Public License version 2 only, as
* published by the Free Software Foundation.
*
* This code is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
* FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
* version 2 for more details (a copy is not included in the LICENSE file
* that accompanied this code).
*/

package yellowsparkle.gui;

import yellowsparkle.Main;
import yellowsparkle.gui.types.controller.EntryPerTickCallback;
import yellowsparkle.gui.types.controller.ResetCallback;
import yellowsparkle.gui.types.controller.SpawnCallback;
import yellowsparkle.gui.types.controller.TickCallback;
import yellowsparkle.gui.types.view.*;
import yellowsparkle.parking.model.ParkingSlot;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class creates the user interface of the programme. It contains
 * a method to create the GUI, a method to "tick" the programme and
 * several methods that are being used while the programme is running.
 * @author ITV1G Group 1
 * @version 1.0
 */
public class GUI implements View, EntryQueueLengthAcceptor, TickCountAcceptor, UsedSlotListAcceptor, EmptySlotListAcceptor, SoldTicketCountAcceptor, TickCallback, ResetCallback, EntryPerTickCallback, SpawnCallback, EntryPerTickAcceptor {
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
    private JButton increaseEntryRateButton;
    private JButton decreaseEntryRateButton;
    private JButton addXCarsToButton;
    private JLabel carCount;
    private int queueLength;
    private int tickCount;
    private List<ParkingSlot> usedSlotList;
    private List<ParkingSlot> emptySlotList;
    private int ticketCount;
    private Consumer<Integer> tickCallback;
    private Consumer<Void> resetCallBack;
    private Consumer<Integer> entryPerTickCallback;
    private Consumer<Integer> spawnCallback;


    /**
     * This method adds the buttons to the GUI.
     */
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
        buttonReset.addActionListener(e -> resetCallBack.accept(null));

        //One step button
        buttonOnestep.addActionListener(e -> tickCallback.accept(1));

        //100 steps button extra
        buttonHundredsteps.addActionListener(e -> tickCallback.accept(100));

        increaseEntryRateButton.addActionListener( e -> entryPerTickCallback.accept(1));

        decreaseEntryRateButton.addActionListener( e -> entryPerTickCallback.accept(-1));

        addXCarsToButton.addActionListener( e -> spawnCallback.accept(25));
    }

    /**
     * This method is used in the GUI to show text in the labels.
     * Labels are in the tab "textbasedview".
     * @return labelQueue           shows current queue at the entrance
     * @return labelTicks           shows how many ticks have passed
     * @return labelTakenSpaces     shows how many spaces are taken by cars
     * @return labelFreeSpaces      shows how many spaces are empty
     * @return labelSoldTickets     shows how many regular tickets are sold
     */
    public void tick() {
        labelQueue.setText("In the queue there are " + queueLength + " cars");
        labelTicks.setText("There have been " + tickCount + " ticks");
        labelTakenSpaces.setText("There are " + usedSlotList.size() + " spaces used");
        labelFreeSpaces.setText("There are " + emptySlotList.size() + " spaces empty");
        labelSoldTickets.setText("There are " + ticketCount + " normal tickets sold");
    }

    /**
     * This method creates the window/frame for the GUI.
     * @return gui      returns the GUI window
     */
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

    /**
     * This method locates the dynamic images in the GUI,
     * for the piechart and the simulator.
     */
    private void createUIComponents() {
        imagePanel1=new ImagePanel();
        Main.addView(imagePanel1);
        pieChart = new PieChart();
        Main.addView((View) pieChart);
    }

    /**
     * This method implements the entry queue length method
     * @param queueLength
     */
    @Override
    public void setEntryQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    /**
     * This method implements the tick count method
     * @param tickCount
     */
    @Override
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    /**
     * This method impelments the used parking spaces method
     * @param usedSlotList
     */
    @Override
    public void setUsedSlotList(List<ParkingSlot> usedSlotList) {
        this.usedSlotList = usedSlotList;
    }

    /**
     * This method implements the empty parking spaces method
     * @param emptySlotList
     */
    @Override
    public void setEmptySlotList(List<ParkingSlot> emptySlotList) {
        this.emptySlotList = emptySlotList;
    }

    /**
     * This method implements the sold ticket count method
     * @param ticketCount
     */
    @Override
    public void setSoldTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    /**
     * This method implements the tick callback method
     * @param tickCallback
     */
    @Override
    public void setTickCallback(Consumer<Integer> tickCallback) {
        this.tickCallback = tickCallback;
    }

    /**
     * This method implements the reset tick callback method
     * @param resetCallback
     */
    @Override
    public void setResetCallback(Consumer<Void> resetCallback) {
        this.resetCallBack = resetCallback;
    }

    @Override
    public void setEntryPerTickCallback(Consumer<Integer> entryPerTickCallback) {
        this.entryPerTickCallback = entryPerTickCallback;
    }

    @Override
    public void setSpawnCallback(Consumer<Integer> spawnCallback) {
        this.spawnCallback = spawnCallback;
    }

    @Override
    public void setEntryPerTick(int i) {
        carCount.setText("Entries per tick: " + i);
    }
}
