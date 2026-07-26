# Event Reservation Platform

Application web full stack pour la gestion et le suivi d'événements (Meetup), avec un backend API sécurisé et une interface utilisateur moderne et réactive.

## Table des Matières

- [Dépendances](#dépendances)
- [Architecture](#architecture)
- [Build et Installation](#build-et-installation)
- [Lancement](#lancement)
- [VM de Démonstration](#vm-de-démonstration)
- [Configuration](#configuration)
- [Tests](#tests)

## Dépendances

### Backend

**Java 21** avec Apache Maven 3.8+

Dépendances Maven principales (voir `backend/pom.xml`):

- **Spring Boot 4.0.5**: framework web sécurisé
- **Spring Security**: authentification et authorization
- **Spring Data JPA**: persistance avec Hibernate
- **PostgreSQL Driver**: accès à la base de données
- **JWT (jjwt 0.11.5)**: tokens JWT pour l'authentification
- **Lombok**: génération de code (getters, setters, etc.)
- **SpringDoc OpenAPI**: documentation Swagger UI
- **JaCoCo**: couverture de tests (rapports de qualité)
- **JUnit 5**: tests unitaires
- **Mockito**: mocking pour les tests

### Frontend

**Node.js 20+** et **npm 10+**

Dépendances npm principales (voir `frontend/package.json`):

- **Vue 3.5.30**: framework frontend réactif
- **TypeScript 5.9**: typage statique
- **Vite 8.0.1**: bundler et dev server ultra-rapide
- **Vue Router 4.6.4**: routage côté client
- **Pinia 3.0.4**: gestion d'état centralisée
- **Axios 1.13.6**: client HTTP
- **Tailwind CSS 3.4.19**: framework CSS utilitaire
- **Lucide Vue Next 1.0.0**: icônes SVG
- **date-fns 4.1.0**: manipulation de dates

### Base de Données

- **PostgreSQL 15+** (ou PostgreSQL 15-alpine si Docker)

##  Architecture

```
gp2m1/
├── backend/                    # API Spring Boot
│   ├── src/
│   │   ├── main/java/          # Code source (controllers, services, models, etc.)
│   │   └── test/java/          # Tests JUnit + Mockito
│   ├── pom.xml                 # Dépendances Maven
│   └── mvnw / mvnw.cmd         # Maven Wrapper (sans installation Maven requise)
├── frontend/                   # Application Vue 3
│   ├── src/
│   │   ├── components/         # Composants réutilisables
│   │   ├── views/              # Pages (Home, Event Details, etc.)
│   │   ├── services/           # Appels API
│   │   ├── stores/             # Gestion Pinia
│   │   └── router/             # Configuration Vue Router
│   ├── package.json            # Dépendances npm
│   └── vite.config.ts          # Configuration Vite
├── database/
│   └── code.sql                # Script d'initialisation PostgreSQL
├── docker-compose.yml          # Orchestration conteneurs (backend + db)
├── postman_collection.json     # Collection de tests API
└── README.md                   # Cette documentation
```

## Build et Installation

### 1. Prérequis

Installer localement:

```bash
# Java 21
java -version
# Output: openjdk version "21.x.x"

# Node.js 20+
node --version npm --version
# Output: v20.x.x, 10.x.x

# Docker + Docker Compose (recommandé)
docker --version
docker compose version
```

### 2. Cloner le projet

```bash
git clone <repository-url>
cd event-reservation-platform
```

### 3. Build Backend

Le backend utilise Maven Wrapper (aucune installation Maven requise).

```bash
cd backend

# Compiler et packer
./mvnw clean package

# Ou juste compiler (sans packaging)
./mvnw clean compile
```

**Artifacts générés:**
- `backend/target/backend-0.0.1-SNAPSHOT.jar` (JAR exécutable)
- `backend/target/site/jacoco/` (rapport couverture de tests)
- `backend/target/surefire-reports/` (résultats tests)

### 4. Build Frontend

```bash
cd frontend

# Installer les dépendances
npm install

# Compiler et minifier
npm run build
```

**Artifacts générés:**
- `frontend/dist/` (dossier prêt pour production)

## Lancement

### Option 1: Docker Compose (Recommandé)

Lance l'API + PostgreSQL dans des conteneurs:

```bash
cd event-reservation-platform
docker compose up --build -d

# Vérifier l'état
docker compose ps

# Voir les logs
docker compose logs -f api
docker compose logs -f db
```

### Option 2: Local (sans Docker)

1. **Démarrer PostgreSQL** (local ou sur VM)

```bash
# Local: installer PostgreSQL et créer la base
psql -U postgres
CREATE DATABASE gp2m1;
\q
```

2. **Configurer le backend** (vérifier `backend/src/main/resources/application.properties`)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gp2m1
spring.datasource.username=postgres
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

3. **Lancer l'API**

```bash
cd backend
./mvnw spring-boot:run
# L'API démarre sur http://localhost:8080
```

4. **Lancer le frontend** (dans un autre terminal)

```bash
cd frontend
npm run dev
# Le frontend démarre sur http://localhost:5173
```

## Configuration

### Variables d'environnement

#### Backend

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/gp2m1
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=change-me-local
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

#### Frontend

```bash
VITE_API_URL=http://localhost:8080/api
```

### Fichiers de configuration

- **Backend:** `backend/src/main/resources/application.properties`
- **Frontend:** `frontend/vite.config.ts`, `frontend/tsconfig.json`
- **Docker:** `docker-compose.yml`

## Tests

### Tests Backend (JUnit + Mockito)

```bash
cd backend

# Lancer tous les tests
./mvnw test

# Lancer un test spécifique
./mvnw test -Dtest=EventControllerTest

# Générer rapport de couverture JaCoCo
./mvnw verify
```

**Couverture attendue:** rapport dans `backend/target/site/jacoco/index.html`

### Tests Frontend

```bash
cd frontend

# Tests (non configurés actuellement, mais infrastructure Vite en place)
npm run test
```

## Authentification

- Tokens JWT générés lors du login
- Stockés en `localStorage` côté frontend
- Envoyés dans l'en-tête `Authorization: Bearer <token>` pour les endpoints privés

## Postman Collection

Une collection Postman est fournie: `postman_collection.json`

- Importe-la dans Postman
- Teste les endpoints et scénarios complets

## Commandes Utiles

```bash
# Backend
cd backend
./mvnw clean compile          # Compiler
./mvnw test                   # Tests
./mvnw spring-boot:run        # Lancer l'API
./mvnw verify                 # Tests + rapports

# Frontend
cd frontend
npm install                   # Installer dépendances
npm run dev                   # Dev server
npm run build                 # Production build
npm run preview               # Préview du build

# Docker
docker compose up --build -d  # Démarrer avec rebuild
docker compose down           # Arrêter
docker compose logs -f        # Voir les logs
```