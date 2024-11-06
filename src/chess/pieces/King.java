package chess.pieces;

import chess.board.ChessBoard;

public class King extends ChessPiece {

    public King(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "♔" : "♚";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        if (isOutOfBoard(line, column) || isOutOfBoard(toLine, toColumn) || isNotMove(line, column, toLine, toColumn)) {
            return false;
        }

        int difLine = Math.abs(line - toLine);
        int difColumn = Math.abs(column - toColumn);

        // Проверка движения на одну клетку
        if (difLine > 1 || difColumn > 1) {
            return false;
        }

        return canCapture(chessBoard, toLine, toColumn) && !isUnderAttack(chessBoard, toLine, toColumn);
    }

    public boolean isUnderAttack(ChessBoard chessBoard, int toLine, int toColumn) {

        if (isOutOfBoard(toLine, toColumn)) {
            return false;
        }

        // Перебираем все фигуры на поле
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = chessBoard.board[i][j];

                if (piece != null && !piece.color.equals(this.color)) {
                    if (piece.canMoveToPosition(chessBoard, i, j, toLine, toColumn)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


