package chess.pieces;

import chess.board.ChessBoard;

public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "♙" : "♟";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        if (isOutOfBoard(line, column) || isOutOfBoard(toLine, toColumn) || isNotMove(line, toLine, column, toColumn)) {
            return false;
        }

        // Определение направление хода и начальной позиции пешки
        int direction = this.color.equals("White") ? 1 : -1;
        int startLine = this.color.equals("White") ? 1 : 6;

        // Логика обычного хода вперед
        if (column == toColumn) {
            // Один шаг вперед
            if (toLine == line + direction && chessBoard.board[toLine][toColumn] == null) {
                return true;
            }
            // Два шага вперед, если пешка на начальной позиции
            if (line == startLine && toLine == line + 2 * direction
                    && chessBoard.board[line + direction][column] == null
                    && chessBoard.board[toLine][toColumn] == null) {
                return true;
            }
        } else
            return Math.abs(column - toColumn) == 1 && toLine == line + direction && canCapture(chessBoard, toLine, toColumn);

        return false;
    }
}


