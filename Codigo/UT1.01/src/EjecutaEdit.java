import java.io.IOException;

public class EjecutaEdit {
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