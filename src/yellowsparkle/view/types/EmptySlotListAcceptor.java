package yellowsparkle.view.types;

import yellowsparkle.parking.model.ParkingSlot;

import java.util.List;

/**
 * Marker for the views that are using the empty parking spaces list
 */
public interface EmptySlotListAcceptor {
    void setEmptySlotList(List<ParkingSlot> emptySlotList);
}
