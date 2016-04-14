package yellowsparkle.gui.types.view;

/**
 * Marker for the views that are using the entry queue length
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface EntryQueueLengthAcceptor {
    /**
     * Acceptor for current entry queue length
     * @param queueLength Entry queue length
     */
    void setEntryQueueLength(int queueLength);
}
