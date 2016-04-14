package yellowsparkle.gui.types.controller;

import java.util.function.Consumer;

/**
 * Marker for the views that are using the reset tick callback method
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface ResetCallback {
    /**
     * Setter for callback
     * @param resetCallback Callback to be used
     */
    void setResetCallback(Consumer<Void> resetCallback);
}
