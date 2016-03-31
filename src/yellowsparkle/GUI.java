package yellowsparkle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    private JPanel panel1;
    private JButton buttonReset;
    private JButton buttonHundredsteps;
    private JButton buttonOnestep;
    private JLabel labelQueue;
    private JButton buttonPause;
    private JLabel labelTicks;


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
                Main.simulator.tick();
            }
        });

        //100 steps button
        buttonHundredsteps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.simulator.tick(100);
            }
        });

    }

    public void tick() {
        //count the queue
        labelQueue.setText("In the queue there are " + Main.simulator.queueLength() + " cars");
        labelTicks.setText("There have been " + Main.simulator.getTickCount() + " ticks");
    }

    //TODO Een alternatieve view;
    // Hier is joeri bezig voor een alternatieve view...
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
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Constants.EXIT = true;
            }
        });
        return gui;
    }

}
