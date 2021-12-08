package com.e.tetris;

import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class Rectangle {

    static void clearAndColorFields(List<TextView> lista, int lastYBlockPosition, int lastXBlockPosition){
        try {

            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - 3 * Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - 2 * Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                    .setBackgroundResource(0);

        }
        catch (Exception ex2) {
            Log.i("clearing fields", ex2.getMessage());
        }

        lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock - 2 * Constants.numberOfColumns))
                .setBackgroundResource(R.drawable.rozowy);
        lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns))
                .setBackgroundResource(R.drawable.rozowy);
        lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock))
                .setBackgroundResource(R.drawable.rozowy);
        lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns))
                .setBackgroundResource(R.drawable.rozowy);
    }

    static boolean canMove(List<TextView> lista){
        if(Positions.positionYBlock < 18 && lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns
                + Positions.positionXBlock).getBackground() == null){

            return true;
        }

        return false;
    }

    static boolean isGameOver(List<TextView> lista){
        if(Positions.positionYBlock < 4 && lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns
                + Positions.positionXBlock).getBackground() != null ){

            return true;
        }

        return false;
    }

    static void onRightBtn(List<TextView> lista){

        if(Positions.positionXBlock<9) {

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 2 * Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.rozowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 2 * Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.rozowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.rozowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.rozowy);


            Positions.positionXBlock++;

        }
    }

    static void onLeftBtn(List<TextView> lista){
        if(Positions.positionXBlock>0) {

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 2 * Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 1))
                    .setBackgroundResource(R.drawable.rozowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 2 * Constants.numberOfColumns - 1))
                    .setBackgroundResource(R.drawable.rozowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns - 1))
                    .setBackgroundResource(R.drawable.rozowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns - 1))
                    .setBackgroundResource(R.drawable.rozowy);

            Positions.positionXBlock--;
        }
    }

    static void onBottomBtn(List<TextView> lista){
        int tempYPosition = Positions.positionYBlock;

        while((tempYPosition < 17 && lista.get((tempYPosition + 3) * Constants.numberOfColumns
                + Positions.positionXBlock).getBackground() == null)||tempYPosition<4){

            tempYPosition++;

        }

        lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(0);
        lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 2 * Constants.numberOfColumns)
                .setBackgroundResource(0);
        lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns)
                .setBackgroundResource(0);
        lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns)
                .setBackgroundResource(0);

        Positions.positionYBlock = tempYPosition;
    }

}
