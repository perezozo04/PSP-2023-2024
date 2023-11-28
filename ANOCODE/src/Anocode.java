import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Scanner;

public class Anocode {
    private static Estacion[] escucha;
    private static PipedWriter[] emisor;
    private static PipedWriter pipedRadioSalida;
    private static PipedReader pipedRadioEntr;
    private static Sincro sincro;
    private static PipedReader readerGlobal;

    public Anocode() {
        escucha = new Estacion[Sincro.MAX_STATIONS];
        emisor = new PipedWriter[Sincro.MAX_STATIONS];
        sincro = new Sincro();
        readerGlobal = new PipedReader();

    }

    public static void inciar() {
        for (int i = 0; i < emisor.length; i++) {
            emisor[i] = new PipedWriter();
            escucha[i] = new Estacion(emisor[i], sincro, i);
        }
        for (int i = 0; i < escucha.length; i++) {
            escucha[i].emisor = emisor[i];
        }

        sincro.esperarFin();
        System.out.println("Todas las estaciones están listas");

        while(true) {
            String estacion = leerNumeroEstacion();
            System.out.println("Movimiento detectado en " + estacion);

            if(leerOrden()) {
                atacar(estacion);
            }
        }
    }

    public static String leerNumeroEstacion() {
        int numero;
        try {
            numero = readerGlobal.read();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return String.valueOf(numero);
    }

    public static boolean leerOrden() {
        Scanner teclado = new Scanner(System.in);
        String mensaje = teclado.nextLine();
        if (mensaje.equals("atacar")) {
            return true;
        } else { return false; }
    }

    public static void atacar(String estacion) {
        System.out.println("Enviando orden de ataque a la estacion " + estacion);
        try {
            emisor[Integer.parseInt(estacion)].write("atacar");
            emisor[Integer.parseInt(estacion)].flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new Anocode().inciar();
    }
}
