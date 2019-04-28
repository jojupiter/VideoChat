package com.example.fofe.opencv.tutorial3;


import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public  class UDPClient implements Runnable{
        String name = "";
        long sleepTime = 1000;
        EditText editText;
int port= 4444;
        public UDPClient(String pName, long sleep, EditText editText){
            name = pName;
            sleepTime = sleep;
            this.editText= editText;
        }

        public void run(){
            int nbre = 0;
            while(true){
                String envoi = name + "-" + (++nbre);
                byte[] buffer = envoi.getBytes();

                try {
                    //On initialise la connexion côté client
                    DatagramSocket client = new DatagramSocket();

                    //On crée notre datagramme
                    InetAddress adresse = InetAddress.getByName("192.168.43.183");
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, port);

                    //On lui affecte les données à envoyer
                    packet.setData(buffer);

                    //On envoie au serveur
                    client.send(packet);




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

}


