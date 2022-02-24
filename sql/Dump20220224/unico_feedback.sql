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
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedbackId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventId` int unsigned DEFAULT NULL,
  `eventRating` int DEFAULT NULL,
  `eventDescription` varchar(255) DEFAULT NULL,
  `venueId` int unsigned DEFAULT NULL,
  `venueRating` int DEFAULT NULL,
  `venueDescription` varchar(255) DEFAULT NULL,
  `employeeId` int unsigned DEFAULT NULL,
  `employeeRating` int DEFAULT NULL,
  `employeeDescription` varchar(255) DEFAULT NULL,
  `serviceProviderId` int unsigned DEFAULT NULL,
  `serviceProviderRating` int DEFAULT NULL,
  `serviceProviderDescription` varchar(255) DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`feedbackId`),
  UNIQUE KEY `feedbackId_UNIQUE` (`feedbackId`),
  KEY `fk_venueFeedback_idx` (`venueId`),
  KEY `fk_employeeFeedback_idx` (`employeeId`),
  KEY `fk_serviceProviderFeedback_idx` (`serviceProviderId`),
  KEY `fk_eventFeedback_idx` (`eventId`),
  KEY `fk_createdByfeedback_idx` (`createdBy`),
  KEY `fk_updatedByfeedback_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByfeedback` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_employeeFeedback` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_eventFeedback` FOREIGN KEY (`eventId`) REFERENCES `event` (`eventId`),
  CONSTRAINT `fk_serviceProviderFeedback` FOREIGN KEY (`serviceProviderId`) REFERENCES `serviceprovider` (`serviceProviderId`),
  CONSTRAINT `fk_updatedByfeedback` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_venueFeedback` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:52:05
