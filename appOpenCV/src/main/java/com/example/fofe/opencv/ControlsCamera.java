package com.example.fofe.opencv;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;

import org.opencv.android.JavaCameraView;

import java.util.List;

public class ControlsCamera extends JavaCameraView {



    public ControlsCamera(Context context, int cameraId) {
        super(context,cameraId);
    }

    public ControlsCamera(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //get list of preview
    public List<Camera.Size> getResolutionList() {
        return mCamera.getParameters().getSupportedPreviewSizes();
    }

    //start camera by connecting connect camera
    void startcamera()
    {
        connectCamera(getWidth(), getHeight());
    }

    //stop the camera and its thread
    void stopCamera()
    {
        disconnectCamera();
    }

    // set resolution
    public void setResolution(Camera.Size resolution) {
        disconnectCamera(); //disconnect camera
        mMaxHeight = 30000;//resolution.height;
        mMaxWidth =15000;//resolution.width;
        connectCamera(mMaxWidth, mMaxHeight); //connect camera

    }

}
