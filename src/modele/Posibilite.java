package modele;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;

public class Posibilite {

    private LocalDateTime debut;
    private LocalDateTime fin;
    private HashMap<String,LinkedList<Personne>> personneDispo;


    public Posibilite(LocalDateTime debut, LocalDateTime fin, HashMap<String, LinkedList<Personne>> personneDispo) {
        this.debut = debut;
        this.fin = fin;
        this.personneDispo = personneDispo;
    }

    //a.concat(b) != b.concat(a)
    public Posibilite concat(Posibilite posibilite) {
        //TODO verif param is after this

        LinkedList<Personne> potentiellementLibres = new LinkedList<>();
        LinkedList<Personne> indisponibles = personneDispo.get("indisponibles");

        for (Personne p : personneDispo.get("disponibles")) {
            if (posibilite.personneDispo.get("disponibles").contains(p)) {
                potentiellementLibres.add(p);
            }
            else {
                indisponibles.add(p);
                System.out.println("------------------------------------------------------indispo");
            }
        }

        HashMap<String,LinkedList<Personne>> newPersonneDispo = new HashMap<>();
        newPersonneDispo.put("disponibles",potentiellementLibres);
        newPersonneDispo.put("indisponibles",indisponibles);

        return new Posibilite(debut,posibilite.fin,newPersonneDispo);
    }

    public boolean estEgal(Posibilite posibilite) {

        if (personneDispo.get("disponibles").size() == posibilite.personneDispo.get("disponibles").size()) {
            return true;
        }
        return false;
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public HashMap<String, LinkedList<Personne>> getPersonneDispo() {
        return personneDispo;
    }
}
