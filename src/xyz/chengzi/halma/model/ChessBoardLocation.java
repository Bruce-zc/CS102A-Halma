package xyz.chengzi.halma.model;

import java.util.Objects;

public class ChessBoardLocation {
    private int row, column;
    private boolean mode5HolePosition;
    private int jumpTimes;
    private ChessBoardLocation lastJumpLocation;

    public ChessBoardLocation getLastJumpLocation() {
        return lastJumpLocation;
    }

    public void setLastJumpLocation(ChessBoardLocation lastJumpLocation) {
        this.lastJumpLocation = lastJumpLocation;
    }

    public int getJumpTimes() {
        return jumpTimes;
    }

    public void setJumpTimes(int jumpTimes) {
        this.jumpTimes = jumpTimes;
    }

    public boolean isMode5HolePosition() {
        return mode5HolePosition;
    }

    public void setMode5HolePosition(boolean mode5HolePosition) {
        this.mode5HolePosition = mode5HolePosition;
    }

    @Override
    public String toString() {
        if (row<10&&column<10){
            return  "0" + row +
                    "0"+ column;
        }
        if (row<10&&column>=10){
            return  "0" + row +
                     column;
        }
        if (row>=10&&column<10){
            return   row +
                    "0"+ column;
        }
        else return row +""+ column;
    }

    public ChessBoardLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoardLocation location = (ChessBoardLocation) o;
        return row == location.row && column == location.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
