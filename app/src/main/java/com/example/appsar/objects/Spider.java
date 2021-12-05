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
import java.util.concurrent.ThreadLocalRandom;

public class Spider extends GameObject {
    Texture tex = GameView.getInstance();
    float startingPoint;
    private Animation spiderMove;


    //konstruktor
    public Spider(float x, float y,  ObjectId id) {
        super(x, y, id);
        startingPoint = y;
        this.y = ThreadLocalRandom.current().nextInt((int)startingPoint, (int)startingPoint+101);
        spiderMove = new Animation(10, tex.spider[0], tex.spider[1], tex.spider[2]);
    }


    //metoda aktualizująca położenie pająków oraz uzyskująca kolejną klatkę animacji
    public void tick(LinkedList<GameObject> object) {
        spiderMove.runAnimation();
        if (y >= startingPoint+100)
            direction = false;
        if (y <= startingPoint)
            direction = true;
        if (direction)
            velY=2;
        else velY=-2;
        y+= velY;
    }

    //metoda rysująca klatki animacji
    public void render(Context context, Canvas canvas, Paint paint) {
        spiderMove.drawAnimation(canvas, (int)x, (int)y, paint);
    }

    //pobranie granic obiektu używane do wykrywania kolizji
    public Rect getBounds() {
        return new Rect((int)x,(int)y, (int)x+GameView.objectSize,(int)y+GameView.objectSize);
    }

    public float getStartingPoint() {
        return startingPoint;
    }
}
