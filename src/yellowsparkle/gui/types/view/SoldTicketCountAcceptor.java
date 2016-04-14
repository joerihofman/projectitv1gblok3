package yellowsparkle.gui.types.view;

/**
 * Marker for the views that are using the sold ticket count method
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface SoldTicketCountAcceptor {
    /**
     * Acceptor for sold tickets
     * @param ticketCount Amount of tickets current sold
     */
    void setSoldTicketCount(int ticketCount);
}
