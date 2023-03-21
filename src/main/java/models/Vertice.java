package models;

import java.awt.geom.*;

//Clase configurado para los objetos json 
public class Vertice {
	private String nombre;
	private boolean visitado;
	private float d;	//distancia del camnino hasta
	private Vertice p;	//El vertice predesesor del camino
	private Point2D origen; //punto origen
	private Ellipse2D circulo;  //nodo
	private final static int diametro = 10;
	public static int nVertices = 1;  //contador  de vertices creados

	public Vertice(){
		this(new Point2D.Double(0,0));
	}

	public Vertice(Point2D p){
		this(p, ""+nVertices); //Se nombra al vertice por su número
	}

	public Vertice(Point2D p, String nombre){
		double x = p.getX();
		double y = p.getY();
		origen = p;
		//color = Color.BLACK;
		circulo = new Ellipse2D.Double(x-diametro/2,y-diametro/2,
				diametro,diametro);
		this.nombre = nombre;
		nVertices++; 
	}



	public Point2D getOrigen() {
		return origen;
	}

	public void setOrigen(Point2D origen) {
		this.origen = origen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	public Ellipse2D getCirculo() {
		return circulo;
	}

	public void setCirculo(Ellipse2D circulo) {
		this.circulo = circulo;
	}
} 
