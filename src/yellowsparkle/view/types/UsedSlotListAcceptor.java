package yellowsparkle.view.types;

import yellowsparkle.parking.model.ParkingSlot;

import java.util.List;

public interface UsedSlotListAcceptor {
    void setUsedSlotList(List<ParkingSlot> usedSlotList);
}
