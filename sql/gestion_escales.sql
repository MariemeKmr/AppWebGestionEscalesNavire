-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: gestion_escales
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `armateur`
--

DROP TABLE IF EXISTS `armateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `armateur` (
  `idArmateur` int(11) NOT NULL AUTO_INCREMENT,
  `nomArmateur` varchar(255) NOT NULL,
  `adresseArmateur` varchar(255) DEFAULT NULL,
  `telephoneArmateur` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idArmateur`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `armateur`
--

LOCK TABLES `armateur` WRITE;
/*!40000 ALTER TABLE `armateur` DISABLE KEYS */;
INSERT INTO `armateur` VALUES (1,'Armateur Test','Adresse ','12002'),(2,'Bolloré Africa Logistics','Dakar, Sénégal','221338888888'),(3,'Maersk Line','Copenhague, Danemark','4533366666'),(4,'MSC Mediterranean Shipping','Genève, Suisse','41223334444'),(5,'COSCO Shipping','Shanghai, Chine','862122222222'),(6,'Marième KAMARA','Mermoz','776092990'),(7,'Fatou Ngom','SYS','8909'),(8,'FGJH','HJ','78'),(9,'BAYE ELI','MAMELLE','775768');
/*!40000 ALTER TABLE `armateur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bonpilotage`
--

DROP TABLE IF EXISTS `bonpilotage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bonpilotage` (
  `idMouvement` int(11) NOT NULL AUTO_INCREMENT,
  `montEscale` decimal(10,2) DEFAULT NULL,
  `dateDebutBon` date NOT NULL,
  `dateFinBon` date DEFAULT NULL,
  `posteQuai` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codeTypeMvt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `numeroEscale` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `etat` varchar(20) NOT NULL DEFAULT 'Saisie',
  PRIMARY KEY (`idMouvement`),
  KEY `bonpilotage_ibfk_1` (`codeTypeMvt`),
  KEY `bonpilotage_ibfk_2` (`numeroEscale`),
  CONSTRAINT `bonpilotage_ibfk_1` FOREIGN KEY (`codeTypeMvt`) REFERENCES `typemouvement` (`codeTypeMvt`),
  CONSTRAINT `bonpilotage_ibfk_2` FOREIGN KEY (`numeroEscale`) REFERENCES `escale` (`numeroEscale`),
  CONSTRAINT `chk_etat_bonpilotage` CHECK (`etat` in ('Saisie','Valide'))
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bonpilotage`
--

LOCK TABLES `bonpilotage` WRITE;
/*!40000 ALTER TABLE `bonpilotage` DISABLE KEYS */;
INSERT INTO `bonpilotage` VALUES (2,100.00,'2024-06-01','2024-06-01','Quai 1','Mouvement','ESC001','Valide'),(3,150.00,'2024-06-02','2024-06-02','Quai 2','Mouvement','ESC002','Valide'),(4,150.00,'2024-06-01','2024-06-01','Quai 3','Mouvement','ESC003','Valide'),(5,100.00,'2024-06-01','2024-06-01','Quai 3','Mouvement','ESC003','Valide'),(9,300.00,'2025-06-29','2025-07-04','99','Mouvement','ESC002','Valide'),(10,50.00,'2025-06-23','2025-06-23','10','Mouvement','ESC001','Valide'),(11,290.00,'2025-06-24','2025-06-24','10','Mouvement','ESC25062003','Valide'),(12,99.99,'2025-06-23','2025-06-23','10','Mouvement','ESC003','Valide'),(13,10000.00,'2025-06-25','2025-06-26','18','Mouvement','ESC003','Valide'),(14,200.00,'2025-06-24','2025-06-24','17','Mouvement','ESC25062003','Valide'),(15,100.00,'2025-06-24','2025-06-24','17','Mouvement','ESC25062003','Valide'),(16,150.00,'2024-06-01','2024-06-01','Quai 1','Mouvement','ESC001','Valide'),(17,1000.00,'2025-06-24','2025-06-27','11','Mouvement','ESC25061902','Valide'),(18,100.02,'2025-06-24','2025-06-24','11','Mouvement','ESC25061902','Valide'),(19,9999.99,'2025-06-27','2025-06-24','13','Mouvement','ESC25062102','Valide'),(20,100.00,'2025-06-24','2025-06-24','15','Mouvement','ESC25062102','Valide'),(35,100.00,'2025-06-25','2025-06-25','Quai 1','ENTREE','ESC001','Valide'),(36,100.00,'2025-06-25','2025-06-25','Quai 2','ENTREE','ESC002','Valide'),(37,100.00,'2025-06-25','2025-06-25','Quai 3','ENTREE','ESC003','Valide'),(38,100.00,'2025-06-25','2025-06-25','Quai 4','ENTREE','ESC25061902','Valide'),(39,100.00,'2025-06-25','2025-06-25','Quai 5','ENTREE','ESC25062003','Valide'),(40,100.00,'2025-06-25','2025-06-25','Quai 6','ENTREE','ESC25062102','Valide'),(41,100.00,'2025-06-25','2025-06-25','Quai 1','SORTIE','ESC001','Valide'),(42,100.00,'2025-06-25','2025-06-25','Quai 2','SORTIE','ESC002','Valide'),(43,100.00,'2025-06-25','2025-06-25','Quai 3','SORTIE','ESC003','Valide'),(44,100.00,'2025-06-25','2025-06-25','Quai 4','SORTIE','ESC25061902','Valide'),(45,100.00,'2025-06-25','2025-06-25','Quai 5','SORTIE','ESC25062003','Valide'),(46,100.00,'2025-06-25','2025-06-25','Quai 6','SORTIE','ESC25062102','Valide'),(47,199.97,'2025-06-30','2025-06-30','16','ENTREE','ESC25062706','Valide'),(48,500.00,'2025-06-30','2025-06-27','3','Mouvement','ESC25062706','Valide'),(49,123.00,'2025-06-26','2025-06-27','13','Mouvement','ESC25062706','Valide'),(50,400.00,'2025-07-01','2025-07-06','10','SORTIE','ESC25062706','Valide'),(53,200.00,'2025-07-04','2025-07-04','10','ENTREE','ESC25070302','Valide'),(54,100.00,'2025-07-09','2025-07-11','789','SORTIE','ESC25070302','Valide'),(56,200.00,'2025-07-10','2025-07-11','6','ENTREE','ESC25062905','Valide'),(59,200.00,'2025-07-18','2025-07-17','10','Mouvement','ESC25071704','Valide'),(60,500.00,'2025-07-18','2025-07-19','14','ENTREE','ESC25071704','Valide'),(61,500.00,'2025-07-20','2025-07-20','6','ENTREE','ESC25062607','Valide'),(62,700.00,'2025-07-17','2025-07-17','','SORTIE','ESC25071704','Valide'),(64,788.00,'2025-07-17','2025-07-12','','SORTIE','ESC25062304','Valide');
/*!40000 ALTER TABLE `bonpilotage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consignataire`
--

DROP TABLE IF EXISTS `consignataire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consignataire` (
  `idConsignataire` int(11) NOT NULL AUTO_INCREMENT,
  `raisonSociale` varchar(255) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idConsignataire`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consignataire`
--

LOCK TABLES `consignataire` WRITE;
/*!40000 ALTER TABLE `consignataire` DISABLE KEYS */;
INSERT INTO `consignataire` VALUES (1,'Maritime Services SA','123 Avenue du Port, Dakar','+221 33 123 45 '),(2,'Ocean Logistics SARL','456 Rue des Navires, Dakar','+221 33 987 6543'),(3,'Port Services International','789 Boulevard Maritime, Dakar','+221 33 456 7890'),(4,'Marieme compagny','Dakar','77999'),(5,'Logistics compagnie','Keur Massar','778990078'),(6,'SDFGH','FGH','456'),(7,'PAD CONS','MOLE2 ','10000'),(8,'Hachage','Almadies','750000000');
/*!40000 ALTER TABLE `consignataire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `escale`
--

DROP TABLE IF EXISTS `escale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `escale` (
  `numeroEscale` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `debutEscale` date NOT NULL,
  `finEscale` date DEFAULT NULL,
  `nomNavire` varchar(255) NOT NULL,
  `prixSejour` double DEFAULT NULL,
  `idConsignataire` int(11) DEFAULT NULL,
  `zone` varchar(100) DEFAULT NULL,
  `numeroNavire` varchar(32) NOT NULL,
  `prixUnitaire` double DEFAULT NULL,
  `terminee` tinyint(1) DEFAULT 0,
  `facturee` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`numeroEscale`),
  KEY `nomNavire` (`nomNavire`),
  KEY `idConsignataire` (`idConsignataire`),
  CONSTRAINT `escale_ibfk_1` FOREIGN KEY (`nomNavire`) REFERENCES `navire` (`nomNavire`),
  CONSTRAINT `escale_ibfk_2` FOREIGN KEY (`idConsignataire`) REFERENCES `consignataire` (`idConsignataire`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `escale`
--

LOCK TABLES `escale` WRITE;
/*!40000 ALTER TABLE `escale` DISABLE KEYS */;
INSERT INTO `escale` VALUES ('ESC001','2024-06-01','2024-06-03','ATLANTIC STAR',300,1,'intérieur','IMO1234567',100,0,1),('ESC002','2024-06-02','2025-06-20','SAHEL PRINCESS',40000,2,'rade','IMO2345678',100,0,1),('ESC003','2024-06-01','2024-06-05','WEST AFRICA',1660,3,'intérieur','IMO3456789',50,0,1),('ESC25061902','2025-06-19','2025-06-19','ATLANTIC STAR',1099.99,1,'rade','IMO1234567',1099.99,0,1),('ESC25062003','2025-06-20','2025-06-28','JLJKN',550,8,'rade','800',56,0,1),('ESC25062102','2025-06-21','2025-07-02','678',2400,5,'rade','567',200,0,1),('ESC25062304','2025-06-23','2025-06-28','SAHEL PRINCESS',12000,2,'rade','IMO2345678',2000,0,0),('ESC25062607','2025-06-26','2025-06-29','FGH',400,7,'intérieur','34567',100,0,0),('ESC25062706','2025-07-06','2025-07-20','SAHEL PRINCESS',9000,2,'rade','IMO2345678',600,0,1),('ESC25062905','2025-06-29','2025-07-06','WEST AFRICA',4000,3,'intérieur','IMO3456789',500,0,0),('ESC25063008','2025-07-27','2025-09-07','WEST AFRICA',6450.429999999999,3,'intérieur','IMO3456789',150.01,0,0),('ESC25063009','2025-06-30','2025-07-04','ATLANTIC STAR',1000,1,'intérieur','IMO1234567',200,0,0),('ESC25070302','2025-07-03','2025-07-11','La bonne cause',900,4,'rade','3578',100,0,1),('ESC25071704','2025-07-17','2025-07-25','MK-marieme',2300,4,'intérieur','01042004',250,0,1),('ESC25071705','2025-07-17','2025-07-17','WEST AFRICA',990,3,'rade','IMO3456789',990,0,0);
/*!40000 ALTER TABLE `escale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `escalesencours`
--

DROP TABLE IF EXISTS `escalesencours`;
/*!50001 DROP VIEW IF EXISTS `escalesencours`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `escalesencours` AS SELECT
 1 AS `numeroEscale`,
  1 AS `nomNavire`,
  1 AS `numeroNavire`,
  1 AS `raisonSociale`,
  1 AS `debutEscale`,
  1 AS `zone`,
  1 AS `prixSejour` */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `facture`
--

DROP TABLE IF EXISTS `facture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero_facture` varchar(50) NOT NULL,
  `date_generation` datetime DEFAULT NULL,
  `montant_total` double NOT NULL,
  `id_agent` int(11) NOT NULL,
  `numero_escale` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_facture` (`numero_facture`),
  UNIQUE KEY `numeroFacture` (`numero_facture`),
  UNIQUE KEY `numero_facture_2` (`numero_facture`),
  KEY `id_agent` (`id_agent`),
  CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`id_agent`) REFERENCES `utilisateur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facture`
--

LOCK TABLES `facture` WRITE;
/*!40000 ALTER TABLE `facture` DISABLE KEYS */;
INSERT INTO `facture` VALUES (2,'FAC-ESC002-1750690109897','2025-06-23 00:00:00',450,1,'ESC002'),(4,'FAC-ESC003-1750694632285','2025-06-23 00:00:00',12209.99,1,'ESC003'),(6,'FAC-ESC25061902-1750843506740','2025-06-25 00:00:00',2200.01,1,'ESC25061902'),(7,'FAC-ESC25062003-1750854979492','2025-06-25 00:00:00',1140,9,'ESC25062003'),(8,'FAC-ESC001-1750864020187','2025-06-25 00:00:00',800,9,'ESC001'),(9,'FAC-ESC25062102-1750864033146','2025-06-25 00:00:00',12699.99,9,'ESC25062102'),(10,'FAC-ESC25062706-1751282857128','2025-06-30 00:00:00',10222.97,1,'ESC25062706'),(11,'FAC-ESC25070302-1751474395481','2025-07-02 00:00:00',1200,1,'ESC25070302'),(12,'FAC-ESC25071704-1752752447267','2025-07-17 00:00:00',3700,1,'ESC25071704');
/*!40000 ALTER TABLE `facture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facture_bon_pilotage`
--

DROP TABLE IF EXISTS `facture_bon_pilotage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facture_bon_pilotage` (
  `id_facture` int(11) NOT NULL,
  `id_mouvement` int(11) NOT NULL,
  PRIMARY KEY (`id_facture`,`id_mouvement`),
  KEY `id_mouvement` (`id_mouvement`),
  CONSTRAINT `facture_bon_pilotage_ibfk_1` FOREIGN KEY (`id_facture`) REFERENCES `facture` (`id`),
  CONSTRAINT `facture_bon_pilotage_ibfk_2` FOREIGN KEY (`id_mouvement`) REFERENCES `bonpilotage` (`idMouvement`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facture_bon_pilotage`
--

LOCK TABLES `facture_bon_pilotage` WRITE;
/*!40000 ALTER TABLE `facture_bon_pilotage` DISABLE KEYS */;
INSERT INTO `facture_bon_pilotage` VALUES (2,3),(2,9),(4,4),(4,5),(4,12),(4,13),(6,17),(6,18),(7,11),(7,14),(7,15),(8,2),(8,10),(8,16),(8,35),(8,41),(9,19),(9,20),(9,40),(9,46),(10,47),(10,48),(10,49),(10,50),(11,53),(11,54),(12,59),(12,60),(12,62);
/*!40000 ALTER TABLE `facture_bon_pilotage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historique`
--

DROP TABLE IF EXISTS `historique`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historique` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utilisateur` varchar(255) DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `date_operation` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historique`
--

LOCK TABLES `historique` WRITE;
/*!40000 ALTER TABLE `historique` DISABLE KEYS */;
/*!40000 ALTER TABLE `historique` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `navire`
--

DROP TABLE IF EXISTS `navire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `navire` (
  `nomNavire` varchar(255) NOT NULL,
  `numeroNavire` varchar(100) NOT NULL,
  `longueurNavire` bigint(20) DEFAULT NULL,
  `largeurNavire` bigint(20) DEFAULT NULL,
  `volumeNavire` bigint(20) DEFAULT NULL,
  `tirantEauNavire` bigint(20) DEFAULT NULL,
  `idConsignataire` int(11) DEFAULT NULL,
  `idArmateur` int(11) NOT NULL,
  PRIMARY KEY (`nomNavire`),
  KEY `idConsignataire` (`idConsignataire`),
  KEY `fk_navire_armateur` (`idArmateur`),
  CONSTRAINT `fk_navire_armateur` FOREIGN KEY (`idArmateur`) REFERENCES `armateur` (`idArmateur`),
  CONSTRAINT `navire_ibfk_1` FOREIGN KEY (`idConsignataire`) REFERENCES `consignataire` (`idConsignataire`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `navire`
--

LOCK TABLES `navire` WRITE;
/*!40000 ALTER TABLE `navire` DISABLE KEYS */;
INSERT INTO `navire` VALUES ('678','567',222,222,22,722,5,5),('ATLANTIC STAR','IMO1234567',250,35,15000,12,1,2),('FGH','34567',6,5,150,5,7,1),('GHJK','56788990',78,7,4368,8,NULL,6),('JLJKN','800',7,8,279,5,8,4),('La bonne cause','3578',40,20,4000,5,4,9),('MK-marieme','01042004',40,15,6000,10,4,6),('MonNavireee','-è_',7,7,343,7,NULL,2),('SAHEL PRINCESS','IMO2345678',90,8,8001,9,2,3),('testi','IMO6789',45,7,945,3,1,6),('WEST AFRICA','IMO3456789',300,42,25000,15,3,4);
/*!40000 ALTER TABLE `navire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `prestationsparescale`
--

DROP TABLE IF EXISTS `prestationsparescale`;
/*!50001 DROP VIEW IF EXISTS `prestationsparescale`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `prestationsparescale` AS SELECT
 1 AS `numeroEscale`,
  1 AS `nomNavire`,
  1 AS `nombreMouvements`,
  1 AS `totalMouvements`,
  1 AS `prixSejour`,
  1 AS `totalEscale` */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `recette_par_periode`
--

DROP TABLE IF EXISTS `recette_par_periode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recette_par_periode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `annee` int(11) DEFAULT NULL,
  `mois` int(11) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recette_par_periode`
--

LOCK TABLES `recette_par_periode` WRITE;
/*!40000 ALTER TABLE `recette_par_periode` DISABLE KEYS */;
/*!40000 ALTER TABLE `recette_par_periode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typemouvement`
--

DROP TABLE IF EXISTS `typemouvement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `typemouvement` (
  `codeTypeMvt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `libelleTypeMvt` varchar(255) DEFAULT NULL,
  `prixTypeMvt` decimal(10,2) NOT NULL,
  PRIMARY KEY (`codeTypeMvt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typemouvement`
--

LOCK TABLES `typemouvement` WRITE;
/*!40000 ALTER TABLE `typemouvement` DISABLE KEYS */;
INSERT INTO `typemouvement` VALUES ('ENTREE','Entree au port',150.00),('Mouvement','Mouvement',0.00),('SORTIE','Sortie du port',150.00);
/*!40000 ALTER TABLE `typemouvement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `mot_de_passe` varchar(100) NOT NULL,
  `role` enum('admin','agent_portuaire','agent_facturation') DEFAULT NULL,
  `nom_complet` varchar(100) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur`
--

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` VALUES (1,'admin@gestionescale.com','admin','admin','Marieme Kamara','77067890'),(2,'agent@gestionescale.com','agent123','agent_portuaire','Agent Portuaire','771111111'),(5,'facturation@gestionescale.com','facture123','agent_facturation','Agent de Facturation','772222222'),(9,'test@test.com','test','admin','test','7777'),(10,'test1@test.com','test','agent_portuaire','test1','777'),(11,'test3@test.com','test','agent_facturation','test2','999');
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `escalesencours`
--

/*!50001 DROP VIEW IF EXISTS `escalesencours`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `escalesencours` AS select `e`.`numeroEscale` AS `numeroEscale`,`e`.`nomNavire` AS `nomNavire`,`n`.`numeroNavire` AS `numeroNavire`,`c`.`raisonSociale` AS `raisonSociale`,`e`.`debutEscale` AS `debutEscale`,`e`.`zone` AS `zone`,`e`.`prixSejour` AS `prixSejour` from ((`escale` `e` join `navire` `n` on(`e`.`nomNavire` = `n`.`nomNavire`)) join `consignataire` `c` on(`e`.`idConsignataire` = `c`.`idConsignataire`)) where `e`.`finEscale` is null */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `prestationsparescale`
--

/*!50001 DROP VIEW IF EXISTS `prestationsparescale`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `prestationsparescale` AS select `e`.`numeroEscale` AS `numeroEscale`,`e`.`nomNavire` AS `nomNavire`,count(`bp`.`idMouvement`) AS `nombreMouvements`,sum(`bp`.`montEscale`) AS `totalMouvements`,`e`.`prixSejour` AS `prixSejour`,sum(`bp`.`montEscale`) + coalesce(`e`.`prixSejour`,0) AS `totalEscale` from (`escale` `e` left join `bonpilotage` `bp` on(`e`.`numeroEscale` = `bp`.`numeroEscale`)) group by `e`.`numeroEscale`,`e`.`nomNavire`,`e`.`prixSejour` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-17 16:19:47
