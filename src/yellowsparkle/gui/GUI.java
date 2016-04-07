package yellowsparkle.gui;

import yellowsparkle.Globals;
import yellowsparkle.Piechart.CreatePiechart;
import yellowsparkle.Piechart.Model;
import yellowsparkle.gui.ImagePanel;
import yellowsparkle.parking.simulation.ParkingException;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

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
    private JLabel labelSoldTickets;
    private ImagePanel piechartPanel;
    private JPanel pieChart;


    public GUI() {
        //Pause button
        buttonPause.addActionListener(e -> {
            if (Globals.pause) {
                buttonPause.setText("Pause");
                Globals.pause = false;
            } else {
                Globals.pause = true;
                buttonPause.setText("Resume");
            }

        });

        //Reset button
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Globals.simulator.reset();
            }
        });

        //One step button
        buttonOnestep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Globals.simulator.tick();
                } catch (ParkingException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //100 steps button extra
        buttonHundredsteps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Globals.simulator.tick(100);
                } catch (ParkingException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void tick() {
        //count the queue
        imagePanel1.update(Globals.simulator.getGarage().getParkingSlots(), Globals.simulator.getGarage().getCars());
        //alternative text view
        labelQueue.setText("In the queue there are " + Globals.simulator.queueLength() + " cars");
        labelTicks.setText("There have been " + Globals.simulator.getTickCount() + " ticks");
        labelTakenSpaces.setText("There are " + Globals.simulator.getGarage().getUsedSpaces().size() + " spaces used");
        labelFreeSpaces.setText("There are " + Globals.simulator.getGarage().getEmptyLocations().size() + " spaces empty");
        labelSoldTickets.setText("There are " + Globals.simulator.getSoldTickets() + " normal tickets sold");
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
        //new CreatePiechart();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Globals.exit = true;
            }
        });
        return gui;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        imagePanel1=new ImagePanel();
    }
}
