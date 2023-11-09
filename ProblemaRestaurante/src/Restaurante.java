import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

class Restaurante {
    final static int MAX_PUESTOS=30;
    final static int MAX_COCINEROS=8;
    public static void main(String[] args) {

        PipedWriter emisor = new PipedWriter();
        try {
            PipedReader receptor = new PipedReader(emisor);
            Mesa [] mesa = new Mesa[MAX_PUESTOS];
            Sincro sincro = new Sincro(MAX_COCINEROS, MAX_PUESTOS);
            Metre metre = new Metre(sincro, receptor);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i <MAX_PUESTOS ; i++) {
                mesa[i] = new Mesa(i,sincro,emisor);
            }
            System.out.println("Iniciando el servicio de Restaurante");
            System.out.println("Pablo Pérez Martínez");
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String formattedTime = sdf.format(new Date(totalTime));
            sincro.esperarClientes();


            System.err.println("Simulación terminada");
            System.err.println("El tiempo de la simulación ha sido de " + totalTime + " ms");
            System.err.println("Pablo Pérez: " + formattedTime);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}