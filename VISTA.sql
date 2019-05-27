-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-05-2019 a las 21:27:33
-- Versión del servidor: 10.1.40-MariaDB
-- Versión de PHP: 7.3.5

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
('Admin1', '1234567', 'Pedrito', 'Amador'),
('Admin_Master', 's{*+-1QlsHvH76Gf029?', 'Alfonso', 'Cuaron');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Ninos`
--

CREATE TABLE `Ninos` (
  `ID` int(11) NOT NULL,
  `Nombres` varchar(50) NOT NULL,
  `Apellido_paterno` varchar(30) NOT NULL,
  `Apellido_materno` varchar(30) NOT NULL,
  `Foto` varchar(100) NOT NULL,
  `Tutor` varchar(100) NOT NULL,
  `Autorizados` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Ninos`
--

INSERT INTO `Ninos` (`ID`, `Nombres`, `Apellido_paterno`, `Apellido_materno`, `Foto`, `Tutor`, `Autorizados`) VALUES
(4, 'Pedro Francisco', 'Hernández', 'Ramón', 'Fotos ninos/nino4.jpg', '6620000000', 'Niños/TutoresDe4.txt');

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
  `Nombres` varchar(50) DEFAULT NULL,
  `Apellido_paterno` varchar(30) DEFAULT NULL,
  `Apellido_materno` varchar(30) NOT NULL,
  `Foto` varchar(100) NOT NULL,
  `Ninos` varchar(100) DEFAULT NULL,
  `Estatus` enum('Tutor','Autorizado','Sospechoso') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Tutores`
--

INSERT INTO `Tutores` (`Telefono`, `Nombres`, `Apellido_paterno`, `Apellido_materno`, `Foto`, `Ninos`, `Estatus`) VALUES
('6620000000', 'Adrián ', 'Soto', 'Tequida', 'Fotos Tutores/tutor6620000000.jpg', 'Tutores/NiñosDe6620000000.txt', 'Autorizado');

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
  ADD PRIMARY KEY (`Telefono`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Ninos`
--
ALTER TABLE `Ninos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
