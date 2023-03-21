package models;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class Grafo {
	private int orden; //orden del grafo
	private ArrayList <Vertice> vertices;
	private Arista [][]M;  //matriz de adyacencia
	private ArrayList <Vertice> visitados;
	private ArrayList <Arista> aristas;
	private Vertice inicio[];
	private ArrayList <Vertice> adyacentes;
	private ArrayList <Double> dv;
	private ArrayList <Double> aux;
	private Vertice pv[];
	private Vertice fin[];
	private boolean flag;
	private Stack<Vertice> Vpila;
	private Stack<Arista> Apila;
	//ESTO ES NUEVO
	private double tiempo;
	public int aglomeracion;
	//

	public Grafo(){
		this(165);
	}

	public Grafo(int orden){
		this.orden = orden;
		vertices = new ArrayList<> ();
		M = new Arista[orden][orden];
		visitados = new ArrayList<> ();
		aristas = new ArrayList<> ();
		inicio = new Vertice[1]; 
		adyacentes = new ArrayList<> ();
		dv = new ArrayList<> ();
		aux = new ArrayList<> ();
		pv = new Vertice[orden];
		fin = new Vertice[1];
		flag = false;
		Vpila = new Stack<Vertice>();
		Apila = new Stack<Arista>();
		//ESTO ES NUEVO
		tiempo = 0;
		//
	}
	
	public boolean getFlag() {
		return flag;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public ArrayList<Vertice> getVertices(){
		return this.vertices;
	}

	public void agregarVertice(Point2D p){		
		Vertice v = new Vertice(p);
		vertices.add(v);
	} 
	
	public void agregarVertice(Point2D p, String nombre){		
		Vertice v = new Vertice(p, nombre);
		vertices.add(v);
	}


	//obtiene el primer Vertice que contenga el punto p
	public Vertice getVertice(Point2D p){
		for(Vertice v: vertices)
			if(v.getCirculo().contains(p))	return v;
		return null;
	}

	public void inicialVertice(Point2D p) { //Asigna el vertice inicial, leyendolo desde el lienzo
		if(p != null) {
			for(Vertice v: vertices)
				if(v.getCirculo().contains(p)){
					restaurar();
					//v.setColor(Color.red);
					inicio[0] = v;
				}
		}
	}

	public void finalVertice(Point2D p) { ////Asigna el vertice final, leyendolo desde el lienzo
		if(inicio != null) {
			if (p != null) 
				for(Vertice v: vertices)
					if(v.getCirculo().contains(p)) {
						//v.setColor(Color.red);
						fin[0] = v;
					}
		}
	}

	public void agregarArista(Point2D po, Point2D pd, double peso_d, double peso_t){
		/*
             Los puntos po y pd, marcan los vÃ©rtices orige y destino de la arista
             Se debe crear unas arista tomando esos puntos.
             DespuÃ©s insertar la arista en la matriz de adyacencia donde corresponda,
             de acuerdo a la posiciÃ³n de los vÃ©rtices en el arrayList.
		 */
		Arista a = new Arista(po, pd);
		a.setPesoT(peso_t);
		a.setPesoD(peso_d);
		int i = vertices.indexOf(getVertice(po));  //obtiene posiciÃ³n vertice orige
		int j = vertices.indexOf(getVertice(pd)); //obtiene posiciÃ³n vertice destino
		//coloca arista en la matriz de adyacencia
		M[i][j]= a;
		M[j][i]= a; //caso grafo no dirigido
	}

	//mostrar la lista de vertices
	public String mostrarVertices(){
		String cad = " ";
		for(Vertice v: vertices) 
			cad += v.getNombre()+", ";
		return cad;
	}   

	public void dibujar(Graphics2D g2){
		//pintamos las aristas
		for(int i=0; i<vertices.size(); i++)
			for(int j=0; j<vertices.size(); j++)
				if(M[i][j] != null) {
					M[i][j].dibujar(g2);
				}

		//pintamos los vertices
		//for(Vertice v: vertices) 
			//v.dibujar(g2);
	}


	public void colorearVertices(Color color) {           
		//for(Vertice v: vertices)
			//v.setColor(color);
	}


	public int obtenerAdyNoVisitado(int v) {
		for (int i=0; i<vertices.size(); i++) {
			if(M[v][i] != null && vertices.get(i).isVisitado() == false) {
				aristas.add(M[v][i]);
				return i;
			}
		}
		return -1;
	}

	public void CMC() { //Dijkstra
		//JFrame jFrame = new JFrame();
		if(inicio[0] != null && fin[0] != null) { //Para que comienze, inicio[0] (vertice inicial) y fin[0] (vertice final) deben ser != null
			Vertice vi;
			double min;
			int i, j = 0, k, r = 0, posvk = 0, posvi = 0;
			boolean flag = false;
			for(i=0; i<vertices.size(); i++) {
				//ESTO ES NUEVO
				//Cambiar el numero "1000" a "100000"
				dv.add(100000.0);
				//
				if(inicio[0] == vertices.get(i))
					j = i;
			}
			dv.add(j, 0.0);
			vi = inicio[0];
			min = dv.get(0);
			pv[j] = vi;
			visitados.addAll(vertices);
			while(vi.isVisitado() == false) {
				for (j=0; j<vertices.size(); j++)
					if(vi == vertices.get(j))
						r = j;
				for (i=0; i<vertices.size(); i++)
					if(M[r][i] != null)
						adyacentes.add(vertices.get(i));
				for(i=0; i<vertices.size(); i++)
					if(vi == vertices.get(i))
						posvi = i;
				for(Vertice vk: adyacentes)
					if(vk.isVisitado() == false) {
						for(i=0; i<vertices.size(); i++)
							if(vk == vertices.get(i))
								posvk = i;
						if(dv.get(posvk) > dv.get(posvi) + calcularCosto(posvi, posvk)) {
							dv.set(posvk, dv.get(posvi) + calcularCosto(posvi, posvk));
							pv[posvk] = vi;
						}
					}
				for (i=0; i<visitados.size(); i++)
					if(vi == visitados.get(i))
						visitados.remove(i);
				for(i=0; i<vertices.size(); i++)
					if(vi == vertices.get(i))
						vertices.get(i).setVisitado(true);
				vi.setVisitado(true);
				for(i=0; i<adyacentes.size(); i++)
					for(j=0; j<vertices.size(); j++)
						if(adyacentes.get(i) == vertices.get(j))
							if(vertices.get(j).isVisitado() == false) {
								if(min == 0.0)
									min = dv.get(j);
								else if(min > dv.get(j)) {
									min = dv.get(j);
									vi = vertices.get(j);			    							
									flag = true;
								}
							}
				if(flag == false) {
					for(i=0; i<vertices.size(); i++)
						//ESTO ES NUEVO
						//Cambiar el numero "1000" a "100000"
						if(vertices.get(i).isVisitado() == false && dv.get(i) < 100000.0)
							//
							aux.add(dv.get(i));
					if(aux.size() > 0) {
						min = Collections.min(aux);
						for(i=0; i<vertices.size(); i++)	
							if(min == dv.get(i) && vertices.get(i).isVisitado() == false)
								vi = vertices.get(i);
						aux.removeAll(aux);
					}
					else
						for(i=0; i<vertices.size(); i++)
							if(vertices.get(i).isVisitado() == false)
								vi = vertices.get(i);
				}
				adyacentes.removeAll(adyacentes);
				flag = false;
			}
			j = 0;
			k = 0;
			vi = fin[0];
			while(vi != inicio[0]) {
				for(i=0; i<vertices.size(); i++)
					if(vi == vertices.get(i)) {
						visitados.add(vi);
						j = i;
					}
				vi = pv[j];
				for(i=0; i<vertices.size(); i++)
					if(vi == vertices.get(i))
						k = i;
				if(M[j][k] != null)
					aristas.add(M[j][k]);
				if(vi == inicio[0])
					visitados.add(vi);
			}
			setFlag(true);
			for (i=0; i<visitados.size(); i++) {
	    		for (j=0; j<vertices.size(); j++)
	        		if(M[i][j] != null) {
	   	    			adyacentes.add(vertices.get(j));
	        		}
	    		if(adyacentes.size() > 2) {
	    			aglomeracion++;
	    		}
	    		adyacentes.removeAll(adyacentes);
	    	}
		}
		
		/*else if(inicio[0] == null && fin[0] == null)
			JOptionPane.showMessageDialog(jFrame, "Seleccione primero el vertice Inicial y Final, despues vuelva a presionar el botón");
		else if(inicio[0] != null && fin[0] == null)
			JOptionPane.showMessageDialog(jFrame, "Seleccione el vertice Final y vuelva a presionar el botón");
	*/
	}

	public void restaurar() {
		for(int i=0; i<vertices.size(); i++) {
			vertices.get(i).setVisitado(false);
			pv[i] = null;
		}
		for(Vertice v: vertices)
			//v.setColor(Color.BLACK);
		visitados.removeAll(visitados);
		for(int i=0; i<vertices.size(); i++)
			for(int j=0; j<vertices.size(); j++)
				if(M[i][j] != null) {
			//		M[i][j].setColor(new Color(0,0,0,0));
				}
		aristas.removeAll(aristas);
		dv.removeAll(dv);
		aglomeracion = 0;
	}

	public ArrayList<Vertice> verticesVisitados() { 
		Vpila.addAll(visitados);
		visitados.removeAll(visitados);
		while(!Vpila.isEmpty())
			visitados.add(Vpila.pop());
		return visitados;
	}

	public ArrayList<Arista> aristasRecorrido(){
		Apila.addAll(aristas);
		aristas.removeAll(aristas);
		while(!Apila.isEmpty())
			aristas.add(Apila.pop());

		//ESTO ES NUEVO
		//La variable "tiempo" la declare global
		for(int i = 0; i < aristas.size(); i++)
			tiempo = tiempo + aristas.get(i).getPesoT();
		//

		return aristas;
	}

	//ESTO ES NUEVO
	//Este metodo es para mandar el valor del tiempo del recorrido a la clase GrafoUI
	public double calcularTiempo() {
		return tiempo;
	}
	//

	public double calcularCosto(int vi, int vk) {
		double costo = M[vi][vk].getPesoD();
		return costo;
	}

	public double costoCaminoD() {
		double costo=0;

		if(inicio[0] != null && fin[0] != null) {
			int j = 0;

			for(int i=0; i<vertices.size(); i++)
				if(fin[0] == vertices.get(i))
					j = i;
			costo = dv.get(j);

		}

		return costo;
	}


	//crea el archivo en disco, recibe como parÃ¡metro la lista de Vertices
	public void escribirVeritces() {
		FileWriter flwriter = null;
		try {
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter("C:\\Users\\abner\\Escritorio\\Vertices.txt");
			//crea un buffer o flujo intermedio antes de escribir directamente en el archivo
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			for (Vertice v : vertices) {
				//escribe los datos en el archivo
				bfwriter.write(v.getOrigen().getX() + "," + v.getOrigen().getY() + "," + v.getNombre() + "," + "\n");
			}
			//cierra el buffer intermedio
			bfwriter.close();
			System.out.println("Archivo creado satisfactoriamente..");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (flwriter != null) {
				try {//cierra el flujo principal
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//crea el archivo en disco, recibe como parÃ¡metro la lista de Vertices
	public void escribirAristas() {
		FileWriter flwriter = null;
		try {
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter("C:\\Users\\abner\\Escritorio\\Aristas.txt");
			//crea un buffer o flujo intermedio antes de escribir directamente en el archivo
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			for(int i=0; i<vertices.size(); i++)
				for(int j=0; j<vertices.size(); j++)
					if(M[i][j] != null) {
						bfwriter.write(M[i][j].getPi().getX() + "," + M[i][j].getPi().getY() + "," + M[i][j].getPf().getX() + "," + M[i][j].getPf().getY() + "," + M[i][j].getPesoD() + "," + "\n");
					}
			//cierra el buffer intermedio
			bfwriter.close();
			System.out.println("Archivo creado satisfactoriamente..");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (flwriter != null) {
				try {//cierra el flujo principal
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//crea el archivo en disco, retorna la lista de estudiantes
	public void leerVeritces() {
		// crea el flujo para leer desde el archivo
		InputStream file = getClass().getResourceAsStream("/files/Vertices.txt");
		Scanner scanner;
		//se pasa el flujo al objeto scanner
		scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			// el objeto scanner lee linea a linea desde el archivo
			String linea = scanner.nextLine();
			Scanner delimitar = new Scanner(linea);
			//se usa una expresiÃ³n regular
			//que valida que antes o despues de una coma (,) exista cualquier cosa
			//parte la cadena recibida cada vez que encuentre una coma				
			delimitar.useDelimiter("\\s*,\\s*");
			agregarVertice(new Point2D.Double(Double.parseDouble(delimitar.next()),Double.parseDouble(delimitar.next())), delimitar.next());
		}
		//se cierra el ojeto scanner
		scanner.close();
	}

	//crea el archivo en disco, retorna la lista de estudiantes
	public void leerAristas() {
		// crea el flujo para leer desde el archivo
		InputStream file = getClass().getResourceAsStream("/files/Aristas.txt");
		Scanner scanner;
		//se pasa el flujo al objeto scanner
		scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			// el objeto scanner lee linea a linea desde el archivo
			String linea = scanner.nextLine();
			Scanner delimitar = new Scanner(linea);
			//se usa una expresiÃ³n regular
			//que valida que antes o despues de una coma (,) exista cualquier cosa
			//parte la cadena recibida cada vez que encuentre una coma				
			delimitar.useDelimiter("\\s*,\\s*");
			agregarArista(new Point2D.Double(Double.parseDouble(delimitar.next()),Double.parseDouble(delimitar.next())), new Point2D.Double(Double.parseDouble(delimitar.next()),Double.parseDouble(delimitar.next())), Double.parseDouble(delimitar.next()), Double.parseDouble(delimitar.next()));
		}
		//se cierra el ojeto scanner
		scanner.close();

		//  			agregarArista(new Point2D.Double(172.0, 508.0), new Point2D.Double(184.0, 525.0), 5.0);
		//  			368.0,192.0,393.0,200.0,5.0,
	}

	public ArrayList<Vertice> getVisitados(){//visitados es el arraylist que tiene el resultado de djikstra

		return visitados;

	}
}