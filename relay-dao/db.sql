-- phpMyAdmin SQL Dump
-- version 3.3.9.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 21 lug, 2014 at 11:15 AM
-- Versione MySQL: 5.5.9
-- Versione PHP: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `landcycle`
--
--191337 

-- --------------------------------------------------------

--
-- Struttura della tabella `forsale`
--

CREATE TABLE `Forsale` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `img` varchar(100) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `tags` varchar(100) DEFAULT NULL,
  `lat` double(10,8) DEFAULT NULL,
  `mailvend` varchar(80) DEFAULT NULL,
  `mailacq` varchar(80) DEFAULT NULL,
  `lng` double(10,8) DEFAULT NULL,
  `optional` varchar(100) DEFAULT NULL,
  `city` varchar(150) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `User` (
  `mail` varchar(80) NOT NULL,
  `firstName` varchar(80) DEFAULT NULL,
  `lastName` varchar(80) DEFAULT NULL,
  `avatar` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- phpMyAdmin SQL Dump
-- version 3.3.9.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 21 lug, 2014 at 11:26 AM
-- Versione MySQL: 5.5.9
-- Versione PHP: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: 'landcycle'
--

--
-- Dump dei dati per la tabella 'forsale'
--

INSERT INTO Forsale VALUES('118908328e-d3e2-469b-b515-1a5e032d6a82', NULL, 'http://localhost:8080/img/webapps/img/118908328e-d3e2-469b-b515-1a5e032d6a82.jpg', 'test', NULL, 45.31940770, 'valerio.artusi@gmail.com', NULL, 9.52376820, NULL, NULL);
INSERT INTO Forsale VALUES('228908328e-d3e2-469b-b515-1a5e032d6a82', NULL, 'http://localhost:8080/img/webapps/img/228908328e-d3e2-469b-b515-1a5e032d6a82.jpg', 'test', NULL, 45.16044240, 'valerio.artusi@gmail.com', NULL, 9.69353570, NULL, NULL);
INSERT INTO Forsale VALUES('8908328e-d3e2-469b-b515-1a5e032d6a82', NULL, 'http://localhost:8080/img/webapps/img/8908328e-d3e2-469b-b515-1a5e032d6a82.jpg', 'test', NULL, 45.31940770, 'valerio.artusi@gmail.com', NULL, 9.52376820, NULL, NULL);
INSERT INTO Forsale VALUES('fb0548ca-8fd1-4ef3-9fe8-fdaac4315fd8', NULL, 'http://localhost:8080/img/webapps/img/fb0548ca-8fd1-4ef3-9fe8-fdaac4315fd8.jpg', 'test', NULL, 45.57055440, 'massimiliano.regis@gmail.com', NULL, 8.05484050, NULL, NULL);
INSERT INTO Forsale VALUES('fb0548ca-8fd1-4ef3-9fe8-fdaac4315fd9', NULL, 'http://localhost:8080/img/webapps/img/fb0548ca-8fd1-4ef3-9fe8-fdaac4315fd9.jpg', 'test', NULL, 45.57055440, 'massimiliano.regis@gmail.com', 'valerioz.artusi@gmail.com', 8.05484050, NULL, NULL);

--
-- Dump dei dati per la tabella 'user'
--

INSERT INTO `User` VALUES('massimiliano.regis@gmail.com', 'max', NULL, NULL);
INSERT INTO `User` VALUES('valerio.artusi@gmail.com', NULL, NULL, NULL);
