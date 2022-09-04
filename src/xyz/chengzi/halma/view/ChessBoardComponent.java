package xyz.chengzi.halma.view;

import xyz.chengzi.halma.listener.GameListener;
import xyz.chengzi.halma.listener.InputListener;
import xyz.chengzi.halma.listener.Listenable;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardComponent extends JComponent implements Listenable<InputListener>, GameListener {
    public static  Color BOARD_COLOR_1 = new Color(255, 255, 204);
    public static  Color BOARD_COLOR_2 = new Color(170, 170, 170);

    private List<InputListener> listenerList = new ArrayList<>();
    private SquareComponent[][] gridComponents;
    private int dimension;
    private int gridSize;


    public static Color getBoardColor1() {
        return BOARD_COLOR_1;
    }

    public static void setBoardColor1(Color boardColor1) {
        BOARD_COLOR_1 = boardColor1;
    }

    public static Color getBoardColor2() {
        return BOARD_COLOR_2;
    }

    public static void setBoardColor2(Color boardColor2) {
        BOARD_COLOR_2 = boardColor2;
    }

    public ChessBoardComponent(int size, int dimension) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLayout(null); // Use absolute layout
        setSize(size, size);

        this.gridComponents = new SquareComponent[dimension][dimension];
        this.dimension = dimension;
        this.gridSize = size / dimension;
        initGridComponents();
    }
    public void repaintSquare(){
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                gridComponents[row][col].repaint();
            }
        }
    }

    private void initGridComponents() {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                gridComponents[row][col] = new SquareComponent(gridSize,
                        (row + col) % 2 == 0 ? BOARD_COLOR_1 : BOARD_COLOR_2);
                gridComponents[row][col].setLocation(col * gridSize, row * gridSize);
                add(gridComponents[row][col]);
            }
        }
    }

    public void setAllNotTraceSquare(){
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                gridComponents[row][col].setTraceSquare(false);
                gridComponents[row][col].setTraceSquare1(false);
                gridComponents[row][col].setTraceSquare2(false);
                gridComponents[row][col].setTraceSquare3(false);
                gridComponents[row][col].setTraceSquare4(false);
                gridComponents[row][col].setTraceSquare5(false);
                gridComponents[row][col].setTraceSquare6(false);
                gridComponents[row][col].setTraceSquare7(false);
                gridComponents[row][col].setTraceSquare8(false);
            }
        }
    }

    public void setAllNotPossibleMoveSquare(){
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                gridComponents[row][col].setPossibleMoveSquare(false);
            }
        }
    }

    public void setAllNotLastMoveSquare(){
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                gridComponents[row][col].setLastMoveSquare(false);
            }
        }
    }

    public SquareComponent getGridAt(ChessBoardLocation location) {
        return gridComponents[location.getRow()][location.getColumn()];
    }

    public void setChessAtGrid(ChessBoardLocation location, Color color) {
        removeChessAtGrid(location);
        getGridAt(location).add(new ChessComponent(color));
    }

    public void removeChessAtGrid(ChessBoardLocation location) {
        // Note: re-validation is required after remove / removeAll
        getGridAt(location).removeAll();
        getGridAt(location).revalidate();
    }

    private ChessBoardLocation getLocationByPosition(int x, int y) {
        return new ChessBoardLocation(y / gridSize, x / gridSize);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            ChessBoardLocation location = getLocationByPosition(e.getX(), e.getY());
            for (InputListener listener : listenerList) {
                if (clickedComponent.getComponentCount() == 0) {
                    listener.onPlayerClickSquare(location, (SquareComponent) clickedComponent);
                } else {
                    listener.onPlayerClickChessPiece(location, (ChessComponent) clickedComponent.getComponent(0));
                }
            }
        }
    }

    @Override
    public void onChessPiecePlace(ChessBoardLocation location, ChessPiece piece) {
        setChessAtGrid(location, piece.getColor());
        repaint();
    }

    @Override
    public void onChessPieceRemove(ChessBoardLocation location) {
        removeChessAtGrid(location);
        repaint();
    }

    @Override
    public void onChessBoardReload(ChessBoard board) {
        for (int row = 0; row < board.getDimension(); row++) {
            for (int col = 0; col < board.getDimension(); col++) {
                ChessBoardLocation location = new ChessBoardLocation(row, col);
                ChessPiece piece = board.getChessPieceAt(location);
                if (piece != null) {
                    setChessAtGrid(location, piece.getColor());
                }
            }
        }
        repaint();
    }

    @Override
    public void registerListener(InputListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(InputListener listener) {
        listenerList.remove(listener);
    }
}
