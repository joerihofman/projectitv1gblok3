package yellowsparkle.gui.types.controller;

import yellowsparkle.parking.model.Garage;

import java.util.function.Consumer;

/**
 * Marker for the views that are using the set garage callback method
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface SetGarageCallback {
    void setGarageCallback(Consumer<Garage> garageCallback);
}
