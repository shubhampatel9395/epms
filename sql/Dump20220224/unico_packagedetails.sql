CREATE DATABASE  IF NOT EXISTS `unico` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `unico`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: unico
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `packagedetails`
--

DROP TABLE IF EXISTS `packagedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packagedetails` (
  `packageDetailsId` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `guestAmount` int DEFAULT NULL,
  `totalCost` double NOT NULL,
  `isStatic` tinyint NOT NULL DEFAULT '1',
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned DEFAULT NULL,
  `venueId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`packageDetailsId`),
  UNIQUE KEY `packageId_UNIQUE` (`packageDetailsId`),
  KEY `fk_eventSubTypeId_package_idx` (`eventSubTypeId`),
  KEY `fk_venueId_package_idx` (`venueId`),
  KEY `fk_createdBy_package_idx` (`createdBy`),
  KEY `fk_updatedBy_package_idx` (`updatedBy`),
  KEY `fk_eventTypeId_package_idx` (`eventTypeId`),
  CONSTRAINT `fk_createdBy_package` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventSubTypeId_package` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_eventTypeId_package` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`),
  CONSTRAINT `fk_updatedBy_package` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_venueId_package` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packagedetails`
--

LOCK TABLES `packagedetails` WRITE;
/*!40000 ALTER TABLE `packagedetails` DISABLE KEYS */;
INSERT INTO `packagedetails` VALUES (1,'Package1','',500,10500,1,1,NULL,1,'2022-02-24 18:39:06','2022-02-24 18:39:06',1,NULL,NULL),(2,'Package2','',200,60050,1,1,NULL,2,'2022-02-24 18:40:16','2022-02-24 18:40:16',1,NULL,NULL),(3,'Package3','',100,2600,1,2,NULL,3,'2022-02-24 18:40:36','2022-02-24 18:40:36',1,NULL,NULL),(4,'Package4','',250,9520,1,2,NULL,3,'2022-02-24 18:41:09','2022-02-24 18:41:09',1,NULL,NULL),(5,'Package5','',120,6000,1,3,NULL,1,'2022-02-24 18:42:04','2022-02-24 18:42:04',1,NULL,NULL),(6,'Package6','',100,12500,1,3,NULL,3,'2022-02-24 18:42:26','2022-02-24 18:42:26',1,NULL,NULL),(7,'Package7','',100,8000,1,4,NULL,1,'2022-02-24 18:42:51','2022-02-24 18:42:51',1,NULL,NULL),(8,'Package8','',150,11250,1,4,NULL,1,'2022-02-24 18:43:21','2022-02-24 18:43:21',1,NULL,NULL),(9,'Package9','',500,61300,1,5,NULL,4,'2022-02-24 18:45:09','2022-02-24 18:45:09',1,NULL,NULL),(10,'Package10','',200,5150,1,5,NULL,6,'2022-02-24 18:45:32','2022-02-24 18:45:32',1,NULL,NULL),(11,'Package11','',250,2100,1,6,NULL,5,'2022-02-24 18:46:24','2022-02-24 18:46:24',1,NULL,NULL),(12,'Package12','',200,10050,1,8,NULL,4,'2022-02-24 18:47:00','2022-02-24 18:47:28',1,NULL,NULL),(13,'Package13','',500,15050,1,9,NULL,6,'2022-02-24 18:47:57','2022-02-24 18:47:57',1,NULL,NULL),(14,'Package14','',150,2750,1,10,NULL,3,'2022-02-24 18:48:30','2022-02-24 18:48:30',1,NULL,NULL),(15,'Package15','',500,11200,1,13,NULL,5,'2022-02-24 18:49:12','2022-02-24 18:49:12',1,NULL,NULL),(16,'Package16','',500,15080,1,11,NULL,8,'2022-02-24 19:03:13','2022-02-24 19:03:13',1,NULL,NULL),(17,'Package17','',20000,55310,1,7,NULL,7,'2022-02-24 19:03:51','2022-02-24 19:03:51',1,NULL,NULL),(18,NULL,NULL,NULL,55100,0,6,NULL,5,'2022-02-24 19:13:37','2022-02-24 19:13:37',1,NULL,NULL);
/*!40000 ALTER TABLE `packagedetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:52:06
