package yellowsparkle.view;

import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.view.types.ParkingSlotCollectionAcceptor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

/**
 * GUI Component to render parking slots
 */
public class ImagePanel extends ViewPanel implements ParkingSlotCollectionAcceptor {
    private BufferedImage parkImage;
    private BufferedImage carImage;
    private Collection<ParkingSlot> parkingSlotCollection;

    /**
     * This ImagePanel takes care of the images of the parking garage simulation.
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
     * This information is from the xml format
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * @param g This fills in the x and y rendering in the parking spots simulation
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