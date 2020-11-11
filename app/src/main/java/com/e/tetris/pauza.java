package com.e.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class pauza extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pauza);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.86), (int)(height*.75));
        TextView wynikPole = findViewById(R.id.wynikPole);
        Button button = findViewById(R.id.buttonRozpocznijPonownie);
        Intent incomingIntent = getIntent();
        ImageButton imageButton1 = findViewById(R.id.ustawienia);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pauza.this, Gameover.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });
        final int wynik = incomingIntent.getIntExtra("wynik",0);
        final int figura = incomingIntent.getIntExtra("figura",0);
        final int obrot = incomingIntent.getIntExtra("obrot",0);
        final int x = incomingIntent.getIntExtra("x",0);
        final int y = incomingIntent.getIntExtra("y", 4);
        wynikPole.setText(""+wynik);
        final ArrayList<Integer> tla = incomingIntent.getIntegerArrayListExtra("tla");
        ImageButton imageButton = findViewById(R.id.zamkniecie);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pauza.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("tla", tla);
                intent.putExtra("wynik",wynik);
                intent.putExtra("figura", figura);
                intent.putExtra("obrot", obrot);
                intent.putExtra("x",x);
                intent.putExtra("y", y);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pauza.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("tla", tla);
                intent.putExtra("wynik",wynik);
                intent.putExtra("figura", figura);
                intent.putExtra("obrot", obrot);
                intent.putExtra("y", y);
                intent.putExtra("x", x);
                startActivity(intent);
            }
        });
    }
}