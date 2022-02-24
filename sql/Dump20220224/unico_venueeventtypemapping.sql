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
-- Table structure for table `venueeventtypemapping`
--

DROP TABLE IF EXISTS `venueeventtypemapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venueeventtypemapping` (
  `venueEventTypemappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int unsigned NOT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueEventTypemappingId`),
  UNIQUE KEY `venueEventTypemappingId_UNIQUE` (`venueEventTypemappingId`),
  KEY `fk_venue_eventType1_idx` (`eventSubTypeId`),
  KEY `fk_venue_eventType` (`venueId`),
  KEY `fk_venue_eventType2_idx` (`eventTypeId`),
  CONSTRAINT `fk_venue_eventSubType` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_venue_eventType` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`),
  CONSTRAINT `fk_venue_eventType2` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venueeventtypemapping`
--

LOCK TABLES `venueeventtypemapping` WRITE;
/*!40000 ALTER TABLE `venueeventtypemapping` DISABLE KEYS */;
INSERT INTO `venueeventtypemapping` VALUES (1,1,1,NULL,1),(2,1,3,NULL,1),(3,1,4,NULL,1),(4,2,1,NULL,1),(5,3,1,NULL,1),(6,3,2,NULL,1),(7,3,3,NULL,1),(8,3,4,NULL,1),(9,3,5,NULL,1),(10,3,6,NULL,1),(11,3,8,NULL,1),(12,3,10,NULL,1),(13,4,5,NULL,1),(14,4,6,NULL,1),(15,4,8,NULL,1),(16,5,5,NULL,1),(17,5,6,NULL,1),(18,5,13,NULL,1),(19,6,5,NULL,1),(20,6,9,NULL,1),(21,6,10,NULL,1),(22,7,7,NULL,1),(23,7,12,NULL,1),(24,7,13,NULL,1),(31,8,5,NULL,1),(32,8,6,NULL,1),(33,8,9,NULL,1),(34,8,10,NULL,1),(35,8,11,NULL,1),(36,8,12,NULL,1);
/*!40000 ALTER TABLE `venueeventtypemapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:51:58
