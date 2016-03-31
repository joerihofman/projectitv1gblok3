package yellowsparkle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MSI on 31-3-2016.
 */
public class GUI {
    private JPanel panel1;
    private JButton buttonReset;
    private JButton buttonHundredsteps;
    private JButton buttonOnestep;
    private JLabel labelQueue;
    private JButton buttonPause;

    boolean pause = false;


    public GUI() {

        //Pause button
        buttonPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pause != true)
                {
                    pause = true;
                    System.out.println("its currently paused ");
                }

                else
                {
                    pause = false;
                    System.out.println("its currently running ");
                }

            }
        });

        //Reset button
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //One step button
        buttonOnestep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //100 steps button
        buttonHundredsteps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
