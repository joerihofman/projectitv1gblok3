package yellowsparkle.view.types;

import java.util.function.Consumer;

public interface TickCallback {
    void setTickCallback(Consumer<Integer> tickCallback);
}
