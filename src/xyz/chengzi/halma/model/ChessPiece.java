package xyz.chengzi.halma.model;

import java.awt.*;

public class ChessPiece {
    private Color color;
    private boolean isLocked;
    private boolean isInvalid;

    public ChessPiece(Color color) {

        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isInvalid() {
        return isInvalid;
    }

    public void setInvalid(boolean invalid) {
        isInvalid = invalid;
    }
}
