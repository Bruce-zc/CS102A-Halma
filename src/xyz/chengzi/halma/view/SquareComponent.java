package xyz.chengzi.halma.view;

import javax.swing.*;
import java.awt.*;

//class picPanel extends JPanel{
//    @Override
//    public Component add(Component comp) {
//        return super.add(comp);
//    }
//
//    @Override
//    public void setLayout(LayoutManager mgr) {
//        super.setLayout(mgr);
//    }
//
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        int x=0,y=0;
//        java.net.URL imageURL=getClass().getResource("res/Wood.jpg");
//        ImageIcon icon=new ImageIcon("res/Wood.jpg");
//        g.drawImage(icon.getImage(), x,y, getSize().width, getSize().height, this);
//        while(true){
//            g.drawImage(icon.getImage(), x, y, this);
//            if(x>getSize().width&&y>getSize().height)
//                break;
//            if(x>getSize().width){
//                x=0;
//                y+=icon.getIconHeight();
//
//            }else{
//                x+=icon.getIconWidth();
//            }
//        }
//
//    }
//}

public class SquareComponent extends JPanel {
    private Color color;
    private Image image;
    private boolean isPossibleMoveSquare;
    private boolean isLastMoveSquare;
    private boolean isRedSquare1;
    private boolean isRedSquare2;
    private boolean isRedSquare3;
    private boolean isGreenSquare1;
    private boolean isGreenSquare2;
    private boolean isGreenSquare3;
    private boolean isOrangeSquare1;
    private boolean isOrangeSquare2;
    private boolean isOrangeSquare3;
    private boolean isBlueSquare1;
    private boolean isBlueSquare2;
    private boolean isBlueSquare3;
    private boolean isTraceSquare;
    private boolean isTraceSquare1;
    private boolean isTraceSquare2;
    private boolean isTraceSquare3;
    private boolean isTraceSquare4;
    private boolean isTraceSquare5;
    private boolean isTraceSquare6;
    private boolean isTraceSquare7;
    private boolean isTraceSquare8;
    public static int lastTheme=0;//0 for location;1 for dog;2 for box;3 for fruits

    public SquareComponent(int size, Color color) {
        setLayout(new GridLayout(1, 1)); // Use 1x1 grid layout
        setSize(size, size);
        this.color = color;

    }
    public SquareComponent(int size,Image image) {
        setLayout(new GridLayout(1, 1)); // Use 1x1 grid layout
        setSize(size, size);
        this.image = image;
    }

    public boolean isTraceSquare1() {
        return isTraceSquare1;
    }

    public void setTraceSquare1(boolean traceSquare1) {
        isTraceSquare1 = traceSquare1;
    }

    public boolean isTraceSquare2() {
        return isTraceSquare2;
    }

    public void setTraceSquare2(boolean traceSquare2) {
        isTraceSquare2 = traceSquare2;
    }

    public boolean isTraceSquare3() {
        return isTraceSquare3;
    }

    public void setTraceSquare3(boolean traceSquare3) {
        isTraceSquare3 = traceSquare3;
    }

    public boolean isTraceSquare4() {
        return isTraceSquare4;
    }

    public void setTraceSquare4(boolean traceSquare4) {
        isTraceSquare4 = traceSquare4;
    }

    public boolean isTraceSquare5() {
        return isTraceSquare5;
    }

    public void setTraceSquare5(boolean traceSquare5) {
        isTraceSquare5 = traceSquare5;
    }

    public boolean isTraceSquare6() {
        return isTraceSquare6;
    }

    public void setTraceSquare6(boolean traceSquare6) {
        isTraceSquare6 = traceSquare6;
    }

    public boolean isTraceSquare7() {
        return isTraceSquare7;
    }

    public void setTraceSquare7(boolean traceSquare7) {
        isTraceSquare7 = traceSquare7;
    }

    public boolean isTraceSquare8() {
        return isTraceSquare8;
    }

    public void setTraceSquare8(boolean traceSquare8) {
        isTraceSquare8 = traceSquare8;
    }

    public boolean isTraceSquare() {
        return isTraceSquare;
    }

    public void setTraceSquare(boolean traceSquare) {
        isTraceSquare = traceSquare;
    }

    public boolean isLastMoveSquare() {
        return isLastMoveSquare;
    }

    public void setLastMoveSquare(boolean lastMoveSquare) {
        isLastMoveSquare = lastMoveSquare;
    }

    public boolean isPossibleMoveSquare() {
        return isPossibleMoveSquare;
    }

    public void setPossibleMoveSquare(boolean possibleMoveSquare) {
        isPossibleMoveSquare = possibleMoveSquare;
    }

    public boolean isRedSquare1() {
        return isRedSquare1;
    }

    public void setRedSquare1(boolean redSquare1) {
        isRedSquare1 = redSquare1;
    }

    public boolean isRedSquare2() {
        return isRedSquare2;
    }

    public void setRedSquare2(boolean redSquare2) {
        isRedSquare2 = redSquare2;
    }

    public boolean isRedSquare3() {
        return isRedSquare3;
    }

    public void setRedSquare3(boolean redSquare3) {
        isRedSquare3 = redSquare3;
    }

    public boolean isGreenSquare1() {
        return isGreenSquare1;
    }

    public void setGreenSquare1(boolean greenSquare1) {
        isGreenSquare1 = greenSquare1;
    }

    public boolean isGreenSquare2() {
        return isGreenSquare2;
    }

    public void setGreenSquare2(boolean greenSquare2) {
        isGreenSquare2 = greenSquare2;
    }

    public boolean isGreenSquare3() {
        return isGreenSquare3;
    }

    public void setGreenSquare3(boolean greenSquare3) {
        isGreenSquare3 = greenSquare3;
    }

    public boolean isOrangeSquare1() {
        return isOrangeSquare1;
    }

    public void setOrangeSquare1(boolean orangeSquare1) {
        isOrangeSquare1 = orangeSquare1;
    }

    public boolean isOrangeSquare2() {
        return isOrangeSquare2;
    }

    public void setOrangeSquare2(boolean orangeSquare2) {
        isOrangeSquare2 = orangeSquare2;
    }

    public boolean isOrangeSquare3() {
        return isOrangeSquare3;
    }

    public void setOrangeSquare3(boolean orangeSquare3) {
        isOrangeSquare3 = orangeSquare3;
    }

    public boolean isBlueSquare1() {
        return isBlueSquare1;
    }

    public void setBlueSquare1(boolean blueSquare1) {
        isBlueSquare1 = blueSquare1;
    }

    public boolean isBlueSquare2() {
        return isBlueSquare2;
    }

    public void setBlueSquare2(boolean blueSquare2) {
        isBlueSquare2 = blueSquare2;
    }

    public boolean isBlueSquare3() {
        return isBlueSquare3;
    }

    public void setBlueSquare3(boolean blueSquare3) {
        isBlueSquare3 = blueSquare3;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintSquare(g);
    }

    private void paintSquare(Graphics g) {
        g.setColor(color);



        int x=0,y=0;
        java.net.URL imageURL=getClass().getResource("res/Wood.jpg");
        ImageIcon icon=new ImageIcon("res/Wood.jpg");
        if (g.getColor().equals(new Color(170, 170, 170))){
            icon=new ImageIcon("res/WoodD.jpg");
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
        }else if (g.getColor().equals(new Color(255, 255, 204))){
            icon=new ImageIcon("res/Wood.jpg");
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
        }else {
            g.fillRect(0, 0, getWidth(), getHeight());
        }



        if (isRedSquare1){
            g.setColor(Color.RED);
            g.drawLine(0,0,760/16,0);
            g.drawLine(0,0,0,760/16);
            g.setColor(Color.BLACK);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isRedSquare2){
            g.setColor(Color.RED);
            g.drawLine(0,0,0,760/16);
            g.setColor(Color.BLACK);
            g.drawLine(0,0,760/16,0);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isRedSquare3){
            g.setColor(Color.RED);
            g.drawLine(0,0,760/16,0);
            g.setColor(Color.BLACK);
            g.drawLine(0,0,0,760/16);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isGreenSquare1){
            g.setColor(Color.GREEN);
            g.drawLine(0,0,760/16,0);
            g.drawLine(0,0,0,760/16);
            g.setColor(Color.BLACK);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isGreenSquare2){
            g.setColor(Color.GREEN);
            g.drawLine(0,0,0,760/16);
            g.setColor(Color.BLACK);
            g.drawLine(0,0,760/16,0);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isGreenSquare3){
            g.setColor(Color.GREEN);
            g.drawLine(0,0,760/16,0);
            g.setColor(Color.BLACK);
            g.drawLine(0,0,0,760/16);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isOrangeSquare1){
            g.setColor(Color.ORANGE);
            g.drawLine(0,0,760/16,0);
            g.drawLine(0,0,0,760/16);
            g.setColor(Color.BLACK);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isOrangeSquare2){
            g.setColor(Color.ORANGE);
            g.drawLine(0,0,0,760/16);
            g.setColor(Color.BLACK);
            g.drawLine(0,0,760/16,0);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isOrangeSquare3){
            g.setColor(Color.ORANGE);
            g.drawLine(0,0,760/16,0);
            g.setColor(Color.BLACK);
            g.drawLine(0,0,0,760/16);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isBlueSquare1){
            g.setColor(Color.BLUE);
            g.drawLine(0,0,760/16,0);
            g.drawLine(0,0,0,760/16);
            g.setColor(Color.BLACK);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isBlueSquare2){
            g.setColor(Color.BLUE);
            g.drawLine(0,0,0,760/16);
            g.setColor(Color.BLACK);
            g.drawLine(0,0,760/16,0);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else if (isBlueSquare3){
            g.setColor(Color.BLUE);
            g.drawLine(0,0,760/16,0);
            g.setColor(Color.BLACK);
            g.drawLine(0,0,0,760/16);
            g.drawLine(760/16,0,760/16,760/16);
            g.drawLine(0,760/16,760/16,760/16);
        }
        else {
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(Color.BLACK);
        if (isPossibleMoveSquare) {
            if (ChessBoardComponent.BOARD_COLOR_2.equals(new Color(170, 170, 170))){
                g.setColor(Color.WHITE);
            }else if (ChessBoardComponent.BOARD_COLOR_1.equals(new Color(255, 255, 204))){
                g.setColor(Color.WHITE);
            }else {
                g.setColor(Color.BLACK);
            }
            g.drawOval(22,22,4,4);
        }
        if (isTraceSquare){
            g.setColor(Color.GRAY);
            g.fillOval(21,21,6,6);
        }
        if (isLastMoveSquare) {
            g.setColor(Color.BLACK);
            switch (lastTheme) {
                case 0:
                    Image image0 = Toolkit.getDefaultToolkit().getImage("res/0.png");
                    g.drawImage(image0, 8, 8, 32, 32, this);
                    break;
                case 1:
                    Image image1 = Toolkit.getDefaultToolkit().getImage("res/1.png");
                    g.drawImage(image1, 8, 8, 32, 32, this);
                    break;
                case 2:
                    Image image2 = Toolkit.getDefaultToolkit().getImage("res/2.png");
                    g.drawImage(image2, 8, 8, 32, 32, this);
                    break;
                case 3:
                    Image image3;
                    double a = Math.random();
                    if (a >= 0 && a < 0.2) {
                        image3 = Toolkit.getDefaultToolkit().getImage("res/3-1.png");
                    } else if (a >= 0.25 & a < 0.4) {
                        image3 = Toolkit.getDefaultToolkit().getImage("res/3-2.png");
                    } else if (a >= 0.4 && a < 0.6) {
                        image3 = Toolkit.getDefaultToolkit().getImage("res/3-3.png");
                    } else if (a >= 0.6 && a < 0.8) {
                        image3 = Toolkit.getDefaultToolkit().getImage("res/3-4.png");
                    } else {
                        image3 = Toolkit.getDefaultToolkit().getImage("res/3-5.png");
                    }
                    g.drawImage(image3, 8, 8, 32, 32, this);
                    break;
                default:
                    g.drawOval(8, 8, 32, 32);
                    break;
            }
        }
        if (ChessBoardComponent.BOARD_COLOR_2.equals(new Color(170, 170, 170))){
            g.setColor(Color.WHITE);
        }else if (ChessBoardComponent.BOARD_COLOR_1.equals(new Color(255, 255, 204))){
            g.setColor(Color.WHITE);
        }else {
            g.setColor(Color.GRAY);
        }

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,10.0f,new float[]{5,5},0);
        g2.setStroke(stroke);
        if (isTraceSquare1){
            g2.drawLine(24,24, 0, 48);
        }
        if (isTraceSquare2){
            g2.drawLine(24,24, 24, 48);
        }
        if (isTraceSquare3){
            g2.drawLine(24,24, 0, 0);
        }
        if (isTraceSquare4){
            g2.drawLine(24,24, 0, 24);
        }
        if (isTraceSquare5){
            g2.drawLine(24,24, 48, 0);
        }
        if (isTraceSquare6){
            g2.drawLine(24,24, 24, 0);
        }
        if (isTraceSquare7){
            g2.drawLine(24,24, 48, 48);
        }
        if (isTraceSquare8){
            g2.drawLine(24,24, 48, 24);
        }
        g2.setStroke(new BasicStroke());

    }
}
