package yellowsparkle.view.types;

import java.util.function.Consumer;

public interface ResetCallback {
    void setResetCallback(Consumer<Void> resetCallback);
}
