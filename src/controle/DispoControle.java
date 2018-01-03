package controle;

import modele.Personne;
import modele.Possibilite;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DispoControle extends MouseAdapter {

    private Possibilite possibilite;
    private JPanel dispoPanel;
    private JPanel indispoPanel;

    public DispoControle(Possibilite p, JPanel dispoPanel, JPanel indispoPanel) {
        this.possibilite = p;
        this.dispoPanel = dispoPanel;
        this.indispoPanel = indispoPanel;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        dispoPanel.removeAll();
        dispoPanel.revalidate();
        dispoPanel.repaint();
        indispoPanel.removeAll();
        indispoPanel.revalidate();
        indispoPanel.repaint();

        System.out.println("dispo : ");
        for (Personne personne : possibilite.getPersonneDispo().get("disponibles")) {

            System.out.println(personne.getId());
            JLabel dispo = new JLabel(personne.getId());
            dispoPanel.add(dispo);
        }
        System.out.println("indispo : ");
        for (Personne personne : possibilite.getPersonneDispo().get("indisponibles")) {

            System.out.println(personne.getId());
            JLabel indispo = new JLabel(personne.getId());
            indispoPanel.add(indispo);
        }

    }
}
