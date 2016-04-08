package yellowsparkle.Piechart;

import yellowsparkle.Globals;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TestForGUI extends JPanel /*implements Runnable*/ {
    private int num;
    private boolean run;
    private int total;
    private List<View> views;

    public TestForGUI() {
        List<View> views = new ArrayList<>();
        views.forEach(View::repaint);
        new Thread().start();
    }

    public void updatePie(List<View> views){
        this.views = views;
        repaint();
    }

    public int getNum() {
        //return num;
        return Globals.simulator.getGarage().getUsedSpaces().size();
    }

    @Override
    public void paintComponent(Graphics g) {
        int num = (getNum() * total);
        int total = Globals.simulator.getGarage().getTotalSpaces().size();

        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.BLUE);

        g.fillArc(10, 10, 180, 180, 0, num);
    }

/*******
 *  public int getNum() {
        //return num;
        return Globals.simulator.getGarage().getUsedSpaces().size();
    }

    public void setNum(int num) {
        if (num >= 0 && num <= 360) {
            this.num = num;
            notifyViews();
        }
    }

    public void start() {
        new Thread(this).start();
    }

    public void stop() {
        run = false;
    }

    public void addView(View view) {
        views.add(view);
    }

    private void notifyViews() {
        views.forEach(View::updateView);
    }

    @Override
    public void run() {
        run = true;
        while (run) {
            setNum(getNum() + 1);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }
 **********/
}