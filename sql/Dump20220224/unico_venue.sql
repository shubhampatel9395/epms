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
-- Table structure for table `venue`
--

DROP TABLE IF EXISTS `venue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venue` (
  `venueId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueName` varchar(45) NOT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `venueTypeId` int unsigned NOT NULL,
  `addressId` int unsigned NOT NULL,
  `latitude` decimal(10,5) NOT NULL,
  `longitude` decimal(10,5) NOT NULL,
  `contactNumber` varchar(15) NOT NULL,
  `email` varchar(45) NOT NULL,
  `guestCapacity` int NOT NULL,
  `cost` double NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`venueId`),
  UNIQUE KEY `venueId_UNIQUE` (`venueId`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `contactNumber_UNIQUE` (`contactNumber`),
  KEY `fk_address_venue_idx` (`addressId`),
  KEY `fk_createdBy_venue_idx` (`createdBy`),
  KEY `fk_updatedBy_venue_idx` (`updatedBy`),
  KEY `fk_venueTypeVenue_idx` (`venueTypeId`),
  CONSTRAINT `fk_address_venue` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`),
  CONSTRAINT `fk_createdBy_venue` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_updatedBy_venue` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_venueTypeVenue` FOREIGN KEY (`venueTypeId`) REFERENCES `enuvenuetype` (`enuVenueTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venue`
--

LOCK TABLES `venue` WRITE;
/*!40000 ALTER TABLE `venue` DISABLE KEYS */;
INSERT INTO `venue` VALUES (1,'Karnavati Club Ltd','The club provides luxurious 5 star room accommodations as many activities like weddings, parties and other business events take place here. This removes the need to hire rooms in hotels completely. The club has 44 AC Deluxe Rooms & 2 Luxurious Suite rooms which have amenities like TV, Phone, air conditioning etc. The rooms provide a good view of either the swimming pool, or the lush green lawn, or the garden embodied with beautiful towers.',1,18,23.00829,72.50126,'9080000000','karnavati@gmail.com',500,3000,'2022-02-24 16:20:21','2022-02-24 16:20:21',1,NULL,NULL),(2,'Shree Mehfil Restaurant','',2,34,20.00045,20.00045,'9726072010','mehfil@gmail.com',100,50,'2022-02-24 17:52:49','2022-02-24 17:52:49',1,NULL,NULL),(3,'Five Petals Hotel & Banquets','',3,35,35.00060,45.90000,'9090909090','5petals@gmail.com',1000,500,'2022-02-24 17:56:05','2022-02-24 17:56:05',1,NULL,NULL),(4,'CARNIVAL CONFERENCE HALL','',4,36,25.00000,45.78787,'9800099000','carnival@gmail.com',150,50,'2022-02-24 18:00:20','2022-02-24 18:00:20',1,NULL,NULL),(5,'Empire business hub','',5,37,60.67760,67.89898,'8787878787','empire@gmail.com',1000,100,'2022-02-24 18:03:13','2022-02-24 18:03:13',1,NULL,NULL),(6,'Sun Communication','',6,38,20.89990,67.89899,'8989898989','sun@gmail.com',500,50,'2022-02-24 18:10:26','2022-02-24 18:10:26',1,NULL,NULL),(7,'Sardar Patel Stadium','',10,39,45.09000,50.90900,'9898989898','sardar@gmail.com',1500,60,'2022-02-24 18:58:13','2022-02-24 18:58:13',1,NULL,NULL),(8,'Tagore Hall','',5,40,30.78700,45.90900,'7878787878','tagor@gmail.com',500,80,'2022-02-24 19:01:37','2022-02-24 19:01:37',1,NULL,NULL);
/*!40000 ALTER TABLE `venue` ENABLE KEYS */;
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
