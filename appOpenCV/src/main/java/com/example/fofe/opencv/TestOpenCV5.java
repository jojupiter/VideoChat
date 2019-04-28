package com.example.fofe.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

public class TestOpenCV5 extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

private static final String Tag= "OCVS&mpleActivity";
    CameraBridgeViewBase cameraBridgeViewBase;
    Mat mat;
    Mat rgba;
    byte[] buf ;
    int temp = 0;

    int port= 4444;

    InetAddress adresse;
    DatagramSocket client;

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
        setContentView(R.layout.activity_test_open_cv5);
        try {
            client = new DatagramSocket();
            adresse = InetAddress.getByName("192.168.43.183");
        }catch (Exception e){
            Log.e("SOCKET","error socket",e);
        }
        cameraBridgeViewBase = findViewById(R.id.javacom);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
      //  SaveBimapInGallery(cameraBridgeViewBase.getDrawingCache());

     //   UdpClientOpencv udpClientOpencv = new UdpClientOpencv(rgba);
      //  Thread cli1 = new Thread(udpClientOpencv);
       // cli1.start();

    }




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
        mat= new Mat(width,height, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mat.release();
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
         rgba = inputFrame.rgba();
        Core.flip(rgba, rgba, 1);

Log.e("TEMP","value of temp = "+temp);


        //    MyVideoChatSend();

        return rgba;
     //return inputFrame.rgba();

    }




private void   MyVideoChatSend(){





    try {

        buf= BipmapToBytearry(MatToBipmap(rgba));


    }catch (Exception e){
        Log.e("UDPsocket"," stream error",e);
    }


    try {
        //On initialise la connexion côté client

        DatagramPacket packet = new DatagramPacket(buf, buf.length, adresse, port);

        Log.e("BUFFER","taille du buffer      "+buf.length);
        //On lui affecte les données à envoyer
        packet.setData(buf);

        //On envoie au serveur
        client.send(packet);
        Log.e("UDP","message send ");
        Log.e("TEST","on camera frame            "+rgba);




    } catch (SocketException e) {
        e.printStackTrace();
    } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public MatOfByte MatToStream2(Mat mat){
        MatOfByte vect = new MatOfByte();
        if(Imgcodecs.imencode(".png",mat,vect)){
            return new MatOfByte(vect.toArray());
        }

        return null;
    }
public Bitmap MatToBipmap(Mat rgba){

    Bitmap image = Bitmap.createBitmap(rgba.cols(),
            rgba.rows(), Bitmap.Config.RGB_565);

    Utils.matToBitmap(rgba, image);

    Bitmap bitmap = (Bitmap) image;
    bitmap = Bitmap.createScaledBitmap(bitmap, 600, 450, false);
    return  bitmap;
}



public byte[] BipmapToBytearry(Bitmap bitmap){
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    byte[] byteArray = byteArrayOutputStream .toByteArray();
    return  byteArray;
}
private Bitmap ByteArrayToBitmap(byte[] byteArray){
    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    return bitmap;
}

private void SaveBimapInGallery(Bitmap bitmap) {

// in thread this method run three times

//  Get path to new gallery image

        Log.e("TEMP", "temp =" + temp);
        temp =  temp + 1;
        if (temp == 100) {

         ;
            try {
                File file = new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera.jpeg/");
                FileOutputStream fileOutputStream= new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                fileOutputStream.close();

            } catch (Exception e) {
                Log.e("GALLERY", "E: " + e.getMessage());
            }


    }
}



    public static File createImageFile(Bitmap bitmap) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        File storageDir = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/");
        if (!storageDir.exists())
            storageDir.mkdirs();


        File image = File.createTempFile(
                timeStamp,                   /* prefix */
                ".jpeg",                     /* suffix */
                storageDir                   /* directory */
        );

        return image;
    }




    public Mat StreamToMat(MatOfByte matOfByte){

        Mat mat = Imgcodecs.imdecode(new MatOfByte(matOfByte), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        return mat;
    }
}
