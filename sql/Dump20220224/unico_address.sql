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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `addressId` int unsigned NOT NULL AUTO_INCREMENT,
  `address1` varchar(45) NOT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `cityId` int unsigned NOT NULL,
  `stateId` int unsigned NOT NULL,
  `countryId` int unsigned NOT NULL,
  `postalCode` varchar(10) DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`addressId`),
  UNIQUE KEY `addressId_UNIQUE` (`addressId`),
  KEY `fk_createdByaddress_idx` (`createdBy`),
  KEY `fk_updatedByaddress_idx` (`updatedBy`),
  KEY `fk_cityId_address_idx` (`cityId`),
  KEY `fk_countryId_address_idx` (`countryId`),
  KEY `fk_stateId_address_idx` (`stateId`),
  CONSTRAINT `fk_cityId_address` FOREIGN KEY (`cityId`) REFERENCES `enucity` (`cityId`),
  CONSTRAINT `fk_countryId_address` FOREIGN KEY (`countryId`) REFERENCES `enucountry` (`countryId`),
  CONSTRAINT `fk_createdByaddress` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_stateId_address` FOREIGN KEY (`stateId`) REFERENCES `enustate` (`stateId`),
  CONSTRAINT `fk_updatedByaddress` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'B/2/102, Vishwas Appartment','',57606,4030,101,'380061','2022-02-24 15:18:05','2022-02-24 15:18:05',1,NULL,NULL),(2,'B/2/102, Vishwas Appartment','',57606,4030,101,'380061','2022-02-24 15:19:32','2022-02-24 15:19:32',1,NULL,NULL),(3,'1, Main Street','',57606,4030,101,'380061','2022-02-24 15:24:09','2022-02-24 15:24:09',1,NULL,NULL),(4,'2, Main Street','',57606,4030,101,'380061','2022-02-24 15:29:03','2022-02-24 15:29:03',1,NULL,NULL),(5,'3 Main School Street','',57606,4030,101,'380061','2022-02-24 15:36:38','2022-02-24 15:36:38',1,NULL,NULL),(6,'4 Street ','',57606,4030,101,'380005','2022-02-24 15:42:36','2022-02-24 15:42:36',1,NULL,NULL),(7,'5 Street main','',57606,4030,101,'380061','2022-02-24 15:45:05','2022-02-24 15:45:05',1,NULL,NULL),(8,'5 Street main','',57606,4030,101,'380061','2022-02-24 15:45:53','2022-02-24 15:45:53',1,NULL,NULL),(9,'3 Street main','',57606,4030,101,'380058','2022-02-24 15:47:56','2022-02-24 15:47:56',1,NULL,NULL),(10,'4 Street Banglow','',57606,4030,101,'380050','2022-02-24 15:49:40','2022-02-24 15:49:40',1,NULL,NULL),(11,'7 Street Flat','',57606,4030,101,'380002','2022-02-24 15:51:54','2022-02-24 15:51:54',1,NULL,NULL),(12,'6 Street One','',57606,4030,101,'380004','2022-02-24 15:52:55','2022-02-24 15:52:55',1,NULL,NULL),(13,'6 Street One','',57606,4030,101,'380004','2022-02-24 15:57:48','2022-02-24 15:57:48',1,NULL,NULL),(14,'45, Chimbur Complex','',57606,4030,101,'380005','2022-02-24 15:59:01','2022-02-24 15:59:01',1,NULL,NULL),(15,'7 Shreekunj Shopping center ','Ghatlodia',57606,4030,101,'380007','2022-02-24 16:00:23','2022-02-24 16:00:23',1,NULL,NULL),(16,'50, Gaurav tower complex','',57606,4030,101,'380009','2022-02-24 16:01:48','2022-02-24 16:01:48',1,NULL,NULL),(17,'Sarkhej Gandhinagar Highway','Opposite Shalby Hospital',57606,4030,101,'380055','2022-02-24 16:18:06','2022-02-24 16:18:06',1,NULL,NULL),(18,'Sarkhej Gandhinagar Highway','Opposite Shalby Hospital',57606,4030,101,'380055','2022-02-24 16:20:20','2022-02-24 16:20:20',1,NULL,NULL),(19,'1, Main Street','',57606,4030,101,'380061','2022-02-24 16:32:45','2022-02-24 16:32:45',1,NULL,NULL),(20,'2, Main Street','',57606,4030,101,'380061','2022-02-24 16:34:02','2022-02-24 16:34:02',1,NULL,NULL),(21,'3, Main Street','',57606,4030,101,'380061','2022-02-24 16:35:13','2022-02-24 16:35:13',1,NULL,NULL),(22,'4, Main Street','',57606,4030,101,'380061','2022-02-24 16:47:54','2022-02-24 16:47:54',1,NULL,NULL),(23,'5, Main Street','',57606,4030,101,'380061','2022-02-24 16:48:57','2022-02-24 16:48:57',1,NULL,NULL),(24,'6, Main Street','',57606,4030,101,'380061','2022-02-24 16:50:36','2022-02-24 16:50:36',1,NULL,NULL),(25,'7, Main Street','',57606,4030,101,'380061','2022-02-24 16:52:07','2022-02-24 16:52:07',1,NULL,NULL),(26,'8, Main Street','',57606,4030,101,'380061','2022-02-24 16:53:25','2022-02-24 16:53:25',1,NULL,NULL),(27,'8, Main Street','',57606,4030,101,'380061','2022-02-24 16:54:37','2022-02-24 16:54:37',1,NULL,NULL),(28,'9, Main Street','',57606,4030,101,'380061','2022-02-24 16:57:10','2022-02-24 16:57:10',1,NULL,NULL),(29,'10, Main Street','',57606,4030,101,'380061','2022-02-24 16:58:08','2022-02-24 16:58:08',1,NULL,NULL),(30,'11, Main Street','',57606,4030,101,'380061','2022-02-24 16:59:13','2022-02-24 16:59:13',1,NULL,NULL),(31,'12, Main Street','',57606,4030,101,'380061','2022-02-24 17:00:15','2022-02-24 17:00:15',1,NULL,NULL),(32,'13, Main Street','',57606,4030,101,'380006','2022-02-24 17:01:12','2022-02-24 17:01:12',1,NULL,NULL),(33,'14, Main Street','',57606,4030,101,'380061','2022-02-24 17:02:11','2022-02-24 17:02:11',1,NULL,NULL),(34,'F-1 to 4, Suryoday Tower',' Sola Road',57606,4030,101,'380061','2022-02-24 17:52:48','2022-02-24 17:52:48',1,NULL,NULL),(35,'Near Chanakyapuri Bridge',' Ravishankar Maharaj Rd, Ghatlodiya',57606,4030,101,'380061','2022-02-24 17:56:05','2022-02-24 17:56:05',1,NULL,NULL),(36,'Vipul Dudhiya Road','Navrangpura',57606,4030,101,'380009','2022-02-24 18:00:20','2022-02-24 18:00:20',1,NULL,NULL),(37,'Science City Rd','Sola',57606,4030,101,'380060','2022-02-24 18:03:13','2022-02-24 18:03:13',1,NULL,NULL),(38,'2, Madhur Complex, Navrang School Road','Naranpura',57606,4030,101,'380013','2022-02-24 18:10:26','2022-02-24 18:10:26',1,NULL,NULL),(39,'Stadium Rd','Motera',57606,4030,101,'380005','2022-02-24 18:58:13','2022-02-24 18:58:13',1,NULL,NULL),(40,'Pritam Rai Cross Rd','opposite Museum, Kocharab, Paldi',57606,4030,101,'380061','2022-02-24 19:01:37','2022-02-24 19:02:21',1,NULL,NULL);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
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
