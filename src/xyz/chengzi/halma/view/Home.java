package xyz.chengzi.halma.view;


import xyz.chengzi.halma.BGM;
import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;


public class Home extends JFrame {



    private JLabel welcome=new JLabel("                 Play Offline");
    private JButton leisureMode = new JButton("Leisure mode");
    private JButton fourP = new JButton("Four players");
    private JButton twoP = new JButton("Two players");
    private JButton oneP = new JButton("Single player");
    private JButton load = new JButton("Load Game");
    private JButton rank = new JButton("Ranking");
    private JButton chooseMode=new JButton("Choose Mode");
    private JFrame home;
    public static BGM player;


    public void setHome(JFrame home) {
        this.home = home;
    }

    public Home() {
        File bgm=new File("bgm.mp3");
        player=new BGM(bgm);
        player.start();
        this.setIconImage(new ImageIcon("game.png").getImage());

        welcome.setBackground(Color.BLACK);
        welcome.setForeground(Color.ORANGE);
        welcome.setForeground(new Color(255,105,20));

        setTitle("Halma");
        setSize(360,328);
        setResizable(false);

        class picPanel extends JPanel{
            @Override
            public Component add(Component comp) {
                return super.add(comp);
            }

            @Override
            public void setLayout(LayoutManager mgr) {
                super.setLayout(mgr);
            }

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                int x=0,y=0;
                java.net.URL imageURL=getClass().getResource("res/home.jpg");
                ImageIcon icon=new ImageIcon("res/home.jpg");
                g.drawImage(icon.getImage(), x,y, getSize().width, getSize().height, this);
                while(true){
                    g.drawImage(icon.getImage(), x, y, this);
                    if(x>getSize().width&&y>getSize().height)
                        break;
                    if(x>getSize().width){
                        x=0;
                        y+=icon.getIconHeight();

                    }else{
                        x+=icon.getIconWidth();
                    }
                }

            }
        }

        JPanel panel = new picPanel(); // Use the panel to group buttons
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //GridLayout layout=new GridLayout(4,2,5,5);
        FlowLayout layout=new FlowLayout();
        layout.setVgap(20);
        layout.setHgap(5);
        panel.setLayout(layout);
        welcome.setPreferredSize(new Dimension(260,25));
        welcome.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        oneP.setPreferredSize(new Dimension(150,40));
        oneP.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        twoP.setPreferredSize(new Dimension(150,40));
        twoP.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        fourP.setPreferredSize(new Dimension(150,40));
        fourP.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        leisureMode.setPreferredSize(new Dimension(150,40));
        leisureMode.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        load.setPreferredSize(new Dimension(150,40));
        load.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        rank.setPreferredSize(new Dimension(150,40));
        rank.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        chooseMode.setPreferredSize(new Dimension(150,40));
        chooseMode.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        welcome.setFont(new Font("微软雅黑", Font.PLAIN, 17));

        Component component;
        DropTargetListener dropTargetListener;
        DropTarget dropTarget=new DropTarget(panel, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {

            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragExit(DropTargetEvent dte) {

            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    try {
                        List<File> list =  (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        File file=list.get(0);
                        try {
                            readSaving(file);
                        }catch (NumberFormatException numberFormatException){
                            JOptionPane.showMessageDialog(null,"File not supported");
                        }
                    } catch (UnsupportedFlavorException | IOException e) {
                        JOptionPane.showMessageDialog(null,"File not supported");
                    }
                }
            }
        });

        panel.add(welcome);
        panel.add(oneP);
        panel.add(twoP);
        panel.add(fourP);
        panel.add(leisureMode);
        panel.add(load);
        panel.add(rank);
        panel.add(chooseMode);

        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setContentPane(panel);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChooseFile(home);
            }
        });
        oneP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame=new JFrame();
                JSlider slider=new JSlider(1,3,2);
                JButton ok=new JButton("OK");
                JLabel info=new JLabel("   Please choose difficulty");
                JPanel panel=new JPanel();
                frame.setSize(210, 220);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                FlowLayout layout=new FlowLayout();
                layout.setVgap(17);
                panel.setLayout(layout);
                ok.setFont(new Font(null, Font.PLAIN, 14));
                slider.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                info.setFont(new Font(null, Font.PLAIN, 15));
                ok.setPreferredSize(new Dimension(80,30));
                info.setPreferredSize(new Dimension(180,35));
                slider.setPreferredSize(new Dimension(120,40));


                Dictionary dict=new Hashtable();
                for (int i=1;i<=3;i++){
                    switch (i){
                        case 1:
                            JLabel easy=new JLabel("Easy");
                            easy.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                            dict.put(1,easy);

                            break;
                        case 2:
                            JLabel med=new JLabel("Medium");
                            med.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                            dict.put(2,med);
                            break;
                        case 3:
                            JLabel hell=new JLabel("Hell");
                            hell.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                            dict.put(3,hell);
                            break;
                    }
                }
                slider.setLabelTable(dict);
                slider.setMajorTickSpacing(1);
                slider.setSnapToTicks(true);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                    }
                });


                panel.add(info);
                panel.add(slider);
                panel.add(ok);
                frame.add(panel);
                frame.setVisible(true);
                ok.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int diff=slider.getValue();
                        frame.setVisible(false);
                        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
                        ChessBoard chessBoard = new ChessBoard(16,0);
                        GameController controller = new GameController(chessBoardComponent, chessBoard);
                        GameFrame mainFrame = new GameFrame(0);
                        mainFrame.status(1);
                        mainFrame.add(chessBoardComponent);
                        mainFrame.setController(controller);
                        mainFrame.setChessBoard(chessBoard);
                        switch (diff){
                            case 1:
                                chessBoard.setHardAI(false);
                                chessBoard.setMediumAI(false);
                                chessBoard.setEasyAI(true);
                                break;
                            case 2:
                                chessBoard.setHardAI(false);
                                chessBoard.setMediumAI(true);
                                chessBoard.setEasyAI(false);
                                break;
                            case 3:
                                chessBoard.setHardAI(true);
                                chessBoard.setMediumAI(false);
                                chessBoard.setEasyAI(false);
                                break;
                        }
                        chessBoard.setGameFrame(mainFrame);
                        mainFrame.setCurrentText();
                        mainFrame.disablePicture();
                        mainFrame.setVisible(true);
                        mainFrame.disableItems();
                        mainFrame.trusItem.setEnabled(false);
                        turnToPlay(controller.getCurrentPlayer());
                        home.setVisible(false);
                        player.stop();
                        controller.gameStartedSpecialEvent();
                    }
                });


            }
        });
        twoP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
                ChessBoard chessBoard = new ChessBoard(16,1);
                GameController controller = new GameController(chessBoardComponent, chessBoard);
                GameFrame mainFrame = new GameFrame(1);
                mainFrame.enableTru();
                mainFrame.status(2);
                mainFrame.disablePicture();
                mainFrame.add(chessBoardComponent);
                mainFrame.setController(controller);
                mainFrame.setChessBoard(chessBoard);
                chessBoard.setGameFrame(mainFrame);
                mainFrame.setCurrentText();
                mainFrame.setVisible(true);
                turnToPlay(controller.getCurrentPlayer());
                home.setVisible(false);
                player.stop();
            }
        });
        fourP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
                ChessBoard chessBoard = new ChessBoard(16,2);
                GameController controller = new GameController(chessBoardComponent, chessBoard);
                GameFrame mainFrame = new GameFrame(2);
                mainFrame.status(4);
                mainFrame.add(chessBoardComponent);
                mainFrame.setController(controller);
                mainFrame.setChessBoard(chessBoard);
                mainFrame.disablePicture();
                mainFrame.tipItem.setEnabled(false);
                mainFrame.trusItem.setEnabled(false);
                chessBoard.setGameFrame(mainFrame);
                mainFrame.setCurrentText();
                mainFrame.setVisible(true);
                turnToPlay(controller.getCurrentPlayer());
                home.setVisible(false);
                player.stop();
            }
        });
        leisureMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame leisureFrame=new JFrame();
                leisureFrame.setIconImage(new ImageIcon("dog.png").getImage());
                JButton ep=new JButton("Eight players");
                JButton ai=new JButton("AI show");
                JButton le=new JButton("Challenges");
                JButton co = new JButton("Holeboard");
                leisureFrame.setTitle("");
                leisureFrame.setSize(280,190);
                JPanel panel=new JPanel();
                FlowLayout layout1=new FlowLayout();
                leisureFrame.setLocationRelativeTo(null); // Center the window
                leisureFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                layout1.setVgap(3);
                layout1.setHgap(12);
                panel.setLayout(layout);
                ep.setPreferredSize(new Dimension(110,50));
                ep.setFont(new Font(null, Font.PLAIN, 13));
                ai.setPreferredSize(new Dimension(110,50));
                ai.setFont(new Font(null, Font.PLAIN, 13));
                le.setPreferredSize(new Dimension(110,50));
                le.setFont(new Font(null, Font.PLAIN, 13));
                co.setPreferredSize(new Dimension(110,50));
                co.setFont(new Font(null, Font.PLAIN, 13));
                panel.add(ai);
                panel.add(ep);
                panel.add(co);
                panel.add(le);
                leisureFrame.add(panel);
                leisureFrame.setVisible(true);
                leisureFrame.setTitle("Leisure mode");
                ep.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
                        ChessBoard chessBoard = new ChessBoard(16,3);
                        GameController controller = new GameController(chessBoardComponent, chessBoard);
                        GameFrame mainFrame = new GameFrame(3);
                        mainFrame.status(8);
                        mainFrame.add(chessBoardComponent);
                        mainFrame.setController(controller);
                        mainFrame.setChessBoard(chessBoard);
                        mainFrame.disablePicture();
                        mainFrame.disableItems();
                        chessBoard.setGameFrame(mainFrame);
                        mainFrame.setCurrentText();
                        mainFrame.setVisible(true);
                        turnToPlay(controller.getCurrentPlayer());
                        home.setVisible(false);
                        leisureFrame.setVisible(false);
                        player.stop();
                    }
                });
                ai.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {


                        JFrame frame=new JFrame();
                        JLabel red=new JLabel("RED");
                        JLabel green=new JLabel("  GREEN");
                        JSlider slider1=new JSlider(1,3,2);
                        JSlider slider2=new JSlider(1,3,2);
                        JButton ok=new JButton("OK");
                        JLabel info=new JLabel("    Please choose difficulty");
                        JPanel panel=new JPanel();
                        frame.setSize(230, 300);
                        frame.setLocationRelativeTo(null);
                        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        FlowLayout layout=new FlowLayout();
                        layout.setVgap(17);
                        panel.setLayout(layout);
                        ok.setFont(new Font(null, Font.PLAIN, 14));
                        info.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                        red.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                        green.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                        slider1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                        slider2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                        info.setFont(new Font(null, Font.PLAIN, 15));
                        ok.setPreferredSize(new Dimension(80,30));
                        info.setPreferredSize(new Dimension(188,35));
                        red.setPreferredSize(new Dimension(60,35));
                        green.setPreferredSize(new Dimension(60,35));
                        slider1.setPreferredSize(new Dimension(90,85));
                        slider2.setPreferredSize(new Dimension(90,85));


                        Dictionary dict=new Hashtable();
                        for (int i=1;i<=3;i++){
                            switch (i){
                                case 1:
                                    JLabel easy=new JLabel("Easy");
                                    easy.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                                    dict.put(1,easy);
                                    break;
                                case 2:
                                    JLabel med=new JLabel("Medium");
                                    med.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                                    dict.put(2,med);
                                    break;
                                case 3:
                                    JLabel hell=new JLabel("Hell");
                                    hell.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                                    dict.put(3,hell);
                                    break;
                            }
                        }
                        slider1.setOrientation(SwingConstants.VERTICAL);
                        slider1.setLabelTable(dict);
                        slider1.setMajorTickSpacing(1);
                        slider1.setSnapToTicks(true);
                        slider1.setPaintTicks(true);
                        slider1.setPaintLabels(true);
                        slider2.setOrientation(SwingConstants.VERTICAL);
                        slider2.setLabelTable(dict);
                        slider2.setMajorTickSpacing(1);
                        slider2.setSnapToTicks(true);
                        slider2.setPaintTicks(true);
                        slider2.setPaintLabels(true);



                        panel.add(info);
                        panel.add(red);
                        panel.add(green);
                        panel.add(slider1);
                        panel.add(slider2);
                        panel.add(ok);
                        frame.add(panel);
                        frame.setVisible(true);
                        ok.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int diff1=slider1.getValue();
                                int diff2=slider2.getValue();
                                frame.setVisible(false);
                                ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
                                ChessBoard chessBoard = new ChessBoard(16,4);
                                GameController controller = new GameController(chessBoardComponent, chessBoard);
                                GameFrame mainFrame = new GameFrame(4);
                                mainFrame.status(1);
                                mainFrame.setTitle("AI Show");
                                mainFrame.enableSpeed();
                                mainFrame.disableUndo();
                                mainFrame.add(chessBoardComponent);
                                mainFrame.setController(controller);
                                mainFrame.setChessBoard(chessBoard);
                                switch (diff1){
                                    case 1:
                                        chessBoard.setMode4Difficulty(10);
                                        break;
                                    case 2:
                                        chessBoard.setMode4Difficulty(20);
                                        break;
                                    case 3:
                                        chessBoard.setMode4Difficulty(30);
                                        break;
                                }
                                switch (diff2){
                                    case 1:
                                        chessBoard.setMode4Difficulty(chessBoard.getMode4Difficulty()+1);
                                        break;
                                    case 2:
                                        chessBoard.setMode4Difficulty(chessBoard.getMode4Difficulty()+2);
                                        break;
                                    case 3:
                                        chessBoard.setMode4Difficulty(chessBoard.getMode4Difficulty()+3);
                                        break;
                                }
                                leisureFrame.setVisible(false);
                                chessBoard.setGameFrame(mainFrame);
                                mainFrame.setCurrentText();
                                mainFrame.disablePicture();
                                mainFrame.disableItems();
                                mainFrame.setVisible(true);
                                //turnToPlay(controller.getCurrentPlayer());
                                home.setVisible(false);
                                player.stop();
                                controller.gameStartedSpecialEvent();
                            }
                        });


                        //JOptionPane.showMessageDialog(null,"Under construction");
                    }
                });

                le.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Challenge challenge=new Challenge((Home) home,leisureFrame);
                        leisureFrame.setVisible(false);
                        challenge.setVisible(true);
                    }
                });

                co.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        HoleBoardComponent chessBoardComponent = new HoleBoardComponent(760, 16);
                        ChessBoard chessBoard = new ChessBoard(16,5);
                        GameController controller = new GameController(chessBoardComponent, chessBoard);
                        GameFrame mainFrame = new GameFrame(5);
                        mainFrame.status(2);
                        mainFrame.add(chessBoardComponent);
                        mainFrame.setController(controller);
                        mainFrame.setChessBoard(chessBoard);
                        chessBoard.setGameFrame(mainFrame);
                        mainFrame.setCurrentText();
                        mainFrame.setVisible(true);
                        mainFrame.disableItems();
                        mainFrame.setTitle("Holeboard");
                        turnToPlay(controller.getCurrentPlayer());
                        leisureFrame.setVisible(false);
                        home.setVisible(false);
                        player.stop();
                    }
                });


            }
        });
        chooseMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    setVisible(false);
                    ChooseMode cm=new ChooseMode();
                    cm.setVisible(true);
                    player.stop();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
        rank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ranking rank=new Ranking();
                rank.setVisible(true);
            }
        });


    }

    private void showChooseFile(Component parent) {
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setVisible(true);
        fileChooser.setCurrentDirectory(new File("C:/"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("save(*.sav)", "sav"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("save(*.sav)", "sav"));
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            //home.setVisible(false);
            File file = fileChooser.getSelectedFile();
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
                //System.out.println(temp);

                //System.out.println(mode);
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
                mainFrame.disablePicture();
                chessBoard.setGameFrame(mainFrame);
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
//                    System.out.println(row+" "+rStandard);
//                    System.out.println(col+" "+cStandard);
                    cStandard++;

                    ChessBoardLocation location=new ChessBoardLocation(row,col);

                    if (temp.substring(4).equals("RED")){
                        pieceColor=Color.RED;
                        red++;
                    }
                    if (temp.substring(4).equals("GREEN")){
                        pieceColor=Color.GREEN;
                        green++;
                    }
                    if (temp.substring(4).equals("ORANGE")){
                        pieceColor=Color.ORANGE;
                        orange++;
                    }
                    if (temp.substring(4).equals("BLUE")){
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
                if (mode==0){
                    if (error==0&&!chessBoard.isEnd()&&red==19&&green==19&&currentPlayer.equals(Color.GREEN)&&blue==0&&orange==0){
                        mainFrame.setVisible(true);
                        home.setVisible(false);
                        mainFrame.setTitle("Against Computer");
                        chessBoard.setMoveNum1(Integer.parseInt(reader.readLine()));
                        chessBoard.setMoveNum2(Integer.parseInt(reader.readLine()));
                        turnToPlay(currentPlayer);
                        mainFrame.setCurrentText();
                        player.stop();
//                        controller.gameStartedSpecialEvent();
                    }else {
                        GameFrame.bgm.stop();
                        JOptionPane.showMessageDialog(parent, "The save is invalid!");
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
                        player.stop();

                    }else {
                        GameFrame.bgm.stop();
                        JOptionPane.showMessageDialog(parent, "The save is invalid!");
                    }
                }else {
//                    System.out.println(red+""+blue);
//                    System.out.println(orange+""+green);
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
                        player.stop();

                    }else {
                        GameFrame.bgm.stop();
                        JOptionPane.showMessageDialog(parent, "The save is invalid!");
                    }
                }
                reader.close();
            } catch (Exception e) {
                GameFrame.bgm.stop();
                JOptionPane.showMessageDialog(parent, "The save is dated!");
                e.printStackTrace();
            }





        }
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
            mainFrame.disablePicture();
            chessBoard.setGameFrame(mainFrame);
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
//                System.out.println(row+" "+rStandard);
//                System.out.println(col+" "+cStandard);
                cStandard++;

                ChessBoardLocation location=new ChessBoardLocation(row,col);

                if (temp.substring(4).equals("RED")){
                    pieceColor=Color.RED;
                    red++;
                }
                if (temp.substring(4).equals("GREEN")){
                    pieceColor=Color.GREEN;
                    green++;
                }
                if (temp.substring(4).equals("ORANGE")){
                    pieceColor=Color.ORANGE;
                    orange++;
                }
                if (temp.substring(4).equals("BLUE")){
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
            if (mode==0){
                if (error==0&&!chessBoard.isEnd()&&red==19&&green==19&&currentPlayer.equals(Color.GREEN)&&blue==0&&orange==0){
                    mainFrame.setVisible(true);
                    home.setVisible(false);
                    mainFrame.setTitle("Against Computer");
                    chessBoard.setMoveNum1(Integer.parseInt(reader.readLine()));
                    chessBoard.setMoveNum2(Integer.parseInt(reader.readLine()));
                    turnToPlay(currentPlayer);
                    mainFrame.setCurrentText();
                    player.stop();
//                        controller.gameStartedSpecialEvent();
                }else {
                    GameFrame.bgm.stop();
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
                    player.stop();

                }else {
                    GameFrame.bgm.stop();
                    JOptionPane.showMessageDialog(null, "The save is invalid!");
                }
            }else {
//                System.out.println(red+""+blue);
//                System.out.println(orange+""+green);
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
                    player.stop();

                }else {
                    GameFrame.bgm.stop();
                    JOptionPane.showMessageDialog(null, "The save is invalid!");
                }
            }
            reader.close();
        } catch (Exception e) {
            GameFrame.bgm.stop();
            JOptionPane.showMessageDialog(null, "File not supported!");
            e.printStackTrace();
        }
    }


    public void showDifficultyChoice(){
        JFrame frame=new JFrame();
        JSlider slider=new JSlider(1,3,2);
        JButton ok=new JButton("OK");
        JLabel info=new JLabel("   Please choose difficulty");
        JPanel panel=new JPanel();
        frame.setSize(210, 220);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FlowLayout layout=new FlowLayout();
        layout.setVgap(17);
        panel.setLayout(layout);
        ok.setFont(new Font(null, Font.PLAIN, 14));
        slider.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        info.setFont(new Font(null, Font.PLAIN, 15));
        ok.setPreferredSize(new Dimension(80,30));
        info.setPreferredSize(new Dimension(180,35));
        slider.setPreferredSize(new Dimension(120,40));

        Dictionary dict=new Hashtable();
        for (int i=1;i<=3;i++){
            switch (i){
                case 1:
                    JLabel easy=new JLabel("Easy");
                    easy.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                    dict.put(1,easy);

                    break;
                case 2:
                    JLabel med=new JLabel("Medium");
                    med.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                    dict.put(2,med);
                    break;
                case 3:
                    JLabel hell=new JLabel("Hell");
                    hell.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                    dict.put(3,hell);
                    break;
            }
        }
        slider.setLabelTable(dict);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
//        slider.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                System.out.println(slider.getValue());
//            }
//        });

        panel.add(info);
        panel.add(slider);
        panel.add(ok);
        frame.add(panel);
        frame.setVisible(true);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
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
