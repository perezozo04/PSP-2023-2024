import java.io.*;
import java.util.Random;

public class Estacion extends Thread {
    private boolean fin = false;
    public PrintWriter flujoS;
    private Sincro sincro;
    private BufferedReader flujoE;
    private int numeroEstacion;

    public Estacion(PipedWriter emisor, PipedReader receptor, Sincro sincro, int numeroEstacion) {

        this.start();
        sincro.notificarEstacionLista();
    }

    @Override
    public void run() {
        while (!fin) {

            if (generarNumeroRandom() == 10) {
                notificarAtaque();
            } else {
                // esperarRandom();
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public int generarNumeroRandom() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    public void esperarRandom() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notificarAtaque() {
        int mensaje = this.numeroEstacion ;
        sincro.lockprint();
        System.out.println("Movimiento detectado en " + mensaje);
        try {
            emisor.write(mensaje);
            emisor.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sincro.releasePrint();

    }

    public boolean leerOrden() {
        
    }
}
