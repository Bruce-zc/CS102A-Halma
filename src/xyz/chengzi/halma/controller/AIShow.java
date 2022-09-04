package xyz.chengzi.halma.controller;

import java.awt.*;

public class AIShow extends Thread{
    GameController gc;
    public static long think=300;
    public AIShow(GameController gc) {
        this.gc=gc;
    }


    public void run() {
        super.run();
        think=300;
        while (!gc.getModel().isEnd()){
            if (gc.getCurrentPlayer().equals(Color.RED)){
                try {
                    Thread.sleep(think);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gc.getModel().automaticallyMoveRed();
                gc.nextPlayer();
                if (gc.getModel().getMode4Difficulty()%10 == 1) {
                    gc.getModel().setMediumAI(false);
                    gc.getModel().setHardAI(false);
                    gc.getModel().setEasyAI(true);
                }
                if (gc.getModel().getMode4Difficulty()%10 == 2) {
                    gc.getModel().setMediumAI(true);
                    gc.getModel().setHardAI(false);
                    gc.getModel().setEasyAI(false);
                }
                if (gc.getModel().getMode4Difficulty()%10 == 3) {
                    gc.getModel().setMediumAI(false);
                    gc.getModel().setHardAI(true);
                    gc.getModel().setEasyAI(false);
                }
            }else if (gc.getCurrentPlayer().equals(Color.GREEN)){
                try {
                    Thread.sleep(think);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gc.getModel().automaticallyMoveGreen();
                gc.nextPlayer();
                if (gc.getModel().getMode4Difficulty()/10 == 1) {
                    gc.getModel().setMediumAI(false);
                    gc.getModel().setHardAI(false);
                    gc.getModel().setEasyAI(true);
                }
                if (gc.getModel().getMode4Difficulty()/10 == 2) {
                    gc.getModel().setMediumAI(true);
                    gc.getModel().setHardAI(false);
                    gc.getModel().setEasyAI(false);
                }
                if (gc.getModel().getMode4Difficulty()/10 == 3) {
                    gc.getModel().setMediumAI(false);
                    gc.getModel().setHardAI(true);
                    gc.getModel().setEasyAI(false);
                }
            }
        }
        if (gc.getModel().isEnd()){
            this.stop();
        }
        gc.gameStartedSpecialEvent();
    }

}

