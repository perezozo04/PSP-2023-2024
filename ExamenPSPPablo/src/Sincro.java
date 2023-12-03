import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Sincro {
    private CountDownLatch latch;
    private Semaphore lock;
    private CountDownLatch camion;
    private CountDownLatch central;

    public Sincro(int MAX_CAMIONES) {
        this.latch = new CountDownLatch(MAX_CAMIONES);
        this.lock = new Semaphore(1);
        this.camion = new CountDownLatch(1);
        this.central = new CountDownLatch(1);
    }

    public void notificarCamion() {
        latch.countDown();
    }

    public void esperarCamiones() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void finalizarCamiones() {
        camion.countDown();
    }
    public void esperarFinCamiones() {
        try {
            camion.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printLock() {
        try {
            lock.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printRelease() {
        lock.release();
    }

    public void finalizarCentral() {
        central.countDown();
    }
    public void esperarFinCentral() {
        try {
            central.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
