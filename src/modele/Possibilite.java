package modele;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;

public class Possibilite {

    private LocalDateTime debut;
    private LocalDateTime fin;
    private HashMap<String,LinkedList<Personne>> personneDispo;

    /**
     * Créé une possibilite, qui est une plage dans laquelle la réunion peut commencer
     * @param debut la date de debut de la possibilite
     * @param fin la date de fin de la possibilite
     * @param personneDispo les personnes dispo est indispo pour cette possibilite
     */
    public Possibilite(LocalDateTime debut, LocalDateTime fin, HashMap<String, LinkedList<Personne>> personneDispo) {
        this.debut = debut;
        this.fin = fin;
        this.personneDispo = personneDispo;
    }

    /**
     * Methode pour concaténer deux possibilités, attention a.concat(b) != b.concat(a)
     * @param possibilite la possibilte à concaténer avec this
     * @return la nouvelle possibilite créé
     */
    public Possibilite concat(Possibilite possibilite) {
        //TODO verif que possibilite est après this

        LinkedList<Personne> potentiellementLibres = new LinkedList<>();
        LinkedList<Personne> indisponibles = personneDispo.get("indisponibles");

        for (Personne p : personneDispo.get("disponibles")) {
            if (possibilite.personneDispo.get("disponibles").contains(p)) {
                potentiellementLibres.add(p);
            }
            else {
                indisponibles.add(p);
            }
        }

        HashMap<String,LinkedList<Personne>> newPersonneDispo = new HashMap<>();
        newPersonneDispo.put("disponibles",potentiellementLibres);
        newPersonneDispo.put("indisponibles",indisponibles);

        return new Possibilite(debut, possibilite.fin,newPersonneDispo);
    }

    /**
     * Vérifie si deux possibilite sont égales, elles le sont si le nombre de personne dispo
     * est égal au nombre de personnes indispo
     * @param possibilite le possibilte à comparer
     * @return true si les possibilités sont égales
     */
    public boolean estEgal(Possibilite possibilite) {

        if (personneDispo.get("disponibles").size() == possibilite.personneDispo.get("disponibles").size()) {
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
