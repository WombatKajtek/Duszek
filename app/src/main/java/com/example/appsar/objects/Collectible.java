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

public class Collectible extends GameObject {
    Texture tex = GameView.getInstance();
    private int type;

    public Collectible(float x, float y, ObjectId id, int type) {
        super(x, y, id);
        this.type = type;
    }

    public int getType() {
        return type;
    }


    public void tick(LinkedList<GameObject> object) {
    }


    public void render(Context context, Canvas canvas, Paint paint) {
        if (type == 0)
            canvas.drawBitmap(tex.collectible[0], x, y, paint);
        if (type == 1)
            canvas.drawBitmap(tex.collectible[1], x, y, paint);
        if (type == 2)
            canvas.drawBitmap(tex.collectible[2], x, y, paint);

    }

    //pobranie granic obiektu używane do wykrywania kolizji
    public Rect getBounds() {
        return new Rect((int)x,(int)y, (int)x+ GameView.objectSize,(int)y+GameView.objectSize);
    }
}
