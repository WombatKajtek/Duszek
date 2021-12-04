package com.example.appsar.framework;

import android.graphics.Bitmap;

//klasa pozwalająca wczytywać całe sprite sheety, zamiast pojedynczych obrazków
public class SpriteSheet {

    private Bitmap image;

    public SpriteSheet(Bitmap image){
        this.image = image;
    }


    //metoda pozwalająca wybrać pojedynczy obrazek z całego arkuszu
    public Bitmap grabImage(int col, int row, int width, int height){
        Bitmap img = Bitmap.createBitmap(image, (col*width)-width, (row*height)-height,width,height);
        return img;
    }


}
