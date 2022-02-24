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
-- Table structure for table `packagevenuefacilitymapping`
--

DROP TABLE IF EXISTS `packagevenuefacilitymapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packagevenuefacilitymapping` (
  `packageVenueFacilitymappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `packageId` int unsigned NOT NULL,
  `venueId` int unsigned NOT NULL,
  `facilityId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`packageVenueFacilitymappingId`),
  UNIQUE KEY `packagevenuefacilitymappingId_UNIQUE` (`packageVenueFacilitymappingId`),
  KEY `fk_packageId_pvfmapping_idx` (`packageId`),
  KEY `fk_facilityId_pvfmapping_idx` (`facilityId`),
  KEY `fk_venueId_pvfmapping_idx` (`venueId`),
  KEY `fk_createdBypackagevenuefacilitymapping_idx` (`createdBy`),
  KEY `fk_updatedBypackagevenuefacilitymapping_idx` (`updatedBy`),
  CONSTRAINT `fk_createdBypackagevenuefacilitymapping` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_facilityId_pvfmapping` FOREIGN KEY (`facilityId`) REFERENCES `enuvenuefacility` (`venueFacilityId`),
  CONSTRAINT `fk_packageId_pvfmapping` FOREIGN KEY (`packageId`) REFERENCES `packagedetails` (`packageDetailsId`),
  CONSTRAINT `fk_updatedBypackagevenuefacilitymapping` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_venueId_pvfmapping` FOREIGN KEY (`venueId`) REFERENCES `packagedetails` (`venueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packagevenuefacilitymapping`
--

LOCK TABLES `packagevenuefacilitymapping` WRITE;
/*!40000 ALTER TABLE `packagevenuefacilitymapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `packagevenuefacilitymapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:51:54
