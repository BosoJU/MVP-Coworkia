# Coworkia – MVP 
Application de réservation d’espaces de coworking. Les utilisateurs peuvent consulter les zones disponibles et réserver un créneau. Les administrateur ont les mêmes droits que les utilisateurs et ont en plus un accès à l’historique global des réservations. 
## Stack technique : 
- **Backend**: Java 21, Spring Boot, Spring Security (authentification par token JWT), Spring Data JPA
- **Frontend**: Angular
- **Base de données**: H2 (en mémoire, réinitialisation à chaque démarrage)
## Cloner le projet : 
### Backend
Ouvrir le projet à l’aide de l’URL du repository sur un IDE (IntelliJ)
Fichier --> New --> From version control

URL : https://github.com/BosoJU/MVP-Coworkia.git

Load Maven à l’aide de la notification, ou de l’onglet Maven sur la droite sur IntelliJ et de l’icône de double flèche en cercle.

### Frontend 
Ouvrir le dossier frontend présent dans le dossier cloné précédemment

## Prérequis : 
- Java 21 minimum et recommandé
- Node.js et npm
- Angular CLI

**Depuis la racine du projet backend ou frontend**
  
|Pour vérifier ce qui est déjà installé sur votre machine | Pour installer les package nécessaires |
|---|---|
|java -version| Télécharger un JDK 21 (recommandé) ou plus https://www.oracle.com/fr/java/technologies/downloads/#java26 |
|node -v|Installer Node.js depuis https://nodejs.org/fr/download|
|ng version|Dans un terminal Powershell: npm install -g @angular/cli|
|npm -version | Puis npm install|
## Lancer le projet
Lancer la classe **BackendApplication.java** pour le backend. 

Depuis le dossier frontend ouvert dans VS Code, dans un terminal powershell lancer la commande : ng serve -o

Le serveur s’ouvre automatiquement sur http://localhost:4200

## Comptes de test
|Rôle |Email |Mot de passe|
|---|---|---|
|Utilisateur|user@coworkia.fr|user1234|
|Administrateur|admin@coworkia.fr|admin1234|

## Jeu de données 
La base H2 est non persistante, elle se relance à chaque démarrage avec les données insérées via data.sql. Ce fichier contient notamment 5 réservations sur la zone Hélios dont la capacité maximum est de 5, du 25/09/2026 au 25/10/2026, afin de pouvoir observer directement le comportement de l’application face à une zone dont la capacité est complète (tentative de réservation refusée). 
On retrouve aussi des réservations passées afin d’afficher un historique complet, avec des réservations futures et passées. 

## Accessibilité (RGAA/WCAG) 
|Critère |MVP concerné ? |Mise en oeuvre|
|---|---|---|
|Navigation au clavier|Oui| Pas besoin de mise en œuvre, l’utilisation des éléments HTML natifs permettent le déplacement au clavier. Testé, déplacement au clavier (tab) fonctionnel sur toutes les pages. |
|Contrastes suffisants|Oui|Couleur vérifiées et ajustées avec colo.adobe.com|
|Compatibilité technologies d’assistance|Oui|id ajouté sur les champs email/mot de passe pour les associer à leur label, role= "alert" sur le message d’erreur, role= "status" sur le message de confirmation|
|Redimensionnement des textes|Oui|Unités rem utilisées plutôt que des px fixes, il est possible de zommer via le navigateur sans casser l'interface|
|Affichage non contraint|Non|Application desktop uniquement, pas de contraintes d’orientation mobiles|
|Navigation cohérente|Oui|Navigation identique et au même emplacement sur toutes pages, nombre de liens faible|
|Couleur seule ne suffit pas|Oui|La couleur seule ne transmet jamais l’information|
|Images lisibles|Non|Aucune image dans l’application|
|Sous-titres pour contenu audio|Non|Aucun contenu audio ni vidéo dans l’application|
 

