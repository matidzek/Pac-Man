package Events;

import java.util.EventObject;

public class ZmianaCzasuEvent extends EventObject {
    private int czas;
    public ZmianaCzasuEvent(Object source, int czas) {
        super(source);
        this.czas = czas;
    }
    public int getCzas() {
        return czas;
    }
}
