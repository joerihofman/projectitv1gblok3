package yellowsparkle.gui.types.view;

import yellowsparkle.parking.model.ParkingSlot;

import java.util.List;

/**
 * Marker for the views that are using the used parking spaces method
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface UsedSlotListAcceptor {
    /**
     * Acceptor for used slots in a garage
     * @param usedSlotList List of used slots in a garage
     */
    void setUsedSlotList(List<ParkingSlot> usedSlotList);
}
