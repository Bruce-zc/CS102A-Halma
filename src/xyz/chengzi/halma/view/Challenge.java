package xyz.chengzi.halma.view;

import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Challenge extends JFrame {
    public static void main(String[] args) {
        Challenge c=new Challenge(null,null);
        c.setVisible(true);
    }
    Home home;
    JFrame lm;
    JButton l1=new JButton("Level 1");
    JButton l2=new JButton("Level 2");
    JButton l3=new JButton("Level 3");
    JButton l4=new JButton("Level 4");
    JButton l5=new JButton("Level 5");
    JButton l6=new JButton("Level 6");
    JButton l7=new JButton("Level 7");
    JButton l8=new JButton("Level 8");
    JButton menu=new JButton("Back");
    public Challenge(Home home,JFrame lm){
        this.home=home;
        this.lm=lm;
        this.setIconImage(new ImageIcon("game.png").getImage());
        this.setFont(new Font(null, Font.PLAIN, 15));
        setTitle("Challenges");
        setSize(270,310);

        JPanel panel = new JPanel(); // Use the panel to group buttons
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FlowLayout layout=new FlowLayout();
        layout.setVgap(12);
        layout.setHgap(8);
        panel.setLayout(layout);
        l1.setPreferredSize(new Dimension(110,40));
        l1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        l2.setPreferredSize(new Dimension(110,40));
        l2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        l3.setPreferredSize(new Dimension(110,40));
        l3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        l4.setPreferredSize(new Dimension(110,40));
        l4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        l5.setPreferredSize(new Dimension(110,40));
        l5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        l6.setPreferredSize(new Dimension(110,40));
        l6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        l7.setPreferredSize(new Dimension(110,40));
        l7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        l8.setPreferredSize(new Dimension(110,40));
        l8.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        menu.setPreferredSize(new Dimension(110,40));
        menu.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        panel.add(l1);
        panel.add(l2);
        panel.add(l3);
        panel.add(l4);
        panel.add(l5);
        panel.add(l6);
        panel.add(l7);
        panel.add(l8);
        panel.add(menu);
        add(panel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        l1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file=new File("cha/1.sav");
                readSaving(file);
                setVisible(false);
            }
        });
        l2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file=new File("cha/2.sav");
                readSaving(file);
                setVisible(false);
            }
        });
        l3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file=new File("cha/3.sav");
                readSaving(file);
                setVisible(false);
            }
        });
        l4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file=new File("cha/4.sav");
                readSaving(file);
                setVisible(false);
            }
        });
        l5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file=new File("cha/5.sav");
                readSaving(file);
                setVisible(false);
            }
        });
        l6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file=new File("cha/6.sav");
                readSaving(file);
                setVisible(false);
            }
        });
        l7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file=new File("cha/7.sav");
                readSaving(file);
                setVisible(false);
            }
        });
        l8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file=new File("cha/8.sav");
                readSaving(file);
                setVisible(false);
            }
        });
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                lm.setVisible(true);
            }
        });



    }
    private void readSaving(File file){
        try{
            BufferedReader reader=new BufferedReader(new FileReader(file));

            int mode=-1;
            Color currentPlayer=Color.GRAY;
            String temp=reader.readLine();
            int red=0;
            int green=0;
            int blue=0;
            int orange=0;

            mode=Integer.parseInt(String.valueOf(temp.charAt(0)));



            if (temp.substring(2).equals("RED")){
                currentPlayer=Color.RED;
            }
            if (temp.substring(2).equals("GREEN")){
                currentPlayer=Color.GREEN;
            }
            if (temp.substring(2).equals("ORANGE")){
                currentPlayer=Color.ORANGE;
            }
            if (temp.substring(2).equals("BLUE")){
                currentPlayer=Color.BLUE;
            }

            ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
            ChessBoard chessBoard = new ChessBoard(16,mode);
            if (temp.startsWith("E", 1)){
                chessBoard.setEasyAI(true);
            }
            if (temp.startsWith("M", 1)){
                chessBoard.setMediumAI(true);
            }
            if (temp.startsWith("H", 1)){
                chessBoard.setHardAI(true);
            }
            int error=0;
            GameController controller = new GameController(chessBoardComponent, chessBoard);
            controller.setCurrentPlayer(currentPlayer);
            GameFrame mainFrame = new GameFrame(mode);
            mainFrame.add(chessBoardComponent);
            mainFrame.setController(controller);
            mainFrame.setChessBoard(chessBoard);
            chessBoard.setGameFrame(mainFrame);
            mainFrame.newGame.setEnabled(false);
            mainFrame.tipItem.setEnabled(false);
            mainFrame.trusItem.setEnabled(false);
            chessBoard.clear();
            int rStandard=0;int cStandard=0;
            while((temp = reader.readLine())!=null&&!temp.equals("END")){
                Color pieceColor=Color.GRAY;
                if (cStandard==16){
                    rStandard++;
                    cStandard=0;
                }

                int row=Integer.parseInt(temp.substring(0,2));

                int col=Integer.parseInt(temp.substring(2,4));
                if (row!=rStandard||col!=cStandard){
                    error++;

                }
                System.out.println(row+" "+rStandard);
                System.out.println(col+" "+cStandard);
                cStandard++;

                ChessBoardLocation location=new ChessBoardLocation(row,col);

                if (temp.substring(4).equals("R")){
                    pieceColor=Color.RED;
                    red++;
                }
                if (temp.substring(4).equals("G")){
                    pieceColor=Color.GREEN;
                    green++;
                }
                if (temp.substring(4).equals("O")){
                    pieceColor=Color.ORANGE;
                    orange++;
                }
                if (temp.substring(4).equals("B")){
                    pieceColor=Color.BLUE;
                    blue++;
                }
                if (!pieceColor.equals(Color.GRAY)) {
                    //System.out.println(pieceColor);
                    chessBoard.setChessPieceAt(location,new ChessPiece(pieceColor));
                    chessBoard.loadInitialPieces(Integer.parseInt(temp.substring(0,2)),Integer.parseInt(temp.substring(2,4)),pieceColor);
                }
            }
//                ArrayList<String> moves=new ArrayList<>();
//                while ((temp = reader.readLine())!=null){
//                    moves.add(temp);
//                }
            //read moveNum
            //read move details
        //    System.out.println(red+" "+green);
            if (mode==0){
                if (error==0&&!chessBoard.isEnd()&&red==19&&green==19&&currentPlayer.equals(Color.GREEN)&&blue==0&&orange==0){
                    mainFrame.disablePicture();
                    mainFrame.setVisible(true);
                    home.setVisible(false);
                    mainFrame.setTitle("Against Computer");
                    chessBoard.setMoveNum1(Integer.parseInt(reader.readLine()));
                    chessBoard.setMoveNum2(Integer.parseInt(reader.readLine()));
                    turnToPlay(currentPlayer);
                    mainFrame.setCurrentText();

                    Home.player.stop();
//                        controller.gameStartedSpecialEvent();
                }else {
                    JOptionPane.showMessageDialog(null, "The save is invalid!");
                }
            }else if (mode==1){
                if (error==0&&red==19&&green==19&&!chessBoard.isEnd()){
                    mainFrame.setVisible(true);
                    home.setVisible(false);
                    mainFrame.setTitle("2 Players");
                    chessBoard.setMoveNum1(Integer.parseInt(reader.readLine()));
                    chessBoard.setMoveNum2(Integer.parseInt(reader.readLine()));
                    turnToPlay(currentPlayer);
                    mainFrame.setCurrentText();
                    Home.player.stop();

                }else {
                    JOptionPane.showMessageDialog(null, "The save is invalid!");
                }
            }else {
    //            System.out.println(red+""+blue);
     //           System.out.println(orange+""+green);
                if (error==0&&red==13&&blue==13&&orange==13&&green==13&&!chessBoard.isEnd()){
                    mainFrame.setVisible(true);
                    home.setVisible(false);
                    mainFrame.setTitle("4 Players");
                    mainFrame.setCurrentText();
                    chessBoard.setMoveNum1(Integer.parseInt(reader.readLine()));
                    chessBoard.setMoveNum2(Integer.parseInt(reader.readLine()));
                    chessBoard.setMoveNum3(Integer.parseInt(reader.readLine()));
                    chessBoard.setMoveNum4(Integer.parseInt(reader.readLine()));
                    turnToPlay(currentPlayer);
                    Home.player.stop();

                }else {
                    JOptionPane.showMessageDialog(null, "The save is invalid!");
                }
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "The save is dated!");
            e.printStackTrace();
        }
    }

    public void turnToPlay(Color currentPlayer){
        if (currentPlayer.equals(Color.RED)){
            JOptionPane.showMessageDialog(null, "It's RED's turn to play!");
        }
        if (currentPlayer.equals(Color.GREEN)){
            JOptionPane.showMessageDialog(null, "It's GREEN's turn to play!");
        }
        if (currentPlayer.equals(Color.BLUE)){
            JOptionPane.showMessageDialog(null, "It's BLUE's turn to play!");
        }
        if (currentPlayer.equals(Color.ORANGE)){
            JOptionPane.showMessageDialog(null, "It's ORANGE's turn to play!");
        }
        if (currentPlayer.equals(Color.CYAN)){
            JOptionPane.showMessageDialog(null, "It's CYAN's turn to play!");
        }
        if (currentPlayer.equals(Color.BLACK)){
            JOptionPane.showMessageDialog(null, "It's BLACK's turn to play!");
        }
        if (currentPlayer.equals(Color.YELLOW)){
            JOptionPane.showMessageDialog(null, "It's YELLOW's turn to play!");
        }
        if (currentPlayer.equals(Color.PINK)){
            JOptionPane.showMessageDialog(null, "It's PINK's turn to play!");
        }
    }
}
