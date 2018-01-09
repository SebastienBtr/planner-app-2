package presentation;

import javax.swing.*;
import java.awt.*;

import LGoodDatePicker.com.github.lgooddatepicker.components.DatePickerSettings;
import LGoodDatePicker.com.github.lgooddatepicker.components.DateTimePicker;
import LGoodDatePicker.com.github.lgooddatepicker.components.TimePicker;
import LGoodDatePicker.com.github.lgooddatepicker.components.TimePickerSettings;
import LGoodDatePicker.com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

import controle.*;
import modele.Planificateur;
import modele.Reunion;
import controle.VetoPolicy.*;

public class PlannerFrame extends JFrame{

    private Planificateur planificateur;
    private Reunion reunion;
    private JLabel[][] lesCases;
    private JPanel dispoPanel;
    private JPanel indispoPanel;

    public PlannerFrame() {

        super("Planificateur de réunion");
        this.planificateur = new Planificateur();
        this.reunion = new Reunion();
        this.lesCases = new JLabel[50][6];
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        leftPanel();
        rightPanel();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);
        this.setVisible(true);
        //this.setPreferredSize(new Dimension(500,500));
        //this.setResizable(false);
        this.pack();
    }

    private void rightPanel() {

        JPanel panelRight = new JPanel();
        panelRight.setLayout(new BorderLayout());

        panelRight.add(calendar(),BorderLayout.CENTER);

        JLabel info = new JLabel("cliquez sur une créneau pour voir les disponibilités");
        info.setHorizontalAlignment(JLabel.CENTER);
        panelRight.add(info,BorderLayout.NORTH);

        this.add(panelRight,BorderLayout.CENTER);
    }

    private void leftPanel() {
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft,BoxLayout.Y_AXIS));

        duree(panelLeft);
        plageDebut(panelLeft);
        plageFin(panelLeft);
        listeParticipant(panelLeft);
        validateButton(panelLeft);
        disponible(panelLeft);
        indisponible(panelLeft);

        this.add(panelLeft,BorderLayout.WEST);
    }

    /*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////// LEFT PANEL /////////////////////////////////////////////////////////////////////////////////////////////
     */
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

    private void plageDebut(JPanel panelLeft) {

        JLabel slotStartLabel = new JLabel("Début :");
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

    private void plageFin(JPanel panelLeft) {

        JLabel slotEndLabel = new JLabel("Fin :");
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

    private void listeParticipant(JPanel panelLeft) {

        JPanel participant = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(participant);

        for(int i =0; i< planificateur.getPersonnes().size(); i++) {

            JCheckBox checkBox = new JCheckBox(planificateur.getPersonnes().get(i).getId());
            checkBox.addActionListener(new PersonneControle(planificateur,reunion,i));
            participant.add(checkBox);
        }
        scrollPane.setAlignmentX( Component.LEFT_ALIGNMENT );
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, scrollPane.getMinimumSize().height*planificateur.getPersonnes().size()));

        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(scrollPane);
    }

    private void validateButton(JPanel panelLeft) {

        JButton validate = new JButton("Valider");
        validate.setEnabled(false);

        validate.setAlignmentX( Component.LEFT_ALIGNMENT );
        validate.setMaximumSize(new Dimension(Integer.MAX_VALUE, validate.getMinimumSize().height));

        ValiderReunion validerReunion = new ValiderReunion(planificateur,reunion,validate);
        reunion.addObserver(validerReunion);
        validate.addActionListener(validerReunion);

        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(validate);
    }

    private void disponible(JPanel panelLeft) {

        JLabel dispo = new JLabel("Disponibles : ");

        dispoPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(dispoPanel);
        scrollPane.setAlignmentX( Component.LEFT_ALIGNMENT );

        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(dispo);
        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(scrollPane);

    }

    private void indisponible(JPanel panelLeft) {

        JLabel indispo = new JLabel("Indisponibles : ");

        indispoPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(indispoPanel);
        scrollPane.setAlignmentX( Component.LEFT_ALIGNMENT );

        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(indispo);
        panelLeft.add(Box.createRigidArea(new Dimension(5,15)));
        panelLeft.add(scrollPane);

    }

    /*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////// RIGHT PANEL /////////////////////////////////////////////////////////////////////////////////////////////
     */
    private JPanel calendar() {
        JPanel calendar = new JPanel();

        calendar.setLayout(new GridLayout(50,6));
        for (int lig = 0; lig < 50; lig++) {
            for (int col = 0; col < 6; col++) {
                JLabel label = new JLabel("   ");
                label.setOpaque(true);
                //label.setBackground(Color.lightGray);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
                label.setHorizontalAlignment(JLabel.CENTER);
                //label.setVerticalAlignment(JLabel.CENTER);
                lesCases[lig][col] = label;
                calendar.add(lesCases[lig][col]);
            }
        }

        int heure = 8;
        for (int i = 1; i < 50; i = i+4) {
            lesCases[i][0].setText(heure+":00");
            lesCases[i][0].setHorizontalAlignment(JLabel.RIGHT);
            heure++;

            for (int j = 1; j < 6; j++) {
                lesCases[i][j].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
            }
        }

        lesCases[0][1].setText("Lundi");
        lesCases[0][1].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
        lesCases[0][2].setText("Mardi");
        lesCases[0][2].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
        lesCases[0][3].setText("Mercredi");
        lesCases[0][3].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
        lesCases[0][4].setText("Jeudi");
        lesCases[0][4].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
        lesCases[0][5].setText("Vendredi");
        lesCases[0][5].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));

        CalendarControle calendarControle = new CalendarControle(reunion,lesCases,dispoPanel,indispoPanel);
        reunion.addObserver(calendarControle);

        return calendar;
    }


    public static void main(String[] args) {
        PlannerFrame frame = new PlannerFrame();
    }
}