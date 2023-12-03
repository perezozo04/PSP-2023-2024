import java.io.*;
import java.sql.SQLOutput;

public class Tranca {
    public static final int MAX_CAMIONES = 10;
    public static final float precioKM = 0.2f;

    public static void main(String[] args) {
        Sincro sincro = new Sincro(MAX_CAMIONES);
        PipedWriter[] emisor = new PipedWriter[MAX_CAMIONES];
        PrintWriter[] flujoS = new PrintWriter[MAX_CAMIONES];

        PipedReader[] receptor = new PipedReader[MAX_CAMIONES];
        BufferedReader[] flujoE = new BufferedReader[MAX_CAMIONES];


        PipedWriter confirmaS = new PipedWriter();
        PipedReader confirmaE = new PipedReader();
        try {
            confirmaE = new PipedReader(confirmaS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Camion[] camiones = new Camion[MAX_CAMIONES];
        Central central = new Central(sincro, flujoS, confirmaE);
        for (int i = 0; i < MAX_CAMIONES; i++) {
            try {
                emisor[i] = new PipedWriter();
                flujoS[i] = new PrintWriter(emisor[i]);
                receptor[i] = new PipedReader(emisor[i]);
                flujoE[i] = new BufferedReader(receptor[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < MAX_CAMIONES; i++) {
            camiones[i] = new Camion(i, sincro, flujoE[i], confirmaS);
        }
        //sincro.esperarCamiones();
        System.out.println("Pablo Perez Martinez Iniciamos la simulacion...");
        long start = System.currentTimeMillis();
        //sincro.esperarFinCamiones();

        for(int i = 0; i < MAX_CAMIONES; i++) {
            System.out.println("El camion " + i + " ha hecho " + central.getKm(i) + "km Importe > " + (central.getKm(i)*precioKM) + "€");
        }
        //sincro.finalizarCentral();

        long end = System.currentTimeMillis();
        System.out.println("La simulacion ha terminado en " + (end - start) + "ms");
    }

}
