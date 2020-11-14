-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: englishdb
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Noun'),(2,'Verb'),(3,'Adjective'),(4,'Adverb');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choice`
--

DROP TABLE IF EXISTS `choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `choice` (
  `id` varchar(100) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `question_id` varchar(100) DEFAULT NULL,
  `is_correct` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_choice_question_idx` (`question_id`),
  CONSTRAINT `fk_choice_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choice`
--

LOCK TABLES `choice` WRITE;
/*!40000 ALTER TABLE `choice` DISABLE KEYS */;
INSERT INTO `choice` VALUES ('064fc3c7-c63f-4210-b899-10ef025a9f53','A','0ecdcd2e-0189-4851-9418-04cd6dddda65',''),('0b33c8d4-9cb3-495a-bde3-7a10b486a578','A','88ad8bc2-5ad9-4b64-a3d3-b3c195b38f4a',''),('11c6129b-b245-43eb-8cb2-fbb66f8d76c1','B','4741f15e-2f1e-4683-884b-f735fb077bb5','\0'),('2a8c03db-9992-420e-ac53-b2fb75f84565','A','ccf0ad25-b9d7-43e9-a532-6d09509cd678',''),('480afdcd-9cec-4545-9760-f75da6665e17','A','744f130e-b8c7-48d3-ad42-e03836a10ccb',''),('832c4b4a-ee1f-45b6-b982-9772d91d44b7','B','8741c142-d1de-4901-8eb4-86634fb1db3f','\0'),('89d2c611-8933-4305-a1cf-c0c64da8f2cc','B','0ecdcd2e-0189-4851-9418-04cd6dddda65','\0'),('8d79d4ed-1aa9-468f-83dd-438455f9ad43','B','ccf0ad25-b9d7-43e9-a532-6d09509cd678','\0'),('a31bb467-3c0f-4cbf-a10f-7dc9eda8f552','B','744f130e-b8c7-48d3-ad42-e03836a10ccb','\0'),('afcdc381-d6c4-48e0-acfd-c8603ef8f30f','A','8741c142-d1de-4901-8eb4-86634fb1db3f',''),('b5453865-bb5b-4055-af91-940034909636','A','4741f15e-2f1e-4683-884b-f735fb077bb5',''),('b74f135c-0730-4678-a20c-1d148ac7e2d0','B','88ad8bc2-5ad9-4b64-a3d3-b3c195b38f4a','\0');
/*!40000 ALTER TABLE `choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` varchar(100) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_question_category_idx` (`category_id`),
  CONSTRAINT `fk_question_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('0ecdcd2e-0189-4851-9418-04cd6dddda65','Test Question',1),('4741f15e-2f1e-4683-884b-f735fb077bb5','Test Question',1),('744f130e-b8c7-48d3-ad42-e03836a10ccb','Test Question',1),('8741c142-d1de-4901-8eb4-86634fb1db3f','Test Question',1),('88ad8bc2-5ad9-4b64-a3d3-b3c195b38f4a','Test Question',1),('ccf0ad25-b9d7-43e9-a532-6d09509cd678','Test Question',1);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-14 10:04:27
