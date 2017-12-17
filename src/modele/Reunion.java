package modele;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

public class Reunion extends Observable {

    public static final Integer CHANGEMENT_DUREE = new Integer(0); // PAC
    public static final Integer CHANGEMENT_DEBUT = new Integer(1); // PAC
    public static final Integer CHANGEMENT_FIN = new Integer(2); // PAC
    public static final Integer CHANGEMENT_PERSONNES = new Integer(3); // PAC
    public static final Integer CHANGEMENT_POSSIBILITES = new Integer(4); // PAC

    private int duree;
    private LocalDateTime debutPlage;
    private LocalDateTime finPlage;
    private LinkedList<Personne> personnes;
    private ArrayList<Posibilite> posibilites;

    public Reunion() {
        personnes = new LinkedList<>();
        posibilites = new ArrayList<>();
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
        this.setChanged();                    // PAC
        this.notifyObservers(CHANGEMENT_DUREE); // PAC
    }

    public LocalDateTime getDebutPlage() {
        return debutPlage;
    }

    public void setDebutPlage(LocalDateTime debutPlage) {
        this.debutPlage = debutPlage;
        this.setChanged();                    // PAC
        this.notifyObservers(CHANGEMENT_DEBUT); // PAC

    }

    public LocalDateTime getFinPlage() {
        return finPlage;
    }

    public void setFinPlage(LocalDateTime finPlage) {
        this.finPlage = finPlage;
        this.setChanged();                    // PAC
        this.notifyObservers(CHANGEMENT_FIN); // PAC
    }

    public LinkedList<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(LinkedList<Personne> personnes) {
        this.personnes = personnes;
        this.setChanged();                    // PAC
        this.notifyObservers(CHANGEMENT_PERSONNES); // PAC
    }

    public ArrayList<Posibilite> getPosibilites() {
        return posibilites;
    }

    public void setPosibilites(ArrayList<Posibilite> posibilites) {
        this.posibilites = posibilites;
        this.setChanged();                    // PAC
        this.notifyObservers(CHANGEMENT_POSSIBILITES); // PAC
    }
}
