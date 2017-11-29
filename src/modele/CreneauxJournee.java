package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class CreneauxJournee {
	
	private HashMap<Integer,Evenement> creneaux;
	private String[] tabAssociatifHeures;

	
	public CreneauxJournee(){
		this.creneaux = new HashMap<>();

		this.tabAssociatifHeures = new String[49]; //creation d'un tableau associatif pour que chaque creneau corresponde à une heure
		// Exemple: la premiere entrée de la hashmap correspondra à 8:00
		// Cela sert pour l'affichage
		for (int i=8;i<20;i++){
			for (int j=0;j<60;j=j+15){
				if (j==0){
					this.tabAssociatifHeures[(i-8)*4+j/15]=i+":"+j+"0";	
				}
				else{
					this.tabAssociatifHeures[(i-8)*4+j/15]=i+":"+j;
				}
				
			}
		}
		this.tabAssociatifHeures[48] = "20:00";
	}
	
	/**
	 * Cette méthode permet de calculer le hash de départ d'un évènement
	 * le hash correspond au nombre de quart d'heure écoulé à partir de 8h
	 * donc le premier sera égal à 0 si l'évenement commence à 8h
	 * @param  e  l'évènement concerné
	 * @return      le hash calculé
	 */
	public int calculerHashDepart(Evenement e){
		return (e.getHeure_debut()-8)*4+(e.getMinute_debut()/15);
		
	}
	
	/**
	 * Cette méthode permet de récupérer l'évènement associé à un créneau
	 * @param  heure l'heure du début du créneau
	 * @param  min les minutes du début du créneau
	 * @return      renvoie l'evenement du creneau concerné
	 */
	public Evenement getEvenement(int heure, int min) throws IllegalArgumentException{
		if (heure<8 || heure>20 || min>59){
			throw new IllegalArgumentException();
		}
		return this.creneaux.get((heure-8)*4+(min/15));
	}
	
	/**
	 * Cette méthode permet d'ajouter un évènement sur un ou plusieurs créneaux
	 * @param  e l'évènement à ajouter
	 * @return      renvoie un booléen true si l'évènement a pu être ajouté sinon false
	 */
	public boolean ajouterEvenement(Evenement e){
		boolean flag=false;
		int hash = this.calculerHashDepart(e); // on calcul le hash de départ de l'évenement (l'heure à laquelle il commence)
		LinkedList<Integer> evenementsASupprimer = new LinkedList<>();
		for (int i=0;i<e.getNbCreneaux();i++){
			// Cete boucle sert à parcourir les créneau de la journée qui seraient pris par l'évènement
			// cela permet de voir si un autre évènement est déjà présent
			if (contientEvenement(i, hash)){
				flag=true;
				// on ne pourra pas ajouter un évènement si il y en a déjà obligatoire sur un  des créneaux
			}
			//TODO a implementer, ou pas?
//			if (this.creneaux.get(hash+i)!=null && this.creneaux.get(hash+i).getPriorite().equals("A")){
//				evenementsASupprimer.add(hash+i);
//			}
		}
		if (flag==false){
//			this.dissocierEvenements(evenementsASupprimer);//supprime les evenements de type A qui avait des horaires en commun avec l'evenement ajoute
			for (int i=0;i<e.getNbCreneaux();i++){
				
				this.creneaux.put(hash+i, e);

			}
		}
		return !(flag);

	}

	private boolean contientEvenement(int i, int hash) {
		return this.creneaux.get(hash+i)!=null && this.creneaux.get(hash+i).getPriorite().equals("O");
	}
	
	

	/**
	 * Cette méthode permet de dissocier un évènement d'un ou plusieurs créneaux
	 * @param  asupprimer une arraylist d'entier qui contient les hash des différents créneaux concernés
	 */
	public void dissocierEvenements(ArrayList<Integer> asupprimer){
		for (Integer i:asupprimer){
			this.creneaux.remove(i);
		}
	}
	
	public String toString(){
		Iterator i = this.creneaux.entrySet().iterator();
		String str="";
		while(i.hasNext()){
	        Map.Entry pair = (Map.Entry)i.next();
	        str += (this.tabAssociatifHeures[(int) pair.getKey()] + " = " + pair.getValue().toString())+ "\n";
		}
		return str;
	}

}
