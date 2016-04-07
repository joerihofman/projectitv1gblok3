package yellowsparkle.Piechart;

import yellowsparkle.Main;

import javax.swing.*;

@SuppressWarnings("serial")
public class View extends JPanel {
    private Model model;
    int totalparts = (360/ Main.simulator.getGarage().getTotalSpaces());

    public View(Model model) {
        this.model=model;
        model.addView(this);
        setSize(200, 200);
        setVisible(true);
    }

    public void setModel(Model model) {
        this.model=model;
    }

    public Model getModel() {
        return model;
    }

    public void updateView() {
        repaint();
    }
}