package yellowsparkle.gui;

import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.gui.types.view.ParkingSlotCollectionAcceptor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

/**
 * GUI Component to render parking slots
 * @author ITV1G Group 1
 * @version 1.0
 */
public class ImagePanel extends ViewPanel implements ParkingSlotCollectionAcceptor {
    public static BufferedImage parkImage = null;
    public static BufferedImage carImage = null;
    public static BufferedImage corpImage = null;
    public static BufferedImage subImage = null;
    private Collection<ParkingSlot> parkingSlotCollection;

    /**
     * Constructor for ImagePanel
     */
    @SuppressWarnings("ConstantConditions")
    public ImagePanel() {
        try {
            parkImage = ImageIO.read(this.getClass().getClassLoader().getResource("park.png"));
            carImage = ImageIO.read(this.getClass().getClassLoader().getResource("car.png"));
            corpImage = ImageIO.read(this.getClass().getClassLoader().getResource("car_corp.png"));
            subImage = ImageIO.read(this.getClass().getClassLoader().getResource("car_sub.png"));
        } catch (IOException ex) {
            ex.printStackTrace(); /** handle exception... */
        }
    }

    /**
     * Renders the imagePanel
     * @param g Graphics to render to
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        parkingSlotCollection.forEach(parkingSlot -> {
            g.drawImage(parkImage, parkingSlot.getPosition().getRenderX(), parkingSlot.getPosition().getRenderY(), null);
            if (!parkingSlot.isEmpty()) {
                g.drawImage(parkingSlot.getCar().getImage(), parkingSlot.getPosition().getRenderX(), parkingSlot.getPosition().getRenderY(), null);
                char[] chars = String.valueOf(parkingSlot.getCar().getLifespan()).toCharArray();
                g.drawChars(chars, 0, chars.length, parkingSlot.getPosition().getRenderX(), parkingSlot.getPosition().getRenderY()+12);
            }
        });
    }

    /**
     * Hook for parking slot collection
     * @param parkingSlotCollection new parking slot collection
     */
    @Override
    public void setParkingSlotCollection(Collection<ParkingSlot> parkingSlotCollection) {
        this.parkingSlotCollection = parkingSlotCollection;
    }
}