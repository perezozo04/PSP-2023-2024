import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Estacion extends Thread {
    private boolean fin = false;
    public PipedWriter emisor;
    private Sincro sincro;
    private int numeroEstacion;

    public Estacion(PipedWriter emisor, Sincro sincro, int numeroEstacion) {
        this.emisor = emisor;
        this.sincro = sincro;
        this.numeroEstacion = numeroEstacion;
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
}
