-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2026 at 01:43 PM
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
-- Database: `sankar`
--

-- --------------------------------------------------------

--
-- Table structure for table `invoices`
--

CREATE TABLE `invoices` (
  `invoice_no` bigint(20) NOT NULL,
  `party_name` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `payment_mode` varchar(50) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoices`
--

INSERT INTO `invoices` (`invoice_no`, `party_name`, `date`, `payment_mode`, `total_amount`, `created_at`) VALUES
(1, 'Sankar', '2026-06-15', 'Cash', 100.00, '2026-06-14 20:50:03'),
(2, 'Sankar', '2026-06-15', 'Digital', 101.00, '2026-06-14 21:19:31'),
(3, 'Maa Bhagabati', '2026-06-15', 'Cash', 10006.00, '2026-06-14 22:29:20');

-- --------------------------------------------------------

--
-- Table structure for table `invoice_details`
--

CREATE TABLE `invoice_details` (
  `invoice_no` bigint(20) NOT NULL,
  `Item_id` int(11) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `rate` decimal(10,2) NOT NULL,
  `sub_total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoice_details`
--

INSERT INTO `invoice_details` (`invoice_no`, `Item_id`, `item_name`, `quantity`, `rate`, `sub_total`) VALUES
(1, 6, 'Luhakata', 100, 1.00, 100.00),
(2, 5, 'Blade', 101, 1.00, 101.00),
(3, 19, 'Galigua', 10000, 1.00, 10000.00),
(3, 12, 'Astighai', 1, 6.00, 6.00);

--
-- Triggers `invoice_details`
--
DELIMITER $$
CREATE TRIGGER `after_purchase_entry_stock` AFTER INSERT ON `invoice_details` FOR EACH ROW BEGIN
    UPDATE stock_report 
    SET purchase_stock = purchase_stock + NEW.quantity,
        purchase_rate = NEW.rate
    WHERE item_id = NEW.item_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `id` int(11) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `purchase_price` decimal(10,2) NOT NULL,
  `sale_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`id`, `item_name`, `purchase_price`, `sale_price`) VALUES
(1, 'salita', 2.00, 4.00),
(2, 'Agarbati', 10.00, 20.00),
(3, 'Salita2', 3.00, 4.00),
(4, 'Karpur(5gm)', 3.00, 5.00),
(5, 'Blade', 1.00, 2.00),
(6, 'Luhakata', 1.00, 1.50),
(7, 'Chandankatha', 20.00, 25.00),
(8, 'Gangajala(50m.l)', 8.00, 10.00),
(9, 'Kalash', 10.00, 20.00),
(10, 'Salukana', 6.00, 8.00),
(11, 'Jhati', 10.00, 18.00),
(12, 'Astighai', 6.00, 9.00),
(13, 'Ghodani', 3.00, 4.00),
(14, 'MatcheBox', 1.00, 2.00),
(15, 'Aruachaula', 8.00, 11.00),
(16, 'Mugadali', 8.00, 11.00),
(17, 'Rasi', 3.00, 5.00),
(18, 'Jabadhan', 3.00, 5.00),
(19, 'Galigua', 1.00, 2.00),
(20, 'Honey', 4.00, 5.00);

--
-- Triggers `items`
--
DELIMITER $$
CREATE TRIGGER `after_item_master_insert` AFTER INSERT ON `items` FOR EACH ROW BEGIN
    INSERT INTO stock_report (item_id, item_name, purchase_rate, sale_rate, opening_stock, purchase_stock, sale_stock)
    VALUES (NEW.id, NEW.item_name, NEW.purchase_price, NEW.sale_price, 0, 0, 0);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices`
--

CREATE TABLE `sales_invoices` (
  `invoice_no` bigint(20) NOT NULL,
  `date` date NOT NULL,
  `payment_mode` varchar(50) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales_invoices`
--

INSERT INTO `sales_invoices` (`invoice_no`, `date`, `payment_mode`, `total_amount`, `created_at`) VALUES
(1, '2026-06-16', 'Cash', 5.00, '2026-06-16 11:10:52'),
(2, '2026-06-16', 'Cash', 5.00, '2026-06-16 12:13:48'),
(3, '2026-06-16', 'Cash', 15.50, '2026-06-16 12:14:39'),
(4, '2026-06-16', 'Digital', 275.00, '2026-06-16 12:41:28');

-- --------------------------------------------------------

--
-- Table structure for table `sale_invoice_details`
--

CREATE TABLE `sale_invoice_details` (
  `id` bigint(20) NOT NULL,
  `invoice_no` bigint(20) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `rate` decimal(10,2) NOT NULL,
  `sub_total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sale_invoice_details`
--

INSERT INTO `sale_invoice_details` (`id`, `invoice_no`, `item_id`, `item_name`, `quantity`, `rate`, `sub_total`) VALUES
(1, 1, 4, 'Karpur(5gm)', 1, 5.00, 5.00),
(2, 2, 4, 'Karpur(5gm)', 1, 5.00, 5.00),
(3, 3, 16, 'Mugadali', 1, 11.00, 11.00),
(4, 3, 6, 'Luhakata', 3, 1.50, 4.50),
(5, 4, 7, 'Chandankatha', 11, 25.00, 275.00);

--
-- Triggers `sale_invoice_details`
--
DELIMITER $$
CREATE TRIGGER `after_sale_entry_stock` AFTER INSERT ON `sale_invoice_details` FOR EACH ROW BEGIN
    UPDATE stock_report 
    SET sale_stock = sale_stock + NEW.quantity
    WHERE item_id = NEW.item_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `stock_report`
--

CREATE TABLE `stock_report` (
  `item_id` int(11) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `purchase_rate` decimal(10,2) NOT NULL DEFAULT 0.00,
  `sale_rate` decimal(10,2) NOT NULL DEFAULT 0.00,
  `opening_stock` int(11) NOT NULL DEFAULT 0,
  `purchase_stock` int(11) NOT NULL DEFAULT 0,
  `sale_stock` int(11) NOT NULL DEFAULT 0,
  `available_stock` int(11) GENERATED ALWAYS AS (`opening_stock` + `purchase_stock` - `sale_stock`) STORED,
  `last_updated` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stock_report`
--

INSERT INTO `stock_report` (`item_id`, `item_name`, `purchase_rate`, `sale_rate`, `opening_stock`, `purchase_stock`, `sale_stock`, `last_updated`) VALUES
(1, 'salita', 2.00, 4.00, 100, 0, 0, '2026-06-15 12:29:54'),
(2, 'Agarbati', 10.00, 20.00, 0, 0, 0, '2026-06-15 12:11:22'),
(3, 'Salita2', 3.00, 4.00, 0, 0, 0, '2026-06-15 12:11:22'),
(4, 'Karpur(5gm)', 3.00, 5.00, 0, 0, 2, '2026-06-16 12:13:48'),
(5, 'Blade', 1.00, 2.00, 0, 0, 0, '2026-06-15 12:11:22'),
(6, 'Luhakata', 1.00, 1.50, 0, 0, 3, '2026-06-16 12:14:39'),
(7, 'Chandankatha', 20.00, 25.00, 0, 0, 11, '2026-06-16 12:41:28'),
(8, 'Gangajala(50m.l)', 8.00, 10.00, 0, 0, 0, '2026-06-15 12:11:22'),
(9, 'Kalash', 10.00, 20.00, 0, 0, 0, '2026-06-15 12:11:22'),
(10, 'Salukana', 6.00, 8.00, 0, 0, 0, '2026-06-15 12:11:22'),
(11, 'Jhati', 10.00, 18.00, 0, 0, 0, '2026-06-15 12:11:22'),
(12, 'Astighai', 6.00, 9.00, 0, 0, 0, '2026-06-15 12:11:22'),
(13, 'Ghodani', 3.00, 4.00, 0, 0, 0, '2026-06-15 12:11:22'),
(14, 'MatcheBox', 1.00, 2.00, 0, 0, 0, '2026-06-15 12:11:22'),
(15, 'Aruachaula', 8.00, 11.00, 0, 0, 0, '2026-06-15 12:11:22'),
(16, 'Mugadali', 8.00, 11.00, 0, 0, 1, '2026-06-16 12:14:39'),
(17, 'Rasi', 3.00, 5.00, 0, 0, 0, '2026-06-15 12:11:22'),
(18, 'Jabadhan', 3.00, 5.00, 0, 0, 0, '2026-06-15 12:11:22'),
(19, 'Galigua', 1.00, 2.00, 0, 0, 0, '2026-06-15 12:11:22'),
(20, 'Honey', 4.00, 5.00, 0, 0, 0, '2026-06-15 12:11:22');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `Fullname` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `Fullname`, `username`, `Password`) VALUES
(1, 'SankarSwain', 'Sankar', '143143'),
(2, 'gourang swain', 'gourang', 'g14314');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invoices`
--
ALTER TABLE `invoices`
  ADD UNIQUE KEY `invoice_no` (`invoice_no`);

--
-- Indexes for table `invoice_details`
--
ALTER TABLE `invoice_details`
  ADD KEY `invoice_id` (`invoice_no`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  ADD PRIMARY KEY (`invoice_no`);

--
-- Indexes for table `sale_invoice_details`
--
ALTER TABLE `sale_invoice_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stock_report`
--
ALTER TABLE `stock_report`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  MODIFY `invoice_no` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `sale_invoice_details`
--
ALTER TABLE `sale_invoice_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
