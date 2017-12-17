package controle;

import modele.Planificateur;
import modele.Reunion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ValiderReunion implements ActionListener, Observer {

    private Planificateur modele;
    private Reunion reunion;
    private JButton button;

    public ValiderReunion(Planificateur modele, Reunion reunion, JButton validate) {
        this.modele = modele;
        this.reunion = reunion;
        this.button = validate;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        reunion.setPosibilites(
                modele.getPosibilite(
                        reunion.getPersonnes(),reunion.getDuree(),reunion.getDebutPlage(),reunion.getFinPlage()));
    }

    @Override
    public void update(Observable observable, Object message) {

        if (reunion.getDuree() != 0 &&
                reunion.getDebutPlage() != null &&
                reunion.getFinPlage() != null &&
                !reunion.getPersonnes().isEmpty()) {

            button.setEnabled(true);

        }else {
            button.setEnabled(false);
        }
    }
}
