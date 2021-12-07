package com.e.tetris;

import android.util.Log;
import android.widget.TextView;

import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public class Square {

    static void showPrediction(List<TextView> lista, int numberOfColumns, int numberOfRows){

        boolean temp=false;
        int tempPositionY = Positions.positionY+1;

        try {
            lista.get(Positions.positionOfPrediction).setBackgroundResource(0);
            lista.get(Positions.positionOfPrediction + 1).setBackgroundResource(0);
            lista.get(Positions.positionOfPrediction - numberOfColumns).setBackgroundResource(0);
            lista.get(Positions.positionOfPrediction - numberOfColumns + 1).setBackgroundResource(0);

        }
        catch (Exception ex){
            Log.i("error",ex.getMessage());
        }

        while(!temp){

            tempPositionY++;

            if( tempPositionY < ( numberOfRows - 1 )){

                if( lista.get( tempPositionY * numberOfColumns + Positions.positionX)
                        .getBackground()!=null && !lista.get( tempPositionY * numberOfColumns + Positions.positionX).getBackground().getConstantState()
                        .equals(R.drawable.bloczek)){
                    temp=true;
                }
                else if(lista.get(tempPositionY * numberOfColumns + Positions.positionX + 1)
                        .getBackground()!=null){
                    temp=true;
                }
            }
            else{
                temp=true;
            }
        }


        Positions.positionOfPrediction = tempPositionY * numberOfColumns + Positions.positionX;

        lista.get((Positions.positionOfPrediction - numberOfColumns))
                .setBackgroundResource(R.drawable.bloczek);
        lista.get((Positions.positionOfPrediction - numberOfColumns + 1))
                .setBackgroundResource(R.drawable.bloczek);
        lista.get((Positions.positionOfPrediction))
                .setBackgroundResource(R.drawable.bloczek);
        lista.get((Positions.positionOfPrediction + 1))
                .setBackgroundResource(R.drawable.bloczek);
    }

    static void colorTheBorder(List<TextView> lista, int numberOfColumns ){
        if((Positions.positionY > 0 && Positions.positionX >0) || (Positions.positionY >1)){
            lista.get((Positions.positionY * numberOfColumns + Positions.positionX - numberOfColumns)).setBackgroundResource(0);
            lista.get((Positions.positionY * numberOfColumns + Positions.positionX - numberOfColumns +1)).setBackgroundResource(0);
        }
        lista.get((Positions.positionY * numberOfColumns + Positions.positionX)).setBackgroundResource(R.drawable.zolty);
        lista.get((Positions.positionY * numberOfColumns + Positions.positionX +1)).setBackgroundResource(R.drawable.zolty);
        lista.get((Positions.positionY * numberOfColumns + Positions.positionX + numberOfColumns)).setBackgroundResource(R.drawable.zolty);
        lista.get((Positions.positionY * numberOfColumns + Positions.positionX + numberOfColumns +1)).setBackgroundResource(R.drawable.zolty);
    }

    static void onRightBtn(List<TextView> lista){
        if(Positions.positionXBlock<8) {

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 2))
                    .setBackgroundResource(R.drawable.zolty);

            Positions.positionXBlock++;
        }
    }

    static void onLeftBtn(List<TextView> lista){
        if(Positions.positionXBlock>0) {

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1).setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns - 1))
                    .setBackgroundResource(R.drawable.zolty);

            Positions.positionXBlock--;
        }
    }

    static void onBottomBtn(List<TextView> lista){
        int tempYPosition = Positions.positionYBlock;

        while((tempYPosition < 17 && lista.get((tempYPosition + 3) * Constants.numberOfColumns
                + Positions.positionXBlock).getBackground() == null && lista.get((tempYPosition + 3) *
                Constants.numberOfColumns + Positions.positionXBlock + 1).getBackground() == null)||tempYPosition<4){

            tempYPosition++;

        }

        lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns)
                .setBackgroundResource(0);
        lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns+1)
                .setBackgroundResource(0);
        lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(0);
        lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1).setBackgroundResource(0);

        Positions.positionYBlock = tempYPosition;
    }

    static void clearAndColorFields(List<TextView> lista, int lastYBlockPosition, int lastXBlockPosition){
        try {

            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                    .setBackgroundResource(0);

        }
        catch (Exception ex2) {
            Log.i("clearing fields", ex2.getMessage());
        }

        lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock))
                .setBackgroundResource(R.drawable.zolty);
        lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + 1))
                .setBackgroundResource(R.drawable.zolty);
        lista.get((lastYBlockPosition * Constants.numberOfColumns +
                Positions.positionXBlock + Constants.numberOfColumns))
                .setBackgroundResource(R.drawable.zolty);
        lista.get((lastYBlockPosition * Constants.numberOfColumns +
                Positions.positionXBlock + Constants.numberOfColumns + 1))
                .setBackgroundResource(R.drawable.zolty);
    }

}
