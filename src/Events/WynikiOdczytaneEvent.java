package Events;

import Model.PlayersData;

import java.util.ArrayList;
import java.util.EventObject;

public class WynikiOdczytaneEvent extends EventObject {
    private final ArrayList<PlayersData> wyniki;

    public WynikiOdczytaneEvent(Object source, ArrayList<PlayersData> wyniki) {
        super(source);
        this.wyniki = wyniki;
    }

    public ArrayList<PlayersData> getWyniki() {
        return wyniki;
    }
}
