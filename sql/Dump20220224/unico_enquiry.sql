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
-- Table structure for table `enquiry`
--

DROP TABLE IF EXISTS `enquiry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enquiry` (
  `enquiryId` int unsigned NOT NULL AUTO_INCREMENT,
  `personName` varchar(45) NOT NULL,
  `mobileNumber` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned DEFAULT NULL,
  `startDate` date NOT NULL,
  `startTime` time NOT NULL,
  `endDate` date NOT NULL,
  `endTime` time NOT NULL,
  `description` varchar(255) NOT NULL,
  `isPublic` tinyint NOT NULL DEFAULT '1',
  `isFree` tinyint NOT NULL DEFAULT '1',
  `estimatedGuest` int NOT NULL,
  `minBudget` double NOT NULL,
  `maxBudget` double NOT NULL,
  `enquiryStatusId` int unsigned NOT NULL DEFAULT '1',
  `response` varchar(10000) DEFAULT NULL,
  `responseDate` date DEFAULT NULL,
  `responseTime` time DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`enquiryId`),
  UNIQUE KEY `enquiryId_UNIQUE` (`enquiryId`),
  KEY `fk_enquiryeventtype_idx` (`eventTypeId`),
  KEY `fk_enquirysubeventtype_idx` (`eventSubTypeId`),
  KEY `fk_enquirystatus_idx` (`enquiryStatusId`),
  KEY `fk_createdByenquiry_idx` (`createdBy`),
  KEY `fk_updatedByenquiry_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByenquiry` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_enquiryeventsubtype` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_enquiryeventtype` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`),
  CONSTRAINT `fk_enquirystatus` FOREIGN KEY (`enquiryStatusId`) REFERENCES `enuenquirystatus` (`statusId`),
  CONSTRAINT `fk_updatedByenquiry` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enquiry`
--

LOCK TABLES `enquiry` WRITE;
/*!40000 ALTER TABLE `enquiry` DISABLE KEYS */;
INSERT INTO `enquiry` VALUES (1,'Shubham','8735889977','shubhampatel9395@gmail.com',5,NULL,'2022-02-05','12:00:00','2022-02-05','17:00:00','',0,0,50,5000,10000,1,NULL,NULL,NULL,'2022-02-24 17:04:19','2022-02-24 17:04:19',1,NULL,NULL),(2,'Chandni','8866616641','patelchandni.125@gmail.com',1,NULL,'2022-03-30','20:00:00','2022-03-30','21:00:00','',0,0,150,5000,15000,1,NULL,NULL,NULL,'2022-02-24 17:06:39','2022-02-24 17:06:39',1,NULL,NULL),(3,'Shubh','9913801105','patelshubh2002@gmail.com',11,NULL,'2022-02-26','17:00:00','2022-02-26','22:00:00','',1,1,500,10000,15000,1,NULL,NULL,NULL,'2022-02-24 17:09:53','2022-02-24 17:09:53',1,NULL,NULL);
/*!40000 ALTER TABLE `enquiry` ENABLE KEYS */;
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
