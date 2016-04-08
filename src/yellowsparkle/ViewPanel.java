package yellowsparkle;

import yellowsparkle.view.View;

import javax.swing.*;

public abstract class ViewPanel extends JPanel implements View {
    public void tick() {
        this.repaint();
    }
}
