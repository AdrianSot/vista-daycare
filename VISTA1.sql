-- MySQL dump 10.17  Distrib 10.3.13-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: VISTA
-- ------------------------------------------------------
-- Server version	10.3.13-MariaDB-1:10.3.13+maria~bionic

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Administradores`
--

DROP TABLE IF EXISTS `Administradores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Administradores` (
  `Usuario` varchar(20) NOT NULL,
  `Contrasena` varchar(20) DEFAULT NULL,
  `Nombre` varchar(20) DEFAULT NULL,
  `Apellido` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Administradores`
--

LOCK TABLES `Administradores` WRITE;
/*!40000 ALTER TABLE `Administradores` DISABLE KEYS */;
INSERT INTO `Administradores` VALUES ('Admin02','2jH91228dbz,.-k,0P+','Quentin','Tarantino'),('Admin_Master','s{*+-1QlsHvH76Gf029?','Alfonso','Cuaron');
/*!40000 ALTER TABLE `Administradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ninos`
--

DROP TABLE IF EXISTS `Ninos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ninos` (
  `ID` varchar(5) NOT NULL,
  `Nombre` varchar(30) DEFAULT NULL,
  `Apellido` varchar(30) DEFAULT NULL,
  `Foto` varchar(100) DEFAULT NULL,
  `Tutor` varchar(12) DEFAULT NULL,
  `Autorizados` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ninos`
--

LOCK TABLES `Ninos` WRITE;
/*!40000 ALTER TABLE `Ninos` DISABLE KEYS */;
INSERT INTO `Ninos` VALUES ('17392','Fernando','Moreno','/home/blabla/Carpeta-Caras-Ninos/17392.jpg','6612345335','/home/blablabla/Autorizados-17392.txt'),('73648','Nahomi Adriana','Soto','/home/blabla/Carpeta-Caras-Ninos/73648.jpg','6623547586','/home/blabla/Autorizados-73648.txt'),('74633','Agustin','De Iturbide','/home/blabla/Carpeta-Caras-Ninos/74633.jpg','6221052333','/home/blabla/Autorizados-74633.txt'),('84726','Ivan JR','Moreno','/home/blabla/Carpeta-Caras-Ninos/84726.jpg','6612345335','/home/blablabla/Autorizados-84726.txt');
/*!40000 ALTER TABLE `Ninos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Recepcionistas`
--

DROP TABLE IF EXISTS `Recepcionistas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Recepcionistas` (
  `Usuario` varchar(10) NOT NULL,
  `Contrasena` varchar(20) DEFAULT NULL,
  `Nombre` varchar(20) DEFAULT NULL,
  `Apellido` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Recepcionistas`
--

LOCK TABLES `Recepcionistas` WRITE;
/*!40000 ALTER TABLE `Recepcionistas` DISABLE KEYS */;
INSERT INTO `Recepcionistas` VALUES ('Recep01','J9237189sL,.','Pablo','Escobar'),('Recep02','jsdsj8H','Joaquin','Guzman'),('Recep03','277465281-.*','Quentin','Tarantino');
/*!40000 ALTER TABLE `Recepcionistas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tutores`
--

DROP TABLE IF EXISTS `Tutores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tutores` (
  `Telefono` varchar(12) NOT NULL,
  `Nombre` varchar(12) DEFAULT NULL,
  `Apellido` varchar(20) DEFAULT NULL,
  `Foto` varchar(100) NOT NULL,
  `Ninos` varchar(100) DEFAULT NULL,
  `Estatus` enum('Tutor','Autorizado','Sospechoso') DEFAULT NULL,
  PRIMARY KEY (`Foto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tutores`
--

LOCK TABLES `Tutores` WRITE;
/*!40000 ALTER TABLE `Tutores` DISABLE KEYS */;
INSERT INTO `Tutores` VALUES ('6621052333','Pedrito','Hernandez','/home/blabla/Carpeta-Caras-Autorizados/6621052333.jpg','/home/blabla/ID-Ninos-6621052333.txt','Autorizado'),('00000','','','/home/blabla/Carpeta-Caras-Sospechosos/00000.jpg','','Sospechoso'),('6612345335','Fernanda','Dominguez','/home/blabla/Carpeta-Caras-Tutores/6612345335.jpg','/home/blabla/ID-Ninos-6612345335.txt','Tutor');
/*!40000 ALTER TABLE `Tutores` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-15 18:00:16
