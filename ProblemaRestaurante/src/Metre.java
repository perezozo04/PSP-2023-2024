import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;

class Metre extends Thread {
    private BufferedReader flujoE;
    private Sincro sincro;
    private boolean fin = false;

    public Metre(Sincro sincro, PipedReader receptor) {
        this.sincro = sincro;
        this.flujoE = new BufferedReader(receptor);
        this.start();
    }

    @Override
    public void run() {
        while (!fin) {
            try {
                String mensaje = flujoE.readLine();
                sincro.lockprint();

                System.out.println("Mensaje recibido: " + mensaje);
                sincro.releaseprint();

            } catch (IOException e) {
                finalizar();
            }
        }
    }

    public void finalizar() {
        fin = true;
    }
}