package yellowsparkle.Piechart;

/**
 * Created by MSI on 5-4-2016.
 *
 * Model for the piechart
 */
import yellowsparkle.Main;

import java.util.*;

public class Model implements Runnable {
    private int aantal;
    private List<View> views;
    private boolean run;

    public Model() {
        views=new ArrayList<View>();
        for(View v: views) v.updateView();
        new Thread(this).start();
    }

    public int getAantal() {
        return Main.simulator.usedParkingSpaces();
    }

    public void setAantal(int aantal) {
        if (aantal>=0 && aantal <=360) {
            this.aantal=aantal;
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
        for(View v: views) v.updateView();
    }

    @Override
    public void run() {
        run=true;
        while(run) {
            setAantal(getAantal()+1);
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
    }
}