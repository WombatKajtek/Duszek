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
import com.example.appsar.window.Handler;

import java.util.LinkedList;

public class Player extends GameObject {


    private Handler handler;
    private float gravity=0.002f*GameView.objectSize;
    private final float MAX_SPEED = 40;
    public static int HEALTH = 100;
    public static int LIVES = 2;
    private boolean hit = false;




    Texture tex = GameView.getInstance();
    Equipment eq = GameView.getInstanceEq();

    private Animation playerWalk;
    private Animation playerWalkBackwards;


    public Player(float x, float y, Handler handler, ObjectId id, Boolean direction) {
        super(x, y, id);
        this.handler = handler;
        this.direction = direction;

        playerWalk = new Animation(10, tex.player[0], tex.player[1], tex.player[2], tex.player[3]);
        playerWalkBackwards = new Animation(10, tex.player[4], tex.player[5], tex.player[6], tex.player[7]);



    }



    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY;

        if (falling || jumping){
            velY += gravity;

            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }
        Collision(object);

        playerWalk.runAnimation();
        playerWalkBackwards.runAnimation();




    }


    //klasa obsługująca wykrywanie kolizji
    private void Collision(LinkedList<GameObject> objects){

        //iterujemy po liście obiektów
        for (int i=0;i<handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);

            //kolizje z blokami mapy, bloki ograniczają ruch gracza
            if (tempObject.getId() == ObjectId.Block){

                //z góry
                if (getBoundsTop().intersect(tempObject.getBounds())){
                    y = tempObject.getY() + GameView.objectSize+1;
                    velY=0;
                }

                //z dołu
                if (getBounds().intersect(tempObject.getBounds())){
                    y = tempObject.getY() - GameView.objectSize;
                    velY=0;
                    falling=false;
                    jumping=false;
                }
                else
                    falling=true;

                //z prawej
                if (getBoundsRight().intersect(tempObject.getBounds())){
                    x = tempObject.getX() - GameView.objectSize;

                }

                //z lewej
                if (getBoundsLeft().intersect(tempObject.getBounds())){
                    x = tempObject.getX() + GameView.objectSize;

                }
            }

            //kolizje z flagą pozwalająca na zmiane poziomu
            else if (tempObject.getId() == ObjectId.Flag){
                if (getBounds().intersect(tempObject.getBounds())) {

                    x=1000;
                    y=500;
                    handler.setDir(tempObject.getDirection());
                    handler.drawLevel();
                }
            }

            //kolizje z pająkiem kończy się utratą zdrowia i odtworzeniem animacji otrzymania
            // obrażeń
            else if (tempObject.getId() == ObjectId.Spider){
                if (getBounds().intersect(tempObject.getBounds())) {
                    hit=true;
                    if (HEALTH >0)
                 HEALTH--;
                }
            }

            else if (tempObject.getId() == ObjectId.Snail){
                if (getBounds().intersect(tempObject.getBounds())) {
                    hit=true;
                    if (HEALTH >0)
                        HEALTH--;
                }
            }

            //kolizje ze zbieralnymi przedmiotami, pozwala zbierać te przedmioty do ekwipunku.
            //jeśli wejdziemy w kolizję z tym przedmiotem to zostanie on usunięty z listy obiektów
            //a zostanie dodany do ekwipunku i narysowany na interfejsie
            else if (tempObject.getId() == ObjectId.Collectible){
                if (getBounds().intersect(tempObject.getBounds())) {
                Collectible col = (Collectible)tempObject;
                handler.removeObject(tempObject);
                if (col.getType()>=0)
                    eq.setSlot(col.getType());
                }
            }

            //kolizje z kotłem, jeśli zebraliśmy wszystkie przedmioty, to możemy je do niego włożyć
            //co pozwala ukończyć grę
            else if (tempObject.getId() == ObjectId.Cauldron) {
                if (getBounds().intersect(tempObject.getBounds())) {
                    if (eq.isFlashlight() && eq.isGum() && eq.isPaper()){
                eq.clearEq();
                GameView.gameWon=true;
                    }
                }
            }
        }
    }


    //metoda rysująca klatki animacji na ekranie, w zależności od różnych sytuacji: w którą stronę
    //jest zwrócony gracz, czy dostaje obrażenia, czy straciłwsszystkie punkty zdrowia
    public void render(Context context, Canvas canvas, Paint paint) {

            if (!direction) {
                if (HEALTH <= 0)
                    canvas.drawBitmap(tex.playerDeath[1], x, y, paint);
                else {
                    if (hit){
                        canvas.drawBitmap(tex.playerHit[1], x, y, paint);
                        hit=false;
                    }
                    else
                    playerWalkBackwards.drawAnimation(canvas, (int) x, (int) y, paint);
                }
            }
            else if (direction) {
                if (HEALTH <= 0)
                    canvas.drawBitmap(tex.playerDeath[0], x, y, paint);
                else {
                    if (hit){
                        canvas.drawBitmap(tex.playerHit[0], x, y, paint);
                        hit=false;
                    }
                    else
                    playerWalk.drawAnimation(canvas, (int) x, (int) y, paint);
                }

                }
        }




    //pobranie granic obiektu używane do wykrywania kolizji:

    //z dołu
    public Rect getBounds() {
        return new Rect((int)x+(GameView.objectSize/2)-((GameView.objectSize/2)/2),(int)y+GameView.objectSize/2, (int)(x+ GameView.objectSize/2),(int)(y+GameView.objectSize));
    }

    //z góry
    public Rect getBoundsTop() {
        return new Rect((int)x+(GameView.objectSize/2)-((GameView.objectSize/2)/2),(int)y, (int)(x+ GameView.objectSize/2),(int)(y+GameView.objectSize/2));
    }

    //z lewej strony
    public Rect getBoundsLeft() {
        return new Rect((int)x,(int)(y+0.02*GameView.objectSize), (int)(x+ (0.02*GameView.objectSize)),(int)(y+GameView.objectSize-0.02*GameView.objectSize));
    }

    //z prawej strony
    public Rect getBoundsRight() {
        return new Rect((int)(x+(GameView.objectSize)-(0.02*GameView.objectSize)),(int)(y+(0.02*GameView.objectSize)), (int)(x+ GameView.objectSize),(int)(y+GameView.objectSize-0.02*GameView.objectSize));
    }


}
