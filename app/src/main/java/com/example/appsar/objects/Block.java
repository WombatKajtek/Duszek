package com.example.appsar.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.example.appsar.framework.GameObject;
import com.example.appsar.framework.ObjectId;
import com.example.appsar.framework.Texture;
import com.example.appsar.window.GameView;

import java.util.LinkedList;

//klasa bloków, z których zbudowana jest mapa, dziedziczy on po klasie asbtrakcyjnej GameObject
public class Block extends GameObject {
    Texture tex = GameView.getInstance();

    //zmienna typ pozwala rysować 2 różne werjse bloków w zależności od podanego parametru
    private int type;


    //konstruktor
    public Block(float x, float y,int type, ObjectId id) {
        super(x, y, id);
        this.type=type;

    }



    public void tick(LinkedList<GameObject> object) {
    }

    //w zalezności od podanego typu rysowana jest odpowiednia wersja bloku
    public void render(Context context, Canvas canvas, Paint paint) {
        if (type == 0)
            canvas.drawBitmap(tex.block[0], x, y, paint);
        if (type == 1)
            canvas.drawBitmap(tex.block[1], x, y, paint);
        if (type == 2)
            canvas.drawBitmap(tex.block[2], x, y, paint);
        if (type == 3)
            canvas.drawBitmap(tex.block[3], x, y, paint);
    }

    //pobranie granic obiektu używane do wykrywania kolizji
    public Rect getBounds() {
        return new Rect((int)x,(int)y, (int)x+GameView.objectSize,(int)y+GameView.objectSize);
    }



}
