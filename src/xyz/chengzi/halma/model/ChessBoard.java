package xyz.chengzi.halma.model;


import xyz.chengzi.halma.BGM;
import xyz.chengzi.halma.Online.Sender;
import xyz.chengzi.halma.listener.GameListener;
import xyz.chengzi.halma.listener.Listenable;
import xyz.chengzi.halma.view.ChessBoardComponent;
import xyz.chengzi.halma.view.ChooseMode;
import xyz.chengzi.halma.view.EndGame;
import xyz.chengzi.halma.view.GameFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class ChessBoard implements Listenable<GameListener> {
    BGM music;
    Socket socket;
    String ip;
    boolean isOnline=false;
    public ArrayList<String> moves=new ArrayList<>();
    public ArrayList<String> initialPosition=new ArrayList<>();
    ChessBoardLocation source;
    ChessBoardLocation destiny;
    GameFrame gameFrame;
    private int moveNum1;
    private int moveNum2;
    private int moveNum3;
    private int moveNum4;
    private int moveNum5;
    private int moveNum6;
    private int moveNum7;
    private int moveNum8;

    public boolean abtWin1;
    public boolean abtWin2;
    private boolean abtWin3;
    private boolean abtWin4;
    public boolean abtWin5;
    public boolean abtWin6;
    private boolean abtWin7;
    private boolean abtWin8;
    private String isTru="N";//N: not taken over;G: Green taken over;R: Red taken over

    private boolean isUndoValid = false;
    public BGM abtw = new BGM(new File("abtw.mp3"));
    private List<GameListener> listenerList = new ArrayList<>();
    private Square[][] grid;
    private int dimension;
    private ArrayList<ChessBoardLocation> jumpValidLocation = new ArrayList<>();
    private ArrayList<ChessBoardLocation> simpleMoveValidLocation = new ArrayList<>();
    private ArrayList<ChessBoardLocation> validLocation = new ArrayList<>();
    private ArrayList<ChessBoardLocation> jumpValidLocationCopy = new ArrayList<>();
    //0-single player 1-two players 2-four players 3-eight players
    //4-AI show 5-hole board 6-Challenges
    private int mode;
    private int mode0LetAIWinCounter = 0;
    private boolean isSpecialTimeRed;
    private boolean isSpecialTimeGreen;
    private boolean isEasyAI = false;
    private boolean isMediumAI = false;
    private boolean isHardAI = false;
    private int mode4Difficulty;
    private String name;
    private String winner = "";
    private int jumpTimesCounter;
    private ArrayList<ChessBoardLocation> jumpLocation = new ArrayList<>();
    private final ArrayList<ChessBoardLocation> camp1 = new ArrayList<>();//top left corner
    private final ArrayList<ChessBoardLocation> camp2 = new ArrayList<>();//bottom right corner
    private final ArrayList<ChessBoardLocation> camp3 = new ArrayList<>();//top right corner
    private final ArrayList<ChessBoardLocation> camp4 = new ArrayList<>();//bottom left corner
    private final ArrayList<ChessBoardLocation> camp5 = new ArrayList<>();
    private final ArrayList<ChessBoardLocation> camp6 = new ArrayList<>();
    private final ArrayList<ChessBoardLocation> camp7 = new ArrayList<>();
    private final ArrayList<ChessBoardLocation> camp8 = new ArrayList<>();
    private ChessBoardComponent view;
    private boolean isComplicatedJump = false;
    private boolean isSurrenderEnd = false;
    private boolean started=false;

    public boolean isSurrenderEnd() {
        return isSurrenderEnd;
    }

    public void setSurrenderEnd(boolean surrenderEnd) {
        isSurrenderEnd = surrenderEnd;
    }

    public ChessBoardComponent getView() {
        return view;
    }

    public void setView(ChessBoardComponent view) {
        this.view = view;
    }

    public int getJumpTimesCounter() {
        return jumpTimesCounter;
    }

    public void setJumpTimesCounter(int jumpTimesCounter) {
        this.jumpTimesCounter = jumpTimesCounter;
    }

    public int getMode4Difficulty() {
        return mode4Difficulty;
    }

    public void setMode4Difficulty(int mode4Difficulty) {
        this.mode4Difficulty = mode4Difficulty;
    }

    public String getTru() {
        return isTru;
    }

    public void setTru(String isTru) {
        this.isTru = isTru;
    }

    public boolean isEasyAI() {
        return isEasyAI;
    }

    public void setEasyAI(boolean easyAI) {
        isEasyAI = easyAI;
    }

    public boolean isMediumAI() {
        return isMediumAI;
    }

    public void setMediumAI(boolean mediumAI) {
        isMediumAI = mediumAI;
    }

    public boolean isHardAI() {
        return isHardAI;
    }

    public void setHardAI(boolean hardAI) {
        isHardAI = hardAI;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public int getMoveNum1() {
        return moveNum1;
    }

    public int getMoveNum2() {
        return moveNum2;
    }

    public int getMoveNum3() {
        return moveNum3;
    }

    public int getMoveNum4() {
        return moveNum4;
    }

    public int getMoveNum5() {
        return moveNum5;
    }

    public int getMoveNum6() {
        return moveNum6;
    }

    public int getMoveNum7() {
        return moveNum7;
    }

    public int getMoveNum8() {
        return moveNum8;
    }

    public void setMoveNum1(int moveNum1) {
        this.moveNum1 = moveNum1;
    }

    public void setMoveNum2(int moveNum2) {
        this.moveNum2 = moveNum2;
    }

    public void setMoveNum3(int moveNum3) {
        this.moveNum3 = moveNum3;
    }

    public void setMoveNum4(int moveNum4) {
        this.moveNum4 = moveNum4;
    }

    public void setMoveNum5(int moveNum5) {
        this.moveNum5= moveNum5;
    }

    public void setMoveNum6(int moveNum6) {
        this.moveNum6 = moveNum6;
    }

    public void setMoveNum7(int moveNum7) {
        this.moveNum7 = moveNum7;
    }

    public void setMoveNum8(int moveNum8) {
        this.moveNum8 = moveNum8;
    }


    public ChessBoard(int dimension, int mode) {
        this.grid = new Square[dimension][dimension];
        this.dimension = dimension;
        this.mode = mode;
        if (mode == 0 || mode == 1 || mode == 4 || mode == 5){
            camp1.add(new ChessBoardLocation(0,0));
            camp1.add(new ChessBoardLocation(0,1));
            camp1.add(new ChessBoardLocation(1,0));
            camp1.add(new ChessBoardLocation(0,2));
            camp1.add(new ChessBoardLocation(1,1));
            camp1.add(new ChessBoardLocation(2,0));
            camp1.add(new ChessBoardLocation(0,3));
            camp1.add(new ChessBoardLocation(1,2));
            camp1.add(new ChessBoardLocation(2,1));
            camp1.add(new ChessBoardLocation(3,0));
            camp1.add(new ChessBoardLocation(0,4));
            camp1.add(new ChessBoardLocation(1,3));
            camp1.add(new ChessBoardLocation(2,2));
            camp1.add(new ChessBoardLocation(3,1));
            camp1.add(new ChessBoardLocation(4,0));
            camp1.add(new ChessBoardLocation(1,4));
            camp1.add(new ChessBoardLocation(2,3));
            camp1.add(new ChessBoardLocation(3,2));
            camp1.add(new ChessBoardLocation(4,1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-2));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-3));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-2));
            camp2.add(new ChessBoardLocation(dimension-3,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-4));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-3));
            camp2.add(new ChessBoardLocation(dimension-3,dimension-2));
            camp2.add(new ChessBoardLocation(dimension-4,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-5));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-4));
            camp2.add(new ChessBoardLocation(dimension-3,dimension-3));
            camp2.add(new ChessBoardLocation(dimension-4,dimension-2));
            camp2.add(new ChessBoardLocation(dimension-5,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-5));
            camp2.add(new ChessBoardLocation(dimension-3,dimension-4));
            camp2.add(new ChessBoardLocation(dimension-4,dimension-3));
            camp2.add(new ChessBoardLocation(dimension-5,dimension-2));
        }
        if (mode == 2){
            camp1.add(new ChessBoardLocation(0,0));
            camp1.add(new ChessBoardLocation(0,1));
            camp1.add(new ChessBoardLocation(1,0));
            camp1.add(new ChessBoardLocation(0,2));
            camp1.add(new ChessBoardLocation(1,1));
            camp1.add(new ChessBoardLocation(2,0));
            camp1.add(new ChessBoardLocation(0,3));
            camp1.add(new ChessBoardLocation(1,2));
            camp1.add(new ChessBoardLocation(2,1));
            camp1.add(new ChessBoardLocation(3,0));
            camp1.add(new ChessBoardLocation(1,3));
            camp1.add(new ChessBoardLocation(2,2));
            camp1.add(new ChessBoardLocation(3,1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-2));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-3));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-2));
            camp2.add(new ChessBoardLocation(dimension-3,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-1,dimension-4));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-3));
            camp2.add(new ChessBoardLocation(dimension-3,dimension-2));
            camp2.add(new ChessBoardLocation(dimension-4,dimension-1));
            camp2.add(new ChessBoardLocation(dimension-2,dimension-4));
            camp2.add(new ChessBoardLocation(dimension-3,dimension-3));
            camp2.add(new ChessBoardLocation(dimension-4,dimension-2));
            camp3.add(new ChessBoardLocation(0,dimension-1));
            camp3.add(new ChessBoardLocation(0,dimension-2));
            camp3.add(new ChessBoardLocation(1,dimension-1));
            camp3.add(new ChessBoardLocation(0,dimension-3));
            camp3.add(new ChessBoardLocation(1,dimension-2));
            camp3.add(new ChessBoardLocation(2,dimension-1));
            camp3.add(new ChessBoardLocation(0,dimension-4));
            camp3.add(new ChessBoardLocation(1,dimension-3));
            camp3.add(new ChessBoardLocation(2,dimension-2));
            camp3.add(new ChessBoardLocation(3,dimension-1));
            camp3.add(new ChessBoardLocation(1,dimension-4));
            camp3.add(new ChessBoardLocation(2,dimension-3));
            camp3.add(new ChessBoardLocation(3,dimension-2));
            camp4.add(new ChessBoardLocation(dimension-1,0));
            camp4.add(new ChessBoardLocation(dimension-1,1));
            camp4.add(new ChessBoardLocation(dimension-2,0));
            camp4.add(new ChessBoardLocation(dimension-1,2));
            camp4.add(new ChessBoardLocation(dimension-2,1));
            camp4.add(new ChessBoardLocation(dimension-3,0));
            camp4.add(new ChessBoardLocation(dimension-1,3));
            camp4.add(new ChessBoardLocation(dimension-2,2));
            camp4.add(new ChessBoardLocation(dimension-3,1));
            camp4.add(new ChessBoardLocation(dimension-4,0));
            camp4.add(new ChessBoardLocation(dimension-2,3));
            camp4.add(new ChessBoardLocation(dimension-3,2));
            camp4.add(new ChessBoardLocation(dimension-4,1));
        }
        if (mode == 3) {

            camp1.add(new ChessBoardLocation(0, 1));
            camp1.add(new ChessBoardLocation(0, 2));
            camp1.add(new ChessBoardLocation(0, 3));
            camp1.add(new ChessBoardLocation(1, 2));
            camp1.add(new ChessBoardLocation(1, 3));
            camp1.add(new ChessBoardLocation(2, 3));

            camp3.add(new ChessBoardLocation(1, 0));
            camp3.add(new ChessBoardLocation(2, 0));
            camp3.add(new ChessBoardLocation(3, 0));
            camp3.add(new ChessBoardLocation(2, 1));
            camp3.add(new ChessBoardLocation(3, 1));
            camp3.add(new ChessBoardLocation(3, 2));


            camp2.add(new ChessBoardLocation(dimension - 1, dimension - 2));
            camp2.add(new ChessBoardLocation(dimension - 1, dimension - 3));
            camp2.add(new ChessBoardLocation(dimension - 1, dimension - 4));
            camp2.add(new ChessBoardLocation(dimension - 2, dimension - 3));
            camp2.add(new ChessBoardLocation(dimension - 2, dimension - 4));
            camp2.add(new ChessBoardLocation(dimension - 3, dimension - 4));

            camp4.add(new ChessBoardLocation(dimension - 2, dimension - 1));
            camp4.add(new ChessBoardLocation(dimension - 3, dimension - 1));
            camp4.add(new ChessBoardLocation(dimension - 4, dimension - 1));
            camp4.add(new ChessBoardLocation(dimension - 3, dimension - 2));
            camp4.add(new ChessBoardLocation(dimension - 4, dimension - 2));
            camp4.add(new ChessBoardLocation(dimension - 4, dimension - 3));

            camp5.add(new ChessBoardLocation(0, dimension-2));
            camp5.add(new ChessBoardLocation(0, dimension-3));
            camp5.add(new ChessBoardLocation(0, dimension-4));
            camp5.add(new ChessBoardLocation(1, dimension-3));
            camp5.add(new ChessBoardLocation(1, dimension-4));
            camp5.add(new ChessBoardLocation(2, dimension-4));

            camp8.add(new ChessBoardLocation(1, dimension-1));
            camp8.add(new ChessBoardLocation(2, dimension-1));
            camp8.add(new ChessBoardLocation(3, dimension-1));
            camp8.add(new ChessBoardLocation(2, dimension-2));
            camp8.add(new ChessBoardLocation(3, dimension-2));
            camp8.add(new ChessBoardLocation(3, dimension-3));

            camp7.add(new ChessBoardLocation(dimension-2, 0));
            camp7.add(new ChessBoardLocation(dimension-3, 0));
            camp7.add(new ChessBoardLocation(dimension-4, 0));
            camp7.add(new ChessBoardLocation(dimension-3, 1));
            camp7.add(new ChessBoardLocation(dimension-4, 1));
            camp7.add(new ChessBoardLocation(dimension-4, 2));

            camp6.add(new ChessBoardLocation(dimension-1, 1));
            camp6.add(new ChessBoardLocation(dimension-1, 2));
            camp6.add(new ChessBoardLocation(dimension-1, 3));
            camp6.add(new ChessBoardLocation(dimension-2, 2));
            camp6.add(new ChessBoardLocation(dimension-2, 3));
            camp6.add(new ChessBoardLocation(dimension-3, 3));


        }
        initGrid();
    }

    public void setValidLocation(ArrayList<ChessBoardLocation> validLocation) {
        this.validLocation = validLocation;
    }

    public ArrayList<ChessBoardLocation> getValidLocation() {
        return validLocation;
    }

    public void loadInitialPieces(int row, int col, Color color){
        grid[row][col].setPiece(new ChessPiece(color));
    }

    private void initGrid() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }

    public void placeInitialPieces() {
        if (mode == 0 || mode == 1 || mode == 4 || mode == 5) {
            for (ChessBoardLocation location: camp1){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.RED));
            }
            for (ChessBoardLocation location: camp2){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.GREEN));
            }
//            for (ChessBoardLocation location: camp1){
//                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.GREEN));
//            }
//            grid[5][1].setPiece(new ChessPiece(Color.GREEN));
//            grid[4][1].setPiece(null);
//            grid[10][10].setPiece(new ChessPiece(Color.RED));
            listenerList.forEach(listener -> listener.onChessBoardReload(this));
        }
        else if(mode==2){
            for (ChessBoardLocation location: camp1){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.RED));
            }
            for (ChessBoardLocation location: camp2){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.GREEN));
            }
            for (ChessBoardLocation location: camp3){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.BLUE));
            }
            for (ChessBoardLocation location: camp4){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.ORANGE));
            }
//            for (ChessBoardLocation location: camp4){
//                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.BLUE));
//            }
//            grid[dimension-4][1].setPiece(null);
//            grid[dimension-5][1].setPiece(new ChessPiece(Color.BLUE));
//            grid[1][1].setPiece(new ChessPiece(Color.RED));
//            grid[2][2].setPiece(new ChessPiece(Color.GREEN));
//            grid[3][3].setPiece(new ChessPiece(Color.ORANGE));
            listenerList.forEach(listener -> listener.onChessBoardReload(this));
        }
        else if(mode==3){
            for (ChessBoardLocation location: camp1){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.RED));
            }
            for (ChessBoardLocation location: camp2){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.GREEN));
            }
            for (ChessBoardLocation location: camp3){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.BLUE));
            }
            for (ChessBoardLocation location: camp4){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.ORANGE));
            }
            for (ChessBoardLocation location: camp5){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.CYAN));
            }
            for (ChessBoardLocation location: camp6){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.BLACK));
            }
            for (ChessBoardLocation location: camp7){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.YELLOW));
            }
            for (ChessBoardLocation location: camp8){
                grid[location.getRow()][location.getColumn()].setPiece(new ChessPiece(Color.PINK));
            }
            listenerList.forEach(listener -> listener.onChessBoardReload(this));
        }
    }

    public void clear(){
        int count=0;
        for (int i=0;i<dimension;i++){
            for (int j=0;j<dimension;j++){
                ChessBoardLocation location=new ChessBoardLocation(i,j);
                if (mode == 5) {

                    if (i>=5&&i<=10&&j>=5&&j<=10) {
                        count++;
                      //  System.out.println(count);
                    }else {
                        removeChessPieceAt(location);
                    }

                }else {
                    removeChessPieceAt(location);
                }

            }
        }
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }

    public void recordMove(ChessBoardLocation src,ChessBoardLocation dest){
        String player="";
        if (getChessPieceAt(src).getColor().equals(Color.RED)){
            player="RED";
        }
        if (getChessPieceAt(src).getColor().equals(Color.GREEN)){
            player="GREEN";
        }
        if (getChessPieceAt(src).getColor().equals(Color.ORANGE)){
            player="ORANGE";
        }
        if (getChessPieceAt(src).getColor().equals(Color.BLUE)){
            player="BLUE";
        }
        if (getChessPieceAt(src).getColor().equals(Color.CYAN)){
            player="CYAN";
        }
        if (getChessPieceAt(src).getColor().equals(Color.PINK)){
            player="PINK";
        }
        if (getChessPieceAt(src).getColor().equals(Color.BLACK)){
            player="BLACK";
        }
        if (getChessPieceAt(src).getColor().equals(Color.YELLOW)){
            player="YELLOW";
        }
        moves.add(src+""+dest+""+player);
    }

    public void replayMove(ChessBoardLocation src,ChessBoardLocation dest){
        setChessPieceAt(dest, removeChessPieceAt(src));
    }
    public void onlineMove(ChessBoardLocation src,ChessBoardLocation dest){
        onlineSetChessPieceAt(dest, onlineRemoveChessPieceAt(src));
        //recordMove(src,dest);
    }

    private void onlineSetChessPieceAt(ChessBoardLocation dest, ChessPiece removeChessPieceAt) {
        getGridAt(dest).setPiece(removeChessPieceAt);
    }
    public ChessPiece onlineRemoveChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        return piece;
    }

    public void recordInitialPosition(){
        if (moves.isEmpty()){
            String info="";
            for (int i=0;i<getDimension();i++){
                for (int j=0;j<getDimension();j++){
                    ChessBoardLocation location=new ChessBoardLocation(i,j);
                    if (getChessPieceAt(location)!=null) {
                        if (getChessPieceAt(location).getColor()==Color.RED){
                            info=location.toString()+"RED";
                        }
                        if (getChessPieceAt(location).getColor()==Color.GREEN){
                            info=location.toString()+"GREEN";
                        }
                        if (getChessPieceAt(location).getColor()==Color.ORANGE){
                            info=location.toString()+"ORANGE";
                        }
                        if (getChessPieceAt(location).getColor()==Color.BLUE){
                            info=location.toString()+"BLUE";
                        }
                        initialPosition.add(info);
                    }else {

                        info=location+"empty";
                        initialPosition.add(info);
                    }
                }
            }
        }
    }

    class ShowMove extends Thread{
        ArrayList<ChessBoardLocation> path=new ArrayList<>();
        ChessBoardLocation src;
        ChessBoardLocation dest;
        ShowMove(ArrayList<ChessBoardLocation> path,ChessBoardLocation src, ChessBoardLocation dest){
            this.path=path;
            this.dest=dest;
            this.src=src;
        }
        @Override
        public void run() {
            super.run();
            if (getPath(src,dest).size() == 2) {
                moveChessPiece(src,dest);
            } else {
                isComplicatedJump = true;
                addMoveNum(src);
                destiny = dest;
                source = src;
                recordMove(src,dest);
                for (int i = 0; i < path.size()-1; i++){
                    try {
                        if (i+1==path.size()) {
                            moveChessPiece(path.get(i), path.get(i + 1));
//                            BGM chess = new BGM(new File("chess.mp3"));
//                            chess.start();
                        }else{
                        Thread.sleep(300);
                        moveChessPiece(path.get(i), path.get(i + 1));
//                        BGM chess = new BGM(new File("chess.mp3"));
//                        chess.start();
                        view.repaint();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (isEnd()){
                    endGame();
                }
            }
            gameFrame.setStartTime(System.currentTimeMillis());
            isComplicatedJump = false;
        }
    }

    public void complicatedMoveChessPiece(ChessBoardLocation src, ChessBoardLocation dest){
        ArrayList<ChessBoardLocation> path = getPath(src,dest);
        ShowMove sm=new ShowMove(path,src,dest);
        sm.start();
        recordInitialPosition();
    }

    public void moveChessPiece(ChessBoardLocation src, ChessBoardLocation dest) {
        Color color = getChessPieceAt(src).getColor();
        if (!isOnline) {
            BGM chess = new BGM(new File("chess.mp3"));
            chess.start();
            if (!isValidMove(src, dest)) {
                throw new IllegalArgumentException("Illegal halma move");
            }
            if (!isComplicatedJump) {
                recordMove(src, dest);
            }
            setChessPieceAt(dest,removeChessPieceAt(src));
            if (!started) {
                gameFrame.thread.start();
                started=true;
            }
            if (getMode() == 2) {
                if (getChessPieceAt(dest).getColor().equals(Color.RED)) {

                    gameFrame.cp.setText("BLUE");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.GREEN)) {
                    gameFrame.cp.setText("ORANGE");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.BLUE)) {
                    gameFrame.cp.setText("GREEN");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.ORANGE)) {
                    gameFrame.cp.setText("RED");
                }
            } else if (getMode() == 0 || getMode() == 1 || getMode() == 4 || getMode() == 5 || getMode() == 6){
                if (getChessPieceAt(dest).getColor().equals(Color.RED)) {
                    gameFrame.cp.setText("GREEN");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.GREEN)) {
                    gameFrame.cp.setText("RED");
                }
            }else {
                if (getChessPieceAt(dest).getColor().equals(Color.RED)) {
                    gameFrame.cp.setText("CYAN");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.GREEN)) {
                    gameFrame.cp.setText("BLACK");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.BLUE)) {
                    gameFrame.cp.setText("RED");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.ORANGE)) {
                    gameFrame.cp.setText("GREEN");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.CYAN)) {
                    gameFrame.cp.setText("PINK");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.BLACK)) {
                    gameFrame.cp.setText("YELLOW");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.YELLOW)) {
                    gameFrame.cp.setText("BLUE");
                }
                if (getChessPieceAt(dest).getColor().equals(Color.PINK)) {
                    gameFrame.cp.setText("ORANGE");
                }
            }
            this.destiny = dest;
            this.source = src;
            if (!isComplicatedJump){
                addMoveNum(dest);
            }
//            addMoveNum(dest);
            if(isTimeToRemind()){
                JOptionPane.showMessageDialog(null, "Please move your chess pieces out of your own camp in 5 moves, or you will lose the game!");
            }
            if (isEnd()) {
                if (!isComplicatedJump) {
                    endGame();
                }
            } else {
                if ((mode == 0 && !color.equals(Color.RED)|| mode == 1)) {
                    if (aboutToWin().charAt(0) == 'T') {
                        if (!abtWin1 && aboutToWin().charAt(1) == 'R') {
                            JOptionPane.showMessageDialog(null, "Player RED is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            GameFrame.bgm.stop();
                            abtw.start();
                            abtWin1 = true;
                        }
                        if (!abtWin2 && aboutToWin().charAt(1) == 'G') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player GREEN is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin2 = true;
                        }
                    }
                }
                if (mode == 2) {
                    if (aboutToWin().charAt(0) == 'T') {
                        if (!abtWin1 && aboutToWin().charAt(1) == 'R') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player RED is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin1 = true;
                        }
                        if (!abtWin2 && aboutToWin().charAt(1) == 'G') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player GREEN is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin2 = true;
                        }
                        if (!abtWin3 && aboutToWin().charAt(1) == 'B') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player BLUE is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin3 = true;
                        }
                        if (!abtWin4 && aboutToWin().charAt(1) == 'O') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player ORANGE is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin4 = true;
                        }
                    }
                }
                if (mode == 3) {
                    if (aboutToWin().charAt(0) == 'T') {
                        if (!abtWin1 && aboutToWin().charAt(1) == 'R') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player RED is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin1 = true;
                        }
                        if (!abtWin2 && aboutToWin().charAt(1) == 'G') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player GREEN is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin2 = true;
                        }
                        if (!abtWin3 && aboutToWin().charAt(1) == 'B') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player BLUE is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin3 = true;
                        }
                        if (!abtWin4 && aboutToWin().charAt(1) == 'O') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player ORANGE is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin4 = true;
                        }
                        if (!abtWin5 && aboutToWin().charAt(1) == 'C') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player CYAN is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin5 = true;
                        }
                        if (!abtWin6 && aboutToWin().charAt(1) == 'L') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player BLACK is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin6 = true;
                        }
                        if (!abtWin7 && aboutToWin().charAt(1) == 'Y') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player YELLOW is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();
                            abtWin7 = true;
                        }
                        if (!abtWin8 && aboutToWin().charAt(1) == 'P') {
                            GameFrame.bgm.stop();
                            JOptionPane.showMessageDialog(null, "Player PINK is about to win! BE CAREFUL!");
                            //BGM abtw = new BGM(new File("abtw.mp3"));
                            abtw.start();

                            abtWin8 = true;
                        }
                    }
                }
            }
            if (!isTru.equals("N")){
                setMode(0);
            }
        }else {
            BGM chess = new BGM(new File("chess.mp3"));
            chess.start();
            recordInitialPosition();
            recordMove(src, dest);
            setChessPieceAt(dest, removeChessPieceAt(src));

            Sender sender=new Sender(this,socket);
            sender.start();
            sender.run();
            //
            //sender.stop();
        }
    }

    private void addMoveNum(ChessBoardLocation dest) {
        if (getChessPieceAt(dest).getColor().equals(Color.RED)) {
            moveNum1++;
        }
        if (getChessPieceAt(dest).getColor().equals(Color.GREEN)) {
            moveNum2++;
        }
        if (getChessPieceAt(dest).getColor().equals(Color.BLUE)) {
            moveNum3++;
        }
        if (getChessPieceAt(dest).getColor().equals(Color.ORANGE)) {
            moveNum4++;
        }
        if (getChessPieceAt(dest).getColor().equals(Color.CYAN)) {
            moveNum5++;
        }
        if (getChessPieceAt(dest).getColor().equals(Color.BLACK)) {
            moveNum6++;
        }
        if (getChessPieceAt(dest).getColor().equals(Color.YELLOW)) {
            moveNum7++;
        }
        if (getChessPieceAt(dest).getColor().equals(Color.PINK)) {
            moveNum8++;
        }
    }

    public int getDimension() {
        return dimension;
    }

    public boolean isValidMove(ChessBoardLocation src, ChessBoardLocation dest) {
        jumpValidLocation.clear();
        simpleMoveValidLocation.clear();
        validLocation.clear();

        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            JOptionPane.showMessageDialog(null, "You cannot move that way!");
            return false;
        }
        Color color = getChessPieceAt(src).getColor();
        if (getValidLocation(src, color).contains(dest)) return true;
        JOptionPane.showMessageDialog(null, "You cannot move that way!");
        return false;
    }

    public void endGame(){
        if (isOnline){
            JOptionPane.showMessageDialog(null,"Game over!");
            gameFrame.setVisible(false);
            ChooseMode cm= null;
            try {
                cm = new ChooseMode();
                cm.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {

            boolean isNotNormalEnd = false;
            abtw.stop();
            try {
               GameFrame.bgm.stop();
            }catch (NullPointerException i){
                System.out.println("Already stopped");
            }
            gameFrame.stopTimer();
            int move = 0;
            final String[] winner = {""};
            if (mode != 2 && mode != 3) {
                if (gameFrame.isOverTimeEnd()){
                    winner[0] = "DRAW";
                    isNotNormalEnd = true;
                }
                if (isSurrenderEnd){
                    if (gameFrame.getSurrenderColor() == Color.RED){
                        winner[0] = "REDLOSE";
                        isNotNormalEnd = true;
                    }
                    if (gameFrame.getSurrenderColor() == Color.GREEN){
                        winner[0] = "GREENLOSE";
                        isNotNormalEnd = true;
                    }
                    if (gameFrame.getSurrenderColor() == Color.ORANGE){
                        winner[0] = "ORANGELOSE";
                        isNotNormalEnd = true;
                    }
                    if (gameFrame.getSurrenderColor() == Color.BLUE){
                        winner[0] = "BLUELOSE";
                        isNotNormalEnd = true;
                    }
                }
                if (isPlayer1Win()) {
                    winner[0] = "RED";
                    move = moveNum1;
                }
                if (isPlayer2Win()) {
                    winner[0] = "GREEN";
                    move = moveNum2;
                }
                if (isPlayer1Lose2p()) {
                    winner[0] = "REDLOSE";
                    isNotNormalEnd = true;
                }
                if (isPlayer2Lose2p()) {
                    winner[0] = "GREENLOSE";
                    isNotNormalEnd = true;
                }
            } else if (mode == 2) {
                if (isSurrenderEnd){
                    if (gameFrame.getSurrenderColor() == Color.RED){
                        winner[0] = "REDLOSE";
                        isNotNormalEnd = true;
                    }
                    if (gameFrame.getSurrenderColor() == Color.GREEN){
                        winner[0] = "GREENLOSE";
                        isNotNormalEnd = true;
                    }
                    if (gameFrame.getSurrenderColor() == Color.ORANGE){
                        winner[0] = "ORANGELOSE";
                        isNotNormalEnd = true;
                    }
                    if (gameFrame.getSurrenderColor() == Color.BLUE){
                        winner[0] = "BLUELOSE";
                        isNotNormalEnd = true;
                    }
                }
                if (isPlayer1Win4p()) {
                    winner[0] = "RED";
                    move = moveNum1;
                }
                if (isPlayer2Win4p()) {
                    winner[0] = "GREEN";
                    move = moveNum2;
                }
                if (isPlayer3Win4p()) {
                    winner[0] = "BLUE";
                    move = moveNum3;
                }
                if (isPlayer4Win4p()) {
                    winner[0] = "ORANGE";
                    move = moveNum4;
                }
                if (isPlayer1Lose4p()) {
                    winner[0] = "REDLOSE";
                    isNotNormalEnd = true;
                }
                if (isPlayer2Lose4p()) {
                    winner[0] = "GREENLOSE";
                    isNotNormalEnd = true;
                }
                if (isPlayer3Lose4p()) {
                    winner[0] = "BLUELOSE";
                    isNotNormalEnd = true;
                }
                if (isPlayer4Lose4p()) {
                    winner[0] = "ORANGELOSE";
                    isNotNormalEnd = true;
                }
            } else if (mode == 3) {
                if (isPlayer1Win8p()) {
                    winner[0] = "RED";
                    move = moveNum1;
                }
                if (isPlayer2Win8p()) {
                    winner[0] = "GREEN";
                    move = moveNum2;
                }
                if (isPlayer3Win8p()) {
                    winner[0] = "BLUE";
                    move = moveNum3;
                }
                if (isPlayer4Win8p()) {
                    winner[0] = "ORANGE";
                    move = moveNum4;
                }
                if (isPlayer5Win8p()) {
                    winner[0] = "CYAN";
                    move = moveNum5;
                }
                if (isPlayer6Win8p()) {
                    winner[0] = "BLACK";
                    move = moveNum6;
                }
                if (isPlayer7Win8p()) {
                    winner[0] = "YELLOW";
                    move = moveNum7;
                }
                if (isPlayer8Win8p()) {
                    winner[0] = "PINK";
                    move = moveNum8;
                }
            }
            if (((mode != 0 || !winner[0].equals("RED")) && mode != 4) && !isNotNormalEnd) {
                JFrame frame = new JFrame();
                JTextField inputName = new JTextField();
                JLabel info = new JLabel("Your name will be remembered in ranking!");
                JButton ok = new JButton("OK");

                frame.setSize(300, 200);
                inputName.setFont(new Font(null, Font.PLAIN, 14));
                info.setFont(new Font(null, Font.PLAIN, 12));
                ok.setFont(new Font(null, Font.PLAIN, 14));

                info.setPreferredSize(new Dimension(250, 40));
                inputName.setPreferredSize(new Dimension(210, 35));
                ok.setPreferredSize(new Dimension(80, 30));

                JPanel panel = new JPanel();
                FlowLayout layout = new FlowLayout();
                panel.setLayout(layout);
                layout.setVgap(10);
                layout.setHgap(5);

                panel.add(info);
                panel.add(inputName);
                panel.add(ok);
                frame.add(panel);
                frame.setLocationRelativeTo(null); // Center the window
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                int finalMove = move;
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String text;
                        text = inputName.getText();
                 //       System.out.println(1);
                        if (text.length() > 6) {
                            JOptionPane.showMessageDialog(null, "The name should be less than 6 letters.");
                       //     System.out.println(2);
                            frame.setVisible(false);
                            endGame();
                            return;
                        } else if (text.equals("")) {
                            JOptionPane.showMessageDialog(null, "The name can not be null.");
                        //    System.out.println(2);
                            frame.setVisible(false);
                            endGame();
                            return;
                        } else {
                            name = text;
                            frame.setVisible(false);
                        }
                        if (name.length() == 1) name = name.concat("     ");
                        if (name.length() == 2) name = name.concat("    ");
                        if (name.length() == 3) name = name.concat("   ");
                        if (name.length() == 4) name = name.concat("  ");
                        if (name.length() == 5) name = name.concat(" ");

                        BufferedWriter writer = null;
                        try {
                            writer = new BufferedWriter(new FileWriter("rank.txt", true));
                            if (finalMove < 10) {
                                writer.write(name + "000" + finalMove + "" + LocalDateTime.now() + "\r\n");
                            } else if (finalMove < 100) {
                                writer.write(name + "00" + finalMove + "" + LocalDateTime.now() + "\r\n");
                            } else if (finalMove < 1000) {
                                writer.write(name + "0" + finalMove + "" + LocalDateTime.now() + "\r\n");
                            }
                            writer.flush();
                            writer.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        if (winner[0].equals("RED   ")) {
                            winner[0] = "RED";
                        }
                        if (winner[0].equals("BLUE  ")) {
                            winner[0] = "BLUE";
                        }
                        if (winner[0].equals("GREEN ")) {
                            winner[0] = "GREEN";
                        }
                        EndGame endGame = new EndGame(winner[0], finalMove, gameFrame, moves, getMode(), initialPosition);
                        endGame.setVisible(true);
                    }
                });
            } else if (mode == 0 && winner[0].equals("RED")) {
                try {
                    name = "AI    ";
                    BufferedReader reader = new BufferedReader(new FileReader("rank.txt"));
                    BufferedWriter writer = new BufferedWriter(new FileWriter("rank.txt", true));
                    if (move < 10) {
                        writer.write(name + "000" + move + "" + LocalDateTime.now() + "\r\n");
                    } else if (move < 100) {
                        writer.write(name + "00" + move + "" + LocalDateTime.now() + "\r\n");
                    } else if (move < 1000) {
                        writer.write(name + "0" + move + "" + LocalDateTime.now() + "\r\n");
                    }
                    writer.flush();
                    writer.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                winner[0] = "RED";
                EndGame endGame = new EndGame(winner[0], move, gameFrame, moves, getMode(), initialPosition);
                endGame.setVisible(true);
            } else {
                EndGame endGame = new EndGame(winner[0], move, gameFrame, moves, getMode(), initialPosition);
                endGame.setVisible(true);
            }
        }
    }


    private String aboutToWin(){
        if (mode==0||mode==1) {
            int camp1G=0;
            int camp2R=0;
            for (ChessBoardLocation location : camp1) {
                if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.GREEN)) {
                    camp1G++;
                }
            }
            for (ChessBoardLocation location : camp2) {
                if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.RED)) {
                    camp2R++;
                }
            }
            if (camp1G >= 17&& camp2R < 17){
                return "TG";
            }
            if (camp2R>=17 && camp1G < 17){
                return "TR";
            }
            return "F";
        }else {
            if (mode == 2){
                int camp1G=0;
                int camp2R=0;
                int camp3O=0;
                int camp4B=0;
                for (ChessBoardLocation location : camp1) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.GREEN)) {
                        camp1G++;
                    }
                }
                for (ChessBoardLocation location : camp2) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.RED)) {
                        camp2R++;
                    }
                }
                for (ChessBoardLocation location : camp3) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.ORANGE)) {
                        camp3O++;
                    }
                }
                for (ChessBoardLocation location : camp4) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.BLUE)) {
                        camp4B++;
                    }
                }
                if (camp1G > 11 && !(camp2R > 11) && !(camp3O > 11) && !(camp4B > 11)){
                    return "TG";
                }
                if (!(camp1G > 11) && camp2R > 11 && !(camp3O > 11) && !(camp4B > 11)){
                    return "TR";
                }
                if (!(camp1G > 11) && !(camp2R > 11) && camp3O > 11 && !(camp4B > 11)){
                    return "TO";
                }
                if (!(camp1G > 11) && !(camp2R > 11) && !(camp3O > 11) && camp4B > 11){
                    return "TB";
                }
                return "F";
            }
            if (mode == 3){
                int camp1G=0;
                int camp2R=0;
                int camp3O=0;
                int camp4B=0;
                int camp5L=0;
                int camp6C=0;
                int camp7P=0;
                int camp8Y=0;
                for (ChessBoardLocation location : camp1) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.GREEN)) {
                        camp1G++;
                    }
                }
                for (ChessBoardLocation location : camp2) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.RED)) {
                        camp2R++;
                    }
                }
                for (ChessBoardLocation location : camp3) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.ORANGE)) {
                        camp3O++;
                    }
                }
                for (ChessBoardLocation location : camp4) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.BLUE)) {
                        camp4B++;
                    }
                }
                for (ChessBoardLocation location : camp5) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.BLACK)) {
                        camp5L++;
                    }
                }
                for (ChessBoardLocation location : camp6) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.CYAN)) {
                        camp6C++;
                    }
                }
                for (ChessBoardLocation location : camp7) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.PINK)) {
                        camp7P++;
                    }
                }
                for (ChessBoardLocation location : camp8) {
                    if (getChessPieceAt(location) != null && getChessPieceAt(location).getColor().equals(Color.YELLOW)) {
                        camp8Y++;
                    }
                }
                if (camp1G > 4 && !(camp2R > 4) && !(camp3O > 4) && !(camp4B > 4) && !(camp5L > 4) && !(camp6C > 4) && !(camp7P > 4) && !(camp8Y > 4)){
                    return "TG";
                }
                if (!(camp1G > 4) && camp2R > 4 && !(camp3O > 4) && !(camp4B > 4) && !(camp5L > 4) && !(camp6C > 4) && !(camp7P > 4) && !(camp8Y > 4)){
                    return "TR";
                }
                if (!(camp1G > 4) && !(camp2R > 4) && camp3O > 4 && !(camp4B > 4) && !(camp5L > 4) && !(camp6C > 4) && !(camp7P > 4) && !(camp8Y > 4)){
                    return "TO";
                }
                if (!(camp1G > 4) && !(camp2R > 4) && !(camp3O > 4) && camp4B > 4 && !(camp5L > 4) && !(camp6C > 4) && !(camp7P > 4) && !(camp8Y > 4)){
                    return "TB";
                }

                if (!(camp1G > 4) && !(camp2R > 4) && !(camp3O > 4) && !(camp4B > 4) && camp5L > 4 && !(camp6C > 4) && !(camp7P > 4) && !(camp8Y > 4)){
                    return "TL";
                }
                if (!(camp1G > 4) && !(camp2R > 4) && !(camp3O > 4) && !(camp4B > 4) && !(camp5L > 4) && camp6C > 4 && !(camp7P > 4) && !(camp8Y > 4)){
                    return "TC";
                }
                if (!(camp1G > 4) && !(camp2R > 4) && !(camp3O > 4) && !(camp4B > 4) && !(camp5L > 4) && !(camp6C > 4) && camp7P > 4 && !(camp8Y > 4)){
                    return "TP";
                }
                if (!(camp1G > 4) && !(camp2R > 4) && !(camp3O > 4) && !(camp4B > 4) && !(camp5L > 4) && !(camp6C > 4) && !(camp7P > 4) && camp8Y > 4){
                    return "TY";
                }
                return "F";
            }

            return "";
        }
    }


    public ArrayList<ChessBoardLocation> getRedChessPieceLocation(){
        ArrayList<ChessBoardLocation> redLocationList = new ArrayList<>();
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                if (getChessPieceAt(new ChessBoardLocation(i,j)) != null) {
                    if (getChessPieceAt(new ChessBoardLocation(i, j)).getColor() == Color.RED) {
                        redLocationList.add(new ChessBoardLocation(i, j));
                    }
                }
            }
        }
        return redLocationList;
    }

    public ArrayList<ChessBoardLocation> getGreenChessPieceLocation(){
        ArrayList<ChessBoardLocation> redLocationList = new ArrayList<>();
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                if (getChessPieceAt(new ChessBoardLocation(i,j)) != null) {
                    if (getChessPieceAt(new ChessBoardLocation(i, j)).getColor() == Color.GREEN) {
                        redLocationList.add(new ChessBoardLocation(i, j));
                    }
                }
            }
        }
        return redLocationList;
    }

    public void automaticallyMoveRed(){
        if (isSpecialTimeRed) {
            letAIWinTheGameRed();
            recordInitialPosition();
            return;
        }
        if (isHardAI) {
            double greatestDistance = 0;
            ArrayList<ChessBoardLocation> bestSrc = new ArrayList<>();
            ArrayList<ChessBoardLocation> bestDest = new ArrayList<>();
            for (ChessBoardLocation srcLocation : getRedChessPieceLocation()) {
                for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.RED)) {
                    int x = destLocation.getRow() - srcLocation.getRow();
                    int y = destLocation.getColumn() - srcLocation.getColumn();
                    double distance = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
                    if (x >= 0 && y >= 0 && distance > greatestDistance) {
                        bestSrc.clear();
                        bestDest.clear();
                        bestSrc.add(srcLocation);
                        bestDest.add(destLocation);
                        greatestDistance = distance;
                    }
                    if (x >= 0 && y >= 0 && distance == greatestDistance) {
                        bestSrc.add(srcLocation);
                        bestDest.add(destLocation);
                    }
                }
            }
            int randomNumber = (int) (Math.random() * bestSrc.size());
            for (ChessBoardLocation location : bestSrc){
                if (camp1.contains(location)){
                    randomNumber = bestSrc.indexOf(location);
                }
            }
            if (bestSrc.size() > 0) {
                moveChessPiece(bestSrc.get(randomNumber), bestDest.get(randomNumber));
            }else {
                isSpecialTimeRed = true;
                letAIWinTheGameRed();
            }
        }
        if (isEasyAI){
            boolean moveFromCamp = false;
            ArrayList<ChessBoardLocation> possibleSrc = new ArrayList<>();
            ArrayList<ChessBoardLocation> possibleDest = new ArrayList<>();
            for (ChessBoardLocation srcLocation : getRedChessPieceLocation()) {
                for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.RED)) {
                    int x = destLocation.getRow() - srcLocation.getRow();
                    int y = destLocation.getColumn() - srcLocation.getColumn();
                    if (x >= 0 && y >= 0) {
                        boolean campChessPieces = false;
                        for (ChessBoardLocation location: camp1){
                            if (getChessPieceAt(location) != null) {
                                if (getChessPieceAt(location).getColor().equals(Color.RED)) {
                                    campChessPieces = true;
                                }
                            }
                        }
                        if (!campChessPieces) {
                            possibleSrc.add(srcLocation);
                            possibleDest.add(destLocation);
                        }
                        else {
                            moveFromCamp = true;
                            if (camp1.contains(srcLocation)) {
                                possibleSrc.add(srcLocation);
                                possibleDest.add(destLocation);
                            }
                        }
                    }
                }
            }
            int randomNumber = (int) (Math.random() * possibleSrc.size());
            if (possibleSrc.size() > 0) {
                moveChessPiece(possibleSrc.get(randomNumber), possibleDest.get(randomNumber));
            }else {
                if (moveFromCamp){
                    for (ChessBoardLocation srcLocation : getRedChessPieceLocation()) {
                        for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.RED)) {
                            int x = destLocation.getRow() - srcLocation.getRow();
                            int y = destLocation.getColumn() - srcLocation.getColumn();
                            if (x >= 0 && y >= 0) {
                                possibleSrc.add(srcLocation);
                                possibleDest.add(destLocation);
                            }
                        }
                    }
                    randomNumber = (int) (Math.random() * possibleSrc.size());
                    if (possibleSrc.size() > 0) {
                        moveChessPiece(possibleSrc.get(randomNumber), possibleDest.get(randomNumber));
                    }
                    return;
                }
                isSpecialTimeRed = true;
                letAIWinTheGameRed();
            }
        }
        if (isMediumAI) {
            boolean moveFromCamp = false;
            ArrayList<ChessBoardLocation> possibleSrc = new ArrayList<>();
            ArrayList<ChessBoardLocation> possibleDest = new ArrayList<>();
            for (ChessBoardLocation srcLocation : getRedChessPieceLocation()) {
                for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.RED)) {
                    int x = destLocation.getRow() - srcLocation.getRow();
                    int y = destLocation.getColumn() - srcLocation.getColumn();
                    if (x >= 0 && y >= 0) {
                        boolean campChessPieces = false;
                        for (ChessBoardLocation location: camp1){
                            if (getChessPieceAt(location) != null) {
                                if (getChessPieceAt(location).getColor().equals(Color.RED)) {
                                    campChessPieces = true;
                                }
                            }
                        }
                        if (!campChessPieces) {
                            possibleSrc.add(srcLocation);
                            possibleDest.add(destLocation);
                        }
                        else {
                            moveFromCamp = true;
                            if (camp1.contains(srcLocation)) {
                                possibleSrc.add(srcLocation);
                                possibleDest.add(destLocation);
                            }
                        }
                    }
                }
            }
            int randomNumber1 = (int) (Math.random() * possibleSrc.size());
            int randomNumber2 = (int) (Math.random() * possibleSrc.size());
            int randomNumber3 = (int) (Math.random() * possibleSrc.size());
            if (possibleSrc.size() > 0) {
                if (randomNumber1 == randomNumber2 && randomNumber2 == randomNumber3) {
                    moveChessPiece(possibleSrc.get(randomNumber1), possibleDest.get(randomNumber1));
                }
                else {
                    int x1 = possibleDest.get(randomNumber1).getRow() - possibleSrc.get(randomNumber1).getRow();
                    int y1 = possibleDest.get(randomNumber1).getColumn() - possibleSrc.get(randomNumber1).getColumn();
                    double distance1 = Math.pow(Math.pow(x1, 2) + Math.pow(y1, 2), 0.5);
                    int x2 = possibleDest.get(randomNumber2).getRow() - possibleSrc.get(randomNumber2).getRow();
                    int y2 = possibleDest.get(randomNumber2).getColumn() - possibleSrc.get(randomNumber2).getColumn();
                    double distance2 = Math.pow(Math.pow(x2, 2) + Math.pow(y2, 2), 0.5);
                    int x3 = possibleDest.get(randomNumber3).getRow() - possibleSrc.get(randomNumber3).getRow();
                    int y3 = possibleDest.get(randomNumber3).getColumn() - possibleSrc.get(randomNumber3).getColumn();
                    double distance3 = Math.pow(Math.pow(x3, 2) + Math.pow(y3, 2), 0.5);
                    if (distance1 > distance2 && distance1 > distance3){
                        moveChessPiece(possibleSrc.get(randomNumber1), possibleDest.get(randomNumber1));
                    }
                    else if (distance2 > distance1 && distance2 > distance3){
                        moveChessPiece(possibleSrc.get(randomNumber2), possibleDest.get(randomNumber2));
                    }
                    else if (distance3 > distance1 && distance3 > distance2){
                        moveChessPiece(possibleSrc.get(randomNumber3), possibleDest.get(randomNumber3));
                    }
                    else {
                        if (Math.random() > (double)(2/3)){
                            moveChessPiece(possibleSrc.get(randomNumber1), possibleDest.get(randomNumber1));
                        }
                        else if (Math.random() > (double)(1/3)) {
                            moveChessPiece(possibleSrc.get(randomNumber2), possibleDest.get(randomNumber2));
                        }
                        else {
                            moveChessPiece(possibleSrc.get(randomNumber3), possibleDest.get(randomNumber3));
                        }
                    }
                }
            }else {
                if (moveFromCamp){
                    for (ChessBoardLocation srcLocation : getRedChessPieceLocation()) {
                        for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.RED)) {
                            int x = destLocation.getRow() - srcLocation.getRow();
                            int y = destLocation.getColumn() - srcLocation.getColumn();
                            if (x >= 0 && y >= 0) {
                                possibleSrc.add(srcLocation);
                                possibleDest.add(destLocation);
                            }
                        }
                    }
                    randomNumber1 = (int) (Math.random() * possibleSrc.size());
                    if (possibleSrc.size() > 0) {
                        moveChessPiece(possibleSrc.get(randomNumber1), possibleDest.get(randomNumber1));
                    }
                    return;
                }
                isSpecialTimeRed = true;
                letAIWinTheGameRed();
            }
        }
        recordInitialPosition();
        gameFrame.setStartTime(System.currentTimeMillis());
    }

    public void automaticallyMoveGreen(){
        if (isSpecialTimeGreen) {
            letAIWinTheGameGreen();
            recordInitialPosition();
            return;
        }
        if (isHardAI) {
            double greatestDistance = 0;
            ArrayList<ChessBoardLocation> bestSrc = new ArrayList<>();
            ArrayList<ChessBoardLocation> bestDest = new ArrayList<>();
            for (ChessBoardLocation srcLocation : getGreenChessPieceLocation()) {
                for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.GREEN)) {
                    int x = destLocation.getRow() - srcLocation.getRow();
                    int y = destLocation.getColumn() - srcLocation.getColumn();
                    double distance = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
                    if (x <= 0 && y <= 0 && distance > greatestDistance) {
                        bestSrc.clear();
                        bestDest.clear();
                        bestSrc.add(srcLocation);
                        bestDest.add(destLocation);
                        greatestDistance = distance;
                    }
                    if (x <= 0 && y <= 0 && distance == greatestDistance) {
                        bestSrc.add(srcLocation);
                        bestDest.add(destLocation);
                    }
                }
            }
            int randomNumber = (int) (Math.random() * bestSrc.size());
            for (ChessBoardLocation location : bestSrc){
                if (camp2.contains(location)){
                    randomNumber = bestSrc.indexOf(location);
                }
            }
            if (bestSrc.size() > 0) {
                moveChessPiece(bestSrc.get(randomNumber), bestDest.get(randomNumber));
            }else {
                isSpecialTimeGreen = true;
                letAIWinTheGameGreen();
            }
        }
        if (isEasyAI){
            boolean moveFromCamp = false;
            ArrayList<ChessBoardLocation> possibleSrc = new ArrayList<>();
            ArrayList<ChessBoardLocation> possibleDest = new ArrayList<>();
            for (ChessBoardLocation srcLocation : getGreenChessPieceLocation()) {
                for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.GREEN)) {
                    int x = destLocation.getRow() - srcLocation.getRow();
                    int y = destLocation.getColumn() - srcLocation.getColumn();
                    if (x <= 0 && y <= 0) {
                        boolean campChessPieces = false;
                        for (ChessBoardLocation location: camp2){
                            if (getChessPieceAt(location) != null) {
                                if (getChessPieceAt(location).getColor().equals(Color.GREEN)) {
                                    campChessPieces = true;
                                }
                            }
                        }
                        if (!campChessPieces) {
                            possibleSrc.add(srcLocation);
                            possibleDest.add(destLocation);
                        }
                        else {
                            moveFromCamp = true;
                            if (camp2.contains(srcLocation)) {
                                possibleSrc.add(srcLocation);
                                possibleDest.add(destLocation);
                            }
                        }
                    }
                }
            }
            int randomNumber = (int) (Math.random() * possibleSrc.size());
            if (possibleSrc.size() > 0) {
                moveChessPiece(possibleSrc.get(randomNumber), possibleDest.get(randomNumber));
            }else {
                if (moveFromCamp){
                    for (ChessBoardLocation srcLocation : getGreenChessPieceLocation()) {
                        for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.GREEN)) {
                            int x = destLocation.getRow() - srcLocation.getRow();
                            int y = destLocation.getColumn() - srcLocation.getColumn();
                            if (x <= 0 && y <= 0) {
                                possibleSrc.add(srcLocation);
                                possibleDest.add(destLocation);
                            }
                        }
                    }
                    randomNumber = (int) (Math.random() * possibleSrc.size());
                    if (possibleSrc.size() > 0) {
                        moveChessPiece(possibleSrc.get(randomNumber), possibleDest.get(randomNumber));
                    }
                    return;
                }
                isSpecialTimeGreen = true;
                letAIWinTheGameGreen();
            }
        }
        if (isMediumAI) {
            boolean moveFromCamp = false;
            ArrayList<ChessBoardLocation> possibleSrc = new ArrayList<>();
            ArrayList<ChessBoardLocation> possibleDest = new ArrayList<>();
            for (ChessBoardLocation srcLocation : getGreenChessPieceLocation()) {
                for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.GREEN)) {
                    int x = destLocation.getRow() - srcLocation.getRow();
                    int y = destLocation.getColumn() - srcLocation.getColumn();
                    if (x <= 0 && y <= 0) {
                        boolean campChessPieces = false;
                        for (ChessBoardLocation location: camp2){
                            if (getChessPieceAt(location) != null) {
                                if (getChessPieceAt(location).getColor().equals(Color.GREEN)) {
                                    campChessPieces = true;
                                }
                            }
                        }
                        if (!campChessPieces) {
                            possibleSrc.add(srcLocation);
                            possibleDest.add(destLocation);
                        }
                        else {
                            moveFromCamp = true;
                            if (camp2.contains(srcLocation)) {
                                possibleSrc.add(srcLocation);
                                possibleDest.add(destLocation);
                            }
                        }
                    }
                }
            }
            int randomNumber1 = (int) (Math.random() * possibleSrc.size());
            int randomNumber2 = (int) (Math.random() * possibleSrc.size());
            int randomNumber3 = (int) (Math.random() * possibleSrc.size());
            if (possibleSrc.size() > 0) {
                if (randomNumber1 == randomNumber2 && randomNumber2 == randomNumber3) {
                    moveChessPiece(possibleSrc.get(randomNumber1), possibleDest.get(randomNumber1));
                }
                else {
                    int x1 = possibleDest.get(randomNumber1).getRow() - possibleSrc.get(randomNumber1).getRow();
                    int y1 = possibleDest.get(randomNumber1).getColumn() - possibleSrc.get(randomNumber1).getColumn();
                    double distance1 = Math.pow(Math.pow(x1, 2) + Math.pow(y1, 2), 0.5);
                    int x2 = possibleDest.get(randomNumber2).getRow() - possibleSrc.get(randomNumber2).getRow();
                    int y2 = possibleDest.get(randomNumber2).getColumn() - possibleSrc.get(randomNumber2).getColumn();
                    double distance2 = Math.pow(Math.pow(x2, 2) + Math.pow(y2, 2), 0.5);
                    int x3 = possibleDest.get(randomNumber3).getRow() - possibleSrc.get(randomNumber3).getRow();
                    int y3 = possibleDest.get(randomNumber3).getColumn() - possibleSrc.get(randomNumber3).getColumn();
                    double distance3 = Math.pow(Math.pow(x3, 2) + Math.pow(y3, 2), 0.5);
                    if (distance1 > distance2 && distance1 > distance3){
                        moveChessPiece(possibleSrc.get(randomNumber1), possibleDest.get(randomNumber1));
                    }
                    else if (distance2 > distance1 && distance2 > distance3){
                        moveChessPiece(possibleSrc.get(randomNumber2), possibleDest.get(randomNumber2));
                    }
                    else if (distance3 > distance1 && distance3 > distance2){
                        moveChessPiece(possibleSrc.get(randomNumber3), possibleDest.get(randomNumber3));
                    }
                    else {
                        if (Math.random() > (double)(2/3)){
                            moveChessPiece(possibleSrc.get(randomNumber1), possibleDest.get(randomNumber1));
                        }
                        else if (Math.random() > (double)(1/3)) {
                            moveChessPiece(possibleSrc.get(randomNumber2), possibleDest.get(randomNumber2));
                        }
                        else {
                            moveChessPiece(possibleSrc.get(randomNumber3), possibleDest.get(randomNumber3));
                        }
                    }
                }
            }else {
                if (moveFromCamp){
                    for (ChessBoardLocation srcLocation : getGreenChessPieceLocation()) {
                        for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.GREEN)) {
                            int x = destLocation.getRow() - srcLocation.getRow();
                            int y = destLocation.getColumn() - srcLocation.getColumn();
                            if (x <= 0 && y <= 0) {
                                possibleSrc.add(srcLocation);
                                possibleDest.add(destLocation);
                            }
                        }
                    }
                    randomNumber1 = (int) (Math.random() * possibleSrc.size());
                    if (possibleSrc.size() > 0) {
                        moveChessPiece(possibleSrc.get(randomNumber1), possibleDest.get(randomNumber1));
                    }
                    return;
                }
                isSpecialTimeGreen = true;
                letAIWinTheGameGreen();
            }
        }
        recordInitialPosition();
        gameFrame.setStartTime(System.currentTimeMillis());
    }

    public void sortToFillThePosition(ChessBoardLocation location, Color color){
        if (getChessPieceAt(location) == null){
            ChessBoardLocation bestSrc = new ChessBoardLocation(dimension-1, dimension-1);
            ChessBoardLocation bestDest = new ChessBoardLocation(dimension-1, dimension-1);
            double shortestDistance = 100;
            if (color.equals(Color.RED)) {
                for (ChessBoardLocation srcLocation : getRedChessPieceLocation()) {
                    if (getChessPieceAt(srcLocation).isLocked()) continue;
                    for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.RED)) {
                        int x = location.getRow() - destLocation.getRow();
                        int y = location.getColumn() - destLocation.getColumn();
                        double distance = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
                        if (distance < shortestDistance) {
                            bestSrc = srcLocation;
                            bestDest = destLocation;
                            shortestDistance = distance;
                        }
                    }
                }
            }
            if (color.equals(Color.GREEN)) {
                for (ChessBoardLocation srcLocation : getGreenChessPieceLocation()) {
                    if (getChessPieceAt(srcLocation).isLocked()) continue;
                    for (ChessBoardLocation destLocation : getValidLocation(srcLocation, Color.GREEN)) {
                        int x = location.getRow() - destLocation.getRow();
                        int y = location.getColumn() - destLocation.getColumn();
                        double distance = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
                        if (distance < shortestDistance) {
                            bestSrc = srcLocation;
                            bestDest = destLocation;
                            shortestDistance = distance;
                        }
                    }
                }
            }
            moveChessPiece(bestSrc, bestDest);
            mode0LetAIWinCounter++;
        }
    }

    public void letAIWinTheGameRed(){
        mode0LetAIWinCounter = 0;
        getChessPieceAt(new ChessBoardLocation(dimension-1, dimension-1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-1, dimension-2),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-1, dimension-2)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-2, dimension-1),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-2, dimension-1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-2, dimension-2),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-2, dimension-2)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-1, dimension-3),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-1, dimension-3)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-3, dimension-1),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-3, dimension-1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-2, dimension-3),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-2, dimension-3)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-3, dimension-2),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-3, dimension-2)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-1, dimension-4),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-1, dimension-4)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-4, dimension-1),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-4, dimension-1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-3, dimension-3),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-3, dimension-3)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-2, dimension-4),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-2, dimension-4)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-4, dimension-2),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-4, dimension-2)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-1, dimension-5),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-1, dimension-5)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-5, dimension-1),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-5, dimension-1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-3, dimension-4),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-3, dimension-4)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-4, dimension-3),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-4, dimension-3)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-2, dimension-5),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-2, dimension-5)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(dimension-5, dimension-2),Color.RED);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(dimension-5, dimension-2)).setLocked(true);
    }

    public void letAIWinTheGameGreen(){
        mode0LetAIWinCounter = 0;
        getChessPieceAt(new ChessBoardLocation(0, 0)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(0, 1),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(0, 1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(1, 0),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(1, 0)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(1, 1),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(1, 1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(0, 2),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(0, 2)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(2, 0),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(2, 0)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(1, 2),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(1, 2)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(2, 1),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(2, 1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(0, 3),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(0, 3)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(3, 0),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(3, 0)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(2, 2),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(2, 2)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(1, 3),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(1, 3)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(3, 1),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(3, 1)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(0, 4),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(0, 4)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(4, 0),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(4, 0)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(2, 3),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(2, 3)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(3, 2),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(3, 2)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(1, 4),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(1, 4)).setLocked(true);
        sortToFillThePosition(new ChessBoardLocation(4, 1),Color.GREEN);
        if (mode0LetAIWinCounter == 1) return;
        getChessPieceAt(new ChessBoardLocation(4, 1)).setLocked(true);
    }

    public ChessBoardLocation getChessBoardLocationByColumnAndRow(int col, int row){
//        System.out.print(1);
//        System.out.print(jumpValidLocation);
        for (ChessBoardLocation location: jumpValidLocation){
            if (location.getColumn() == col && location.getRow() == row){
                return location;
            }
        }
        return null;
    }

    public ArrayList<ChessBoardLocation> getJumpValidLocation(ChessBoardLocation src, Color color){
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (src.getRow() + i < dimension && src.getColumn() + j < dimension && src.getRow() + i > -1 && src.getColumn() + j > -1) {
                    if (getChessPieceAt(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j)) != null) {
                        if (src.getRow() + 2 * i > -1 && src.getColumn() + 2 * j > -1 && src.getRow() + 2 * i < dimension && src.getColumn() + 2 * j < dimension) {
                            if (!jumpValidLocation.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                if (getChessPieceAt((new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) == null) {
                                    if (color == Color.RED && camp2.contains(src)) {
                                        if (camp2.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            jumpTimesCounter++;
                                            ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                            jumpValidLocation.add(location);
                                            location.setJumpTimes(jumpTimesCounter);
                                            location.setLastJumpLocation(src);
                                            getJumpValidLocation(location, color);
                                        }
                                    } else if (color == Color.GREEN && camp1.contains(src)) {
                                        if (camp1.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            jumpTimesCounter++;
                                            ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                            jumpValidLocation.add(location);
                                            location.setJumpTimes(jumpTimesCounter);
                                            location.setLastJumpLocation(src);
                                            getJumpValidLocation(location, color);
                                        }
                                    } else if (color == Color.ORANGE && camp3.contains(src)){
                                        if (camp3.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            jumpTimesCounter++;
                                            ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                            jumpValidLocation.add(location);
                                            location.setJumpTimes(jumpTimesCounter);
                                            location.setLastJumpLocation(src);
                                            getJumpValidLocation(location, color);
                                        }
                                    }else if (color == Color.BLUE && camp4.contains(src)){
                                        if (camp4.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            jumpTimesCounter++;
                                            ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                            jumpValidLocation.add(location);
                                            location.setJumpTimes(jumpTimesCounter);
                                            location.setLastJumpLocation(src);
                                            getJumpValidLocation(location, color);
                                        }
                                    }
                                    else if (color == Color.BLACK && camp5.contains(src)) {
                                        if (camp5.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            jumpTimesCounter++;
                                            ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                            jumpValidLocation.add(location);
                                            location.setJumpTimes(jumpTimesCounter);
                                            location.setLastJumpLocation(src);
                                            getJumpValidLocation(location, color);
                                        }
                                    } else if (color == Color.CYAN && camp6.contains(src)){
                                        if (camp6.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            jumpTimesCounter++;
                                            ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                            jumpValidLocation.add(location);
                                            location.setJumpTimes(jumpTimesCounter);
                                            location.setLastJumpLocation(src);
                                            getJumpValidLocation(location, color);
                                        }
                                    }else if (color == Color.PINK && camp7.contains(src)){
                                        if (camp7.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            jumpTimesCounter++;
                                            ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                            jumpValidLocation.add(location);
                                            location.setJumpTimes(jumpTimesCounter);
                                            location.setLastJumpLocation(src);
                                            getJumpValidLocation(location, color);
                                        }
                                    } else if (color == Color.YELLOW && camp8.contains(src)){
                                        if (camp8.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            jumpTimesCounter++;
                                            ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                            jumpValidLocation.add(location);
                                            location.setJumpTimes(jumpTimesCounter);
                                            location.setLastJumpLocation(src);
                                            getJumpValidLocation(location, color);
                                        }
                                    }
                                    else {
                                        jumpTimesCounter++;
                                        ChessBoardLocation location = new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j);
                                        jumpValidLocation.add(location);
                                        location.setJumpTimes(jumpTimesCounter/3);
                                        location.setLastJumpLocation(src);
                                        getJumpValidLocation(location, color);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (mode == 5){
            for (ChessBoardLocation location: jumpValidLocation){
                if (location.getColumn()<=10 && location.getColumn()>=5 && location.getRow()<=10 && location.getRow()>=5){
                    location.setMode5HolePosition(true);
                }
            }
        }
        return jumpValidLocation;
    }

    public ArrayList<ChessBoardLocation> getSimpleJumpValidLocation(ChessBoardLocation src, Color color){
        ArrayList<ChessBoardLocation> simpleJumpValidLocation = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (src.getRow() + i < dimension && src.getColumn() + j < dimension && src.getRow() + i > -1 && src.getColumn() + j > -1) {
                    if (getChessPieceAt(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j)) != null) {
                        if (src.getRow() + 2 * i > -1 && src.getColumn() + 2 * j > -1 && src.getRow() + 2 * i < dimension && src.getColumn() + 2 * j < dimension) {
                            if (!jumpValidLocation.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                if (getChessPieceAt((new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) == null) {
                                    if (color == Color.RED && camp2.contains(src)) {
                                        if (camp2.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                        }
                                    } else if (color == Color.GREEN && camp1.contains(src)) {
                                        if (camp1.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                        }
                                    } else if (color == Color.ORANGE && camp3.contains(src)){
                                        if (camp3.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                        }
                                    }else if (color == Color.BLUE && camp4.contains(src)){
                                        if (camp4.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                        }
                                    }
                                    else if (color == Color.BLACK && camp5.contains(src)) {
                                        if (camp5.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                        }
                                    } else if (color == Color.CYAN && camp6.contains(src)){
                                        if (camp6.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                        }
                                    }else if (color == Color.PINK && camp7.contains(src)){
                                        if (camp7.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                        }
                                    } else if (color == Color.YELLOW && camp8.contains(src)){
                                        if (camp8.contains(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j))) {
                                            simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                        }
                                    }
                                    else {
                                        simpleJumpValidLocation.add(new ChessBoardLocation(src.getRow() + 2 * i, src.getColumn() + 2 * j));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (mode == 5){
            for (ChessBoardLocation location: jumpValidLocation){
                if (location.getColumn()<=10 && location.getColumn()>=5 && location.getRow()<=10 && location.getRow()>=5){
                    location.setMode5HolePosition(true);
                }
            }
        }
        return simpleJumpValidLocation;
    }

    public ArrayList<ChessBoardLocation> getSimpleMoveValidLocation(ChessBoardLocation src, Color color){
        simpleMoveValidLocation.clear();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (src.getRow() + i < dimension && src.getColumn() + j < dimension && src.getRow() + i > -1 && src.getColumn() + j > -1) {
                    if (getChessPieceAt(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j)) == null) {
                        if (color == Color.RED && camp2.contains(src)) {
                            if (camp2.contains(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j))) {
                                simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                            }
                        } else if (color == Color.GREEN && camp1.contains(src)) {
                            if (camp1.contains(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j))) {
                                simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                            }
                        }else if (color == Color.ORANGE && camp3.contains(src)) {
                            if (camp3.contains(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j))) {
                                simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                            }
                        }else if (color == Color.BLUE && camp4.contains(src)) {
                            if (camp4.contains(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j))) {
                                simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                            }
                        }else if (color == Color.BLACK && camp5.contains(src)) {
                            if (camp5.contains(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j))) {
                                simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                            }
                        }else if (color == Color.CYAN && camp6.contains(src)) {
                            if (camp6.contains(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j))) {
                                simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                            }
                        }else if (color == Color.PINK && camp7.contains(src)) {
                            if (camp7.contains(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j))) {
                                simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                            }
                        }else if (color == Color.YELLOW && camp8.contains(src)) {
                            if (camp8.contains(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j))) {
                                simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                            }
                        }
                        else simpleMoveValidLocation.add(new ChessBoardLocation(src.getRow() + i, src.getColumn() + j));
                    }
                }
            }
        }
        if (mode == 5){
            for (ChessBoardLocation location: simpleMoveValidLocation){
                if (location.getColumn()<=10 && location.getColumn()>=5 && location.getRow()<=10 && location.getRow()>=5){
                    location.setMode5HolePosition(true);
                }
            }
        }
        return simpleMoveValidLocation;
    }

    public ArrayList<ChessBoardLocation> getValidLocation(ChessBoardLocation src, Color color){
        jumpValidLocation.clear();
        validLocation.clear();
        validLocation.addAll(getJumpValidLocation(src, color));
        validLocation.addAll(getSimpleMoveValidLocation(src, color));
        jumpValidLocationCopy = getJumpValidLocation(src, color);
        return validLocation;
    }

    public ArrayList<ChessBoardLocation> getPath(ChessBoardLocation src, ChessBoardLocation dest){
//        System.out.print(2);
//        System.out.print(jumpValidLocation);
        //TODO
        ArrayList<ChessBoardLocation> path = new ArrayList<>();
        path.add(src);
        if (getChessPieceAt(src).getColor().equals(Color.RED)){
            if (getSimpleMoveValidLocation(src,Color.RED).contains(dest)){
                path.add(dest);
                return path;
            }
            else if (getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation().equals(src)){
                path.add(dest);
                return path;
            }
            else{
                path = getPath(src, getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation());
                path.add(dest);
            }
        }
        if (getChessPieceAt(src).getColor().equals(Color.GREEN)){
            if (getSimpleMoveValidLocation(src,Color.GREEN).contains(dest)){
                path.add(dest);
                return path;
            }
            else if (getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation().equals(src)){
                path.add(dest);
                return path;
            }
            else{
                path = getPath(src, getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation());
                path.add(dest);
            }
        }
        if (getChessPieceAt(src).getColor().equals(Color.BLUE)){
            if (getSimpleMoveValidLocation(src,Color.BLUE).contains(dest)){
                path.add(dest);
                return path;
            }
            else if (getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation().equals(src)){
                path.add(dest);
                return path;
            }
            else{
                path = getPath(src, getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation());
                path.add(dest);
            }
        }
        if (getChessPieceAt(src).getColor().equals(Color.ORANGE)){
            if (getSimpleMoveValidLocation(src,Color.ORANGE).contains(dest)){
                path.add(dest);
                return path;
            }
            else if (getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation().equals(src)){
                path.add(dest);
                return path;
            }
            else{
                path = getPath(src, getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation());
                path.add(dest);
            }
        }

        if (getChessPieceAt(src).getColor().equals(Color.CYAN)){
            if (getSimpleMoveValidLocation(src,Color.CYAN).contains(dest)){
                path.add(dest);
                return path;
            }
            else if (getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation().equals(src)){
                path.add(dest);
                return path;
            }
            else{
                path = getPath(src, getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation());
                path.add(dest);
            }
        }
        if (getChessPieceAt(src).getColor().equals(Color.PINK)){
            if (getSimpleMoveValidLocation(src,Color.PINK).contains(dest)){
                path.add(dest);
                return path;
            }
            else if (getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation().equals(src)){
                path.add(dest);
                return path;
            }
            else{
                path = getPath(src, getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation());
                path.add(dest);
            }
        }
        if (getChessPieceAt(src).getColor().equals(Color.BLACK)){
            if (getSimpleMoveValidLocation(src,Color.BLACK).contains(dest)){
                path.add(dest);
                return path;
            }
            else if (getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation().equals(src)){
                path.add(dest);
                return path;
            }
            else{
                path = getPath(src, getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation());
                path.add(dest);
            }
        }
        if (getChessPieceAt(src).getColor().equals(Color.YELLOW)){
            if (getSimpleMoveValidLocation(src,Color.YELLOW).contains(dest)){
                path.add(dest);
                return path;
            }
            else if (getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation().equals(src)){
                path.add(dest);
                return path;
            }
            else{
                path = getPath(src, getChessBoardLocationByColumnAndRow(dest.getColumn(), dest.getRow()).getLastJumpLocation());
                path.add(dest);
            }
        }
        return path;
    }

    private boolean isTimeToRemind(){
        if (mode == 0 || mode == 1 || mode == 5){
            if (moveNum1 == 95 && moveNum2 == 95){
                return true;
            }
        }
        if (mode == 2){
            if (moveNum1 == 70 && moveNum2 == 70 && moveNum3 == 70 && moveNum4 == 70){
                return true;
            }
        }
        return false;
    }
    public boolean isEnd(){
        if(mode==1||mode==0 || mode == 4 || mode == 5){
            return isPlayer1Win()||isPlayer2Win()||isPlayer1Lose2p()||isPlayer2Lose2p();}
        else if (mode == 2)return isPlayer1Win4p()||isPlayer2Win4p()||isPlayer3Win4p()||isPlayer4Win4p()||isPlayer1Lose4p()||isPlayer2Lose4p()||isPlayer3Lose4p()||isPlayer4Lose4p();
        else return isPlayer1Win8p()||isPlayer2Win8p()||isPlayer3Win8p()||isPlayer4Win8p()||isPlayer5Win8p()||isPlayer6Win8p()||isPlayer7Win8p()||isPlayer8Win8p();
    }
    //player 1 means initial position is in camp1
    public boolean isPlayer1Lose2p(){
        if (moveNum1 == 100){
            for (ChessBoardLocation location: camp1){
                if (getChessPieceAt(location) != null){
                    if (getChessPieceAt(location).getColor().equals(Color.RED)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isPlayer2Lose2p(){
        if (moveNum2 == 100){
            for (ChessBoardLocation location: camp2){
                if (getChessPieceAt(location) != null){
                    if (getChessPieceAt(location).getColor().equals(Color.GREEN)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isPlayer1Lose4p(){
        if (moveNum1 == 75){
            for (ChessBoardLocation location: camp1){
                if (getChessPieceAt(location) != null){
                    if (getChessPieceAt(location).getColor().equals(Color.RED)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isPlayer2Lose4p(){
        if (moveNum2 == 75){
            for (ChessBoardLocation location: camp2){
                if (getChessPieceAt(location) != null){
                    if (getChessPieceAt(location).getColor().equals(Color.GREEN)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isPlayer3Lose4p(){
        if (moveNum3 == 75){
            for (ChessBoardLocation location: camp3){
                if (getChessPieceAt(location) != null){
                    if (getChessPieceAt(location).getColor().equals(Color.BLUE)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isPlayer4Lose4p(){
        if (moveNum4 == 75){
            for (ChessBoardLocation location: camp4){
                if (getChessPieceAt(location) != null){
                    if (getChessPieceAt(location).getColor().equals(Color.ORANGE)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isPlayer1Win(){
        for (ChessBoardLocation location: camp2){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.RED)){
                return false;
            }
        }
        return true;
    }
    //player 2 means initial position is in camp2
    public boolean isPlayer2Win(){
        for (ChessBoardLocation location: camp1){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.GREEN)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer1Win4p(){
        for (ChessBoardLocation location: camp2){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.RED)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer2Win4p(){
        for (ChessBoardLocation location: camp1){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.GREEN)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer3Win4p(){
        for (ChessBoardLocation location: camp4){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.BLUE)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer4Win4p(){
        for (ChessBoardLocation location: camp3){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.ORANGE)){
                return false;
            }
        }
        return true;
    }

    public boolean isPlayer1Win8p(){
        for (ChessBoardLocation location: camp2){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.RED)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer2Win8p(){
        for (ChessBoardLocation location: camp1){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.GREEN)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer3Win8p(){
        for (ChessBoardLocation location: camp4){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.BLUE)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer4Win8p(){
        for (ChessBoardLocation location: camp3){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.ORANGE)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer5Win8p(){
        for (ChessBoardLocation location: camp6){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.CYAN)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer6Win8p(){
        for (ChessBoardLocation location: camp5){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.BLACK)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer7Win8p(){
        for (ChessBoardLocation location: camp7){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.PINK)){
                return false;
            }
        }
        return true;
    }
    public boolean isPlayer8Win8p(){
        for (ChessBoardLocation location: camp8){
            if (getChessPieceAt(location) == null){
                return false;
            }
            if (!getChessPieceAt(location).getColor().equals(Color.BLACK)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public ChessBoardLocation getSource() {
        return source;
    }

    public void setSource(ChessBoardLocation source) {
        this.source = source;
    }

    public ChessBoardLocation getDestiny() {
        return destiny;
    }

    public void setDestiny(ChessBoardLocation destiny) {
        this.destiny = destiny;
    }

    public boolean isUndoValid() {
        return isUndoValid;
    }

    public void setUndoValid(boolean undoValid) {
        isUndoValid = undoValid;
    }

    public int undo() {
        if (!isUndoValid()){
            JOptionPane.showMessageDialog(null, "Invalid undo operation");
            return 1;
        }
        if (mode != 0) {
            if (source == null || destiny == null) {
                JOptionPane.showMessageDialog(null, "Invalid undo operation");
                return 1;
            } else {
                moves.remove(moves.size() - 1);
                setChessPieceAt(source, removeChessPieceAt(destiny));
                JOptionPane.showMessageDialog(null, "Undo successfully");
                source = null;
                destiny = null;
                return 0;
            }
        } else {
            if (source == null || destiny == null) {
                JOptionPane.showMessageDialog(null, "Invalid undo operation");
                return 1;
            } else {
                String toUndo = moves.get(moves.size() - 2);
                setChessPieceAt(source, removeChessPieceAt(destiny));
                ChessBoardLocation from=new ChessBoardLocation(Integer.parseInt(toUndo.substring(0,2)),Integer.parseInt(toUndo.substring(2,4)));
                ChessBoardLocation to=new ChessBoardLocation(Integer.parseInt(toUndo.substring(4,6)),Integer.parseInt(toUndo.substring(6,8)));
                setChessPieceAt(from, removeChessPieceAt(to));
                if (moves.size()==2){
                    moves.remove(1);
                    moves.remove(0);
                }else {
                    moves.remove(moves.size() - 1);
                    moves.remove(moves.size() - 2);
                }
                JOptionPane.showMessageDialog(null, "Undo successfully");
                source = null;
                destiny = null;
                return 1318;
            }
        }

    }

}
