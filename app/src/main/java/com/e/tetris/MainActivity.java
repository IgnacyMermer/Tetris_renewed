package com.e.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    ImageButton dol, prawo, lewo, rotate, pauza;
    TextView wynikPole;
    int temp,widthGlowne, heightGlowne, width, height, czas=500,x=7,y=-1, figura=1,obrot=0, y4;
    int wielkoscWys=32;
    int wielkoscSzer=16;
    int wielkoscPlan=10;
    boolean gameover=false, lala=false, lala2=false, lala3=true;
    ArrayList<Integer> wysokosci;
    ArrayList<TextView> lista;
    int y3=31, y5=31;
    int wynik=0;
    long t;
    int wysokoscObnizenia = 31;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = new ArrayList<>();
        wynikPole = findViewById(R.id.wynik);
        dol = findViewById(R.id.strzalka);
        prawo = findViewById(R.id.strzalka_prawo);
        lewo = findViewById(R.id.strzalka_lewo);
        rotate = findViewById(R.id.strzalka_w_dol);
        pauza = findViewById(R.id.ustawienia);
        Intent incomingIntent = getIntent();
        final ArrayList<Integer> listaPlansza = incomingIntent.getIntegerArrayListExtra("tla");
        x=incomingIntent.getIntExtra("x",4);
        y= incomingIntent.getIntExtra("y",0);
        wynik = incomingIntent.getIntExtra("wynik",0);
        wynikPole.setText(""+wynik);
        figura=incomingIntent.getIntExtra("figura",0);
        obrot=incomingIntent.getIntExtra("obrot",0);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        wielkoscPlan = sharedPreferences.getInt("wielkosc", 10);
        y5=wielkoscPlan;
        final android.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                RelativeLayout relativeLayout1 = findViewById(R.id.layoutCaly);
                int szer = relativeLayout1.getWidth();
                RelativeLayout relativeLayout = findViewById(R.id.layout_pod_gridem);
                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                widthGlowne = relativeLayout.getWidth();
                int szer1 = wynikPole.getHeight();
                heightGlowne=relativeLayout.getHeight();
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                for(int i=0;i<(wielkoscPlan*wielkoscPlan*2);i++){
                    TextView textView = new TextView(MainActivity.this);
                    lista.add(textView);
                    gridLayout.addView(textView);
                }
                if((heightGlowne/2)>widthGlowne){
                    heightGlowne=widthGlowne*2;
                }
                else{
                    widthGlowne=heightGlowne/2;
                }
                szer-=widthGlowne+3;
                szer=szer/2;
                layoutParams1.width = widthGlowne;
                layoutParams1.height = heightGlowne;
                layoutParams1.setMargins(szer,szer1,szer,0);
                relativeLayout.setLayoutParams(layoutParams1);
                relativeLayout.setBackgroundResource(R.drawable.ramka);
                wielkoscSzer=wielkoscPlan;
                wielkoscWys=wielkoscPlan*2;
                width = (widthGlowne-50)/wielkoscSzer;
                height = (heightGlowne-40)/wielkoscWys;
                for(int i=0;i<(wielkoscPlan*wielkoscPlan*2);i++){
                    TextView textView = lista.get(i);
                    android.widget.GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.width = width;
                    layoutParams.height = height;
                    int u = i/wielkoscSzer;
                    if(u>2){
                        u++;
                    }
                    layoutParams.rowSpec = GridLayout.spec(u);
                    layoutParams.columnSpec = GridLayout.spec(i%wielkoscSzer);
                    textView.setLayoutParams(layoutParams);
                }
                for(int i=0;i<wielkoscSzer;i++){
                    TextView textView2 = new TextView(MainActivity.this);
                    android.widget.GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.width = width;
                    layoutParams.height = height/4;
                    layoutParams.rowSpec = GridLayout.spec(3);
                    layoutParams.columnSpec = GridLayout.spec(i);
                    textView2.setLayoutParams(layoutParams);
                    textView2.setBackgroundColor(Color.parseColor("#000000"));
                    gridLayout.addView(textView2);
                }
                if(listaPlansza!=null){
                    for(int i=0;i<(wielkoscPlan*wielkoscPlan*2);i++){
                        switch (listaPlansza.get(i)){
                            case 1:
                                lista.get(i).setBackgroundResource(R.drawable.zolty);
                                break;
                            case 2:
                                lista.get(i).setBackgroundResource(R.drawable.rozowy);
                                break;
                            case 3:
                                lista.get(i).setBackgroundResource(R.drawable.pomaranczowy);
                                break;
                            case 4:
                                lista.get(i).setBackgroundResource(R.drawable.turkusowy);
                                break;
                        }
                    }
                }
            }
        });
        wysokoscObnizenia=(wielkoscWys-1);
        final ArrayList<TextView> lista2= lista;
        wysokosci = new ArrayList<>();
        for(int i=0;i<wielkoscPlan;i++){
            wysokosci.add(31);
        }
        Runnable runnable1 = new Runnable(){
            @Override
            public void run(){
                synchronized (this){
                    try{
                        while(!gameover){
                            if(lala){
                                y3=y;
                                if(figura==0){
                                    y3-=2;
                                }
                                else if(figura==1&&obrot==0){
                                   y3-=4;
                                }
                                else if(figura==1&&obrot==1){
                                    y3-=1;
                                }
                                else if(figura==2&&obrot==3){
                                    y3-=2;
                                }
                                else if(figura==3&&obrot==1){
                                    y3-=2;
                                }
                                else if(figura==3&&obrot==3){
                                    y3-=2;
                                }
                                else if(figura==2&&obrot==2){
                                    y3-=3;
                                }
                                else if(figura==2&&obrot==1){
                                    y3-=2;
                                }
                                else if(figura==3&&obrot==2){
                                    y3-=3;
                                }
                                else if(figura==2||figura==3){
                                    y3-=3;
                                }
                                if(y3<y5){
                                    y5=y3;
                                }
                                y=0;
                                x=4;
                                obrot=0;
                                lala=false;
                                figura = (int)(Math.random()*4);
                            }
                            t = System.currentTimeMillis()+czas;
                            while(t>System.currentTimeMillis()){}
                            y++;
                            lala3=true;
                            sprawdzanie();
                            if(y<0){
                                throw new Exception("lala");
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run(){
                                    try {
                                        y4=y;
                                        if(y<0){
                                            y=0;
                                            throw new Exception("lala");
                                        }
                                        if(figura==0){
                                            while(!lala2){
                                                for(int i=(wielkoscWys-1);i>3;i--){
                                                    for(int j=(wielkoscSzer-1);j>=0;j--){
                                                        if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                            }
                                                        }
                                                    }
                                                }
                                                y4++;
                                                if(y4<(wielkoscWys-1)){
                                                    if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                        lala2=true;
                                                    }
                                                    else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                        lala2=true;
                                                    }
                                                }
                                                else{
                                                    lala2=true;
                                                }
                                            }
                                            lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                            lista.get((y4*wielkoscSzer+x-wielkoscSzer+1)).setBackgroundResource(R.drawable.bloczek);
                                            lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                            lista.get((y4*wielkoscSzer+x+1)).setBackgroundResource(R.drawable.bloczek);
                                        }
                                        else if(figura==1){
                                            if(obrot==0){
                                                y4--;
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-(wielkoscSzer*3))).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==1){
                                                y4--;
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+3).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+3).setBackgroundResource(R.drawable.bloczek);
                                            }
                                        }
                                        else if(figura==2){
                                            if(obrot==0){
                                                y4--;
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer-1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==1){
                                                y4--;
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==2){
                                                y4--;
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x-wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-(wielkoscSzer*2)+1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==3){
                                                y4--;
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+(wielkoscSzer*2)+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(R.drawable.bloczek);
                                            }
                                        }
                                        else if(figura==3){
                                            if(obrot==0){
                                                y4--;
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x-1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==1){
                                                y4--;
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==2) {
                                                y4--;
                                                while (!lala2) {
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if (y4 < (wielkoscWys-1)) {
                                                        if (lista.get(y4 * wielkoscSzer + x + wielkoscSzer).getBackground() != null) {
                                                            lala2 = true;
                                                        } else if (lista.get(y4 * wielkoscSzer + x + 1).getBackground() != null) {
                                                            lala2 = true;
                                                        }
                                                    } else {
                                                        lala2 = true;
                                                    }
                                                }
                                                lista.get((y4 * wielkoscSzer + x - wielkoscWys)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4 * wielkoscSzer + x - wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4 * wielkoscSzer + x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4 * wielkoscSzer + x - wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==3){
                                                y4--;
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscWys+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                        }
                                    }
                                    catch (Exception ex2){
                                        Log.i("wynik", ex2.getMessage().toString());
                                    }
                                    lala2=false;
                                    if(y<0){
                                        y=0;
                                        gameover=true;
                                        Intent i = new Intent(MainActivity.this, Gameover.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        i.putExtra("wynik", wynik);
                                        startActivity(i);
                                    }
                                    if(y5<1){
                                        gameover=true;
                                        Intent i = new Intent(MainActivity.this, Gameover.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        i.putExtra("wynik", wynik);
                                        startActivity(i);
                                    }
                                    for(int i=0;i<y5-3;i++){
                                        for(int j=0;j<wielkoscSzer;j++){
                                            lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                        }
                                    }
                                    for(int i=0;i<(wielkoscSzer*3);i++){
                                        if(lista.get(i).getBackground()!=null){
                                            if(lista.get(i).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                gameover=true;
                                                Intent intent = new Intent(MainActivity.this, Gameover.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                intent.putExtra("wynik", wynik);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                    if(figura==0){
                                        if((y>0&&x>0)||(y>1)){
                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                            lista.get((y*wielkoscSzer+x-wielkoscSzer+1)).setBackgroundResource(0);
                                        }
                                        lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.zolty);
                                        lista.get((y*wielkoscSzer+x+1)).setBackgroundResource(R.drawable.zolty);
                                        lista.get((y*wielkoscSzer+x+wielkoscSzer)).setBackgroundResource(R.drawable.zolty);
                                        lista.get((y*wielkoscSzer+x+wielkoscSzer+1)).setBackgroundResource(R.drawable.zolty);
                                    }
                                    else if(figura==1){
                                        if(obrot==0){
                                            if(y>3){
                                                lista.get(y*wielkoscSzer+x-(wielkoscSzer*4)).setBackgroundResource(0);
                                            }
                                            if(y>2){
                                                lista.get((y*wielkoscSzer+x-(wielkoscSzer*3))).setBackgroundResource(R.drawable.rozowy);
                                            }
                                            if(y>1){
                                                lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.rozowy);
                                            }
                                            if(y>0){
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.rozowy);
                                            }
                                            lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.rozowy);
                                        }
                                        else if(obrot==1){
                                            if(y>0){
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+2).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+3).setBackgroundResource(0);
                                            }
                                            lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.rozowy);
                                            lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.rozowy);
                                            lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.rozowy);
                                            lista.get(y*wielkoscSzer+x+3).setBackgroundResource(R.drawable.rozowy);
                                        }
                                    }
                                    else if(figura==2){
                                        if(obrot==0){
                                            if(y>2){
                                                lista.get(y*wielkoscSzer+x-(wielkoscSzer*3)).setBackgroundResource(0);
                                            }
                                            if(y>1){
                                                lista.get((y*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.pomaranczowy);
                                            }
                                            if(y>0){
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(0);
                                            }
                                            lista.get(y*wielkoscSzer+x-1).setBackgroundResource(R.drawable.pomaranczowy);
                                            lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.pomaranczowy);
                                        }
                                        else if(obrot==1){
                                            if(y>1){
                                                lista.get(y*wielkoscSzer+x-wielkoscWys).setBackgroundResource(0);
                                            }
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+2).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                            lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.pomaranczowy);
                                            lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.pomaranczowy);
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(R.drawable.pomaranczowy);
                                        }
                                        else if(obrot==2){
                                            if(y>2){
                                                lista.get(y*wielkoscSzer+x-(wielkoscSzer*3)).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-(wielkoscSzer*3)+1).setBackgroundResource(0);
                                            }
                                            if(y>1){
                                                lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(R.drawable.pomaranczowy);
                                            }
                                            if(y>0){
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.pomaranczowy);
                                            }
                                            lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.pomaranczowy);
                                        }
                                        else if(obrot==3){
                                            if(y>1){
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+2).setBackgroundResource(0);
                                            }
                                            lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                            lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.pomaranczowy);
                                            lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.pomaranczowy);
                                            lista.get(y*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(R.drawable.pomaranczowy);
                                        }
                                    }
                                    else if(figura==3){
                                        if(obrot==0){
                                            if(y>2){
                                                lista.get(y*wielkoscSzer+x-(wielkoscSzer*3)).setBackgroundResource(0);
                                            }
                                            if(y>1){
                                                lista.get((y*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x-(wielkoscSzer*2)-1).setBackgroundResource(0);
                                            }
                                            if(y>0){
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(R.drawable.turkusowy);
                                            }
                                            lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.turkusowy);
                                        }
                                        else if(obrot==1){
                                            if(y>1){
                                                lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(0);
                                            }
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+2).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                            lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.turkusowy);
                                            lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.turkusowy);
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                        }
                                        else if(obrot==2){
                                            if(y>2){
                                                lista.get(y*wielkoscSzer+x-(wielkoscSzer*3)).setBackgroundResource(0);
                                            }
                                            if(y>1){
                                                lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(0);
                                            }
                                            if(y>0){
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.turkusowy);
                                            }
                                            lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.turkusowy);
                                        }
                                        else if(obrot==3){
                                            if(y>1){
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+2).setBackgroundResource(0);
                                            }
                                            lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                            lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.turkusowy);
                                            lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.turkusowy);
                                            lista.get(y*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                        }
                                    }
                                    int u11=0;
                                    wysokoscObnizenia=wielkoscWys-1;
                                    for(int i11=wysokoscObnizenia;i11>=1;i11--){
                                        u11=0;
                                        Log.i("wynik", ""+wielkoscSzer+" "+wysokoscObnizenia);
                                        for(int j11=0;j11<wielkoscSzer;j11++){
                                            if(lista2.get(i11*wielkoscSzer+j11).getBackground()!=null){
                                                if(!lista2.get(i11*wielkoscSzer+j11).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                    u11++;
                                                }
                                            }
                                        }
                                        if(u11==wielkoscSzer){
                                            wysokoscObnizenia=i11-1;
                                            if(wysokoscObnizenia>(wielkoscWys-1)){
                                                wysokoscObnizenia=(wielkoscWys-1);
                                            }
                                            if(wysokoscObnizenia<0){
                                                wysokoscObnizenia=0;
                                            }
                                            break;
                                        }
                                    }
                                    if(u11==wielkoscSzer){
                                        wynik+=10;
                                        wynikPole.setText(""+wynik);
                                        for(int i=wysokoscObnizenia;i>=3;i--){
                                            for(int j=wielkoscSzer-1;j>=0;j--){
                                                Drawable dr = lista.get(i*wielkoscSzer+j).getBackground();
                                                lista.get(i*wielkoscSzer+j+wielkoscSzer).setBackground(dr);
                                                lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                            }
                                        }
                                        wysokoscObnizenia+=1;
                                    }
                                }
                            });
                            pauza.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    gameover=true;
                                    Intent i = new Intent(MainActivity.this, com.e.tetris.pauza.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    i.putExtra("x",x);
                                    i.putExtra("y", y);
                                    i.putExtra("figura", figura);
                                    i.putExtra("obrot", obrot);
                                    i.putExtra("wynik", wynik);
                                    final ArrayList<Integer> tla = new ArrayList<>();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                       for(int j=0;j<wielkoscWys;j++){
                                            for(int j2=0;j2<wielkoscSzer;j2++){
                                                if(lista.get(j*wielkoscSzer+j2).getBackground()!=null){
                                                    if(lista.get(j*wielkoscSzer+j2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.zolty).getConstantState())){
                                                        tla.add(1);
                                                    }
                                                    else if(lista.get(j*wielkoscSzer+j2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.rozowy).getConstantState())){
                                                        tla.add(2);
                                                    }
                                                    else if(lista.get(j*wielkoscSzer+j2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.pomaranczowy).getConstantState())){
                                                        tla.add(3);
                                                    }
                                                    else if(lista.get(j*wielkoscSzer+j2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.turkusowy).getConstantState())){
                                                        tla.add(4);
                                                    }
                                                    else{
                                                        tla.add(0);
                                                    }
                                                }
                                                else{
                                                    tla.add(0);
                                                }
                                            }
                                        }
                                        }
                                    });
                                    i.putExtra("tla", tla);
                                    startActivity(i);
                                }
                            });
                            rotate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(y<0){
                                        y=0;
                                    }
                                    int y2=y+1;
                                    int x2=x;
                                    int blokada=0;
                                    if(figura==0){}
                                    else if(figura==1){
                                        if(y<wielkoscWys){
                                            if(obrot==0){
                                                if(x2>wielkoscSzer-4){
                                                    x2=wielkoscSzer-4;
                                                }
                                                if(lista.get(y2*wielkoscSzer+x2+1).getBackground()!=null){
                                                    blokada=1;
                                                }
                                                else if(lista.get(y2*wielkoscSzer+x2+2).getBackground()!=null){
                                                    blokada=1;
                                                }
                                                else if(lista.get(y2*wielkoscSzer+x2+3).getBackground()!=null){
                                                    blokada=1;
                                                }
                                                else if(lista.get(y2*wielkoscSzer+x2-1).getBackground()!=null){
                                                    blokada=1;
                                                }
                                                if(blokada==0){
                                                    obrot=1;
                                                    if(y>2){
                                                        lista.get((y*wielkoscSzer+x-(wielkoscSzer*3))).setBackgroundResource(0);
                                                    }
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                    }
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                    if(x>(wielkoscSzer-4)){
                                                        x=wielkoscSzer-4;
                                                    }
                                                }
                                            }
                                            else{
                                                if(lista.get(y2*wielkoscSzer+x).getBackground()!=null){
                                                    blokada=1;
                                                }
                                                else if(lista.get((y-1)*wielkoscSzer+x).getBackground()!=null){
                                                    blokada=1;
                                                }
                                                if(blokada==0){
                                                    obrot=0;
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                    lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                    lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                    lista.get(y*wielkoscSzer+x+3).setBackgroundResource(0);
                                                }
                                            }
                                        }
                                    }
                                    else if(figura==2){
                                        if(obrot==0){
                                            if(x2>(wielkoscSzer-3)){
                                                x2=wielkoscSzer-3;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x2+1).getBackground()!=null){
                                                blokada=1;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x2+2).getBackground()!=null){
                                                blokada=1;
                                            }
                                            if(blokada==0){
                                                obrot=1;
                                                if(y>1){
                                                    lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                }
                                                lista.get(y*wielkoscSzer+x-1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                if(x>(wielkoscSzer-3)){
                                                    x=wielkoscSzer-3;
                                                }
                                            }
                                        }
                                        else if(obrot==1){
                                            if(y>1){
                                                if(lista.get(y*wielkoscSzer+x-wielkoscWys).getBackground()!=null){
                                                    blokada=1;
                                                }
                                                else if(lista.get(y*wielkoscSzer+x-wielkoscWys+1).getBackground()!=null){
                                                    blokada=1;
                                                }
                                            }
                                            if(blokada==0){
                                                obrot=2;
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                            }
                                        }
                                        else if(obrot==2){
                                            if(x2>(wielkoscSzer-3)){
                                                x2=wielkoscSzer-3;
                                            }
                                            if(lista.get(y2*wielkoscSzer+x2+1).getBackground()!=null){
                                                blokada=1;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x2+2).getBackground()!=null){
                                                blokada=1;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x2+wielkoscSzer+2).getBackground()!=null){
                                                blokada=1;
                                            }
                                            if(blokada==0){
                                                obrot=3;
                                                if(y>1){
                                                    lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(0);
                                                    lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                }
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                if(x>(wielkoscSzer-3)){
                                                    x=wielkoscSzer-3;
                                                }
                                            }
                                        }
                                        else if(obrot==3){
                                            if(lista.get(y2*wielkoscSzer+x-1).getBackground()!=null){
                                                blokada=1;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x).getBackground()!=null){
                                                blokada=1;
                                            }
                                            if(blokada==0){
                                                obrot=0;
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+2).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(0);
                                            }
                                        }
                                    }
                                    else if(figura==3){
                                        if(obrot==0){
                                            if(x2>(wielkoscSzer-3)){
                                                x2=wielkoscSzer-3;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x2+1).getBackground()!=null){
                                                blokada=1;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x2+2).getBackground()!=null){
                                                blokada=1;
                                            }
                                            if(blokada==0){
                                                obrot=1;
                                                if(y>1){
                                                    lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(0);
                                                }
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                if(x>(wielkoscSzer-3)){
                                                    x=wielkoscSzer-3;
                                                }
                                            }
                                        }
                                        else if(obrot==1){
                                            if(y>1){
                                                if(lista.get(y*wielkoscSzer+x-wielkoscWys).getBackground()!=null){
                                                    blokada=1;
                                                }
                                            }
                                            if(blokada==0){
                                                obrot=2;
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                            }
                                        }
                                        else if(obrot==2){
                                            if(x2>(wielkoscSzer-3)){
                                                x2=wielkoscSzer-3;
                                            }
                                            if(lista.get(y2*wielkoscSzer+x2+1).getBackground()!=null){
                                                blokada=1;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x2+2).getBackground()!=null){
                                                blokada=1;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x2+wielkoscSzer+1).getBackground()!=null){
                                                blokada=1;
                                            }
                                            if(blokada==0){
                                                obrot=3;
                                                if(y>1){
                                                    lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                    lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                }
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                if(x>(wielkoscSzer-3)){
                                                    x=wielkoscSzer-3;
                                                }
                                            }
                                        }
                                        else if(obrot==3){
                                            if(lista.get(y2*wielkoscSzer+x-wielkoscSzer-1).getBackground()!=null){
                                                blokada=1;
                                            }
                                            else if(lista.get(y2*wielkoscSzer+x).getBackground()!=null){
                                                blokada=1;
                                            }
                                            obrot=0;
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+2).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                            lista.get(y*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(0);
                                        }
                                    }
                                    lala2=false;
                                    try {
                                        y4=y;
                                        if(y<0){
                                            y=0;
                                            throw new Exception("lala");
                                        }
                                        if(figura==0){
                                            while(!lala2){
                                                for(int i=(wielkoscWys-1);i>3;i--){
                                                    for(int j=(wielkoscSzer-1);j>=0;j--){
                                                        if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                            }
                                                        }
                                                    }
                                                }
                                                y4++;
                                                if(y4<(wielkoscWys-1)){
                                                    if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                        lala2=true;
                                                    }
                                                    else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                        lala2=true;
                                                    }
                                                }
                                                else{
                                                    lala2=true;
                                                }
                                            }
                                            lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                            lista.get((y4*wielkoscSzer+x-wielkoscSzer+1)).setBackgroundResource(R.drawable.bloczek);
                                            lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                            lista.get((y4*wielkoscSzer+x+1)).setBackgroundResource(R.drawable.bloczek);
                                        }
                                        else if(figura==1){
                                            if(obrot==0){
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-(3*wielkoscSzer))).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==1){
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+3).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+3).setBackgroundResource(R.drawable.bloczek);
                                            }
                                        }
                                        else if(figura==2){
                                            if(obrot==0){
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer-1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==1){
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==2){
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-(wielkoscSzer*2)+1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==3){
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscWys+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(R.drawable.bloczek);
                                            }
                                        }
                                        else if(figura==3){
                                            if(obrot==0){
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x-1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==1){
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==2) {
                                                while (!lala2) {
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if (y4 < (wielkoscWys-1)) {
                                                        if (lista.get(y4 * wielkoscSzer + x + wielkoscSzer).getBackground() != null) {
                                                            lala2 = true;
                                                        } else if (lista.get(y4 * wielkoscSzer + x + 1).getBackground() != null) {
                                                            lala2 = true;
                                                        }
                                                    } else {
                                                        lala2 = true;
                                                    }
                                                }
                                                lista.get((y4 * wielkoscSzer + x - wielkoscWys)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4 * wielkoscSzer + x - wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4 * wielkoscSzer + x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4 * wielkoscSzer + x - wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(obrot==3){
                                                while (!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscWys+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                            }
                                        }
                                    }
                                    catch (Exception ex2){
                                        Log.i("wynik", ex2.getMessage().toString());
                                    }
                                }
                            });
                            prawo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int blad=0;
                                    lala2=false;
                                    if((x<(wielkoscSzer-1))&&(lala3)  ){
                                        if(figura==0){
                                            if(x<(wielkoscSzer-2)){
                                                if(lista.get(y*wielkoscSzer+x+2).getBackground()!=null){
                                                    blad=1;
                                                }
                                                if(lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                    blad=1;
                                                }
                                                if(blad==0){
                                                    lista.get((y*wielkoscSzer+x)).setBackgroundResource(0);
                                                    lista.get((y*wielkoscSzer+x+wielkoscSzer)).setBackgroundResource(0);
                                                    x++;
                                                    lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.zolty);
                                                    lista.get((y*wielkoscSzer+x+1)).setBackgroundResource(R.drawable.zolty);
                                                    lista.get((y*wielkoscSzer+x+wielkoscSzer)).setBackgroundResource(R.drawable.zolty);
                                                    lista.get((y*wielkoscSzer+x+wielkoscSzer+1)).setBackgroundResource(R.drawable.zolty);
                                                }
                                            }
                                        }
                                        else if(figura==1){
                                            if(obrot==0){
                                                if(y>2){
                                                    if(lista.get(y*wielkoscSzer+x-(3*wielkoscSzer)+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(y>1){
                                                    if(lista.get(y*wielkoscSzer+x-(2*wielkoscSzer)+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(y>0){
                                                    if(lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                                                    blad=1;
                                                }
                                                if(blad==0){
                                                    if(y>2){
                                                        lista.get((y*wielkoscSzer+x-(3*wielkoscSzer))).setBackgroundResource(0);
                                                    }
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                    }
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                    x++;
                                                    if(y>2){
                                                        lista.get((y*wielkoscSzer+x-(wielkoscSzer*3))).setBackgroundResource(R.drawable.rozowy);
                                                    }
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.rozowy);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.rozowy);
                                                    }
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.rozowy);
                                                }
                                            }
                                            else if(obrot==1){
                                                if(x<(wielkoscSzer-4)){
                                                    if(lista.get(y*wielkoscSzer+x+4).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(blad==0){
                                                    if(x<(wielkoscSzer-4)){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+3).setBackgroundResource(0);
                                                        x++;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.rozowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.rozowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.rozowy);
                                                        lista.get(y*wielkoscSzer+x+3).setBackgroundResource(R.drawable.rozowy);
                                                    }
                                                }
                                            }
                                        }
                                        else if(figura==2){
                                            if(obrot==0){
                                                if(y>1){
                                                    if(lista.get(y*wielkoscSzer+x-wielkoscWys+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(y>0){
                                                    if(lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                                                    blad=1;
                                                }
                                                if(blad==0){
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                    }
                                                    lista.get(y*wielkoscSzer+x-1).setBackgroundResource(0);
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                    x++;
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                    lista.get(y*wielkoscSzer+x-1).setBackgroundResource(R.drawable.pomaranczowy);
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                }
                                            }
                                            else if(obrot==1){
                                                if(x<(wielkoscSzer-3)){
                                                    if(lista.get(y*wielkoscSzer+x+3).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(blad==0){
                                                    if(x<(wielkoscSzer-3)){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                        x++;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==2){
                                                if(x<(wielkoscSzer-2)){
                                                    if(y>1){
                                                        if(lista.get(y*wielkoscSzer+x-(wielkoscWys-2)).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(y>0){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(blad==0){
                                                    if(x<(wielkoscSzer-2)){
                                                        if(y>1){
                                                            lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(0);
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                        }
                                                        if(y>0){
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        x++;
                                                        if(y>1){
                                                            lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(R.drawable.pomaranczowy);
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.pomaranczowy);
                                                        }
                                                        if(y>0){
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.pomaranczowy);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==3){
                                                if(x<(wielkoscSzer-3)){
                                                    if(lista.get(y*wielkoscSzer+x+3).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+3).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(blad==0){
                                                    if(x<(wielkoscSzer-3)){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(0);
                                                        x++;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                }
                                            }
                                        }
                                        else if(figura==3){
                                            if(obrot==0){
                                                if(y>1){
                                                    if(lista.get(y*wielkoscSzer+x-wielkoscWys+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(y>0){
                                                    if(lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                                                    blad=1;
                                                }
                                                if(blad==0){
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(0);
                                                    }
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                    x++;
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                }
                                            }
                                            else if(obrot==1){
                                                if(x<(wielkoscSzer-3)){
                                                    if(lista.get(y*wielkoscSzer+x+3).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x-wielkoscSzer+2).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(blad==0){
                                                    if(x<(wielkoscSzer-3)){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                        x++;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==2){
                                                if(x<(wielkoscSzer-2)){
                                                    if(y>1){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscWys+1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x-wielkoscSzer+2).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                }
                                                if(blad==0){
                                                    if(x<(wielkoscSzer-2)){
                                                        if(y>1){
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                        }
                                                        if(y>0){
                                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        x++;
                                                        if(y>1){
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.turkusowy);
                                                        }
                                                        if(y>0){
                                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.turkusowy);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==3){
                                                if(x<(wielkoscSzer-3)){
                                                    if(lista.get(y*wielkoscSzer+x+3).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(blad==0){
                                                    if(x<(wielkoscSzer-3)){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(0);
                                                        x++;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                }
                                            }
                                        }
                                        lala2=false;
                                        try {
                                            y4=y;
                                            if(y<0){
                                                y=0;
                                                throw new Exception("lala");
                                            }
                                            if(figura==0){
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer+1)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x+1)).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(figura==1){
                                                if(obrot==0){
                                                    while(!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get((y4*wielkoscSzer+x-(3*wielkoscSzer))).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==1){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+3).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+3).setBackgroundResource(R.drawable.bloczek);
                                                }
                                            }
                                            else if(figura==2){
                                                if(obrot==0){
                                                    while(!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer-1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==1){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==2){
                                                    while(!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==3){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscWys+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(R.drawable.bloczek);
                                                }
                                            }
                                            else if(figura==3){
                                                if(obrot==0){
                                                    while(!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x-1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get((y4*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==1){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==2) {
                                                    while (!lala2) {
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if (y4 < (wielkoscWys-1)) {
                                                            if (lista.get(y4 * wielkoscSzer + x + wielkoscSzer).getBackground() != null) {
                                                                lala2 = true;
                                                            } else if (lista.get(y4 * wielkoscSzer + x + 1).getBackground() != null) {
                                                                lala2 = true;
                                                            }
                                                        } else {
                                                            lala2 = true;
                                                        }
                                                    }
                                                    lista.get((y4 * wielkoscSzer + x - wielkoscWys)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4 * wielkoscSzer + x - wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4 * wielkoscSzer + x)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4 * wielkoscSzer + x - wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==3){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscWys+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                            }
                                        }
                                        catch (Exception ex2){
                                            Log.i("wynik", ex2.getMessage().toString());
                                        }
                                    }
                                }
                            });
                            lewo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int blad=0;
                                    lala2=false;
                                    if(x>0){
                                        if(figura==0){
                                            if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                blad=1;
                                            }
                                            if(y<(wielkoscWys-1)){
                                                if(lista.get(y*wielkoscSzer+x+wielkoscSzer-1).getBackground()!=null){
                                                    blad=1;
                                                }
                                            }
                                            if(blad==0){
                                                lista.get((y*wielkoscSzer+x+1)).setBackgroundResource(0);
                                                lista.get((y*wielkoscSzer+x+wielkoscSzer+1)).setBackgroundResource(0);
                                                x--;
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.zolty);
                                                lista.get((y*wielkoscSzer+x+1)).setBackgroundResource(R.drawable.zolty);
                                                lista.get((y*wielkoscSzer+x+wielkoscSzer)).setBackgroundResource(R.drawable.zolty);
                                                lista.get((y*wielkoscSzer+x+wielkoscSzer+1)).setBackgroundResource(R.drawable.zolty);
                                            }
                                        }
                                        else if(figura==1){
                                            if(obrot==0){
                                                if(y>2){
                                                    if(lista.get(y*wielkoscSzer+x-(3*wielkoscSzer+1)).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(y>1){
                                                    if(lista.get(y*wielkoscSzer+x-(wielkoscSzer*2+1)).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(y>0){
                                                    if(lista.get(y*wielkoscSzer+x-wielkoscSzer-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                }
                                                if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                    blad=1;
                                                }
                                                if(blad==0){
                                                    if(y>2){
                                                        lista.get((y*wielkoscSzer+x-(3*wielkoscSzer))).setBackgroundResource(0);
                                                    }
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-(2*wielkoscSzer))).setBackgroundResource(0);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                    }
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                    x--;
                                                    if(y>2){
                                                        lista.get((y*wielkoscSzer+x-(3*wielkoscSzer))).setBackgroundResource(R.drawable.rozowy);
                                                    }
                                                    if(y>1){
                                                        lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.rozowy);
                                                    }
                                                    if(y>0){
                                                        lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.rozowy);
                                                    }
                                                    lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.rozowy);
                                                }
                                            }
                                            else if(obrot==1){
                                                if(y<32){
                                                    if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+3).setBackgroundResource(0);
                                                        x--;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.rozowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.rozowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.rozowy);
                                                        lista.get(y*wielkoscSzer+x+3).setBackgroundResource(R.drawable.rozowy);
                                                    }
                                                }
                                            }
                                        }
                                        else if(figura==2){
                                            if(obrot==0){
                                                if(x>1){
                                                    if(y>1){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscWys-1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(y>0){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(lista.get(y*wielkoscSzer+x-2).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        if(y>1){
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                        }
                                                        if(y>0){
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x-1).setBackgroundResource(0);
                                                        x--;
                                                        if(y>1){
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.pomaranczowy);
                                                        }
                                                        if(y>0){
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.pomaranczowy);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x-1).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==1){
                                                if(y<wielkoscWys){
                                                    if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x-wielkoscSzer-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                        x--;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==2){
                                                if(x>0){
                                                    if(y>1){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscWys-1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(y>0){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscSzer-1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        if(y>1){
                                                            lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(0);
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                        }
                                                        if(y>0){
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        x--;
                                                        if(y>1){
                                                            lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(R.drawable.pomaranczowy);
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.pomaranczowy);
                                                        }
                                                        if(y>0){
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.pomaranczowy);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==3){
                                                if(y<wielkoscWys){
                                                    if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(0);
                                                        x--;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.pomaranczowy);
                                                        lista.get(y*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(R.drawable.pomaranczowy);
                                                    }
                                                }
                                            }
                                        }
                                        else if(figura==3){
                                            if(obrot==0){
                                                if(x>1){
                                                    if(y>1){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscWys-1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(y>0){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscSzer-2).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        if(y>1){
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                        }
                                                        if(y>0){
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                            lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(0);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        x--;
                                                        if(y>1){
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.turkusowy);
                                                        }
                                                        if(y>0){
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.turkusowy);
                                                            lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(R.drawable.turkusowy);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==1){
                                                if(y<wielkoscWys){
                                                    if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x-wielkoscSzer).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                        x--;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==2){
                                                if(x>0){
                                                    if(y>1){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscWys-1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(y>0){
                                                        if(lista.get(y*wielkoscSzer+x-wielkoscWys-1).getBackground()!=null){
                                                            blad=1;
                                                        }
                                                    }
                                                    if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        if(y>1){
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(0);
                                                        }
                                                        if(y>0){
                                                            lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(0);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        x--;
                                                        if(y>1){
                                                            lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.turkusowy);
                                                        }
                                                        if(y>0){
                                                            lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(R.drawable.turkusowy);
                                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.turkusowy);
                                                        }
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                }
                                            }
                                            else if(obrot==3){
                                                if(x>0){
                                                    if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                        blad=1;
                                                    }
                                                    if(blad==0){
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                        lista.get(y*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(0);
                                                        x--;
                                                        lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.turkusowy);
                                                        lista.get(y*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                                    }
                                                }
                                            }
                                        }
                                        lala2=false;
                                        try {
                                            y4=y;
                                            if(y<0){
                                                y=0;
                                                throw new Exception("lala");
                                            }
                                            if(figura==0){
                                                while(!lala2){
                                                    for(int i=(wielkoscWys-1);i>3;i--){
                                                        for(int j=(wielkoscSzer-1);j>=0;j--){
                                                            if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                    lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    y4++;
                                                    if(y4<(wielkoscWys-1)){
                                                        if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                        else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            lala2=true;
                                                        }
                                                    }
                                                    else{
                                                        lala2=true;
                                                    }
                                                }
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x-wielkoscSzer+1)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                lista.get((y4*wielkoscSzer+x+1)).setBackgroundResource(R.drawable.bloczek);
                                            }
                                            else if(figura==1){
                                                if(obrot==0){
                                                    while(!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get((y4*wielkoscSzer+x-(3*wielkoscSzer))).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==1){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+3).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+3).setBackgroundResource(R.drawable.bloczek);
                                                }
                                            }
                                            else if(figura==2){
                                                if(obrot==0){
                                                    while(!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer-1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==1){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==2){
                                                    while(!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get((y4*wielkoscSzer+x-(wielkoscSzer*2))).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==3){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscWys+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(R.drawable.bloczek);
                                                }
                                            }
                                            else if(figura==3){
                                                if(obrot==0){
                                                    while(!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x-1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get((y4*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4*wielkoscSzer+x)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==1){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==2) {
                                                    while (!lala2) {
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if (y4 < (wielkoscWys-1)) {
                                                            if (lista.get(y4 * wielkoscSzer + x + wielkoscSzer).getBackground() != null) {
                                                                lala2 = true;
                                                            } else if (lista.get(y4 * wielkoscSzer + x + 1).getBackground() != null) {
                                                                lala2 = true;
                                                            }
                                                        } else {
                                                            lala2 = true;
                                                        }
                                                    }
                                                    lista.get((y4 * wielkoscSzer + x - wielkoscWys)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4 * wielkoscSzer + x - wielkoscSzer)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get((y4 * wielkoscSzer + x)).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4 * wielkoscSzer + x - wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                                else if(obrot==3){
                                                    while (!lala2){
                                                        for(int i=(wielkoscWys-1);i>3;i--){
                                                            for(int j=(wielkoscSzer-1);j>=0;j--){
                                                                if(lista.get(i*wielkoscSzer+j).getBackground()!=null){
                                                                    if (lista.get(i*wielkoscSzer+j).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                        lista.get(i*wielkoscSzer+j).setBackgroundResource(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        y4++;
                                                        if(y4<(wielkoscWys-1)){
                                                            if(lista.get(y4*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                            else if(lista.get(y4*wielkoscSzer+x+wielkoscWys+1).getBackground()!=null){
                                                                lala2=true;
                                                            }
                                                        }
                                                        else{
                                                            lala2=true;
                                                        }
                                                    }
                                                    lista.get(y4*wielkoscSzer+x).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+1).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+2).setBackgroundResource(R.drawable.bloczek);
                                                    lista.get(y4*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(R.drawable.bloczek);
                                                }
                                            }
                                        }
                                        catch (Exception ex2){
                                            Log.i("wynik", ex2.getMessage().toString());
                                        }
                                    }
                                }
                            });
                            dol.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        if(!lala3){
                                            throw new Exception("lala");
                                        }
                                        lala2=true;
                                        lala3=false;
                                        if(y<0){
                                            y=0;
                                            throw new Exception("lala");
                                        }
                                        if(figura==0){
                                            lista.get((y*wielkoscSzer+x)).setBackgroundResource(0);
                                            lista.get((y*wielkoscSzer+x+1)).setBackgroundResource(0);
                                            lista.get((y*wielkoscSzer+wielkoscSzer+1+x)).setBackgroundResource(0);
                                            lista.get((y*wielkoscSzer+wielkoscSzer+x)).setBackgroundResource(0);
                                            while(!lala){
                                                y++;
                                                if(y<(wielkoscWys-1)){
                                                    if(lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                        if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                            lala=true;
                                                        }
                                                    }
                                                    else if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                        if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                            lala=true;
                                                        }
                                                    }
                                                }
                                                else{
                                                    lala=true;
                                                }
                                            }
                                            lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.zolty);
                                            lista.get((y*wielkoscSzer+x-wielkoscSzer+1)).setBackgroundResource(R.drawable.zolty);
                                            lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.zolty);
                                            lista.get((y*wielkoscSzer+x+1)).setBackgroundResource(R.drawable.zolty);
                                        }
                                        else if(figura==1){
                                            if(obrot==0){
                                                if(y>2){
                                                    lista.get((y*wielkoscSzer+x-(3*wielkoscSzer))).setBackgroundResource(0);
                                                }
                                                if(y>1){
                                                    lista.get(y*wielkoscSzer+x-(2*wielkoscSzer)).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                }
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(0);
                                                while(!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get((y*wielkoscSzer+x-(3*wielkoscSzer))).setBackgroundResource(R.drawable.rozowy);
                                                lista.get((y*wielkoscSzer+x-(2*wielkoscSzer))).setBackgroundResource(R.drawable.rozowy);
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.rozowy);
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.rozowy);
                                            }
                                            else if(obrot==1){
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+3).setBackgroundResource(0);
                                                while (!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+3).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+3).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.rozowy);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.rozowy);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.rozowy);
                                                lista.get(y*wielkoscSzer+x+3).setBackgroundResource(R.drawable.rozowy);
                                            }
                                        }
                                        else if(figura==2){
                                            if(obrot==0){
                                                if(y>1){
                                                    lista.get(y*wielkoscSzer+x-wielkoscWys).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                }
                                                lista.get(y*wielkoscSzer+x-1).setBackgroundResource(0);
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(0);
                                                while(!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer-1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer-1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x-1).setBackgroundResource(R.drawable.pomaranczowy);
                                            }
                                            else if(obrot==1){
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                while (!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(R.drawable.pomaranczowy);
                                            }
                                            else if(obrot==2){
                                                if(y>1){
                                                    lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(0);
                                                    lista.get(y*wielkoscSzer+x-wielkoscWys).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                }
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(0);
                                                while(!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscWys+1).setBackgroundResource(R.drawable.pomaranczowy);
                                            }
                                            else if(obrot==3){
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(0);
                                                while (!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscWys+2).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscWys+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.pomaranczowy);
                                                lista.get(y*wielkoscSzer+x+wielkoscSzer+2).setBackgroundResource(R.drawable.pomaranczowy);
                                            }
                                        }
                                        else if(figura==3){
                                            if(obrot==0){
                                                if(y>1){
                                                    lista.get(y*wielkoscSzer+x-wielkoscWys).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(0);
                                                }
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(0);
                                                while(!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x-1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer-1).setBackgroundResource(R.drawable.turkusowy);
                                            }
                                            else if(obrot==1){
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                while (!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                            }
                                            else if(obrot==2){
                                                if(y>1){
                                                    lista.get(y*wielkoscSzer+x-wielkoscWys).setBackgroundResource(0);
                                                }
                                                if(y>0){
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(0);
                                                    lista.get(y*wielkoscSzer+x-wielkoscSzer).setBackgroundResource(0);
                                                }
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(0);
                                                while(!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get((y*wielkoscSzer+x-wielkoscWys)).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get((y*wielkoscSzer+x-wielkoscSzer)).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get((y*wielkoscSzer+x)).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x-wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                            }
                                            else if(obrot==3){
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(0);
                                                lista.get(y*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(0);
                                                while (!lala){
                                                    y++;
                                                    if(y<(wielkoscWys-1)){
                                                        if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                        else if(lista.get(y*wielkoscSzer+x+wielkoscWys+1).getBackground()!=null){
                                                            if(!lista.get(y*wielkoscSzer+x+wielkoscWys+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                                                                lala=true;
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        lala=true;
                                                    }
                                                }
                                                lista.get(y*wielkoscSzer+x).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x+1).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x+2).setBackgroundResource(R.drawable.turkusowy);
                                                lista.get(y*wielkoscSzer+x+wielkoscSzer+1).setBackgroundResource(R.drawable.turkusowy);
                                            }
                                        }
                                    }
                                    catch (Exception ex2){
                                        Log.i("wynik", ex2.getMessage().toString());
                                    }
                                }
                            });
                        }
                    }
                    catch (Exception ex){
                        Log.i("wynik", ex.getMessage().toString());
                    }
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.start();
    }
    public void sprawdzanie(){
        if(figura==0){
            if(y<(wielkoscWys-1)){
                if(lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+wielkoscSzer).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }

            }
            else{
                lala=true;
            }
        }
        else if(figura==1&&obrot==0){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==1&&obrot==1){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+2).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+3).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+3).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==2&&obrot==0){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x-1).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x-1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==2&&obrot==1){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+2).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==2&&obrot==2){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                if(y>2){
                    if(lista.get(y*wielkoscSzer+x-wielkoscWys+1).getBackground()!=null){
                        if(!lista.get(y*wielkoscSzer+x-wielkoscWys+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                            lala=true;
                        }
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==2&&obrot==3){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==3&&obrot==0){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x-wielkoscSzer-1).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x-wielkoscSzer-1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==3&&obrot==1){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+1).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+2).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==3&&obrot==2){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                if(y>2){
                    if(lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground()!=null){
                        if(!lista.get(y*wielkoscSzer+x-wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                            lala=true;
                        }
                    }
                }
            }
            else{
                lala=true;
            }
        }
        else if(figura==3&&obrot==3){
            if(y<wielkoscWys){
                if(lista.get(y*wielkoscSzer+x).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+2).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+2).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
                else if(lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground()!=null){
                    if(!lista.get(y*wielkoscSzer+x+wielkoscSzer+1).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bloczek).getConstantState())){
                        lala=true;
                    }
                }
            }
            else{
                lala=true;
            }
        }
    }
}
