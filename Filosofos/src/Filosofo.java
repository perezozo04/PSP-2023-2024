

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Filosofo extends Thread {

    private static final int MAX_COMIDAS = 3;
    private int id;
    private Semaphore[] palilloEspera;
    private int palilloIzq;
    private int palilloDer;
    private CyclicBarrier inicio;
    private int comidas = 0;

    public Filosofo(int id, Semaphore[] palilloEspera, int[][] palillos,
                    CyclicBarrier inicio) {
        this.id = id;
        this.palilloEspera = palilloEspera;
        this.palilloDer = palillos[id][0];
        this.palilloIzq = palillos[id][1];
        this.inicio = inicio;
        this.start();
    }

    public void run() {
        //esperamos para que todos los filósofos se inicien a la vez
        try {
            inicio.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        while (comidas < MAX_COMIDAS) {
            pensar();
            comer();
            comidas++;
        }
        System.out.println("Filósofo "+id+" ha terminado");
    }

    private void comer() {
        //bloqueamos el palillo izquierdo
        synchronized (palilloEspera[palilloIzq]) {
            //comprobamos que el palillo derecho también está disponible
            if (!palilloEspera[palilloDer].tryAcquire()) {
                //el palillo derecho no está disponible, liberamos el izquierdo y salimos
                palilloEspera[palilloIzq].release();
                return;
            }

            //los dos palillos están disponibles, podemos comer
            System.out.println("Filósofo "+id+" comiendo...");
            esperar();
            palilloEspera[palilloIzq].release();
            palilloEspera[palilloDer].release();
        }
    }

    private void esperar() {
        Random aleatorio = new Random();
        int espera = aleatorio.nextInt(500)+500;
        try {
            Thread.sleep(espera);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void pensar() {
        Random aleatorio = new Random();
        int espera = aleatorio.nextInt(1500)+1000;
        try {
            System.out.println("Filósofo "+id+" pensando...");
            Thread.sleep(espera);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}