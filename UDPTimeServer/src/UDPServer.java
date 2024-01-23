

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPServer {
    
    
    public static void main(String[]args) {
        final int port = 7777;
        boolean fin = false;
        
        try {
            DatagramSocket sUDP = new DatagramSocket(port);
            
            byte[] buffer = new byte[1024];
            
            while (!fin) {
                //Construimos un datagrama para recibir las peticiones
                DatagramPacket peticion = new DatagramPacket (buffer, buffer.length);
                
                //Leemos las peticiones
                sUDP.receive(peticion);
                System.out.println("Datagrama recibido desde " + peticion.getAddress());
                
                System.out.println("Perto : "+ peticion.getPort());
                
                
                //Enviamos un echo como respuesta
                DatagramPacket respuesta = new DatagramPacket(buffer, port)
                
            }
            
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        
    }

}