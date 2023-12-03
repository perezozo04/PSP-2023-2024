import java.io.*;
// Camion pipe comprartido(reader), flujo de escritura(wirter
public class Camion extends Thread {
    private int id;
    private Sincro sincro;
    private BufferedReader flujoE;
    private PipedWriter confirmaS;
    private PrintWriter flujoS;

    public Camion(int id, Sincro sincro, BufferedReader receptor, PipedWriter confirmaS) {
        this.flujoE = new BufferedReader(receptor);
        this.confirmaS = confirmaS;
        flujoS = new PrintWriter(confirmaS);
        this.id = id;
        this.sincro = sincro;
        this.start();
        sincro.notificarCamion();
    }

    @Override
    public void run() {
        boolean fin = false;
        while (!fin) {
            try {
                try {
                    if (flujoE.readLine().equals("FIN")) {
                        fin = true;
                    } else {
                        int cantidad = Integer.parseInt(flujoE.readLine());
                        Thread.sleep(cantidad);
                        sincro.printLock();
                        flujoS.println("FIN");
                        flujoS.flush();
                        sincro.printRelease();
                    }
                } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
