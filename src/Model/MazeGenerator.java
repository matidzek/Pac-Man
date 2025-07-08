package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MazeGenerator {
    private int columns;
    private int rows;
    private int czyparzkol;
    private int czyparzwier;
    private long iloscKulek;
    private List<List<Integer>> data;
    public MazeGenerator(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.czyparzkol = columns % 2;
        this.czyparzwier = rows % 2;
        this.data = new ArrayList<>();

        var odwiedzone = mazeGenerator();
        backtracker(1, 1, odwiedzone);
        dodajPetle();
        iloscKulek = policzIloscKulek();
    }

    public ArrayList<ArrayList<Integer>> mazeGenerator(){
        //tworzenie dopasowanej mapy
        for (int i = 0; i <= rows + czyparzwier; i++) {
            data.add(new ArrayList<>());
            for (int j = 0; j <= columns + czyparzkol; j++) { //dla 5 kolumn wykona 7 pętli, dla 6 kolumn wykona 7 pętli
                data.get(i).add(1);
            }
        }
        //oddzielna "plansza" na odwiedzone elementy dla algorytmu tworzącego labirynt
        ArrayList<ArrayList<Integer>> odwiedzone = new ArrayList<>();
        for (int i = 0; i <= rows + czyparzwier; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j <= columns + czyparzkol; j++) {
                row.add(0);
            }
            odwiedzone.add(row);
        }
        return odwiedzone;
    }

    private void backtracker(int row, int col, ArrayList<ArrayList<Integer>> odwiedzone) {
        final int[][] kierunki = {
                {2, 0}, //gora
                {-2, 0}, //dol
                {0, -2}, //lewo
                {0, 2} //prawo
        };
        odwiedzone.get(row).set(col, 1);
        data.get(row).set(col, 0);
        Collections.shuffle(Arrays.asList(kierunki)); // aby losować ścieżkę, inaczej plansza bylaby zbyt jednolita/prosta
        for (int[] kierunek : kierunki) {
            int nrow = row + kierunek[0];
            int ncol = col + kierunek[1];
            if (ncol > 0 && ncol< data.get(0).size() - 1 && nrow > 0
                    && nrow < data.size() - 1
                    && odwiedzone.get(nrow).get(ncol) != 1
            ) {
                int wallRow = row + kierunek[0] / 2; //usuwam scianę między dwoma rzędzami
                int wallCol = col + kierunek[1] / 2; //usuwam scianę między dwoma kolumnami
                data.get(wallRow).set(wallCol, 0);
                backtracker(nrow, ncol, odwiedzone);
            }
        }
    }

    private void dodajPetle() {
        //tworzenie pętli w labiryncie
        int dziurka = 0; //aby zawsze zostalo minimum jedno przejscie między kolumnami
        for (int i = 1; i < rows+czyparzwier; i++) {
            for (int j = 1; j < columns +czyparzkol ; j++) {
                int ilesasiadow= 0;
                if (data.get(i).get(j) == 1) {
                    ilesasiadow += data.get(i).get(j+1) + data.get(i).get(j-1);
                    if(ilesasiadow == 2){
                        data.get(i).set(j,0);
                    }
                    //gdy mam pojedyncą komórkę ze scianą
                    if( ((int) (Math.random()*2) == 0) && dziurka++ > 0 && data.get(i-1).get(j) == 0 && data.get(i+1).get(j) == 0 && data.get(i).get(j) == 1) {
                        data.get(i+1).set(j,1);
                        data.get(i-1).set(j,1);
                    }
                }

            }
        }
    }

    private long policzIloscKulek(){
        return data.stream().flatMap(inList -> inList.stream()).filter(i -> i == 0).count();
    }


    public List<List<Integer>> getData() {
        return data;
    }

    public long getIloscKulek() {
        return iloscKulek;
    }
}
