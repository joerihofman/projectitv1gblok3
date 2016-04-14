package yellowsparkle.parking.model;

import java.util.Date;

/**
 * Class to model parking tickets
 * @author ITV1G Group 1
 * @version 1.0
 */
public class Ticket {

    private TicketType type;
    private Date start;
    private Date exit;
    private String corporation;

    public Ticket(TicketType type) {
        this(type, new Date(0), new Date(0));
    }

    /**
     * Creates a ticket with the type and a start date  
     * @param type  type of the ticket
     * @param start StartDate of the ticket 
     */
    public Ticket(TicketType type, Date start) {
        this(type, start, (String) null);
    }

    /**
     * Creates a ticket with the type and a start date
     * @param type  type of the ticket
     * @param start StartDate of the ticket
     * @param corporation Corporation for corporate tickets.
     */
    public Ticket(TicketType type, Date start, String corporation) {
        this(type, start, new Date(0));
    }

    /**
     * A check if the ticket is valid
     * @param type Ticket type
     * @param start Start date of the ticket
     * @param end End date of the ticket
     */
    public Ticket(TicketType type, Date start, Date end) {
        this(type, start, end, null);
    }

    /**
     * A check if the ticket is valid
     * @param type Ticket type
     * @param start Start date of the ticket
     * @param end End date of the ticket
     * @param corporation Corporation for corporate tickets.
     */
    public Ticket(TicketType type, Date start, Date end, String corporation){
        this.type = type;
        if (type != TicketType.REGULAR) {
            this.start = start;
            this.exit = end;
            if (start == null || end == null) {
                throw new IllegalArgumentException("Invalid date");
            }
            if (type == TicketType.CORPORATE_PARKING) {
                if (corporation == null) {
                    throw new IllegalArgumentException("Corporate ticket without corporation!");
                }
                this.corporation = corporation;
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

    public String getCorporation() {
        return corporation;
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