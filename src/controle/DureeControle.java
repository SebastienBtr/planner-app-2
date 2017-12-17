package controle;

import LGoodDatePicker.com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import LGoodDatePicker.com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import modele.Reunion;

public class DureeControle implements TimeChangeListener {

    private Reunion reunion;

    public DureeControle(Reunion reunion) {
        this.reunion = reunion;
    }

    public void timeChanged(TimeChangeEvent event) {
        int duree = event.getNewTime().getMinute() + event.getNewTime().getHour()*60;
        this.reunion.setDuree(event.getNewTime().getMinute() + event.getNewTime().getHour()*60);
    }
}
