package Mines;
import java.io.Serializable;
import java.util.Random;

import game.ButtonsMap;

/**
 * Create Mines to Jbuttons
 */

public class MinesCreator implements Serializable {
    private static final long serialVersionUID = 7077516197894917713L;
    private int minesCnt;


    public MinesCreator(){
    	  setMinesCnt();
    }
    //Produce Mines and make sure the input coordinate that does not create Mine twice
    public void createMines(ButtonsMap bm, int X, int Y){
        int cnt = this.minesCnt;
        int x, y;
        Random random = new Random();
        while(cnt-- != 0){
            x = random.nextInt(bm.getCols());
            y = random.nextInt(bm.getRows());
           //Random X and Y coordinate to create Mines
            if(bm.getMineButton(y, x).isMine() || x == X && y == Y) {
            	//Avoid that the input coordinate contains a mine
                cnt++;
                continue;
            }
            //Set this x and y has a mine
            bm.getMineButton(y, x).setMine(true);
        }
    }
    private void setMinesCnt(){
            this.minesCnt = 40;
            //Default Mines Count
    }

    public int getMinesCnt() {
        return minesCnt;
    }
}

