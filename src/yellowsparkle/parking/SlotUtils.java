package yellowsparkle.parking;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import yellowsparkle.parking.model.Garage;
import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.parking.model.Position;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;


public class SlotUtils {
    private static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder dBuilder;

    static {
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


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

    public static Document toXML(List<ParkingSlot> parkingSlots) throws ParserConfigurationException {
        Document doc = dBuilder.newDocument();
        Element garage = doc.createElement("garage");
        doc.appendChild(garage);
        for (ParkingSlot slot : parkingSlots) {
            Element slotElement = doc.createElement("parkingSlot");
            slotElement.setAttribute("building", slot.getPosition().getBuilding());
            slotElement.setAttribute("floor", String.valueOf(slot.getPosition().getFloor()));
            slotElement.setAttribute("row", String.valueOf(slot.getPosition().getRow()));
            slotElement.setAttribute("place", String.valueOf(slot.getPosition().getPlace()));
            slotElement.setAttribute("renderX", String.valueOf(slot.getPosition().getRenderX()));
            slotElement.setAttribute("renderY", String.valueOf(slot.getPosition().getRenderY()));
            garage.appendChild(slotElement);
        }
        return doc;
    }

    public static Document toXML(Garage garage) {
        Document doc = dBuilder.newDocument();

        return doc;
    }

    private static int roundUp(int i, int space) {
        return ((i + space-1) /space) * space;
    }
}
