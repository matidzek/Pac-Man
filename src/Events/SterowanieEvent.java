package Events;

import java.util.EventObject;

public class SterowanieEvent extends EventObject {
    private int x;
    private int y;

    public SterowanieEvent(Object source, int x, int y) {
        super(source);
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
