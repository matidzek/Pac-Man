package Events;

import Model.MapModel;

import java.util.EventObject;

public class ZmianaDanychEvent extends EventObject {
    private int x;
    private int y;
    public ZmianaDanychEvent(Object source, int x, int y) {
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
