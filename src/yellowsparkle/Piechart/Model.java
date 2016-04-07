package yellowsparkle.Piechart;

import yellowsparkle.Main;

import java.util.ArrayList;
import java.util.List;

public class Model implements Runnable {
    private int num;
    private List<View> views;
    private boolean run;

    public Model() {
        views= new ArrayList<>();
        views.forEach(View::updateView);
        new Thread(this).start();
    }

    public int getNum() {
        //return num;
        return Main.simulator.getGarage().getUsedSpaces();
    }

    public void setNum(int num) {
        if (num>=0 && num <=360) {
            this.num =num;
            notifyViews();
        }
    }

    public void start() {
        new Thread(this).start();
    }

    public void stop() {
        run=false;
    }

    public void addView(View view) {
        views.add(view);
    }

    private void notifyViews() {
        views.forEach(View::updateView);
    }

    @Override
    public void run() {
        run=true;
        while(run) {
            setNum(getNum()+1);
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
    }
}