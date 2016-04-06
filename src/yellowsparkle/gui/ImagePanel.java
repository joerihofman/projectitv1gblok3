package yellowsparkle.gui;

import yellowsparkle.parking.model.Car;
import yellowsparkle.parking.model.ParkingSlot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImagePanel extends JPanel {
    private Collection<ParkingSlot> parkingSlotList;
    private List<Car> carList;
    private BufferedImage parkImage;
    private BufferedImage carImage;

    @SuppressWarnings("ConstantConditions")
    public ImagePanel() {
        parkingSlotList = new ArrayList<>();
        carList = new ArrayList<>();
        try {
            parkImage = ImageIO.read(this.getClass().getClassLoader().getResource("park.png"));
            carImage = ImageIO.read(this.getClass().getClassLoader().getResource("car.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
            // handle exception...
        }
    }

    public void update(Collection<ParkingSlot> parkingSlotList, List<Car> carList) {
        this.parkingSlotList = parkingSlotList;
        this.carList = carList;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        parkingSlotList.forEach(parkingSlot -> {
            g.drawImage(parkImage, parkingSlot.getPosition().getRenderX(), parkingSlot.getPosition().getRenderY(), null);
            if (!parkingSlot.isEmpty()) {
                g.drawImage(carImage, parkingSlot.getPosition().getRenderX(), parkingSlot.getPosition().getRenderY(), null);
            }
        });
    }
}
