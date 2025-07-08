package Events;
import java.util.EventObject;

public class PauzaEvent extends EventObject {
    private boolean pauza;

    public PauzaEvent(Object source, boolean pauza) {
        super(source);
        this.pauza = pauza;
    }

    public boolean getPauza() {
        return pauza;
    }
}
