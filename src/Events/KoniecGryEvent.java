package Events;

import java.util.EventObject;

public class KoniecGryEvent extends EventObject {
    private boolean koniec;
    private int score;
    public KoniecGryEvent(Object source, boolean koniec, int score) {
        super(source);
        this.koniec = koniec;
        this.score = score;
    }
    public boolean getKoniec() {
        return koniec;
    }
    public int getScore() {
        return score;
    }
}
