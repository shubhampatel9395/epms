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
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `paymentId` int unsigned NOT NULL AUTO_INCREMENT,
  `transactionId` int unsigned NOT NULL,
  `paymentTypeId` int unsigned NOT NULL,
  `paymentStakeholderId` int unsigned NOT NULL,
  `remarks` varchar(45) DEFAULT NULL,
  `isReceived` tinyint NOT NULL DEFAULT '1',
  `userDetailsId` int unsigned DEFAULT NULL,
  `venueId` int unsigned DEFAULT NULL,
  `attendeeId` int unsigned DEFAULT NULL,
  `employeeId` int unsigned DEFAULT NULL,
  `serviceProviderId` int unsigned DEFAULT NULL,
  `amount` double NOT NULL,
  `createdAt` datetime NOT NULL,
  PRIMARY KEY (`paymentId`),
  UNIQUE KEY `paymentId_UNIQUE` (`paymentId`),
  UNIQUE KEY `transactionId_UNIQUE` (`transactionId`),
  KEY `fk_paymentType_idx` (`paymentTypeId`),
  KEY `fk_paymentStakeholderType_idx` (`paymentStakeholderId`),
  KEY `fk_venuePayment_idx` (`venueId`),
  KEY `fk_employeePayment_idx` (`employeeId`),
  KEY `fk_customerPayment_idx` (`userDetailsId`),
  KEY `fk_serviceProviderPayment_idx` (`serviceProviderId`),
  CONSTRAINT `fk_customerPayment` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_employeePayment` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_paymentStakeholderType` FOREIGN KEY (`paymentStakeholderId`) REFERENCES `enupaymentstakeholder` (`paymentStakeholderId`),
  CONSTRAINT `fk_paymentType` FOREIGN KEY (`paymentTypeId`) REFERENCES `enupaymenttype` (`paymentTypeId`),
  CONSTRAINT `fk_serviceProviderPayment` FOREIGN KEY (`serviceProviderId`) REFERENCES `serviceprovider` (`serviceProviderId`),
  CONSTRAINT `fk_venuePayment` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
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
