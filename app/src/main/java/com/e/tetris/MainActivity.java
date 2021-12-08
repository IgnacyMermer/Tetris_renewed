package com.e.tetris;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton downBtn, rightBtn, leftBtn, rotateBtn, stopBtn;
    boolean isGameOver = false, isBlockGoing = true;
    TextView scoreField;

    int boardWidth, boardHeight, width, height, timeOfMotion = 350;

    long t = 0;

    ArrayList<TextView> lista;

    int lastYBlockPosition = Positions.positionYBlock;
    int lastXBlockPosition = Positions.positionXBlock;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = new ArrayList<>();
        scoreField = findViewById(R.id.wynik);
        downBtn = findViewById(R.id.strzalka);
        rightBtn = findViewById(R.id.strzalka_prawo);
        leftBtn = findViewById(R.id.strzalka_lewo);
        rotateBtn = findViewById(R.id.strzalka_w_dol);
        stopBtn = findViewById(R.id.ustawienia);
        final ArrayList<Integer> listaPlansza = null;

        final android.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        Runnable mainRunnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {

                    try{
                        while(!isGameOver) {

                            while (isBlockGoing) {
                                t = System.currentTimeMillis() + timeOfMotion;
                                while (t > System.currentTimeMillis()) { }

                                if(Positions.actualBlockNumber == 0){

                                    if(Square.isGameOver(lista)){
                                        isGameOver = true;
                                    }

                                    else if (Square.canMove(lista)) {
                                        Positions.positionYBlock++;
                                    }

                                    else if(Positions.positionYBlock < 4){
                                        Positions.positionYBlock++;
                                    }

                                    else{
                                        isBlockGoing=false;
                                    }

                                }

                                else if(Positions.actualBlockNumber == 1){
                                    if(Rectangle.isGameOver(lista)){
                                        isGameOver = true;
                                    }

                                    else if (Rectangle.canMove(lista)) {
                                        Positions.positionYBlock++;
                                    }

                                    else if(Positions.positionYBlock < 4){
                                        Positions.positionYBlock++;
                                    }

                                    else{
                                        isBlockGoing=false;
                                    }
                                }


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        lastYBlockPosition = Positions.positionYBlock;
                                        lastXBlockPosition = Positions.positionXBlock;

                                        if(Positions.actualBlockNumber == 0){
                                            Square.clearAndColorFields(lista, lastYBlockPosition, lastXBlockPosition);
                                        }

                                        else if(Positions.actualBlockNumber == 1){
                                            Rectangle.clearAndColorFields(lista, lastYBlockPosition, lastXBlockPosition);
                                        }

                                    }
                                });
                            }

                            System.out.println(Math.random());

                            Positions.actualBlockNumber = (int)Math.round(Math.random());

                            isBlockGoing=true;
                            Positions.positionXBlock = 0;
                            Positions.positionYBlock = 2;
                            Positions.positionX = 0;
                            Positions.positionY = 0;
                            Positions.tempPositionY = 50;
                            Positions.tempPositionY = 10;
                            lastXBlockPosition = 0;
                            lastYBlockPosition = 2;


                        }

                    }
                    catch (Exception exception){
                        Log.i("mainError", exception.getMessage());
                    }
                }
            }
        };

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Positions.actualBlockNumber){
                    case 0:
                        Square.onRightBtn(lista);
                        t = System.currentTimeMillis()+timeOfMotion;
                        break;
                    case 1:
                        Rectangle.onRightBtn(lista);
                        t = System.currentTimeMillis()+timeOfMotion;
                        break;
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Positions.actualBlockNumber){
                    case 0:
                        Square.onLeftBtn(lista);
                        t = System.currentTimeMillis()+timeOfMotion;
                        break;
                    case 1:
                        Rectangle.onLeftBtn(lista);
                        t = System.currentTimeMillis()+timeOfMotion;
                        break;
                }

            }
        });

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Positions.actualBlockNumber){
                    case 0:
                        Square.onBottomBtn(lista);
                        break;
                    case 1:
                        Rectangle.onBottomBtn(lista);
                        break;
                }


            }
        });


        final Thread mainThread = new Thread(mainRunnable);


        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                RelativeLayout relativeLayout1 = findViewById(R.id.layoutCaly);
                int szer = relativeLayout1.getWidth();

                RelativeLayout relativeLayout = findViewById(R.id.layout_pod_gridem);
                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

                boardWidth = relativeLayout.getWidth();
                int szer1 = scoreField.getHeight();
                boardHeight =relativeLayout.getHeight();
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                for(int i = 0; i < (Constants.numberOfColumns * Constants.numberOfRows); i++){
                    TextView textView = new TextView(MainActivity.this);
                    lista.add(textView);
                    gridLayout.addView(textView);
                }

                if((boardHeight / 2) > boardWidth){
                    boardHeight = boardWidth * 2;
                }
                else{
                    boardWidth = boardHeight / 2;
                }

                szer -= boardWidth + 3;
                szer = szer / 2;

                layoutParams1.width = boardWidth;
                layoutParams1.height = boardHeight;
                layoutParams1.setMargins(szer, szer1, szer,0);

                relativeLayout.setLayoutParams(layoutParams1);
                relativeLayout.setBackgroundResource(R.drawable.ramka);
                width = (boardWidth - 50) / Constants.numberOfColumns;
                height = (boardHeight - 40) / Constants.numberOfRows;

                for(int i = 0; i < ( Constants.numberOfRows * Constants.numberOfColumns ); i++){

                    TextView textView = lista.get(i);
                    android.widget.GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.width = width;
                    layoutParams.height = height;

                    int column = i / Constants.numberOfColumns;

                    if(column>2){
                        column++;
                    }

                    layoutParams.rowSpec = GridLayout.spec(column);
                    layoutParams.columnSpec = GridLayout.spec(i % Constants.numberOfColumns);
                    textView.setLayoutParams(layoutParams);

                }

                for(int i = 0; i< Constants.numberOfColumns; i++){
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
                    for(int i = 0; i < ( Constants.numberOfRows * Constants.numberOfColumns ); i++){
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

                mainThread.start();

            }
        });

    }
}
