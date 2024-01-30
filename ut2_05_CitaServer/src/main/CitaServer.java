package main;


import domain.MesaService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class CitaServer {
	public static void main(String[] args) {
		try {
			// Lista de Sockets remotos
			List<Socket> clientesRemotos = new LinkedList<>();
			
			int port = 8888;
			ServerSocket miServer = new ServerSocket(port);
			
			System.out.println("Servicio de atencion al cliente al Alumno Numérico (SATAN)... at "+port);
			boolean fin = false;
			int id = 1;
			while (!fin) {
				//Acceptamos la conexión
				Socket s = miServer.accept();
				//Iniciamos el servicio que gestiona la conexión
				MesaService mesaService = new MesaService(id, s);
				clientesRemotos.add(s);
				String ipCliente = s.getRemoteSocketAddress().toString();
				System.out.println("Puesto " +id+ " conectado desde " +ipCliente);
				id++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
