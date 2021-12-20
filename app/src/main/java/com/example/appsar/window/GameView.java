package com.example.appsar.window;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.appsar.R;
import com.example.appsar.framework.GameObject;
import com.example.appsar.framework.ObjectId;
import com.example.appsar.framework.Sound;
import com.example.appsar.framework.Texture;
import com.example.appsar.objects.Equipment;
import com.example.appsar.objects.Player;

//klasa widoku gry, zawiera główną pętlę gry w której są aktualizowane i rysowane wszystkie obiekty
//gry
public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private GameActivity gameActivity;
    private boolean inGame;
    public static int screenWidth, screenHeight;
    public static int objectSize;
    private Paint paint;
    private Bitmap background =null;
    //private static SoundPool soundPool;
    //private static int sound;
    public static int LEVEL=0;
    public static boolean gameWon;
    private Handler handler;
    static Sound sound;
    static Texture tex;
    static Equipment equipment;
    private boolean soundPlaying=false;

    //metoda inicjalizująca
    private void init(){
        equipment = new Equipment();
        tex = new Texture(getContext());
        sound = new Sound(getContext());
        handler = new Handler(getContext());

        gameWon=false;
        BufferedImageLoader loader = new BufferedImageLoader();
        background = loader.loadImage(getContext(), R.drawable.backgroundcastle);
    }

    //konstruktor
    public GameView(GameActivity gameActivity, Context context, int screenX, int screenY) {
        super(context);
        this.gameActivity = gameActivity;
        this.getHolder().setFixedSize(1920,1080);

    /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);


     */
        //sound = soundPool.load(this.getContext(), R.raw.cloth4, 1);



        screenWidth = screenX;
        screenHeight = screenY;
        objectSize = 128;
        paint = new Paint();
    }

    //metoda zwracająca instancję klasy Texture
    public static Texture getInstance(){
        return tex;
    }

    //metoda zwracajca instancję klasy Equipment
    public static Equipment getInstanceEq(){
        return equipment;
    }

    /*
    //metoda zwracająca instancję klasy SounPool
    public static SoundPool getInstanceSp(){
        return soundPool;
    }

     */
    public static Sound getInstanceS(){
        return sound;
    }

    //główna pętla gry, w tej metodzie zachodzą wszystkie aktualizacje
    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(inGame){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                updates++;
                delta--;
            }
            draw();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    //metoda aktualizująca pozycje obietków
    private void update() {
        handler.tick();
        if (!soundPlaying) {
        if (Player.HEALTH <= 0 ) {
                soundPlaying=true;
                sound.playSound(sound.soundDying, 0);
            }
            if (gameWon){
                soundPlaying=true;
                sound.playSound(sound.soundWinning, 0);
            }
        }
    }

    //metoda rysująca obiekty na ekranie
    private void draw() {

        if (getHolder().getSurface().isValid()){

            //wypisanie odpowiedniej wiadomości w zależności od stanu gry
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background,0,0,paint);
            handler.render(getContext(), canvas, paint);

            //przegrana
            if (Player.HEALTH <= 0 && Player.LIVES <= 0){
                paint.setColor(Color.WHITE);
                paint.setTextSize(200);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Koniec Gry", (1920/2),(1080/2),paint);
            }

            //utracenie 1 szansy
            if (Player.HEALTH <= 0 && Player.LIVES > 0){
                paint.setColor(Color.WHITE);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Dotknij, aby spróbować jeszcze raz", (1920/2),(1080/2),paint);
            }

            //wygrana gry
            if (gameWon){
                paint.setColor(Color.WHITE);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Gratulacje! Ukończyłeś grę!", (1920/2),(1080/2),paint);
            }

            //rysowanie pasku zycia
            paint.setColor(Color.BLACK);
            canvas.drawRect(638,48,1642,80,paint);
            paint.setColor(Color.RED);
            canvas.drawRect(640,50,640+Player.HEALTH *10,78,paint);

            //rysowanie ilości pozostałych szans
            canvas.drawBitmap(tex.player[0],384,0,paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(80);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("x"+Player.LIVES, 520,84,paint);

            //rysowanie ekwipunku
            if (equipment.getSlot(0)!=-1)
                canvas.drawBitmap(tex.collectible[equipment.getSlot(0)],0,0,paint );
            if (equipment.getSlot(1)!=-1)
                canvas.drawBitmap(tex.collectible[equipment.getSlot(1)],128,0,paint );
            if (equipment.getSlot(2)!=-1)
                canvas.drawBitmap(tex.collectible[equipment.getSlot(2)],256,0,paint );

            getHolder().unlockCanvasAndPost(canvas);
        }


    }

    //metoda wywołana gdy aktywność odzyska focus
    public void resume(){
        //zabezpieczenie przed tworzeniem wielu wątków
        if (inGame)
            return;

        //przechodzimy do gry, wartość zmiennej inGame zmieniamy na true, tworzymy nowy wątek
        inGame = true;
        thread = new Thread(this);
        thread.start();
    }

    //metoda wywoływana gdy aktywność straci focus
    public void pause(){

        try {
            inGame=false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //metoda obsługująca sterowanie, dla uproszczenia testowania na symulatorze pierwsza ćwiartka
    //ekranu od lewej odpowiada za ruch w lewo, kolejna za ruch w prawo, prawa połowa ekranu za skok
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);


            if (tempObject.getId() == ObjectId.Player) {

                if (Player.HEALTH >0 && !gameWon){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //ruch w lewo
                        if (event.getX() < GameView.screenWidth/4) {
                            tempObject.setVelX(-10);
                            tempObject.setDirection(false);

                            //ruch w prawo
                        }  else if (event.getX() < GameView.screenWidth/2) {
                            tempObject.setVelX(10);
                            tempObject.setDirection(true);

                        }

                        //początek skoku
                         else if (event.getX() > GameView.screenWidth/2 && !tempObject.isJumping()){
                            tempObject.setVelY(-14);
                            sound.playSound(sound.soundJumping,0);
                        }

                        break;
                    case MotionEvent.ACTION_UP:

                        //zatrzymanie postaci po puszczeniu ekranu
                        tempObject.setVelX(0);

                        //jeśli puścimy ekran wcześniej to postać skoczy niżej
                        if (!tempObject.isJumping()){
                            tempObject.setJumping(true);
                            tempObject.setVelY(tempObject.getVelY()/2);
                        }
                        break;
                    }
                }
                //jeśli gra się skończy to po kliknięciu przejdziemy do menu
                else{
                    if (tempObject.getId() == ObjectId.Player){
                        tempObject.setVelX(0);
                        tempObject.setVelY(0);
                    }
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        if (Player.LIVES>=0 && !gameWon){
                            Player.HEALTH=100;
                            handler.clearLevel();
                            handler.switchLevel();
                            Player.LIVES--;
                            soundPlaying=false;
                        }
                        if (Player.LIVES <0 || gameWon){
                            soundPlaying=false;
                            thread.interrupt();
                            gameActivity.finish();
                        }


                    }

                }
            }
        }
        return true;
    }
}
