package yellowsparkle.view.types;

/**
 * Marker for the views that are using the entry queue length
 */
public interface EntryQueueLengthAcceptor {
    void setEntryQueueLength(int queueLength);
}
