package chess.pieces;

import chess.board.ChessBoard;

public class Horse extends ChessPiece {

    public Horse(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "♘" : "♞";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        if (isOutOfBoard(line, column) || isOutOfBoard(toLine, toColumn) || isNotMove(line, toLine, column, toColumn)) {
            return false;
        }

        int difLine = Math.abs(line - toLine);
        int difColumn = Math.abs(column - toColumn);

        return ((difLine == 2 && difColumn == 1) || (difLine == 1 && difColumn == 2))
                && (chessBoard.board[toLine][toColumn] == null || canCapture(chessBoard, toLine, toColumn));
    }
}


