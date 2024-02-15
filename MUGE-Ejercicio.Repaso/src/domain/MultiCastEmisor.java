package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MultiCastEmisor extends Thread{
    private InetAddress mCastInet;
    private int mCastPort;
    private BufferedReader flujoE;
    private DatagramSocket sMulti;
    private boolean fin = false;

    public MultiCastEmisor(InetAddress mCastInet, int mCastPort, BufferedReader flujoE) {
        this.mCastInet = mCastInet;
        this.mCastPort = mCastPort;
        this.flujoE = flujoE;
        this.start();
    }

    public void run() {
        try {
            sMulti = new DatagramSocket();
            String mensa;
            while(!fin) {
                mensa = flujoE.readLine();
                DatagramPacket msgPacket = new DatagramPacket(
                        mensa.getBytes(), mensa.getBytes().length,
                        mCastInet,mCastPort
                );
                sMulti.send(msgPacket
                );
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
