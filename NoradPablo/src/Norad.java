import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Norad {
    public static final int MAX_MISILES = 100;

    public static void main(String[] args) {
        Misiles[] misiles = new Misiles[MAX_MISILES];
        Sincro sincro = new Sincro();

        PipedWriter[] emisor = new PipedWriter[MAX_MISILES];
        PrintWriter[] flujoS = new PrintWriter[MAX_MISILES];

        for (int i = 0; i < MAX_MISILES; i++) {

            emisor[i] = new PipedWriter();
            flujoS[i] = new PrintWriter(emisor[i]);

            misiles[i] = new Misiles(i, sincro, emisor[i]);

            sincro.notificarMisilCreado();
            System.out.println("Misil " + i + " creado");

        }

        sincro.esperarFin();
        System.out.println("Todos los misiles estan creados");


        Scanner teclado = new Scanner(System.in);

        boolean fin = false;
        //while (!fin) {
            System.out.print("Orden -> ");
            String orden = teclado.nextLine();
            if (orden.equals("ataca")) {
                sincro.lanzarMisil();
                for (int i = 0; i < MAX_MISILES; i++) {
                    flujoS[i].println("ataca");
                    flujoS[i].flush();
                }
                sincro.esperarActivacion();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Todos los misiles estan activados");
                // fin = true;

           // }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Aciertos: " + sincro.getAciertos());
            System.out.println("Fallos: " + sincro.getFallos());
        }

    }
}
