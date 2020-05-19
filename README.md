# Projet application mobile : COVID-19 Data

## Description du projet

L'objectif de l'application est d'avoir accès simplement sur son téléphone aux données du COVID-19 de chaque pays du monde en temps réel.

L'application fournit à l'utilisateur le nombre de nouveaux cas, de morts et de personnes soignées pour chaque pays (248 avec îles comprises) ainsi qu'un récapitulatif des données du monde entier et à quel moment les données ont été actualisées (Date et heure).

## Consignes respectées 

* Affichage d'une liste dans un Recyclar View
* Détail d'un élement 
* Appel WebService à une API Rest : https://covid19api.com/
* Stockage de données en cache (SharedPreferences)
* Fonctions supplémentaires : 
  - Gitflow
  - Design (Harmonie des couleurs + ajout d'images + GIF)
  - Icone application personnalisé au nom de l'application (COVID-19) avec respect des couleurs 
  - Bouton cliquable permettant le filtrage de la liste des pays (Permet une recherche par pays ou par pattern de lettres)
  - L'application vérifie à chaque ouverture si le téléphone est connecté à internet, si oui elle actualise les données avec l'API sinon elle récupère les donnes en cache et averti l'utilisateur que les données ne sont pas à jour
  - Précise la date et l'heure de l'actualisation des données par l'API
  
## Fonctionnalités
  
  ### Ecran principal
  
  ![Ecran principal](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/EcranPrincipal.png)

  ### Fonction de recherche
  
  - Barre de recherche
  
  ![Barre de recherche](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/BarreRecherche.png)
 
  - Recherche par pays
  
  ![Recherche pays](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/RecherchePays.png)
  
  - Recherche par pattern
  
  ![Recherche pattern](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/RecherchePattern.png)
  
  ### Mise à jour des données en fonction de la connexion internet (Appel de l'API si le téléphone est connecté à internet, sinon on récupère le cache)
  
  ![Internet On](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/InternetOn.png) ![Internet Off](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/InternetOff.png)
 
  ### Icone de l'application sur Samsung Galaxy S7 Edge
  
  ![Icone application](https://github.com/Pragma-dev/Project_ESIEA/blob/master/images_readme/IconeApplication.jpg)

