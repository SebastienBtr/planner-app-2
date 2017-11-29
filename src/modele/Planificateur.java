package modele;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class Planificateur {

	/**
	 * Cette m�thode permet de r�cup�rer les disponiblit�s de plusieurs personne sur un m�me cr�neau
	 * @param  personnes une LinkedList de Personne
	 * @param  date la date
	 * @param  heure_debut l'heure du d�but du cr�neau
	 * @param  min_debut les minutes du d�but du cr�neau
	 * @return      renvoie une hashmap avec deux champs ("disponible" et "indisponibles") qui contiennent des personnes
	 */
	public HashMap<String,LinkedList<Personne>> getPersonnesDispo(LinkedList<Personne> personnes,LocalDate date, int heure_debut, int min_debut){
		HashMap<String,LinkedList<Personne>> personnesResultat = new HashMap<>();
		LinkedList<Personne> potentiellementLibres = new LinkedList<>();
		LinkedList<Personne> indisponibles = new LinkedList<>();
		for (Personne p:personnes){
			if (p.estLibre(date, heure_debut, min_debut)!=1){
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
}
