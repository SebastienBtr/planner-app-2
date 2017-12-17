package controle;

import LGoodDatePicker.com.github.lgooddatepicker.components.DatePickerSettings;
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

public class FinPlageControle implements DateTimeChangeListener, Observer {

    private Reunion reunion;
    private DatePickerSettings dateEndSettings;
    private TimePickerSettings endTimeSettings;
    private DateTimePicker endDateTimePicker;

    private int heuredebut;
    private int mindebut;
    private int heurefin;
    private int minfin;

    public FinPlageControle(Reunion reunion, DatePickerSettings dateEndSettings, TimePickerSettings endTimeSettings, DateTimePicker endDateTimePicker) {

        this.reunion = reunion;
        this.dateEndSettings = dateEndSettings;
        this.endTimeSettings = endTimeSettings;
        this.endDateTimePicker = endDateTimePicker;

        this.heuredebut = 8;
        this.mindebut = 0;
        this.heurefin = 20;
        this.minfin = 0;
    }

    @Override
    public void dateOrTimeChanged(DateTimeChangeEvent event) {
        reunion.setFinPlage(event.getNewDateTimeStrict());
    }

    @Override
    public void update(Observable observable, Object message) {

        if (reunion.getDuree() == 0 || reunion.getDebutPlage() == null) {
            endDateTimePicker.setEnabled(false);

        }else {
            endDateTimePicker.setEnabled(true);
        }

        Integer iMessage = (Integer)message;

        if ((iMessage == reunion.CHANGEMENT_DEBUT || iMessage == reunion.CHANGEMENT_FIN) && reunion.getDebutPlage() != null) {

            LocalDate minDate = reunion.getDebutPlage().toLocalDate();
            dateEndSettings.setVetoPolicy(new VetoPolicy.LimitDateVetoPolicy(minDate));

            int minutes = 1200 - reunion.getDuree(); //1200min = 20h
            heurefin = (int)(minutes / 60);
            minfin = minutes - 60*heurefin;

            endTimeSettings.setVetoPolicy(new VetoPolicy.LimitTimeVetoPolicy(heuredebut,mindebut,heurefin,minfin));

            if (reunion.getFinPlage() != null && reunion.getFinPlage().isBefore(reunion.getDebutPlage())) {
                reunion.setFinPlage(reunion.getDebutPlage());
                endDateTimePicker.setDateTimeStrict(reunion.getDebutPlage());
            }
        }

        if (iMessage == reunion.CHANGEMENT_DUREE) {

            int oldMinute = heurefin*60 + minfin;

            int minutes = 1200 - reunion.getDuree(); //1200min = 20h
            heurefin = (int)(minutes / 60);
            minfin = minutes - 60*heurefin;

            endTimeSettings.setVetoPolicy(new VetoPolicy.LimitTimeVetoPolicy(heuredebut,mindebut,heurefin,minfin));

            if (oldMinute > minutes && reunion.getFinPlage() != null) {
                LocalDate date = reunion.getFinPlage().toLocalDate();
                LocalTime time = LocalTime.of(heurefin,minfin);
                LocalDateTime dateTime = LocalDateTime.of(date, time);

                reunion.setFinPlage(dateTime);
                endDateTimePicker.setDateTimeStrict(dateTime);
            }
        }
    }
}
