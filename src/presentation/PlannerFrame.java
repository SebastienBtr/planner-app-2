package presentation;

import javax.swing.*;

import LGoodDatePicker.com.github.lgooddatepicker.components.DatePickerSettings;
import LGoodDatePicker.com.github.lgooddatepicker.components.DateTimePicker;
import LGoodDatePicker.com.github.lgooddatepicker.components.TimePicker;
import LGoodDatePicker.com.github.lgooddatepicker.components.TimePickerSettings;
import LGoodDatePicker.com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

import controle.*;
import modele.Planificateur;
import modele.Reunion;
import controle.VetoPolicy.*;


import java.awt.*;

public class PlannerFrame extends JFrame{

    private Planificateur modele;
    private Reunion reunion;

    public PlannerFrame() {

        super("Plannificateur de réunion");
        this.modele = new Planificateur();
        this.reunion = new Reunion();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        leftPanel();
        rightPanel();

        this.setVisible(true);
        //this.setPreferredSize(new Dimension(500,500));
        //this.setResizable(false);
        this.pack();
    }

    private void rightPanel() {

        JPanel panelRight = new JPanel();
        panelRight.setLayout(new BorderLayout());

        panelRight.add(calendar(),BorderLayout.CENTER);

        this.add(panelRight,BorderLayout.CENTER);
    }

    private JPanel calendar() {
        JPanel calendar = new JPanel();
        calendar.setLayout(new GridLayout(10,6));

        return calendar;
    }

    private void leftPanel() {
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft,BoxLayout.Y_AXIS));

        //Period
        duree(panelLeft);

        //start
        plageDebut(panelLeft);

        //end
        PlageFin(panelLeft);


        //select participants
        JPanel participant = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(participant);
        for(int i =0; i< modele.getPersonnes().size(); i++) {
            JCheckBox checkBox = new JCheckBox(modele.getPersonnes().get(i).getId());
            checkBox.addActionListener(new PersonneControle(modele,reunion,i));
            participant.add(checkBox);
        }
        scrollPane.setAlignmentX( Component.LEFT_ALIGNMENT );
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, scrollPane.getMinimumSize().height*modele.getPersonnes().size()));



        //validate
        JButton validate = new JButton("Valider");
        validate.setEnabled(false);
        validate.setAlignmentX( Component.LEFT_ALIGNMENT );
        validate.setMaximumSize(new Dimension(Integer.MAX_VALUE, validate.getMinimumSize().height));
        ValiderReunion validerReunion = new ValiderReunion(modele,reunion,validate);
        reunion.addObserver(validerReunion);
        validate.addActionListener(validerReunion);



        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(scrollPane);
        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(validate);

        this.add(panelLeft,BorderLayout.WEST);

    }

    private void PlageFin(JPanel panelLeft) {

        JLabel slotEndLabel = new JLabel("Fin");
        slotEndLabel.setAlignmentX( Component.LEFT_ALIGNMENT );

        TimePickerSettings endTimeSettings = new TimePickerSettings();
        endTimeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);

        DatePickerSettings dateEndSettings = new DatePickerSettings();
        DateTimePicker endDateTimePicker = new DateTimePicker(dateEndSettings,endTimeSettings);
        endDateTimePicker.setEnabled(false);

        endTimeSettings.setVetoPolicy(new LimitTimeVetoPolicy());
        dateEndSettings.setVetoPolicy(new LimitDateVetoPolicy());

        FinPlageControle finPlageControle = new FinPlageControle(reunion,dateEndSettings,endTimeSettings,endDateTimePicker);
        reunion.addObserver(finPlageControle);
        endDateTimePicker.addDateTimeChangeListener(finPlageControle);

        endDateTimePicker.setAlignmentX( Component.LEFT_ALIGNMENT );
        endDateTimePicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, endDateTimePicker.getMinimumSize().height));

        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(slotEndLabel);
        panelLeft.add(endDateTimePicker);
    }

    private void plageDebut(JPanel panelLeft) {

        JLabel slotStartLabel = new JLabel("Début");
        slotStartLabel.setAlignmentX( Component.LEFT_ALIGNMENT );

        TimePickerSettings startTimeSettings = new TimePickerSettings();
        startTimeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);

        DatePickerSettings dateStartSettings = new DatePickerSettings();
        DateTimePicker startDateTimePicker = new DateTimePicker(dateStartSettings,startTimeSettings);
        startDateTimePicker.setEnabled(false);

        startTimeSettings.setVetoPolicy(new LimitTimeVetoPolicy());
        dateStartSettings.setVetoPolicy(new LimitDateVetoPolicy());

        DebutPlageControle debutPlageControle = new DebutPlageControle(reunion,startTimeSettings,startDateTimePicker);
        reunion.addObserver(debutPlageControle);
        startDateTimePicker.addDateTimeChangeListener(debutPlageControle);

        startDateTimePicker.setAlignmentX( Component.LEFT_ALIGNMENT );
        startDateTimePicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, startDateTimePicker.getMinimumSize().height));

        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(slotStartLabel);
        panelLeft.add(startDateTimePicker);
    }

    private void duree(JPanel panelLeft) {

        JLabel periodLabel = new JLabel("Durée :");
        periodLabel.setAlignmentX( Component.LEFT_ALIGNMENT );

        TimePickerSettings periodTimeSettings = new TimePickerSettings();
        TimePicker period = new TimePicker(periodTimeSettings);
        periodTimeSettings.setVetoPolicy(new PeriodTimeVetoPolicy());
        periodTimeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);

        period.addTimeChangeListener(new DureeControle(reunion));
        period.setAlignmentX( Component.LEFT_ALIGNMENT );
        period.setMaximumSize(new Dimension(Integer.MAX_VALUE, period.getMinimumSize().height));

        panelLeft.add(periodLabel);
        panelLeft.add(period);
    }



    public static void main(String[] args) {
        PlannerFrame frame = new PlannerFrame();
    }
}