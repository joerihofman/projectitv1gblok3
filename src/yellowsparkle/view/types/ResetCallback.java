package yellowsparkle.view.types;

import java.util.function.Consumer;

/**
 * Marker for the views that are using the reset tick callback method
 */
public interface ResetCallback {
    void setResetCallback(Consumer<Void> resetCallback);
}
