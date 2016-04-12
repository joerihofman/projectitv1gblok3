package yellowsparkle.view.types;

import java.util.function.Consumer;

/**
 * Marker for the views that are using the tick callback method
 */
public interface TickCallback {
    void setTickCallback(Consumer<Integer> tickCallback);
}
