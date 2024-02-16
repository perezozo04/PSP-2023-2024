import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class SRIServer {

    public static void main(String[] args) {
        try {
            //Lista de Sockets remotos
            List<Socket> clientesRemotos = new LinkedList<>();

            //Clase para el contador, máximo de 10 funcionarios

            //final String mCastIpIP = "224.0.1.1";
            //final int mCastPort = 8889;
            //InetAddress mCastInet = InetAddress.getByName(mCastIpIP);


            PipedWriter emisor = new PipedWriter();
            PipedReader receptor = new PipedReader(emisor);
            PrintWriter flujoS = new PrintWriter(emisor);
            BufferedReader flujoE = new BufferedReader(receptor);

            //Clase que envía por multicast

            int port = 8888;
            ServerSocket miServer = new ServerSocket(port);

            System.out.println("Servicio de aTención al Alumno Numérico (SATAN)... at " + port);
            boolean fin = false;
            int id = 0;
            while (!fin) {
                //Acceptamos la conexión
                Socket s = miServer.accept();
                clientesRemotos.add(s);
                String ipCliente = s.getRemoteSocketAddress().toString();
                System.out.println("Funcionario " + id + " conectado desde " + ipCliente);

                SriService sriService = new SriService(id, s, flujoS, flujoE);

                //Iniciamos el servicio que gestiona la conexión
                id++;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
