package com.example.fofe.opencv.tutorial3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SocketReceiver extends Activity {


    public final static int port = 4444;
    private ImageView imageView;

    public  SocketReceiver(ImageView imageView){
        this.imageView= imageView;

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
                        byte[] buffer = new byte[8192000];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                        //Cette méthode permet de récupérer le datagramme envoyé par le client
                        //Elle bloque le thread jusqu'à ce que celui-ci ait reçu quelque chose.
                        server.receive(packet);
                        Log.e("UDPServer","Packet recu");
                        //nous récupérons le contenu de celui-ci et nous l'affichons
                      byte[] data = packet.getData();
                      Log.e("UDP","lenngt of receive data ="+data.length);
                      final Bitmap bitmap= ByteArrayToBitmap(data);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               UpdateImageView(bitmap);
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

    private Bitmap ByteArrayToBitmap(byte[] byteArray){
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }
    private void UpdateImageView(Bitmap bitmap){
        Log.e("IMAGEVIEW","updateImgeView running");
imageView.setImageBitmap(bitmap);
    }
}
