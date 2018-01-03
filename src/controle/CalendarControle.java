package controle;

import modele.Possibilite;
import modele.Reunion;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Observable;
import java.util.Observer;

public class CalendarControle implements Observer {

    private Reunion reunion;
    private JLabel[][] lesCases;
    private JPanel dispoPanel;
    private JPanel indispoPanel;

    public CalendarControle(Reunion reunion, JLabel[][] lesCases, JPanel dispoPanel, JPanel indispoPanel) {
        this.reunion = reunion;
        this.lesCases = lesCases;
        this.dispoPanel = dispoPanel;
        this.indispoPanel = indispoPanel;
    }

    @Override
    public void update(Observable observable, Object message) {

        Integer iMessage = (Integer)message;

        if (iMessage == reunion.CHANGEMENT_POSSIBILITES) {

            int[] maxMin = calculerMax();

            for (int l = 0; l < 50; l++) {
                for (int c = 0; c < 6; c++) {
                    lesCases[l][c].setBackground(null);
                }
            }

            int lig = 1 + ((reunion.getDebutPlage().getHour() - 8)*4) + (reunion.getDebutPlage().getMinute() / 15);
            int col = 1;
            LocalDate date = reunion.getPossibilites().get(0).getDebut().toLocalDate();
            lesCases[0][1].setText(date.toString());
            lesCases[0][2].setText(date.plusDays(1).toString());
            lesCases[0][3].setText(date.plusDays(2).toString());
            lesCases[0][4].setText(date.plusDays(3).toString());
            lesCases[0][5].setText(date.plusDays(4).toString());

            for (Possibilite p : reunion.getPossibilites()) {

                LocalDateTime debut = p.getDebut();
                while ( !(debut.isAfter(p.getFin()) || debut.isEqual(p.getFin())) ) {

                    if (debut.toLocalDate().isAfter(date)) {
                        date = debut.toLocalDate();
                        col++;
                        lig = 1;
                    }

                    if (col >=6) {
                        break;
                    }

                    if (p.getPersonneDispo().get("disponibles").size() == maxMin[0]) {
                        lesCases[lig][col].setBackground(Color.GREEN);

                    }else if(p.getPersonneDispo().get("disponibles").size() == maxMin[1]) {
                        lesCases[lig][col].setBackground(Color.RED);

                    }else {
                        lesCases[lig][col].setBackground(Color.ORANGE);
                    }

                    lesCases[lig][col].setToolTipText(debut.toLocalTime()+"");
                    lesCases[lig][col].addMouseListener(new DispoControle(p,dispoPanel,indispoPanel));

                    debut = incrementDate(debut);
                    lig++;
                }
                //lesCases[lig][col].setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));

                System.out.println("debut : "+p.getDebut()+", fin : "+p.getFin());
                System.out.println("nbdispo : "+p.getPersonneDispo().get("disponibles").size());
                System.out.println("nbIndispo : "+p.getPersonneDispo().get("indisponibles").size());

            }
        }
    }

    private LocalDateTime incrementDate(LocalDateTime date) {
        LocalTime time = LocalTime.of(20,00);
        int heures = (int)(reunion.getDuree() / 60);
        int minutes = reunion.getDuree() - 60*heures;
        time = time.minusHours(heures);
        time = time.minusMinutes(minutes);
        LocalDateTime limitTime = LocalDateTime.of(date.toLocalDate(), time);

        if (date.plusMinutes(15).isEqual(limitTime)) {
            LocalTime heureDebutJournee = LocalTime.of(8,00);
            LocalDateTime newDate = LocalDateTime.of(date.toLocalDate().plusDays(1),heureDebutJournee);
            return newDate;
        }

        return date.plusMinutes(15);
    }

    private int[] calculerMax() {

        int max = 0;
        int min = reunion.getPersonnes().size();

        for (Possibilite p : reunion.getPossibilites()) {

            int size = p.getPersonneDispo().get("disponibles").size();

            if (size > max) {
                max = size;
            }

            if (size < min) {
                min = size;
            }
        }

        int[] ret = new int[2];
        ret[0] = max;
        ret[1] = min;


        return ret;
    }
}
