package modele;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;


public class Personne {

	private String id;
	private String nom;
	private String prenom;
	private Calendrier calendrier;
	private String addr;
	
	public Personne (String addr, String nom, String prenom) {
		this.id=nom+" "+prenom;
		this.addr=addr;
		this.nom=nom;
		this.prenom=prenom;
		this.calendrier=new Calendrier();
			
	}
	
	public Personne (){
		this.calendrier=new Calendrier();
		this.id="";
		this.nom="";
		this.prenom="";
	}

	/**
	 * Renvoie un booleen si les deux cr�neaux se chevauchent
	 *
	 * @param  new_creneau le creneau � ajouter � la liste de creneaux de la personne
	 * @param  importance degr� d'importance
	 * @return      renvoie un entier 0 ou 1 (0 si l'ajout s'est bien pass�, 1 si le creneau n'a pas pu �tre ajout� puisqu'il chevauche un autre cr�neau.
	 */
	public boolean ajouterEvenement(LocalDate date, Evenement e){
		if (Evenement.estEligible(e)) {
			return this.calendrier.ajouterEvenement(date, e);
		}
		return false;
	}
	
	/**
	 * Cette m�thode permet de savoir le degr� de disponibilit� de la personne
	 * @param  date la date
	 * @param  heure_debut l'heure du d�but du cr�neau
	 * @param  min_debut les minutes du d�but du cr�neau
	 * @return      renvoie un entier correspondant au degr� de disponiblit� A pour annulable, I pour informatif, O pour obligatoire sinon 0 s'il n'y a pas d'�v�nement
	 */
	public int estLibre(LocalDate date,int heure_debut,int min_debut){
		String dispo = this.calendrier.getDispo(date,heure_debut,min_debut);
		switch (dispo)
		{
		case "A":
		    return 1;
		case "I":
		    return 2;
		case "O":
		    return 3;
		  default:
		    return 0;             
		}
	}
	
	@Override
	public String toString() {
		return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom
				+ ", calendrier=" + this.calendrier.toString() + "]";
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}
	
	public String getId(){
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	

	
}
