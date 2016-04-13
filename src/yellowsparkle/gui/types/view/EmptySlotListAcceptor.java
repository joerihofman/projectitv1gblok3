package yellowsparkle.gui.types.view;

import yellowsparkle.parking.model.ParkingSlot;

import java.util.List;

/**
 * Marker for the views that are using the empty parking spaces list
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface EmptySlotListAcceptor {
    void setEmptySlotList(List<ParkingSlot> emptySlotList);
}
