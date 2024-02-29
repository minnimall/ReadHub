-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 25, 2024 at 07:36 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `finalmobile`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `book_id` int(11) NOT NULL,
  `book_name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `num_of_read` int(11) NOT NULL DEFAULT 0,
  `book_img` varchar(255) NOT NULL,
  `btype_id` int(11) NOT NULL,
  `pub_id` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `writer_name` varchar(255) NOT NULL,
  `pub_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`book_id`, `book_name`, `description`, `num_of_read`, `book_img`, `btype_id`, `pub_id`, `created_at`, `updated_at`, `deleted_at`, `writer_name`, `pub_name`) VALUES
(1, 'hg', 'h', 0, 'http://10.0.2.2/ReadHubApi/uploads/bookPart-1708883210982.pdf', 4, NULL, NULL, NULL, NULL, 'h', 'h');

-- --------------------------------------------------------

--
-- Table structure for table `bookshelf`
--

CREATE TABLE `bookshelf` (
  `bookshelf_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `book_2`
--

CREATE TABLE `book_2` (
  `id` int(11) NOT NULL,
  `book_name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `pub_name` varchar(255) NOT NULL,
  `writer_name` varchar(255) NOT NULL,
  `btype` varchar(255) NOT NULL,
  `pdfFile` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_2`
--

INSERT INTO `book_2` (`id`, `book_name`, `description`, `pub_name`, `writer_name`, `btype`, `pdfFile`) VALUES
(1, 'g', 'gg', 'gg', 'gg', 'Cocoa Mint', 'http://10.0.2.2/ReadHubApi/uploads/bookPart-1708876033708.pdf'),
(2, 'jjj', 'jjj', 'jj', 'jj', 'Cocoa Mint', 'http://10.0.2.2/ReadHubApi/uploads/bookPart-1708877323333.pdf'),
(3, 'mm', 'mm', 'mm', 'mm', '', 'http://10.0.2.2/ReadHubApi/uploads/bookPart-1708877362310.pdf'),
(4, 'hh', 'ggg', 'gg', 'gg', 'Turn back time', 'http://10.0.2.2/ReadHubApi/uploads/bookPart-1708877944245.pdf'),
(5, 'hh', 'gffh', 'hh', 'fh', 'Fantasy', 'http://10.0.2.2/ReadHubApi/uploads/bookPart-1708878378173.pdf'),
(6, 'pol', 'jjh', 'gg', 'ggg', '6', 'http://10.0.2.2/ReadHubApi/uploads/bookPart-1708883040039.pdf');

-- --------------------------------------------------------

--
-- Table structure for table `book_detail`
--

CREATE TABLE `book_detail` (
  `bdetail_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `cate_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `book_has_writer`
--

CREATE TABLE `book_has_writer` (
  `bhw_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `writer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `book_tag`
--

CREATE TABLE `book_tag` (
  `tag_id` int(11) NOT NULL,
  `tag_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `book_type`
--

CREATE TABLE `book_type` (
  `btype_id` int(11) NOT NULL,
  `btype_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_type`
--

INSERT INTO `book_type` (`btype_id`, `btype_name`) VALUES
(1, 'Love Novel'),
(2, 'Boy Love'),
(3, 'Girl Love'),
(4, 'Fantasy'),
(5, 'Horror'),
(6, 'Turn back time'),
(7, 'Historical');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `cate_id` int(11) NOT NULL,
  `cate_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `my_book`
--

CREATE TABLE `my_book` (
  `mybook_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `publisher`
--

CREATE TABLE `publisher` (
  `pubid` int(11) NOT NULL,
  `pub_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reply`
--

CREATE TABLE `reply` (
  `reply_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `review_id` int(11) NOT NULL,
  `reply_descrip` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `review_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `review_descrip` varchar(255) NOT NULL,
  `review_rating` double NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `email`, `password`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'jaemin', 'jm@gmail.com', '$2b$10$E4zwW5sjf12LPrCEVf46tOMrjM8bTVqi7rn/nq4FkA2.FXAysbM4G', NULL, NULL, NULL),
(2, 'jeno', 'jn@gmail.com', '$2b$10$NA5R2XMiAUfW3e59tfZxjOf2ia2oERF9HHe5yyfHQpnV6AO8RxwaS', NULL, NULL, NULL),
(3, 'jm', 'jmm', '$2b$10$QOMeMhLg.l6KKb.Zad2Cteu1Pk8Q8MjVeHt4H0SBP9e4pGQdah2n.', NULL, NULL, NULL),
(4, 'j', 'j', '$2b$10$0UMrYsqBjCuZhgIGLWUifuWvPpOVnvXENivJ.LtYUClraty03cb9i', NULL, NULL, NULL),
(5, 'lk', 'lk', '$2b$10$7OA3flbwQwpX01fiNZ5ckuMv7PzMIXmSzxhiMW11VVkis9FIZqRam', NULL, NULL, NULL),
(6, 'ji', 'ji', '$2b$10$bxc7mACY/itcieZJLeOCEeXkr8ujm4TSqwcQhGIgr10OqrtkiT.Le', NULL, NULL, NULL),
(7, 'i ', 'k', '$2b$10$Zthar3/pxhzM2ZY4SnsBz.nU.8Jn7UNInJ7th4xLQ.jfCthDEsFte', NULL, NULL, NULL),
(8, 'jaemineiei', 'myloveluna', '$2b$10$3G61tF7/f26MQHhN9xf.weazHdOuXF8/x5ponbqxyHwT/qY.MOEd2', NULL, NULL, NULL),
(9, 'lukejaemin', 'lukedaddy', '$2b$10$lj6Zp9tIIiWC1M1yg9OzAuwctjl9KaOG3u3TEW49/RNHmYCxY8ZuW', NULL, NULL, NULL),
(10, 'jmjm', 'jm', '$2b$10$QtgwMdTmuG7lD2cuDGOECe4wVHjOXF9cI3P8Pm/YLo04zOxtCxaLy', NULL, NULL, NULL),
(11, 'pjaemin', 'pjj', '$2b$10$zXsO3FyEhQPkD7UN/zgWdeTDvLHjQdIQ8RrRzBAsf5jtRo5Qc8Y8W', NULL, NULL, NULL),
(12, 'dream', 'dream', '$2b$10$VhrLL5dwiPIIdqJrrethEeA0oYEGndfch1uojQ0rzuwWvWMTQYMRq', NULL, NULL, NULL),
(13, 'dsd', 'dsds', '$2b$10$fvCFcqhMM76xpm0BFktgbetKnYKvVOv8uxUEJIlH7sKO6n1p/Auqe', NULL, NULL, NULL),
(14, 'sds', 'dsds', '$2b$10$UClt60kI/kV97Sb2mN/SdeASERthhk6rsGtKoP8WoMY37zuWJYLli', NULL, NULL, NULL),
(15, 'dff', 'fdd', '$2b$10$pwcYS4TeV5uh5uVGbqwl0u/T1ed4oJbByahzqCFEhuwdlrs9aW04e', NULL, NULL, NULL),
(16, 'edsfd', 'fdfdf', '$2b$10$ipwgyl4o.NKRv38JstdPP.3iDA001.W.ucNXIEZ/xc/RdK.OCarmm', NULL, NULL, NULL),
(17, 'wdd', 'dsd', '$2b$10$7Ik8dkTxtp.O/A1uzz0Mz.WatL0L5WnW0Hr3Upl7ZIbk/2AJCkic2', NULL, NULL, NULL),
(18, 'xcxc', 'cccccccccv', '$2b$10$75OEWpQMEsWDsyTxmaiqN.IKG5171SwyYnSOiApdaQG3UaNiafMQu', NULL, NULL, NULL),
(19, 'err', 'rrtrtt', '$2b$10$L.rs1w9/eChvYZCgbXg8Pu0PNznzYMXDRwSzvLebn0/seuAnsMDhS', NULL, NULL, NULL),
(20, 'Lucy Na', 'lc', '$2b$10$usM/zzroJR8z3.P7IM6T1.3PM.G.jLtkoLN.ZbZn2mKTBTDHAswZC', NULL, NULL, NULL),
(21, 'u', 'u', '$2b$10$cb0bCsbcAvj0YHLgVToAeOZS/yX/kF.4Wza7/6xNeh9aQ5WOdAvAK', NULL, NULL, NULL),
(22, 'd', 'fdgfg', '$2b$10$brXoVskjgiMwQyrHGVxTzukaiUd.JWYxj/ISagxISzJKtHe7cSVd6', NULL, NULL, NULL),
(23, 'gg', 'fghg', '$2b$10$QUakZHauGsFNt8X8x4.CzeHSU4utel4CNFofNWGx5chpXMiRvrE7.', NULL, NULL, NULL),
(24, 'rtr', 'tt', '$2b$10$cGhclbrP7OddJzEGoG3AqeFS6reLYcPyBaaAIQhlk9GZ4gFP/i.xG', NULL, NULL, NULL),
(25, 'ddf', 'gfgfg', '$2b$10$KK/O5jeuRkHsHPPWuLvALu1uuUUeMQq8XpselwWXbyUWStdow4Zzy', NULL, NULL, NULL),
(26, 'fdfd', 'dfdff', '$2b$10$qmgNyRcU.QYc0k7fZalifuHBqpRdcMgvbeRtq/00IvjEiSyW2q0Me', NULL, NULL, NULL),
(27, 'dfd', 'ffdf', '$2b$10$FZEeZQ71IBmLPnNwjqOcKOlNLkIFMnywWrSZrNb8pG4MV4WgnHjcm', NULL, NULL, NULL),
(28, 'edfdf', 'dfdf', '$2b$10$9idQo9EHUwjUq.JQPVwmj.6vqq7tebNtlo5C/Naao8fvJjwkNK8gC', NULL, NULL, NULL),
(29, 'efd', 'gfhg', '$2b$10$DaVK50rp/Q7FvN1wjyjeRe3kKecS/5TLZv6YEq53ocWsBQSqbRRMK', NULL, NULL, NULL),
(30, 'sdsdfdfddd', 'dsdsd', '$2b$10$EWmN93DLqz1beleXeyrDK.VelYSibywmDR6yDAlYUOo7PoWdn2bES', NULL, NULL, NULL),
(31, 'dfddddddddd', 'fggfg', '$2b$10$FjdEsNL4mykmnQBg/.4teuguaVF0okTX/AEER00YEbAoxyEYUOHFi', NULL, NULL, NULL),
(32, 'xss', 'dsds', '$2b$10$GEJcbBAQVq45fq2pdlJgzu4FZmALU6yC5byVhxxPUmJJwchagOtPa', NULL, NULL, NULL),
(33, 'dde', 'dfdf', '$2b$10$1TCeJV8vlAEy3zWzfWTjAeDAUWrdk4wvejMkxI.dreUZGGxAKjfeu', NULL, NULL, NULL),
(34, 'sdeff', 'dfdf', '$2b$10$UxUwlrUxY3VRBQ7ELxZciei0ax8mONlueezSwRVFQVsOtVQ0alSUm', NULL, NULL, NULL),
(35, 'fdsfdd', 'gfgfgfg', '$2b$10$2rAfQR1IFimLpeAUlBydRu1hirzOIvwELsAMlUijEE6Qx2O2dRw62', NULL, NULL, NULL),
(36, 'fgf', 'fgg', '$2b$10$C1xTP5fwsAoLz3vQKABq..oP5Y5dU1AduFaXUB/db1mME6QpU4icC', NULL, NULL, NULL),
(37, 'sdll', 'dsds', '$2b$10$LnBtt/thszpoe5zjTWTu8.CNMfqLlgLukIbBxBiVLvdV8ArdCuxoK', NULL, NULL, NULL),
(38, 'frf', 'frrt', '$2b$10$BvadE9DwaJv9b1Ek74N2fOs/UoEoihkwcv3IFD1vDHbLRO6bsDU6G', NULL, NULL, NULL),
(39, 'fgl;;', '\'', '$2b$10$NsosASMoZ4t.uS8k8XfdDOuYF4fdUaPhnPqp535c.E6.9gIiWdhXK', NULL, NULL, NULL),
(40, 'fgh,,,,,', ',', '$2b$10$oxt03AEvJv7DsJe4JQMoE.Cc8qmCeUgFN14rfw.crYC1q.ylbgygC', NULL, NULL, NULL),
(41, 'kl', ';p;p;p;p', '$2b$10$R/.Ij6a7F0iUpMO4NG1dFO7uWAVgSBpTdyZGt/4isK5N4MTu9rptm', NULL, NULL, NULL),
(42, 'jj', 'kk', '$2b$10$MCUVoIwc2.6YcP5rULY8L.PCGKbaofw1IHkQx36OO6Fghn2gfdOny', NULL, NULL, NULL),
(43, 'kkk', 'kk', '$2b$10$72CR8zHrhwlf5F1v4bKoGet1MJDL9R.QsZnLsvLcWEQJb6AA93vZW', NULL, NULL, NULL),
(44, 'klpp', 'o;;', '$2b$10$wU3m7BjCJYUsx7PFhqAAyOEwAV9hYUuJnOZ4bF8kKPAQm/eZEBe0i', NULL, NULL, NULL),
(45, ';lll', 'llll', '$2b$10$gMrpfKxx23VdrnzFs6liS.BUcnkwqmh8nqLlUlXY1Jz2WqcCcY0I6', NULL, NULL, NULL),
(46, 'lmnn', 'bbv', '$2b$10$qb2C6LG3naVpkSMSH30Ml.KYLpGMDLacZNC9odiK3UVaeRB1mLwpK', NULL, NULL, NULL),
(47, 'lll89', 'lll', '$2b$10$cwQdfP/4VzqB6qgBjMHglu8lOrRGNOgsGV49BypyHqN2idCK9/0KO', NULL, NULL, NULL),
(48, 'kjhm', 'mnmnm', '$2b$10$aBmLhJd2gHRfYrhiL/HmiOCZ60n6vn7WTCf7Sv4sfaOW8DUb0Tof2', NULL, NULL, NULL),
(49, 'gggytjh', 'xd', '$2b$10$uZrMEI5EWDXodCaKobAFHuSRrW0.zu3TTR/62m9w1lTGgTIS8cTbO', NULL, NULL, NULL),
(50, 'kkkop', 'kkk', '$2b$10$/iYPSXdX2jM0lr2zjeNr3uELOpkeA3V2eoXn.WMMoqNIaJj3coynW', NULL, NULL, NULL),
(51, 'lo09-', 'jyj', '$2b$10$1YrSfOeaGT0nA.W/RBx2yeSBpJD2CFGwLw3/KSKrekUSLnPfU1r6e', NULL, NULL, NULL),
(52, 'ko99', '99', '$2b$10$9eimoMW3uNS20XXF9LCT/OIrpc0j7SVeS4Vt1xOwZT1eUAhji/OTW', NULL, NULL, NULL),
(53, 'hu89', 'ygg', '$2b$10$AYVeD5RmChwwb2vPKaaPFu58vwJ/mKC9jQtvugW78fH2FJ5IkPkSm', NULL, NULL, NULL),
(54, 'hu89g', 'ygg', '$2b$10$zlVVbQrYs9./L.iaQ1rmKuk2iGlqZSAVeIaui1xyTmAZyO0RXnoNu', NULL, NULL, NULL),
(55, 'hu89gfdggggh', 'ygg', '$2b$10$8CpQSWS4fZQwD.8f6MjgQOKaKy2SaezWjr9n/QVruYW6k1BeL4UDG', NULL, NULL, NULL),
(56, '-fffv', 'vccc', '$2b$10$iXfUQsBOX9kZpI48l1PKuuNlqaKz/8CLP5K3htybaQ2EXER6wNvCq', NULL, NULL, NULL),
(57, 'fffv', 'vccc', '$2b$10$wOBLlSDLVeGxxzRXyp/n2.coOw1ItJbeezEvlvE4/zGvLRKjLuSfy', NULL, NULL, NULL),
(58, '09plj', 'bvv', '$2b$10$I8i8j97Q8LkxkPksVZdH5OEH3G4mvQoE0sru7AP.zlAZK2LQrkHwu', NULL, NULL, NULL),
(59, '123', 'bvv', '$2b$10$eS4bjEiIR2IxvH2Th9UEN.b1bKyt6yR4FCWX5zBdzwm.VzoYX2Fbu', NULL, NULL, NULL),
(60, '090', 'gh', '$2b$10$vdojpaMuZTQR0u5P/59u0epcmh6zn1ZiZU75iveOysJO0NsbnA5kC', NULL, NULL, NULL),
(61, 'gh', 'g', '$2b$10$PEXnd1eSREek4ijf2F1ThuoTaaxDB9nDrUPe9PsubPjvdXBKNMr2W', NULL, NULL, NULL),
(62, 'gh67h', 'g', '$2b$10$NAL1/v1ct5W7Y9q3.XQ6/ONOlQ5jMc9YUBI5GtlMK4KbcGXkRztR6', NULL, NULL, NULL),
(63, 'gh67hhhhhh', 'g', '$2b$10$54aGyiliSr9SIxJKwrjBYu/jandoUbo1vTePB/rwak4uDbX9pU2M6', NULL, NULL, NULL),
(64, 'kk,9-', 'h', '$2b$10$LxbgOlCjjJBaZe00bvePfOmMtjoeB2TIQdf9DpYRJKtFjXMAgjkEy', NULL, NULL, NULL),
(65, 'jj787uyj', 'hhjhh', '$2b$10$ZiMKKiCBeujGYsasSUeYDOVEfmfkAiuZHOUr.EbRiPNT5W5cDm34G', NULL, NULL, NULL),
(66, 'bg', 'gg', '$2b$10$bmwgBwP5sIsmiQflK8AdN.j4GGI4CTQGTwIKw3EDj.rnBskedXPau', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_interested`
--

CREATE TABLE `user_interested` (
  `interest_id` int(11) NOT NULL,
  `tag_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `userinterested` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_interested`
--

INSERT INTO `user_interested` (`interest_id`, `tag_id`, `user_id`, `userinterested`) VALUES
(1, NULL, 43, 'Historical'),
(2, NULL, 45, '[Turn back time, Horror]'),
(3, NULL, 45, '[Turn back time, Horror]'),
(4, NULL, 45, '[Turn back time, Horror]'),
(5, NULL, 46, '[Horror, Fantasy]');

-- --------------------------------------------------------

--
-- Table structure for table `writer`
--

CREATE TABLE `writer` (
  `writer_id` int(11) NOT NULL,
  `writer_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`),
  ADD KEY `btype_id` (`btype_id`),
  ADD KEY `pub_id` (`pub_id`);

--
-- Indexes for table `bookshelf`
--
ALTER TABLE `bookshelf`
  ADD PRIMARY KEY (`bookshelf_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `book_2`
--
ALTER TABLE `book_2`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `book_detail`
--
ALTER TABLE `book_detail`
  ADD PRIMARY KEY (`bdetail_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `cate_id` (`cate_id`),
  ADD KEY `tag_id` (`tag_id`);

--
-- Indexes for table `book_has_writer`
--
ALTER TABLE `book_has_writer`
  ADD PRIMARY KEY (`bhw_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `writer_id` (`writer_id`);

--
-- Indexes for table `book_tag`
--
ALTER TABLE `book_tag`
  ADD PRIMARY KEY (`tag_id`);

--
-- Indexes for table `book_type`
--
ALTER TABLE `book_type`
  ADD PRIMARY KEY (`btype_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`cate_id`);

--
-- Indexes for table `my_book`
--
ALTER TABLE `my_book`
  ADD PRIMARY KEY (`mybook_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `publisher`
--
ALTER TABLE `publisher`
  ADD PRIMARY KEY (`pubid`);

--
-- Indexes for table `reply`
--
ALTER TABLE `reply`
  ADD PRIMARY KEY (`reply_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `review_id` (`review_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_interested`
--
ALTER TABLE `user_interested`
  ADD PRIMARY KEY (`interest_id`),
  ADD KEY `book_id` (`tag_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `writer`
--
ALTER TABLE `writer`
  ADD PRIMARY KEY (`writer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `book_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `bookshelf`
--
ALTER TABLE `bookshelf`
  MODIFY `bookshelf_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `book_2`
--
ALTER TABLE `book_2`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `book_detail`
--
ALTER TABLE `book_detail`
  MODIFY `bdetail_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `book_has_writer`
--
ALTER TABLE `book_has_writer`
  MODIFY `bhw_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `book_tag`
--
ALTER TABLE `book_tag`
  MODIFY `tag_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `book_type`
--
ALTER TABLE `book_type`
  MODIFY `btype_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `cate_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `my_book`
--
ALTER TABLE `my_book`
  MODIFY `mybook_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `publisher`
--
ALTER TABLE `publisher`
  MODIFY `pubid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reply`
--
ALTER TABLE `reply`
  MODIFY `reply_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT for table `user_interested`
--
ALTER TABLE `user_interested`
  MODIFY `interest_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `writer`
--
ALTER TABLE `writer`
  MODIFY `writer_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`btype_id`) REFERENCES `book_type` (`btype_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `publisher` (`pubid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `bookshelf`
--
ALTER TABLE `bookshelf`
  ADD CONSTRAINT `bookshelf_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bookshelf_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `book_detail`
--
ALTER TABLE `book_detail`
  ADD CONSTRAINT `book_detail_ibfk_1` FOREIGN KEY (`tag_id`) REFERENCES `book_tag` (`tag_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_detail_ibfk_2` FOREIGN KEY (`cate_id`) REFERENCES `category` (`cate_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_detail_ibfk_3` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `book_has_writer`
--
ALTER TABLE `book_has_writer`
  ADD CONSTRAINT `book_has_writer_ibfk_1` FOREIGN KEY (`writer_id`) REFERENCES `writer` (`writer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_has_writer_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `my_book`
--
ALTER TABLE `my_book`
  ADD CONSTRAINT `my_book_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `my_book_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reply`
--
ALTER TABLE `reply`
  ADD CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_interested`
--
ALTER TABLE `user_interested`
  ADD CONSTRAINT `user_interested_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_interested_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `book_tag` (`tag_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
