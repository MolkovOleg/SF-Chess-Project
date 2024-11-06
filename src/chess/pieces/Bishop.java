package chess.pieces;

import chess.board.ChessBoard;

public class Bishop extends ChessPiece {

    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "♗" : "♝";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        if (isOutOfBoard(line, column) || isOutOfBoard(toLine, toColumn) || isNotMove(line, toLine, column, toColumn)) {
            return false;
        }

        int difLine = Math.abs(line - toLine);
        int difColumn = Math.abs(column - toColumn);

        // Проверка на движение по диагонали
        if (difLine != difColumn) {
            return false;
        }

        // Проверка на препятствие
        if (!isPathClear(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        return canCapture(chessBoard, toLine, toColumn);
    }
}


