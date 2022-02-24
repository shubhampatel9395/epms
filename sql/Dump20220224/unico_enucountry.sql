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
-- Table structure for table `enucountry`
--

DROP TABLE IF EXISTS `enucountry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enucountry` (
  `countryId` int unsigned NOT NULL AUTO_INCREMENT,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY (`countryId`),
  UNIQUE KEY `countryId_UNIQUE` (`countryId`)
) ENGINE=InnoDB AUTO_INCREMENT=251 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enucountry`
--

LOCK TABLES `enucountry` WRITE;
/*!40000 ALTER TABLE `enucountry` DISABLE KEYS */;
INSERT INTO `enucountry` VALUES (1,'Afghanistan'),(2,'Aland Islands'),(3,'Albania'),(4,'Algeria'),(5,'American Samoa'),(6,'Andorra'),(7,'Angola'),(8,'Anguilla'),(9,'Antarctica'),(10,'Antigua And Barbuda'),(11,'Argentina'),(12,'Armenia'),(13,'Aruba'),(14,'Australia'),(15,'Austria'),(16,'Azerbaijan'),(17,'Bahamas The'),(18,'Bahrain'),(19,'Bangladesh'),(20,'Barbados'),(21,'Belarus'),(22,'Belgium'),(23,'Belize'),(24,'Benin'),(25,'Bermuda'),(26,'Bhutan'),(27,'Bolivia'),(28,'Bosnia and Herzegovina'),(29,'Botswana'),(30,'Bouvet Island'),(31,'Brazil'),(32,'British Indian Ocean Territory'),(33,'Brunei'),(34,'Bulgaria'),(35,'Burkina Faso'),(36,'Burundi'),(37,'Cambodia'),(38,'Cameroon'),(39,'Canada'),(40,'Cape Verde'),(41,'Cayman Islands'),(42,'Central African Republic'),(43,'Chad'),(44,'Chile'),(45,'China'),(46,'Christmas Island'),(47,'Cocos (Keeling) Islands'),(48,'Colombia'),(49,'Comoros'),(50,'Congo'),(51,'Democratic Republic of the Congo'),(52,'Cook Islands'),(53,'Costa Rica'),(54,'Cote D\'Ivoire (Ivory Coast)'),(55,'Croatia'),(56,'Cuba'),(57,'Cyprus'),(58,'Czech Republic'),(59,'Denmark'),(60,'Djibouti'),(61,'Dominica'),(62,'Dominican Republic'),(63,'East Timor'),(64,'Ecuador'),(65,'Egypt'),(66,'El Salvador'),(67,'Equatorial Guinea'),(68,'Eritrea'),(69,'Estonia'),(70,'Ethiopia'),(71,'Falkland Islands'),(72,'Faroe Islands'),(73,'Fiji Islands'),(74,'Finland'),(75,'France'),(76,'French Guiana'),(77,'French Polynesia'),(78,'French Southern Territories'),(79,'Gabon'),(80,'Gambia The'),(81,'Georgia'),(82,'Germany'),(83,'Ghana'),(84,'Gibraltar'),(85,'Greece'),(86,'Greenland'),(87,'Grenada'),(88,'Guadeloupe'),(89,'Guam'),(90,'Guatemala'),(91,'Guernsey and Alderney'),(92,'Guinea'),(93,'Guinea-Bissau'),(94,'Guyana'),(95,'Haiti'),(96,'Heard Island and McDonald Islands'),(97,'Honduras'),(98,'Hong Kong S.A.R.'),(99,'Hungary'),(100,'Iceland'),(101,'India'),(102,'Indonesia'),(103,'Iran'),(104,'Iraq'),(105,'Ireland'),(106,'Israel'),(107,'Italy'),(108,'Jamaica'),(109,'Japan'),(110,'Jersey'),(111,'Jordan'),(112,'Kazakhstan'),(113,'Kenya'),(114,'Kiribati'),(115,'North Korea'),(116,'South Korea'),(117,'Kuwait'),(118,'Kyrgyzstan'),(119,'Laos'),(120,'Latvia'),(121,'Lebanon'),(122,'Lesotho'),(123,'Liberia'),(124,'Libya'),(125,'Liechtenstein'),(126,'Lithuania'),(127,'Luxembourg'),(128,'Macau S.A.R.'),(129,'Macedonia'),(130,'Madagascar'),(131,'Malawi'),(132,'Malaysia'),(133,'Maldives'),(134,'Mali'),(135,'Malta'),(136,'Man (Isle of)'),(137,'Marshall Islands'),(138,'Martinique'),(139,'Mauritania'),(140,'Mauritius'),(141,'Mayotte'),(142,'Mexico'),(143,'Micronesia'),(144,'Moldova'),(145,'Monaco'),(146,'Mongolia'),(147,'Montenegro'),(148,'Montserrat'),(149,'Morocco'),(150,'Mozambique'),(151,'Myanmar'),(152,'Namibia'),(153,'Nauru'),(154,'Nepal'),(155,'Bonaire, Sint Eustatius and Saba'),(156,'Netherlands'),(157,'New Caledonia'),(158,'New Zealand'),(159,'Nicaragua'),(160,'Niger'),(161,'Nigeria'),(162,'Niue'),(163,'Norfolk Island'),(164,'Northern Mariana Islands'),(165,'Norway'),(166,'Oman'),(167,'Pakistan'),(168,'Palau'),(169,'Palestinian Territory Occupied'),(170,'Panama'),(171,'Papua new Guinea'),(172,'Paraguay'),(173,'Peru'),(174,'Philippines'),(175,'Pitcairn Island'),(176,'Poland'),(177,'Portugal'),(178,'Puerto Rico'),(179,'Qatar'),(180,'Reunion'),(181,'Romania'),(182,'Russia'),(183,'Rwanda'),(184,'Saint Helena'),(185,'Saint Kitts And Nevis'),(186,'Saint Lucia'),(187,'Saint Pierre and Miquelon'),(188,'Saint Vincent And The Grenadines'),(189,'Saint-Barthelemy'),(190,'Saint-Martin (French part)'),(191,'Samoa'),(192,'San Marino'),(193,'Sao Tome and Principe'),(194,'Saudi Arabia'),(195,'Senegal'),(196,'Serbia'),(197,'Seychelles'),(198,'Sierra Leone'),(199,'Singapore'),(200,'Slovakia'),(201,'Slovenia'),(202,'Solomon Islands'),(203,'Somalia'),(204,'South Africa'),(205,'South Georgia'),(206,'South Sudan'),(207,'Spain'),(208,'Sri Lanka'),(209,'Sudan'),(210,'Suriname'),(211,'Svalbard And Jan Mayen Islands'),(212,'Swaziland'),(213,'Sweden'),(214,'Switzerland'),(215,'Syria'),(216,'Taiwan'),(217,'Tajikistan'),(218,'Tanzania'),(219,'Thailand'),(220,'Togo'),(221,'Tokelau'),(222,'Tonga'),(223,'Trinidad And Tobago'),(224,'Tunisia'),(225,'Turkey'),(226,'Turkmenistan'),(227,'Turks And Caicos Islands'),(228,'Tuvalu'),(229,'Uganda'),(230,'Ukraine'),(231,'United Arab Emirates'),(232,'United Kingdom'),(233,'United States'),(234,'United States Minor Outlying Islands'),(235,'Uruguay'),(236,'Uzbekistan'),(237,'Vanuatu'),(238,'Vatican City State (Holy See)'),(239,'Venezuela'),(240,'Vietnam'),(241,'Virgin Islands (British)'),(242,'Virgin Islands (US)'),(243,'Wallis And Futuna Islands'),(244,'Western Sahara'),(245,'Yemen'),(246,'Zambia'),(247,'Zimbabwe'),(248,'Kosovo'),(249,'Curaçao'),(250,'Sint Maarten (Dutch part)');
/*!40000 ALTER TABLE `enucountry` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 19:52:02
