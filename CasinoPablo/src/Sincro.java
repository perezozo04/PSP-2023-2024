import java.util.concurrent.CountDownLatch;

public class Sincro {
    private int maxJugadores;
    private CountDownLatch latch = new CountDownLatch(maxJugadores);
    public Sincro(int MAX_JUGADORES) {
        this.maxJugadores = MAX_JUGADORES;
        notificarFinalCreacionJugador();
    }

    public void notificarFinalCreacionJugador() {
        latch.countDown();

    }

    public void esperarCreacionJugador() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
