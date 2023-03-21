//comportamiento de variables globales 
const diametro = 10;
const radio = diametro / 2;

class Vertice {

	constructor(x , y, name) {
		this.nombre = name;
		this.punto = {
			coordenadax: x,
			coordenaday: y 
		};
		this.color = "yellow";
	}
	
	setCoordenadas(x,y){
		this.punto = {
			coordenadx: x,
			coordenday: y
		};
	}
	
	getCoordenadas(){
		return this.punto;
	}

	getNombre() {
		return this.nombre;
	}

	setNombre(name) {
		this.nombre = name;
	}

	setColor(c) {
		this.color = c;
	}

	getColor() {
		return this.color;
	}

	//cambiar el metodo dibujar vertice 
	dibujar() {
		const canvas = document.getElementById('lienzo');
		const ctx = canvas.getContext('2d');
		if (ctx) {
			ctx.strokeStyle = this.color;
			ctx.fillStyle = "black";
			ctx.lineWidth = 1;
			ctx.beginPath();
			ctx.arc(this.punto.coordenadax,
				this.punto.coordenaday,
				radio, 0, 2*Math.PI, false);
			ctx.fill();
			ctx.stroke();
		}
	}
	
}

