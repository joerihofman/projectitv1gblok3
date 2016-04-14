package yellowsparkle.gui.types.controller;

import java.util.function.Consumer;

/**
 * Marker for the views that are using spawn callback method
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface SpawnCallback {
    /**
     * Setter for callback
     * @param spawnCallback Callback to be used
     */
    void setSpawnCallback(Consumer<Integer> spawnCallback);
}
