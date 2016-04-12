package yellowsparkle.view;

import javax.swing.*;

/**
 * Refreshes the view each tick.
 * Has something to do with the color
 */
public abstract class ViewPanel extends JPanel implements View {
    public void tick() {
        this.repaint();
    }
}
