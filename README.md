# Projet application mobile : COVID-19 Data

## Description du projet

L'objectif de l'application est d'avoir accès simplement sur son téléphone aux données du COVID-19 de chaque pays du monde en temps réel.

L'application fournit à l'utilisateur le nombre de nouveaux cas, de morts et de personnes soignées pour chaque pays (248 avec îles comprises) ainsi qu'un récapitulatif des données du monde entier et à quel moment les données ont été actualisées (Date et heure).

## Consignes respectées 

* Ecran 1 : Affichage d'une liste dans un Recyclar View
* Ecran 2 : Affichage du détail d'un élement
* Appel WebService à une API Rest : https://covid19api.com/
* Stockage de données en cache (SharedPreferences)
* Fonctions supplémentaires : 
  - Gitflow
  - MVC
  - Singleton
  - Design Patterns
  - Principe SOLID
  - Design (Harmonie des couleurs + ajout d'images + GIF)
  - Icone application personnalisé au nom de l'application (COVID-19) avec respect des couleurs 
  - Bouton cliquable permettant le filtrage de la liste des pays (Permet une recherche par pays ou par pattern de lettres)
  - Bouton permettant le retour sur le MainActivity à partir du DetailActivity 
  - L'application vérifie à chaque ouverture et passage dans le MainActivity si le téléphone est connecté à internet, si oui elle           actualise les données avec l'API sinon elle récupère les données en cache et averti l'utilisateur que les données ne sont pas à jour
  - Précise la date et l'heure de l'actualisation des données par l'API
  
## Fonctionnalités
  
  ### Ecran principal
  
  ![Ecran principal](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/Principal.png)

  ### Fonction de recherche
  
  - Barre de recherche
  
  ![Barre de recherche](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/Recherche.png)
 
  - Recherche par pays
  
  ![Recherche pays](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/Name.png)
  
  - Recherche par pattern
  
  ![Recherche pattern](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/pattern.png)
  
  ### Mise à jour des données en fonction de la connexion internet (Appel de l'API si le téléphone est connecté à internet, sinon on récupère le cache)
  
  ![Internet On](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/InternetOn.png) ![Internet Off](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/InternetOff.png)
  
  ### Affichage des statistiques d'un pays après avoir cliqué dessus + bouton retour sur l'activité principal

  ![Ecran1](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/Ecran1.png) ![Ecran2](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/Ecran2.png)
  
 
  ### Icone de l'application sur Samsung Galaxy S7 Edge
  
  ![Icone application](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/IconeApplication.jpg)

