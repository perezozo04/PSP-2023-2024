import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Sincro {
    CountDownLatch latch;
    private Semaphore lock;
    static final int MAX_STATIONS = 10;

    public Sincro () {
        this.latch = new CountDownLatch(MAX_STATIONS);
        this.lock = new Semaphore(1);
    }

    public void esperarFin() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notificarEstacionLista() {
        latch.countDown();
    }

    public void lockprint() {
        try {
            lock.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releasePrint() {
        lock.release();
    }
}
