package Juego2d;

import javax.swing.*;
import java.awt.*;

public class Juego extends Canvas {
    private static final long serialVersionUID = 1L;
    private static final int ANCHO = 800;
    private static final int ALTO = 600;

    private static final String NOMBRE = "Juego";

    private static JFrame ventana;

    private Juego(){
        setPreferredSize(new Dimension(ANCHO,ALTO));
        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this, BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

    }

    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}
