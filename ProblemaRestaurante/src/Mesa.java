import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Mesa extends Thread {
    private int id;
    private PipedWriter emisor;
    private PrintWriter flujoS;
    private Sincro sincro;
    private Lock lock = new ReentrantLock();
    private boolean hacomido = false;

    Mesa(int id,Sincro sincro, PipedWriter emisor ) {
        this.id = id;
        this.emisor = emisor;
        this.sincro = sincro;
        this.flujoS = new PrintWriter(emisor);
        this.start();
    }

    public void run() {
        Random random = new Random();
        while (!hacomido) {
            try {

                if (sincro.buscaCocinero()) {
                    Thread.sleep(random.nextInt(2000));
                    String mensaje = "Mesa " + id + " comiendo";
                    sincro.lockprint();
                    System.out.println(mensaje);
                    sincro.releaseprint();
                    sincro.sueltaCocinero();
                    sincro.notificarClienteFin();


                    hacomido = true;

                } else {
                    Thread.sleep(random.nextInt(1000));
                    // lock.lock();
                    String mensaje = "Mesa " + id + " esperando cocinero";

                    flujoS.println(mensaje);
                    flujoS.flush();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}