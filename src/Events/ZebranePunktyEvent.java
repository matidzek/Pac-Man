package Events;

import java.util.EventObject;

public class ZebranePunktyEvent extends EventObject {
    private int punkty;
    public ZebranePunktyEvent(Object source, int punkty) {
        super(source);
        this.punkty = punkty;
    }
    public int getPunkty() {
        return punkty;
    }

}
