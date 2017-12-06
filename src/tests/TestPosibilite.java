package tests;

import modele.Personne;
import modele.Planificateur;
import modele.Posibilite;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestPosibilite {

    public static void main(String[] args) {
        Planificateur plan = new Planificateur();

        LocalDateTime debut = LocalDateTime.of(2017,12,31,8,30);
        LocalDateTime fin = LocalDateTime.of(2017,12,31,12,0);

        ArrayList<Posibilite> posibilites = plan.getPosibilite(plan.getPersonnes(),60,debut,fin);

        for (Posibilite p : posibilites) {
            System.out.println("----------de "+p.getDebut()+" à "+p.getFin()+"-------------\n");

            System.out.println("dispo : \n");
            for(Personne personne : p.getPersonneDispo().get("disponibles")) {
                System.out.println(personne.getId());
            }
            System.out.println("\nindispo : \n");
            if (p.getPersonneDispo().get("indisponibles") != null) {
                for (Personne personne : p.getPersonneDispo().get("indisponibles")) {
                    System.out.println(personne.getId());
                }
            }
        }
    }
}
