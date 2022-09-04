package xyz.chengzi.halma.view;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Ranking extends JFrame {
    JLabel info=new JLabel();
    JLabel moveNum=new JLabel();
    JButton menu=new JButton("Back to main menu");
  //  GameFrame frame=new GameFrame();
    JTable table=new JTable();
    String[][] data;
    String[] header;

    public String[][] sort(String[][] data,int line){
        String[] temp={"","","",""};
        String[][] output=new String[data.length][4];
        for(int i=0;i<line;i++) {
            for(int j=0;j<line-i;j++) {
                if (data[j][1].equals("")){
                    break;
                } else if(Integer.parseInt(data[j][1])>=Integer.parseInt(data[j+1][1])) {
                    temp=data[j];
                    data[j]=data[j+1];
                    data[j+1]=temp;
                }
            }
        }
        return output;
    }

        public Ranking() {

            this.setIconImage(new ImageIcon("dog.png").getImage());
            info.setFont(new Font(null, Font.PLAIN, 13));
            menu.setFont(new Font(null, Font.PLAIN, 13));

            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            setLayout(null);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            int line=0;
            try {
                BufferedReader reader=new BufferedReader(new FileReader("rank.txt"));
                BufferedReader liner=new BufferedReader(new FileReader("rank.txt"));

                String temp="";
//                while(( liner.readLine())!=null){
//                    line++;
//                }
                data=new String[70][];
                while((temp = reader.readLine())!=null){
                    //read rank.txt

                    data[line]= new String[]{temp.substring(0, 6),temp.substring(6,10),temp.substring(10,20),temp.substring(21,26)};

                    line++;
                }
                for (int i=line;i<70;i++){
                    data[i]=new String[]{"","9999","",""};
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            setTitle("Ranking");
            setSize(330,320);



            sort(data,line);
            for (int i = line; i < 70; i++) {
                data[i][1] = "";
            }
            header=new String[]{"Player","Move","Date","Time"};
            table=new JTable(data,header);


            info.setSize(150,50);
            menu.setSize(150,50);
            moveNum.setSize(150,50);
            table.setSize(260,180);
            table.getTableHeader().setSize(260,25);
            table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(110);
            table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(85);
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);
            table.setEnabled(false);

            table.getTableHeader().setLocation(28,10);
            menu.setLocation(82,233);
            table.setLocation(28,36);


            table.setShowVerticalLines(true);
            //add(info);
            add(table.getTableHeader());
            add(menu);
            add(table);
            //add(moveNum);




            menu.setSize(150, 30);
            add(menu);


            menu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }

            });


            setLocationRelativeTo(null); // Center the window
        }
}

