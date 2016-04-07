package yellowsparkle.parking.model;

import java.util.Date;

public class Ticket {

    private TicketType type;
    private Date start;
    private Date exit;

    public Ticket(TicketType type) {
        this(type, new Date(0));
    }

    /**
     * Creates a ticket with the type and a start date
     * @param type  type of the ticket
     * @param start StartDate of the ticket
     */
    public Ticket(TicketType type, Date start) {
        this(type, start, new Date(0));
    }

    /**
     * A check if the ticket is valid
     * @param type Ticket type
     * @param start Start date of the ticket
     * @param exit Exit date of the ticket
     */
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

    /**
     * This calculates if the date on the ticket is still valid
     * @param date Date of the subscription / ticket
     * @return the isValid of the ticket
     */
    public boolean isValid(Date date) {
        return start.before(date) && exit.after(date);
    }


    /**
    * Gets the ticket type
    * @return ticket type
    */
    public TicketType getType() {
        return type;
    }

    public Date getStart() {
        return start;
    }

    /**
     * Sets the start date of the ticket
     * @param start Start date of the ticket
     */
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

        /**
        * this makes a random ticket from the TicketType.
        */
        public static TicketType getRandomTicket() {
            return values()[(int) (Math.random() * values().length)];
        }

    }
}
