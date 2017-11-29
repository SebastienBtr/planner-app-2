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
	 * Cette méthode permet d'affecter un evenement à une journée dans la hashmap journees 
	 * @param  date une date
	 * @param  e l'évènement à ajouter
	 * @return      un boolean true si l'évènement a bien été ajouté sinon false
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
		//TODO implementer la méthode
	}
	
	
	
	/**
	 * Cette méthode permet de savoir si un creneau est libre a une certaine date
	 * @param  date une date
	 * @param  heure_debut l'heure du début du creneau
	 * @param  min_debut les minutes du début du creneau
	 * @return      un string correspondant au au dégré de disponiblité (A,O,I) ou une chaine de caractères vide s'il n'y pa d'évèvenement
	 */
	public String getDispo(LocalDate date,int heure_debut,int min_debut){
		if (this.journees.get(date)==null) return "";
		if (this.journees.get(date).getEvenement(heure_debut, min_debut)!=null){
			return this.journees.get(date).getEvenement(heure_debut, min_debut).getPriorite();
		}
		return "";
	}
	
	/**
	 * Cette méthode permet d'ajouter une journée à la Hashmap journees
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
