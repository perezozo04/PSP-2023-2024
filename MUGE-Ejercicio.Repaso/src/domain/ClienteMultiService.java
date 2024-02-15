package domain;

import main.MugeServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ClienteMultiService extends Thread {
    // El segundo paso es completar esta clase, el primer paso es hacer el main MUGESERVER
    private int id;
    private Socket s;
    private PrintWriter flujoSPiped;
    PrintWriter flujoSClient;
    Scanner flujoEClient;
    private FirmaDigital miFirma = null;

    private Semaphore lock;

    public ClienteMultiService(int id, Socket s, PrintWriter flujoS, Semaphore lock) {
        this.id=id;
        this.s=s;
        this.flujoSPiped=flujoS;
        this.lock=lock;
        this.start();
    }

    public void run() {
        boolean fin = false;
        System.out.println("Conexión desde "+s.getInetAddress().getHostAddress());

        try {
            //Enlazamos los flujos
            flujoSClient = new PrintWriter(s.getOutputStream());
            flujoEClient = new Scanner(s.getInputStream());
            flujoSClient.println("Bienvenido a MUGE");
            flujoSClient.println("Puesto "+id);
            flujoSClient.println("Indica el comando (genera/publica/privada/quit/halt");
            flujoSClient.flush();
            while (!fin) {
                String comando = flujoEClient.next();
                System.out.println(comando);
                if (comando.contains("quit")) {
                    fin = true;
                }
                else if (comando.contains("halt")){
                    MugeServer.finalizarServidor();
                    fin = true;
                } else {
                    procesaComando(comando);
                }
                flujoSClient.flush();
            }
            flujoSClient.close();
            flujoEClient.close();
            s.close();
        } catch (IOException | NoSuchElementException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("El cliente cierra la terminal");
        }

    }

    private void procesaComando(String comando) {
        int siguienteAlumno;
        int pasaSiguienteAlumno;
        //Pedimos número como alumno
        if (comando.contains("genera")) {
            miFirma = new FirmaDigital();
            flujoSClient.println("Claves Generadas...");
            flujoSClient.println(">");
        }
        else if (comando.contains("publica")) {
            if (miFirma == null) {
                flujoSClient.println("Primero hay que generar la firma mediante el comando genera");
            } else {
                String miClavePublica = miFirma.getClavePub().toString();
                String clienteIP = s.getInetAddress().toString();
                String mensa = clienteIP + " CLAVE PUBLICA";
                flujoSClient.println(mensa);
                flujoSClient.println(miClavePublica);
                flujoSClient.println("---------------");
                //Enviamos por el piped a Multicast
                try {
                    lock.acquire();
                    flujoSPiped.println(mensa);
                    flujoSPiped.println(miClavePublica);
                    flujoSPiped.println("----------------");
                    flujoSPiped.flush();
                    lock.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        else if (comando.contains("privada")) {
            if (miFirma == null) {
                flujoSClient.println("Primero hay que generar la firma mediante el comando genera");
            }
        }
    }

}
