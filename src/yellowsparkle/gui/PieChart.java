/**
 * @author ITV1G Group 1
 * @version 1.0
 * @since 8/4/16
 */

package yellowsparkle.gui;

import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.gui.types.view.ParkingSlotCollectionAcceptor;
import yellowsparkle.gui.types.view.UsedSlotListAcceptor;

import java.awt.*;
import java.util.Collection;
import java.util.List;

/**
 * This class is for the pie chart in the gui. So you can see in a pie chart the amount of places occupied
 * @author ITV1G Group 1
 * @version 1.0
 */
public class PieChart extends ViewPanel implements ParkingSlotCollectionAcceptor, UsedSlotListAcceptor {
    private Collection<ParkingSlot> parkingSlotCollection;
    private List<ParkingSlot> usedSpaces;


    /**
     * This method creates the piechart for the GUI.
     * @param g is used to set the color of the pie chart
     */
    @Override
    public void paintComponent(Graphics g) {
        if (parkingSlotCollection != null) {
            int num = (int) (((double) usedSpaces.size() / (double) parkingSlotCollection.size()) * 360);
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 154, 154);
            g.setColor(Color.BLUE);

            g.fillArc(2, 2, 150, 150, 0, num);
        }
    }

    /**
     * @param usedSlotList this gives a list with all the used parking spots
     */
    @Override
    public void setUsedSlotList(List<ParkingSlot> usedSlotList) {
        this.usedSpaces = usedSlotList;
    }

    /**
     *
     * @param parkingSlotCollection is a collection for all the parking slots
     *
     */
    @Override
    public void setParkingSlotCollection(Collection<ParkingSlot> parkingSlotCollection) {
        this.parkingSlotCollection = parkingSlotCollection;
    }
}
