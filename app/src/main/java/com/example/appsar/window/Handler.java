package com.example.appsar.window;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.appsar.R;
import com.example.appsar.framework.GameObject;
import com.example.appsar.framework.ObjectId;
import com.example.appsar.objects.Block;
import com.example.appsar.objects.Cauldron;
import com.example.appsar.objects.Collectible;
import com.example.appsar.objects.Equipment;
import com.example.appsar.objects.Flag;
import com.example.appsar.objects.Player;
import com.example.appsar.objects.Spider;

import java.util.LinkedList;

//klasa pozwalająca operować na wszystkich obiektach na raz, tworzymy liste obiektów i aktualizujemy
//je wszystkie w jednym miejscu
public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private Bitmap level = null, level2 = null, level3 = null, level_1 = null;
    private int x=896,y=768;
    private boolean dir;
    Equipment eq = GameView.getInstanceEq();

    //konstruktor
    public Handler(Context context) {
        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage(context, R.drawable.level);
        level2 = loader.loadImage(context, R.drawable.level2);
        level3= loader.loadImage(context, R.drawable.level3);
        level_1 = loader.loadImage(context, R.drawable.level_1);
        LoadImageLevel(level);
        Player.HEALTH=100;
        Player.LIVES=2;
        GameView.LEVEL=0;
    }

    //aktualizowanie pozycji obiektów
    public void tick()
    {
        for (int i=0;i<object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }

    //rysowanie obiektów na ekranie
    public void render(Context context, Canvas canvas, Paint paint)
    {
        for (int i=0;i<object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.render(context, canvas, paint);
        }
    }

    //klasa rysujaca planszę na podstawie bitmapy
    public void LoadImageLevel(Bitmap image){
        int w = image.getWidth();
        int h = image.getHeight();

        //bitmapa jest skanowana piskel po pikselu w celu sprawdzenia koloru, w zależności od koloru
        //rysowany jest dany typ obiektu
        for (int xx=0;xx<w;xx++){
            for (int yy=0;yy<h;yy++){
                int pixel = image.getPixel(xx,yy);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;


                if (red == 255 && green==255 && blue == 255) addObject(new Block(xx*GameView.objectSize,yy*GameView.objectSize,0,ObjectId.Block));
                if (red == 127 && green==127 && blue == 127) addObject(new Block(xx*GameView.objectSize,yy*GameView.objectSize,1,ObjectId.Block));
                if (red == 255 && green==0 && blue == 0) addObject(new Flag(xx*GameView.objectSize,yy*GameView.objectSize,false,ObjectId.Flag));
                if (red == 0 && green==255 && blue == 0) addObject(new Flag(xx*GameView.objectSize,yy*GameView.objectSize,true,ObjectId.Flag));
                if (red == 255 && green==0 && blue == 255) addObject(new Spider(xx*GameView.objectSize,yy*GameView.objectSize,ObjectId.Spider));
                if (red == 0 && green==127 && blue == 0) addObject(new Cauldron(xx*GameView.objectSize,yy*GameView.objectSize,ObjectId.Cauldron));
                if (red == 0 && green==0 && blue == 255 && !eq.isGum()) addObject(new Collectible(xx*GameView.objectSize,yy*GameView.objectSize,ObjectId.Collectible,0));
                if (red == 127 && green==0 && blue == 0 && !eq.isFlashlight()) addObject(new Collectible(xx*GameView.objectSize,yy*GameView.objectSize,ObjectId.Collectible,1));
                if (red == 127 && green==127 && blue == 0 && !eq.isPaper()) addObject(new Collectible(xx*GameView.objectSize,yy*GameView.objectSize,ObjectId.Collectible,2));




            }
        }
        //dodanie gracza na wierzchniej warstwie, aby nie był przykryty przez inne obiekty
        addObject(new Player(x,y,this,ObjectId.Player, true));
    }


    //metoda pozwalająca na zmianę poziomu - wczytywana jest inna bitmapa według, której rysowana
    //jest nowa plansza
    public void switchLevel(){

        switch (GameView.LEVEL)
        {
            case -1:
                LoadImageLevel(level_1);
                break;
            case 1:
                LoadImageLevel(level2);
                break;
            case 2:
                LoadImageLevel(level3);
                break;
            default:
                LoadImageLevel(level);
                break;
        }
    }

    //kompletna metoda tworząca planszę, w zależności od tego z której strony gracz wszedł na
    //planszę, zostanie przeniesiony na przeciwną stronę kolejnej, co daje poczucie płynnego
    //przechodzenia pomiędzy planszami
    public void drawLevel(){
        clearLevel();
        if (dir)
        {
            GameView.LEVEL++;
            x=146;
        }
        else
        {
            GameView.LEVEL--;
            x=1646;
        }
        switchLevel();

    }

    //metoda czyszcząca cały poziom
    public void clearLevel(){
        object.clear();
    }

    //metoda pozwalająca na dodawanie obiektów do listy
    public void addObject(GameObject object){
        this.object.add(object);
    }

    //metoda pozwalająca na usuwanie obiektów z listy
    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    //metoda pozwalająca na ustawienie, z której strony gracz wchodzi na planszę
    public void setDir(boolean dir) {
        this.dir = dir;
    }
}
