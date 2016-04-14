package yellowsparkle.gui.types.controller;

import java.util.function.Consumer;

/**
 * Marker for the views that are using the tick callback method
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface TickCallback {
    /**
     * Setter for callback
     * @param tickCallback Callback to be used
     */
    void setTickCallback(Consumer<Integer> tickCallback);
}
