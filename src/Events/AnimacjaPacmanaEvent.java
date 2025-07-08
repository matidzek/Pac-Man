package Events;

import Model.MapModel;

import java.util.EventObject;

public class AnimacjaPacmanaEvent extends EventObject {
    private MapModel.Kierunek kierunek;
    private int katSzczeki;
    public AnimacjaPacmanaEvent(Object source, MapModel.Kierunek kierunek, int katSzczeki) {
        super(kierunek);
        this.kierunek = kierunek;
        this.katSzczeki = katSzczeki;
    }
    public MapModel.Kierunek getKierunek() {
        return kierunek;
    }
    public int getKatSzczeki() {
        return katSzczeki;
    }
}
