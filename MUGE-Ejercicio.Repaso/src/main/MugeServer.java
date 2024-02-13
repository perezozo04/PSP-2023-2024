package main;

import domain.ClienteMultiService;
import domain.MultiCastEmisor;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class MugeServer {
    public static void main(String[] args) {
        try {
            //Lista de Sockets remotos
            List<Socket> clientesRemotos = new LinkedList<>();

            //Clase para el contador, máximo de 10 funcionarios
            final String mCastIpIP = "224.0.1.1";
            final int mCastPort = 7777;
            InetAddress mCastInet = InetAddress.getByName(mCastIpIP);

            //Clase que envía por multicast
            // Esto para comunicarse con el multicast Emisor
            PipedWriter emisor = new PipedWriter();
            PipedReader receptor = new PipedReader(emisor);
            PrintWriter flujoS = new PrintWriter(emisor);
            BufferedReader flujoE = new BufferedReader(receptor);
            Semaphore lock = new Semaphore(1);
            MultiCastEmisor mCastEmisor = new MultiCastEmisor(mCastInet, mCastPort, flujoE);

            int port = 8888;
            ServerSocket miServer = new ServerSocket(port);

            System.out.println("Servicio Clave funcionando... at "+port);

            int id = 0;
            boolean fin = false;
            while (!fin) {
                //Acceptamos la conexión
                Socket s = miServer.accept();
                clientesRemotos.add(s);
                String ipCliente = s.getRemoteSocketAddress().toString();
                System.out.println("Funcionario "+id+" conectado desde "+ipCliente);

                //Iniciamos el servicio que gestiona la conexión
                ClienteMultiService clienteMultiService = new ClienteMultiService(id,s,flujoS,lock);

                id++;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void finalizarServidor() {
    }
}
