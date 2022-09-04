package xyz.chengzi.halma.Online;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.Buffer;
//
//public class Server {
//    public static Socket socket=null;
//    private int mode;
//    public Server(int mode){
//        this.mode=mode;
//    }
//    public void startServer() throws IOException {
//        ServerSocket server=new ServerSocket(8131);
//        Client client=new Client(mode);
//        Thread clientThread=new Thread(client);
//        clientThread.start();
//        Server.socket=server.accept();
//        System.out.println("connected");
//
//        InputStream inputStream=socket.getInputStream();
//        InputStreamReader streamReader=new InputStreamReader(inputStream);
//        BufferedReader in=new BufferedReader(streamReader);
//        PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
//        InputStreamReader streamReader1=new InputStreamReader(System.in);
//        BufferedReader out=new BufferedReader(streamReader1);
//
//
//        String inMove="";
//        String outMove="";
//        while(true){
//            //move the chess
//
//            inMove=in.readLine();
//            System.out.println(inMove);
//            outMove=out.readLine();
//            printWriter.println(outMove);
//            printWriter.flush();
//        }
//    }
//
//}



import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.view.ChessBoardComponent;
import xyz.chengzi.halma.view.GameFrame;
import xyz.chengzi.halma.view.HomeForOnline;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public  class  Server extends Thread{
    //Socket socket;
    ServerSocket server = null;
    public static Socket socket = null;
    static GameController controller;
    GameFrame mainframe;
    ChessBoard chessboard;
    String a;

    public Server() {
        try {
            server = new ServerSocket(8131);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        super.run();
        try{
           // System.out.println("waiting");
            JOptionPane.showMessageDialog(null, "waiting to connect");
            socket = server.accept();
            socket.setKeepAlive(true);
            Client c=new Client(socket);
            c.start();

            //startGame();
            new Sender(chessboard,server.accept()).start();
            InputStream in = socket.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            while ((length=in.read(buf))!=-1){
                try {
               //     System.out.println("aaa");
                    readMove(new String(buf, 0, length));
               //     System.out.println("client saying: "+new String(buf,0,length));
                }catch (Exception exception){
                    if (!new String(buf, 0, length).equals(a)) {
                        mainframe.displayer.append(new String(buf, 0, length));
                        a = new String(buf, 0, length);
                    }
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void turnToPlay(Color currentPlayer){
        JOptionPane.showMessageDialog(null, "The joiner plays first!");

    }
    public void setValidPiece(ChessBoard chessboard){
        for (int i=0;i<chessboard.getDimension();i++){
            for (int j=0;j<chessboard.getDimension();j++){
                ChessBoardLocation location=new ChessBoardLocation(i,j);
                if (chessboard.getChessPieceAt(location)!=null) {
                    if (chessboard.getChessPieceAt(location).getColor()==Color.GREEN){
                        chessboard.getChessPieceAt(location).setInvalid(false);
                    }

                }
            }
        }
    }


    public void startGame(){
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        ChessBoard chessBoard = new ChessBoard(16,1);
        chessBoard.setOnline(true);
        chessBoard.setSocket(socket);

        GameController controller = new GameController(chessBoardComponent, chessBoard);
        controller.setCurrentPlayer(Color.GREEN);
        GameFrame mainFrame = new GameFrame(1);
        mainFrame.status(2);
        mainFrame.add(chessBoardComponent);
        mainFrame.setController(controller);
        mainFrame.setChessBoard(chessBoard);
        chessBoard.setGameFrame(mainFrame);
        mainFrame.setCurrentText();
        mainFrame.disablePicture();
        mainFrame.setVisible(true);
        turnToPlay(controller.getCurrentPlayer());
        setValidPiece(chessBoard);
        this.controller=controller;
        this.mainframe=mainFrame;
        this.chessboard=chessBoard;
    }
    public void readMove(String m){
        ChessBoardLocation src = new ChessBoardLocation(Integer.parseInt(m.substring(0, 2)), Integer.parseInt(m.substring(2, 4)));
        ChessBoardLocation dest = new ChessBoardLocation(Integer.parseInt(m.substring(4, 6)), Integer.parseInt(m.substring(6, 8)));
        try {
            chessboard.setSocket(socket);
            chessboard.replayMove(src, dest);
        }catch (NullPointerException n) {
            setValidPiece(chessboard);
        }
        controller.nextPlayer();
        controller.nextPlayer();
        setValidPiece(chessboard);
    }
//    public static void main(String[] args) {
//        Server server = new Server();
//        server.start();
//    }
}
