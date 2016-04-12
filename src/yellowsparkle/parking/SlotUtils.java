package yellowsparkle.parking;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import yellowsparkle.parking.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


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

    private static Element slotToElement(Document doc, ParkingSlot slot) {
        Element slotElement = doc.createElement("parkingSlot");
        slotElement.setAttribute("building", slot.getPosition().getBuilding());
        slotElement.setAttribute("floor", String.valueOf(slot.getPosition().getFloor()));
        slotElement.setAttribute("row", String.valueOf(slot.getPosition().getRow()));
        slotElement.setAttribute("place", String.valueOf(slot.getPosition().getPlace()));
        slotElement.setAttribute("renderX", String.valueOf(slot.getPosition().getRenderX()));
        slotElement.setAttribute("renderY", String.valueOf(slot.getPosition().getRenderY()));
        return slotElement;
    }

    private static Element garageToElement(Document doc, Garage garage) {
        Element root = doc.createElement("garage");
        garage.forEach(parkingSlot -> root.appendChild(slotToElement(doc, parkingSlot)));
        garage.forEachGarage(g -> root.appendChild(garageToElement(doc, g)));
        return root;
    }

    public static Document toXML(List<ParkingSlot> parkingSlots) throws ParserConfigurationException {
        Document doc = dBuilder.newDocument();
        Element garage = doc.createElement("garage");
        doc.appendChild(garage);
        parkingSlots.forEach(parkingSlot -> garage.appendChild(slotToElement(doc, parkingSlot)));
        return doc;
    }

    public static Document toXML(Garage garage) {
        Document doc = dBuilder.newDocument();
        doc.appendChild(garageToElement(doc, garage));
        return doc;
    }

    public static Garage garageFromXML(String xml, Predicate<Car> carPredicate) throws IOException, SAXException {
        Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        return parseGarageTags(doc.getElementsByTagName("garage").item(0), carPredicate);
    }

    private static Garage parseGarageTags(Node node, Predicate<Car> carPredicate) {
        List<ParkingSlot> slots = new ArrayList<>();
        parseSlotTags(slots, node.getChildNodes(), false);
        Garage garage = new GarageImpl(slots, carPredicate);
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (Objects.equals(child.getNodeName(), "garage")) {
                garage.addGarage(parseGarageTags(child, carPredicate));
            }
        }
        return garage;
    }

    public static List<ParkingSlot> slotsFromXML(String xml) throws IOException, SAXException {
        Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        List<ParkingSlot> slots = new ArrayList<>();
        parseSlotTags(slots, doc.getElementsByTagName("garage"), true);
        return slots;
    }

    private static void parseSlotTags(List<ParkingSlot> slots, NodeList nodes, boolean recurse) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node != null) {
                if (Objects.equals(node.getNodeName(), "parkingSlot")) {
                    NamedNodeMap attributes = node.getAttributes();
                    slots.add(new ParkingSlot(new Position(attributes.getNamedItem("building").getTextContent(),
                            Integer.valueOf(attributes.getNamedItem("floor").getTextContent()),
                            Integer.valueOf(attributes.getNamedItem("row").getTextContent()),
                            Integer.valueOf(attributes.getNamedItem("place").getTextContent()),
                            Integer.valueOf(attributes.getNamedItem("renderX").getTextContent()),
                            Integer.valueOf(attributes.getNamedItem("renderY").getTextContent()))));
                } else if (Objects.equals(node.getNodeName(), "garage") && recurse) {
                    parseSlotTags(slots, node.getChildNodes(), true);
                }
            }
        }
    }

    private static int roundUp(int i, int space) {
        return ((i + space-1) /space) * space;
    }
}
