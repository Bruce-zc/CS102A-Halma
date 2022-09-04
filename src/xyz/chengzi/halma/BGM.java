package xyz.chengzi.halma;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BGM extends Thread{
    Player player;
    File bgm;

    public BGM(File file) {
        this.bgm = file;
    }


    public void run() {
        super.run();
        try {
            play();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    public void play() throws FileNotFoundException, JavaLayerException {

        BufferedInputStream stream =new BufferedInputStream(new FileInputStream(bgm));
        player = new Player(stream);
        player.play();
    }

}
