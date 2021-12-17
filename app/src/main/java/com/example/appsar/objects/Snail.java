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

public class Snail extends GameObject {
    Texture tex = GameView.getInstance();
    private float startingPoint;
    private Animation snailMoveR, snailMoveL;

    //konstruktor
    public Snail(float x, float y, boolean direction, ObjectId id) {
        super(x, y, id);
        this.direction = direction;
        startingPoint = x;

        snailMoveR = new Animation(10, tex.snail[0], tex.snail[1], tex.snail[2]);
        snailMoveL = new Animation(10, tex.snail[3], tex.snail[4], tex.snail[5]);
    }


    //metoda aktualizująca położenie pająków oraz uzyskująca kolejną klatkę animacji
    public void tick(LinkedList<GameObject> object) {
        snailMoveR.runAnimation();
        snailMoveL.runAnimation();

        if (startingPoint > GameView.screenWidth/2) {
            if (x >= startingPoint)
                direction = false;
            if (x <= startingPoint - 1024)
                direction = true;
        }
        else
        {
            if (x >= startingPoint + 1024)
                direction = false;
            if (x <= startingPoint)
                direction = true;
        }

        if (direction)
            velX=2;
        else velX=-2;
        x+= velX;


    }

    //metoda rysująca klatki animacji
    public void render(Context context, Canvas canvas, Paint paint) {
        if (direction)snailMoveR.drawAnimation(canvas, (int)x, (int)y, paint);
        else snailMoveL.drawAnimation(canvas, (int)x, (int)y, paint);
    }

    //pobranie granic obiektu używane do wykrywania kolizji
    public Rect getBounds() {
        return new Rect((int)x,(int)y, (int)x+GameView.objectSize,(int)y+GameView.objectSize);
    }

    public float getStartingPoint() {
        return startingPoint;
    }
}
