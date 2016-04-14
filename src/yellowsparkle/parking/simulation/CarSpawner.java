package yellowsparkle.parking.simulation;

import yellowsparkle.Main;
import yellowsparkle.gui.ImagePanel;
import yellowsparkle.parking.model.Car;
import yellowsparkle.parking.model.Ticket;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * Car spawner class
 */
public class CarSpawner {
    /**
     * Creates a new random car
     * @return Newly created car
     */
    public static Car spawnCar() {
        Ticket ticket = null;
        BufferedImage image = null;
        switch (Main.random.nextInt(6)) {
            case 0:
            case 1:
            case 2:
                ticket = new Ticket(Ticket.TicketType.REGULAR);
                image = ImagePanel.carImage;
                break;
            case 3:
                ticket = new Ticket(Ticket.TicketType.SUBSCRIPTION, new Date(0), new Date(System.currentTimeMillis() + 1000*60*60*24));
                image = ImagePanel.subImage;
                break;
            case 4:
                ticket = new Ticket(Ticket.TicketType.RESERVATION, new Date(0), new Date(System.currentTimeMillis() + 1000*60*60*24));
                image = ImagePanel.subImage;
                break;
            case 5:
                ticket = new Ticket(Ticket.TicketType.CORPORATE_PARKING, new Date(0), new Date(System.currentTimeMillis() + 1000*60*60*24), "AHOLD");
                image = ImagePanel.corpImage;
                break;
        }


        return new Car(status -> {
            if (status == Car.Status.PARK && (Main.random.nextInt(128) < 4)) return Car.Status.EXIT_WAIT;
            else return status;
        }, image, ticket);
    }
}
