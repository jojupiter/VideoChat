package com.example.fofe.opencv.tutorial3;

import android.app.Activity;
import android.util.Log;

import com.example.fofe.opencv.Encapsulated;

import org.opencv.core.Mat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServerOpencv  extends Activity{



    Encapsulated encapsulated;
Mat matVideo;
    public final static int port = 4444;

    public UdpServerOpencv(Mat matVideo){
this.matVideo=matVideo;
    }

    public void send() {
        Log.e("UDP","Test UDP");
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {

                    //Création de la connexion côté serveur, en spécifiant un port d'écoute
                    DatagramSocket server = new DatagramSocket(port);

                    while (true) {

                        //On s'occupe maintenant de l'objet paquet
                        byte[] buffer = new byte[8192];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                        //Cette méthode permet de récupérer le datagramme envoyé par le client
                        //Elle bloque le thread jusqu'à ce que celui-ci ait reçu quelque chose.
                        server.receive(packet);
                        Log.e("UDPServer","Packet recu");
                        //nous récupérons le contenu de celui-ci et nous l'affichons

                        byte[] data = packet.getData();
                        ByteArrayInputStream in = new ByteArrayInputStream(data);
                        ObjectInputStream is = new ObjectInputStream(in);
                        try{
                             encapsulated= (Encapsulated) is.readObject();

                        }catch (Exception e){
                            Log.e("UDP","UDP server error ",e);
                        }







                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            //    matVideo= encapsulated.getMat();
                                Log.e("SOCKET","mat recu           "+matVideo);
                            }
                        });

                    }
                } catch (SocketException e) {
                    Log.e("UDP","socket error",e);
                } catch (IOException e) {
                    Log.e("Thread","thread exception",e);
                }
            }
        });
        t.start();
    }




}
