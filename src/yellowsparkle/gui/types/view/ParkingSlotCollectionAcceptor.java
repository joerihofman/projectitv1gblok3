package yellowsparkle.gui.types.view;

import yellowsparkle.parking.model.ParkingSlot;

import java.util.Collection;

/**
 * Marker for views that use all parking slots
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface ParkingSlotCollectionAcceptor {
    void setParkingSlotCollection(Collection<ParkingSlot> parkingSlotCollection);
}
