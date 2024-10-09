
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.BasicStroke;

public class Arista {
    private double peso;
    private Point2D Pi, Pf;
    private Color color;
    private Line2D linea;
    
    public Arista(Point2D pi, Point2D pf){
        this(pi,pf,1);
    }
    
    public Arista (Point2D pi, Point2D pf, double peso){
    	Pi = pi;
    	Pf = pf;
        linea = new Line2D.Double(pi,pf);
        color = new Color(0,0,0,0); //Define el color desde el constructor
        this.peso = peso;
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

	public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    //dibuja la arista 
    public void dibujar(Graphics2D g2){
    	BasicStroke grosor = new BasicStroke(6);
    	g2.setStroke(grosor);
   	 	g2.setPaint(color);
   	 	g2.draw(linea);
   	 	g2.setPaint(Color.BLACK);
   }
}
