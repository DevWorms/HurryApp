<?php
session_start();

if ((empty($_SESSION['Id']) && empty($_SESSION['Nombre'])) || ($_SESSION['Id'] == "" && $_SESSION['Nombre'] == "")) {
	header("Location: ../");
}