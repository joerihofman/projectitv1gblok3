package yellowsparkle.gui;

import javax.swing.*;

/**
 * JPanel implementation of View
 * @author ITV1G Group 1
 * @version 1.0
 */
public abstract class ViewPanel extends JPanel implements View {
    /**
     * Repaint this viewPanel every tick
     */
    public void tick() {
        this.repaint();
    }
}
