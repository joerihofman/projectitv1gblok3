package yellowsparkle.gui.types.view;

/**
 * Marker for the views that are using the amount of cars entering per tick
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface EntryPerTickAcceptor {
    /**
     * Acceptor for current entries per tick
     * @param i Entries per tick
     */
    void setEntryPerTick(int i);
}
