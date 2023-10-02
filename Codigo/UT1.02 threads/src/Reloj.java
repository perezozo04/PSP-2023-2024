import java.util.Random;

public class Reloj extends Thread {
    String mensaje;
    Boolean fin = false;

    public Reloj(String mensaje) {
        this.mensaje = mensaje;
    }

    public void run() {
        Random aleatorio = new Random();
        while (!fin) {
            System.out.println(mensaje);
            int espera = aleatorio.nextInt(1000);
            try {
                Thread.sleep(espera);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void parar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fin = true;
        System.out.println("Parado" + mensaje);
    }
}
