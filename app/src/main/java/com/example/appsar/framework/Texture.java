package com.example.appsar.framework;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.appsar.R;
import com.example.appsar.window.BufferedImageLoader;

//klasa przechowująca wszystkie tekstury obiektów, które rysujemy na ekranie za pomoca handlera
public class Texture {

    SpriteSheet bs, ps, ss, cs, cas;
    private Bitmap block_sheet = null;
    private Bitmap player_sheet = null;
    private Bitmap spider_sheet = null;
    private Bitmap collectibles_sheet = null;
    private Bitmap cauldron_sheet = null;

    public Bitmap[] block = new Bitmap[2];
    public Bitmap[] player = new Bitmap[8];
    public Bitmap[] spider = new Bitmap[3];
    public Bitmap[] playerDeath = new Bitmap[2];
    public Bitmap[] playerHit = new Bitmap[2];
    public Bitmap[] collectible = new Bitmap[3];
    public Bitmap[] cauldron = new Bitmap[2];


    //konstruktor
    public Texture(Context context){

        //wczytujemy akrusze obrazów (sprite sheety) z plików i skalujemy je w górę
        BufferedImageLoader loader = new BufferedImageLoader();
        block_sheet = loader.loadImage(context, R.drawable.block_sheet, 128*2,128);
        player_sheet = loader.loadImage(context, R.drawable.player_sheet, 128*8,128*2);
        spider_sheet = loader.loadImage(context, R.drawable.spider_sheet, 128*3,128);
        collectibles_sheet = loader.loadImage(context, R.drawable.collectibles_sheet, 128*3,128);
        cauldron_sheet = loader.loadImage(context, R.drawable.cauldron_sheet, 128*2,128);


        //tworzymy z nich obiekty klasy Sprite sheet
        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);
        ss = new SpriteSheet(spider_sheet);
        cs = new SpriteSheet(collectibles_sheet);
        cas = new SpriteSheet(cauldron_sheet);

        getTextures();
    }


    //tworzymy tablice obrazów
    private void getTextures(){
        block[0]=bs.grabImage(1,1,128,128);
        block[1]=bs.grabImage(2,1,128,128);

        player[0] = ps.grabImage(1,1,128,128);
        player[1] = ps.grabImage(2,1,128,128);
        player[2] = ps.grabImage(3,1,128,128);
        player[3] = ps.grabImage(4,1,128,128);

        player[4] = ps.grabImage(5,1,128,128);
        player[5] = ps.grabImage(6,1,128,128);
        player[6] = ps.grabImage(7,1,128,128);
        player[7] = ps.grabImage(8,1,128,128);

        spider[0] = ss.grabImage(1,1,128,128);
        spider[1] = ss.grabImage(2,1,128,128);
        spider[2] = ss.grabImage(3,1,128,128);

        playerDeath[0] = ps.grabImage(1,2,128,128);
        playerDeath[1] = ps.grabImage(2,2,128,128);

        playerHit[0] = ps.grabImage(3,2,128,128);
        playerHit[1] = ps.grabImage(4,2,128,128);

        collectible[0] = cs.grabImage(1,1,128,128);
        collectible[1] = cs.grabImage(2,1,128,128);
        collectible[2] = cs.grabImage(3,1,128,128);

        cauldron[0] = cas.grabImage(1,1,128,128);
        cauldron[1] = cas.grabImage(2,1,128,128);



    }



}
