package yellowsparkle.gui;

import yellowsparkle.parking.model.Car;
import yellowsparkle.parking.model.ParkingSlot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImagePanel extends JPanel {
    private List<ParkingSlot> parkingSlotList;
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

    public void update(List<ParkingSlot> parkingSlotList, List<Car> carList) {
        this.parkingSlotList = parkingSlotList;
        this.carList = carList;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        parkingSlotList.forEach(location -> g.drawImage(parkImage, location.getRow()*30, location.getPlace()*30 + location.getFloor() * 100, null));
        carList.forEach(car -> {
            g.drawImage(carImage, car.getParkingSlot().getRow() * 30, car.getParkingSlot().getPlace() * 30 + car.getParkingSlot().getFloor() * 100, null);
            g.drawChars(String.valueOf(car.getLifespan()).toCharArray(), 0, 1, car.getParkingSlot().getRow() * 30, car.getParkingSlot().getPlace() * 30 + car.getParkingSlot().getFloor() * 100 + 12);
        });
    }
}
