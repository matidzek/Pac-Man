package Controller;

import Events.*;
import Model.PlayersData;

import java.util.ArrayList;
import java.util.List;

public class OdczytWynikuController implements OdczytWynikuListener, WynikiOdczytaneListener{
    private List<OdczytWynikuListener> OdczytWynikuListeners = new ArrayList<>();
    private List<ZapisWynikuListener> ZapisWynikuListeners = new ArrayList<>();
    private List<WynikiOdczytaneListener> WynikiOdczytaneListeners = new ArrayList<>();
    @Override
    public void onOdczytWyniku(OdczytWynikuEvent e) {
        for(OdczytWynikuListener l : OdczytWynikuListeners) {
            l.onOdczytWyniku(e);
        }
    }

    @Override
    public void onWynikiOdczytane(WynikiOdczytaneEvent e) {
        for(WynikiOdczytaneListener l : WynikiOdczytaneListeners) {
            l.onWynikiOdczytane(e);
        }
    }

    public void addOdczytWynikuListener(OdczytWynikuListener l) {
        OdczytWynikuListeners.add(l);
    }
    public void removeOdczytWynikuListener(OdczytWynikuListener l) {
        OdczytWynikuListeners.remove(l);
    }
    public void addWynikiOdczytaneListener(WynikiOdczytaneListener l) {
        WynikiOdczytaneListeners.add(l);
    }
    public void removeWynikiOdczytaneListener(WynikiOdczytaneListener l) {
        WynikiOdczytaneListeners.remove(l);
    }



}
