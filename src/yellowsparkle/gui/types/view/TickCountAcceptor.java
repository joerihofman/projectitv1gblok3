package yellowsparkle.gui.types.view;

/**
 * Marker for the views that are using the tick count
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface TickCountAcceptor {
    /**
     * Acceptor for current tick count
     * @param tickCount Ticks that have passed since the last reset
     */
    void setTickCount(int tickCount);
}
