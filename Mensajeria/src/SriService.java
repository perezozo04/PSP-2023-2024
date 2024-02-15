import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class SriService extends Thread {
    private int id;
    private Socket s;
    private PrintWriter flujoS;
    private BufferedReader flujoEUdp;
    private PrintWriter flujoSUdp;
    private Scanner flujoE;
    //private InetAddress mCastInet;
    //private int mCastPort;



    public SriService(int id, Socket s, PrintWriter flujoS, BufferedReader flujoE) { // , InetAddress mCastInet, int mCastPort
        this.id = id;
        this.s = s;
        this.flujoSUdp = flujoS;
        this.flujoEUdp = flujoE;
        //this.mCastInet = mCastInet;
        //this.mCastPort = mCastPort;
        this.start();
    }


    public void run() {
        boolean fin = false;
        System.out.println("Conexion desde " + s.getInetAddress().getHostAddress());

        try {
            flujoS = new PrintWriter(s.getOutputStream());
            flujoE = new Scanner(s.getInputStream());
            flujoS.println("Bienvenido al servicio de atención al cliente" + id);
            flujoS.flush();
            while (!fin) {
                try {
                    String comando = flujoE.next();
                    if (comando.contains("QUIT")) {
                        fin = true;
                    } else {
                        procesaComando(comando);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println(e);
                    break;
                }
            }
            flujoS.close();
            flujoE.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void procesaComando(String comando) throws UnknownHostException {
        if (comando.contains("cliente")) {
            flujoS.println("Cliente: " + id);
            flujoS.flush();
        } else if (comando.contains("mensa")) {
            String restOfLine = flujoE.nextLine();
            Scanner lineScanner = new Scanner(restOfLine);
            if (lineScanner.hasNextInt()) {
                int numero = lineScanner.nextInt();
                String mensaje = lineScanner.nextLine();
                flujoS.println("Cliente " + numero + ": " + mensaje);
                SRIServiceUDP mCast = new SRIServiceUDP(flujoEUdp);
                flujoSUdp.println("Cliente " + numero + ": " + mensaje);
                flujoSUdp.flush();
                flujoSUdp.close();
                flujoS.close();
            }
            lineScanner.close();
        }
    }

}

