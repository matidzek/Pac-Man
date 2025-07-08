package Events;

import java.util.EventObject;

public class ZmianaZyciaEvent extends EventObject {
    int zycia;
    public ZmianaZyciaEvent(Object source, int zycia) {
        super(source);
        this.zycia = zycia;
    }
    public int getZycia() {
        return zycia;
    }
}
