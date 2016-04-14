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
import java.util.concurrent.Exchanger;


/**
 * Utility class for parkingSlot operations
 * @author ITV1G Group 1
 * @version 1.0
 */

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


    /**
     * Generates a rectangular field of parking slots
     * @param building Building name for position
     * @param rows Row size
     * @param places Amount of places per row
     * @return List of parkingSlots generated
     */
    public static List<ParkingSlot> genericRectangular(String building, int rows, int places) {
        return genericRectangular(building, rows, places, 0, 0);
    }

    /**
     * Generates a rectangular field of parking slots
     * With render position offsets
     * @param building Building name for position
     * @param rows Row size
     * @param places Amount of places per row
     * @param offsetX X coordinate offset
     * @param offsetY Y coordinate offset
     * @return List of parkingSlots generated
     */
    public static List<ParkingSlot> genericRectangular(String building, int rows, int places, int offsetX, int offsetY) {
        assert rows > 0 && places > 0;
        List<ParkingSlot> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < places; j++) {
                list.add(new ParkingSlot(new Position(building, 1, i, j, offsetX + (roundUp(i * 35, 70) - 27 * (i%2)), offsetY + (j * 25))));
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
        if (garage.getPredicate().getType() instanceof GaragePredicate.EnumType) {
            root.setAttribute("predicateType", ((GaragePredicate.EnumType) garage.getPredicate().getType()).name());
            root.setAttribute("corporation", garage.getPredicate().getCorporation());
        }
        garage.forEach(parkingSlot -> root.appendChild(slotToElement(doc, parkingSlot)));
        garage.forEachGarage(g -> root.appendChild(garageToElement(doc, g)));
        return root;
    }

    /**
     * Converts list of parkingSlots to an XML garage
     * @param parkingSlots List of parkingSlots to convert
     * @return XML Document that was generated
     * @throws ParserConfigurationException
     */
    public static Document toXML(List<ParkingSlot> parkingSlots) throws ParserConfigurationException {
        Document doc = dBuilder.newDocument();
        Element garage = doc.createElement("garage");
        doc.appendChild(garage);
        parkingSlots.forEach(parkingSlot -> garage.appendChild(slotToElement(doc, parkingSlot)));
        return doc;
    }


    /**
     * Converts garage to an XML garage
     * @param garage garage to convert
     * @return XML Document that was generated
     */
    public static Document toXML(Garage garage) {
        Document doc = dBuilder.newDocument();
        doc.appendChild(garageToElement(doc, garage));
        return doc;
    }

    /**
     * Converts XML to a garage
     * @param xml XML to convert
     * @return Generated garage
     * @throws IOException
     * @throws SAXException
     */
    public static Garage garageFromXML(String xml) throws IOException, SAXException {
        Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        return parseGarageTags(doc.getElementsByTagName("garage").item(0));
    }

    private static Garage parseGarageTags(Node node) {
        List<ParkingSlot> slots = new ArrayList<>();
        parseSlotTags(slots, node.getChildNodes(), false);
        NamedNodeMap attributes = node.getAttributes();
        GaragePredicate garagePredicate;
        try {
            String predicateType = attributes.getNamedItem("predicateType").getTextContent();
            String corporation = attributes.getNamedItem("corporation").getTextContent();
            garagePredicate = new GaragePredicate(GaragePredicate.EnumType.valueOf(predicateType), corporation);
        } catch (NullPointerException e) {
            garagePredicate = new GaragePredicate(GaragePredicate.EnumType.ANY);
        }

        Garage garage = new GarageImpl(slots, garagePredicate);
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (Objects.equals(child.getNodeName(), "garage")) {
                garage.addGarage(parseGarageTags(child));
            }
        }
        return garage;
    }

    /**
     * Converts XML to a ParkingSlot list
     * @param xml XML to convert
     * @return List of parkingslots
     * @throws IOException
     * @throws SAXException
     */
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
