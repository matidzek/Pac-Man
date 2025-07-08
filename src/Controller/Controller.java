package Controller;

import Events.*;
import Model.ZapisOdczytWyniku;
import View.EndScreen;
import View.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
//controller
public class Controller implements KoniecGryListener, ZmianaDanychListener, ZebranePunktyListener, KeyListener, ZmianaCzasuListener, ZmianaZyciaListener, AnimacjaPacmanaListener{
    private List<SterowanieListener> klawiaturaListeners = new ArrayList<>();
    private List<ZmianaDanychListener> danychListeners = new ArrayList<>();
    private List<ZebranePunktyListener> punktyListeners = new ArrayList<>();
    private List<PauzaListener> pauzaListeners = new ArrayList<>();
    private List<ZmianaCzasuListener> zmianaCzasuListeners = new ArrayList<>();
    private List<ZmianaZyciaListener> zmianaZyciaListeners = new ArrayList<>();
    private List<KoniecGryListener> koniecGryListeners = new ArrayList<>();
    private List<AnimacjaPacmanaListener> AnimacjaPacmanaListeners = new ArrayList<>();

    public void onSterowanie(SterowanieEvent e) {
        for(SterowanieListener l : klawiaturaListeners) {
            l.onSterowanie(e);
        }
    }
    public void onZmianaDanych(ZmianaDanychEvent e) {
        for(ZmianaDanychListener l : danychListeners) {
            l.onZmianaDanych(e);
        }
    }

    @Override
    public void onZebranePunkty(ZebranePunktyEvent evt) {
        for(ZebranePunktyListener l : punktyListeners) {
            l.onZebranePunkty(evt);
        }
    }

    @Override
    public void onAnimacjaPacmanaListener(AnimacjaPacmanaEvent e) {
        for (AnimacjaPacmanaListener l : AnimacjaPacmanaListeners) {
            l.onAnimacjaPacmanaListener(e);
        }
    }

    @Override
    public void onKoniecGry(KoniecGryEvent e) {
        for(KoniecGryListener l : koniecGryListeners) {
            l.onKoniecGry(e);
        }
        ZapisWynikuController scoreController = new ZapisWynikuController();
        ZapisOdczytWyniku zapisOdczytWyniku = new ZapisOdczytWyniku();
        SwingUtilities.invokeLater(() ->
        {
            EndScreen endScreen = new EndScreen(e.getScore()); //wywoluję okienko na koniec gry
            endScreen.addZapisWynikuListener(scoreController);
            scoreController.addZapisWynkuListener(zapisOdczytWyniku);

        });
    }

    @Override
    public void onZmianaCzasu(ZmianaCzasuEvent e) {
        for (ZmianaCzasuListener l : zmianaCzasuListeners) {
            l.onZmianaCzasu(e);
        }
    }
    public void onZmianaZycia(ZmianaZyciaEvent e) {
        for(ZmianaZyciaListener l : zmianaZyciaListeners) {
            l.onZmianaZycia(e);
        }
    }

    public void firePauza(PauzaEvent e) {
        for(PauzaListener l : pauzaListeners) {
            l.onPauza(e);
        }
    }

    boolean ctrl = false;
    boolean shift = false;
    boolean Q = false;
    @Override
    public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
        onSterowanie(new SterowanieEvent(this, -1, 0));
    }
    else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        onSterowanie(new SterowanieEvent(this, +1, 0));
    }
    else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        onSterowanie(new SterowanieEvent(this, 0, -1));
    }
    else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        onSterowanie(new SterowanieEvent(this, 0, +1));
    }
    else if (e.getKeyCode()==KeyEvent.VK_CONTROL)  ctrl = true;
    else if (e.getKeyCode()==KeyEvent.VK_SHIFT)  {
        if (ctrl==true) shift = true;

    }
    else if (e.getKeyCode()==KeyEvent.VK_Q)
        if (ctrl && shift) {
            firePauza(new PauzaEvent(this, false));
            SwingUtilities.invokeLater(MainMenu::new);
        }
    if (ctrl && shift && Q) {
        firePauza(new PauzaEvent(this, false));
        SwingUtilities.invokeLater(MainMenu::new);
        ctrl = false;
        shift = false;
    }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) ctrl = false;
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = false;
        if (e.getKeyCode() == KeyEvent.VK_Q) Q = false;}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void addSterowanieListener(SterowanieListener l) {
        klawiaturaListeners.add(l);
    }
    public void removeSterowanieListener(SterowanieListener l) {
        klawiaturaListeners.remove(l);
    }
    public void addZmianaDanychListener(ZmianaDanychListener l) {
        danychListeners.add(l);
    }
    public void removeZmianaDanychListener(ZmianaDanychListener l) {
        danychListeners.remove(l);
    }
    public void addZebranePunktyListener(ZebranePunktyListener l) {
        punktyListeners.add(l);
    }
    public void removeZebranePunktyListener(ZebranePunktyListener l) {
        punktyListeners.remove(l);
    }
    public void addPauzaListener(PauzaListener l) {
        pauzaListeners.add(l);
    }
    public void removePauzaListener(PauzaListener l) {
        pauzaListeners.remove(l);
    }
    public void addZmianaCzasuListener(ZmianaCzasuListener l) {
        zmianaCzasuListeners.add(l);
    }
    public void removeZmianaCzasuListener(ZmianaCzasuListener l) {
        zmianaCzasuListeners.remove(l);
    }
    public void addZmianaZyciaListener(ZmianaZyciaListener l) {
        zmianaZyciaListeners.add(l);
    }
    public void removeZmianaZyciaListener(ZmianaZyciaListener l) {
        zmianaZyciaListeners.remove(l);
    }

    public void addKoniecGryListener(KoniecGryListener l) {
        koniecGryListeners.add(l);
    }
    public void removeKoniecGryListener(KoniecGryListener l) {
        koniecGryListeners.remove(l);
    }
    public void addAnimacjaPacmanaListener(AnimacjaPacmanaListener l) {
        AnimacjaPacmanaListeners.add(l);
    }


}
