package Model;

import Events.*;

import java.util.ArrayList;
import java.util.List;



public class MapModel implements SterowanieListener, PauzaListener{
    public enum Kierunek {
        GORA, DOL, LEWO, PRAWO
    }
    private final List<List<Integer>> data;
    private int pacmanx = 1;
    private int pacmany = 1;
    private boolean isRunning = true;
    private PacMan pacMan;
    private int pacmanxchange;
    private int pacmanychange;
    private final int ms = 18; //ustala prędkosc gry
    private final int ghostsNumber;
    private final int columns;
    private final int rows;
    private int czasGry;
    private final Object timerLock;
    private TimerThread timer;
    private int zycia;
    private int punkty;
    private final long iloscPunktowNaMapie;
    private Kierunek pacmanKierunek = Kierunek.PRAWO;
    private int kataSzczeki = 0;
    private int katbSzczeki = 10;

    private List<ZmianaDanychListener> zmianaDanychlisteners = new ArrayList<>();
    private List<ZebranePunktyListener> zebranePunktyListeners = new ArrayList<>();
    private List<ZmianaCzasuListener> zmianaCzasuListeners = new ArrayList<>();
    private List<ZmianaZyciaListener> zmianaZyciaListeners = new ArrayList<>();
    private List<KoniecGryListener> KoniecGryListeners = new ArrayList<>();
    private List<AnimacjaPacmanaListener> AnimacjaPacmanaListeners = new ArrayList<>();



    private List<Ghost> ghosts = new ArrayList<>();
    private List<Thread> ghostThreads = new ArrayList<>();

    public MapModel(int columns, int rows, int ghostsNumber) {
        this.zycia = 2;
        this.punkty = 0;
        this.timerLock = new Object();
        this.timer = new TimerThread();
        this.czasGry = 0;
        this.ghostsNumber = ghostsNumber;
        this.columns = columns;
        this.rows = rows;
        MazeGenerator mazeGenerator = new MazeGenerator(columns, rows); //stworzenie labiryntu
        this.data = mazeGenerator.getData();
        this.data.get(1).set(1,2);
        this.pacMan = new PacMan();
        this.iloscPunktowNaMapie = mazeGenerator.getIloscKulek();
        startGame();
    }

    private void startGame(){
        addEntities();
    }

    public void addEntities() {
        int duchSpawnX = 0;
        int duchSpawnY = 0;
        for (int i = 0; i < this.ghostsNumber; i++){
            ghosts.add(new Ghost(rows/2 + duchSpawnY++,columns/2 + duchSpawnX++,3+i));
        }
        for (int j = 0; j < this.ghostsNumber - 1; j++){ //tworzę powiązania między wątkami za pomocą notify wait
            ghosts.get(j).setNext(ghosts.get(j+1));
        }
        ghosts.get(this.ghostsNumber-1).setNext(pacMan);
        pacMan.setNext(ghosts.get(0)); //powiązanie pacmana z początkowym duchem, powstaje pętla wywolań
        pacMan.start();
        for (Ghost ghost : ghosts) {
            Thread ghostThread = new Thread(ghost);
            ghostThreads.add(ghostThread);
            ghostThread.start();
        }
        timer.start();
        try {
            Thread.sleep(100); //dla pewnosci, aby pacman zdążyl wejsc do wait, inaczej nie zawsze zawiadomi kolejnych duchów
            synchronized (pacMan) {
                pacMan.notify();
            }
            synchronized (timerLock) {
                timerLock.notify();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void stopGame(){
        this.isRunning = false;
    }


    public void onSterowanie(SterowanieEvent evt) {
        pacmanxchange = evt.getX();
        pacmanychange = evt.getY();
        if (pacmanxchange == 1) pacmanKierunek = Kierunek.DOL;
        else if (pacmanxchange == -1) pacmanKierunek = Kierunek.GORA;
        else if (pacmanychange == 1) pacmanKierunek = Kierunek.PRAWO;
        else if (pacmanychange == -1) pacmanKierunek = Kierunek.LEWO;

    }

    @Override
    public void onPauza(PauzaEvent evt) {
        this.isRunning = false;
    }



    public void fireZmianaCzasu(int czasGry) {
        for (ZmianaCzasuListener scl : this.zmianaCzasuListeners) {
            scl.onZmianaCzasu(new ZmianaCzasuEvent(this,czasGry));
        }
    }
    public void fireZmianaZycia(int zmianaZycia) {
        for(ZmianaZyciaListener scl : this.zmianaZyciaListeners){
            scl.onZmianaZycia(new ZmianaZyciaEvent(this, zmianaZycia));
        }
    }
    public void fireZmianaDanych(int x, int y) {
        for (ZmianaDanychListener scl : this.zmianaDanychlisteners) {
            scl.onZmianaDanych(new ZmianaDanychEvent(this, x, y));
        }
    }
    public void fireZebranePunkty(int punkty) {
        for(ZebranePunktyListener scl : this.zebranePunktyListeners)
            scl.onZebranePunkty(
                    new ZebranePunktyEvent(this, punkty)
            );
    }
    public void fireKoniecGry(boolean state) {
        for (KoniecGryListener scl : this.KoniecGryListeners){
            scl.onKoniecGry(
                    new KoniecGryEvent(this, true, punkty)
            );
        }
    }
    public void fireAnimacjaPacmana(Kierunek kierunek, int katSzczeki) {
        for (AnimacjaPacmanaListener scl : this.AnimacjaPacmanaListeners) {
            scl.onAnimacjaPacmanaListener(
                    new AnimacjaPacmanaEvent(this, kierunek, katSzczeki)
            );
        }
    }

    class PacMan extends Thread {
        private Thread next;

        public void setNext(Thread next) {
            this.next = next;
        }
        @Override
        public void run() {
            try {
                while (isRunning) {
                    synchronized (this) {
                        this.wait();
                    }
                    int oldx = pacmanx;
                    int oldy = pacmany;

                    int nowaPozycjaPacMana;
                    synchronized (data) {
                        nowaPozycjaPacMana = getValueAt(pacmanx + pacmanxchange, pacmany + pacmanychange);
                        if (nowaPozycjaPacMana == -1 || nowaPozycjaPacMana == 0) { //pusta komórka
                            pacmanx += pacmanxchange;
                            pacmany += pacmanychange;
                            data.get(oldx).set(oldy, -1);
                            data.get(pacmanx).set(pacmany, 2);
                            fireZmianaDanych(oldx, oldy);
                            fireZmianaDanych(pacmanx, pacmany);
                            if (nowaPozycjaPacMana == 0) {
                                punkty += 10;
                                fireZebranePunkty(punkty);
                            }
                        }
                    }
                    kataSzczeki += katbSzczeki;
                    if (kataSzczeki >= 45 || kataSzczeki <= 0) {
                        katbSzczeki = -katbSzczeki;
                        kataSzczeki = Math.max(0, Math.min(45, kataSzczeki));
                    }
                    fireAnimacjaPacmana(pacmanKierunek, kataSzczeki);
                    Thread.sleep(ms);
                    synchronized (next){
                        next.notify();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Wątek");
                e.printStackTrace();
            }
        }

    }


    class Ghost extends Thread {
        private int x;
        private int y;
        private int numerDucha;
        private Thread next;
        final int[][] kierunki = {
                {1, 0}, //gora
                {-1, 0}, //dol
                {0, -1}, //lewo
                {0, 1} //prawo
        };
        public void setNext(Thread next) {
            this.next = next;
        }

        Ghost(int x, int y, int numerDucha) {
            this.x = x;
            this.y = y;
            this.numerDucha = numerDucha;
        }
        int[] kierunek = kierunki[(int) (Math.random() * 4)];
        int duchxchange = kierunek[0];
        int duchychange = kierunek[1];

        public void run()  {
            try {
                while (isRunning) {
                    synchronized (this) {
                        this.wait();
                    }
                    int oldx = x;
                    int oldy = y;
                    int newx = x + duchxchange;
                    int newy = y + duchychange;
                    int proby = 0;
                    synchronized (data) {
                        int val = getValueAt(newx, newy);
                        if (val == 2) {
                            zycia--;
                            fireZmianaZycia(zycia);
                            if (zycia == 0 || iloscPunktowNaMapie == punkty % 10) {
                                stopGame();
                                Thread.sleep(2000);
                                fireKoniecGry(true);
                            }
                        }
                        else if (val == 0 || val == -1) {
                            x = newx;
                            y = newy;
                        }
                        else{
                            do {
                                kierunek = kierunki[(int) (Math.random() * 4)];
                                duchxchange = kierunek[0];
                                duchychange = kierunek[1];
                                newx = x + duchxchange;
                                newy = y + duchychange;
                                proby++;
                            } while (getValueAt(newx, newy) != 0 && getValueAt(newx, newy) != -1 && proby < 30);
                        }
                        data.get(oldx).set(oldy, getValueAt(newx, newy));
                        data.get(x).set(y, numerDucha);
                        fireZmianaDanych(oldx, oldy);
                        fireZmianaDanych(x, y);
                    }
                    Thread.sleep(ms);
                    synchronized (next) {
                        next.notify();
                    }

                }
            } catch (InterruptedException e) {

            }
        }
    }
    class TimerThread extends Thread {
        @Override
        public void run() {
            try {
                synchronized (timerLock) { //do rozpoczęcia gry nieaktywny
                    timerLock.wait();
                }
                while (isRunning) {
                    fireZmianaCzasu(czasGry++);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Timer przerwany");
            }
        }
    }

    public List<List<Integer>> getMapa() {
        synchronized (data) {
            return data;
        }
    }
    public int getValueAt(int x, int y) {
        synchronized (data) {
            return data.get(x).get(y);
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
    public void addZmianaDanychListener(ZmianaDanychListener zmianaDanychlistener) {
        this.zmianaDanychlisteners.add(zmianaDanychlistener);
    }
    public void removeZmianaDanychListener(ZmianaDanychListener zmianaDanychlistener) {
        this.zmianaDanychlisteners.remove(zmianaDanychlistener);
    }

    public void addZmianaCzasuListener(ZmianaCzasuListener zmianaCzasuListener) {
        this.zmianaCzasuListeners.add(zmianaCzasuListener);
    }
    public void removeZmianaCzasuListener(ZmianaCzasuListener zmianaCzasuListener) {
        this.zmianaCzasuListeners.remove(zmianaCzasuListener);
    }
    public void addZebranePunktyListener(ZebranePunktyListener zebranePunktyListener) {
        this.zebranePunktyListeners.add(zebranePunktyListener);
    }
    public void removeZebranePunktyListener(ZebranePunktyListener zebranePunktyListener) {
        this.zebranePunktyListeners.remove(zebranePunktyListener);
    }
    public void addZmianaZyciaListener(ZmianaZyciaListener zmianaZyciaListener) {
        this.zmianaZyciaListeners.add(zmianaZyciaListener);
    }
    public void removeZmianaZyciaListener(ZmianaZyciaListener zmianaZyciaListener) {
        this.zmianaZyciaListeners.remove(zmianaZyciaListener);
    }

    public void addKoniecGryListener(KoniecGryListener KoniecGryListener) {
        this.KoniecGryListeners.add(KoniecGryListener);
    }
    public void removeKoniecGryListener(KoniecGryListener KoniecGryListener) {
        this.KoniecGryListeners.remove(KoniecGryListener);
    }
    public void addAnimacjaPacmanaListener(AnimacjaPacmanaListener AnimacjaPacmanaListener) {
        this.AnimacjaPacmanaListeners.add(AnimacjaPacmanaListener);
    }
}



