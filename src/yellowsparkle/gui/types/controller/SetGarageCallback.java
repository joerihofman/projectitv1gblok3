package yellowsparkle.gui.types.controller;

import yellowsparkle.parking.model.Garage;

import java.util.function.Consumer;

/**
 * Marker for the views that are using the set garage callback method
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface SetGarageCallback {
    /**
     * Setter for callback
     * @param garageCallback Callback to be used
     */
    void setGarageCallback(Consumer<Garage> garageCallback);
}
