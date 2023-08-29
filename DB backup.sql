-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 06, 2021 at 08:55 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cms`
--

-- --------------------------------------------------------

--
-- Table structure for table `acc`
--

CREATE TABLE `acc` (
  `uid` text NOT NULL,
  `name` text NOT NULL,
  `branch` text NOT NULL,
  `pwd` text NOT NULL,
  `roll` text NOT NULL,
  `contact` text NOT NULL,
  `p_link` text NOT NULL,
  `type` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `acc`
--

INSERT INTO `acc` (`uid`, `name`, `branch`, `pwd`, `roll`, `contact`, `p_link`, `type`) VALUES
('admin', 'Admin', 'None', 'admin', '00', '00-000-0000', '', 'Institute'),
('20104075', 'Akash', 'IT', '20104075@apsit', '32', '000000000', '', 'Student'),
('20104077', 'Amir', 'Mechanical', '20104077@apsit', '', '', '', 'Student'),
('20104078', 'Pranil', 'IT', '20104078@apsit', '', '', '', 'Student'),
('20104079', 'Pratik', 'Computer', '20104079@apsit', '', '', '', 'Student'),
('20104080', 'Akash patil', 'IT', '20104080@apsit', '', '', '', 'Student'),
('001', 'Kunal', 'Civil', '001@apsit', '88', '8097866765', '', 'Student'),
('20104040', 'Pranil', 'Computer', 'Pranil@123', '03', '8888888888', '', 'Student'),
('20104010', 'Sahil', 'Mechanical', 'Sahil@123', '31', '', '', 'Student'),
('99999999', 'Anagha', 'IT', 'Akash@123', '99', '9874563210', '', 'Student'),
('20104115', 'Mansi Virangama', 'IT', '20104115@apsit', '01', '', '', 'Student');

-- --------------------------------------------------------

--
-- Table structure for table `b_list`
--

CREATE TABLE `b_list` (
  `branch` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `b_list`
--

INSERT INTO `b_list` (`branch`) VALUES
('IT'),
('CIVIL'),
('Mechanical'),
('Computer');

-- --------------------------------------------------------

--
-- Table structure for table `c_list`
--

CREATE TABLE `c_list` (
  `branch` text NOT NULL,
  `certi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `c_list`
--

INSERT INTO `c_list` (`branch`, `certi`) VALUES
('Computer', 'python'),
('Mechanical', 'gear'),
('Mechanical', 'transmission'),
('IT', 'Oracle JFO'),
('IT', 'Oracle DFO'),
('IT', 'Hacker Rank'),
('IT', 'Apsit Skills'),
('IT', 'Google Cloud'),
('IT', 'Cisco'),
('IT', 'gccf');

-- --------------------------------------------------------

--
-- Table structure for table `std_certi`
--

CREATE TABLE `std_certi` (
  `uid` text NOT NULL,
  `certi` text NOT NULL,
  `c_link` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `std_certi`
--

INSERT INTO `std_certi` (`uid`, `certi`, `c_link`) VALUES
('20104040', 'python', 'E:$Akash$Java$Java$docs$Computer$python$20104040.jpg'),
('20104010', 'machines', 'E:$Akash$Java$Java$docs$Mechanical$machines$20104010.jpg'),
('20104010', 'gear', 'E:$Akash$Java$Java$docs$Mechanical$gear$20104010.jpg'),
('20104010', 'transmission', 'E:$Akash$Java$Java$docs$Mechanical$transmission$20104010.jpg'),
('99999999', 'Cisco', 'E:$Akash$Java$Java$docs$IT$Cisco$99999999.jpg'),
('99999999', 'Oracle JFO', 'E:$Akash$Java$Java$docs$IT$Oracle JFO$99999999.jpg'),
('99999999', 'Hacker Rank', 'E:$Akash$Java$Java$docs$IT$Hacker Rank$99999999.jpg'),
('20104115', 'gccf', 'E:$Akash$Java$Java$docs$IT$gccf$20104115.jpg');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
