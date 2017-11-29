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

		this.tabAssociatifHeures = new String[49]; //creation d'un tableau associatif pour que chaque creneau corresponde � une heure
		// Exemple: la premiere entr�e de la hashmap correspondra � 8:00
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
	 * Cette m�thode permet de calculer le hash de d�part d'un �v�nement
	 * le hash correspond au nombre de quart d'heure �coul� � partir de 8h
	 * donc le premier sera �gal � 0 si l'�venement commence � 8h
	 * @param  e  l'�v�nement concern�
	 * @return      le hash calcul�
	 */
	public int calculerHashDepart(Evenement e){
		return (e.getHeure_debut()-8)*4+(e.getMinute_debut()/15);
		
	}
	
	/**
	 * Cette m�thode permet de r�cup�rer l'�v�nement associ� � un cr�neau
	 * @param  heure l'heure du d�but du cr�neau
	 * @param  min les minutes du d�but du cr�neau
	 * @return      renvoie l'evenement du creneau concern�
	 */
	public Evenement getEvenement(int heure, int min) throws IllegalArgumentException{
		if (heure<8 || heure>20 || min>59){
			throw new IllegalArgumentException();
		}
		return this.creneaux.get((heure-8)*4+(min/15));
	}
	
	/**
	 * Cette m�thode permet d'ajouter un �v�nement sur un ou plusieurs cr�neaux
	 * @param  e l'�v�nement � ajouter
	 * @return      renvoie un bool�en true si l'�v�nement a pu �tre ajout� sinon false
	 */
	public boolean ajouterEvenement(Evenement e){
		boolean flag=false;
		int hash = this.calculerHashDepart(e); // on calcul le hash de d�part de l'�venement (l'heure � laquelle il commence)
		LinkedList<Integer> evenementsASupprimer = new LinkedList<>();
		for (int i=0;i<e.getNbCreneaux();i++){
			// Cete boucle sert � parcourir les cr�neau de la journ�e qui seraient pris par l'�v�nement
			// cela permet de voir si un autre �v�nement est d�j� pr�sent
			if (contientEvenement(i, hash)){
				flag=true;
				// on ne pourra pas ajouter un �v�nement si il y en a d�j� obligatoire sur un  des cr�neaux
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
	 * Cette m�thode permet de dissocier un �v�nement d'un ou plusieurs cr�neaux
	 * @param  asupprimer une arraylist d'entier qui contient les hash des diff�rents cr�neaux concern�s
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
