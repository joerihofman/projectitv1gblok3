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
    private BufferedImage parkImage;
    private BufferedImage carImage;
    private Collection<ParkingSlot> parkingSlotCollection;

    /**
     * Constructor for ImagePanel
     */
    @SuppressWarnings("ConstantConditions")
    public ImagePanel() {
        try {
            parkImage = ImageIO.read(this.getClass().getClassLoader().getResource("park.png"));
            carImage = ImageIO.read(this.getClass().getClassLoader().getResource("car.png"));
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
                g.drawImage(carImage, parkingSlot.getPosition().getRenderX(), parkingSlot.getPosition().getRenderY(), null);
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