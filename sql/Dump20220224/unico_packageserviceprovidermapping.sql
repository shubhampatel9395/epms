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
-- Table structure for table `packageserviceprovidermapping`
--

DROP TABLE IF EXISTS `packageserviceprovidermapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packageserviceprovidermapping` (
  `packageServiceprovidermappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `packageId` int unsigned NOT NULL,
  `serviceProviderId` int unsigned NOT NULL,
  `statusId` int unsigned NOT NULL DEFAULT '4',
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`packageServiceprovidermappingId`),
  UNIQUE KEY `packageServiceprovidermappingId_UNIQUE` (`packageServiceprovidermappingId`),
  KEY `fk_packageId_mapping_idx` (`packageId`),
  KEY `fk_serviceProvider_mapping_idx` (`serviceProviderId`),
  KEY `fk_serviceProviderStatus_mapping_idx` (`statusId`),
  KEY `fk_createdBypackageserviceprovidermapping_idx` (`createdBy`),
  KEY `fk_updatedBypackageserviceprovidermapping_idx` (`updatedBy`),
  CONSTRAINT `fk_createdBypackageserviceprovidermapping` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_packageId_mapping` FOREIGN KEY (`packageId`) REFERENCES `packagedetails` (`packageDetailsId`),
  CONSTRAINT `fk_serviceProvider_mapping` FOREIGN KEY (`serviceProviderId`) REFERENCES `serviceprovider` (`serviceProviderId`),
  CONSTRAINT `fk_serviceProviderStatus_mapping` FOREIGN KEY (`statusId`) REFERENCES `enuserviceproviderworkingstatus` (`statusId`),
  CONSTRAINT `fk_updatedBypackageserviceprovidermapping` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packageserviceprovidermapping`
--

LOCK TABLES `packageserviceprovidermapping` WRITE;
/*!40000 ALTER TABLE `packageserviceprovidermapping` DISABLE KEYS */;
INSERT INTO `packageserviceprovidermapping` VALUES (1,1,5,4,'2022-02-24 18:39:07','2022-02-24 18:39:07',1,NULL,NULL),(2,1,7,4,'2022-02-24 18:39:09','2022-02-24 18:39:09',1,NULL,NULL),(3,2,6,4,'2022-02-24 18:40:17','2022-02-24 18:40:17',1,NULL,NULL),(4,2,1,4,'2022-02-24 18:40:17','2022-02-24 18:40:17',1,NULL,NULL),(5,2,8,4,'2022-02-24 18:40:17','2022-02-24 18:40:17',1,NULL,NULL),(6,3,5,4,'2022-02-24 18:40:36','2022-02-24 18:40:36',1,NULL,NULL),(7,4,6,4,'2022-02-24 18:41:09','2022-02-24 18:41:09',1,NULL,NULL),(8,4,8,4,'2022-02-24 18:41:09','2022-02-24 18:41:09',1,NULL,NULL),(9,5,6,4,'2022-02-24 18:42:04','2022-02-24 18:42:04',1,NULL,NULL),(10,6,5,4,'2022-02-24 18:42:27','2022-02-24 18:42:27',1,NULL,NULL),(11,6,3,4,'2022-02-24 18:42:27','2022-02-24 18:42:27',1,NULL,NULL),(12,7,5,4,'2022-02-24 18:42:51','2022-02-24 18:42:51',1,NULL,NULL),(13,7,6,4,'2022-02-24 18:42:51','2022-02-24 18:42:51',1,NULL,NULL),(14,8,6,4,'2022-02-24 18:43:21','2022-02-24 18:43:21',1,NULL,NULL),(15,8,7,4,'2022-02-24 18:43:21','2022-02-24 18:43:21',1,NULL,NULL),(16,9,5,4,'2022-02-24 18:45:09','2022-02-24 18:45:09',1,NULL,NULL),(17,9,6,4,'2022-02-24 18:45:09','2022-02-24 18:45:09',1,NULL,NULL),(18,9,1,4,'2022-02-24 18:45:09','2022-02-24 18:45:09',1,NULL,NULL),(19,9,8,4,'2022-02-24 18:45:09','2022-02-24 18:45:09',1,NULL,NULL),(20,10,2,4,'2022-02-24 18:45:32','2022-02-24 18:45:32',1,NULL,NULL),(21,11,5,4,'2022-02-24 18:46:24','2022-02-24 18:46:24',1,NULL,NULL),(24,12,7,4,'2022-02-24 18:47:28','2022-02-24 18:47:28',1,NULL,NULL),(25,12,4,4,'2022-02-24 18:47:28','2022-02-24 18:47:28',1,NULL,NULL),(26,13,3,4,'2022-02-24 18:47:57','2022-02-24 18:47:57',1,NULL,NULL),(27,13,7,4,'2022-02-24 18:47:57','2022-02-24 18:47:57',1,NULL,NULL),(28,14,5,4,'2022-02-24 18:48:30','2022-02-24 18:48:30',1,NULL,NULL),(29,15,7,4,'2022-02-24 18:49:12','2022-02-24 18:49:12',1,NULL,NULL),(30,15,8,4,'2022-02-24 18:49:12','2022-02-24 18:49:12',1,NULL,NULL),(31,16,3,4,'2022-02-24 19:03:14','2022-02-24 19:03:14',1,NULL,NULL),(32,16,7,4,'2022-02-24 19:03:14','2022-02-24 19:03:14',1,NULL,NULL),(33,17,1,4,'2022-02-24 19:03:51','2022-02-24 19:03:51',1,NULL,NULL),(34,17,4,4,'2022-02-24 19:03:51','2022-02-24 19:03:51',1,NULL,NULL),(35,18,2,4,'2022-02-24 19:13:38','2022-02-24 19:13:38',1,NULL,NULL),(36,18,1,4,'2022-02-24 19:13:38','2022-02-24 19:13:38',1,NULL,NULL);
/*!40000 ALTER TABLE `packageserviceprovidermapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:51:57
