package Mines;
import java.io.Serializable;

import game.ButtonsMap;

/**
 * Mine Counter- Calculate numbers of mines around this block 
 */

public class MinesCounter implements Serializable {
    private static final long serialVersionUID = -6003667686542091934L;
    ButtonsMap bm;

    public MinesCounter(ButtonsMap bm){
        this.bm = bm;
    }

    //Get and set i,j is Mine
    public void countMines(){
        for(int i = 0; i < bm.getRows(); i++){
            for(int j = 0; j < bm.getCols(); j++){
                //If it is not a mine, does not count
                if(bm.getMineButton(i, j).isMine())
                    continue;
                bm.getMineButton(i, j).setMineCntAround(getCountAround(i, j));
            }
        }
    }
    //Get the Mines Count around a button
    public int getCountAround(int i, int j){
        int cnt = 0;
        if (isMine(i - 1, j - 1))
            cnt++;
        if (isMine(i - 1, j))
            cnt++;
        if (isMine(i - 1, j + 1))
            cnt++;
        if (isMine(i, j - 1))
            cnt++;
        if (isMine(i, j + 1))
            cnt++;
        if (isMine(i + 1, j - 1))
            cnt++;
        if (isMine(i + 1, j))
            cnt++;
        if (isMine(i + 1, j + 1))
            cnt++;
        return cnt;
    }
    //return if it is Mine
    public boolean isMine(int i, int j){
        if(i < 0 || i >= bm.getRows() || j < 0 || j >= bm.getCols())
            return false;
        return bm.getMineButton(i, j).isMine();
    }
}
