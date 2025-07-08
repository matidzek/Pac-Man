package Model;

import java.io.Serializable;

public class PlayersData  implements Serializable {
    private String playerName;
    private int playerScore;

    public PlayersData(String playerName, int playerScore) {
        super();
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

}
