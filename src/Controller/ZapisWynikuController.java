package Controller;

import Events.OdczytWynikuEvent;
import Events.OdczytWynikuListener;
import Events.ZapisWynikuEvent;
import Events.ZapisWynikuListener;

import java.util.ArrayList;
import java.util.List;

public class ZapisWynikuController implements ZapisWynikuListener{
    private List<ZapisWynikuListener> zapisWynikuListeners = new ArrayList<>();
    private List<OdczytWynikuListener> OdczytWynikuListeners = new ArrayList<>();

    public void onZapisWyniku(ZapisWynikuEvent e) {
        for(ZapisWynikuListener l : zapisWynikuListeners) {
            l.onZapisWyniku(e);
        }
    }


    public void addZapisWynkuListener(ZapisWynikuListener l) {
        zapisWynikuListeners.add(l);
    }
    public void removeZapisWynkuListener(ZapisWynikuListener l) {
        zapisWynikuListeners.remove(l);
    }

}
