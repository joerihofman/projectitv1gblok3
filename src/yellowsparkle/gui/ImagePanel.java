package yellowsparkle.gui;

import yellowsparkle.parking.Car;
import yellowsparkle.parking.Location;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImagePanel extends JPanel {
    private List<Location> locationList;
    private List<Car> carList;
    private BufferedImage parkImage;
    private BufferedImage carImage;

    @SuppressWarnings("ConstantConditions")
    public ImagePanel() {
        locationList = new ArrayList<>();
        carList = new ArrayList<>();
        try {
            parkImage = ImageIO.read(this.getClass().getClassLoader().getResource("park.png"));
            carImage = ImageIO.read(this.getClass().getClassLoader().getResource("car.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
            // handle exception...
        }
    }

    public void update(List<Location> locationList, List<Car> carList) {
        this.locationList = locationList;
        this.carList = carList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        locationList.forEach(location -> g.drawImage(parkImage, location.getRow()*30, location.getPlace()*30 + location.getFloor() * 100, null));
        carList.forEach(car -> g.drawImage(carImage, car.getLocation().getRow()*30, car.getLocation().getPlace()*30 + car.getLocation().getFloor() * 100, null));
    }
}
