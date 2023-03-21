class Arista {

	constructor(x1, y1, x2, y2, dis, time) {
		this.punto1 = { x: x1, y: y1 };
		this.punto2 = { x: x2, y: y2 };
		this.distancia = dis;
		this.tiempo = time;
		this.color = "black"
	}

	getPuntoInicial() {
		return this.punto1;
	}

	setPuntoInicial(x, y) {
		this.punto1.x = x;
		this.punto1.y = y;
	}

	getPuntoFinal() {
		return this.punto2;
	}

	setPf(x, y) {
		this.punto2.x = x;
		this.punto2.y = y;
	}

	getTiempo() {
		return this.tiempo;
	}

	setTiempo(t) {
		this.tiempo = t;
	}

	getDistancia() {
		return this.distancia;
	}

	setDistancia(dis) {
		this.distancia = dis;
	}

	setColor(c) {
		this.color = c;
	}

	getColor() {
		return this.color;
	}

	// modificar el metodo dibujar 
	dibujar() {
		const canvas = document.getElementById('lienzo');
		const ctx = canvas.getContext('2d');
		if (ctx) {

			ctx.lineWidth = 3;
			ctx.strokeStyle = this.color;

			ctx.beginPath();
			ctx.moveTo(this.punto1.x, this.punto1.y);
			ctx.lineTo(this.punto2.x, this.punto2.y);
			ctx.stroke();
		}
	}
}