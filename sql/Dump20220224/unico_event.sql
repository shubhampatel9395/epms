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
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `eventId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventTitle` varchar(45) NOT NULL,
  `objective` varchar(255) DEFAULT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned DEFAULT NULL,
  `userDetailsId` int unsigned NOT NULL,
  `packageId` int unsigned NOT NULL,
  `eventOrganizerId` int unsigned DEFAULT NULL,
  `isPublic` tinyint NOT NULL DEFAULT '1',
  `isFree` tinyint NOT NULL DEFAULT '1',
  `startDate` date NOT NULL,
  `startTime` time NOT NULL,
  `endDate` date NOT NULL,
  `endTime` time NOT NULL,
  `estimatedGuest` int NOT NULL,
  `registrationFee` double NOT NULL DEFAULT '0',
  `registrationAvailable` int NOT NULL DEFAULT '0',
  `eventStatusId` int unsigned NOT NULL DEFAULT '1',
  `bill` blob,
  `totalCost` double NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  `eventcol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`eventId`),
  UNIQUE KEY `eventBookingId_UNIQUE` (`eventId`),
  KEY `fk_eventPackage_idx` (`packageId`),
  KEY `fk_eventOrganiser_idx` (`eventOrganizerId`),
  KEY `fk_customer_idx` (`userDetailsId`),
  KEY `fk_createdByevent_idx` (`createdBy`),
  KEY `fk_updatedByevent_idx` (`updatedBy`),
  KEY `fk_eventTypeEvent_idx` (`eventTypeId`),
  KEY `fk_eventSubTypeEvent_idx` (`eventSubTypeId`),
  KEY `fk_eventstatusId_idx` (`eventStatusId`),
  CONSTRAINT `fk_createdByevent` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_customer` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventOrganiser` FOREIGN KEY (`eventOrganizerId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_eventPackage` FOREIGN KEY (`packageId`) REFERENCES `packagedetails` (`packageDetailsId`),
  CONSTRAINT `fk_eventstatusId` FOREIGN KEY (`eventStatusId`) REFERENCES `enueventstatus` (`statusId`),
  CONSTRAINT `fk_eventSubTypeEvent` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_eventTypeEvent` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`),
  CONSTRAINT `fk_updatedByevent` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'Preet\'s Birthday Party','To have a fun with family',1,NULL,3,2,1,0,1,'2022-03-30','18:00:00','2022-03-30','23:00:00',150,0,0,2,NULL,60050,'2022-02-24 19:09:42','2022-02-24 19:09:42',1,NULL,NULL,NULL),(2,'Business Meeting','To meet with comfort',6,NULL,5,18,3,0,1,'2022-03-01','12:00:00','2022-02-05','12:00:00',200,0,0,2,NULL,55100,'2022-02-24 19:13:38','2022-02-24 19:13:38',1,NULL,NULL,NULL),(3,'Sport Event','Race to win!!!',7,NULL,6,17,3,1,1,'2022-03-16','09:00:00','2022-03-17','15:00:00',1000,0,0,2,NULL,55810,'2022-02-24 19:15:55','2022-02-24 19:15:55',1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
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
