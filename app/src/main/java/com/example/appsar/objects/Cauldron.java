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

public class Cauldron extends GameObject {
    Texture tex = GameView.getInstance();
    private Animation cauldronMove;

    //konstruktor
    public Cauldron(float x, float y, ObjectId id) {
        super(x, y, id);
        cauldronMove = new Animation(10, tex.cauldron[0], tex.cauldron[1]);

    }

    //uzyskanie kolejnej klatki animacji
    public void tick(LinkedList<GameObject> object) {
        cauldronMove.runAnimation();

    }

    //rysowanie pojedynczej klatki animacji na ekranie
    public void render(Context context, Canvas canvas, Paint paint) {
        cauldronMove.drawAnimation(canvas, (int)x, (int)y, paint);

    }

    //pobranie granic obiektu używane do wykrywania kolizji
    public Rect getBounds() {
        return new Rect((int)x,(int)y, (int)x+ GameView.objectSize,(int)y+GameView.objectSize);
    }
}
