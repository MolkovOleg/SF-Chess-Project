package chess.pieces;

import chess.board.ChessBoard;

public abstract class ChessPiece {
    public String color;
    public boolean check = true;

    public ChessPiece(String color) {
        this.color = color;
    }

    // Проверка на выход за пределы поля
    public final boolean isOutOfBoard(int line, int column) {
        return line < 0 || line > 7 || column < 0 || column > 7;
    }

    // Проверка на ход в изначальную позицию
    public final boolean isNotMove(int line, int toLine, int column, int toColumn) {
        return (line == toLine && column == toColumn);
    }

    // Проверка на вражескую фигуру
    protected boolean isEnemyPiece(ChessPiece piece) {
        return piece != null && !piece.color.equals(this.color);
    }

    // Проверка на возможность взятия фигуры
    protected final boolean canCapture(ChessBoard chessBoard, int toLine, int toColumn) {
        ChessPiece piece = chessBoard.board[toLine][toColumn];
        return piece == null || isEnemyPiece(piece);
    }

    // Проверка на препятствия для Ладьи, Слона и Королевы
    final protected boolean isPathClear(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        // Инициализируем шаг
        int lineStep = 0;
        int columnStep = 0;

        // Определяем движение по строке
        if (toLine > line) {
            lineStep = 1;
        } else if (toLine < line) {
            lineStep = -1;
        }

        // Определяем движение по столбцу
        if (toColumn > column) {
            columnStep = 1;
        } else if (toColumn < column) {
            columnStep = -1;
        }

        // Определяем позицию после первого шага
        int currentLine = line + lineStep;
        int currentColumn = column + columnStep;

        // Цикл для нахождения препятствия на пути
        while (toLine != currentLine || toColumn != currentColumn) {
            if (chessBoard.board[currentLine][currentColumn] != null) {
                return false;
            }
            currentLine += lineStep;
            currentColumn += columnStep;
        }
        return true;
    }

    // Проверка на возможность хода в целом (для каждой фигуры своя логика)
    abstract public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    // Получения символа фигуры (у каждого свое обозначение)
    abstract public String getSymbol();
}


