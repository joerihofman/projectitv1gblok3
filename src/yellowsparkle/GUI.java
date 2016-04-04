package yellowsparkle;

import yellowsparkle.gui.ImagePanel;
import yellowsparkle.parking.simulation.ParkingException;

import javax.swing.*;
import java.awt.event.*;

public class GUI {
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


    public GUI() {
        //Pause button
        buttonPause.addActionListener(e -> {
            if (Constants.PAUSE) {
                buttonPause.setText("Pause");
                Constants.PAUSE = false;
            } else {
                Constants.PAUSE = true;
                buttonPause.setText("Resume");
            }

        });

        //Reset button
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.simulator.reset();
            }
        });

        //One step button
        buttonOnestep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.simulator.tick();
                } catch (ParkingException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //100 steps button
        buttonHundredsteps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.simulator.tick(100);
                } catch (ParkingException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void tick() {
        //count the queue
        imagePanel1.update(Main.simulator.getGarage().getLocations(), Main.simulator.getGarage().getCars());
        //alternative text view
        labelQueue.setText("In the queue there are " + Main.simulator.queueLength() + " cars");
        labelTicks.setText("There have been " + Main.simulator.getTickCount() + " ticks");
        labelTakenSpaces.setText("There are " + Main.simulator.usedParkingSpaces() + " spaces used");
        labelFreeSpaces.setText("There are " + Main.simulator.freeParkingSpaces() + " spaces empty");
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
                Constants.EXIT = true;
            }
        });
        return gui;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        imagePanel1=new ImagePanel();
    }
}
