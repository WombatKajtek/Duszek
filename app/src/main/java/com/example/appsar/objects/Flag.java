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

//klasa flag używanych do przechodzenia pomiędzy planszami, dziedziczy po klasie GameObject.
//flagi są niewidoczne dlatego metoda render pozostaje pusta, potrzebujemy jedynie granice obiektu
//do wykrywania kolizji
public class Flag extends GameObject {

    //konstruktor
    public Flag(float x, float y, boolean direction, ObjectId id) {
        super(x, y, id);
        this.direction = direction;
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Context context, Canvas canvas, Paint paint) {

    }

    //pobranie granic obiektu używane do wykrywania kolizji
    public Rect getBounds() {
        return new Rect((int)x,(int)y, (int)x+GameView.objectSize,(int)y+GameView.objectSize);
    }



}
