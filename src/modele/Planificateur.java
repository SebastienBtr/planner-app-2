package modele;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Planificateur {

	private LinkedList<Personne> personnes;
	private EchangeurICS exch;

	public Planificateur() {
	    personnes = new LinkedList<>();
        exch = new EchangeurICS();

        File calendars = new File("fichiers");

        if (calendars.listFiles() != null) {
            for (File file : calendars.listFiles()) {

                try {
                    personnes.add(exch.lireFichierICS(file));

                } catch (IOException e) {
                    System.out.println("Erreur lors de la lecture du fichier.");
                }
            }
        }
    }

	/**
	 * Cette m�thode permet de r�cup�rer les disponiblit�s de plusieurs personne sur un m�me cr�neau
	 * @param  personnes une LinkedList de Personne
	 * @param  date la date
	 * @param  heure_debut l'heure du d�but du cr�neau
	 * @param  min_debut les minutes du d�but du cr�neau
	 * @return      renvoie une hashmap avec deux champs ("disponible" et "indisponibles") qui contiennent des personnes
	 */
	public HashMap<String,LinkedList<Personne>>
    getPersonnesDispo(LinkedList<Personne> personnes,LocalDate date, int heure_debut, int min_debut){

	    HashMap<String,LinkedList<Personne>> personnesResultat = new HashMap<>();
		LinkedList<Personne> potentiellementLibres = new LinkedList<>();
		LinkedList<Personne> indisponibles = new LinkedList<>();

		for (Personne p:personnes){
			if (p.estLibre(date, heure_debut, min_debut)!=3){
				potentiellementLibres.add(p);
			}
			else {
				indisponibles.add(p);
			}
		}

		personnesResultat.put("disponibles", potentiellementLibres);
		personnesResultat.put("indisponibles", indisponibles);
		return personnesResultat;
	}

	public ArrayList<Posibilite>
    getPosibilite(LinkedList<Personne> personnes, int duree, LocalDateTime debutPlage, LocalDateTime finPlage ) {

        if (!correctParam(personnes, duree, debutPlage, finPlage)) {
            throw new IllegalArgumentException();
        }

        ArrayList<Posibilite> posibilites = new ArrayList<>();
        int i = 0;

        while (!debutPlage.isEqual(finPlage)) {

            Posibilite newPosibilite = null;
            int creneau = 0;

            while (creneau != duree) {

                Posibilite tmpPosibilite = new Posibilite(debutPlage,debutPlage.plusMinutes(15),
                        getPersonnesDispo(personnes,debutPlage.toLocalDate(),debutPlage.plusHours((int)(debutPlage.getMinute() + creneau)/60).getHour(),debutPlage.plusMinutes(creneau).getMinute()));

                if (newPosibilite != null) {
                    newPosibilite = newPosibilite.concat(tmpPosibilite);
                }
                else {
                    newPosibilite = tmpPosibilite;
                }
                creneau += 15;
            }

            if (i > 0 && posibilites.get(i-1).estEgal(newPosibilite)) {
                newPosibilite = posibilites.get(i-1).concat(newPosibilite);
                posibilites.add(i-1,newPosibilite);
            }
            else {
                posibilites.add(newPosibilite);
            }

            debutPlage = incrementDate(debutPlage,duree);
            i++;
        }

        return posibilites;
    }

    private boolean correctParam(LinkedList<Personne> personnes, int duree, LocalDateTime debutPlage, LocalDateTime finPlage) {
        //TODO verif param
        return true;
    }

    private LocalDateTime incrementDate(LocalDateTime date, int duree) {
        LocalTime time = LocalTime.of(20,00);
        LocalDateTime limitTime = LocalDateTime.of(date.toLocalDate(), time);

        if (date.plusMinutes(15).isAfter(limitTime)) {

            LocalTime heureDebutJournee = LocalTime.of(8,00);
            LocalDateTime newDate = LocalDateTime.of(date.toLocalDate().plusDays(1),heureDebutJournee);
            return newDate;
        }

        return date.plusMinutes(15);
    }

	/**
	 *
	 * @param nomFichier
	 */
	public void addPersonne(String nomFichier) {

        try{
            personnes.add(exch.lireFichierICS(new File("fichiers"+File.separator+nomFichier)));

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier.");
        }
    }

    /**
     *
     * @return
     */
    public LinkedList<Personne> getPersonnes() {
        return personnes;
    }
}
