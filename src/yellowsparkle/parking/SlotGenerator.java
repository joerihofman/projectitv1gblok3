package yellowsparkle.parking;

import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.parking.model.Position;

import java.util.ArrayList;
import java.util.List;

public class SlotGenerator {
    public static List<ParkingSlot> genericRectangular(String building, int rows, int places) {
        assert rows > 0 && places > 0;
        List<ParkingSlot> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < places; j++) {
                list.add(new ParkingSlot(new Position(building, 1, i, j, roundUp(i * 35, 70) - 27 * (i%2), j * 25)));
            }
        }

        return list;
    }

    private static int roundUp(int i, int space) {
        return ((i + space-1) /space) * space;
    }
}
