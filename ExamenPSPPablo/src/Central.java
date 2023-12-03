import java.io.*;
import java.util.Random;

public class Central extends Thread{

    private BufferedReader flujoE;
    private PrintWriter[] flujoS = new PrintWriter[Tranca.MAX_CAMIONES];

    public int getKm(int i) {
        return km[i];
    }

    private int[] km = new int[Tranca.MAX_CAMIONES];
    private Sincro sincro;
    public Central(Sincro sincro, PrintWriter[] flujoS, PipedReader confirmaE){
        this.sincro = sincro;
        this.flujoS = flujoS;
        this.flujoE = new BufferedReader(confirmaE);
        this.start();
    }
    @Override
    public void run() {
        boolean fin = false;
        for (int i = 0; i < Tranca.MAX_CAMIONES; i++){
            int camionRandom = generarNumeroCamiion();
            int kmRandom = generarKm();
            flujoS[camionRandom].println(kmRandom);
            km[camionRandom] += kmRandom;
            flujoS[i].flush();
            try {
                flujoE.readLine();
            } catch (IOException e) {
                   throw new RuntimeException(e);
            }
        }
        sincro.finalizarCamiones();
        sincro.esperarFinCentral();
        for (int i = 0; i < Tranca.MAX_CAMIONES; i++){
            flujoS[i].println("FIN");
            flujoS[i].flush();
        }
    }

    private int generarNumeroCamiion() {
        Random rand = new Random();
        return rand.nextInt(Tranca.MAX_CAMIONES);
    }

    private int generarKm() {
        Random rand = new Random();
        return rand.nextInt(500)+500;
    }



}
