package yellowsparkle.parking.model;

import java.util.Date;

public class Ticket {

    private TicketType type;
    private Date start;
    private Date exit;

    public Ticket(TicketType type) {
        this(type, new Date(0));
    }

    public Ticket(TicketType type, Date start) {
        this(type, start, new Date(0));
    }

    public Ticket(TicketType type, Date start, Date exit){
        this.type = type;
        if (type != TicketType.REGULAR) {
            this.start = start;
            this.exit = exit;
            if (start == null || exit == null) {
                throw new IllegalArgumentException("Invalid date");
            }
        }
    }

    public boolean isValid(Date date) {
        return start.before(date) && exit.after(date);
    }

    public TicketType getType() {
        return type;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getExit() {
        return exit;
    }

    public void setExit(Date exit) {
        this.exit = exit;
    }


    public enum TicketType{
        /**
         * Regular parking ticket; Pay on exit, no guaranteed spot.
         */
        REGULAR,
        /**
         * Reserved parking spot; Paid in advance, one guaranteed spot of unspecified location.
         */
        RESERVATION,
        /**
         * Subscription parking ticket; Paid in advance, no guaranteed spot.
         */
        SUBSCRIPTION,
        /**
         * Corporate parking parking ticket; payment by corporation, block of spaces rented out to a corporation.
         */
        CORPORATE_PARKING;

        //Picks a random ticket type of the enum above.
        public static TicketType getRandomTicket() {
            //returns this.
            return values()[(int) (Math.random() * values().length)];
        }

    }
}
