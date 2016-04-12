package yellowsparkle.view;

import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.view.types.ParkingSlotCollectionAcceptor;
import yellowsparkle.view.types.UsedSlotListAcceptor;

import java.awt.*;
import java.util.Collection;
import java.util.List;


public class PieChart extends ViewPanel implements ParkingSlotCollectionAcceptor, UsedSlotListAcceptor {
    private Collection<ParkingSlot> parkingSlotCollection;
    private List<ParkingSlot> usedSpaces;


    /**
     *
     * @param g is used to set the color of the pie chart
     */
    @Override
    public void paintComponent(Graphics g) {
        if (parkingSlotCollection != null) {
            int num = (int) (((double) usedSpaces.size() / (double) parkingSlotCollection.size()) * 360);
            super.paintComponent(g);
            g.setColor(Color.YELLOW);
            g.fillRect(0, 0, 200, 200);
            g.setColor(Color.BLUE);
            g.fillArc(10, 10, 180, 180, 0, num);
        }
    }

    /**
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
