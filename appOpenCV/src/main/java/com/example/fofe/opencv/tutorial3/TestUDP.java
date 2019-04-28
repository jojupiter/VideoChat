package com.example.fofe.opencv.tutorial3;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TestUDP  extends Activity{

    public final static int port = 4444;
    private EditText editText;

public  TestUDP(EditText editText){
this.editText= editText;

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
                   final String str = new String(packet.getData());





                    //editText.setText(str+"packet recu");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editText.setText(str+"   fofe ca donne");

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