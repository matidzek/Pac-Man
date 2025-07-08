package Events;

import java.util.EventObject;

public class ZapisWynikuEvent extends EventObject {
    private String nick;
    private int score;

    public ZapisWynikuEvent(Object source, String nick, int score) {
        super(source);
        this.nick = nick;
        this.score = score;
    }

    public String getNick() {
        return nick;
    }

    public int getScore() {
        return score;
    }
}
