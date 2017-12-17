package controle;

import LGoodDatePicker.com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import LGoodDatePicker.com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import LGoodDatePicker.com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class VetoPolicy {

    /**
     * A veto policy is a way to disallow certain dates from being selected in
     * calendar. A vetoed date cannot be selected by using the keyboard or the mouse.
     */
    public static class LimitDateVetoPolicy implements DateVetoPolicy {

        private LocalDate minDateAllowed;

        public LimitDateVetoPolicy(LocalDate minDateAllowed) {
            this.minDateAllowed = minDateAllowed;
        }

        public LimitDateVetoPolicy() {
            minDateAllowed = LocalDate.now();
        }

        /**
         * isDateAllowed, Return true if a date should be allowed, or false if a date should be
         * vetoed.
         */
        @Override
        public boolean isDateAllowed(LocalDate date) {

            if (date.isBefore(minDateAllowed)) {
                return false;
            }

            if ((date.getDayOfWeek() == DayOfWeek.SATURDAY) || (date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                return false;
            }

            return true;
        }
    }

    /**
     * A veto policy is a way to disallow certain times from being selected in
     * the time picker. A vetoed time cannot be added to the time drop down menu. A vetoed time
     * cannot be selected by using the keyboard or the mouse.
     */
    public static class LimitTimeVetoPolicy implements TimeVetoPolicy {

        private int heureFinLimite;
        private int minFinLimite;
        private int heureDebutLimite;
        private int minDebutLimite;


        public LimitTimeVetoPolicy(int heureDebutLimite, int minDebutLimite, int heureFinLimite, int minFinLimite) {
            this.heureDebutLimite = heureDebutLimite;
            this.minDebutLimite = minDebutLimite;
            this.heureFinLimite = heureFinLimite;
            this.minFinLimite = minFinLimite;
        }

        public LimitTimeVetoPolicy() {
            this.heureDebutLimite = 8;
            this.minDebutLimite = 0;
            this.heureFinLimite = 20;
            this.minFinLimite = 0;
        }

        public void setDebut(int heureDebutLimite, int minDebutLimite) {

            this.heureDebutLimite = heureDebutLimite;
            this.minDebutLimite = minDebutLimite;
        }

        /**
         * isTimeAllowed, Return true if a time should be allowed, or false if a time should be
         * vetoed.
         */
        @Override
        public boolean isTimeAllowed(LocalTime time) {

            return PickerUtilities.isLocalTimeInRange(
                    time, LocalTime.of(heureDebutLimite, minDebutLimite), LocalTime.of(heureFinLimite, minFinLimite), true);
        }
    }

    public static class PeriodTimeVetoPolicy implements TimeVetoPolicy {

        @Override
        public boolean isTimeAllowed(LocalTime time) {

            return PickerUtilities.isLocalTimeInRange(
                    time, LocalTime.of(00, 00), LocalTime.of(04, 00), true);
        }
    }
}
