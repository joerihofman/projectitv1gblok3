package yellowsparkle.view;

import javax.swing.*;

public abstract class ViewPanel extends JPanel implements View {
    public void tick() {
        this.repaint();
    }
}
