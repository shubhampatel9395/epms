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
-- Table structure for table `eventemployeemapping`
--

DROP TABLE IF EXISTS `eventemployeemapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventemployeemapping` (
  `eventEmployeemappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventId` int unsigned NOT NULL,
  `employeeId` int unsigned NOT NULL,
  `workDescription` varchar(255) DEFAULT NULL,
  `statusId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`eventEmployeemappingId`),
  UNIQUE KEY `eventEmployeemappingId_UNIQUE` (`eventEmployeemappingId`),
  KEY `fk_eventemployee_mapping_idx` (`employeeId`),
  KEY `fk_eventemployeestatus_mapping_idx` (`statusId`),
  KEY `fk_eventId_mapping_idx` (`eventId`),
  KEY `fk_createdByeventemployeemapping_idx` (`createdBy`),
  KEY `fk_updatedByeventemployeemapping_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByeventemployeemapping` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventemployee_mapping` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_eventemployeestatus_mapping` FOREIGN KEY (`statusId`) REFERENCES `enuemployeeworkingstatus` (`statusId`),
  CONSTRAINT `fk_eventId_mapping` FOREIGN KEY (`eventId`) REFERENCES `event` (`eventId`),
  CONSTRAINT `fk_updatedByeventemployeemapping` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventemployeemapping`
--

LOCK TABLES `eventemployeemapping` WRITE;
/*!40000 ALTER TABLE `eventemployeemapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventemployeemapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:52:00
