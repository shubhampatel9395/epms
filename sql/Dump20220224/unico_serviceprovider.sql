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
-- Table structure for table `serviceprovider`
--

DROP TABLE IF EXISTS `serviceprovider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviceprovider` (
  `serviceProviderId` int unsigned NOT NULL AUTO_INCREMENT,
  `userDetailsId` int unsigned NOT NULL,
  `serviceTypeId` int unsigned NOT NULL,
  `cost` double unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`serviceProviderId`),
  UNIQUE KEY `serviceProviderId_UNIQUE` (`serviceProviderId`),
  KEY `fk_userDetails_serviceprovider_idx` (`userDetailsId`),
  KEY `fk_serviceType_serviceProvider_idx` (`serviceTypeId`),
  KEY `fk_createdByserviceprovider_idx` (`createdBy`),
  KEY `fk_updatedByserviceprovider_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByserviceprovider` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_serviceType_serviceProvider` FOREIGN KEY (`serviceTypeId`) REFERENCES `enuservicetype` (`serviceTypeId`),
  CONSTRAINT `fk_updatedByserviceprovider` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_userDetails_serviceprovider` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviceprovider`
--

LOCK TABLES `serviceprovider` WRITE;
/*!40000 ALTER TABLE `serviceprovider` DISABLE KEYS */;
INSERT INTO `serviceprovider` VALUES (1,1,4,50000,'2022-02-24 15:18:11','2022-02-24 15:21:29',1,NULL,NULL),(2,4,2,5000,'2022-02-24 15:29:03','2022-02-24 18:20:55',1,NULL,NULL),(3,11,3,10000,'2022-02-24 15:51:54','2022-02-24 18:20:59',1,NULL,NULL),(4,12,5,5000,'2022-02-24 15:52:56','2022-02-24 18:21:16',1,NULL,NULL),(5,13,2,2000,'2022-02-24 15:57:49','2022-02-24 18:21:04',1,NULL,NULL),(6,14,3,3000,'2022-02-24 15:59:02','2022-02-24 18:21:20',1,NULL,NULL),(7,15,4,5000,'2022-02-24 16:00:23','2022-02-24 18:20:51',1,NULL,NULL),(8,16,5,6000,'2022-02-24 16:01:49','2022-02-24 18:21:08',1,NULL,NULL);
/*!40000 ALTER TABLE `serviceprovider` ENABLE KEYS */;
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
