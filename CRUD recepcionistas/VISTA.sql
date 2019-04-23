-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 09-04-2019 a las 23:03:52
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `VISTA`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Administradores`
--

CREATE TABLE `Administradores` (
  `Usuario` varchar(20) NOT NULL,
  `Contrasena` varchar(20) DEFAULT NULL,
  `Nombre` varchar(20) DEFAULT NULL,
  `Apellido` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Administradores`
--

INSERT INTO `Administradores` (`Usuario`, `Contrasena`, `Nombre`, `Apellido`) VALUES
('Admin02', '2jH91228dbz,.-k,0P+', 'Quentin', 'Tarantino'),
('Admin_Master', 's{*+-1QlsHvH76Gf029?', 'Alfonso', 'Cuaron');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Ninos`
--

CREATE TABLE `Ninos` (
  `ID` varchar(5) NOT NULL,
  `Nombre` varchar(30) DEFAULT NULL,
  `Apellido` varchar(30) DEFAULT NULL,
  `Foto` varchar(100) DEFAULT NULL,
  `Tutor` varchar(12) DEFAULT NULL,
  `Autorizados` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Ninos`
--

INSERT INTO `Ninos` (`ID`, `Nombre`, `Apellido`, `Foto`, `Tutor`, `Autorizados`) VALUES
('17392', 'Fernando', 'Moreno', '/home/blabla/Carpeta-Caras-Ninos/17392.jpg', '6612345335', '/home/blablabla/Autorizados-17392.txt'),
('73648', 'Nahomi Adriana', 'Soto', '/home/blabla/Carpeta-Caras-Ninos/73648.jpg', '6623547586', '/home/blabla/Autorizados-73648.txt'),
('74633', 'Agustin', 'De Iturbide', '/home/blabla/Carpeta-Caras-Ninos/74633.jpg', '6221052333', '/home/blabla/Autorizados-74633.txt'),
('84726', 'Ivan JR', 'Moreno', '/home/blabla/Carpeta-Caras-Ninos/84726.jpg', '6612345335', '/home/blablabla/Autorizados-84726.txt');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Recepcionistas`
--

CREATE TABLE `Recepcionistas` (
  `Usuario` varchar(10) NOT NULL,
  `Contrasena` varchar(20) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `Apellido` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Recepcionistas`
--

INSERT INTO `Recepcionistas` (`Usuario`, `Contrasena`, `Nombre`, `Apellido`) VALUES
('Recep01', 'u23b 3 3', 'Pedro', 'Hernández'),
('Recep02', 'nui23rhfnuie2', 'Rocio', 'Ramón'),
('Recep03', 'hf48jr3no9ueji', 'Olivia', 'Arana'),
('Recep07', '232WED', 'Adrián', 'Soto'),
('Recep08', '348nr', 'Carmen', 'Amador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Tutores`
--

CREATE TABLE `Tutores` (
  `Telefono` varchar(12) NOT NULL,
  `Nombre` varchar(12) DEFAULT NULL,
  `Apellido` varchar(20) DEFAULT NULL,
  `Foto` varchar(100) NOT NULL,
  `Ninos` varchar(100) DEFAULT NULL,
  `Estatus` enum('Tutor','Autorizado','Sospechoso') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Tutores`
--

INSERT INTO `Tutores` (`Telefono`, `Nombre`, `Apellido`, `Foto`, `Ninos`, `Estatus`) VALUES
('6621052333', 'Pedrito', 'Hernandez', '/home/blabla/Carpeta-Caras-Autorizados/6621052333.jpg', '/home/blabla/ID-Ninos-6621052333.txt', 'Autorizado'),
('00000', '', '', '/home/blabla/Carpeta-Caras-Sospechosos/00000.jpg', '', 'Sospechoso'),
('6612345335', 'Fernanda', 'Dominguez', '/home/blabla/Carpeta-Caras-Tutores/6612345335.jpg', '/home/blabla/ID-Ninos-6612345335.txt', 'Tutor');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Administradores`
--
ALTER TABLE `Administradores`
  ADD PRIMARY KEY (`Usuario`);

--
-- Indices de la tabla `Ninos`
--
ALTER TABLE `Ninos`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `Recepcionistas`
--
ALTER TABLE `Recepcionistas`
  ADD PRIMARY KEY (`Usuario`);

--
-- Indices de la tabla `Tutores`
--
ALTER TABLE `Tutores`
  ADD PRIMARY KEY (`Foto`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
