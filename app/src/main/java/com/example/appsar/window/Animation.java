package com.example.appsar.window;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//klasa pozawalająca odtwarzać animacje
public class Animation {

    private int speed;
    private int frames;

    private int index=0;
    private int count=0;

    private Bitmap[] images;
    private Bitmap currentImg;

    //konstruktor
    public Animation(int speed, Bitmap... args){
        this.speed=speed;
        images = new Bitmap[args.length];
        for (int i=0; i<args.length; i++){
            images[i]=args[i];
        }
        frames = args.length;
        currentImg = args[0];
    }

    //metoda pozwalająca na odtwarzanie animacji
    public void runAnimation(){
        index = (index+1) % speed;
        if (index == 0) {
            nextFrame();
        }
    }

    //metoda uzyskująca kolejną klatkę animacji
    private void nextFrame(){
        currentImg = images[count++ % frames];
    }

    //metoda rysująca klatkę animacji na ekranie
    public void drawAnimation(Canvas canvas, int x, int y, Paint paint){
        canvas.drawBitmap(currentImg, x, y, paint);
    }







}
