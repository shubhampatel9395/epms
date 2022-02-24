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
-- Table structure for table `venuefacilitymapping`
--

DROP TABLE IF EXISTS `venuefacilitymapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venuefacilitymapping` (
  `venueFacilitymappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int unsigned NOT NULL,
  `facilityId` int unsigned NOT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueFacilitymappingId`),
  UNIQUE KEY `venueFacilitymappingId_UNIQUE` (`venueFacilitymappingId`),
  KEY `fk_venue_facility1_idx` (`facilityId`),
  KEY `fk_venue_facility_idx` (`venueId`),
  CONSTRAINT `fk_venue_facility` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`),
  CONSTRAINT `fk_venue_facility1` FOREIGN KEY (`facilityId`) REFERENCES `enuvenuefacility` (`venueFacilityId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venuefacilitymapping`
--

LOCK TABLES `venuefacilitymapping` WRITE;
/*!40000 ALTER TABLE `venuefacilitymapping` DISABLE KEYS */;
INSERT INTO `venuefacilitymapping` VALUES (1,1,1,1),(2,2,1,1),(3,3,1,1),(4,3,2,1),(5,3,3,1),(6,4,1,1),(7,4,2,1),(8,5,1,1),(9,5,2,1),(10,5,4,1),(11,6,1,1),(12,7,1,1),(13,7,2,1),(14,7,3,1),(18,8,1,1),(19,8,2,1),(20,8,3,1);
/*!40000 ALTER TABLE `venuefacilitymapping` ENABLE KEYS */;
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
