import java.io.IOException;

public class EjecutaEditProcessBuilder {
    public static void main(String[] args) {

        String comando = "gedit";

        ProcessBuilder pb = new ProcessBuilder(comando);
        try {
            Process p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Terminado principal");
    }
}
