package com.example.appsar.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.SoundPool;

import com.example.appsar.framework.GameObject;
import com.example.appsar.framework.ObjectId;
import com.example.appsar.framework.Sound;
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
    private boolean soundPlaying = false;
    private int streamId;




    Texture tex = GameView.getInstance();
    Equipment eq = GameView.getInstanceEq();
    Sound sound = GameView.getInstanceS();

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


        if (!soundPlaying) {
            if (hit) {
                soundPlaying = true;
                streamId = sound.playSound(sound.soundGettingHit, -1);
            }
        }
            if (!hit) {
                soundPlaying = false;
                sound.stopSound(streamId);
            }

    }


    //klasa obs??uguj??ca wykrywanie kolizji
    private void Collision(LinkedList<GameObject> objects){

        //iterujemy po li??cie obiekt??w
        for (int i=0;i<handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);

            //kolizje z blokami mapy, bloki ograniczaj?? ruch gracza
            if (tempObject.getId() == ObjectId.Block){

                //z g??ry
                if (getBoundsTop().intersect(tempObject.getBounds())){
                    y = tempObject.getY() + GameView.objectSize+1;
                    velY=0;
                }

                //z do??u
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

            //kolizje z flag?? pozwalaj??ca na zmiane poziomu
            else if (tempObject.getId() == ObjectId.Flag){
                if (getBounds().intersect(tempObject.getBounds())) {

                    x=1000;
                    y=500;
                    handler.setDir(tempObject.getDirection());
                    handler.drawLevel();
                    sound.playSound(sound.soundChangingLevel,0);
                }
            }

            //kolizje z przeciwnikami ko??cz?? si?? utrat?? zdrowia i odtworzeniem animacji otrzymania
            // obra??e??
            else if (tempObject.getId() == ObjectId.Spider || tempObject.getId() == ObjectId.Snail){
                if (getBounds().intersect(tempObject.getBounds())) {
                    hit=true;
                    if (HEALTH >0)
                 HEALTH--;

                }
            }



            //kolizje ze zbieralnymi przedmiotami, pozwala zbiera?? te przedmioty do ekwipunku.
            //je??li wejdziemy w kolizj?? z tym przedmiotem to zostanie on usuni??ty z listy obiekt??w
            //a zostanie dodany do ekwipunku i narysowany na interfejsie
            else if (tempObject.getId() == ObjectId.Collectible){
                if (getBounds().intersect(tempObject.getBounds())) {
                Collectible col = (Collectible)tempObject;
                handler.removeObject(tempObject);
                if (col.getType()>=0)
                    eq.setSlot(col.getType());
                    sound.playSound(sound.soundCollectingItem,0);
                }

            }

            //kolizje z kot??em, je??li zebrali??my wszystkie przedmioty, to mo??emy je do niego w??o??y??
            //co pozwala uko??czy?? gr??
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


    //metoda rysuj??ca klatki animacji na ekranie, w zale??no??ci od r????nych sytuacji: w kt??r?? stron??
    //jest zwr??cony gracz, czy dostaje obra??enia, czy straci??wsszystkie punkty zdrowia
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




    //pobranie granic obiektu u??ywane do wykrywania kolizji:

    //z do??u
    public Rect getBounds() {
        return new Rect((int)x+(GameView.objectSize/2)-((GameView.objectSize/2)/2),(int)y+GameView.objectSize/2, (int)(x+ GameView.objectSize/2),(int)(y+GameView.objectSize));
    }

    //z g??ry
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
