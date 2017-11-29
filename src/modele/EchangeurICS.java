package modele;

import java.io.BufferedReader;
import java.time.temporal.ChronoUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

public class EchangeurICS {
	 private final long MILLISECONDES_PAR_JOUR = 1000 * 60 * 60 * 24; 

	public EchangeurICS(){
		super();
	}

	/**
	 * Cette méthode permet de lire et de parser un fichier ICS afin de créer une personne avec son calendrier
	 * @param  fichier Un fichier ics
	 * @return      renvoie la personne à qui appartient le fichier ICS
	 */
	public Personne lireFichierICS(File fichier) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fichier));
		String ligne ="";
		String idPersonne = "";
		String nomPersonne = "";
		String prenomPersonne = "";
		LocalDate date_debut = LocalDate.now();
		int heure_debut= 0;
		int min_debut= 0;
		int heure_fin= 0;
		int min_fin = 0;
		LocalDate date_fin = LocalDate.now();
		String description ="";
		String titre ="";
		Personne p = new Personne();
		p.setId(fichier.getName().replace(".ics", ""));
		while ((ligne = br.readLine()) != null ) {
			
			if(ligne.contains("X-WR-RELCALID")){
				idPersonne = ligne.split("X-WR-RELCALID:PASS-")[1];
				
			}
			else if(ligne.contains("X-WR-CALNAME;VALUE=TEXT:")){
				String str = ligne.split("X-WR-CALNAME;VALUE=TEXT:")[1].split("'s PASS Schedule")[0];
				prenomPersonne = str.substring(str.lastIndexOf(" ")+1).trim();
				p.setPrenom(prenomPersonne);
				nomPersonne = str.split("\\s(\\w+)$")[0].trim();
				p.setNom(nomPersonne);
			}
			else if(ligne.contains("DTSTART:")){
				String[] nbs = ligne.split("\\D+");
				// cette regex permet de récupérer uniquement les caractères de type digital 
				int annee = Integer.parseInt(nbs[nbs.length-2].substring(0,4));
				int mois = Integer.parseInt(nbs[nbs.length-2].substring(4,6));
				int jour = Integer.parseInt(nbs[nbs.length-2].substring(6,8));
				date_debut = LocalDate.of(annee, mois, jour);
				heure_debut = Integer.parseInt(nbs[nbs.length-1].substring(0,2));
				min_debut = Integer.parseInt(nbs[nbs.length-1].substring(2,4));
			}
			else if(ligne.contains("DTEND:")){
				String[] nbs = ligne.split("\\D+");
				int annee = Integer.parseInt(nbs[nbs.length-2].substring(0,4));
				int mois = Integer.parseInt(nbs[nbs.length-2].substring(4,6));
				int jour = Integer.parseInt(nbs[nbs.length-2].substring(6,8));
				date_fin = LocalDate.of(annee, mois, jour);
				heure_fin = Integer.parseInt(nbs[nbs.length-1].substring(0,2));
				min_fin = Integer.parseInt(nbs[nbs.length-1].substring(0,2));

			}
			else if(ligne.contains("SUMMARY:")){
				titre= ligne.substring("SUMMARY:".length(), ligne.length());
			}
			else if(ligne.contains("DESCRIPTION:")){
				description = ligne.substring("DESCRIPTION:".length(), ligne.length());
			}
			else if (ligne.contains("END:VEVENT")){
				long nombre_jour = ChronoUnit.DAYS.between(date_debut, date_fin);
				for (int i=0;i<nombre_jour+1;i++){
						p.ajouterEvenement(date_debut.plusDays(i), new Evenement(heure_debut, min_debut, heure_fin, min_fin, description, titre));
				}
				heure_debut =0;
				min_debut=0;
				heure_fin=0;
				min_fin=0;
				description="";
				titre="";
			}
		}
		br.close();
		return p;
	}
	
}
