package controle;

import LGoodDatePicker.com.github.lgooddatepicker.components.DateTimePicker;
import LGoodDatePicker.com.github.lgooddatepicker.components.TimePickerSettings;
import LGoodDatePicker.com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import LGoodDatePicker.com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import modele.Reunion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Observable;
import java.util.Observer;

public class DebutPlageControle implements DateTimeChangeListener, Observer {

    private Reunion reunion;
    private TimePickerSettings startTimeSettings;
    private DateTimePicker startDateTimePicker;

    private int heuredebut;
    private int mindebut;
    private int heurefin;
    private int minfin;

    public DebutPlageControle(Reunion reunion, TimePickerSettings startTimeSettings, DateTimePicker startDateTimePicker) {
        this.reunion = reunion;
        this.startTimeSettings = startTimeSettings;
        this.startDateTimePicker = startDateTimePicker;

        this.heuredebut = 8;
        this.mindebut = 0;
        this.heurefin = 20;
        this.minfin = 0;
    }

    @Override
    public void dateOrTimeChanged(DateTimeChangeEvent event) {
        reunion.setDebutPlage(event.getNewDateTimeStrict());
    }

    @Override
    public void update(Observable observable, Object message) {

        if (reunion.getDuree() == 0) {
            startDateTimePicker.setEnabled(false);

        }else {
            startDateTimePicker.setEnabled(true);
        }

        Integer iMessage = (Integer)message;

        if (iMessage == reunion.CHANGEMENT_DUREE) {

            int oldMinute = heurefin*60 + minfin;

            int minutes = 1200 - reunion.getDuree(); //1200min = 20h
            heurefin = (int)(minutes / 60);
            minfin = minutes - 60*heurefin;

            startTimeSettings.setVetoPolicy(new VetoPolicy.LimitTimeVetoPolicy(8,0,heurefin,minfin));

            if (oldMinute > minutes && reunion.getFinPlage() != null) {
                LocalDate date = reunion.getFinPlage().toLocalDate();
                LocalTime time = LocalTime.of(heurefin,minfin);
                LocalDateTime dateTime = LocalDateTime.of(date, time);

                reunion.setFinPlage(dateTime);
                startDateTimePicker.setDateTimeStrict(dateTime);
            }
        }
    }
}
