package xyz.chengzi.halma.Online;

import xyz.chengzi.halma.model.ChessBoard;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class Sender extends Thread{
    public Socket socket;
    ChessBoard chessBoard;
    public Sender(ChessBoard cb, Socket socket){
        //this.socket=socket;
        this.chessBoard=cb;
    }


    public void run(){
        super.run();

        try{
            socket=Client.socket;
            if(socket != null){
                OutputStream out = socket.getOutputStream();
                String input = "";

                input=chessBoard.moves.get(chessBoard.moves.size()-1);
                //System.out.println("aaa");
                out.write((input).getBytes());
                out.flush();
            }else {
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
