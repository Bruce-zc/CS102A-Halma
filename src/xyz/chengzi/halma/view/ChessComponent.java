package xyz.chengzi.halma.view;

import xyz.chengzi.halma.BGM;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ChessComponent extends JComponent {
    private Color color;
    private boolean selected;
    public static int chessTheme = 1;
    //1-classic 2-happy face 3-shadow


    public static int getChessTheme() {
        return chessTheme;
    }

    public void setChessTheme(int chessTheme) {
        ChessComponent.chessTheme = chessTheme;
    }

    public ChessComponent(Color color) {
        this.color = color;
    }

    public boolean isSelected() {
        BGM clicked=new BGM(new File("click.mp3"));
        clicked.start();
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintChess(g);
    }

    private void paintChess(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        int spacing = (int) (getWidth() * 0.05);
        if (chessTheme == 1){
            if(g.getColor()==Color.RED){
            Image image = Toolkit.getDefaultToolkit().getImage("chess/chessR.png");
            g.drawImage(image,spacing-17,spacing-13, getWidth()+32,getHeight()+20,this);
            g.setColor(Color.BLACK);
            if (selected) { // Draw a + sign in the center of the piece
                g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
            }}
            else if(g.getColor()==Color.GREEN){
                Image image = Toolkit.getDefaultToolkit().getImage("chess/chessG.png");
                    g.drawImage(image,spacing-43,spacing-30, getWidth()*3-5,getHeight()*2+18,this);
                    g.setColor(Color.BLACK);
                    if (selected) { // Draw a + sign in the center of the piece
                        g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                        g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                        g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
                    }
            }
            else if(g.getColor()==Color.ORANGE){
                Image image = Toolkit.getDefaultToolkit().getImage("chess/chessO.png");
                g.drawImage(image,spacing-7,spacing-3, getWidth()+12,getHeight()+2,this);
                g.setColor(Color.BLACK);
                if (selected) { // Draw a + sign in the center of the piece
                    g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                    g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                    g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
                }
            }
            else if(g.getColor()==Color.BLUE){
                Image image = Toolkit.getDefaultToolkit().getImage("chess/chessB.png");
                g.drawImage(image,spacing-7,spacing-3, getWidth()+12,getHeight()+2,this);
                g.setColor(Color.BLACK);
                if (selected) { // Draw a + sign in the center of the piece
                    g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                    g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                    g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
                }
            }else if (g.getColor()==Color.CYAN){
                Image image = Toolkit.getDefaultToolkit().getImage("chess/chessC.png");
                g.drawImage(image,spacing-7,spacing-3, getWidth()+12,getHeight()+2,this);
                g.setColor(Color.BLACK);
                if (selected) { // Draw a + sign in the center of the piece
                    g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                    g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                    g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
                }
            }else if (g.getColor()==Color.BLACK){
                Image image = Toolkit.getDefaultToolkit().getImage("chess/chessBl.png");
                g.drawImage(image,spacing-7,spacing-3, getWidth()+12,getHeight()+2,this);
                g.setColor(Color.BLACK);
                if (selected) { // Draw a + sign in the center of the piece
                    g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                    g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                    g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
                }
            }else if (g.getColor()==Color.YELLOW){
                Image image = Toolkit.getDefaultToolkit().getImage("chess/chessY.png");
                g.drawImage(image,spacing-7,spacing-3, getWidth()+12,getHeight()+2,this);
                g.setColor(Color.BLACK);
                if (selected) { // Draw a + sign in the center of the piece
                    g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                    g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                    g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
                }
            }else if (g.getColor()==Color.PINK){
                Image image = Toolkit.getDefaultToolkit().getImage("chess/chessP.png");
                g.drawImage(image,spacing-7,spacing-3, getWidth()+12,getHeight()+2,this);
                g.setColor(Color.BLACK);
                if (selected) { // Draw a + sign in the center of the piece
                    g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                    g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                    g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
                }
            }
            else
                g.fillOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);
            g.setColor(Color.BLACK);
            g.drawOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);
            g.setColor(Color.BLACK);
            if (selected) { // Draw a + sign in the center of the piece
                g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
                g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
                g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
            }
        }

        if (chessTheme == 2) {
            g.fillOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);
            g.setColor(Color.BLACK);
            g.fillOval(14, 17, 4, 4);
            g.fillOval(30, 17, 4, 4);
            if (selected) {
                g.setColor(Color.BLACK);
                g.drawOval(19, 27, 8, 8);
            } else {
                g.setColor(Color.BLACK);
                g.drawLine(14, 28, 34, 28);
                g.drawArc(13, 13, 22, 22, 202, 136);
            }
        }
        if (chessTheme == 3){
            if (selected) {
                g.setColor(new Color(153, 147, 147, 255));
                g.fillOval(5,5,40,40);
                g.setColor(color);
                g.fillOval(spacing+2, spacing+2, getWidth() - 2 * spacing-4, getHeight() - 2 * spacing-4);
                g.setColor(Color.BLACK);
                g.fillOval(23,23,2,2);
            }
            else {
                g.fillOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);
                g.setColor(Color.BLACK);
            }
        }
        if(chessTheme==4){
            if (selected) {
                Image image = Toolkit.getDefaultToolkit().getImage("panda.jpg");
                g.drawImage(image,spacing+3,spacing+3, getWidth()-6*spacing,getHeight()-6*spacing,this);
                g.setColor(new Color(153, 147, 147, 255));
                g.fillOval(5,5,40,40);
                g.setColor(color);
                g.fillOval(spacing+2, spacing+2, getWidth() - 2 * spacing-4, getHeight() - 2 * spacing-4);
                g.setColor(Color.BLACK);
                g.fillOval(23,23,2,2);
            }
            else {
                g.fillOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);
                g.setColor(Color.BLACK);
            }
        }
        }
    }

