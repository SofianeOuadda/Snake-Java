## Présentation du Projet

Ce dépôt contient deux modules :

1. **snake** : L'application Java Swing pour jouer au Snake.
2. **server** : Une application Spring Boot fournissant des endpoints REST pour enregistrer et consulter les scores du jeu.

## Configuration

### Fichier `application.properties`

Le fichier `application.properties` du module `server` contient la configuration de la base de données et d'autres paramètres de l'application Spring Boot. Pour éviter de compromettre des informations sensibles, ce fichier est ignoré par Git. Un template est disponible (par exemple `application.template.properties`) :  
- Copiez ce template en `application.properties`
- Ajustez les paramètres (URL de la base, identifiants, etc.) selon vos besoins.

## Règles du Jeu

- **Pommes (Apple)** : font grandir votre serpent.
- **Brocolis (Broccoli)** : réduisent la taille du serpent ou n’ont aucun effet, selon l’espèce de serpent que vous contrôlez.

## Différentes Espèces de Serpents

Au démarrage, votre serpent est choisi aléatoirement parmi ces trois espèces :

- **Python**  
  - Manger une pomme ajoute **1 segment**.
  - Manger un brocoli retire **2 segments** (si possible).

- **Anaconda**  
  - Manger une pomme ajoute **2 segments**.
  - Manger un brocoli retire **1 segment** (si possible).

- **BoaConstrictor**  
  - Manger une pomme ajoute **3 segments**.
  - Manger un brocoli n’a **aucun effet**.

A chaque fois que le serpent mange le score augmente de +1 quelque soit l'objet mangé.

## Niveaux de Difficulté et Localisation des Fruits

Après chaque pomme ou brocoli mangé, la difficulté détermine comment les futurs fruits seront placés. Cette difficulté est sélectionnée **aléatoirement** parmi plusieurs stratégies, influençant la position des fruits dans la grille.

- **Stratégie Facile (Easy)**  
  Les fruits apparaissent à proximité de la tête du serpent.

- **Stratégie Aléatoire (Random)**  
  Les fruits sont placés de manière totalement aléatoire dans la grille.
  
- **Stratégie Difficile (Difficult)**  
  Les fruits sont initialement placés comme en mode random, mais ils sont **relocalisés aléatoirement toutes les 2secondes**.


## Lancer le jeu

1. **Cloner le dépôt** :

   ```git clone https://github.com/votre-compte/snakeInc.git```
   
3. ** Lancer le module snake :
   
   ```
   cd snakeInc
   ./gradlew :snake:run
   ```
  
5. **Lancer le module server** :
   
   ```
   ./gradlew :server:bootRun
   ```
   
## Tests d'integration

Deux tests d'intégration sont disponibles dans le module server. Ils vérifient l'API REST (création et récupération de scores) en utilisant une base H2 en mémoire. Sans entrer dans les détails, ces tests démontrent le bon fonctionnement de bout en bout du serveur.

Pour exécuter les tests : ```./gradlew test```

