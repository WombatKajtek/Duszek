package com.example.appsar.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.LinkedList;

//klasa abstrakcyjna po której dziedziczymy wszystkie obiekty w grze, zawiera współrzędne, metody do
//aktualizowania stanu obiektów, rysowania ich na ekranie oraz umożliwiająca wykrywanie kolizji
public abstract class GameObject {

    protected float x,y;
    protected ObjectId id;
    protected float velX=0,velY=0;
    protected boolean falling = true;
    protected boolean jumping = false;
    protected boolean direction = true;

    //konstruktor
    public GameObject(float x, float y, ObjectId id)
    {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    //metoda do aktualizowania stanu obietków
    public abstract void tick(LinkedList<GameObject> object);

    //metoda do rysowania obiektów na ekranie
    public abstract void render(Context context, Canvas canvas, Paint paint);

    //metoda pobierająca granice obiektu, używana do wykrywania kolizji
    public abstract Rect getBounds();

    //gettery i settery wszystkich atrybutów obiektów
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public float getVelX(){
        return velX;
    }
    public float getVelY(){
        return velY;
    }
    public void setVelX(float velX){
        this.velX=velX;
    }
    public void setVelY(float velY){
        this.velY=velY;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setDirection(boolean dir){
        this.direction = dir;
    }

    public boolean getDirection(){ return direction; }

    public ObjectId getId(){
        return id;
    }

}
