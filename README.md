Event Reservation Platform

Application web full stack de création, de gestion et de réservation d’événements.

Le projet associe une API REST sécurisée développée avec Spring Boot, une interface utilisateur Vue 3 et une base de données PostgreSQL. Il permet aux utilisateurs de créer un compte, publier des événements, s’y inscrire, se désinscrire et gérer leurs favoris.

Fonctionnalités

Création de compte et authentification par JWT

Connexion et gestion du profil utilisateur

Consultation des événements à venir

Affichage du détail d’un événement

Création, modification et suppression d’événements

Inscription et désinscription à un événement

Consultation des participants

Ajout et retrait d’événements dans les favoris

Statistiques sur les événements

Documentation interactive de l’API avec Swagger UI

Collection Postman fournie pour tester les principaux scénarios

Technologies utilisées

Backend

Java 21

Spring Boot 4.0.5

Spring Web MVC

Spring Security

Spring Data JPA

Hibernate

PostgreSQL

JWT avec jjwt 0.11.5

SpringDoc OpenAPI / Swagger

Maven Wrapper

JUnit 5

Mockito

JaCoCo

Frontend

Vue 3.5

TypeScript 5.9

Vite 8

Vue Router

Pinia

Axios

Tailwind CSS

Lucide Vue Next

date-fns

Infrastructure

Docker

Docker Compose

PostgreSQL 15 Alpine

GitHub Actions

Architecture

event-reservation-platform/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/e11even/backend/
│   │   │   │   ├── config/
│   │   │   │   ├── controllers/
│   │   │   │   ├── dto/
│   │   │   │   ├── models/
│   │   │   │   ├── repositories/
│   │   │   │   ├── security/
│   │   │   │   └── services/
│   │   │   └── resources/
│   │   └── test/
│   ├── Dockerfile
│   ├── pom.xml
│   └── mvnw
├── database/
│   └── code.sql
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── router/
│   │   ├── services/
│   │   ├── stores/
│   │   ├── types/
│   │   ├── utils/
│   │   └── views/
│   ├── package.json
│   └── vite.config.ts
├── .env.example
├── .gitignore
├── docker-compose.yml
├── postman_collection.json
└── README.md

Prérequis

Pour exécuter l’ensemble du projet :

Git

Docker

Docker Compose

Node.js 20 ou une version plus récente

npm 10 ou une version plus récente

Java et Maven ne sont pas obligatoires pour un lancement avec Docker. Ils sont nécessaires pour exécuter directement le backend hors conteneur.

Vérification des outils :

git --version
docker --version
docker-compose --version
node --version
npm --version
java -version

Selon l’installation de Docker, la commande disponible peut être :

docker-compose

ou :

docker compose

Les exemples suivants utilisent docker-compose.

Installation

1. Cloner le dépôt

git clone git@github.com:miloud69009/event-reservation-platform.git
cd event-reservation-platform

2. Préparer les variables d’environnement

Copier le fichier d’exemple :

cp .env.example .env

Contenu attendu :

POSTGRES_USER=postgres
POSTGRES_PASSWORD=change-me-local
POSTGRES_DB=gp2m1

APP_JWT_SECRET=change-me-local-jwt-secret-at-least-32-characters
APP_JWT_EXPIRATION_MS=86400000

VITE_API_URL=http://localhost:8080/api

Le fichier .env est ignoré par Git et ne doit pas être publié.

Pour une utilisation autre qu’une démonstration locale, remplacez le mot de passe PostgreSQL et la clé JWT par des valeurs robustes.

Lancement avec Docker

Le fichier docker-compose.yml démarre :

la base de données PostgreSQL ;

l’API Spring Boot.

Le frontend Vue est lancé séparément avec npm.

1. Démarrer PostgreSQL et le backend

À la racine du projet :

docker-compose up --build -d

Vérifier l’état des conteneurs :

docker-compose ps

Afficher les logs du backend :

docker-compose logs -f api

Afficher les logs de PostgreSQL :

docker-compose logs -f db

Le backend communique avec PostgreSQL dans le réseau Docker sur :

db:5432

Depuis l’ordinateur hôte, PostgreSQL est accessible sur :

localhost:5433

2. Démarrer le frontend

Dans un autre terminal :

cd frontend
npm ci
npm run dev

L’URL de l’API utilisée par défaut est :

http://localhost:8080/api

Pour la modifier localement, créer un fichier frontend/.env.local :

VITE_API_URL=http://localhost:8080/api

3. Accéder à l’application

Frontend : http://localhost:5173

Backend : http://localhost:8080

Swagger UI : http://localhost:8080/swagger-ui/index.html

Documentation OpenAPI : http://localhost:8080/v3/api-docs

PostgreSQL depuis l’hôte : localhost:5433

4. Arrêter l’application

docker-compose down

Pour supprimer également le volume PostgreSQL et recréer complètement la base au prochain démarrage :

docker-compose down -v

Attention : cette dernière commande supprime les données enregistrées dans la base Docker.

Lancement sans Docker

1. Préparer PostgreSQL

Créer une base nommée gp2m1, puis exécuter le script :

psql -U postgres -c "CREATE DATABASE gp2m1;"
psql -U postgres -d gp2m1 -f database/code.sql

2. Configurer les variables du backend

Exemple :

export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/gp2m1
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=change-me-local
export SPRING_JPA_HIBERNATE_DDL_AUTO=update
export APP_JWT_SECRET=change-me-local-jwt-secret-at-least-32-characters
export APP_JWT_EXPIRATION_MS=86400000

3. Lancer le backend

cd backend
chmod +x mvnw
./mvnw spring-boot:run

4. Lancer le frontend

Dans un autre terminal :

cd frontend
npm ci
npm run dev

Principaux endpoints de l’API

Authentification

Méthode

Endpoint

Description

POST

/api/auth/register

Créer un compte

POST

/api/auth/login

Se connecter

Événements

Méthode

Endpoint

Description

GET

/api/events

Récupérer les événements

GET

/api/events/{id}

Récupérer un événement

GET

/api/events/stats

Récupérer les statistiques

POST

/api/events

Créer un événement

PUT

/api/events/{id}

Modifier un événement

DELETE

/api/events/{id}

Supprimer un événement

POST

/api/events/{id}/register

S’inscrire à un événement

DELETE

/api/events/{id}/register

Se désinscrire

POST

/api/events/{id}/like

Ajouter aux favoris

DELETE

/api/events/{id}/like

Retirer des favoris

GET

/api/events/{id}/registrations

Consulter les participants

Utilisateur

Méthode

Endpoint

Description

GET

/api/users/me

Récupérer le profil connecté

PUT

/api/users/me

Modifier le profil

GET

/api/users/me/registrations

Consulter ses participations

GET

/api/users/me/likes

Consulter ses favoris

PUT

/api/users/me/email

Modifier son adresse électronique

PUT

/api/users/me/password

Modifier son mot de passe

DELETE

/api/users/me

Supprimer son compte

Les endpoints privés nécessitent un jeton JWT envoyé dans l’en-tête :

Authorization: Bearer <token>

Tests et qualité

Backend

Lancer tous les tests :

cd backend
./mvnw test

Résultat vérifié sur le projet :

87 tests exécutés

0 échec

0 erreur

build Maven réussi

Générer le rapport JaCoCo :

./mvnw verify

Le rapport est généré dans :

backend/target/site/jacoco/index.html

Frontend

Installer exactement les versions du fichier package-lock.json :

cd frontend
npm ci

Vérifier le typage TypeScript et produire le build de production :

npm run build

Le projet ne contient pas encore de suite de tests automatisés pour le frontend. Le contrôle actuellement utilisé est le build Vue/TypeScript avec Vite.

Le dossier produit est :

frontend/dist/

Collection Postman

Le fichier suivant est fourni à la racine :

postman_collection.json

Il peut être importé dans Postman pour tester les scénarios d’authentification, de gestion d’événements, d’inscription et de désinscription.

L’URL de base locale est :

http://localhost:8080

Sécurité

Authentification sans session avec JWT

Hachage des mots de passe avec BCrypt

Protection des endpoints privés avec Spring Security

Validation du jeton JWT sur les requêtes authentifiées

Configuration CORS limitée au frontend local

Secrets configurés par variables d’environnement

Fichier .env exclu du dépôt Git

Images Docker séparant la phase de compilation et l’exécution du backend

Les valeurs présentes dans .env.example sont uniquement destinées au développement local.

Commandes utiles

# Démarrer le backend et PostgreSQL
docker-compose up --build -d

# Vérifier les conteneurs
docker-compose ps

# Suivre les logs
docker-compose logs -f

# Arrêter les conteneurs
docker-compose down

# Tests backend
cd backend
./mvnw test

# Build backend
./mvnw clean package

# Lancer le backend sans Docker
./mvnw spring-boot:run

# Installer le frontend
cd ../frontend
npm ci

# Lancer le frontend
npm run dev

# Build frontend
npm run build

# Prévisualiser le build
npm run preview

État du projet

La version actuelle est fonctionnelle pour une démonstration locale et comprend :

une API Spring Boot sécurisée ;

une interface Vue 3 et TypeScript ;

une base PostgreSQL initialisée avec des données de démonstration ;

une configuration Docker Compose ;

une documentation Swagger ;

une collection Postman ;

des tests automatisés backend ;

un build frontend validé.

Améliorations possibles :

ajout de tests automatisés frontend ;

conteneurisation du frontend ;

déploiement sur une plateforme publique ;

gestion centralisée des secrets en production ;

ajout d’une capture d’écran de l’application.