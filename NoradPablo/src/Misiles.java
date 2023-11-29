import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PipedReader;
import java.util.Random;


public class Misiles extends Thread{
    private PipedReader receptor;
    private BufferedReader flujoE;
    private int id;
    private Sincro sincro;
    public Misiles(int i, Sincro sincro, PipedWriter pipedWriter) {
        this.id = i;
        this.sincro = sincro;
        try {
            this.receptor = new PipedReader(pipedWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.flujoE = new BufferedReader(receptor);
        this.start();

    }

    public void run() {
        try {
            String orden = flujoE.readLine();
            if (orden.equals("ataca")) {
                System.out.println("Misil " + id + " atacando");
                esperaAleatoria();
                if(aciertoRandom()) {
                    sincro.sumarAcierto();
                } else {
                    sincro.sumarFallo();
                }
                sincro.lanzarMisil();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void esperaAleatoria() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(2500)+500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean aciertoRandom() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
