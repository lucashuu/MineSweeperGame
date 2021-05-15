package util;

import javax.swing.*;

import game.GameBiz;
import game.GameWindow;
import server.DBHelper;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */

public class Archiver {

    
    private GameBiz gb1 = null, gb2 = null, gb3 = null;//Save the most recent game
    private int chosen;
    private List<GameBiz> glist = new ArrayList<GameBiz>();
    GameWindow gw;

    public Archiver(GameBiz g){
    	DBHelper db = new DBHelper();
    	glist.add(g);
    }
    
    public void chooseToSave(GameBiz gb){
       // read();
        //
      //  showSaveDialog(gb);
    }
    /*
    public GameBiz chooseToRead(){

        read();
        
        showReadDialog();
        if(chosen == 1){
            return gb1;
        }else if(chosen == 2){
            return gb2;
        }else if(chosen == 3){
            return gb3;
        }
        return null;
    }
	
    
    public void save(){
        File f = new File(ARCHIVE_PATH);
        if(!f.exists()){
            createFile();
        }
        ObjectOutputStream oout = null;
        try{
            oout = new ObjectOutputStream(new FileOutputStream(f));
            oout.writeObject(gb1);
            oout.writeObject(gb2);
            oout.writeObject(gb3);
        }catch (Exception e) {
            e.printStackTrace();
            return;
        }finally {
            try{
                oout.close();
            }catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
    }
   
    
    public void read(){
      
        ObjectInputStream oin = null;
        try{
            oin = new ObjectInputStream(new FileInputStream(f));
            gb1 = (GameBiz) oin.readObject();
            gb2 = (GameBiz) oin.readObject();
            gb3 = (GameBiz) oin.readObject();
        }catch (Exception e) {
            e.printStackTrace();
            return;
        }finally {
            try{
                oin.close();
            }catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
    }
     */
}

    