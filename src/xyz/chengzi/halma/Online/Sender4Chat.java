package xyz.chengzi.halma.Online;

import xyz.chengzi.halma.model.ChessBoard;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class Sender4Chat extends Thread {
    public Socket socket;
    ChessBoard chessBoard;
    String input;

    public Sender4Chat(String input) {
        //this.socket=socket;
        this.input = input;
    }


    public void run() {
        super.run();

        try {
            socket = Client.socket;
            if (socket != null) {
                OutputStream out = socket.getOutputStream();
                //System.out.println("aaa");
                out.write((input).getBytes());
                out.flush();
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
