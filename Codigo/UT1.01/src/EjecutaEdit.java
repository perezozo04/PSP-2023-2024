import java.io.*;

public class EjecutaEdit {
    public static void main(String[] args) {

        String[] comando = {"ls", "-l"};
        Runtime runtimeActual = Runtime.getRuntime();
        try {
            Process p = runtimeActual.exec(comando);
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);
            while ( linea != null) {
                String linea;
                linea = buffer.readLine();
            }
            try {
                int estado = p.waitFor();
                System.out.println("Proceso principal " + estado);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Terminado principal Proceso");
    }
}
