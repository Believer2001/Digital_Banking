## Digital Banking

### Description du projet : 
On souhaite créer une application qui permet de gérer des comptes bancaires. chaque compte appartient à un client. 
Un compte peut subir plusieurs opérations de type DEBIT ou CREDIT. Il existe deux types de comptes : Comptes
courants et comptes épargnes.
 Le projet est composé de trois différentes parties qui sont : la partie Backend qui va être développée en utilisant 
 JEE Spring pour créer une API Restfull, une partie Frontend qui sera développé en utilisant ANGULAR et une dernière 
partie qui s'occupera de la sécurité le n l'application en gérant les authorisations et les authentifications. 

#### Partie 1 :Backend(Spring)
1. Créer un projet Spring Boot
2. Créer les entités JPA : Customer, BankAccount, Saving Account, CurrentAccount, AccountOperation
3. Créer les interfaces JPA Repository basées sur Spring Data
4. Tester la couche DAO
   5 Couche service, DTOs
6. RestController
7. Tester les web services Restful

#### Partie 2 : Frontend (Client Angular)

#### Partie 3 : (SpringSécurity)
Sécuriser l'application avec un système d'authentification basé sur Spring Security et Json Web Token

#### Les principales fonctionnalités
Ajouter des fonctionnalisés supplémentaires à votre projet comme :

- La gestion des clients (saisie, ajout, suppression, édition, recherche , etc.)
- La gestion des comptes (Ajout des comptes, recherche et administration des comptes)
- Pour chaque client, compte, opération enregistrée, il faut enregistrer avec l'enregistrement l'identifiant de l'utilisateur authentifié qui a effectué l'opération
- Gestion des comptes et des mot de passes des utilisateurs avec la possibilité qu'un utilisateur change son mot de passe
- Partie Dash Board : En utilisant ChartJS (ng-chart), créer la partie dashboard de l'application montrant des graphiques et des statistiques utiles pour les prises de décision
- Autres fonctionnalisés supplémentaires

#### Architecture du projet

![Architecture du projet]()

#### Diagramme de classe

![diagramme de classe]()

### Développement
 #### Partie 1:
1. Créer un projet Spring Boot

2. Création des entités JPA : Customer, BankAccount, Saving Account, CurrentAccount,
![entites](./captureEcran/entities.png)![](./captureEcran/basededonnee.png) 
3. Créer les interfaces JPA Repository basées sur Spring Data
![repository](./captureEcran/epositorry.png)
![donnee enntre](./captureEcran/donneeEntree.png) 
![donnee enntre](./captureEcran/donneeBankaccount.png)
![donnee entre dan operation](./captureEcran/donnebankoperation.png)

#### Nous allons utiliser la strategie table_per_classe
![table par classe](./captureEcran/tableperclss.png)

#### Nous alolons utiliser la strategie join table 
![table par classe](./captureEcran/joined.png)

 
#### La couche web 

![le test de la couche service ](./captureEcran/service.png)

#### Ajout de la couche DTO, le mapping de la couuche dto et de la  dao et le test de l api et ajout de swager pour es test
![get ](./captureEcran/getcust.png)
![get par id ](./captureEcran/getcust1.png)
![ pour eregistrer](./captureEcran/post.png)
![pour mettre a jour](./captureEcran/put.png)
![ pour faire la suppression](./captureEcran/delete.png)
 La documentation swagger et l interface de test 
![interface de test](./captureEcran/interfacetest.png)
![interface de docs](./captureEcran/interfacedocs.png)

![ historique de la page ](./captureEcran/testHistory.png)
![ historique de la page ](./captureEcran/size3.png)





