package com.e.tetris;

import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class BlockL {

    static void clearAndColorFields(List<TextView> lista, int lastYBlockPosition, int lastXBlockPosition){

        if(Positions.actualRotation == 0) {
            try {

                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - 2 * Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                        .setBackgroundResource(0);

            } catch (Exception ex2) {
                Log.i("clearing fields", ex2.getMessage());
            }

            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + Constants.numberOfColumns)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + Constants.numberOfColumns + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 1){
            try {

                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 1)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 2)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                        .setBackgroundResource(0);

            } catch (Exception ex2) {
                Log.i("clearing fields", ex2.getMessage());
            }

            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 2){
            try {

                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - 2 * Constants.numberOfColumns + 1)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - 2 * Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                        .setBackgroundResource(0);

            } catch (Exception ex2) {
                Log.i("clearing fields", ex2.getMessage());
            }

            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 3){
            try {

                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 2)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 2)
                        .setBackgroundResource(0);

            } catch (Exception ex2) {
                Log.i("clearing fields", ex2.getMessage());
            }

            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

    }

    static boolean canMove(List<TextView> lista){
        if(Positions.actualRotation == 0 && Positions.positionYBlock < 18 &&
                lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns
                        + Positions.positionXBlock).getBackground() == null &&
                lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns
                        + Positions.positionXBlock + 1).getBackground() == null){

            return true;
        }

        else if(Positions.actualRotation == 1 && Positions.positionYBlock < 18 &&
                lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns + Positions.positionXBlock).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock + 1).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock + 2).getBackground() == null){

            return true;

        }

        else if(Positions.actualRotation == 2 && Positions.positionYBlock < 19 &&
                lista.get((Positions.positionYBlock) * Constants.numberOfColumns + Positions.positionXBlock + 1).getBackground() == null &&
                lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns + Positions.positionXBlock).getBackground() == null){

            return true;

        }

        else if(Positions.actualRotation == 3 && Positions.positionYBlock < 18 &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock + 1).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock + 2).getBackground() == null){

            return true;

        }

        return false;
    }

    static boolean isGameOver(List<TextView> lista){
        return false;
    }

}