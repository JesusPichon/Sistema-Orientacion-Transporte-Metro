import javax.swing.*;

public class Main{

    public static void main(String[] args) {
        
        Grafo grafo = new Grafo(); //instanciamos el grafo
        GrafoUI frame = new GrafoUI(grafo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
