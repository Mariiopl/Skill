-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS `competicion` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `competicion`;

-- Configuración inicial
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Tabla `especialidad`
DROP TABLE IF EXISTS `especialidad`;
CREATE TABLE `especialidad` (
  `id_especialidad` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Datos de `especialidad`
LOCK TABLES `especialidad` WRITE;
INSERT INTO `especialidad` VALUES 
(1,'DW01','Desarrollo Web'),
(2,'AS03','Administración de Sistemas'),
(3,'EM03','Electromecánica'),
(5,'N01','Neurología'),
(7,'A0012','API'),
(8,'A018','API REST');
UNLOCK TABLES;

-- Tabla `participante`
DROP TABLE IF EXISTS `participante`;
CREATE TABLE `participante` (
  `id_participante` bigint NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) DEFAULT NULL,
  `centro` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `especialidad_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_participante`),
  KEY `FKlxdtv2uddfityuvytebpy1bvx` (`especialidad_id`),
  CONSTRAINT `FKlxdtv2uddfityuvytebpy1bvx` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidad` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Datos de `participante`
LOCK TABLES `participante` WRITE;
INSERT INTO `participante` VALUES 
(1,'Fernández','IES Tecnológico','Carlos',1),
(2,'Gómez','IES Innovación','Lucía',2),
(3,'Martínez','IES Avanzado','Pedro',3);
UNLOCK TABLES;

-- Tabla `prueba`
DROP TABLE IF EXISTS `prueba`;
CREATE TABLE `prueba` (
  `id_prueba` bigint NOT NULL AUTO_INCREMENT,
  `enunciado` varchar(255) DEFAULT NULL,
  `puntuacion_maxima` int DEFAULT NULL,
  `especialidad_id_especialidad` bigint DEFAULT NULL,
  PRIMARY KEY (`id_prueba`),
  KEY `FK7dslb6swly342nlxs0ofbwodu` (`especialidad_id_especialidad`),
  CONSTRAINT `FK7dslb6swly342nlxs0ofbwodu` FOREIGN KEY (`especialidad_id_especialidad`) REFERENCES `especialidad` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Datos de `prueba`
LOCK TABLES `prueba` WRITE;
INSERT INTO `prueba` VALUES 
(1,'Desarrollar una web responsive con Angular',100,7),
(2,'Configurar un servidor Linux con Apache',100,2),
(3,'Reparar un circuito eléctrico en un automóvil',100,1),
(30,'rggfdg',10,2);
UNLOCK TABLES;

-- Tabla `evaluacion`
DROP TABLE IF EXISTS `evaluacion`;
CREATE TABLE `evaluacion` (
  `id_evaluacion` bigint NOT NULL AUTO_INCREMENT,
  `nota_final` double DEFAULT NULL,
  `participante_id_participante` bigint DEFAULT NULL,
  `prueba_id_prueba` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_evaluacion`),
  KEY `FK20salokiook4e907l470vn4lp` (`participante_id_participante`),
  KEY `FKn2ec80472re9cvf84m80j8trs` (`prueba_id_prueba`),
  KEY `FKbx8xhfco0598ofhl3e3lumbtd` (`user_id`),
  CONSTRAINT `FK20salokiook4e907l470vn4lp` FOREIGN KEY (`participante_id_participante`) REFERENCES `participante` (`id_participante`),
  CONSTRAINT `FKbx8xhfco0598ofhl3e3lumbtd` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKn2ec80472re9cvf84m80j8trs` FOREIGN KEY (`prueba_id_prueba`) REFERENCES `prueba` (`id_prueba`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Datos de `evaluacion`
LOCK TABLES `evaluacion` WRITE;
INSERT INTO `evaluacion` VALUES 
(1,85.5,1,1,1,NULL),
(2,92.5,2,2,1,NULL),
(3,6,3,3,1,NULL),
(4,85.6,1,3,2,NULL),
(5,85.7,3,1,1,NULL),
(6,8.5,1,2,1,'Completada');
UNLOCK TABLES;

-- Tabla `item`
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id_item` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `grados_consecuion` int DEFAULT NULL,
  `peso` int DEFAULT NULL,
  `prueba_id_prueba` bigint DEFAULT NULL,
  `id_prueba` bigint DEFAULT NULL,
  `id_evaluacion` bigint DEFAULT NULL,
  PRIMARY KEY (`id_item`),
  KEY `FKvolddppnuko1b59bpuopxgnu` (`prueba_id_prueba`),
  KEY `FKaa8klre0t2vuvt80regagu4hw` (`id_prueba`),
  KEY `FK3w951st890owhnlip3w72kp2c` (`id_evaluacion`),
  CONSTRAINT `FK3w951st890owhnlip3w72kp2c` FOREIGN KEY (`id_evaluacion`) REFERENCES `evaluacion` (`id_evaluacion`),
  CONSTRAINT `FKaa8klre0t2vuvt80regagu4hw` FOREIGN KEY (`id_prueba`) REFERENCES `prueba` (`id_prueba`),
  CONSTRAINT `FKvolddppnuko1b59bpuopxgnu` FOREIGN KEY (`prueba_id_prueba`) REFERENCES `prueba` (`id_prueba`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Datos de `item`
LOCK TABLES `item` WRITE;
INSERT INTO `item` VALUES 
(1,'Diseño de la interfaz',10,30,1,1,NULL),
(5,'Configuración del servidor',10,40,2,1,NULL),
(6,'Montaje y prueba del circuito',10,50,3,2,NULL);
UNLOCK TABLES;

-- Tabla `evaluacion_item`
DROP TABLE IF EXISTS `evaluacion_item`;
CREATE TABLE `evaluacion_item` (
  `id_evaluacion_item` bigint NOT NULL AUTO_INCREMENT,
  `explicacion` varchar(255) DEFAULT NULL,
  `valoracion` int DEFAULT NULL,
  `evaluacion_id_evaluacion` bigint DEFAULT NULL,
  `item_id_item` bigint DEFAULT NULL,
  PRIMARY KEY (`id_evaluacion_item`),
  KEY `FK9j6sdkemx29ihe9v51pk47jf4` (`evaluacion_id_evaluacion`),
  KEY `FK7bp5j00j6feivohwgeldq0ex7` (`item_id_item`),
  CONSTRAINT `FK7bp5j00j6feivohwgeldq0ex7` FOREIGN KEY (`item_id_item`) REFERENCES `item` (`id_item`),
  CONSTRAINT `FK9j6sdkemx29ihe9v51pk47jf4` FOREIGN KEY (`evaluacion_id_evaluacion`) REFERENCES `evaluacion` (`id_evaluacion`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Datos de `evaluacion_item`
LOCK TABLES `evaluacion_item` WRITE;
INSERT INTO `evaluacion_item` VALUES 
(1,'Diseño bien logrado pero con algunos errores de accesibilidad.',4,1,1),
(2,'Servidor configurado correctamente con alta seguridad.',5,2,5),
(3,'El circuito funciona, pero hay detalles de conexión a mejorar.',3,3,6);
UNLOCK TABLES;

-- Tabla `user`
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `especialidad_id_especialidad` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6m109a756qkv6x337w9g8ijxe` (`especialidad_id_especialidad`),
  CONSTRAINT `FK6m109a756qkv6x337w9g8ijxe` FOREIGN KEY (`especialidad_id_especialidad`) REFERENCES `especialidad` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Datos de `user`
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES 
(1,'Pastor Liébanas','12345347A','Mario','$2a$10$aeBwqqv.0SRSqExMDtNC5uyUo7ca4teR1HQgTDHE1szXBTpbgss4u','admin','Mario',NULL),
(2,'Carmona Pedrosa','12345347B','Irene','$2a$10$gs8jiP.g9NXyz.SN9VSHi.34/GVa3EPxc7y5n1u5NS6i/X9Kwv9b2','experto','Irene',2),
(3,'Beltran Santiago','12345347C','Pablo','$2a$10$gVaLmFj6Dl2qpgI1RNvChOqwDi8MzmxAJHZAWthcjZ53Zcp4sMiv.','experto','Pablo',5),
(5,'Rodriguez','12345678D','Carol','$2a$10$X/IUQwJJsWGjhwxZprzr5eMgmmsHIHSBW3Gnw8DXE3FBHSPJG3/ci','experto','Carol',1),
(19,'Pastor','12345678Z','Kai','$2a$10$5Afi0sTuVhuIe3pTAqE0hOdjis24tH9dlrU9su52kVgxP1Iob2Su6','experto','Kai',7),
(20,'Roronoa','12345678A','Kuina','$2a$10$FM/aF5g5mKjJm61LjOpncupgAlBuGdeEVUY0cW7bfKuOMsnvDltie','experto','Kuina',3),
(21,'Rodriguez','12345678A','asda','$2a$10$jsTGKOmHAi4O2njknVQ./.3ll1jKQFdzJ6AZjF2R2.ZZ5LJPMNHc2','experto','K',2);
UNLOCK TABLES;

-- Restaurar configuraciones
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Fin del archivo