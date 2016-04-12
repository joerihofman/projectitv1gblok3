package yellowsparkle.view;

import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.view.types.ParkingSlotCollectionAcceptor;
import yellowsparkle.view.types.UsedSlotListAcceptor;

import java.awt.*;
import java.util.Collection;
import java.util.List;


/**
 * This class
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
     * This method
     * @param usedSlotList this gives a list with all the used parking spots
     */
    @Override
    public void setUsedSlotList(List<ParkingSlot> usedSlotList) {
        this.usedSpaces = usedSlotList;
    }

    @Override
    public void setParkingSlotCollection(Collection<ParkingSlot> parkingSlotCollection) {
        this.parkingSlotCollection = parkingSlotCollection;
    }
}
