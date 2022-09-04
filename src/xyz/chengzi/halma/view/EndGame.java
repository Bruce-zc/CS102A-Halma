package xyz.chengzi.halma.view;


import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class EndGame extends JFrame {
    JButton turn=new JButton("play");
    JLabel info=new JLabel();
    JLabel moveNum=new JLabel();
    JButton menu=new JButton("Back to main menu");
    String winner="";
    int move=0;
    GameFrame frame;
    ArrayList<String> moves;
    ArrayList<String> iniPosition;
    int mode;
    Timer finalTimer;


    public EndGame(String winner,int move,GameFrame gameFrame,ArrayList<String> moves,int mode,ArrayList<String> initialPosition) {
        GameFrame.bgm.stop();
        int tempTheme=ChessComponent.chessTheme;
        this.setIconImage(new ImageIcon("dog.png").getImage());
        this.mode=mode;
        this.winner=winner;
        this.move=move;
        this.frame=gameFrame;
        this.moves=moves;
        this.iniPosition=initialPosition;
        setTitle("Game over!");
        setFont(new Font(null, Font.PLAIN, 13));
        setSize(300,420);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.stopTimer();
        frame.setVisible(false);
        if (winner.equals("DRAW")){
            info.setText("          Draw!");
            moveNum.setText("  The game is over.");
        }
        if (winner.equals("REDLOSE")){
            info.setText("     RED loses!");
            moveNum.setText("  The game is over.");
        }
        if (winner.equals("GREENLOSE")){
            info.setText("    GREEN loses!");
            moveNum.setText("  The game is over.");
        }
        if (winner.equals("BLUELOSE")){
            info.setText("     BLUE loses!");
            moveNum.setText("  The game is over.");
        }
        if (winner.equals("ORANGELOSE")){
            info.setText("    ORANGE loses!");
            moveNum.setText("  The game is over.");
        }
        if (winner.equals("RED")){
            info.setText("  RED is the winner!");
            moveNum.setText("It took "+move+" move to win!");
        }
        if (winner.equals("GREEN")){
            info.setText("  GREEN is the winner!");
            moveNum.setText("It took "+move+" move to win!");
        }
        if (winner.equals("BLUE")){
            info.setText("  BLUE is the winner!");
            moveNum.setText("It took "+move+" move to win!");
        }
        if (winner.equals("ORANGE")){
            info.setText("  ORANGE is the winner!");
            moveNum.setText("It took "+move+" move to win!");
        }



        //replay
        ChessComponent.chessTheme=2;
        ChessBoardComponent board = new ChessBoardComponent(240, 16);
        ChessBoard chessBoard = new ChessBoard(16,mode);
        GameController controller = new GameController(board, chessBoard);
        add(board);

//


        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //setContentPane(panel);
        board.setLocation(25,85);
        info.setLocation(70,30);
        moveNum.setLocation(70,60);
        menu.setLocation(70,340);
        turn.setLocation(90,340);

        info.setSize(250,30);
        moveNum.setSize(250,30);
        menu.setSize(150, 30);
        turn.setSize(50,30);

        info.setFont(new Font(null, Font.PLAIN, 15));
        moveNum.setFont(new Font(null, Font.PLAIN, 15));


        info.setVisible(true);
        moveNum.setVisible(true);
        add(info);
        add(menu);
        add(moveNum);
        //add(turn);
        //button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Button clicked!"));
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //chessBoard.abtw.stop();
                GameFrame.bgm.stop();
                Home home=new Home();
                setVisible(false);
                home.setHome(home);
                home.setVisible(true);
                finalTimer.cancel();
                ChessComponent.chessTheme=tempTheme;
            }

        });
        turn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        chessBoard.clear();
        for (String initialize:iniPosition){
            Color pieceColor=Color.GRAY;
            ChessBoardLocation location=new ChessBoardLocation(Integer.parseInt(initialize.substring(0,2)),Integer.parseInt(initialize.substring(2,4)));
            if (initialize.substring(4).equals("RED")){
                pieceColor=Color.RED;
            }
            if (initialize.substring(4).equals("GREEN")){
                pieceColor=Color.GREEN;
            }
            if (initialize.substring(4).equals("ORANGE")){
                pieceColor=Color.ORANGE;
            }
            if (initialize.substring(4).equals("BLUE")){
                pieceColor=Color.BLUE;
            }
            if (!pieceColor.equals(Color.GRAY)) {
                //System.out.println(pieceColor);
                chessBoard.setChessPieceAt(location,new ChessPiece(pieceColor));
                chessBoard.loadInitialPieces(Integer.parseInt(initialize.substring(0,2)),Integer.parseInt(initialize.substring(2,4)),pieceColor);
            }
        }
        final int[] i = {0};
        int j=moves.size()-1;
        Timer timer1 = new Timer();
            timer1=new Timer();
        finalTimer = timer1;
        timer1.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (i[0]<=j) {
                        for (int m = 0; m < 16; m++) {
                            for (int n = 0; n < 16; n++) {
                                if (chessBoard.getChessPieceAt(new ChessBoardLocation(m,n)) != null) {
                                    chessBoard.getChessPieceAt(new ChessBoardLocation(m,n)).setInvalid(true);
                                }
                            }
                        }
                        String m = moves.get(i[0]);
                        ChessBoardLocation src = new ChessBoardLocation(Integer.parseInt(m.substring(0, 2)), Integer.parseInt(m.substring(2, 4)));
                        ChessBoardLocation dest = new ChessBoardLocation(Integer.parseInt(m.substring(4, 6)), Integer.parseInt(m.substring(6, 8)));
                        chessBoard.replayMove(src, dest);
                        i[0]++;
                    }else {
                        finalTimer.cancel();
                    }
                }
            }, 300,300);


     }
}

