package models;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.BasicStroke;

public class Arista {
	//ESTO ES NUEVO
	//Cambie la variable "peso" por "peso_t" y agregue tambien "peso_d"
	private double peso_t;
	private double peso_d;
	//
	private Point2D Pi, Pf;
	//private Color color;
	private Line2D linea;

	public Arista(Point2D pi, Point2D pf){
		//ESTO ES NUEVO
		//Agregue otro "1"
		this(pi,pf,1,1);
		//
	}

	public Arista (Point2D pi, Point2D pf, double peso_d, double peso_t){
		Pi = pi;
		Pf = pf;
		linea = new Line2D.Double(pi,pf);
		//color = new Color(0,0,0,0);
		//ESTO ES NUEVO
		this.peso_d = peso_d;
		this.peso_t = peso_t;
		//
	}

	public Point2D getPi() {
		return Pi;
	}

	public void setPi(Point2D Pi) {
		this.Pi = Pi;
	}

	public Point2D getPf() {
		return Pf;
	}

	public void setPf(Point2D Pf) {
		this.Pf = Pf;
	}

	//ESTO ES NUEVO
	//Modifique el get y set del la variable "peso" por "peso_t"
	//Agregue el set y get de "peso_d"
	public double getPesoD() {
		return peso_d;
	}

	public void setPesoD(double peso) {
		this.peso_d = peso;
	}

	public double getPesoT() {
		return peso_t;
	}

	public void setPesoT(double peso) {
		this.peso_t = peso;
	}
	//

	/*public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}*/

	public void dibujar(Graphics2D g2){
		BasicStroke grosor = new BasicStroke(6);
		g2.setStroke(grosor);
		//g2.setPaint(color);
		g2.draw(linea);
		g2.setPaint(Color.BLACK);
	}
}

