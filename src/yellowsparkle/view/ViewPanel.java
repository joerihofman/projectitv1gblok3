package yellowsparkle.view;

import javax.swing.*;

/**
 * Refreshes the view panel for each tick with the repaint method.
 */
public abstract class ViewPanel extends JPanel implements View {
    public void tick() {
        this.repaint();
    }
}
