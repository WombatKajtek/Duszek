package com.example.appsar.window;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//klasa pozwalająca na wczytywanie obrazóz w pliku z opcją ich skalowania w zależności od podanych
//argumentów zostanie użyty jeden z dwóch konstruktorów
public class BufferedImageLoader {

    private Bitmap image;

    //konstruktor do wczytywania obrazów bez skalowania
    public Bitmap loadImage(Context context, int path){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        image = BitmapFactory.decodeResource(context.getResources(), path, opts );
        return image;
    }

    //konstruktor pozwalającay wczytać obraz i go przeskalować do podanych rozmiarów
    public Bitmap loadImage(Context context, int path, int width, int height){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        image = BitmapFactory.decodeResource(context.getResources(), path, opts );
        image = Bitmap.createScaledBitmap(image, width,height,false);
        return image;
    }
}
