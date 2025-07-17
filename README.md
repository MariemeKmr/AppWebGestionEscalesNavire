# AppWebGestionEscalesNavire

## Présentation

**AppWebGestionEscalesNavire** est une application web développée dans le cadre d’un stage professionnel au Port Autonome de Dakar (PAD). Elle vise à moderniser la gestion des escales de navires, des Bons de Pilotage et de la facturation portuaire. Ce projet s’inscrit dans la transformation digitale du secteur portuaire sénégalais et a pour but d’améliorer la fiabilité, la rapidité et la traçabilité des opérations portuaires.

## Fonctionnalités
- **Gestion des navires** : Ajout, modification, suppression, consultation des navires, armateurs et consignataires.
- **Gestion des escales** : Planification, suivi, clôture et consultation des escales de navires.
- **Gestion des Bons de Pilotage** : Création, validation et historisation des mouvements de navires (entrée, sortie, shifting).
- **Gestion des utilisateurs et rôles** : Accès sécurisé, authentification, gestion des permissions selon le profil (administrateur, agent portuaire, agent financier).
- **Facturation automatisée** : Génération automatique des factures, calcul des montants, édition PDF.
- **Reporting & audit** : Tableaux de bord personnalisés, génération de rapports, suivi des opérations et historisation.
- **Sécurité** : Accès par identifiants, gestion des sessions, droits d’accès selon le rôle utilisateur.

## Architecture technique
- **Backend** : Java EE (Servlets, JSP, JDBC)
- **Frontend** : HTML5, CSS3, JavaScript
- **Base de données** : MariaDB
- **Serveur d’application** : Apache Tomcat
- **Gestion de versions** : Git & GitHub

L’application est organisée en plusieurs couches : modèle (entities), DAO (accès aux données), services (logique métier), servlets (contrôleurs HTTP) et JSP (vue & interface utilisateur).

## Installation
1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/MariemeKmr/AppWebGestionEscalesNavire.git
   ```

2. **Configurer la base de données**  
   - Installer MariaDB et créer la base de données selon le schéma fourni dans `/docs` ou `/sql`.
   - Renseigner les paramètres de connexion dans le fichier de configuration (ex: `config.properties`).

3. **Déployer l’application**  
   - Importer le projet dans votre IDE Java (Eclipse/IntelliJ).
   - Compiler et générer le `.war`.
   - Déployer le `.war` sur un serveur Apache Tomcat.

4. **Accéder à l’application**  
   - Ouvrir votre navigateur à l’adresse : `http://localhost:8080/AppWebGestionEscalesNavire`

## Utilisation
- **Connexion** : Authentification obligatoire selon le rôle (administrateur, agent portuaire, agent financier).
- **Gestion** : Accès aux modules de gestion selon profil : navires, escales, bons de pilotage, facturation.
- **Rapports et audit** : Accès aux historiques, rapports et tableaux de bord personnalisés.


## Remerciements
Merci au Port Autonome de Dakar, à la Direction Digitale, aux encadrants académiques et professionnels, ainsi qu’à toutes les personnes ayant permis la réalisation de ce stage et de ce projet.

## Mots-clés
gestion portuaire, escale de navire, application web, Java EE, MariaDB, facturation, automatisation, transformation digitale, Port Autonome de Dakar
