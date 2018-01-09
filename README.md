# planner-app

Projet Java de planification de réunion.

## Objectif

Le but du projet est de réaliser un outil d'aide à la détermination des créneaux propices à une réunion connaissant la liste des participants et leurs emplois du temps.

## Documentation

Pour exécuter le projet il faut lancer le main de la classe PlannerFrame.

## Spécification

Aucune fonctionnalité réseau n'est implémenté dans le projet, l'ajout des emplois du temps des utilisateurs ce fait via le dépot du ficher ics du calendrier dans le répertoire "fichiers" du projet.

Chaque évènement dispose d'un niveau d'importance qui doit être renseigné dans le déscription de l'évènement :

* O : un évènement obligatoire
* A : un évènement annulable
* I : un évènement informatif

L'unité de temps est le quart d'heure (il est impossible de planifier une réunion à 9h50 par exemple). Les réunions ne peuvent avoir lieu qu'entre 8h et 20h.

