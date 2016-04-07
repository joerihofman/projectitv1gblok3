package yellowsparkle.Piechart;

import yellowsparkle.Globals;

import java.awt.*;

@SuppressWarnings("serial")
public class PieView extends View {

    public PieView(Model model) {
        super(model);
    }

    public void paintComponent(Graphics g) {
        int num =(getModel().getNum()*totalparts);
        int total = Globals.simulator.getGarage().getTotalSpaces().size();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.BLUE);

        g.fillArc(10, 10, 180, 180, 0, num);
    }
}
