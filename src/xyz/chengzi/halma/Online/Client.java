package xyz.chengzi.halma.Online;

//import javax.swing.*;
//import java.io.*;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Client implements Runnable{
//    private int mode;
//    public Client(int mode){
//        this.mode=mode;
//    }
//    @Override
//    public void run() {
//        Scanner input=new Scanner(System.in);
//        if (mode==1) {
//            try {
//                System.out.println("input IP");
//                startClient(input.next());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
////        }else {
////            try {
////                startClient();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
//    }
//
//    public void startClient(String IP) throws IOException {
//        JFrame frame=new JFrame();
//        JTextField getIP=new JTextField();
//
//
//
//        Socket socket=new Socket(IP,8131);
//        System.out.println("connected");
//        PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
//
//        InputStreamReader streamReader=new InputStreamReader(System.in);
//        BufferedReader reader=new BufferedReader(streamReader);
//        InputStream in=socket.getInputStream();//读取数据
//        BufferedReader inReader=new BufferedReader(new InputStreamReader(in));
//
//
//        OutputStream output=socket.getOutputStream();
//        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output));
//        //Scanner input=new Scanner(System.in);
//        String outMove="";
//        String inMove="";
//        while(true){
//            //input moves
//            System.out.println("input moves");
//            outMove=reader.readLine();
//
//            writer.write(outMove);
//            inMove=inReader.readLine();
//            System.out.println(inMove);
//            printWriter.flush();
//
//
//        }
//    }
////    public void startClient() throws IOException {
////        while (true){
////            if (Server.socket!=null){
////                break;
////            }else {
////                try {
////                    Thread.sleep(200);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
////        }
////        Socket socket=new Socket(Server.socket.getInetAddress().getHostAddress(), 8131);
////        System.out.println("connected");
////        OutputStream output=socket.getOutputStream();
////        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output));
////        Scanner input=new Scanner(System.in);
////        while(true){
////            //input moves
////            System.out.println("moves");
////            writer.write(input.next());
////            writer.newLine();
////            writer.flush();
////        }
////    }
//}

import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.view.ChessBoardComponent;
import xyz.chengzi.halma.view.ChooseMode;
import xyz.chengzi.halma.view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;

public class Client extends Thread {
    public static Socket socket = null;
    GameController controller;
    GameFrame mainframe;
    ChessBoard chessboard;
    String a;
    public Client(String IP) throws IOException {
        socket = new Socket(IP, 8131);
        socket.setKeepAlive(true);
    }
    public Client(Socket socker) throws IOException {
        socket = socker;
        socket.setKeepAlive(true);
    }

    @Override
    public void run() {
        controller=startGame();
        controller.nextPlayer();
        mainframe.cp.setText("GREEN");
        //new sendMessThread().start();
        super.run();
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buf = new byte[1024];
            int length = 0;
            try {
                while ((length = inputStream.read(buf)) != -1) {
                    try {
                        readMove(new String(buf, 0, length));
                        mainframe.surItem.setEnabled(true);
                     //   System.out.println(new String(buf, 0, length));
                    }catch (Exception exception){
                        if (new String(buf, 0, length).equals("endgame-surrender-encryption-2020052315:39")){
                            chessboard.endGame();
                            socket.close();

                            JOptionPane.showMessageDialog(null,"You make him/her surrender! You win!");
                        }else {
                            if (!new String(buf, 0, length).equals(a)) {
                                mainframe.displayer.append(new String(buf, 0, length));
                                a = new String(buf, 0, length);
                            }
                        }
                    }

                }
            }catch (SocketException c){
                JOptionPane.showMessageDialog(null,"Connection lost");
                mainframe.setVisible(false);
                ChooseMode cm=new ChooseMode();
                cm.setVisible(true);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void turnToPlay(Color currentPlayer){
        JOptionPane.showMessageDialog(null, "The joiner plays first!");

    }
    public GameController startGame(){
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        ChessBoard chessBoard = new ChessBoard(16,1);
        chessBoard.setOnline(true);
        chessBoard.setSocket(socket);

        GameController controller = new GameController(chessBoardComponent, chessBoard);
        controller.setCurrentPlayer(Color.GREEN);

        GameFrame mainFrame = new GameFrame(socket);
        mainFrame.status(2);
        mainFrame.add(chessBoardComponent);
        mainFrame.setController(controller);
        mainFrame.setChessBoard(chessBoard);
        chessBoard.setGameFrame(mainFrame);
        mainFrame.setCurrentText();
        mainFrame.disablePicture();
        mainFrame.onlineDisable();
        this.controller=controller;
        this.mainframe=mainFrame;
        this.chessboard=chessBoard;

        //mainFrame.setVisible(true);

        turnToPlay(controller.getCurrentPlayer());
        setValidPiece(chessBoard);

        return controller;

    }
    public void setValidPiece(ChessBoard chessboard){
        for (int i=0;i<chessboard.getDimension();i++){
            for (int j=0;j<chessboard.getDimension();j++){
                ChessBoardLocation location=new ChessBoardLocation(i,j);
                if (chessboard.getChessPieceAt(location)!=null) {
                    if (chessboard.getChessPieceAt(location).getColor()==Color.RED){
                        chessboard.getChessPieceAt(location).setInvalid(false);
                    }

                }
            }
        }
    }

    public void readMove(String m){
        ChessBoardLocation src = new ChessBoardLocation(Integer.parseInt(m.substring(0, 2)), Integer.parseInt(m.substring(2, 4)));
        ChessBoardLocation dest = new ChessBoardLocation(Integer.parseInt(m.substring(4, 6)), Integer.parseInt(m.substring(6, 8)));
        try {
            chessboard.moveChessPiece(src, dest);
            if (m.substring(8,11).equals("RED")){
                controller.setCurrentPlayer(Color.GREEN);
            }else {
                controller.setCurrentPlayer(Color.RED);
            }
        }catch (NullPointerException n) {
            setValidPiece(chessboard);
        }
        setValidPiece(chessboard);
        //controller.nextPlayer();
    }
//    class sendMessThread extends Thread{
//        @Override
//        public void run() {
//            super.run();
//
//            Scanner scanner=null;
//            OutputStream os= null;
//            try {
//                scanner=new Scanner(System.in);
//                os= socket.getOutputStream();
//                String in="";
//                do {
//                    in=scanner.next();
//                    os.write((""+in).getBytes());
//                    os.flush();
//                } while (!in.equals("end"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            scanner.close();
//
//            try {
//                if (os != null) {
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }



//    public static void main(String[] args) {
//
//        Client clientTest= null;
//        try {
//            clientTest = new Client(new Scanner(System.in).next());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        clientTest.start();
//    }
}