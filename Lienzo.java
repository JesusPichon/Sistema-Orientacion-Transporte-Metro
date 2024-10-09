import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//Lienzo para dibujar el grafo

class Lienzo extends JPanel
{
	private Grafo grafo;
	private boolean flag = false;
	private Point2D vi, vf;

			
	//constructor
	public Lienzo(Grafo g1) {
		
		grafo = g1;  //asignamos la referencia para manejarla
		setLayout(null);


		
		//agregamos codigo para que el lienzo "oiga" al raton
		this.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent evento)
			{
				Point2D x = evento.getPoint();
				if(grafo.getVertice(x) != null) {
					
					if(flag == false) {
						vi = x;
						grafo.inicialVertice(vi);  // Aqui elijo el vertice inicial
						repaint();
						flag = true;

					}
					if(flag == true && x != vi) {
						vf = x;  // Aqui elijo el vertice final
						grafo.finalVertice(vf);  // Aqui elijo el vertice inicial
						repaint();
						flag = false;
						

					}
				}
				repaint();
			}
 			
		}); // fin de la clase interna
		
		//Ahora "oira" cuando se mueva el raton
		addMouseMotionListener(new MouseMotionAdapter(){
			//Va a cambiar de icono del cursos al pasar por los vertices
			public void mouseMoved(MouseEvent evento)
			{
				if(grafo.getVertice(evento.getPoint()) == null)
					setCursor(Cursor.getDefaultCursor());
				else
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}); // fin de la clase interna Motion

	} // fin constructor
	

//	@Override
    public void paint(Graphics g){
    	super.paintComponent(g);
        Dimension dimension = this.getSize();
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/Mapa.png"));
        g.drawImage(icon.getImage(), 0, 0, dimension.width, dimension.height, null);
        setOpaque(false);
        Graphics2D g2 = (Graphics2D)g;
        grafo.dibujar(g2);
        super.paintChildren(g);
    }

} //clase