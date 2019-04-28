package com.example.fofe.opencv.tutorial3;

import android.util.Log;

import com.example.fofe.opencv.Encapsulated;

import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClientOpencv implements Runnable {


    Encapsulated encapsulated;
    byte[] buf ;
    long sleepTime = 1;
   Mat mat;
    int port= 4444;
    public UdpClientOpencv( Mat mat){
        this.mat= mat;
    }


    public void run(){


try {
    //encapsulated = new Encapsulated(mat);
    Log.e("UDPSerialise","SERIALISE ");
}catch (Exception e){

    Log.e("UDPSerialise","SERIALISE ERROR",e);
}

try {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(encapsulated);
    buf = baos.toByteArray();

}catch (Exception e){
    Log.e("UDPsocket"," stream error",e);
}



            try {
                //On initialise la connexion côté client
                DatagramSocket client = new DatagramSocket();


                InetAddress adresse = InetAddress.getByName("192.168.43.183");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, adresse, port);

                //On lui affecte les données à envoyer
                packet.setData(buf);

                //On envoie au serveur
                client.send(packet);
Log.e("UDP","message send ");



                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







