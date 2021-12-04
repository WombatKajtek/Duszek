package com.example.appsar.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.appsar.framework.GameObject;
import com.example.appsar.framework.ObjectId;
import com.example.appsar.framework.Texture;
import com.example.appsar.window.Animation;
import com.example.appsar.window.GameView;

import java.util.LinkedList;

public class Spider extends GameObject {
    Texture tex = GameView.getInstance();
    int bot=0;
    private Animation spiderMove;


    //konstruktor
    public Spider(float x, float y,  ObjectId id) {
        super(x, y, id);

        spiderMove = new Animation(10, tex.spider[0], tex.spider[1], tex.spider[2]);
    }


    //metoda aktualizująca położenie pająków oraz uzyskująca kolejną klatkę animacji
    public void tick(LinkedList<GameObject> object) {
        spiderMove.runAnimation();
        if (bot >= 100)
            direction = false;
        if (bot <100)
            direction = true;
        if (direction)
            velY=2;
        else velY=-2;
        y+= velY;
        bot++;
        if (bot >= 200)
            bot=0;
    }

    //metoda rysująca klatki animacji
    public void render(Context context, Canvas canvas, Paint paint) {
        spiderMove.drawAnimation(canvas, (int)x, (int)y, paint);
    }

    //pobranie granic obiektu używane do wykrywania kolizji
    public Rect getBounds() {
        return new Rect((int)x,(int)y, (int)x+GameView.objectSize,(int)y+GameView.objectSize);
    }



}
