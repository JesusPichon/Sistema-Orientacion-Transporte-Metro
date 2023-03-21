package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.*;

/**
 * Servlet implementation class myController
 */
public class myController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Grafo mapa;
	public boolean flag;
	public Point2D vi, vf;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public myController() {
        super();
        // TODO Auto-generated constructor stub
        this.mapa = new Grafo();
		this.mapa.leerVeritces();
		this.mapa.leerAristas();
		this.flag = false;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String operacion = request.getParameter("accion");
		switch (operacion) {
		case "vertices":
			enviarVertices(request, response);
			break;
		case "coordenadas":
			coordenadas(request, response);
			break;
		case "cmc":
			recorrer(request, response);
			break;
		case "recorridoVertices":
			recorridoVertices(request, response);
			break;
		case "recorridoAristas":
			recorridoAristas(request, response);
			break;
		case "restaurar":
			restaurarMapa(request, response);
			break;
		case "costoCamino":
			getDistancia(request, response);
			break;
		case "aglomeracion":
			getTiempoEstacion(request, response);
			break;
		default:
			try (PrintWriter out = response.getWriter()) {
				out.print("Error");
			}
		}
	}
	
	private void getTiempoEstacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int tiempoEstacion = (int) mapa.aglomeracion;

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		try (PrintWriter out = response.getWriter()) {
			out.print(tiempoEstacion);
		}
	}
	
	private void getDistancia(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int costo = (int) mapa.costoCaminoD();

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		try (PrintWriter out = response.getWriter()) {
			out.print(costo);
		}
	}
	
	private void restaurarMapa(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		mapa.restaurar();

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		try (PrintWriter out = response.getWriter()) {
			out.print("<p> Mapa restaurado <p>");
		}

	}
	
	private void recorridoAristas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		ArrayList<Arista> aristas = mapa.aristasRecorrido();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String aristasJson = gson.toJson(aristas);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		

		try (PrintWriter out = response.getWriter()) {
			out.print(aristasJson);
		}
	}
	
	private void recorridoVertices(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		ArrayList<Vertice> vertices = mapa.verticesVisitados();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String verticesJson = gson.toJson(vertices);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		

		try (PrintWriter out = response.getWriter()) {
			out.print(verticesJson);
		}
	}
	
	private void recorrer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		mapa.CMC();
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		
		try (PrintWriter out = response.getWriter()) {
			out.print("<p> Recorrido ejecutado con exito <p>");
		}

	}
	
	private void coordenadas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Double x = Double.parseDouble(request.getParameter("x"));
		Double y = Double.parseDouble(request.getParameter("y"));

		Vertice verticeEncontrado = mapa.getVertice(new Point2D.Double(x,y));
		Point2D x1 = verticeEncontrado.getOrigen();

		if(x1 != null) {
			if(flag == false) {
				vi = x1;
				mapa.inicialVertice(vi);  // Aqui elijo el vertice inicial
				flag = true;
			}
			if(flag == true && x1  != vi) {
				vf = x1;  // Aqui elijo el vertice final
				mapa.finalVertice(vf);  // Aqui elijo el vertice inicial
				flag = false;
			}
		}


		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String vertice = gson.toJson(verticeEncontrado);
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try (PrintWriter out = response.getWriter()) {
			out.print(vertice);
		}
	}

	private void enviarVertices(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String stringJson = gson.toJson(mapa.getVertices());
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try (PrintWriter out = response.getWriter()) {
			out.print(stringJson);
			out.flush();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
