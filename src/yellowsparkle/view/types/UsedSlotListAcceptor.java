package yellowsparkle.view.types;

import yellowsparkle.parking.model.ParkingSlot;

import java.util.List;

/**
 * Marker for the views that are using the used parking spaces method
 */
public interface UsedSlotListAcceptor {
    void setUsedSlotList(List<ParkingSlot> usedSlotList);
}
