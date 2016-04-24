-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 24, 2016 at 02:02 AM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vls1`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_name` varchar(100) NOT NULL,
  `user_pass` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_520_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_name`, `user_pass`) VALUES
('admin', 'eed1be2e97e7c8319c2aa4a22f48abd41bc822ee4f00be1aa7e9d2dc94fc1f30b880ca3454b380dc5a3b72919ffe93ebd7f0d847dd66582c9c1fdfada4e48a93'),
('daniel', 'b9e2970e4f635294550197ab8d17dac2015f07847c6e6594d67039745f33b55d0f40f2c5f7cea5fac4651ade0b14ee13fdf16b4d0c8a5c6124ed3bd42f15a348'),
('doctorStrange', '63858ffe2aae0c23d86e17e1fb0bd8232514f4adeec5d58813c5946f74a232cf67c04a3db06df930487f2ad0bfd2e36bd3fb5fb479d4abbfa8711bb505472703'),
('donald', '09d8d7b2345e48f3dbe42d81883b9cf4a5d2de2264929c0a99d0957fcfba3d697b30bf4b5b3c0218f211a037446fbda831949d46d9a15cc30e503a63474ec4e5'),
('gancho', '4c671c252da5cc91b41ed7af3a5b25a0bcaa2335c8fc54bf25e0b69c02cc6b7e231dfceddab57a80c1ccfe31523cfec4e066d032049baac992f2f10cc74a8aba'),
('jsmith', 'b9e2970e4f635294550197ab8d17dac2015f07847c6e6594d67039745f33b55d0f40f2c5f7cea5fac4651ade0b14ee13fdf16b4d0c8a5c6124ed3bd42f15a348'),
('misho', '7ac3f7e071cd39745ddeca722ae9f5f5ea143c4c65db6c2ee8dbd00f5ff342c739e9026a349449c5827ab66f9c7499510182ff807349b79f593ca931dcf9a24b'),
('robin', '071b38098125700fe49e65ef0059bfda1fbd110472a8d7490e0b3af6a8260712f958b9c7f27be61a8ddfe4fecb71f9e8002360f894f06dd4b1f383963d71db41'),
('steliyan', 'b9e2970e4f635294550197ab8d17dac2015f07847c6e6594d67039745f33b55d0f40f2c5f7cea5fac4651ade0b14ee13fdf16b4d0c8a5c6124ed3bd42f15a348');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_name` varchar(100) NOT NULL,
  `role_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_name`, `role_name`) VALUES
('jsmith', 'customer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_name`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_name`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `users` (`user_name`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
