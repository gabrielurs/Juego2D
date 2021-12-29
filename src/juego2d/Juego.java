package juego2d;

import control.Teclado;
import graficos.Pantalla;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

public class Juego extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    private static final int ANCHO = 800;
    private static final int ALTO = 600;
    private static volatile boolean enFuncionamiento = false;


    private static final String NOMBRE = "Juego";
    private static int aps = 0;
    private static int fps = 0;

    private static int x = 0;
    private static int y = 0;


    private static JFrame ventana;
    private static Thread thread;
    private static Teclado teclado;
    private static Pantalla pantalla;

    private static BufferedImage imagen = new BufferedImage(ANCHO,ALTO,BufferedImage.TYPE_INT_RGB);
    private static int[] pixeles = ((DataBufferInt) imagen.getRaster().getDataBuffer()).getData();


    private Juego(){
        setPreferredSize(new Dimension(ANCHO,ALTO));
        pantalla = new Pantalla(ANCHO,ALTO);

        teclado = new Teclado();
        addKeyListener(teclado);
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
        try {
            System.out.println(new File("C:/texturas/desierto.jpg").getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Juego juego = new Juego();
        juego.iniciar();
    }

    private synchronized void iniciar(){
        enFuncionamiento = true;
        thread = new Thread(this, "graficos");
        thread.start();
    }

    private synchronized void detener(){
    enFuncionamiento = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void actualizar(){
        teclado.actualizar();
        if (teclado.arriba){System.out.println("arriba");}
        if (teclado.abajo){System.out.println("abajo");}
        if (teclado.izquierda){System.out.println("izquierda");}
        if (teclado.derecha){System.out.println("derecha");}

        aps++;
    }

    private void mostrar(){
        BufferStrategy estrategia = getBufferStrategy();
        if (estrategia==null){
            createBufferStrategy(3);
            return;
        }
        pantalla.limpiar();
        pantalla.mostrar(x,y);

        System.arraycopy(pantalla.pixeles,0,pixeles,0,pixeles.length);
//        for (int i = 0; i < pixeles.length; i++) {
//            pixeles[i]=pantalla.pixeles[i];
//        }

        Graphics g = estrategia.getDrawGraphics();
        g.drawImage(imagen,0,0,getWidth(),getHeight(),null);
        g.dispose();
        estrategia.show();
        fps++;
    }

    @Override
    public void run() {
        final int NS_POR_SEGUNDO = 1000000000;
        final byte APS_OBJETIVO = 60;
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();
        double tiempoTranscurrido;
        double delta = 0;
        requestFocus();

        while (enFuncionamiento){
            final long inicioBucle = System.nanoTime();
            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            referenciaActualizacion = inicioBucle;
            delta+= tiempoTranscurrido / NS_POR_ACTUALIZACION;

            while (delta>=1){
                actualizar();
                delta--;
            }
            mostrar();
            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO){
                ventana.setTitle(NOMBRE+" || APS: " + aps + " || FPS: " + fps);
                aps = 0;
                fps = 0;
                referenciaContador = System.nanoTime();
            }
        }

    }
}
