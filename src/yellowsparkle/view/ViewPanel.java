/**
 * @author ITV1G Group 1
 * @version 1.0
 * @since 12/4/16
 */

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
