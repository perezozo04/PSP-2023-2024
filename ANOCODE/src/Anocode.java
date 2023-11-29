import java.io.*;
import java.util.Scanner;

public class Anocode {
    private static BufferedReader flujoE;
    private static PrintWriter[] flujoS;
    public static void inciar() {
        Estacion[] escucha = new Estacion[Sincro.MAX_STATIONS];
        PipedWriter[] emisor = new PipedWriter[Sincro.MAX_STATIONS];
        flujoS = new PrintWriter[Sincro.MAX_STATIONS];
        Sincro sincro = new Sincro();
        PipedReader readerGlobal = new PipedReader();
        PipedWriter radioE = null;
        try {
            radioE = new PipedWriter(readerGlobal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        flujoE = new BufferedReader(readerGlobal);
        for (int i = 0; i < Sincro.MAX_STATIONS; i++) {
            emisor[i] = new PipedWriter();
            flujoS[i] = new PrintWriter(emisor[i]);
            escucha[i] = new Estacion(i,sincro,radioE,emisor[i]);
        }


        sincro.esperarFin();
        System.out.println("Todas las estaciones están listas");

        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String estacion = leerNumeroEstacion();
            System.out.println("Movimiento detectado en " + estacion);

            enviarOrden(estacion);
        }
    }

    public static String leerNumeroEstacion() {
        String numero;
        try {
            numero = flujoE.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return numero;
    }

    public static void enviarOrden(String estacion) {
        Scanner teclado = new Scanner(System.in);
        String mensaje = teclado.nextLine();
        if (mensaje.equals("atacar")) {
            flujoS[Integer.parseInt(estacion)].println("atacar");
            flujoS[Integer.parseInt(estacion)].flush();
        }
    }


    public static void main(String[] args) {
        inciar();
    }
}
