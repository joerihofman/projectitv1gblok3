package yellowsparkle.view;

import yellowsparkle.parking.model.ParkingSlot;
import yellowsparkle.view.types.ParkingSlotCollectionAcceptor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

public class ImagePanel extends ViewPanel implements ParkingSlotCollectionAcceptor {
    private BufferedImage parkImage;
    private BufferedImage carImage;
    private Collection<ParkingSlot> parkingSlotCollection;

    @SuppressWarnings("ConstantConditions")
    public ImagePanel() {
        try {
            parkImage = ImageIO.read(this.getClass().getClassLoader().getResource("park.png"));
            carImage = ImageIO.read(this.getClass().getClassLoader().getResource("car.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
            // handle exception...
        }
    }

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

    @Override
    public void setParkingSlotCollection(Collection<ParkingSlot> parkingSlotCollection) {
        this.parkingSlotCollection = parkingSlotCollection;
    }
}
