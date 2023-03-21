<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Metro</title>
<link rel="stylesheet" href="Resources/Styles/main.css">
</head>
<body>
	<header>
		<h1>Sistema de Orientacion de Transporte en Metro</h1>
	</header>
	<div class="main">
		<div class="panelCanvas">
			<canvas id="lienzo" width="800" height="600">
				Lo sentimos tu navegador no soporta canvas 
			</canvas>
		</div>
		<div class="panelMensaje">
			<div class="mensajes">
				<p id="recorrido">Recorrido:</p>
				<p id="distancia">Distancia:</p>
				<p id="tiempo">Tiempo:</p>
				<p>Estas aqui:</p>
				<p id="puntoInicio"></p>
				<p>Destino:</p>
				<p id="puntoFin"></p>
			</div>
			<div class="panelBotones">
				<button id="recorrer" type="button">Recorrer</button>
			</div>
		</div>
	</div>
	<script src="Resources/Scripts/Arista.js"></script>
	<script src="Resources/Scripts/Vertice.js"></script>
	<script src="Resources/Scripts/Fetch.js"></script>
</body>
</html>