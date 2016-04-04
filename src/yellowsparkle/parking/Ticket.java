package yellowsparkle.parking;

/**
 * Created by MSI on 4-4-2016.
 */
public class Ticket {

    private TicketType type;

    public Ticket(TicketType type){
        this.type = type;
    }



    public enum TicketType{
        /**
         * Regular parking ticket; pay on exit
         */
        REGULAR,
        /**
         * Subscription parking ticket; no need to pay
         */
        SUBSCRIPTION,
        /**
         * Corporate_parking parking ticket; payment by company, multiple places
         */
        CORPORATE_PARKING;
    }
}
