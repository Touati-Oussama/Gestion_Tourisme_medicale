-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 01, 2023 at 12:33 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tourisme_medical`
--

-- --------------------------------------------------------

--
-- Table structure for table `appartment_meuble`
--

CREATE TABLE `appartment_meuble` (
  `id` int(25) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `nbchambre` int(25) NOT NULL,
  `ville` varchar(25) NOT NULL,
  `prix_chambre` double NOT NULL,
  `vide` tinyint(1) NOT NULL,
  `adresse` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appartment_meuble`
--

INSERT INTO `appartment_meuble` (`id`, `nom`, `nbchambre`, `ville`, `prix_chambre`, `vide`, `adresse`) VALUES
(1, 'Appartment 1', 2, 'SOUSSE', 150, 1, 'Khzema'),
(2, 'Appartment 2', 3, 'SOUSSE', 200, 1, 'Sahloul 4'),
(4, 'Appartment 3', 2, 'SOUSSE', 90, 1, 'Sousee Beb Bhar');

-- --------------------------------------------------------

--
-- Table structure for table `chambre_clinique`
--

CREATE TABLE `chambre_clinique` (
  `id` int(25) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `vide` tinyint(1) NOT NULL,
  `id_clinique` int(25) NOT NULL,
  `nbLits` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chambre_clinique`
--

INSERT INTO `chambre_clinique` (`id`, `nom`, `vide`, `id_clinique`, `nbLits`) VALUES
(1, 'Chambre 1', 1, 47, 2),
(2, 'Chambre 2', 1, 47, 2),
(3, 'Chambre 3', 1, 47, 2),
(4, 'Chambre 4', 1, 47, 2),
(5, 'Chambre 1', 1, 49, 1),
(6, 'Chambre 2', 1, 49, 2),
(7, 'Chambre 3', 1, 49, 3),
(8, 'Chambre 4', 1, 49, 2),
(9, 'Chambre 5', 1, 49, 1),
(10, 'Chambre 1', 1, 50, 3),
(11, 'Chambre 1', 1, 50, 2),
(12, 'Chambre 1', 1, 50, 1);

-- --------------------------------------------------------

--
-- Table structure for table `chambre_hotel`
--

CREATE TABLE `chambre_hotel` (
  `id` int(25) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `vide` tinyint(1) NOT NULL,
  `id_hotel` int(25) NOT NULL,
  `superficie` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chambre_hotel`
--

INSERT INTO `chambre_hotel` (`id`, `nom`, `vide`, `id_hotel`, `superficie`) VALUES
(2, 'Chambre 1', 0, 3, 90),
(3, 'Chambre 2', 1, 3, 90),
(4, 'Chambre 3', 1, 3, 90),
(5, 'Chambre 4', 1, 3, 90),
(6, 'Chambre 5', 0, 3, 90),
(7, 'Chambre 1', 1, 5, 90),
(8, 'Chambre 2', 1, 5, 90),
(9, 'Chambre 3', 1, 5, 90),
(10, 'Chambre 1', 1, 6, 90),
(11, 'Chambre 2', 1, 6, 90),
(12, 'Chambre 3', 1, 6, 90),
(14, 'Chambre 2', 1, 7, 90),
(15, 'Chambre 3', 1, 7, 90),
(16, 'Chambre 1', 1, 8, 90),
(17, 'Chambre 2', 1, 8, 90),
(18, 'Chambre 1', 1, 9, 90),
(19, 'Chambre 1', 1, 10, 90),
(20, 'Chambre 1', 1, 11, 90),
(21, 'Chambre 1', 1, 12, 90),
(22, 'Chambre 1', 1, 13, 90);

-- --------------------------------------------------------

--
-- Table structure for table `chirurgie`
--

CREATE TABLE `chirurgie` (
  `id` int(25) NOT NULL,
  `prix` double NOT NULL,
  `specialite_id` int(25) NOT NULL,
  `type` varchar(25) NOT NULL,
  `duree` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chirurgie`
--

INSERT INTO `chirurgie` (`id`, `prix`, `specialite_id`, `type`, `duree`) VALUES
(1, 2000, 13, 'Chirurgie laser', 3),
(3, 1000, 13, 'Chirurgie des ongles', 1),
(4, 2000, 7, 'Chirurgie de la cataracte', 3),
(5, 1500, 7, 'Chirurgie du glaucome', 2);

-- --------------------------------------------------------

--
-- Table structure for table `clinique`
--

CREATE TABLE `clinique` (
  `id` int(25) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `adresse` varchar(60) NOT NULL,
  `telephone` int(25) NOT NULL,
  `email` varchar(60) NOT NULL,
  `prix_chambre` double NOT NULL,
  `ville` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `clinique`
--

INSERT INTO `clinique` (`id`, `nom`, `adresse`, `telephone`, `email`, `prix_chambre`, `ville`) VALUES
(47, 'El Yosr', 'Avenue Ibn El Jazzar', 501472581, 'elyosr@gmail.com', 100, 'SOUSSE'),
(49, 'Les oliviers', 'Boulevard du 14 Janvier', 73242711, 'cliniquelesoliviers.net', 120, 'SOUSSE'),
(50, 'Pasteur', ' Centre Urbain Nord', 36402000, 'clinique.pasteur@gmail.com', 160, 'TUNIS');

-- --------------------------------------------------------

--
-- Table structure for table `hotel`
--

CREATE TABLE `hotel` (
  `id` int(25) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `adresse` varchar(25) NOT NULL,
  `telephone` int(25) NOT NULL,
  `ville` varchar(25) NOT NULL,
  `email` varchar(25) NOT NULL,
  `prix_chambre` double NOT NULL,
  `categorie` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`id`, `nom`, `adresse`, `telephone`, `ville`, `email`, `prix_chambre`, `categorie`) VALUES
(3, 'Movenpick', 'kantoui Sousse', 2222222, 'SOUSSE', 'movenpick@gmail.com', 250, 5),
(5, 'Sousse Pearl Marriott Res', 'Abdelhamid El Kadhi Avenu', 73104000, 'SOUSSE', 'pearl.m@gmail.com', 150, 5),
(6, 'Sousse Palace', 'Avenue Hbib bourgiba', 1111111, 'SOUSSE', 'palace@gmail.com', 120, 4),
(7, 'El Kaiser', 'Boulevard Taib MHiri', 22222222, 'SOUSSE', 'elkaiser@gmail.com', 100, 3),
(8, 'Hotel Le Sultan', 'Route Touristique', 77777777, 'HAMMAMET', 'lesultan@gmail.com', 175, 5),
(9, 'Medina Belisaire & Thalas', 'Rue De La Medina Yasmine ', 14725836, 'HAMMAMET', 'medina@gmail.com', 120, 4),
(10, 'Magic Hotel Manar', 'Route Touristique Mrezga', 14736925, 'HAMMAMET', 'magic@gmail.com', 200, 5),
(11, 'Hotel Belvedere Fourati', '10 Avenue des Etats Unis ', 12345678, 'TUNIS', 'fourati@gmail.com', 200, 5),
(12, 'Dar El Jeld Hotel & Spa', '10 Rue Dar El Jeld', 10203040, 'TUNIS', 'hotel@gmail.com', 160, 4),
(13, 'Hotel TIBA', '4 Ali Bach Hamba', 25814796, 'TUNIS', 'hotel.tiba@gmail.com', 90, 3);

-- --------------------------------------------------------

--
-- Table structure for table `medicin`
--

CREATE TABLE `medicin` (
  `id` int(25) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `prenom` varchar(25) NOT NULL,
  `telephone` int(25) NOT NULL,
  `dateNaiss` date NOT NULL,
  `email` varchar(25) NOT NULL,
  `sexe` varchar(25) NOT NULL,
  `specialite_id` int(25) NOT NULL,
  `clinique_id` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicin`
--

INSERT INTO `medicin` (`id`, `nom`, `prenom`, `telephone`, `dateNaiss`, `email`, `sexe`, `specialite_id`, `clinique_id`) VALUES
(1, 'Medicin1', 'Med1', 12345, '2023-11-22', 'med@gmail.com', 'HOMME', 13, 47),
(3, 'Med2', 'Med2', 10203040, '2023-11-16', 'med2@gmail.com', 'HOMME', 13, 47),
(5, 'Medicin3', 'Medicin3', 58731605, '2023-11-13', 'med3@gmail.com', 'HOMME', 7, 49);

-- --------------------------------------------------------

--
-- Table structure for table `medicin_chirurgie`
--

CREATE TABLE `medicin_chirurgie` (
  `medicin_id` int(11) NOT NULL,
  `chirurgie_id` int(11) NOT NULL,
  `reduction` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicin_chirurgie`
--

INSERT INTO `medicin_chirurgie` (`medicin_id`, `chirurgie_id`, `reduction`) VALUES
(1, 1, 0.5),
(1, 3, 0.3),
(3, 1, 0.2);

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `id` int(11) NOT NULL,
  `nom` varchar(11) NOT NULL,
  `prenom` varchar(25) NOT NULL,
  `dateNaiss` date NOT NULL,
  `email` varchar(25) NOT NULL,
  `sexe` varchar(25) NOT NULL,
  `nationalite` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id`, `nom`, `prenom`, `dateNaiss`, `email`, `sexe`, `nationalite`) VALUES
(1, 'Touati', 'Oussama', '2023-11-01', 'oussma@gmail.com', 'HOMME', 'ALLEMAGNE'),
(2, 'Touati', 'Ahmed', '2023-11-01', 'ahmed@gmail.com', 'HOMME', 'TUNISIA');

-- --------------------------------------------------------

--
-- Table structure for table `rendez_vous`
--

CREATE TABLE `rendez_vous` (
  `id` int(11) NOT NULL,
  `date_debut` date NOT NULL,
  `prix_total` float NOT NULL,
  `chambre_hotel_id` int(11) DEFAULT NULL,
  `appartment_id` int(11) DEFAULT NULL,
  `chambre_clinique_id` int(11) DEFAULT NULL,
  `chirurgie_id` int(11) DEFAULT NULL,
  `soin_id` int(11) DEFAULT NULL,
  `medicin_id` int(11) NOT NULL,
  `clinique_id` int(11) NOT NULL,
  `date_fin` date DEFAULT NULL,
  `patient_id` int(11) DEFAULT NULL,
  `heure` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rendez_vous`
--

INSERT INTO `rendez_vous` (`id`, `date_debut`, `prix_total`, `chambre_hotel_id`, `appartment_id`, `chambre_clinique_id`, `chirurgie_id`, `soin_id`, `medicin_id`, `clinique_id`, `date_fin`, `patient_id`, `heure`) VALUES
(41, '2023-12-01', 1750, 2, NULL, NULL, 1, NULL, 1, 47, '2023-12-04', 1, NULL),
(42, '2023-12-01', 60, NULL, NULL, NULL, NULL, 2, 3, 47, NULL, 2, '8H'),
(43, '2023-12-30', 60, NULL, NULL, NULL, NULL, 2, 3, 47, NULL, 2, '10h');

-- --------------------------------------------------------

--
-- Table structure for table `soin_medicale`
--

CREATE TABLE `soin_medicale` (
  `id` int(25) NOT NULL,
  `prix` double NOT NULL,
  `specialite_id` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `soin_medicale`
--

INSERT INTO `soin_medicale` (`id`, `prix`, `specialite_id`) VALUES
(1, 1500, 7),
(2, 60, 13);

-- --------------------------------------------------------

--
-- Table structure for table `specialite`
--

CREATE TABLE `specialite` (
  `id` int(25) NOT NULL,
  `specialite` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `specialite`
--

INSERT INTO `specialite` (`id`, `specialite`) VALUES
(4, 'Pediatrie'),
(5, 'Psychatrie'),
(6, 'Chirurgie generale'),
(7, 'Ophtalmologie'),
(8, 'Oncologie'),
(9, 'Nephrologie'),
(11, 'Rhumatologie'),
(13, 'Dermatologie');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appartment_meuble`
--
ALTER TABLE `appartment_meuble`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `chambre_clinique`
--
ALTER TABLE `chambre_clinique`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `chambre_hotel`
--
ALTER TABLE `chambre_hotel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `chirurgie`
--
ALTER TABLE `chirurgie`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `clinique`
--
ALTER TABLE `clinique`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `medicin`
--
ALTER TABLE `medicin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `medicin_chirurgie`
--
ALTER TABLE `medicin_chirurgie`
  ADD PRIMARY KEY (`medicin_id`,`chirurgie_id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rendez_vous`
--
ALTER TABLE `rendez_vous`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_chambre_clinique` (`chambre_clinique_id`),
  ADD KEY `FK_chambre_hotel` (`chambre_hotel_id`),
  ADD KEY `FK_appartment` (`appartment_id`),
  ADD KEY `FK_chirurgie_id` (`chirurgie_id`),
  ADD KEY `FK_soin_id` (`soin_id`),
  ADD KEY `FK_medicin_id` (`medicin_id`),
  ADD KEY `FK_clinique_id` (`clinique_id`),
  ADD KEY `FK_patient_id` (`patient_id`);

--
-- Indexes for table `soin_medicale`
--
ALTER TABLE `soin_medicale`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `specialite`
--
ALTER TABLE `specialite`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appartment_meuble`
--
ALTER TABLE `appartment_meuble`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `chambre_clinique`
--
ALTER TABLE `chambre_clinique`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `chambre_hotel`
--
ALTER TABLE `chambre_hotel`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `chirurgie`
--
ALTER TABLE `chirurgie`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `clinique`
--
ALTER TABLE `clinique`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `medicin`
--
ALTER TABLE `medicin`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `rendez_vous`
--
ALTER TABLE `rendez_vous`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `soin_medicale`
--
ALTER TABLE `soin_medicale`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `specialite`
--
ALTER TABLE `specialite`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rendez_vous`
--
ALTER TABLE `rendez_vous`
  ADD CONSTRAINT `FK_appartment` FOREIGN KEY (`appartment_id`) REFERENCES `appartment_meuble` (`id`),
  ADD CONSTRAINT `FK_chambre_clinique` FOREIGN KEY (`chambre_clinique_id`) REFERENCES `chambre_clinique` (`id`),
  ADD CONSTRAINT `FK_chambre_hotel` FOREIGN KEY (`chambre_hotel_id`) REFERENCES `chambre_hotel` (`id`),
  ADD CONSTRAINT `FK_chirurgie_id` FOREIGN KEY (`chirurgie_id`) REFERENCES `chirurgie` (`id`),
  ADD CONSTRAINT `FK_clinique_id` FOREIGN KEY (`clinique_id`) REFERENCES `clinique` (`id`),
  ADD CONSTRAINT `FK_medicin_id` FOREIGN KEY (`medicin_id`) REFERENCES `medicin` (`id`),
  ADD CONSTRAINT `FK_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  ADD CONSTRAINT `FK_soin_id` FOREIGN KEY (`soin_id`) REFERENCES `soin_medicale` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
