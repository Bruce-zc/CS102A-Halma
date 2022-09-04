package xyz.chengzi.halma.controller;

import xyz.chengzi.halma.listener.InputListener;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;
import xyz.chengzi.halma.view.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements InputListener {
    private AIShow ais;
    private ChessBoardComponent view;
    private HoleBoardComponent view2;
    private ChessBoard model;

    public ChessBoardComponent getView() {
        return view;
    }

    public void setView(ChessBoardComponent view) {
        this.view = view;
    }

    public HoleBoardComponent getView2() {
        return view2;
    }

    public void setView2(HoleBoardComponent view2) {
        this.view2 = view2;
    }

    private ChessBoardLocation selectedLocation;

    public ChessBoard getModel() {
        return model;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    private Color currentPlayer;


    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard) {
        this.view = chessBoardComponent;
        this.model = chessBoard;
        //random order 50%RED 50%GREEN
        if(chessBoard.getMode()==1 || chessBoard.getMode() == 0|| chessBoard.getMode() == 5 || chessBoard.getMode() == 4){
            if (Math.random() > 0.5) {
                this.currentPlayer = Color.RED;
            }
            else this.currentPlayer = Color.GREEN;
            for (int row = 0; row < 16 ; row++) {
                for (int col = 0; col < 16; col++) {
                    if ((row == 0 || row == 1) && col == 5) {
                        view.getGridAt(new ChessBoardLocation(row, col)).setRedSquare2(true);
                    }
                    if ((col == 0 || col == 1) && row == 5) {
                        view.getGridAt(new ChessBoardLocation(row, col)).setRedSquare3(true);
                    }
                    if (row >= 2 && row <= 4 && row + col == 6) {
                        view.getGridAt(new ChessBoardLocation(row, col)).setRedSquare1(true);
                    }
                    if (row == 15&& col == 11){
                        view.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare2(true);
                    }
                    if (row == 11 && col == 15){
                        view.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare3(true);
                    }
                    if (row <= 14 && col <= 14 && row + col == 25) {
                        view.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare1(true);
                    }
                }
            }
            view.registerListener(this);
            model.registerListener(view);
            model.placeInitialPieces();
        }
        if(chessBoard.getMode()==2){
            double a=Math.random();
            if (a >= 0&&a<0.25) {
                this.currentPlayer = Color.RED;
            }
            else if (a>=0.25&&a<0.5)
                this.currentPlayer = Color.BLUE;
            else if(a>=0.5&&a<0.75)
                this.currentPlayer=Color.GREEN;
            else
                this.currentPlayer=Color.ORANGE;
            for (int row = 0; row < 19 ; row++) {
                for (int col = 0; col < 19; col++) {
                    if (model.getMode() == 2) {
                        if ((row == 0 || row == 1) && col == 4) {
                            view.getGridAt(new ChessBoardLocation(row, col)).setRedSquare2(true);
                        }
                        if ((col == 0 || col == 1) && row == 4) {
                            view.getGridAt(new ChessBoardLocation(row, col)).setRedSquare3(true);
                        }
                        if (row >= 2 && row <= 3 && row + col == 5) {
                            view.getGridAt(new ChessBoardLocation(row, col)).setRedSquare1(true);
                        }
                        if (row == 15 && col == 12) {
                            view.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare2(true);
                        }
                        if (row == 12 && col == 15) {
                            view.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare3(true);
                        }
                        if (row <= 14 && col <= 14 && row + col == 26) {
                            view.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare1(true);
                        }
                    }
                }
            }
            view.getGridAt(new ChessBoardLocation(0,12)).setBlueSquare2(true);
            view.getGridAt(new ChessBoardLocation(1,12)).setBlueSquare2(true);
            view.getGridAt(new ChessBoardLocation(2,13)).setBlueSquare2(true);
            view.getGridAt(new ChessBoardLocation(3,14)).setBlueSquare2(true);
            view.getGridAt(new ChessBoardLocation(2,12)).setBlueSquare3(true);
            view.getGridAt(new ChessBoardLocation(3,13)).setBlueSquare3(true);
            view.getGridAt(new ChessBoardLocation(4,14)).setBlueSquare3(true);
            view.getGridAt(new ChessBoardLocation(4,15)).setBlueSquare3(true);

            view.getGridAt(new ChessBoardLocation(13,3)).setOrangeSquare2(true);
            view.getGridAt(new ChessBoardLocation(12,2)).setOrangeSquare2(true);
            view.getGridAt(new ChessBoardLocation(15,4)).setOrangeSquare2(true);
            view.getGridAt(new ChessBoardLocation(14,4)).setOrangeSquare2(true);
            view.getGridAt(new ChessBoardLocation(12,0)).setOrangeSquare3(true);
            view.getGridAt(new ChessBoardLocation(14,3)).setOrangeSquare3(true);
            view.getGridAt(new ChessBoardLocation(13,2)).setOrangeSquare3(true);
            view.getGridAt(new ChessBoardLocation(12,1)).setOrangeSquare3(true);

            view.registerListener(this);
            model.registerListener(view);
            model.placeInitialPieces();
        }
        if(chessBoard.getMode()==3){
            double a=Math.random();
            if (a >= 0&&a<0.125) {
                this.currentPlayer = Color.RED;
            }
            else if (a>=0.125&&a<0.25) {
                this.currentPlayer = Color.BLUE;
            }
            else if(a>=0.25&&a<0.375) {
                this.currentPlayer = Color.GREEN;
            }
            else if(a>=0.375&&a<0.5) {
                this.currentPlayer = Color.ORANGE;
            }
            else if (a >= 0.5&&a<0.625) {
                this.currentPlayer = Color.BLACK;
            }
            else if (a>=0.625&&a<0.75) {
                this.currentPlayer = Color.PINK;
            }
            else if(a>=0.75&&a<0.875) {
                this.currentPlayer = Color.CYAN;
            }
            else if(a>=0.875&&a<1) {
                this.currentPlayer = Color.YELLOW;
            }

            view.registerListener(this);
            model.registerListener(view);
            model.placeInitialPieces();
    }}
    public GameController(HoleBoardComponent chessBoardComponent, ChessBoard chessBoard) {
        this.view2 = chessBoardComponent;
        this.model = chessBoard;
        //random order 50%RED 50%GREEN
        if(chessBoard.getMode()==1 || chessBoard.getMode() == 0|| chessBoard.getMode() == 5){
            if (Math.random() > 0.5) {
                this.currentPlayer = Color.RED;
            }
            else this.currentPlayer = Color.GREEN;
            for (int row = 0; row < 16 ; row++) {
                for (int col = 0; col < 16; col++) {
                    if ((row == 0 || row == 1) && col == 5) {
                        view2.getGridAt(new ChessBoardLocation(row, col)).setRedSquare2(true);
                    }
                    if ((col == 0 || col == 1) && row == 5) {
                        view2.getGridAt(new ChessBoardLocation(row, col)).setRedSquare3(true);
                    }
                    if (row >= 2 && row <= 4 && row + col == 6) {
                        view2.getGridAt(new ChessBoardLocation(row, col)).setRedSquare1(true);
                    }
                    if (row == 15&& col == 11){
                        view2.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare2(true);
                    }
                    if (row == 11 && col == 15){
                        view2.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare3(true);
                    }
                    if (row <= 14 && col <= 14 && row + col == 25) {
                        view2.getGridAt(new ChessBoardLocation(row, col)).setGreenSquare1(true);
                    }
                }
            }
            view2.registerListener(this);
            model.registerListener(view2);
            model.placeInitialPieces();
        }}



    public ChessBoardLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(ChessBoardLocation location) {
        this.selectedLocation = location;
    }

    public void resetSelectedLocation() {
        setSelectedLocation(null);
    }

    public boolean hasSelectedLocation() {
        return selectedLocation != null;
    }

    public Color nextPlayer() {
       if(model.getMode() == 1 || model.getMode() == 0|| model.getMode() == 5 || model.getMode() == 4){
           return currentPlayer = currentPlayer == Color.RED ? Color.GREEN : Color.RED;
       }
       else if(model.getMode()==2)
           if(currentPlayer==Color.RED)
              return currentPlayer =Color.BLUE;
           else if(currentPlayer==Color.BLUE)
               return currentPlayer =Color.GREEN;
           else if(currentPlayer==Color.GREEN)
               return currentPlayer =Color.ORANGE;
           else if(currentPlayer==Color.ORANGE)
               return currentPlayer = Color.RED;
           else
               return currentPlayer;
       else {
           if (currentPlayer == Color.RED) {
               return currentPlayer = Color.CYAN;
           }
           else if (currentPlayer == Color.CYAN) {
               return currentPlayer = Color.PINK;
           }
           else if (currentPlayer == Color.PINK) {
               return currentPlayer = Color.ORANGE;
           }
           else if (currentPlayer == Color.ORANGE) {
               return currentPlayer = Color.GREEN;
           }
           else if (currentPlayer == Color.GREEN) {
               return currentPlayer = Color.BLACK;
           }
           else if (currentPlayer == Color.BLACK) {
               return currentPlayer = Color.YELLOW;
           }
           else if (currentPlayer == Color.YELLOW) {
               return currentPlayer = Color.BLUE;
           }
           else if (currentPlayer == Color.BLUE) {
               return currentPlayer = Color.RED;
           }
           else
               return currentPlayer;
       }

    }

    public void gameStartedSpecialEvent(){
        if (model.getMode() == 0){
            if (this.currentPlayer.equals(Color.RED)){
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!model.getTru().equals("G")) {
                    model.automaticallyMoveRed();
                }else {
                    //model.automaticallyMoveGreen();
                    setCurrentPlayer(Color.GREEN);
                }
                nextPlayer();
            }
        }
        if (model.getMode() == 4){
            this.ais=new AIShow(this);
            ais.start();
        }
    }

    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
        if (view2==null){
            if (hasSelectedLocation() && model.isValidMove(getSelectedLocation(), location)) {
                model.setUndoValid(true);
                view.setAllNotPossibleMoveSquare();
                view.getGridAt(selectedLocation).setLastMoveSquare(true);
                model.setView(view);
                ArrayList<ChessBoardLocation> path = model.getPath(selectedLocation, location);
                for (ChessBoardLocation location1: path){
                    if (location1 != selectedLocation) {
                        view.getGridAt(location1).setTraceSquare(true);
                    }
                }
                for (int i = 0; i < path.size() - 1; i++){
                    SquareComponent center = view.getGridAt(new ChessBoardLocation((path.get(i+1).getRow() + path.get(i).getRow())/2,(path.get(i+1).getColumn() + path.get(i).getColumn())/2));
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 2 && path.get(i+1).getRow() - path.get(i).getRow() == -2){
                        view.getGridAt(path.get(i+1)).setTraceSquare1(true);
                        view.getGridAt(path.get(i)).setTraceSquare5(true);
                        center.setTraceSquare5(true);
                        center.setTraceSquare1(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == -2 && path.get(i+1).getRow() - path.get(i).getRow() == 2){
                        view.getGridAt(path.get(i+1)).setTraceSquare5(true);
                        view.getGridAt(path.get(i)).setTraceSquare1(true);
                        center.setTraceSquare5(true);
                        center.setTraceSquare1(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 0 && path.get(i+1).getRow() - path.get(i).getRow() == -2){
                        view.getGridAt(path.get(i+1)).setTraceSquare2(true);
                        view.getGridAt(path.get(i)).setTraceSquare6(true);
                        center.setTraceSquare6(true);
                        center.setTraceSquare2(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 0 && path.get(i+1).getRow() - path.get(i).getRow() == 2){
                        view.getGridAt(path.get(i+1)).setTraceSquare6(true);
                        view.getGridAt(path.get(i)).setTraceSquare2(true);
                        center.setTraceSquare6(true);
                        center.setTraceSquare2(true);
                    }

                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 2 && path.get(i+1).getRow() - path.get(i).getRow() == 2){
                        view.getGridAt(path.get(i+1)).setTraceSquare3(true);
                        view.getGridAt(path.get(i)).setTraceSquare7(true);
                        center.setTraceSquare3(true);
                        center.setTraceSquare7(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == -2 && path.get(i+1).getRow() - path.get(i).getRow() == -2){
                        view.getGridAt(path.get(i+1)).setTraceSquare7(true);
                        view.getGridAt(path.get(i)).setTraceSquare3(true);
                        center.setTraceSquare3(true);
                        center.setTraceSquare7(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 2 && path.get(i+1).getRow() - path.get(i).getRow() == 0){
                        view.getGridAt(path.get(i+1)).setTraceSquare4(true);
                        view.getGridAt(path.get(i)).setTraceSquare8(true);
                        center.setTraceSquare4(true);
                        center.setTraceSquare8(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == -2 && path.get(i+1).getRow() - path.get(i).getRow() == 0){
                        view.getGridAt(path.get(i+1)).setTraceSquare8(true);
                        view.getGridAt(path.get(i)).setTraceSquare4(true);
                        center.setTraceSquare8(true);
                        center.setTraceSquare4(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 1 && path.get(i+1).getRow() - path.get(i).getRow() == 1){
                        view.getGridAt(path.get(i+1)).setTraceSquare3(true);
                        view.getGridAt(path.get(i)).setTraceSquare7(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == -1 && path.get(i+1).getRow() - path.get(i).getRow() == -1){
                        view.getGridAt(path.get(i+1)).setTraceSquare7(true);
                        view.getGridAt(path.get(i)).setTraceSquare3(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 0 && path.get(i+1).getRow() - path.get(i).getRow() == 1){
                        view.getGridAt(path.get(i+1)).setTraceSquare6(true);
                        view.getGridAt(path.get(i)).setTraceSquare2(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 0 && path.get(i+1).getRow() - path.get(i).getRow() == -1){
                        view.getGridAt(path.get(i+1)).setTraceSquare2(true);
                        view.getGridAt(path.get(i)).setTraceSquare6(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 1 && path.get(i+1).getRow() - path.get(i).getRow() == 0){
                        view.getGridAt(path.get(i+1)).setTraceSquare4(true);
                        view.getGridAt(path.get(i)).setTraceSquare8(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == -1 && path.get(i+1).getRow() - path.get(i).getRow() == 0){
                        view.getGridAt(path.get(i+1)).setTraceSquare8(true);
                        view.getGridAt(path.get(i)).setTraceSquare4(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == 1 && path.get(i+1).getRow() - path.get(i).getRow() == -1){
                        view.getGridAt(path.get(i+1)).setTraceSquare1(true);
                        view.getGridAt(path.get(i)).setTraceSquare5(true);
                    }
                    if (path.get(i+1).getColumn() - path.get(i).getColumn() == -1 && path.get(i+1).getRow() - path.get(i).getRow() == 1){
                        view.getGridAt(path.get(i+1)).setTraceSquare5(true);
                        view.getGridAt(path.get(i)).setTraceSquare1(true);
                    }
                }
                model.complicatedMoveChessPiece(selectedLocation, location);
                resetSelectedLocation();
                nextPlayer();

                if (model.getMode() == 0) {

                    Timer timer=new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!model.isEnd()) {
                                if (!model.getTru().equals("G")) {
                                    model.automaticallyMoveRed();
                                }else {
                                    model.automaticallyMoveGreen();
                                    setCurrentPlayer(Color.GREEN);
                                }
                                nextPlayer();
                            }
                        }
                    },path.size()*300 + 300);

                }
            }
        }else {
            if (hasSelectedLocation() && model.isValidMove(getSelectedLocation(), location)) {
                model.setUndoValid(true);
                view2.setAllNotPossibleMoveSquare();
                view2.getGridAt(selectedLocation).setLastMoveSquare(true);
                view2.repaint();
                model.complicatedMoveChessPiece(selectedLocation, location);;
                resetSelectedLocation();
                nextPlayer();

                if (model.getMode() == 0) {

                    Timer timer=new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!model.isEnd()) {
                                if (!model.getTru().equals("G")) {
                                    model.automaticallyMoveRed();
                                }else {
                                    model.automaticallyMoveGreen();
                                    setCurrentPlayer(Color.GREEN);
                                }
                                nextPlayer();
                            }
                        }
                    },600);

                }
            }
        }
    }

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        if (view2==null){
            if (model.getChessPieceAt(location).isInvalid()) return;
            ChessPiece piece = model.getChessPieceAt(location);
            if (piece.getColor() == currentPlayer && (!hasSelectedLocation() || location.equals(getSelectedLocation()))) {
                if (!hasSelectedLocation()) {
                    model.setUndoValid(false);
                    view.setAllNotLastMoveSquare();
                    view.setAllNotTraceSquare();
                    view.repaint();
                    setSelectedLocation(location);
                    if (!(model.isHardAI())) {
                        for (ChessBoardLocation location1 : model.getValidLocation(location, component.getColor())) {
                            view.getGridAt(location1).setPossibleMoveSquare(true);
                            view.repaint();
                        }
                    }
                } else {
                    resetSelectedLocation();
                    view.setAllNotPossibleMoveSquare();
                    view.repaint();
                }
                component.setSelected(!component.isSelected());
                component.repaint();
            }
        }else {
            if (model.getChessPieceAt(location).isInvalid()) return;
            ChessPiece piece = model.getChessPieceAt(location);
            if (piece.getColor() == currentPlayer && (!hasSelectedLocation() || location.equals(getSelectedLocation()))) {
                if (!hasSelectedLocation()) {
                    model.setUndoValid(false);
                    view2.setAllNotLastMoveSquare();
                    view2.repaint();
                    setSelectedLocation(location);
                    for (ChessBoardLocation location1: model.getValidLocation(location, component.getColor())){
                        if (!(location1.isMode5HolePosition())) {
                            view2.getGridAt(location1).setPossibleMoveSquare(true);
                            view2.repaint();
                        }
                    }
                } else {
                    resetSelectedLocation();
                    view2.setAllNotPossibleMoveSquare();
                    view2.repaint();
                }
                component.setSelected(!component.isSelected());
                component.repaint();
            }
        }
    }

    public void setModel(ChessBoard model) {
        this.model = model;
    }

    public AIShow getAis() {
        return ais;
    }

    public void setAis(AIShow ais) {
        this.ais = ais;
    }
}
