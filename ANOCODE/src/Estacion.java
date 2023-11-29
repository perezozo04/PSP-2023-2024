import java.io.*;
import java.util.Random;

public class Estacion extends Thread {
    private boolean fin = false;
    private PipedWriter radioEmisor;
    private PrintWriter flujoS;

    private PipedReader receptor;
    private BufferedReader flujoE;
    private Sincro sincro;
    private int id;



    public Estacion(int id, Sincro sincro, PipedWriter radioEmisor, PipedWriter emisor) {
        this.id = id;
        this.sincro = sincro;
        this.radioEmisor = radioEmisor;
        this.flujoS = new PrintWriter(radioEmisor);

        try {
            this.receptor = new PipedReader(emisor);
            this.flujoE = new BufferedReader(receptor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.start();
        sincro.notificarEstacionLista();
    }

    @Override
    public void run() {
        while (!fin) {

            if (generarNumeroRandom() == 10) {
                sincro.lockprint();
                notificarAtaque();

                if (leerOrden()) {
                    System.out.println("Estacion " + this.id + " ha atacado");
                }
                sincro.releasePrint();
            } else {
                esperarRandom();

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

        System.out.println("Movimiento detectado en " + this.id);
        flujoS.println(this.id);
        flujoS.flush();


    }

    public boolean leerOrden() {
        try {
            String orden = flujoE.readLine();
            if (orden.equals("atacar")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
