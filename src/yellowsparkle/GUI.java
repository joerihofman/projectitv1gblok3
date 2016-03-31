package yellowsparkle;

import yellowsparkle.gui.ImagePanel;

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
    private JPanel imagePanel;
    private JSplitPane splitPane1;


    public GUI() {
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
        ((ImagePanel) imagePanel).update(Main.simulator.getGarage().getLocations(), Main.simulator.getGarage().getCars());
        imagePanel.repaint();
    }

    public static GUI init() {
        GUI gui = new GUI();
        gui.panel1.setEnabled(false);
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

    private void createUIComponents() {
        imagePanel = new ImagePanel();
        splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT) {
            private final int location = 36;

            {
                setDividerLocation(location);
            }

            @Override
            public int getDividerLocation() {
                return location;
            }

            @Override
            public int getLastDividerLocation() {
                return location;
            }
        };
    }
}
