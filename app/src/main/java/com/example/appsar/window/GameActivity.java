package com.example.appsar.window;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

//klasa aktywności gry, przechodzimy do niej po kliknięciu przycisku w menu w klasie MainActivity
//opisujemy w niej parametry okna i pobieramy rozmiar ekranu
public class GameActivity extends Activity {

    private com.example.appsar.window.GameView gameView;
    private Point point = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //pobieramy rozmiar pasku nawigacji w celu uzyskania jak najdokładniejszego rozmiaru ekranu
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");

        //ustawiamy parametry ekranu przy tworzeniu, aby gra była na pełnym ekranie a wszystkie
        //paski pozostały ukryte
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        //obliczamy dokładny rozmiar ekranu
        getWindowManager().getDefaultDisplay().getSize(point);
        point.x = point.x + resources.getDimensionPixelSize(resourceId);
        gameView = new com.example.appsar.window.GameView(this,this, point.x, point.y);

        //ustawiamy content view na widok gry (klasa GameView)
        setContentView(gameView);
    }


    //metoda wstrzymująca działanie gry gdy aktywność straci focus
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //metoda kontynuująca działanie gry gdy aktywność odzyska focus
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }


    //ustawiamy parametry ekranu przy zmianie focusu, aby gra była na pełnym ekranie a wszystkie
    //paski pozostały ukryte
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}