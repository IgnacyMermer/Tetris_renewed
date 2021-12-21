package com.e.tetris;

import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class BlockT {

    static void clearAndColorFields(List<TextView> lista, int lastYBlockPosition, int lastXBlockPosition){

        if(Positions.actualRotation == 0) {
            try {

                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 1)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 2)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                        .setBackgroundResource(0);

            } catch (Exception ex2) {
                Log.i("clearing fields", ex2.getMessage());
            }

            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 2)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + Constants.numberOfColumns + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 1){
            try {

                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1 - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - 2 * Constants.numberOfColumns + 1)
                        .setBackgroundResource(0);

            } catch (Exception ex2) {
                Log.i("clearing fields", ex2.getMessage());
            }

            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 2){
            try {

                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 2 - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1 - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - 2 * Constants.numberOfColumns + 1)
                        .setBackgroundResource(0);

            } catch (Exception ex2) {
                Log.i("clearing fields", ex2.getMessage());
            }

            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 3){
            try {

                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1 - Constants.numberOfColumns)
                        .setBackgroundResource(0);
                lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - 2 * Constants.numberOfColumns)
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
            lista.get((lastYBlockPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

    }

    static boolean canMove(List<TextView> lista){
        if(Positions.actualRotation == 0 && Positions.positionYBlock < 18 &&
                lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns
                    + Positions.positionXBlock + 1).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns
                        + Positions.positionXBlock).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns
                        + Positions.positionXBlock + 2).getBackground() == null){

            return true;
        }

        else if(Positions.actualRotation == 1 && Positions.positionYBlock < 18 &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock).getBackground() == null &&
                lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns + Positions.positionXBlock + 1).getBackground() == null){

            return true;

        }

        else if(Positions.actualRotation == 2 && Positions.positionYBlock < 19 &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock + 1).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock + 2).getBackground() == null){

            return true;

        }

        else if(Positions.actualRotation == 3 && Positions.positionYBlock < 18 &&
                lista.get((Positions.positionYBlock + 2) * Constants.numberOfColumns + Positions.positionXBlock).getBackground() == null &&
                lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock + 1).getBackground() == null){

            return true;

        }

        return false;
    }

    static boolean isGameOver(List<TextView> lista){
        if(Positions.actualRotation == 0 && Positions.positionYBlock < 4 && lista.get((Positions.positionYBlock + 2)
                * Constants.numberOfColumns + Positions.positionXBlock + 1).getBackground() != null && lista.get((Positions.positionYBlock + 1)
                * Constants.numberOfColumns + Positions.positionXBlock).getBackground() != null && lista.get((Positions.positionYBlock + 1)
                * Constants.numberOfColumns + Positions.positionXBlock + 2).getBackground() != null ){

            return true;
        }

        else if(Positions.actualRotation == 1 && Positions.positionYBlock < 4 && lista.get((Positions.positionYBlock + 2)
                * Constants.numberOfColumns + Positions.positionXBlock).getBackground() != null
                && lista.get((Positions.positionYBlock + 3) * Constants.numberOfColumns +
                Positions.positionXBlock + 1).getBackground() != null){

            return true;

        }

        else if(Positions.actualRotation == 2 && Positions.positionYBlock < 4 && lista.get((Positions.positionYBlock + 2)
                * Constants.numberOfColumns + Positions.positionXBlock).getBackground() != null
                && lista.get((Positions.positionYBlock + 3) * Constants.numberOfColumns +
                Positions.positionXBlock + 1).getBackground() != null){

            return true;

        }

        return false;
    }

    static void onRightBtn(List<TextView> lista){

        if(Positions.actualRotation == 0 && Positions.positionXBlock < 7 &&
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 2)
                .getBackground()==null && lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 3)
                .getBackground()==null) {

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 3))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 2))
                    .setBackgroundResource(R.drawable.turkusowy);


            Positions.positionXBlock++;

        }

        else if(Positions.actualRotation == 1 && Positions.positionXBlock < 8 &&
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 2)
                .getBackground()==null && lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2)
                .getBackground()==null && lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock
                - Constants.numberOfColumns + 2).getBackground()==null){

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2 - Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 2))
                    .setBackgroundResource(R.drawable.turkusowy);


            Positions.positionXBlock++;
        }

        else if(Positions.actualRotation == 2 && Positions.positionXBlock < 7 &&
                lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 2)
                .getBackground()==null && lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 3)
                .getBackground()==null){

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 3))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 2))
                    .setBackgroundResource(R.drawable.turkusowy);


            Positions.positionXBlock++;
        }

        else if(Positions.actualRotation == 3 && Positions.positionXBlock < 8 &&
                lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                .getBackground()==null && lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2)
                .getBackground()==null && lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock
                - Constants.numberOfColumns + 1).getBackground()==null){

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1 - Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);


            Positions.positionXBlock++;
        }

    }

    static void onLeftBtn(List<TextView> lista){
        if(Positions.actualRotation == 0 && Positions.positionXBlock>0) {

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);

            Positions.positionXBlock--;
        }

        else if(Positions.actualRotation == 1 && Positions.positionXBlock>0){
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);

            Positions.positionXBlock--;
        }

        else if(Positions.actualRotation == 2 && Positions.positionXBlock>0){
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1))
                    .setBackgroundResource(R.drawable.turkusowy);

            Positions.positionXBlock--;
        }

        else if(Positions.actualRotation == 3 && Positions.positionXBlock>0){
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns)
                    .setBackgroundResource(0);


            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns - 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns - 1))
                    .setBackgroundResource(R.drawable.turkusowy);

            Positions.positionXBlock--;
        }

    }

    static void rotate(List<TextView> lista){

        int lastYBlockPosition = Positions.positionYBlock;
        int lastXBlockPosition = Positions.positionXBlock;

        if(Positions.actualRotation == 0){
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 2)
                    .setBackgroundResource(0);

            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 1){
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1 - Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);

            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 2){
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1 - Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 2)
                    .setBackgroundResource(0);

            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 3){
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition - Constants.numberOfColumns)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition)
                    .setBackgroundResource(0);
            lista.get(lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + Constants.numberOfColumns)
                    .setBackgroundResource(0);

            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + 2))
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get((lastYBlockPosition * Constants.numberOfColumns + lastXBlockPosition + Constants.numberOfColumns + 1))
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        Positions.actualRotation = Positions.actualRotation == 0 ? 1 : Positions.actualRotation == 1 ? 2 : Positions.actualRotation == 2 ? 3 : 0;

    }

    static void onBottomBtn(List<TextView> lista){
        int tempYPosition = Positions.positionYBlock;

        if(Positions.actualRotation == 0){
            while((tempYPosition < 18 && lista.get((tempYPosition + 1) * Constants.numberOfColumns
                    + Positions.positionXBlock).getBackground() == null  && lista.get((tempYPosition + 2) * Constants.numberOfColumns
                    + Positions.positionXBlock + 1).getBackground() == null  && lista.get((tempYPosition + 1)
                    * Constants.numberOfColumns + Positions.positionXBlock + 2).getBackground() == null)||tempYPosition<4){

                tempYPosition++;

            }

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);

            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + 2)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 1){
            while((tempYPosition < 18 && lista.get((tempYPosition + 1) * Constants.numberOfColumns
                    + Positions.positionXBlock).getBackground() == null  && lista.get((tempYPosition + 2) * Constants.numberOfColumns
                    + Positions.positionXBlock + 1).getBackground() == null)||tempYPosition<4){

                tempYPosition++;

            }

            lista.get((Positions.positionYBlock - 1) * Constants.numberOfColumns + Positions.positionXBlock + 1).setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);

            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 2){
            while((tempYPosition < 18 && lista.get((tempYPosition + 1) * Constants.numberOfColumns
                    + Positions.positionXBlock).getBackground() == null  && lista.get((tempYPosition + 1) * Constants.numberOfColumns
                    + Positions.positionXBlock + 1).getBackground() == null  && lista.get((tempYPosition + 1)
                    * Constants.numberOfColumns + Positions.positionXBlock + 2).getBackground() == null)||tempYPosition<4){

                tempYPosition++;

            }

            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 2)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1)
                    .setBackgroundResource(0);

            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + 2)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        else if(Positions.actualRotation == 3){
            while((tempYPosition < 18 && lista.get((tempYPosition + 1) * Constants.numberOfColumns
                    + Positions.positionXBlock + 1).getBackground() == null  && lista.get((tempYPosition + 2) * Constants.numberOfColumns
                    + Positions.positionXBlock).getBackground() == null)||tempYPosition<4){

                tempYPosition++;

            }

            lista.get((Positions.positionYBlock - 1) * Constants.numberOfColumns + Positions.positionXBlock).setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);
            lista.get(Positions.positionYBlock * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(0);
            lista.get((Positions.positionYBlock + 1) * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(0);

            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + 1)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock - Constants.numberOfColumns)
                    .setBackgroundResource(R.drawable.turkusowy);
            lista.get(tempYPosition * Constants.numberOfColumns + Positions.positionXBlock + Constants.numberOfColumns)
                    .setBackgroundResource(R.drawable.turkusowy);
        }

        Positions.positionYBlock = tempYPosition;
    }

}
