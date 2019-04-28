package com.example.fofe.opencv;

import org.opencv.core.MatOfByte;

import java.io.Serializable;

public class Encapsulated implements Serializable {

public Encapsulated(MatOfByte matOfByte){
    this.matOfByte= matOfByte;
}

    private MatOfByte matOfByte;


    public MatOfByte getMatOfByte() {
        return matOfByte;
    }

    public void setMat(MatOfByte matOfByte) {
        this.matOfByte = matOfByte;
    }
}
