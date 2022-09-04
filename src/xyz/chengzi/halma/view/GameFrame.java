package xyz.chengzi.halma.view;


import xyz.chengzi.halma.BGM;
import xyz.chengzi.halma.Online.Sender;
import xyz.chengzi.halma.Online.Sender4Chat;
import xyz.chengzi.halma.controller.AIShow;
import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame {
    public JTextArea displayer;
    JButton menu;
    private int mode;
    JMenuItem menuItem=new JMenuItem("Menu");
    JMenuItem saveItem=new JMenuItem("Save");
    JMenuItem newGame=new JMenuItem("New game");
    JMenuItem undoItem=new JMenuItem("Undo");
    JMenuItem tipItem=new JMenuItem("Tip");
    JMenuItem trusItem=new JMenuItem("Trusteeship");
    public JMenuItem surItem;
    JMenuItem customization=new JMenuItem("Customization");
    JMenuBar menuBar=new JMenuBar();
    private JButton tru=new JButton("Trusteeship");
    public static BGM bgm;
    ChessBoard chessBoard;
    GameController controller;
    JLabel statusLabel=new JLabel("Current player:");
    public JLabel cp=new JLabel();
    public JLabel timer = new JLabel();
    public JLabel countDown = new JLabel();
    private JButton saveButton;
    JButton undo;
    private long programStart = System.currentTimeMillis();
    private long pauseCount = 0;
    public CountingThread thread =new CountingThread();
    private Color surrenderColor;
    private long startTime = System.currentTimeMillis();
    private boolean isREDOvertime = false;
    private boolean isGREENOvertime = false;
    private boolean isOverTimeEnd = false;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isOverTimeEnd() {
        return isOverTimeEnd;
    }

    public void setOverTimeEnd(boolean overTimeEnd) {
        isOverTimeEnd = overTimeEnd;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Color getSurrenderColor() {
        return surrenderColor;
    }

    public void setSurrenderColor(Color surrenderColor) {
        this.surrenderColor = surrenderColor;
    }

    public void status(int mode){setTitle(mode+ (mode == 1?" Player":" Players"));}
    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }
    public void setController(GameController controller) {
        this.controller = controller;
    }
    Image picture= Toolkit.getDefaultToolkit().getImage("dog.gif");

   ImageIcon a= new ImageIcon(picture);


    JLabel label=new JLabel(a);
    public void setCurrentText(){
            if (controller.getCurrentPlayer().equals(Color.RED)) {
                cp.setText("RED");
            }
            if (controller.getCurrentPlayer().equals(Color.BLUE)) {
                cp.setText("BLUE");
            }
            if (controller.getCurrentPlayer().equals(Color.ORANGE)) {
                cp.setText("ORANGE");
            }
            if (controller.getCurrentPlayer().equals(Color.GREEN)) {
                cp.setText("GREEN");
            }
            if (controller.getCurrentPlayer().equals(Color.CYAN)) {
                cp.setText("CYAN");
            }
            if (controller.getCurrentPlayer().equals(Color.BLACK)) {
                cp.setText("BLACK");
            }
            if (controller.getCurrentPlayer().equals(Color.YELLOW)) {
                cp.setText("YELLOW");
            }
            if (controller.getCurrentPlayer().equals(Color.PINK)) {
                cp.setText("PINK");
            }
    }

    public void disableItems(){
        switch (chessBoard.getMode()){
            case 1:
                trusItem.setEnabled(false);
                trusItem.setEnabled(false);
                break;
            case 3:
            case 5:
                tipItem.setEnabled(false);
                trusItem.setEnabled(false);
                saveItem.setEnabled(false);
                break;
            case 4:
                surItem.setEnabled(false);
                tipItem.setEnabled(false);
                trusItem.setEnabled(false);
                undoItem.setEnabled(false);
                saveItem.setEnabled(false);
                break;
        }
    }
    public void enableSpeed(){
        menu.setVisible(true);
    }
    public void disableSave(){
        //if (chessBoard.getMode()==3||chessBoard.getMode()==4){
            saveButton.setEnabled(false);
        }
    public void disablePicture(){
        label.setVisible(false);
    }
    public GameFrame(int mode) {
        this.mode = mode;

        JMenu gameMenu = new JMenu("Game");
        JMenu controlMenu = new JMenu("Control");
        JMenu viewMenu = new JMenu("View");
        JMenu aboutMenu =new JMenu("About");

        menuBar.add(gameMenu);
        menuBar.add(controlMenu);
        menuBar.add(viewMenu);
        menuBar.add(aboutMenu);

        //Game
        menuItem=new JMenuItem("Menu");
        saveItem=new JMenuItem("Save");
        newGame=new JMenuItem("New game");

        menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        saveItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        newGame.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gameMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        gameMenu.add(menuItem);
        gameMenu.addSeparator();
        gameMenu.add(saveItem);
        gameMenu.add(newGame);


        //Control
        undoItem=new JMenuItem("Undo");
        tipItem=new JMenuItem("Tip");
        trusItem=new JMenuItem("Trusteeship");
        surItem = new JMenuItem("Surrender");

        undoItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        trusItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        tipItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        surItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        controlMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        controlMenu.add(undoItem);
        controlMenu.add(trusItem);
        controlMenu.add(tipItem);
        controlMenu.add(surItem);


        //View
        customization.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        viewMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        viewMenu.add(customization);

        //About
        JMenuItem helpItem=new JMenuItem("Help");
        helpItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        JMenuItem infoItem=new JMenuItem("Info");
        infoItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        aboutMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        aboutMenu.add(helpItem);
        aboutMenu.add(infoItem);




        bgm=new BGM(new File("playing.mp3"));
        bgm.start();
        this.setIconImage(new ImageIcon("dog.png").getImage());
        cp.setFont(new Font(null, Font.PLAIN, 13));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setTitle("Halma Game 0_0 We want high score!");
        setSize(760, 845);
        setResizable(false);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);


        statusLabel.setLocation(5, 753);
        statusLabel.setSize(200, 30);
        statusLabel.setFont(new Font(null, Font.PLAIN, 13));
        add(statusLabel);



        cp.setLocation(90,753);
        cp.setSize(200,30);
        cp.setEnabled(true);
        add(cp);

        timer.setLocation(150,753);
        timer.setSize(200,30);
        timer.setEnabled(true);
        timer.setVisible(true);
        add(timer);

        countDown.setLocation(215,753);
        countDown.setSize(200,30);
        countDown.setEnabled(true);
        countDown.setVisible(true);
        if (mode != 1){
            countDown.setVisible(false);
        }
        add(countDown);


        label.setLocation(760*5/18+24,827*5/18+7);
        label.setSize(280,280);
        label.setEnabled(true);
        label.setVisible(true);
        add(label);






        //JButton button = new JButton("");
        saveButton=new JButton("Save game");
        saveButton.setFont(new Font(null, Font.PLAIN, 13));
        saveButton.setLocation(498, 757);
        saveButton.setSize(100, 30);
//        add(saveButton);

        tru.setLocation(404, 757);
        tru.setFont(new Font(null, Font.PLAIN, 13));
        tru.setSize(95, 30);
        tru.setEnabled(false);
//        add(tru);

        undo=new JButton("Undo");
        undo.setLocation(344, 757);
        undo.setFont(new Font(null, Font.PLAIN, 13));
        undo.setSize(60, 30);
//        add(undo);

        menu=new JButton("Speed");
        menu.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        menu.setLocation(627, 757);
        menu.setSize(120, 30);
        menu.setVisible(false);
        add(menu);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame=new JFrame();
                JSlider slider=new JSlider(10,1010,300);
                JButton ok=new JButton("OK");
                JLabel info=new JLabel("    Please choose speed");
                JPanel panel=new JPanel();
                frame.setLocationRelativeTo(null);frame.setSize(210, 194);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                FlowLayout layout=new FlowLayout();
                layout.setVgap(17);
                panel.setLayout(layout);
                ok.setFont(new Font(null, Font.PLAIN, 14));
                slider.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                info.setFont(new Font(null, Font.PLAIN, 15));
                ok.setPreferredSize(new Dimension(80,30));
                info.setPreferredSize(new Dimension(180,35));
                slider.setPreferredSize(new Dimension(160,50));
                slider.setInverted(true);
                Dictionary dict=new Hashtable();
                JLabel easy=new JLabel("Fast");
                easy.setFont(new Font("微软雅黑", Font.PLAIN, 13));
                dict.put(10,easy);;
                JLabel hell=new JLabel("Slow");
                hell.setFont(new Font("微软雅黑", Font.PLAIN, 13));
                dict.put(1010,hell);
                JLabel m=new JLabel("Medium");
                hell.setFont(new Font("微软雅黑", Font.PLAIN, 13));
                dict.put(500,m);


                slider.setLabelTable(dict);
                slider.setMajorTickSpacing(1000);
                slider.setMinorTickSpacing(500);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        AIShow.think=slider.getValue();
                    }
                });

                panel.add(info);
                panel.add(slider);
                //panel.add(ok);
                frame.add(panel);
                frame.setVisible(true);
//                ok.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//
//                    }
//                });
            }
        });

        setJMenuBar(menuBar);


        thread.stopped = false;

        GameFrame g=this;
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessBoard.clear();
                chessBoard.placeInitialPieces();
                chessBoard.moves.clear();
                chessBoard.setMoveNum1(0);
                chessBoard.setMoveNum2(0);
                chessBoard.setMoveNum3(0);
                chessBoard.setMoveNum4(0);
                chessBoard.setMoveNum5(0);
                chessBoard.setMoveNum5(0);
                chessBoard.setMoveNum6(0);
                chessBoard.setMoveNum7(0);
                chessBoard.setMoveNum8(0);
                startTime = System.currentTimeMillis();
            }
        });


        customization.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame customization=new JFrame("Customization");
                customization.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                customization.setLayout(null);
                customization.setSize(350,250);
                customization.setLocationRelativeTo(null);
                
                JLabel chessTheme=new JLabel("Chess theme");
                JLabel boardTheme=new JLabel("Chessboard theme");
                JLabel traceTheme=new JLabel("Trace Theme");

                JRadioButton or = new JRadioButton("Classic");
                JRadioButton hf = new JRadioButton("Happy face");
                JRadioButton sh = new JRadioButton("Shadow");
                ButtonGroup chess=new ButtonGroup();
                chess.add(or);
                chess.add(hf);
                chess.add(sh);
                JRadioButton original = new JRadioButton("Classic");
                JRadioButton peachyPink = new JRadioButton("Peachy pink");
                JRadioButton nightlyBlue = new JRadioButton("Elegant Blue");
                ButtonGroup board=new ButtonGroup();
                board.add(original);
                board.add(peachyPink);
                board.add(nightlyBlue);

                JRadioButton location = new JRadioButton("Location");
                JRadioButton dog      = new JRadioButton("Dog");
                JRadioButton box      = new JRadioButton("Box");
                JRadioButton fruit    =new JRadioButton("Fruits");
                ButtonGroup lastMove=new ButtonGroup();
                lastMove.add(location);
                lastMove.add(dog);
                lastMove.add(box);
                lastMove.add(fruit);




                customization.add(chessTheme);
                customization.add(or);
                customization.add(hf);
                customization.add(sh);
                customization.add(boardTheme);
                customization.add(original);
                customization.add(peachyPink);
                customization.add(nightlyBlue);
                customization.add(traceTheme);
                customization.add(location);
                customization.add(dog);
                customization.add(box);
                customization.add(fruit);


                chessTheme.setLocation(15,5);
                boardTheme.setLocation(15,65);
                traceTheme.setLocation(15,125);
                or.setLocation(15,35);
                hf.setLocation(90,35);
                sh.setLocation(205,35);
                original   .setLocation(15,95);
                peachyPink .setLocation(90,95);
                nightlyBlue.setLocation(205,95);
                location.setLocation(15,155);
                dog     .setLocation(100,155);
                box     .setLocation(165,155);
                fruit   .setLocation(225,155);


                chessTheme.setSize(110,30);
                boardTheme.setSize(160,30);
                traceTheme.setSize(110,30);
                or.setSize(80,30);
                hf.setSize(120,30);
                sh.setSize(120,30);
                original   .setSize(80,30);
                peachyPink .setSize(115,30);
                nightlyBlue.setSize(140,30);
                location   .setSize(80,30);
                dog        .setSize(60,30);
                box        .setSize(60,30);
                fruit      .setSize(80,30);


                chessTheme.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                boardTheme.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                traceTheme.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                or.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                hf.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                sh.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                original      .setFont(new Font("微软雅黑", Font.PLAIN, 14));
                peachyPink    .setFont(new Font("微软雅黑", Font.PLAIN, 15));
                nightlyBlue   .setFont(new Font("微软雅黑", Font.PLAIN, 15));
                 location     .setFont(new Font("微软雅黑", Font.PLAIN, 14));
                 dog          .setFont(new Font("微软雅黑", Font.PLAIN, 15));
                 box          .setFont(new Font("微软雅黑", Font.PLAIN, 15));
                 fruit        .setFont(new Font("微软雅黑", Font.PLAIN, 15));


                 location.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         SquareComponent.lastTheme=0;
                     }
                 });
                 dog.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         SquareComponent.lastTheme=1;
                     }
                 });
                 box.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         SquareComponent.lastTheme=2;
                     }
                 });
                 fruit.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         SquareComponent.lastTheme=3;
                     }
                 });


                original.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessBoardComponent.BOARD_COLOR_1 = new Color(255, 255, 204);
                        ChessBoardComponent.BOARD_COLOR_2 = new Color(170, 170, 170);
                        HoleBoardComponent.BOARD_COLOR_1 = new Color(255, 255, 204);
                        HoleBoardComponent.BOARD_COLOR_2 = new Color(170, 170, 170);
                        JOptionPane.showMessageDialog(null,"Please relaod the game frame to refresh");
                        g.repaint();
                    }
                });

                peachyPink.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessBoardComponent.BOARD_COLOR_1 = new Color(255, 140, 194);
                        ChessBoardComponent.BOARD_COLOR_2 = new Color(203, 190, 220);
                        HoleBoardComponent.BOARD_COLOR_1 = new Color(255, 140, 194);
                        HoleBoardComponent.BOARD_COLOR_2 = new Color(203, 190, 220);  JOptionPane.showMessageDialog(null,"Please relaod the game frame to refresh");
                        g.repaint();
                    }
                });

                nightlyBlue.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessBoardComponent.BOARD_COLOR_1 = new Color(170, 200, 230);
                        ChessBoardComponent.BOARD_COLOR_2 = new Color(230, 250, 230);
                        HoleBoardComponent.BOARD_COLOR_1 = new Color(170, 200, 230);
                        HoleBoardComponent.BOARD_COLOR_2 = new Color(230, 250, 230);
                        JOptionPane.showMessageDialog(null,"Please relaod the game frame to refresh");
                        g.repaint();
                    }
                });


                or.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessComponent.chessTheme=1;
                        g.repaint();
                    }
                });
                hf.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessComponent.chessTheme=2;
                        g.repaint();
                    }
                });
                sh.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessComponent.chessTheme=3;
                        g.repaint();
                    }
                });

                customization.setVisible(true);




            }
        });

        trusItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
          //      System.out.println(chessBoard.getTru());

                controller.resetSelectedLocation();

                if (chessBoard.getTru().equals("N")){

                    if (controller.getCurrentPlayer().equals(Color.RED)) {
                        chessBoard.setTru("R");
                        chessBoard.setMediumAI(true);
                        chessBoard.automaticallyMoveRed();
                        controller.nextPlayer();
                        controller.gameStartedSpecialEvent();

                    }else {
                        chessBoard.setTru("G");
                        chessBoard.setMediumAI(true);
                        chessBoard.automaticallyMoveGreen();
                        controller.nextPlayer();
                        controller.gameStartedSpecialEvent();
                    }
                }else {
                    chessBoard.setTru("N");
                    chessBoard.setMode(1);
                    JOptionPane.showMessageDialog(null,"It's up to you now!");
                    chessBoard.setMediumAI(false);
                }
            }
        });

        tipItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetSelectedLocation();
                if (chessBoard.getMode()==1){
                    if (controller.getCurrentPlayer().equals(Color.RED)) {
                        chessBoard.setHardAI(true);
                        chessBoard.automaticallyMoveRed();
                        chessBoard.setHardAI(false);
                    }else {
                        chessBoard.setHardAI(true);
                        chessBoard.automaticallyMoveGreen();
                        chessBoard.setHardAI(false);
                    }
                    controller.nextPlayer();
                }else if (chessBoard.getMode()==0&&chessBoard.getMoveNum2()!=0) {

                    if (chessBoard.isMediumAI()) {
                        chessBoard.setMediumAI(false);
                        chessBoard.setHardAI(true);
                        chessBoard.automaticallyMoveGreen();
                        Timer timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                chessBoard.automaticallyMoveRed();
                            }
                        },600);
                        chessBoard.setHardAI(false);
                        chessBoard.setMediumAI(true);

                    }else if (chessBoard.isEasyAI()){
                        chessBoard.setEasyAI(false);
                        chessBoard.setHardAI(true);
                        chessBoard.automaticallyMoveGreen();
                        Timer timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                chessBoard.automaticallyMoveRed();
                            }
                        },600);

                        chessBoard.setHardAI(false);
                        chessBoard.setEasyAI(true);
                    }else {
                        chessBoard.automaticallyMoveGreen();
                        Timer timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                chessBoard.automaticallyMoveRed();
                            }
                        },600);
                    }
                }else {
                        JOptionPane.showMessageDialog(null,"Not available now");
                    }
                }

        });

        surItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chessBoard.getMoveNum1() == 0 && chessBoard.getMoveNum2() == 0 && chessBoard.getMoveNum3() == 0 && chessBoard.getMoveNum4() == 0){
                    JOptionPane.showMessageDialog(null,"Not available now");
                    return;
                }
                int i = JOptionPane.showConfirmDialog(null, "Are you sure to give up?", "Message:", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.OK_OPTION){
                    int j = JOptionPane.showConfirmDialog(null, "Your giving up may affects other players, are you sure?", "Message:", JOptionPane.YES_NO_OPTION);
                    if (j == JOptionPane.OK_OPTION){
                        chessBoard.setSurrenderEnd(true);
                        surrenderColor = controller.getCurrentPlayer();
                        chessBoard.endGame();
                    }
                }

            }
        });

        undoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chessBoard.getMode()!=0){
                int error = 0;
                error = chessBoard.undo();
                    Color currentPlayer = controller.getCurrentPlayer();
                if (error == 0) {
                    if (chessBoard.getMode() == 2) {
                        //Color currentPlayer = controller.getCurrentPlayer();
                        if (currentPlayer.equals(Color.RED)) {
                            cp.setText("ORANGE");
                            controller.setCurrentPlayer(Color.ORANGE);
                        }
                        if (currentPlayer.equals(Color.BLUE)) {
                            controller.setCurrentPlayer(Color.RED);
                            cp.setText("RED");
                        }
                        if (currentPlayer.equals(Color.GREEN)) {
                            controller.setCurrentPlayer(Color.BLUE);
                            cp.setText("BLUE");
                        }
                        if (currentPlayer.equals(Color.ORANGE)) {
                            controller.setCurrentPlayer(Color.GREEN);
                            cp.setText("GREEN");
                        }
                    } else if (chessBoard.getMode()==3){
                        if (currentPlayer.equals(Color.PINK)) {
                            controller.setCurrentPlayer(Color.CYAN);
                            cp.setText("CYAN");
                        }
                        if (currentPlayer.equals(Color.CYAN)) {
                            controller.setCurrentPlayer(Color.RED);
                            cp.setText("RED");
                        }
                        if (currentPlayer.equals(Color.RED)) {
                            controller.setCurrentPlayer(Color.BLUE);
                            cp.setText("BLUE");
                        }
                        if (currentPlayer.equals(Color.BLUE)) {
                            controller.setCurrentPlayer(Color.YELLOW);
                            cp.setText("YELLOW");
                        }
                        if (currentPlayer.equals(Color.YELLOW)) {
                            controller.setCurrentPlayer(Color.BLACK);
                            cp.setText("BLACK");
                        }
                        if (currentPlayer.equals(Color.BLACK)) {
                            controller.setCurrentPlayer(Color.GREEN);
                            cp.setText("GREEN");
                        }
                        if (currentPlayer.equals(Color.GREEN)) {
                            controller.setCurrentPlayer(Color.ORANGE);
                            cp.setText("ORANGE");
                        }
                        if (currentPlayer.equals(Color.ORANGE)) {
                            controller.setCurrentPlayer(Color.PINK);
                            cp.setText("PINK");
                        }


                    }else{
                        controller.setCurrentPlayer(controller.nextPlayer());
                        controller.setCurrentPlayer(controller.nextPlayer());
                        if (controller.nextPlayer().equals(Color.RED)) {
                            cp.setText("RED");
                        } else {
                            cp.setText("GREEN");
                        }
                    }

                }
            }else {
                    chessBoard.undo();
                }
        }
       });

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bgm.stop();
                if (chessBoard.getMode()==4){
                    try{
                       controller.getAis().stop();
                    }catch (Exception ais){
                    }
                }
                thread.stopped = true;
                chessBoard.abtw.stop();
                Home home=new Home();
                setVisible(false);
                home.setHome(home);
                home.setVisible(true);

            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileSaver(getParent());
            }

            private void showFileSaver(Component parent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int result = fileChooser.showSaveDialog(parent);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File save=new File(fileChooser.getSelectedFile().getAbsolutePath(), "saving.sav");
                        save.createNewFile();
                        saveToText(save);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(parent, "Game Saved!");
                }

            }


            public void saveToText(File save)  {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(save));

                    BufferedWriter writer=new BufferedWriter(new FileWriter(save));
                    writer.write(String.valueOf(chessBoard.getMode()));
                    writer.flush();
                    if (chessBoard.isEasyAI()){
                        writer.write("E");
                    }else if (chessBoard.isMediumAI()){
                        writer.write("M");
                    }else if (chessBoard.isHardAI()){
                        writer.write("H");
                    }else {
                        writer.write("N");
                    }
                    writer.flush();

                    //record who will play next
                    Color currentPlayer=controller.getCurrentPlayer();
                    if (currentPlayer.equals(Color.RED)){
                        writer.write("RED");
                    }
                    if (currentPlayer.equals(Color.GREEN)){
                        writer.write("GREEN");
                    }
                    if (currentPlayer.equals(Color.BLUE)){
                        writer.write("BLUE");
                    }
                    if (currentPlayer.equals(Color.ORANGE)){
                        writer.write("ORANGE");
                    }


                    //record the board information
                    String info="";
                    for (int i=0;i<chessBoard.getDimension();i++){
                        for (int j=0;j<chessBoard.getDimension();j++){
                            ChessBoardLocation location=new ChessBoardLocation(i,j);
                            if (chessBoard.getChessPieceAt(location)!=null) {
                                if (chessBoard.getChessPieceAt(location).getColor()==Color.RED){
                                    info=location.toString()+"RED";
                                }
                                if (chessBoard.getChessPieceAt(location).getColor()==Color.GREEN){
                                    info=location.toString()+"GREEN";
                                }
                                if (chessBoard.getChessPieceAt(location).getColor()==Color.ORANGE){
                                    info=location.toString()+"ORANGE";
                                }
                                if (chessBoard.getChessPieceAt(location).getColor()==Color.BLUE){
                                    info=location.toString()+"BLUE";
                                }
                                //System.out.println(location.toString()+chessBoard.getChessPieceAt(location).getColor());
                                writer.newLine();
                                writer.write(info);
                                writer.flush();
                            }else {
                                writer.newLine();
                                info=location+"empty";
                                //System.out.println(location+"empty");
                                writer.write(info);

                                writer.flush();
                            }
                        }
                    }
                    writer.newLine();
                    writer.write("END");
                    writer.newLine();
                    writer.flush();
//                    for (String toWrite:chessBoard.moves){
//                        writer.write(toWrite);
//                        writer.flush();
//                        writer.newLine();
//                    }
                    writer.write(Integer.toString(chessBoard.getMoveNum1()));
                    writer.newLine();
                    writer.write(Integer.toString(chessBoard.getMoveNum2()));
                    writer.newLine();
                    if (chessBoard.getMode()==2){
                        writer.write(Integer.toString(chessBoard.getMoveNum3()));
                        writer.newLine();
                        writer.write(Integer.toString(chessBoard.getMoveNum4()));
                        writer.newLine();
                    }
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        });

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame help = new JFrame("Help");
                JPanel panel = new JPanel();
                help.setSize(450,760);
                help.setLocationRelativeTo(null);
                help.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                help.setVisible(true);
                JTextArea helpText = new JTextArea(0,30);
                helpText.setLineWrap(true);
                helpText.setWrapStyleWord(true);
                int line = 0;
                try {
                    BufferedReader br = new BufferedReader(new FileReader("Rules.txt"));
                    for (;;) {
                        String textThisLine = br.readLine();
                        if (textThisLine == null) break;
                        line++;
                        helpText.append(textThisLine);
                        if (line != 14) {
                            helpText.append("\n");
                        }
                    }
                } catch (IOException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                }
                helpText.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                helpText.setEditable(false);
                helpText.setVisible(true);
                panel.add(helpText);
                ImageIcon rulesImage = new ImageIcon("Rules.png");
                JLabel label = new JLabel(rulesImage);
                label.setVisible(true);
                panel.add(label);
                help.add(panel);
                panel.setVisible(true);
                help.setResizable(false);
            }
        });

        infoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame info = new JFrame("Full mark, please?");
                JPanel panel = new JPanel();
                info.setSize(360,400);
                info.setLocationRelativeTo(null);
                info.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                info.setVisible(true);
                JTextArea infoText = new JTextArea(0,24);
                infoText.setLineWrap(true);
                infoText.setWrapStyleWord(true);
                int line = 0;
                try {
                    BufferedReader br = new BufferedReader(new FileReader("Info.txt"));
                    for (;;) {
                        String textThisLine = br.readLine();
                        if (textThisLine == null) break;
                        line++;
                        infoText.append(textThisLine);
                        if (line != 17) {
                            infoText.append("\n");
                        }
                    }
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                infoText.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                infoText.setEditable(false);
                infoText.setVisible(true);
                panel.add(infoText);
                info.add(panel);
                panel.setVisible(true);
                info.setResizable(false);
            }
        });

    }
    public GameFrame(Socket sock) {
//
        final String[] name = {"Anonymity"};

        JFrame frame= new JFrame();
        JTextField inputIP=new JTextField();
        JLabel info=new JLabel("   Please input your name");
        JButton ok=new JButton("OK");

        frame.setSize(200,200);
        inputIP.setFont(new Font(null, Font.PLAIN, 14));
        info.setFont(new Font(null, Font.PLAIN, 12));
        ok.setFont(new Font(null, Font.PLAIN, 14));

        info.setPreferredSize(new Dimension(150,40));
        inputIP.setPreferredSize(new Dimension(150,35));
        ok.setPreferredSize(new Dimension(80,30));

        JPanel panel=new JPanel();
        FlowLayout layout=new FlowLayout();
        panel.setLayout(layout);
        layout.setVgap(10);
        layout.setHgap(5);


        panel.add(info);
        panel.add(inputIP);
        panel.add(ok);
        frame.add(panel);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        GameFrame thisgc=this;
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                if (!inputIP.getText().equals("")) {
                    name[0] = inputIP.getText();
                }
                thisgc.setVisible(true);
            }
        });


        Socket socket=sock;

        displayer = new JTextArea("          Please watch your language!");
        displayer.append("\n");
        JTextArea inputter = new JTextArea("Type the message here");
        JButton sendButton = new JButton("Send");
        displayer.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        inputter.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        sendButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        sendButton.setLocation(960, 713);
        sendButton.setSize(100, 30);

        inputter.setLocation(780, 545);
        inputter.setSize(285,160);

        JScrollPane scrollPane = new JScrollPane(displayer,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setLocation(780, 23);
        scrollPane.setSize(285,500);

        //setContentPane(scrollPane);
        //displayer.setLocation(780, 23);
        //displayer.setSize(285,500);

        add(scrollPane);

        displayer.setBorder(BorderFactory.createEtchedBorder());
        inputter.setBorder(BorderFactory.createEtchedBorder());

        displayer.setEditable(false);
        //add(displayer);
        add(inputter);add(sendButton);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputter.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Cannot send empty message");
                } else{
                    String output = "\n" + name[0] + ":" + "\n" + inputter.getText();
                    displayer.append("\n" + name[0] + ":" + "\n" + inputter.getText());
                    inputter.setText("");
                    Sender4Chat sender = new Sender4Chat(output);
                    sender.start();
                    sender.run();
                }
            }
        });




        JMenu gameMenu = new JMenu("Game");
        JMenu controlMenu = new JMenu("Control");
        JMenu viewMenu = new JMenu("View");
        JMenu aboutMenu =new JMenu("About");

        menuBar.add(gameMenu);
        menuBar.add(controlMenu);
        menuBar.add(viewMenu);
        menuBar.add(aboutMenu);

        //Game
        menuItem=new JMenuItem("Menu");
        saveItem=new JMenuItem("Save");
        newGame=new JMenuItem("New game");


        menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        saveItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        newGame.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gameMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        gameMenu.add(menuItem);
        gameMenu.addSeparator();
        gameMenu.add(saveItem);
        gameMenu.add(newGame);


        //Control
        undoItem=new JMenuItem("Undo");
        tipItem=new JMenuItem("Tip");
        trusItem=new JMenuItem("Trusteeship");
        surItem = new JMenuItem("Surrender");
        surItem.setEnabled(false);

        undoItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        trusItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        tipItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        surItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        controlMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        controlMenu.add(undoItem);
        controlMenu.add(trusItem);
        controlMenu.add(tipItem);
        controlMenu.add(surItem);


        //View
        customization.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        viewMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        viewMenu.add(customization);

        //About
        JMenuItem helpItem=new JMenuItem("Help");
        helpItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        JMenuItem infoItem=new JMenuItem("Info");
        infoItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        aboutMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        aboutMenu.add(helpItem);
        aboutMenu.add(infoItem);




        bgm=new BGM(new File("playing.mp3"));
        bgm.start();
        this.setIconImage(new ImageIcon("dog.png").getImage());
        cp.setFont(new Font(null, Font.PLAIN, 13));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setTitle("Halma Game 0_0 We want high score!");
        setSize(1100, 845);
        setResizable(false);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);


        statusLabel.setLocation(5, 753);
        statusLabel.setSize(200, 30);
        statusLabel.setFont(new Font(null, Font.PLAIN, 13));
        statusLabel.setText("Current Player:");
        cp.setText("RED");
        add(statusLabel);



        cp.setLocation(90,753);
        cp.setSize(200,30);
        cp.setEnabled(true);
        add(cp);

        timer.setLocation(150,753);
        timer.setSize(200,30);
        timer.setEnabled(true);
        timer.setVisible(true);
        add(timer);


        label.setLocation(760*5/18+24,827*5/18+7);
        label.setSize(280,280);
        label.setEnabled(true);
        label.setVisible(true);
        add(label);



//        countDown.setLocation(215,753);
//        countDown.setSize(200,30);
//        countDown.setEnabled(true);
//        countDown.setVisible(true);
//
//        add(countDown);



        //JButton button = new JButton("");
        saveButton=new JButton("Save game");
        saveButton.setFont(new Font(null, Font.PLAIN, 13));
        saveButton.setLocation(498, 757);
        saveButton.setSize(100, 30);
//        add(saveButton);

        tru.setLocation(404, 757);
        tru.setFont(new Font(null, Font.PLAIN, 13));
        tru.setSize(95, 30);
        tru.setEnabled(false);
//        add(tru);

        undo=new JButton("Undo");
        undo.setLocation(344, 757);
        undo.setFont(new Font(null, Font.PLAIN, 13));
        undo.setSize(60, 30);
//        add(undo);

        menu=new JButton("Speed");
        menu.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        menu.setLocation(627, 757);
        menu.setSize(120, 30);
        menu.setVisible(false);
        add(menu);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame=new JFrame();
                JSlider slider=new JSlider(10,1010,300);
                JButton ok=new JButton("OK");
                JLabel info=new JLabel("    Please choose speed");
                JPanel panel=new JPanel();
                frame.setLocationRelativeTo(null);frame.setSize(210, 194);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                FlowLayout layout=new FlowLayout();
                layout.setVgap(17);
                panel.setLayout(layout);
                ok.setFont(new Font(null, Font.PLAIN, 14));
                slider.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                info.setFont(new Font(null, Font.PLAIN, 15));
                ok.setPreferredSize(new Dimension(80,30));
                info.setPreferredSize(new Dimension(180,35));
                slider.setPreferredSize(new Dimension(160,50));
                slider.setInverted(true);
                Dictionary dict=new Hashtable();
                JLabel easy=new JLabel("Fast");
                easy.setFont(new Font("微软雅黑", Font.PLAIN, 13));
                dict.put(10,easy);;
                JLabel hell=new JLabel("Slow");
                hell.setFont(new Font("微软雅黑", Font.PLAIN, 13));
                dict.put(1010,hell);
                JLabel m=new JLabel("Medium");
                hell.setFont(new Font("微软雅黑", Font.PLAIN, 13));
                dict.put(500,m);


                slider.setLabelTable(dict);
                slider.setMajorTickSpacing(1000);
                slider.setMinorTickSpacing(500);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        AIShow.think=slider.getValue();
                    }
                });

                panel.add(info);
                panel.add(slider);
                //panel.add(ok);
                frame.add(panel);
                frame.setVisible(true);
//                ok.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//
//                    }
//                });
            }
        });

        setJMenuBar(menuBar);

        thread.start();
        thread.stopped = false;

        GameFrame g=this;
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessBoard.clear();
                chessBoard.placeInitialPieces();
                chessBoard.moves.clear();
                chessBoard.setMoveNum1(0);
                chessBoard.setMoveNum2(0);
                chessBoard.setMoveNum3(0);
                chessBoard.setMoveNum4(0);
                chessBoard.setMoveNum5(0);
                chessBoard.setMoveNum5(0);
                chessBoard.setMoveNum6(0);
                chessBoard.setMoveNum7(0);
                chessBoard.setMoveNum8(0);
            }
        });

        surItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  if (chessBoard.getMoveNum1() == 0 && chessBoard.getMoveNum2() == 0 && chessBoard.getMoveNum3() == 0 && chessBoard.getMoveNum4() == 0){
                //    JOptionPane.showMessageDialog(null,"Not available now");
//                    return;
//                }
                int i = JOptionPane.showConfirmDialog(null, "Are you sure to give up?", "Message:", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.OK_OPTION){
                    int j = JOptionPane.showConfirmDialog(null, "Your giving up may affects other players, are you sure?", "Message:", JOptionPane.YES_NO_OPTION);
                    if (j == JOptionPane.OK_OPTION){
                        chessBoard.setSurrenderEnd(true);
                        surrenderColor = controller.getCurrentPlayer();
                        Sender4Chat sender = new Sender4Chat("endgame-surrender-encryption-2020052315:39");
                        sender.start();
                        chessBoard.endGame();

                    }
                }

            }
        });

        customization.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame customization=new JFrame("Customization");
                customization.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                customization.setLayout(null);
                customization.setSize(350,250);
                customization.setLocationRelativeTo(null);

                JLabel chessTheme=new JLabel("Chess theme");
                JLabel boardTheme=new JLabel("Chessboard theme");
                JLabel traceTheme=new JLabel("Trace Theme");

                JRadioButton or = new JRadioButton("Classic");
                JRadioButton hf = new JRadioButton("Happy face");
                JRadioButton sh = new JRadioButton("Shadow");
                JRadioButton emoji = new JRadioButton("Emoji");



                ButtonGroup chess=new ButtonGroup();
                chess.add(or);
                chess.add(hf);
                chess.add(sh);
                chess.add(emoji);
                JRadioButton original = new JRadioButton("Classic");
                JRadioButton peachyPink = new JRadioButton("Peachy pink");
                JRadioButton nightlyBlue = new JRadioButton("Elegant Blue");
                ButtonGroup board=new ButtonGroup();
                board.add(original);
                board.add(peachyPink);
                board.add(nightlyBlue);

                JRadioButton location = new JRadioButton("Location");
                JRadioButton dog      = new JRadioButton("Dog");
                JRadioButton box      = new JRadioButton("Box");
                JRadioButton fruit    =new JRadioButton("Fruits");
                ButtonGroup lastMove=new ButtonGroup();
                lastMove.add(location);
                lastMove.add(dog);
                lastMove.add(box);
                lastMove.add(fruit);




                customization.add(chessTheme);
                customization.add(or);
                customization.add(hf);
                customization.add(sh);
                customization.add(emoji);
                customization.add(boardTheme);
                customization.add(original);
                customization.add(peachyPink);
                customization.add(nightlyBlue);
                customization.add(traceTheme);
                customization.add(location);
                customization.add(dog);
                customization.add(box);
                customization.add(fruit);


                chessTheme.setLocation(15,5);
                boardTheme.setLocation(15,65);
                traceTheme.setLocation(15,125);
                or.setLocation(15,35);
                hf.setLocation(90,35);
                sh.setLocation(205,35);
                emoji.setLocation(235,35);
                original   .setLocation(15,95);
                peachyPink .setLocation(90,95);
                nightlyBlue.setLocation(205,95);
                location.setLocation(15,155);
                dog     .setLocation(100,155);
                box     .setLocation(165,155);
                fruit   .setLocation(225,155);


                chessTheme.setSize(110,30);
                boardTheme.setSize(160,30);
                traceTheme.setSize(110,30);
                or.setSize(80,30);
                hf.setSize(120,30);
                sh.setSize(120,30);
                emoji.setSize(120,30);
                original   .setSize(80,30);
                peachyPink .setSize(115,30);
                nightlyBlue.setSize(140,30);
                location   .setSize(80,30);
                dog        .setSize(60,30);
                box        .setSize(60,30);
                fruit      .setSize(80,30);


                chessTheme.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                boardTheme.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                traceTheme.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                or.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                hf.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                sh.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                emoji.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                original      .setFont(new Font("微软雅黑", Font.PLAIN, 14));
                peachyPink    .setFont(new Font("微软雅黑", Font.PLAIN, 15));
                nightlyBlue   .setFont(new Font("微软雅黑", Font.PLAIN, 15));
                location     .setFont(new Font("微软雅黑", Font.PLAIN, 14));
                dog          .setFont(new Font("微软雅黑", Font.PLAIN, 15));
                box          .setFont(new Font("微软雅黑", Font.PLAIN, 15));
                fruit        .setFont(new Font("微软雅黑", Font.PLAIN, 15));


                location.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SquareComponent.lastTheme=0;
                    }
                });
                dog.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SquareComponent.lastTheme=1;
                    }
                });
                box.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SquareComponent.lastTheme=2;
                    }
                });
                fruit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SquareComponent.lastTheme=3;
                    }
                });


                original.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessBoardComponent.BOARD_COLOR_1 = new Color(255, 255, 204);
                        ChessBoardComponent.BOARD_COLOR_2 = new Color(170, 170, 170);
                        HoleBoardComponent.BOARD_COLOR_1 = new Color(255, 255, 204);
                        HoleBoardComponent.BOARD_COLOR_2 = new Color(170, 170, 170);
                        JOptionPane.showMessageDialog(null,"Please relaod the game frame to refresh");
                        g.repaint();
                    }
                });

                peachyPink.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessBoardComponent.BOARD_COLOR_1 = new Color(255, 140, 194);
                        ChessBoardComponent.BOARD_COLOR_2 = new Color(203, 190, 220);
                        HoleBoardComponent.BOARD_COLOR_1 = new Color(255, 140, 194);
                        HoleBoardComponent.BOARD_COLOR_2 = new Color(203, 190, 220);  JOptionPane.showMessageDialog(null,"Please relaod the game frame to refresh");
                        g.repaint();
                    }
                });

                nightlyBlue.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessBoardComponent.BOARD_COLOR_1 = new Color(170, 200, 230);
                        ChessBoardComponent.BOARD_COLOR_2 = new Color(230, 250, 230);
                        HoleBoardComponent.BOARD_COLOR_1 = new Color(170, 200, 230);
                        HoleBoardComponent.BOARD_COLOR_2 = new Color(230, 250, 230);
                        JOptionPane.showMessageDialog(null,"Please relaod the game frame to refresh");
                        g.repaint();
                    }
                });


                or.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessComponent.chessTheme=1;
                        g.repaint();
                    }
                });
                hf.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessComponent.chessTheme=2;
                        g.repaint();
                    }
                });
                sh.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessComponent.chessTheme=3;
                        g.repaint();
                    }
                });

                customization.setVisible(true);




            }
        });

        trusItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        //        System.out.println(chessBoard.getTru());

                controller.resetSelectedLocation();

                if (chessBoard.getTru().equals("N")){

                    if (controller.getCurrentPlayer().equals(Color.RED)) {
                        chessBoard.setTru("R");
                        chessBoard.setMediumAI(true);
                        chessBoard.automaticallyMoveRed();
                        controller.nextPlayer();
                        controller.gameStartedSpecialEvent();

                    }else {
                        chessBoard.setTru("G");
                        chessBoard.setMediumAI(true);
                        chessBoard.automaticallyMoveGreen();
                        controller.nextPlayer();
                        controller.gameStartedSpecialEvent();
                    }
                }else {
                    chessBoard.setTru("N");
                    chessBoard.setMode(1);
                    JOptionPane.showMessageDialog(null,"It's up to you now!");
                    chessBoard.setMediumAI(false);
                }
            }
        });

        tipItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetSelectedLocation();
                if (chessBoard.getMode()==1){
                    if (controller.getCurrentPlayer().equals(Color.RED)) {
                        chessBoard.setHardAI(true);
                        chessBoard.automaticallyMoveRed();
                        chessBoard.setHardAI(false);
                    }else {
                        chessBoard.setHardAI(true);
                        chessBoard.automaticallyMoveGreen();
                        chessBoard.setHardAI(false);
                    }
                    controller.nextPlayer();
                }else if (chessBoard.getMode()==0&&chessBoard.getMoveNum2()!=0) {

                    if (chessBoard.isMediumAI()) {
                        chessBoard.setMediumAI(false);
                        chessBoard.setHardAI(true);
                        chessBoard.automaticallyMoveGreen();
                        Timer timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                chessBoard.automaticallyMoveRed();
                            }
                        },600);
                        chessBoard.setHardAI(false);
                        chessBoard.setMediumAI(true);

                    }else if (chessBoard.isEasyAI()){
                        chessBoard.setEasyAI(false);
                        chessBoard.setHardAI(true);
                        chessBoard.automaticallyMoveGreen();
                        Timer timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                chessBoard.automaticallyMoveRed();
                            }
                        },600);

                        chessBoard.setHardAI(false);
                        chessBoard.setEasyAI(true);
                    }else {
                        chessBoard.automaticallyMoveGreen();
                        Timer timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                chessBoard.automaticallyMoveRed();
                            }
                        },600);
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Not available now");
                }
            }

        });

        undoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             //   System.out.println(chessBoard.getMode());
                if (chessBoard.getMode()!=0){
                    int error = 0;
                    error = chessBoard.undo();
                    Color currentPlayer = controller.getCurrentPlayer();
                    if (error == 0) {
                        if (chessBoard.getMode() == 2) {
                            //Color currentPlayer = controller.getCurrentPlayer();
                            if (currentPlayer.equals(Color.RED)) {
                                cp.setText("ORANGE");
                                controller.setCurrentPlayer(Color.ORANGE);
                            }
                            if (currentPlayer.equals(Color.BLUE)) {
                                controller.setCurrentPlayer(Color.RED);
                                cp.setText("RED");
                            }
                            if (currentPlayer.equals(Color.GREEN)) {
                                controller.setCurrentPlayer(Color.BLUE);
                                cp.setText("BLUE");
                            }
                            if (currentPlayer.equals(Color.ORANGE)) {
                                controller.setCurrentPlayer(Color.GREEN);
                                cp.setText("GREEN");
                            }
                        } else if (chessBoard.getMode()==3){
                            if (currentPlayer.equals(Color.PINK)) {
                                controller.setCurrentPlayer(Color.CYAN);
                                cp.setText("CYAN");
                            }
                            if (currentPlayer.equals(Color.CYAN)) {
                                controller.setCurrentPlayer(Color.RED);
                                cp.setText("RED");
                            }
                            if (currentPlayer.equals(Color.RED)) {
                                controller.setCurrentPlayer(Color.BLUE);
                                cp.setText("BLUE");
                            }
                            if (currentPlayer.equals(Color.BLUE)) {
                                controller.setCurrentPlayer(Color.YELLOW);
                                cp.setText("YELLOW");
                            }
                            if (currentPlayer.equals(Color.YELLOW)) {
                                controller.setCurrentPlayer(Color.BLACK);
                                cp.setText("BLACK");
                            }
                            if (currentPlayer.equals(Color.BLACK)) {
                                controller.setCurrentPlayer(Color.GREEN);
                                cp.setText("GREEN");
                            }
                            if (currentPlayer.equals(Color.GREEN)) {
                                controller.setCurrentPlayer(Color.ORANGE);
                                cp.setText("ORANGE");
                            }
                            if (currentPlayer.equals(Color.ORANGE)) {
                                controller.setCurrentPlayer(Color.PINK);
                                cp.setText("PINK");
                            }


                        }else{
                            controller.setCurrentPlayer(controller.nextPlayer());
                            controller.setCurrentPlayer(controller.nextPlayer());
                            if (controller.nextPlayer().equals(Color.RED)) {
                                cp.setText("RED");
                            } else {
                                cp.setText("GREEN");
                            }
                        }

                    }
                }else {
                    chessBoard.undo();
                }
            }
        });

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bgm.stop();
                if (chessBoard.getMode()==4){
                    try{
                        controller.getAis().stop();
                    }catch (Exception ais){
                    }
                }
                chessBoard.abtw.stop();
                Home home=new Home();
                setVisible(false);
                home.setHome(home);
                home.setVisible(true);

            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileSaver(getParent());
            }

            private void showFileSaver(Component parent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int result = fileChooser.showSaveDialog(parent);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File save=new File(fileChooser.getSelectedFile().getAbsolutePath(),"saving.sav");
                        save.createNewFile();
                        saveToText(save);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(parent, "Game Saved!");
                }

            }


            public void saveToText(File save)  {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(save));

                    BufferedWriter writer=new BufferedWriter(new FileWriter(save));
                    writer.write(String.valueOf(chessBoard.getMode()));
                    writer.flush();
                    if (chessBoard.isEasyAI()){
                        writer.write("E");
                    }else if (chessBoard.isMediumAI()){
                        writer.write("M");
                    }else if (chessBoard.isHardAI()){
                        writer.write("H");
                    }else {
                        writer.write("N");
                    }
                    writer.flush();

                    //record who will play next
                    Color currentPlayer=controller.getCurrentPlayer();
                    if (currentPlayer.equals(Color.RED)){
                        writer.write("RED");
                    }
                    if (currentPlayer.equals(Color.GREEN)){
                        writer.write("GREEN");
                    }
                    if (currentPlayer.equals(Color.BLUE)){
                        writer.write("BLUE");
                    }
                    if (currentPlayer.equals(Color.ORANGE)){
                        writer.write("ORANGE");
                    }


                    //record the board information
                    String info="";
                    for (int i=0;i<chessBoard.getDimension();i++){
                        for (int j=0;j<chessBoard.getDimension();j++){
                            ChessBoardLocation location=new ChessBoardLocation(i,j);
                            if (chessBoard.getChessPieceAt(location)!=null) {
                                if (chessBoard.getChessPieceAt(location).getColor()==Color.RED){
                                    info=location.toString()+"RED";
                                }
                                if (chessBoard.getChessPieceAt(location).getColor()==Color.GREEN){
                                    info=location.toString()+"GREEN";
                                }
                                if (chessBoard.getChessPieceAt(location).getColor()==Color.ORANGE){
                                    info=location.toString()+"ORANGE";
                                }
                                if (chessBoard.getChessPieceAt(location).getColor()==Color.BLUE){
                                    info=location.toString()+"BLUE";
                                }
                                //System.out.println(location.toString()+chessBoard.getChessPieceAt(location).getColor());
                                writer.newLine();
                                writer.write(info);
                                writer.flush();
                            }else {
                                writer.newLine();
                                info=location+"empty";
                                //System.out.println(location+"empty");
                                writer.write(info);

                                writer.flush();
                            }
                        }
                    }
                    writer.newLine();
                    writer.write("END");
                    writer.newLine();
                    writer.flush();
//                    for (String toWrite:chessBoard.moves){
//                        writer.write(toWrite);
//                        writer.flush();
//                        writer.newLine();
//                    }
                    writer.write(Integer.toString(chessBoard.getMoveNum1()));
                    writer.newLine();
                    writer.write(Integer.toString(chessBoard.getMoveNum2()));
                    writer.newLine();
                    if (chessBoard.getMode()==2){
                        writer.write(Integer.toString(chessBoard.getMoveNum3()));
                        writer.newLine();
                        writer.write(Integer.toString(chessBoard.getMoveNum4()));
                        writer.newLine();
                    }
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        });

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame help = new JFrame("Help");
                JPanel panel = new JPanel();
                help.setSize(450,760);
                help.setLocationRelativeTo(null);
                help.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                help.setVisible(true);
                JTextArea helpText = new JTextArea(0,30);
                helpText.setLineWrap(true);
                helpText.setWrapStyleWord(true);
                int line = 0;
                try {
                    BufferedReader br = new BufferedReader(new FileReader("Rules.txt"));
                    for (;;) {
                        String textThisLine = br.readLine();
                        if (textThisLine == null) break;
                        line++;
                        helpText.append(textThisLine);
                        if (line != 14) {
                            helpText.append("\n");
                        }
                    }
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                helpText.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                helpText.setEditable(false);
                helpText.setVisible(true);
                panel.add(helpText);
                ImageIcon rulesImage = new ImageIcon("Rules.png");
                JLabel label = new JLabel(rulesImage);
                label.setVisible(true);
                panel.add(label);
                help.add(panel);
                panel.setVisible(true);
                help.setResizable(false);
            }
        });

        infoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame info = new JFrame("Full mark, please?");
                JPanel panel = new JPanel();
                info.setSize(360,400);
                info.setLocationRelativeTo(null);
                info.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                info.setVisible(true);
                JTextArea infoText = new JTextArea(0,24);
                infoText.setLineWrap(true);
                infoText.setWrapStyleWord(true);
                int line = 0;
                try {
                    BufferedReader br = new BufferedReader(new FileReader("Info.txt"));
                    for (;;) {
                        String textThisLine = br.readLine();
                        if (textThisLine == null) break;
                        line++;
                        infoText.append(textThisLine);
                        if (line != 17) {
                            infoText.append("\n");
                        }
                    }
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                infoText.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                infoText.setEditable(false);
                infoText.setVisible(true);
                panel.add(infoText);
                info.add(panel);
                panel.setVisible(true);
                info.setResizable(false);
            }
        });
    }

//    public static void main(String[] args) {
//        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
//        ChessBoard chessBoard = new ChessBoard(16,1);
//        GameController controller = new GameController(chessBoardComponent, chessBoard);
//        GameFrame mainFrame = new GameFrame(null);
//        mainFrame.enableTru();
//        mainFrame.status(2);
//        mainFrame.disablePicture();
//        mainFrame.add(chessBoardComponent);
//        mainFrame.setController(controller);
//        mainFrame.setChessBoard(chessBoard);
//        chessBoard.setGameFrame(mainFrame);
//        mainFrame.setCurrentText();
//    }

    public void enableTru(){
        this.tru.setEnabled(true);
    }
    public void stopTimer(){
        thread.stopped = true;
    }

    public void disableUndo() {
        this.undo.setEnabled(false);
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
    public class CountingThread extends Thread {
        public boolean stopped = true;
        private long time1;
        private long time2;
        private int counter = 0;
        private CountingThread() {
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                if (!stopped) {
                    long elapsed = System.currentTimeMillis() - programStart - pauseCount;
                    long countDownTime = System.currentTimeMillis() - startTime;
                    if (mode == 1) {
                        if (countDownTime > 20250 && countDownTime < 20500) {
                            JOptionPane.showMessageDialog(null, "10 seconds left, be quick!");
                            time1 = System.currentTimeMillis();
                        }
                        if (countDownTime > 30000) {
                            if ((controller.getCurrentPlayer() == Color.RED && isGREENOvertime) || (controller.getCurrentPlayer() == Color.GREEN) && isREDOvertime) {
                                isOverTimeEnd = true;
                                chessBoard.endGame();
                            } else {
                                if (controller.getCurrentPlayer().equals(Color.RED)) isREDOvertime = true;
                                if (controller.getCurrentPlayer().equals(Color.GREEN)) isGREENOvertime = true;
                               // System.out.println(chessBoard.getTru());
                                controller.resetSelectedLocation();
                                if (chessBoard.getTru().equals("N")) {

                                    if (controller.getCurrentPlayer().equals(Color.RED)) {
                                        chessBoard.setTru("R");
                                        chessBoard.setMediumAI(true);
                                        chessBoard.automaticallyMoveRed();
                                        controller.nextPlayer();
                                        controller.gameStartedSpecialEvent();

                                    } else {
                                        chessBoard.setTru("G");
                                        chessBoard.setMediumAI(true);
                                        chessBoard.automaticallyMoveGreen();
                                        controller.nextPlayer();
                                        controller.gameStartedSpecialEvent();
                                    }
                                }
                                startTime = System.currentTimeMillis();
                            }
                        }
                    }
                    countDown.setText(format2(countDownTime));
                    timer.setText(format1(elapsed));
                }
                try {
                    sleep(250); // 250毫秒更新一次显示
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }

        // 将毫秒数格式化
        private String format1(long elapsed) {
            int hour, minute, second;
            elapsed = elapsed / 1000;
            second = (int) (elapsed % 60);
            elapsed = elapsed / 60;
            minute = (int) (elapsed % 60);
            elapsed = elapsed / 60;
            hour = (int) (elapsed % 60);
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }

        private String format2(long countDownTime) {
            int second;
            second = 30 - (int) countDownTime / 1000;
            return String.format("%02d", second);
        }
    }
    public void onlineDisable(){
        trusItem.setEnabled(false);
        tipItem.setEnabled(false);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    setVisible(false);
                    ChooseMode cm=new ChooseMode();
                    cm.setVisible(true);
                    bgm.stop();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
    }
}


