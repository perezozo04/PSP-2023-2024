import java.io.File;
import java.io.IOException;

public class EjecutaRedirect {
    public static void main(String[] args) {
        String[] comando = {"cat","noexiste.txt"};
        ProcessBuilder pb = new ProcessBuilder(comando);
        File fout = new File("salida.txt");
        File ferr = new File("error.txt");
        pb.redirectOutput(fout);
        pb.redirectError(ferr);
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ejecucion terminada");

    }
}
