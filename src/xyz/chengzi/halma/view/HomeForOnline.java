package xyz.chengzi.halma.view;

import xyz.chengzi.halma.Online.Client;
import xyz.chengzi.halma.Online.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HomeForOnline extends JFrame{
    JButton client=new JButton("Join:RED");
    JButton host=new JButton("Host:GREEN");
    JButton chooseMode=new JButton("Choose Mode");
    public  HomeForOnline(){
        this.setIconImage(new ImageIcon("dog.png").getImage());
        client.setFont(new Font(null, Font.PLAIN, 13));
        host.setFont(new Font(null, Font.PLAIN, 13));
        chooseMode.setFont(new Font(null, Font.PLAIN, 13));
        setTitle("Online");
        setSize(230,250);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(); // Use the panel to group buttons
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FlowLayout layout=new FlowLayout();
        layout.setVgap(20);
        layout.setHgap(5);
        panel.setLayout(layout);
        host.setLocation(120,70);
        client.setLocation(120,90);
        chooseMode.setLocation(120,110);

        host.setPreferredSize(new Dimension(140,43));
        client.setPreferredSize(new Dimension(140,43));
        chooseMode.setPreferredSize(new Dimension(140,43));

        panel.add(host);
        panel.add(client);
        panel.add(chooseMode);
        add(panel);


        chooseMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    setVisible(false);
                    ChooseMode cm=new ChooseMode();
                    cm.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
        host.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Server server = new Server();
                server.start();
            }
        });

        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame= new JFrame();
                JTextField inputIP=new JTextField();
                JLabel info=new JLabel("  Please input IP address");
                JButton ok=new JButton("OK");

                frame.setSize(200,200);
                inputIP.setFont(new Font(null, Font.PLAIN, 14));
                info.setFont(new Font(null, Font.PLAIN, 12));
                ok.setFont(new Font(null, Font.PLAIN, 14));

                info.setPreferredSize(new Dimension(150,40));
                inputIP.setPreferredSize(new Dimension(150,35));
                ok.setPreferredSize(new Dimension(80,30));
                String IPin=inputIP.getText();
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

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//                        try {
//                            Socket socket = new Socket(inputIP.getText(), 8131);
                            if (!inputIP.getText().equals(IPin)){
                                try {
                                    System.out.println(IPin);
                                    frame.setVisible(false);
                                    Client client = new Client(inputIP.getText());
                                    client.start();
                                    setVisible(false);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                    JOptionPane.showMessageDialog(null,"Please input a valid IP");
                                }
//                                startGame();
                            }else {
                                JOptionPane.showMessageDialog(null,"Please input a valid IP");
                            }

//                        } catch (IOException s){
//                            JOptionPane.showMessageDialog(null,"Please input a valid IP");
//                        }


                    }
                });

            }
        });

    }



    public static void main(String[] args) {
        HomeForOnline ol=new HomeForOnline();
        ol.setVisible(true);
    }
}
