package controle;

import modele.Personne;
import modele.Planificateur;
import modele.Reunion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class PersonneControle implements ActionListener {

    private Reunion reunion;
    private Planificateur modele;
    private int index;

    public PersonneControle(Planificateur modele, Reunion reunion, int i) {
        this.reunion = reunion;
        this.modele = modele;
        this.index = i;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JCheckBox checkBox = (JCheckBox)(actionEvent.getSource());
        LinkedList<Personne> personnes = reunion.getPersonnes();

        if (checkBox.isSelected()) {
            personnes.add(modele.getPersonnes().get(index));
            reunion.setPersonnes(personnes);

        }else {
            personnes.remove(modele.getPersonnes().get(index));
            reunion.setPersonnes(personnes);
        }
    }
}
