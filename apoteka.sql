-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 01, 2026 at 03:51 PM
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
-- Database: `apoteka`
--

-- --------------------------------------------------------

--
-- Table structure for table `lijek`
--

CREATE TABLE `lijek` (
  `id` int(11) NOT NULL,
  `atc_sifra` varchar(40) NOT NULL,
  `naziv` varchar(100) NOT NULL,
  `cijena` decimal(10,2) NOT NULL,
  `proizvodjac` varchar(100) NOT NULL,
  `na_stanju` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lijek`
--

INSERT INTO `lijek` (`id`, `atc_sifra`, `naziv`, `cijena`, `proizvodjac`, `na_stanju`) VALUES
(2, 'CD-12', 'Paracetamol', 3.95, 'srbija', 20);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `userRole` varchar(200) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `dob` varchar(50) DEFAULT NULL,
  `mobileNumber` varchar(50) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `adress` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `userRole`, `name`, `dob`, `mobileNumber`, `email`, `password`, `adress`) VALUES
(3, 'Farmaceut', 'Nigger', '25-01-2007', '0620012301', 'neko@gmail.com', '123kuracA@', 'Sarajevo Alipašino polje'),
(5, 'Vlasnik', 'Mili', '25-01-2007', '0613214213', 'neko@gmail.com', 'password', 'adress');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `lijek`
--
ALTER TABLE `lijek`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `naziv` (`naziv`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `lijek`
--
ALTER TABLE `lijek`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
