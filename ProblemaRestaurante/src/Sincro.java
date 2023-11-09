import java.io.PipedWriter;
import java.io.PipedReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

class Sincro {
    private int MAX_COCINEROS;
    private int MAX_PUESTOS;
    private Semaphore cocineros;
    private PipedWriter pipeWriter = new PipedWriter();
    private PipedReader pipeReader;
    private CountDownLatch latch;
    private Semaphore milock;

    public Sincro(int MAX_COCINEROS, int MAX_PUESTOS) {
        this.MAX_COCINEROS = MAX_COCINEROS;
        this.MAX_PUESTOS = MAX_PUESTOS;
        this.cocineros = new Semaphore(MAX_COCINEROS);
        this.latch = new CountDownLatch(MAX_PUESTOS);
        this.milock = new Semaphore(1);
    }

    public boolean buscaCocinero() {
        return cocineros.tryAcquire();

    }

    public void sueltaCocinero() {
        cocineros.release();
    }

    public void esperarClientes() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notificarClienteFin() {
        latch.countDown();
    }

    public void lockprint() {
        try {
            milock.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseprint() {
        milock.release();
    }

}
