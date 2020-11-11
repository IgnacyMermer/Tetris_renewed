package com.e.tetris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Gameover extends AppCompatActivity{
    public static final String SHARED_PREFS = "sharedPrefs";
    int i, wielk2=10, wielkosc=10;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        final Button button = findViewById(R.id.buttonRozpocznijPonownie);
        Intent incomingIntent = getIntent();
        int wynik2 = incomingIntent.getIntExtra("wynik",0);
        TextView wynikPole = findViewById(R.id.wynikPole);
        wynikPole.setText("Twój wynik wyniósł: "+wynik2+"pkt.");
        TextView wynikRekord = findViewById(R.id.wynikRekord);
        final int x = incomingIntent.getIntExtra("x",4);
        final int y = incomingIntent.getIntExtra("y", 0 );
        final int figura = incomingIntent.getIntExtra("figura", 0);
        final int obrot = incomingIntent.getIntExtra("obrot", 0);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        i = sharedPreferences.getInt("wynik",0);
        wielk2 = sharedPreferences.getInt("wielkosc",10);
        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.plansze, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        if(wielk2==10){
            spinner.setSelection(0);
        }
        else if(wielk2==12){
            spinner.setSelection(1);
        }
        else if(wielk2==14){
            spinner.setSelection(2);
        }
        else if(wielk2==16){
            spinner.setSelection(3);
        }
        if(wynik2>i){
            i=wynik2;
        }
        wynikRekord.setText("Twój rekord wynosi: "+i+"pkt.");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        wielkosc=10;
                        break;
                    case 1:
                        wielkosc=12;
                        break;
                    case 2:
                        wielkosc=14;
                        break;
                    case 3:
                        wielkosc=16;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gameover.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("wynik",i);
                editor.putInt("wielkosc",wielkosc);
                editor.apply();
                intent.putExtra("x", x);
                intent.putExtra("y", y);
                intent.putExtra("figura", figura);
                intent.putExtra("obrot", obrot);
                startActivity(intent);
            }
        });
    }
}