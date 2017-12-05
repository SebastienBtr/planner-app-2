package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import modele.*;

public class PlanificateurApplication {

	public static void main(String[] args) {
		boolean quitter = false;
		Planificateur plan = new Planificateur();
		LinkedList<Personne> personnes = plan.getPersonnes();
		EchangeurICS exch = new EchangeurICS();
		LinkedList<Evenement> evenements = new LinkedList<>();
		try {
			Scanner banniere = new Scanner(new File("img"+File.separator+"banniere.txt"));
			while (banniere.hasNextLine()){
				System.out.println(banniere.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner sc = new Scanner(System.in);
		String entreeClavier ="";
		int indice=0;
		while (quitter==false){
			System.out.println("Tapez une commande ou son alias en chiffre(help pour voir la liste des commandes):");
			entreeClavier = sc.nextLine().toLowerCase();
			
			switch (entreeClavier){
				case "help":
					getHelp();
					break;
				case "creerpersonne":
					creerPersonne(personnes, sc);
					break;
				case "1":
					creerPersonne(personnes, sc);
					break;
				case "lireics":
					lireICS(personnes, exch, sc);
					break;
				case "2":
					lireICS(personnes, exch, sc);
					break;
				case "creerevenement":
					creerEvenement(evenements, sc);
					break;
				case "3":
					creerEvenement(evenements, sc);
					break;
				case "attribuerevenement":
					attribuerEvenement(personnes, evenements, sc);
					break;
				case "4":
					attribuerEvenement(personnes, evenements, sc);
					break;
				case "disponible":
				disponible(personnes, sc);
					break;
				case "5":
				disponible(personnes, sc);
					break;
				case "disponibles":
				disponibles(plan, personnes, sc);
					break;
				case "6":
				disponibles(plan, personnes, sc);
					break;
				case "listepersonnes":
					listePersonnes(personnes);
					break;
				case "7":
					listePersonnes(personnes);
					break;
				case "consultercalendrierpersonne":
					consulterCalendrierPersonne(personnes, sc);
					break;
				case "8":
					consulterCalendrierPersonne(personnes, sc);
					break;
				case "listeevenements":
					listeEvenements(evenements);
					break;
				case "9":
					listeEvenements(evenements);
					break;
				case "quitter":
					System.exit(0);
					break;
				case "10":
					System.exit(0);
					break;
				default:
					System.out.println("La commande n'est pas reconnu, veuillez r�essayer svp.");
					break;
			}

		}

	}

	private static void listeEvenements(LinkedList<Evenement> evenements) {
		for (Evenement e:evenements){
			System.out.println(evenements.indexOf(e)+". "+e.getTitre()+" ("+e.getHeure_debut()+"h"+e.getMinute_debut()+"-"+e.getHeure_fin()+"h"+e.getMinute_fin()+"), importance: "+e.getPriorite());
		}
	}

	private static void listePersonnes(LinkedList<Personne> personnes) {
		for (Personne p:personnes){
			System.out.println(personnes.indexOf(p)+". "+p.getId());
		}
	}

	private static void consulterCalendrierPersonne(
			LinkedList<Personne> personnes, Scanner sc) {
		int indice;
		System.out.println("Bienvenue dans le menu d'affichage du calendrier d'une personne.");
		System.out.println("Veuillez choisir l'indice de la personne dont vous voulez consulter le calendrier:");
		listePersonnes(personnes);
		indice = Integer.parseInt(sc.nextLine());
		System.out.println(personnes.get(indice));
	}

	private static void disponibles(Planificateur plan,
			LinkedList<Personne> personnes, Scanner sc) {
		System.out.println("Veuillez choisir les personnes dont vous voulez savoir la disponibilit� (Ex: 1,2,3,7,9):");
		listePersonnes(personnes);
		String[] indicesPersonnes = sc.nextLine().split(",");
		LinkedList<Personne> personnesATester = new LinkedList<>();
		for (int i=0;i<indicesPersonnes.length;i++){
			personnesATester.add(personnes.get(i));
		}
		System.out.println("Veuillez indiquer la date pour laquelle vous voulez savoir sa disponiblit� (jj/mm/aaaa):");
		String dateString = sc.nextLine();
		LocalDate date = LocalDate.of(Integer.parseInt(dateString.split("/")[2]),Integer.parseInt(dateString.split("/")[1]),Integer.parseInt(dateString.split("/")[0]));
		System.out.println("Veuillez saisir l'heure de d�but du cr�neau (Exemple: 8:00) (/!\\ de 8h00 � 19h45) :");
		String entreeHeureDebut = sc.nextLine();
		int heure_debut = Integer.parseInt(entreeHeureDebut.split(":")[0]);
		int min_debut = Integer.parseInt(entreeHeureDebut.split(":")[1]);
		HashMap<String,LinkedList<Personne>> resultat = plan.getPersonnesDispo(personnesATester, date, heure_debut, min_debut);
		System.out.println("Personnes disponibles le "+date.toString()+" � "+heure_debut+"h"+min_debut+":");
		for (Personne p:resultat.get("disponibles")){
			System.out.println(p.getId());
		}
		System.out.println("Personnes indisponibles le "+date.toString()+" � "+heure_debut+"h"+min_debut+":");
		for (Personne p:resultat.get("indisponibles")){
			System.out.println(p.getId());
		}
	}

	private static void disponible(LinkedList<Personne> personnes, Scanner sc) {
		int indice;
		System.out.println("Bienvenue dans le menu de disponiblite d'une personne (uniquement pour un cr�neau de 15 minutes).");
		System.out.println("Veuillez choisir l'indice de la personne dotnt vous voulez savoir la disponiblit�:");
		listePersonnes(personnes);
		indice = Integer.parseInt(sc.nextLine());
		System.out.println("Veuillez indiquer la date pour laquelle vous voulez savoir sa disponiblit� (jj/mm/aaaa):");
		String dateString = sc.nextLine();
		LocalDate date = LocalDate.of(Integer.parseInt(dateString.split("/")[2]),Integer.parseInt(dateString.split("/")[1]),Integer.parseInt(dateString.split("/")[0]));
		System.out.println("Veuillez saisir l'heure de d�but du cr�neau (Exemple: 8:00) (/!\\ de 8h45 � 19h45) :");
		String entreeHeureDebut = sc.nextLine();
		int heure_debut = Integer.parseInt(entreeHeureDebut.split(":")[0]);
		int min_debut = Integer.parseInt(entreeHeureDebut.split(":")[1]);
		int dispo = personnes.get(indice).estLibre(date, heure_debut, min_debut);
		switch (dispo)
		{
		case 1:
			System.out.println(personnes.get(indice).getId()+" est potientiellement disponible. (Annulable)");
		    break;
		case 2:
			System.out.println(personnes.get(indice).getId()+" est potientiellement disponible. (Informatif)");
		    break;
		case 3:
			System.out.println(personnes.get(indice).getId()+" n'est pas disponible. (Obligatoire)");
		    break;
		  default:
			  System.out.println(personnes.get(indice).getId()+" est disponible.");
		    break;           
		}
	}

	private static void attribuerEvenement(LinkedList<Personne> personnes,
			LinkedList<Evenement> evenements, Scanner sc) {
		System.out.println("Bienvenue dans le menu d'attribution d'�venement � une personne.");
		System.out.println("Veuillez choisir l'indice de la personne � laquelle vous voulez associer un �venement dans la liste ci-dessous:");
		listePersonnes(personnes);
		int indicePersonne = Integer.parseInt(sc.nextLine());
		System.out.println("Veuillez choisir l'indice de l'�venementque vous voulez associer dans la liste ci-dessous:");
		listeEvenements(evenements);
		int indiceEvenement = Integer.parseInt(sc.nextLine());
		System.out.println("Veuillez indiquer la date de l'�venement (jj/mm/aaaa):");
		String dateString = sc.nextLine();
		LocalDate date = LocalDate.of(Integer.parseInt(dateString.split("/")[2]),Integer.parseInt(dateString.split("/")[1]),Integer.parseInt(dateString.split("/")[0]));
		boolean res = personnes.get(indicePersonne).ajouterEvenement(date, evenements.get(indiceEvenement));
		if (res){
			System.out.println("L'�venement a bien �t� ajout�.");
		}
		else{
			System.out.println("Erreur lors de l'ajout de l'�venement.");
		}
	}

	private static void creerEvenement(LinkedList<Evenement> evenements,
			Scanner sc) {
		System.out.println("Bienvenue dans le menu de cr�ation d'�venement.");
		System.out.println("Veuillez saisir le nom de l'�venement:");
		String nomEvent = sc.nextLine();
		System.out.println("Veuillez saisir l'heure de d�but du cr�neau (Exemple: 8:00) (/!\\ de 8h00 � 19h45) :");
		String entreeHeureDebut = sc.nextLine();
		int heure_debut = Integer.parseInt(entreeHeureDebut.split(":")[0]);
		int min_debut = Integer.parseInt(entreeHeureDebut.split(":")[1]);
		System.out.println("Veuillez saisir l'heure de fin du cr�neau (Exemple: 20:00) (/!\\ de 8h15 � 20h00) :");
		String entreeHeureFin = sc.nextLine();
		int heure_fin = Integer.parseInt(entreeHeureFin.split(":")[0]);
		int min_fin = Integer.parseInt(entreeHeureFin.split(":")[1]);
		System.out.println("Veuillez indiquer le niveau d'importance de l'�venement (\"A\" pour annulable, \"O\" pour obligatoire et \"I\" pour informatif):");
		String desc = sc.nextLine();
		System.out.println("Veuillez indiquer une description pour l'�venement:");
		desc = desc+" "+sc.nextLine();
		evenements.add(new Evenement(heure_debut,min_debut,heure_fin,min_fin, desc,nomEvent));
		System.out.println("L'�venement "+evenements.getLast().getTitre()+" a bien �t� cr��.");
	}

	private static void lireICS(LinkedList<Personne> personnes,
			EchangeurICS exch, Scanner sc) {
		System.out.println("Bienvenue dans le menu de lecture de fichier ICS.");
		System.out.println("Veuillez mettre votre fichier ics dans le r�pertoire \"fichiers\".");
		System.out.println("Veuillez rentrer le nom complet (Ex: ics1.ics) du fichier:");
		String nomFichier = sc.nextLine();
		try{
			personnes.add(exch.lireFichierICS(new File("fichiers"+File.separator+nomFichier)));
			System.out.println("La personne ["+personnes.getLast().getId()+"] a bien �t� cr��e.");

		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture du fichier.");
		}
	}

	private static void creerPersonne(LinkedList<Personne> personnes, Scanner sc) {
		System.out.println("Bienvenue dans le menu de cr�ation d'une Personne.");
		System.out.print("Adresse mail de la Personne: ");
		String addr = sc.nextLine();
		System.out.print("Nom de la Personne: ");
		String nom = sc.nextLine();
		System.out.print("Prenom de la Personne: ");
		String prenom = sc.nextLine();
		personnes.add(new Personne(addr, nom, prenom));
		System.out.println("La personne ["+personnes.getLast().getNom()+" "+personnes.getLast().getPrenom()+"] a bien �t� cr��e.");
	}

	private static void getHelp() {
		System.out.println("Liste des commandes:");
		System.out.println("1 - creerPersonne: Permet de cr�er une personne.");
		System.out.println("2 - lireICS: Permet de lire un fichier ICS.");
		System.out.println("3 - creerEvenement: Permet de cr�er un �v�nement.");
		System.out.println("4 - attribuerEvenement: Permet d'attribuer un �v�nement � une personne.");
		System.out.println("5 - disponible: Permet de savoir la disponiblit� d'une personne sur un cr�neau de 15 minutes.");
		System.out.println("6 - disponibles: Permet de savoir la disponiblit� de plusieurs personnes sur un cr�neau de 15 minutes.");
		System.out.println("7 - listePersonnes: Permet de lister l'ensemble des personnes existantes.");
		System.out.println("8 - consulterCalendrierPersonne: Permet de consulter l'emploi du temps d'une personne.");
		System.out.println("9 - listeEvenements: Permet de lister tous les �venement cr��s.");
		System.out.println("10 - quitter: Permet de quitter l'application.");
	}
	
	

}
