package xyz.chengzi.halma.view;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import xyz.chengzi.halma.BGM;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

public class ChooseMode extends JFrame {
    JButton online=new JButton("Online");
    JButton offline=new JButton("Offline");
    JLabel modeChoose=new JLabel("   Welcome to Halma!");
    JButton chooseTheme=new JButton("Change Theme");

    public ChooseMode()throws IOException {
        this.setIconImage(new ImageIcon("game.png").getImage());
        setTitle("");
        setSize(180,270);
        JPanel panel=new JPanel();
        FlowLayout layout=new FlowLayout();
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        layout.setVgap(5);
        layout.setHgap(5);
        panel.setLayout(layout);
        modeChoose.setPreferredSize(new Dimension(150,50));
        modeChoose.setFont(new Font(null, Font.PLAIN, 15));
        offline.setPreferredSize(new Dimension(150,50));
        online.setPreferredSize(new Dimension(150,50));
        chooseTheme.setPreferredSize(new Dimension(150,50));
        chooseTheme.setFont(new Font(null, Font.PLAIN, 13));
        offline.setFont(new Font(null, Font.PLAIN, 13));
        online.setFont(new Font(null, Font.PLAIN, 13));

        online.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeForOnline ol=new HomeForOnline();
                ol.setVisible(true);
                setVisible(false);
            }
        });
        chooseTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent h) {
                String[] theme={"Nimbus","Mint","Smart","Noire","Graphite","Mcwin","Cloth","Windows"};
                JFrame set = new JFrame();
                Component info=new JLabel("  You can choose the theme");
                set.setSize(210, 240);
                set.setLocationRelativeTo(null);
                set.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JPanel panel=new JPanel();
                JButton ok=new JButton("OK");
                JComboBox<String> comboBox= new JComboBox<String>(theme);
                FlowLayout layout=new FlowLayout();
                layout.setVgap(20);
                panel.setLayout(layout);
                ok.setFont(new Font(null, Font.PLAIN, 14));
                info.setFont(new Font(null, Font.PLAIN, 14));
                comboBox.setFont(new Font(null, Font.PLAIN, 15));
                ok.setPreferredSize(new Dimension(80,30));
                info.setPreferredSize(new Dimension(180,43));
                comboBox.setPreferredSize(new Dimension(120,35));
                panel.add(info);
                panel.add(comboBox);
                panel.add(ok);
                set.add(panel);
                set.setVisible(true);

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        String theme= String.valueOf(comboBox.getSelectedItem());
                        if (theme.equals("Cloth")){
                            InitGlobalFont(new Font(null, Font.PLAIN,13));
                            try {
                                org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                                BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
                                org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                                UIManager.put("RootPane.setupButtonVisible", false);
                                //BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
                                //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                            } catch (Exception a) {
                                System.err.println("set skin fail!");
                            }
                        }else if (!theme.equals("Nimbus")){
                            try {
                                switch (theme){
                                    case "Mint":
                                        theme="com.jtattoo.plaf.mint.MintLookAndFeel";
                                        break;
                                    case "Smart":
                                        theme="com.jtattoo.plaf.smart.SmartLookAndFeel";
                                        break;
                                    case "Noire":
                                        theme="com.jtattoo.plaf.noire.NoireLookAndFeel";
                                        break;
                                    case "Graphite":
                                        theme="com.jtattoo.plaf.graphite.GraphiteLookAndFeel";
                                        break;
                                    case "Mcwin":
                                        theme="com.jtattoo.plaf.mcwin.McWinLookAndFeel";
                                        break;
                                    case "Windows":
                                        theme="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                                        break;
//                                case "Berstein":
//                                    theme="com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
//                                    break;
                                }
                                UIManager.setLookAndFeel(theme);

                            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException classNotFoundException) {
                                classNotFoundException.printStackTrace();
                            }
                        }else {
                            for (UIManager.LookAndFeelInfo ui : UIManager.getInstalledLookAndFeels()) {
//                                System.out.println(UIManager.getSystemLookAndFeelClassName());
                                if (ui.getName().equals("Nimbus")) {
                                    try {
                                        UIManager.setLookAndFeel(ui.getClassName());
                                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException classNotFoundException) {
                                        classNotFoundException.printStackTrace();
                                    }
                                    break;
                                }
                            }
                        }
                        set.setVisible(false);
                        ChooseMode cm= null;
                        try {
                            cm = new ChooseMode();
                            cm.setVisible(true);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }
                });
            }
        });
        offline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Home home=new Home();
                home.setHome(home);
                setVisible(false);
                home.setVisible(true);

            }
        });

        panel.add(modeChoose);
        panel.add(online);
        panel.add(offline);
        panel.add(chooseTheme);
        add(panel);



//
////host
//        ServerSocket serverSocket  =new ServerSocket(9999);
//        Socket host = serverSocket.accept();
//        BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(host.getInputStream()));
//        //get data
//        String moveRec ="";
//        while ((moveRec = bufferedReader.readLine())!=null){
//            //
//        }
//
////client
//        Socket client =new Socket("127.0.0.1",9999);
//        BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//        //send data
//        String moveSend="11112333RED";


    }
    private static void InitGlobalFont(Font font){
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
