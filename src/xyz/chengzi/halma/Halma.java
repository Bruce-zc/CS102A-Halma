package xyz.chengzi.halma;



import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import xyz.chengzi.halma.view.ChooseMode;
import xyz.chengzi.halma.view.Home;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;


public class Halma {
    //private static JComboBox<String> comboBox=new JComboBox<>();
    //private static Component info=new JLabel("  You can choose the theme");;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            String[] theme={"Nimbus","Mint","Smart","Noire","Graphite","Mcwin","Cloth","Fast","Hifi"};
            JFrame set = new JFrame();
            set.setIconImage(new ImageIcon("dog.png").getImage());
            Component info=new JLabel("  You can choose the theme");;
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            set.setSize(220, 240);
            set.setLocationRelativeTo(null);
            set.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
                    String theme= String.valueOf(comboBox.getSelectedItem());
                    if (theme.equals("Cloth")){
                        InitGlobalFont(new Font(null, Font.PLAIN,13));
                        try {
                            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
                            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                            UIManager.put("RootPane.setupButtonVisible", false);
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
                                case "Fast":
                                    theme="com.jtattoo.plaf.fast.FastLookAndFeel";
                                    break;
                                case "Hifi":
                                    theme="com.jtattoo.plaf.hifi.HiFiLookAndFeel";
                                    break;
                            }
                            UIManager.setLookAndFeel(theme);

                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
                    }else {
                        for (UIManager.LookAndFeelInfo ui : UIManager.getInstalledLookAndFeels()) {
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

//            try {
//                for (UIManager.LookAndFeelInfo ui : UIManager.getInstalledLookAndFeels()) {
//                    System.out.println(UIManager.getSystemLookAndFeelClassName());
//                    if (ui.getName().equals("Nimbus")) {
//                        UIManager.setLookAndFeel(ui.getClassName());
////                        com.jtattoo.plaf.noire.NoireLookAndFeel 柔和黑
////                        com.jtattoo.plaf.smart.SmartLookAndFeel 木质感+xp风格
////                        com.jtattoo.plaf.mint.MintLookAndFeel 椭圆按钮+黄色按钮背景
////                        com.jtattoo.plaf.mcwin.McWinLookAndFeel 椭圆按钮+绿色按钮背景
////                        com.jtattoo.plaf.luna.LunaLookAndFeel 纯XP风格
////                        com.jtattoo.plaf.hifi.HiFiLookAndFeel 黑色风格
////                        com.jtattoo.plaf.fast.FastLookAndFeel 普通swing风格+蓝色边框
////                        com.jtattoo.plaf.bernstein.BernsteinLookAndFeel 黄色风格
////                        com.jtattoo.plaf.aluminium.AluminiumLookAndFeel 椭圆按钮+翠绿色按钮背景+金属质感（默认）
////                        com.jtattoo.plaf.aero.AeroLookAndFeel xp清新风格
////                        com.jtattoo.plafacryl.AcrylLookAndFeel 布质感+swing纯风格
//                        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//                        //UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
//                        break;
//                    }
//                }
//            } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
            //                ChooseMode cm=new ChooseMode();
//                cm.setVisible(true);






//            try {
//                ChooseMode cm=new ChooseMode();
//                cm.setVisible(true);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


        });

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
