package modele;

import java.util.LinkedList;

public class Evenement {

	private int heure_debut;
	private int minute_debut;
	private int heure_fin;
	private int minute_fin;
	private String description;
	private String titre;
	private int nbCreneaux;

	public Evenement(int heure_debut, int minute_debut, int heure_fin, int minute_fin, String description,String titre){
		this.heure_debut = heure_debut;
		this.minute_debut = minute_debut;
		this.heure_fin = heure_fin;
		this.minute_fin = minute_fin;
		this.description = description;
		this.titre=titre;
		
		int min=(this.getHeure_debut()-8)*4+(this.getMinute_debut()/15);
		int max=(this.getHeure_fin()-8)*4+(this.getMinute_fin()/15);
		
		this.nbCreneaux=max-min;

	}
	
	/**
	 * Renvoie un booleen (true) si les deux créneaux se chevauchent sinon (false)
	 *
	 * @param  e le deuxième evenement à comparer
	 * @return      renvoie un booleen
	 */
	public boolean inter(Evenement e){
		if (this.heure_debut==e.heure_debut){
			if (this.minute_debut==e.minute_debut){
				return false; //même horaires de début donc impossible
			}

			else{// même heure de départ mais vérification que les minutes ne se chevauchent pas
				return !(this.minute_debut<e.minute_debut && this.minute_fin<=e.minute_debut)
						|| (e.minute_debut<this.minute_debut && e.minute_fin<=this.minute_fin);
			}
			
		}
		else if(this.heure_debut<e.heure_debut){
			return !(this.heure_fin<=e.heure_debut && this.minute_fin<=e.minute_debut);
		}
		else{
			return !(e.heure_fin<=this.heure_debut && e.minute_fin<=this.minute_debut);
		}
		
	}
	
	public String getPriorite(){
		if(this.description.length()==0){
			return "A";
		}
		String s =  this.description.substring(0, 1);
		return s;
	}

	@Override
	public String toString() {
		return this.titre+" ("+this.getPriorite()+")";
	}

	public int getHeure_debut() {
		return heure_debut;
	}

	public int getMinute_debut() {
		return minute_debut;
	}

	public int getHeure_fin() {
		return heure_fin;
	}

	public int getMinute_fin() {
		return minute_fin;
	}

	public String getDescription() {
		return description;
	}

	public String getTitre() {
		return titre;
	}

	public int getNbCreneaux() {
		return nbCreneaux;
	}
	
	public boolean equals(Evenement e){
		return this.titre.equals(e.titre);
	}
	public static boolean estEligible(Evenement e){
		if (e.getHeure_debut()>=8 && e.getHeure_debut()<20){
			if (e.getHeure_fin()==e.getHeure_debut()){
				return  e.formatMinutesCorrect() && e.getMinute_debut()<e.getMinute_fin();
			}
			if (e.getHeure_fin()>e.getHeure_debut()){
				if (e.getHeure_fin()==20){
					return e.formatMinutesCorrect() && e.getMinute_fin()==00;
				}
				else{
					return e.formatMinutesCorrect() && e.getHeure_fin()<20;
				}
				
			}
		}
		return false;
	}
	private boolean formatMinutesCorrect(){
		return this.getMinute_fin()==00 ||this.getMinute_fin()==15 ||this.getMinute_fin()==30 ||this.getMinute_fin()==45 && this.getMinute_debut()==0 || this.getMinute_debut()==15 || this.getMinute_debut()==30 || this.getMinute_debut()==45;
	}
}
