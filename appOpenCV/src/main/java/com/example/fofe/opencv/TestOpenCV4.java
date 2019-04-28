package com.example.fofe.opencv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TestOpenCV4 extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String Tag= "OCVS&mpleActivity";
    CameraBridgeViewBase cameraBridgeViewBase;
   Mat matServer;
static Mat Video;
int port = 4444;
    Encapsulated encapsulated;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    cameraBridgeViewBase.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test_open_cv4);
        //UdpServerOpencv udpServerOpencv= new UdpServerOpencv(Video);
        //udpServerOpencv.send();
        cameraBridgeViewBase = findViewById(R.id.camera_view);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);


    }


///////// method to send my Mat ////





    @Override
    protected void onResume() {
        super.onResume();
        if(!OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"problem in OpenCV",Toast.LENGTH_SHORT).show();
        }
        else{
            mLoaderCallback.onManagerConnected(mLoaderCallback.SUCCESS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        matServer= new Mat(width,height, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        matServer.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
/*
      mat=inputFrame.rgba();
      Mat mRgbaT= mat.t();
        Core.flip(mat.t(),mRgbaT,1);
        Imgproc.resize(mRgbaT,mRgbaT,mat.size());
        return mRgbaT;
*/
/*
        Mat rgba = inputFrame.rgba();
        Core.flip(rgba, rgba, 1);

        return rgba;
        //return inputFrame.rgba();
*/


MyVideoChatReceive();

return  Video;

    }


private void MyVideoChatReceive(){

    Log.e("UDP", "MyVideoChatReceive Running");
                try {

                    //Création de la connexion côté serveur, en spécifiant un port d'écoute
                    DatagramSocket server = new DatagramSocket(port);

                 //   while (true) {

                        //On s'occupe maintenant de l'objet paquet
                        byte[] buffer = new byte[8000];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
Log.e("BUFFER","taille du buffer      "+buffer.length);
                        //Cette méthode permet de récupérer le datagramme envoyé par le client
                        //Elle bloque le thread jusqu'à ce que celui-ci ait reçu quelque chose.
                        server.receive(packet);
                        Log.e("UDPServer", "Packet recu");
                        //nous récupérons le contenu de celui-ci et nous l'affichons

                        byte[] data = packet.getData();
                        ByteArrayInputStream in = new ByteArrayInputStream(data);
                        ObjectInputStream is = new ObjectInputStream(in);
                        try {
                            encapsulated = (Encapsulated) is.readObject();

                        } catch (Exception e) {
                            Log.e("UDP", "UDP server error ", e);
                        }


                        Video = StreamToMat(encapsulated.getMatOfByte());
                        Log.e("SOCKET", "mat recu           " + Video);
                   // }



                } catch (SocketException e) {
                    Log.e("UDP","socket error",e);
                } catch (IOException e) {
                    Log.e("Thread","thread exception",e);
                }
            }


    public MatOfByte MatToStream2(Mat mat){
        MatOfByte vect = new MatOfByte();
        if(Imgcodecs.imencode(".png",mat,vect)){
            return new MatOfByte(vect.toArray());
        }

        return null;
    }


    public Mat StreamToMat(MatOfByte matOfByte){

        Mat mat = Imgcodecs.imdecode(new MatOfByte(matOfByte), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        return mat;
    }
}
