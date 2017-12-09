package presentation;

import javax.swing.*;

import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import modele.Planificateur;


import java.awt.*;
import java.time.LocalTime;

public class PlannerFrame extends JFrame{

    private Planificateur modele;

    public PlannerFrame() {

        super("titre");
        this.modele = new Planificateur();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,2));

        leftPanel();


        this.setVisible(true);
        //this.setPreferredSize(new Dimension(500,500));
        //this.setResizable(false);
        this.pack();

    }

    private void leftPanel() {
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft,BoxLayout.Y_AXIS));

        //Period
        JLabel periodLabel = new JLabel("Durée :");
        TimePickerSettings periodTimeSettings = new TimePickerSettings();
        TimePicker period = new TimePicker(periodTimeSettings);
        periodTimeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);

        //time slot
        JLabel slotLabel = new JLabel("Plage horaire :");
        JLabel slotStartLabel = new JLabel("Début");
        JLabel slotEndLabel = new JLabel("Fin");
        TimePickerSettings slotTimeSettings = new TimePickerSettings();
        slotTimeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
        //start
        DateTimePicker startDateTimePicker = new DateTimePicker(null,slotTimeSettings);
        slotTimeSettings.setVetoPolicy(new SampleTimeVetoPolicy());
        //end
        DateTimePicker endDateTimePicker = new DateTimePicker(null,slotTimeSettings);
        slotTimeSettings.setVetoPolicy(new SampleTimeVetoPolicy());

        //select participants


        JPanel participant = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(participant);
        for(int i =0; i< modele.getPersonnes().size(); i++) {
            participant.add(new JCheckBox(modele.getPersonnes().get(i).getId()));
        }


        //validate
        JButton validate = new JButton("Valider");

        panelLeft.add(periodLabel);
        panelLeft.add(period);
        panelLeft.add(slotLabel);
        panelLeft.add(slotStartLabel);
        panelLeft.add(startDateTimePicker);
        panelLeft.add(slotEndLabel);
        panelLeft.add(endDateTimePicker);
        panelLeft.add(scrollPane);
        panelLeft.add(validate);

        this.add(panelLeft);

    }

    /**
     * SampleTimeVetoPolicy, A veto policy is a way to disallow certain times from being selected in
     * the time picker. A vetoed time cannot be added to the time drop down menu. A vetoed time
     * cannot be selected by using the keyboard or the mouse.
     */
    private static class SampleTimeVetoPolicy implements TimeVetoPolicy {

        /**
         * isTimeAllowed, Return true if a time should be allowed, or false if a time should be
         * vetoed.
         */
        @Override
        public boolean isTimeAllowed(LocalTime time) {
            // Only allow times from 8a to 8p, inclusive.
            return PickerUtilities.isLocalTimeInRange(
                    time, LocalTime.of(8, 00), LocalTime.of(20, 00), true);
        }
    }

    public static void main(String[] args) {
        PlannerFrame frame = new PlannerFrame();
    }
}