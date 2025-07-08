package Model;

import Events.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ZapisOdczytWyniku implements ZapisWynikuListener, OdczytWynikuListener {
    ArrayList<PlayersData> playersDatas = new ArrayList<>();
    private final List<WynikiOdczytaneListener> WynikiOdczytaneListeners = new ArrayList<>();
    private final String filePath = "Scores.ser";

    @Override
    public void onZapisWyniku(ZapisWynikuEvent evt) {
        try {
            saveData(new PlayersData(evt.getNick(), evt.getScore()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onOdczytWyniku(OdczytWynikuEvent e) {
        try {
            ArrayList<PlayersData> wyniki = readData();
            for (WynikiOdczytaneListener l : WynikiOdczytaneListeners) {
                l.onWynikiOdczytane(new WynikiOdczytaneEvent(this, wyniki));
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<PlayersData> readData() throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        } else {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<PlayersData> players = (ArrayList<PlayersData>) in.readObject();
            in.close();
            fileIn.close();
            return players;
        }
    }

    public void saveData(PlayersData player) throws IOException {
        try {
            playersDatas = readData();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        playersDatas.add(player);

        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(playersDatas);
        out.close();
        fileOut.close();
        System.out.println("object saved");
    }
    public void addWynikiOdczytaneListener(WynikiOdczytaneListener l) {
        WynikiOdczytaneListeners.add(l);
    }

}
