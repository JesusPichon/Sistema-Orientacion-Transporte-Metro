//Constantes
const rutaImagen = 'Resources/Images/Mapa.png';
const dirServidor = 'localhost'; //Direccion IP del servidor
const list = [];

const listaVertices = [];
const listaAristas = [];

//Variables
var flag = false;
var vi, vf;
var costoDistancia = 0;
var aglomeracion = 0;

//funciones 
function calcularTiempo() {
	var tiempo = 0;
	listaAristas.forEach(element => {
		tiempo = tiempo + element.tiempo;
	});
	return tiempo + (aglomeracion*5);
}

function mensajeDistancia() {
	var costo = costoDistancia;
	var kilometros = Number.parseInt(costo / 1000);
	var metros = Number.parseInt(costo % 1000);
	var dist = "";
	if (costo != 0) {
		if (kilometros == 0)
			dist = "La distancia del recorrido es de " + metros + " metros.";
		else if (kilometros == 1) {

			if (metros == 0)
				dist = "La distancia del recorrido es de " + kilometros + " kilometro.";
			else
				dist = "La distancia del recorrido es de " + kilometros + " kilometro con " + metros + " metros.";
		}
		else
			dist = "La distancia del recorrido es de " + kilometros + " kilometros con " + metros + " metros.";
	}
	return dist;

}

function mensajeTiempo() {
	var costo = calcularTiempo();
	var msj = "";
	var horas = Number.parseInt(costo / 60);
	var minutos = Number.parseInt(costo % 60);

	if (costo != 0) {
		if (horas == 0)
			msj = "Tiempo estimado de llegada " + minutos + " minutos.";
		else if (horas == 1) {

			if (minutos == 0)
				msj = "Tiempo estimado de llegada " + horas + " hora";
			else
				msj = "Tiempo estimado de llegada " + horas + " hora con " + minutos + " minutos.";
		}
		else
			msj = "Tiempo estimado de llegada " + horas + " horas con " + minutos + " minutos.";
	}

	return msj;
}

function getNombresVertices() {
	var listaNombres = "";
		
	listaVertices.forEach(element => {
		listaNombres = listaNombres + ' => ' + element.nombre;
	});

	return listaNombres;
}

function pintarVertices() {
	list.forEach(element => {
		element.dibujar();
	});
}

function dibujarRecorrido() {
	listaAristas.forEach(element => {
		//element.color = "black";
		element.dibujar();
	});
	listaVertices.forEach(element => {
		//element.color = "yellow";
		element.dibujar();
	});
}

function paint() {
	var canvas = document.getElementById('lienzo');
	var ctx = canvas.getContext('2d');
	if (ctx) {
		const img = new Image();
		img.src = rutaImagen;
		ctx.drawImage(img, 0, 0);
	}
}

function getMousePos(canvas, evt) {
	var clientRect = canvas.getBoundingClientRect()
	return {
		x: Math.round(evt.clientX - clientRect.left),
		y: Math.round(evt.clientY - clientRect.top)
	}
}


//Peticiones
function costoCaminoD() {
	const http = new XMLHttpRequest();
	const url = 'http://' + dirServidor + ':8080/Metro/myController?accion=costoCamino';
	http.open("GET", url)
	http.onreadystatechange = function() {

		if (this.readyState == 4 && this.status == 200) {
			costoDistancia = Number(this.responseText);
			
		}
	}
	http.send();
}


function getAllVertices() {
	const http = new XMLHttpRequest();
	const url = 'http://' + dirServidor + ':8080/Metro/myController?accion=vertices';
	http.open("GET", url)
	http.onreadystatechange = function() {

		if (this.readyState == 4 && this.status == 200) {
			var resultado = JSON.parse(this.responseText)
			resultado.forEach(element => {
				list.push(new Vertice(element.origen.x, element.origen.y, element.nombre));
			});
		}
	}
	http.send();
}

function obtenerVertices(puntoX, puntoY) {
	const http = new XMLHttpRequest();
	const url = 'http://' + dirServidor + ':8080/Metro/myController?accion=coordenadas' + '&x=' + puntoX + '&y=' + puntoY;
	http.open("GET", url)
	http.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resultado = JSON.parse(this.responseText);
			if (resultado != null) {
				if (flag == false) {
					vi = resultado;
					document.getElementById('puntoInicio').style.color = "black";
					document.getElementById('puntoInicio').innerHTML = vi.nombre;
					flag = true;
				} if (flag == true && resultado != vi) {
					vf = resultado;
					document.getElementById('puntoFin').style.color = "black";
					document.getElementById('puntoFin').innerHTML = vf.nombre;
					flag = false;
				}
			}
		}
	}
	http.send();
}



function CMC() {
	const http = new XMLHttpRequest();
	const url = 'http://' + dirServidor + ':8080/Metro/myController?accion=cmc';
	http.open("GET", url)
	http.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log(this.responseText);
			costoCaminoD();
			getTiempoAglomeracion();
			getVerticesRecorrido();
			getAristasRecorrido();
		}
	}
	http.send();
}

function restaurar() {
	const http = new XMLHttpRequest();
	const url = 'http://' + dirServidor + ':8080/Metro/myController?accion=restaurar';
	http.open("GET", url)
	http.onreadystatechange = function() {

		if (this.readyState == 4 && this.status == 200) {
			console.log(this.responseText);
		}
	}
	http.send();
}


function getVerticesRecorrido() {
	const http = new XMLHttpRequest();
	const url = 'http://' + dirServidor + ':8080/Metro/myController?accion=recorridoVertices'

	http.open("GET", url)
	http.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resultado = JSON.parse(this.responseText);
			resultado.forEach(element => {
				listaVertices.push(new Vertice(element.origen.x, element.origen.y, element.nombre));
			});
			document.getElementById('recorrido').innerHTML = "La ruta mas corta es:" + getNombresVertices();
		}
	}
	http.send();
}

function getAristasRecorrido() {
	const http = new XMLHttpRequest();
	const url = 'http://' + dirServidor + ':8080/Metro/myController?accion=recorridoAristas'

	http.open("GET", url)
	http.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resultado = JSON.parse(this.responseText);
			resultado.forEach(element => {
				listaAristas.push(new Arista(element.Pi.x, element.Pi.y, element.Pf.x,
					element.Pf.y, element.peso_d, element.peso_t));
			});
			document.getElementById('distancia').innerHTML = mensajeDistancia();
			document.getElementById('tiempo').innerHTML = mensajeTiempo();
			dibujarRecorrido();
		}
	}
	http.send();
}

function getTiempoAglomeracion() {
	const http = new XMLHttpRequest();
	const url = 'http://' + dirServidor + ':8080/Metro/myController?accion=aglomeracion';
	http.open("GET", url)
	http.onreadystatechange = function() {

		if (this.readyState == 4 && this.status == 200) {
			aglomeracion = Number(this.responseText)
		}
	}
	http.send();
}

//Eventos
const lienzo = document.getElementById("lienzo");
lienzo.addEventListener("click", function(evt) {
	var posicion = getMousePos(lienzo, evt);
	console.log("x:" + posicion.x + " y: " + posicion.y);
	obtenerVertices(posicion.x, posicion.y);
});

const botonRecorrer = document.getElementById('recorrer');
botonRecorrer.addEventListener("click", function() {
	CMC();
});

//Evento => cargar la ventana  
window.onload = () => {
	var canvas = document.getElementById('lienzo');
	var ctx = canvas.getContext('2d');
	if (ctx) {
		const img = new Image();
		img.src = rutaImagen;
		//defino el evento onload del objeto imagen
		img.onload = function() {
			//incluyo la imagen en el canvas
			ctx.drawImage(img, 0, 0);
			//crear retraso para pintar los vertices
		};
	}
	restaurar();
};


//Ejecucion
getAllVertices();







