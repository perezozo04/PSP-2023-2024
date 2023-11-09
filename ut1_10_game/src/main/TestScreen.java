package main;
import topos.vista1.*;
import java.awt.Color;
public class TestScreen {
    static final int ANCHO = 11;
    static final int ALTO = 11;
    static final int LADO = 50;
    static final int REDIBUJAR = 100;

    public static void main(String[] args) {
        int objX = 5;
        int objY = 5;

        String rutaImagenFondo ="imagenes/panel-basico.gif";
        String rutaImagenObjetivo ="imagenes/objetivo.png";
        Pantalla pantalla = new Pantalla(ANCHO,ALTO,LADO,Color.BLUE);
        String tecla;

        boolean fin = false;
        do {
            pantalla.resetear();
            cargarFondo(pantalla,rutaImagenFondo);

            if (pantalla.hayTecla()) {
                tecla = pantalla.leerTecla();
                if (tecla.contains("w")) {
                    objY++;
                } if (tecla.contains("s")) {
                    objY--;
                } if (tecla.contains("d")) {
                    objX++;
                } if (tecla.contains("a")) {
                    objX--;
                }

                pantalla.dibujar();
            }
            pantalla.addImagen(objX,objY,rutaImagenObjetivo);
            pantalla.dibujar();
            Alarma.dormir(REDIBUJAR);
        } while(!fin);
        pantalla.addImagen(objX, objY, rutaImagenObjetivo);
        cargarFondo(pantalla,rutaImagenFondo);
        pantalla.addImagen(objX, objY, rutaImagenObjetivo);
        pantalla.dibujar();



    }
    private static void cargarFondo(Pantalla pantalla, String rutaImagenFondo) {
        for (int i = 0; i < ANCHO; i++) {
            for (int j = 0; j < ALTO; j++) {
                pantalla.addImagen(i, j, rutaImagenFondo);
            }
        }
    }
}
