import java.util.concurrent.CountDownLatch;

public class Sincro {
    private int maxMisiles;

    private CountDownLatch misilCreado;
    private CountDownLatch misilActivado;

    public Sincro(int maxMisiles) {
        this.misilCreado = new CountDownLatch(maxMisiles);
        this.misilActivado = new CountDownLatch(maxMisiles);
    }

    public int getAciertos() {
        return aciertos;
    }

    public int getFallos() {
        return fallos;
    }

    private int aciertos = 0;
    private int fallos = 0;

    public void notificarMisilCreado() {
        misilCreado.countDown();
    }

    public void esperarFin() {
        try {
            misilCreado.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void esperarActivacion() {
        try {
            misilActivado.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void lanzarMisil() {
        misilActivado.countDown();
    }

    public void sumarAcierto() {
        aciertos++;
    }

    public void sumarFallo() {
        fallos++;
    }

}
