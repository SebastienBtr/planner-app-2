package modele;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Calendrier {

	private HashMap<LocalDate,CreneauxJournee> journees;
	
	public Calendrier(){
		this.journees = new HashMap<>();
		this.journees.put(LocalDate.now(),new CreneauxJournee());
	}
	
	/**
	 * Cette m�thode permet d'affecter un evenement � une journ�e dans la hashmap journees 
	 * @param  date une date
	 * @param  e l'�v�nement � ajouter
	 * @return      un boolean true si l'�v�nement a bien �t� ajout� sinon false
	 */
	public boolean ajouterEvenement(LocalDate date,Evenement e){
		if (this.journees.get(date)==null){
			this.journees.put(date, new CreneauxJournee());
			this.journees.get(date).ajouterEvenement(e);
			return true;
		}
		return this.journees.get(date).ajouterEvenement(e);
	}
	
	
	public void supprimerEvenement(LocalDate date, Evenement e){
		//TODO implementer la m�thode
	}
	
	
	
	/**
	 * Cette m�thode permet de savoir si un creneau est libre a une certaine date
	 * @param  date une date
	 * @param  heure_debut l'heure du d�but du creneau
	 * @param  min_debut les minutes du d�but du creneau
	 * @return      un string correspondant au au d�gr� de disponiblit� (A,O,I) ou une chaine de caract�res vide s'il n'y pa d'�v�venement
	 */
	public String getDispo(LocalDate date,int heure_debut,int min_debut){
		if (this.journees.get(date)==null) return "";
		if (this.journees.get(date).getEvenement(heure_debut, min_debut)!=null){
			return this.journees.get(date).getEvenement(heure_debut, min_debut).getPriorite();
		}
		return "";
	}
	
	/**
	 * Cette m�thode permet d'ajouter une journ�e � la Hashmap journees
	 * @param  date une date
	 */
	public void ajouterJournee(LocalDate date){
		this.journees.put(date, new CreneauxJournee());
	}
	
	public String toString(){
		Iterator i = this.journees.entrySet().iterator();
		String str="";
		while(i.hasNext()){
	        Map.Entry pair = (Map.Entry)i.next();
	        str += (pair.getKey() + " = " + pair.getValue().toString())+ "\n";
		}
		return str;
	}
}
