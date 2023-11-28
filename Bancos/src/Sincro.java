import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Sincro {
    private int maxClientes;
    private CyclicBarrier cbClientesListos;
    private CountDownLatch countFinClientes;
    public Sincro(int maxClientes) {
        this.cbClientesListos = new CyclicBarrier(maxClientes);
        this.countFinClientes = new CountDownLatch(maxClientes);
    }

    public void awaitFinClientes() {
        try {
            this.countFinClientes.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void countFinClientes() {

            this.countFinClientes.countDown();

    }

    public void awaitClientesListo() {
        //Iniciamos todos los hilos concurrentemente
        try {
            this.cbClientesListos.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
