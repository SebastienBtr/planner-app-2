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

    /**
     * Créé un planificateur et initialise les personnes de l'application en lisant tous les fichiers du répertoire "fichiers"
     */
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

    /**
     * Cette méthode permet de récuperer la liste de Possibilite pour la planification d'une réunion
     * @param personnes la liste des personnes devant participer à la réunion
     * @param duree la duree de la réunion
     * @param debutPlage le debut de la plage dans laquelle la réunion doit avoir lieu
     * @param finPlage la fin de la plage dans laquelle la réunion doit commencer
     * @return la liste des Possibilite
     */
	public ArrayList<Possibilite>
    getPosibilite(LinkedList<Personne> personnes, int duree, LocalDateTime debutPlage, LocalDateTime finPlage ) {

        if (!correctParam(personnes, duree, debutPlage, finPlage)) {
            throw new IllegalArgumentException();
        }

        ArrayList<Possibilite> possibilites = new ArrayList<>();
        int i = 0;

        while (!debutPlage.isEqual(finPlage)) {

            Possibilite newPossibilite = null;
            int creneau = 0;

            while (creneau != duree) {

                Possibilite tmpPossibilite = new Possibilite(debutPlage,debutPlage.plusMinutes(15),
                        getPersonnesDispo(personnes,debutPlage.toLocalDate(),debutPlage.plusHours((int)((debutPlage.getMinute() + creneau)/60)).getHour(),debutPlage.plusMinutes(creneau).getMinute()));

                if (newPossibilite != null) {
                    newPossibilite = newPossibilite.concat(tmpPossibilite);
                }
                else {
                    newPossibilite = tmpPossibilite;
                }
                creneau += 15;
            }

            if (i > 0 && possibilites.get(possibilites.size()-1).estEgal(newPossibilite)) {
                newPossibilite = possibilites.get(possibilites.size()-1).concat(newPossibilite);
                possibilites.set(possibilites.size()-1, newPossibilite);
            }
            else {
                possibilites.add(newPossibilite);
            }

            debutPlage = incrementDate(debutPlage,duree);
            i++;
        }

        return possibilites;
    }

    /**
     * Méthode pour vérifier que tous les paramètre utilisé à la méthode getPossibilite sont correct
     * @param personnes la liste de personnes qui participent à la réunion (ne dois pas être vide)
     * @param duree la duree de la réunion (doit être supérieur à 0 et modulo 15 et inférieur à 4h)
     * @param debutPlage le debut de la plage dans laquelle la réunion doit commencer (ne doit pas être null
     *                   et doit être entre 8h et 20h moins la duree)
     * @param finPlage la fin de la plage dans laquelle la réunion doit commencer (doit etre après le debut et entre 8h et 20h moins la duree)
     * @return true si les parametre sont correct
     */
    private boolean correctParam(LinkedList<Personne> personnes, int duree, LocalDateTime debutPlage, LocalDateTime finPlage) {
        //TODO verif param
        return true;
    }

    /**
     * Méthode pour incrementer une date de 15min et passer au jour suivant si la date depasse 20h moins la durée
     * @param date la date à incrementer
     * @param duree la durée de la réunion
     * @return la nouvelle date calculé
     */
    private LocalDateTime incrementDate(LocalDateTime date, int duree) {
        LocalTime time = LocalTime.of(20,00);
        int heures = (int)(duree / 60);
        int minutes = duree - 60*heures;
        time = time.minusHours(heures);
        time = time.minusMinutes(minutes);
        LocalDateTime limitTime = LocalDateTime.of(date.toLocalDate(), time);

        if (date.plusMinutes(15).isAfter(limitTime)) {
            LocalTime heureDebutJournee = LocalTime.of(8,00);
            LocalDateTime newDate = LocalDateTime.of(date.toLocalDate().plusDays(1),heureDebutJournee);
            return newDate;
        }

        return date.plusMinutes(15);
    }

	/**
	 * Méthode pour ajouter une personne à l'application avec ses événement
	 * @param nomFichier le fichier ics des événement de la personne
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
     * @return la liste des personnes
     */
    public LinkedList<Personne> getPersonnes() {
        return personnes;
    }
}
