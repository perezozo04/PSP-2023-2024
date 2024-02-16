import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SRIServiceUDP extends Thread{

    private InetAddress mCastInet;
    private int mCastPort;
    private BufferedReader flujoE;


    public SRIServiceUDP(BufferedReader flujoE) throws UnknownHostException {
        this.flujoE = flujoE;
        this.mCastInet = InetAddress.getByName("224.0.0.1");
        this.mCastPort = 7777;
        this.start();
    }

    public void run() {
        DatagramSocket sUDP;
        try {
            String mensa;
            sUDP = new DatagramSocket();
                mensa = flujoE.readLine();
                DatagramPacket msgPantalla = new
                        DatagramPacket(mensa.getBytes(),
                        mensa.getBytes().length,mCastInet,mCastPort);
                sUDP.send(msgPantalla);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
