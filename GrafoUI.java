import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrafoUI extends JFrame {
	private JPanel pLetrero, pFunciones,pRecorrido;
    //private JButton btDijkstra, btRestaurar, btSalir;
    private JButton btRecorrido;
    private Lienzo lienzo;
	private Grafo grafo; //referencia al grafo
    public JColorChooser DialogoColor;
    private ThreadColores hilo;
    private JLabel recorrido;

      //constructor
    public GrafoUI(Grafo grafo) {

        super("Rutas de Metro");
        setLocation(100, 100);
        setLayout(new BorderLayout(2,2));

	this.grafo = grafo; //Se conoce el grafo
	lienzo = new Lienzo(this.grafo); //pasamos al grafo para que el liezo lo conozca
    lienzo.setPreferredSize(new Dimension(800,600));
    lienzo.setMinimumSize(new Dimension(800, 600));
    setResizable(false);
    this.add(lienzo, BorderLayout.CENTER);
	initComponentes();
        pack();
   }


    private void initComponentes(){
    	
    	
    	
    	grafo.leerVeritces();
    	grafo.leerAristas();
	    pLetrero = new JPanel();


	    	JLabel instrucciones = new JLabel("Elija una estación de inicio y otra de final.");
	    	instrucciones.setFont(new Font("Arial", Font.PLAIN, 19));

	    	pLetrero.add(instrucciones);
        
            btRecorrido = new JButton("Recorrer");
            btRecorrido.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		
                    grafo.CMC();
                    
                    colorear();

            		
            		
            		double costo = grafo.costoCaminoD();

            		JFrame jFrame = new JFrame();

            		int horas = (int)costo/60;
	    			int minutos = (int)costo%60; 


	    			/*if(costo!=0){
	    				if (horas==0)
	    				JOptionPane.showMessageDialog(jFrame,"<html>Tardarás aproximadamente "+minutos+" minutos <br>en llegar</html>");
	    			else if(horas==1){
	    		
	    			if(minutos==0)
	    				JOptionPane.showMessageDialog(jFrame,"Tardarás aproximadamente "+horas+" hora en llegar.");
	    			else
	    				JOptionPane.showMessageDialog(jFrame,"Tardarás aproximadamente "+horas+" hora con "+minutos+" minutos en llegar.");
	    			}
	    			else 
	    				JOptionPane.showMessageDialog(jFrame,"Tardarás aproximadamente "+horas+" horas con "+minutos+" minutos en llegar.");

  
	    			}
*/

	    			

	    			String msj = new String();

	    			if(costo!=0){
	    				if (horas==0)
	    				msj ="<html><br>Tardarás aproximadamente "+minutos+" minutos en llegar</html>";
	    			else if(horas==1){
	    		
	    			if(minutos==0)
	    				msj="<html><br>Tardarás aproximadamente "+horas+" hora en llegar.</html>";
	    			else
	    				msj="<html><br>Tardarás aproximadamente "+horas+" hora con "+minutos+" minutos en llegar.</html>";
	    			}
	    			else 
	    				msj="<html><br>Tardarás aproximadamente "+horas+" horas con "+minutos+" minutos en llegar.</html>";

  
	    			}


	    			recorrido.setText("<html>El camino mas corto es: "+getRecorrido()+".<br><br>"+msj+"</html>");
	    			
                  	
            		
                    
            	}
            });
          
            recorrido = new JLabel("Recorrido");

            recorrido.setFont(new Font("Arial", Font.PLAIN, 19));




            pFunciones = new JPanel();
            pRecorrido = new JPanel();
            pRecorrido.setPreferredSize(new Dimension(300,300));
            //pFunciones.setLayout(new BorderLayout());

            

            pRecorrido.setLayout(new BorderLayout());



            pRecorrido.add(recorrido,BorderLayout.PAGE_START);
            pFunciones.add(btRecorrido,BorderLayout.CENTER);//BOTÓN DE RECORRIDO


            add(pRecorrido,BorderLayout.EAST);
            add(pLetrero, BorderLayout.PAGE_START);
            add(lienzo,BorderLayout.CENTER );

            add(pFunciones, BorderLayout.PAGE_END);
    }

    private String getRecorrido(){

        		ArrayList<Vertice> vertices = grafo.getVisitados();
                //Muestra el string del recorrido
                String lista = new String();

                Vertice vI = vertices.get(0);
                Vertice vF = vertices.get(vertices.size() - 1);

               

              /* for(Vertice v : vertices){
               	lista = lista+", "+v.getNombre();
               }*/

                for (int i=0; i<=vertices.size()-1; i++) {
                    	

                    	Vertice v = vertices.get(i);


                        if(v==vI)
                            lista = lista+v.getNombre();
                        else 
                        	lista = lista +" - " +v.getNombre();

                }


            return (lista);
   
    }
    
    public void colorear(){



		//instanciamos un  hilo


        
		hilo = new ThreadColores(lienzo, grafo.verticesVisitados(), grafo.aristasRecorrido());
		//el metodo start() llama al metodo RUN del thread
		//a partir de aqui hay dos hilos de ejecucion corriendo
		hilo.start(); //ver clase ThreadColores
	}


}
