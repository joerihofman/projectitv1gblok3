package yellowsparkle.view.types;

import yellowsparkle.parking.model.ParkingSlot;

import java.util.Collection;

/**
 * Marker for the views that are using the Parking slot collection
 */
public interface ParkingSlotCollectionAcceptor {
    void setParkingSlotCollection(Collection<ParkingSlot> parkingSlotCollection);
}
