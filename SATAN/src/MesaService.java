import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MesaService extends Thread {
    private Socket s;
    private int id;
    private int mesaId;
    private ContadorMesas cm;
    private PrintWriter flujoSalida;
    private Scanner flujoEntrada;
    public MesaService (Socket s, int id, ContadorMesas cm) {
        this.s = s;
        this.mesaId = 0;
        this.id = id;
        this.cm = cm;
        this.start();
    }

    public void run() {
        boolean fin = false;
        System.out.println("Conexion desde " + s.getInetAddress().getHostAddress() + " en el puerto " + s.getPort());

        try {
            //Enlazamos los flujos de entrada y salida
            flujoSalida = new PrintWriter(s.getOutputStream());
            flujoEntrada = new Scanner(s.getInputStream());
            flujoSalida.println("Bienvenido a SATA");
            flujoSalida.println("Puesto " + id);
            flujoSalida.println("¿En qué mesa desea ser atendido?");
            flujoSalida.flush();
            while (!fin) {
                String comando = flujoEntrada.next();
                if (comando.contains("quit")) {
                    fin = true;
                } else {
                    if(comando.contains("mesa")) {
                        mesaId = flujoEntrada.nextInt();
                        if (mesaId > 0) {
                            flujoSalida.println("Seleccionada la mesa " + mesaId);
                        } else {
                            flujoSalida.println("Error de comando");
                        }
                    } else {
                        //Para cualquier comando no mesa
                        //Primero tenemos que tener asignada una mesa
                        if (mesaId > 0) {
                            procesaComando(comando);
                        }
                        else {
                            flujoSalida.println("Asigna primero una mesa ");
                        }
                    }
                }
                flujoSalida.flush();
            }
            flujoSalida.println("Adios");
            flujoSalida.flush();
            flujoSalida.close();
            flujoEntrada.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void procesaComando(String comando) {
        int siguienteAlumno;
        int pasaSiguienteAlumno;
        //Pedimos numero como alumno
        if(comando.contains("numero")) {
            siguienteAlumno = cm.nextAlumno();
            String mensaje = "Su número es " + siguienteAlumno;
            flujoSalida.println(mensaje + " espera a ser atendido ");
            flujoSalida.flush();
        }
        else if (comando.contains("next")) {
            pasaSiguienteAlumno = cm.nextContador();
            if (pasaSiguienteAlumno > 0) {
                String mensaje = "NUMERO: " + pasaSiguienteAlumno + " Pase a la mesa " + mesaId;
                flujoSalida.println(mensaje);
                flujoSalida.flush();
            } else {
                flujoSalida.println("No hay alumnos pendientes");
                flujoSalida.flush();
            }
        }
    }

}
