package ut1_06_NADC;

import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Norad {
    final static int MAXMISILES = 100;

    public static void main(String[] args) {
        Sincroniza sincro = new Sincroniza(MAXMISILES);

        Misil[] misil = new Misil[MAXMISILES];

        PipedWriter[] emisor = new PipedWriter[MAXMISILES];
        PrintWriter[] flujos = new PrintWriter[MAXMISILES];
        for (int i = 0; i < misil.length; i++) {
            emisor[i] = new PipedWriter();
            flujos[i] = new PrintWriter(emisor[i]);
            misil[i] = new Misil(i,sincro,emisor[i]);
        }
        sincro.esperarMisilesListos();
        System.out.println("Todos los misiles listos");
        Scanner sc = new Scanner(System.in);
        boolean fin = false;
        while(!fin) {
            System.out.println("Orden > ");
            String comando = sc.nextLine();
            if (comando.contains("atacar")) {
                fin = true;
                sincro.realizarActivacion();
                dobleVerificacion(flujoS);
            }
        }
    }

    private static void dobleVerificacion(PrintWriter[] flujoS, String comando) {
        for (int i = 0; i < MAXMISILES; i++) {
            flujoS[i].println("atacar");

        }
    }


}
