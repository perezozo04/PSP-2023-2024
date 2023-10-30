package ut1_06_NADC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Sincroniza {
    private int maxmisiles;
    private CountDownLatch misilesListosCD;
    private CountDownLatch lanzarMisilesCD;
    private int aciertos;
    private int fallos;
    private Semaphore miLock = new Semaphore(1);


    public Sincroniza(int maxmisiles) {
        this.maxmisiles = maxmisiles;
        this.misilesListosCD = new CountDownLatch(maxmisiles);
        this.lanzarMisilesCD = new CountDownLatch(1);
    }

    public void esperarMisilesListos() {
        try {
            misilesListosCD.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notificarMisilList() {
        misilesListosCD.countDown();
    }

    public void esperarActivacion() {
        try {
            lanzarMisilesCD.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void realizarActivacion() {
        lanzarMisilesCD.countDown();
    }

    public void resetearContadores() {
        try {
            miLock.acquire();
            this.aciertos = 0;
            this.fallos = 0;
            miLock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void contarAcierto() {
        try {
            miLock.acquire();
            this.aciertos++;
            miLock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void contarFallo() {
        try {
            miLock.acquire();
            this.fallos++;
            miLock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
