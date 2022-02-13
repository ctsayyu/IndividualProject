-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 13, 2022 at 01:44 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inixtraining`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_detail_kelas`
--

CREATE TABLE `tb_detail_kelas` (
  `id_detail_kls` int(11) NOT NULL,
  `id_kls` int(11) NOT NULL,
  `id_pst` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_detail_kelas`
--

INSERT INTO `tb_detail_kelas` (`id_detail_kls`, `id_kls`, `id_pst`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 7),
(4, 1, 9),
(5, 1, 16),
(6, 1, 10),
(7, 2, 2),
(8, 2, 3),
(9, 2, 14),
(10, 2, 13),
(11, 3, 4),
(12, 3, 7),
(13, 3, 11),
(14, 3, 15),
(15, 3, 21),
(16, 4, 1),
(17, 4, 3),
(18, 4, 6),
(19, 4, 10),
(20, 4, 17),
(21, 4, 18),
(22, 5, 7),
(23, 5, 9),
(24, 5, 13),
(25, 5, 19),
(26, 5, 20),
(27, 6, 3),
(28, 6, 4),
(29, 6, 14),
(30, 6, 15),
(31, 6, 17),
(32, 6, 19),
(33, 7, 1),
(34, 7, 2),
(35, 7, 3),
(36, 7, 9),
(37, 7, 10),
(38, 7, 11),
(39, 8, 5),
(40, 8, 6),
(41, 8, 7),
(42, 8, 8),
(43, 8, 15),
(44, 8, 16),
(45, 8, 17),
(46, 9, 2),
(47, 9, 4),
(48, 9, 11),
(49, 10, 5),
(50, 10, 8),
(51, 10, 14),
(52, 10, 21),
(53, 11, 1),
(54, 11, 2),
(55, 11, 5),
(56, 11, 9),
(57, 11, 10),
(58, 11, 11),
(59, 12, 4),
(60, 12, 5),
(61, 12, 15),
(62, 12, 18),
(63, 13, 6),
(64, 13, 7),
(65, 13, 19),
(66, 14, 17),
(67, 14, 19),
(68, 14, 21),
(69, 15, 1),
(70, 15, 2),
(71, 15, 6),
(72, 15, 12),
(73, 15, 19),
(74, 16, 7),
(75, 16, 8),
(76, 16, 16),
(77, 16, 20),
(78, 17, 3),
(79, 17, 4),
(80, 17, 9),
(81, 17, 20),
(82, 18, 4),
(83, 18, 7),
(84, 18, 15),
(85, 18, 20),
(86, 19, 1),
(87, 19, 2),
(88, 19, 11),
(89, 19, 18),
(90, 20, 5),
(91, 20, 6),
(92, 20, 19),
(93, 20, 21),
(94, 21, 3),
(95, 21, 9),
(96, 21, 18),
(97, 22, 2),
(98, 22, 8),
(99, 22, 12),
(100, 22, 20),
(101, 22, 21),
(103, 23, 6),
(104, 23, 11),
(105, 23, 14),
(106, 23, 20);

-- --------------------------------------------------------

--
-- Table structure for table `tb_instruktur`
--

CREATE TABLE `tb_instruktur` (
  `id_ins` int(11) NOT NULL,
  `nama_ins` varchar(50) NOT NULL,
  `email_ins` varchar(25) NOT NULL,
  `hp_ins` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_instruktur`
--

INSERT INTO `tb_instruktur` (`id_ins`, `nama_ins`, `email_ins`, `hp_ins`) VALUES
(1, 'Aisha Aileen', 'aisha.aileen@gmail.com', 812345678),
(2, 'Elvira Askana', 'askanae@yahoo.co.id', 813012345),
(3, 'Helia Chessy', 'cheesyhelia@gmail.com', 813311223),
(4, 'Athaya Putra', 'a_putra@yahoo.com', 811234567),
(5, 'Dehaan Ismail', 'd.ismail@gmaill.com', 821223345),
(6, ' Qaid Arkana', 'qaid_arkana@yahoo.co.id', 812001123),
(7, 'Gadhing Ganendra', 'gadhing.g@gmail.co', 815837283),
(8, 'Danu Sanjaya', 'sanjaya_danu@yahoo.com', 82285739),
(9, 'Teguh Hendarsyah', 'hendarsyah@gmail.com', 813647283),
(10, 'Arin Prayudi', 'prayudiar@gmail.com', 812974839);

-- --------------------------------------------------------

--
-- Table structure for table `tb_kelas`
--

CREATE TABLE `tb_kelas` (
  `id_kls` int(11) NOT NULL,
  `tgl_mulai_kls` date NOT NULL,
  `tgl_akhir_kls` date NOT NULL,
  `id_ins` int(11) NOT NULL,
  `id_mat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_kelas`
--

INSERT INTO `tb_kelas` (`id_kls`, `tgl_mulai_kls`, `tgl_akhir_kls`, `id_ins`, `id_mat`) VALUES
(1, '2022-02-07', '2022-02-11', 1, 1),
(2, '2022-02-14', '2022-02-18', 6, 4),
(3, '2022-02-22', '2022-02-25', 4, 8),
(4, '2022-03-07', '2022-02-11', 2, 2),
(5, '2022-03-14', '2022-02-18', 7, 3),
(6, '2022-03-21', '2022-02-24', 9, 6),
(7, '2022-04-04', '2022-04-07', 4, 8),
(8, '2022-05-09', '2022-05-13', 5, 5),
(9, '2022-05-23', '2022-05-27', 10, 7),
(10, '2022-06-13', '2022-06-17', 1, 1),
(11, '2022-06-20', '2022-06-24', 6, 4),
(12, '2022-07-11', '2022-07-15', 3, 10),
(13, '2022-07-04', '2022-07-08', 4, 8),
(14, '2022-08-01', '2022-08-05', 2, 2),
(15, '2022-09-05', '2022-09-09', 8, 9),
(16, '2022-09-12', '2022-09-16', 5, 5),
(17, '2022-10-11', '2022-10-14', 6, 4),
(18, '2022-11-14', '2022-11-18', 3, 10),
(19, '2022-11-22', '2022-11-25', 9, 6),
(20, '2022-12-05', '2022-12-09', 7, 3),
(21, '2022-12-19', '2022-12-23', 2, 2),
(22, '2022-12-12', '2022-12-16', 10, 7),
(23, '2022-01-03', '2022-01-07', 8, 9);

-- --------------------------------------------------------

--
-- Table structure for table `tb_materi`
--

CREATE TABLE `tb_materi` (
  `id_mat` int(11) NOT NULL,
  `nama_mat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_materi`
--

INSERT INTO `tb_materi` (`id_mat`, `nama_mat`) VALUES
(1, 'Oracle Database SQL Workshop'),
(2, 'Android Programming with Java'),
(3, 'Machine Learning in Data Science use Phyton'),
(4, 'Workshop Data Science'),
(5, 'IT Management Essentials'),
(6, 'Pengelolaan Data Center'),
(7, 'Mastering Linux and Shell Programming'),
(8, 'Security Awareness for End Users'),
(9, 'Big Data with Hadoop and Spark'),
(10, 'Certified Ethical Hacking (CEH)');

-- --------------------------------------------------------

--
-- Table structure for table `tb_peserta`
--

CREATE TABLE `tb_peserta` (
  `id_pst` int(11) NOT NULL,
  `nama_pst` varchar(50) NOT NULL,
  `email_pst` varchar(25) NOT NULL,
  `hp_pst` int(15) NOT NULL,
  `instansi_pst` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_peserta`
--

INSERT INTO `tb_peserta` (`id_pst`, `nama_pst`, `email_pst`, `hp_pst`, `instansi_pst`) VALUES
(1, 'Gianna Ailani', 'ailanig@gmail.com', 811201928, 'Maybank'),
(2, 'Haifa Imani', 'haifamani@gmail.com', 811029384, 'Maybank'),
(3, 'Iva Jovita', 'ivaajo@yahoo.com', 821827392, 'Maybank'),
(4, 'Ilona Jannie', 'jannielona@gmail.com', 81319283, 'Maybank'),
(5, 'Jovanka Orlin', 'orlin.jov@yahoo.co.id', 811239482, 'Kementrian Perindustrian RI'),
(6, 'Korinna Vania', 'korinia@gmail.com', 815827382, 'Kementrian Perindustrian RI'),
(7, 'Leta Nayara', 'leta.nayara@gmail.com', 815292849, 'Commonwealth'),
(8, 'Miranda Anum ', 'anum.miranda@gmail.com', 813293849, 'Commonwealth'),
(9, 'Aldo Febrianto', 'febri.aldo@yahoo.com', 813847261, 'Maybank'),
(10, 'Elon Harsaya', 'harsaya_elon@gmail.com', 821983029, 'Maybank'),
(11, 'Dondi Daharyadika', 'dondid@gmail.com', 812183729, 'Maybank'),
(12, 'Haryaka Gastiadi', 'g.haryaka@yahoo.com', 821837293, 'Maybank'),
(13, 'Raphael Hadrian', 'hadrianraph@gmail.com', 811937293, 'Kementrian Perindustrian RI'),
(14, ' Vidya Darmawan', 'darmawan.vidya@gmail.com', 815982739, 'Kementrian Perindustrian RI'),
(15, 'Muhammad Kevlar', 'Kevlar19@gmail.com', 822837492, 'Kementrian Perindustrian RI'),
(16, 'Eko Putra', 'ekoputra99@gmail.com', 813837294, 'Kementrian Perindustrian RI'),
(17, 'Handy Surya', 'surya_handy@yahoo.com', 81528374, 'Commonwealth'),
(18, 'Farrell Jovian', 'farrelljovian97@gmail.com', 813839028, 'Commonwealth'),
(19, 'Advaya Nayaka', 'nayaka_ad@gmail.com', 822839273, 'Commonwealth'),
(20, 'Odwin Kyros', 'kyros.odwin@yahoo.co.id', 822937492, 'Commonwealth'),
(21, 'Abhivandya Aniello', 'abhi_niel@gmail.com', 813675032, 'Commonwealth');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  ADD PRIMARY KEY (`id_detail_kls`),
  ADD KEY `const_detkls_idkls_kls_idkls` (`id_kls`),
  ADD KEY `const_detkls_idpst_pst_idpst` (`id_pst`);

--
-- Indexes for table `tb_instruktur`
--
ALTER TABLE `tb_instruktur`
  ADD PRIMARY KEY (`id_ins`);

--
-- Indexes for table `tb_kelas`
--
ALTER TABLE `tb_kelas`
  ADD PRIMARY KEY (`id_kls`),
  ADD KEY `const_kls_idins_ins_idins` (`id_ins`),
  ADD KEY `id_mat` (`id_mat`);

--
-- Indexes for table `tb_materi`
--
ALTER TABLE `tb_materi`
  ADD PRIMARY KEY (`id_mat`);

--
-- Indexes for table `tb_peserta`
--
ALTER TABLE `tb_peserta`
  ADD PRIMARY KEY (`id_pst`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  MODIFY `id_detail_kls` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- AUTO_INCREMENT for table `tb_instruktur`
--
ALTER TABLE `tb_instruktur`
  MODIFY `id_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `tb_kelas`
--
ALTER TABLE `tb_kelas`
  MODIFY `id_kls` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `tb_materi`
--
ALTER TABLE `tb_materi`
  MODIFY `id_mat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `tb_peserta`
--
ALTER TABLE `tb_peserta`
  MODIFY `id_pst` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  ADD CONSTRAINT `const_detkls_idkls_kls_idkls` FOREIGN KEY (`id_kls`) REFERENCES `tb_kelas` (`id_kls`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `const_detkls_idpst_pst_idpst` FOREIGN KEY (`id_pst`) REFERENCES `tb_peserta` (`id_pst`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_kelas`
--
ALTER TABLE `tb_kelas`
  ADD CONSTRAINT `const_kls_idins_ins_idins` FOREIGN KEY (`id_ins`) REFERENCES `tb_instruktur` (`id_ins`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_kelas_ibfk_1` FOREIGN KEY (`id_mat`) REFERENCES `tb_materi` (`id_mat`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
