import java.awt.*;
import java.util.*;

/*
 *Esta clase es la que va a permitir colorear los vertices.
 *Recibe como parametro la lista de vertices y el panel(lienzo)
 *donde se dibujan
 */
public class ThreadColores extends Thread {

	private Lienzo lienzo;
	private ArrayList <Vertice> lista;
	private ArrayList <Arista> lista2;

	public ThreadColores(Lienzo lienzo, ArrayList <Vertice> lista, ArrayList <Arista> lista2)
	{
		this.lienzo = lienzo;
		this.lista = lista;
		this.lista2 = lista2;
	}

	public void run(){
		for(int i=0; i<lista.size(); i++) {
			lista.get(i).setColor(Color.WHITE);
			try {
				lienzo.repaint();
				sleep(250);
				}catch (InterruptedException ex){}			
			if(i < lista2.size()) {
				lista2.get(i).setColor(new Color(0,0,128));
				try {
					lienzo.repaint();
					sleep(250);
					}catch (InterruptedException ex){}
			}
		}
	}
}