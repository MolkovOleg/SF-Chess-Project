package chess.board;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8];
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    // Списки захваченных фигур
    private final List<ChessPiece> capturedWhitePieces = new ArrayList<>();
    private final List<ChessPiece> capturedBlackPieces = new ArrayList<>();

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {

        // Проверяем, что ход в пределах доски
        if (isOutOfBoard(startLine) || isOutOfBoard(startColumn) || isOutOfBoard(endLine) || isOutOfBoard(endColumn)) {
            return false;
        }

        // Создаем объект фигуры, чтобы избегать многократный доступ
        ChessPiece piece = board[startLine][startColumn];

        // Проверка нахождения фигуры на исходной позиции
        if (piece == null || !piece.color.equals(nowPlayer)) {
            return false;
        }

        // Возможность фигуры перейти на указанное поле
        if (!piece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
            return false;
        }

        // Добавление в список захваченных фигур
        ChessPiece targetPiece = board[endLine][endColumn];
        if (targetPiece != null && !targetPiece.color.equals(nowPlayer)) {
            if (nowPlayer.equals("White")) {
                capturedBlackPieces.add(targetPiece);
            } else {
                capturedWhitePieces.add(targetPiece);
            }
        }

        // Проверка для рокировки
        if (piece instanceof King || piece instanceof Rook) {
            piece.check = false;
        }

        // Превращение пешки в Ферзя
        if (piece instanceof Pawn && (endLine == 0 || endLine == 7)) {
            board[endLine][endColumn] = new Queen(piece.color);
            System.out.println("Пешка превратилась в Ферзя");
        }

        // Перемещение фигуры на новую позицию
        board[endLine][endColumn] = piece;
        board[startLine][startColumn] = null;

        // Передача хода новому игроку
        changePlayer();

        return true;
    }

    // Переключение игрока
    private void changePlayer() {
        nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
    }

    public void printBoard() {
        System.out.println("****** Сейчас ход у " + nowPlayer + " ******");
        System.out.println();
        System.out.println("Игрок 2(Черные)");
        System.out.println("Захваченные фигуры игрока Черных: ");
        for (ChessPiece piece : capturedWhitePieces) {
            System.out.print(piece.getSymbol() + " ");
        }
        System.out.println("\n");
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        for (ChessPiece piece : capturedBlackPieces) {
            System.out.print(piece.getSymbol() + " ");
        }
        System.out.println();
        System.out.println("Захваченные фигуры игрока Белых: ");
        System.out.println("Игрок 1(Белые)");

        // Проверка на Шах или Мат Королю
        String enemyColor = nowPlayer.equals("White") ? "Black" : "White";
        if (isKingInCheck(enemyColor)) {
            if (isCheckMate(enemyColor)) {
                System.out.println("Мат! " + enemyColor + " проиграли");
            } else {
                System.out.println("Шах " + enemyColor + " королю!");
            }
        }
    }

    public boolean castling0() {

        //Проверка на то, чей ход для рокировки
        if (nowPlayer.equals("White")) {

            // Проверка на нахождение Ладьи и Короля на начальных позициях и их цвета
            if (!(board[0][0] instanceof Rook whiteRook && whiteRook.color.equals("White"))
                    || !(board[0][4] instanceof King whiteKing && whiteKing.color.equals("White"))) {
                return false;
            }

            // Проверка, что Король и Ладья не двигались
            if (!whiteRook.check || !whiteKing.check) {
                return false;
            }

            // Проверка на пустые поля между Ладьей и Королем
            if (board[0][1] != null || board[0][2] != null || board[0][3] != null) {
                return false;
            }

            // Проверка на нахождение короля под ударом
            if (whiteKing.isUnderAttack(this, 0, 4)
                    || whiteKing.isUnderAttack(this, 0, 3)
                    || whiteKing.isUnderAttack(this, 0, 2)) {
                return false;
            }

            // Делаем рокировку
            board[0][4] = null;
            board[0][2] = whiteKing;
            whiteKing.check = false;
            board[0][0] = null;
            board[0][3] = whiteRook;
            whiteRook.check = false;

            nowPlayer = "Black";

        } else {

            // Проверка на нахождение Ладьи и Короля на начальных позициях
            if (!(board[7][0] instanceof Rook blackRook && blackRook.color.equals("Black"))
                    || !(board[7][4] instanceof King blackKing && blackKing.color.equals("Black"))) {
                return false;
            }

            // Проверка, что Король и Ладья не двигались
            if (!blackRook.check || !blackKing.check) {
                return false;
            }

            // Проверка на пустые поля между Ладьей и Королем
            if (board[7][1] != null || board[7][2] != null || board[7][3] != null) {
                return false;
            }

            // Проверка на нахождение короля под ударом
            if (blackKing.isUnderAttack(this, 7, 4)
                    || blackKing.isUnderAttack(this, 7, 3)
                    || blackKing.isUnderAttack(this, 7, 2)) {
                return false;
            }

            // Делаем рокировку
            board[7][4] = null;
            board[7][2] = blackKing;
            blackKing.check = false;
            board[7][0] = null;
            board[7][3] = blackRook;
            blackRook.check = false;

            nowPlayer = "White";

        }
        return true;
    }

    public boolean castling7() {

        //Проверка на то, чей ход для рокировки
        if (nowPlayer.equals("White")) {

            // Проверка на нахождение Ладьи и Короля на начальных позициях
            if (!(board[0][7] instanceof Rook whiteRook && whiteRook.color.equals("White"))
                    || !(board[0][4] instanceof King whiteKing && whiteKing.color.equals("White"))) {
                return false;
            }

            // Проверка, что Король и Ладья не двигались
            if (!whiteRook.check || !whiteKing.check) {
                return false;
            }

            // Проверка на пустые поля между Ладьей и Королем
            if (board[0][5] != null || board[0][6] != null) {
                return false;
            }

            // Проверка на нахождение короля под ударом
            if (whiteKing.isUnderAttack(this, 0, 4)
                    || whiteKing.isUnderAttack(this, 0, 5)
                    || whiteKing.isUnderAttack(this, 0, 6)) {
                return false;
            }

            // Делаем рокировку
            board[0][4] = null;
            board[0][6] = whiteKing;
            whiteKing.check = false;
            board[0][7] = null;
            board[0][5] = whiteRook;
            whiteRook.check = false;

            nowPlayer = "Black";

        } else {

            // Проверка на нахождение Ладьи и Короля на начальных позициях
            if (!(board[7][7] instanceof Rook blackRook && blackRook.color.equals("Black"))
                    || !(board[7][4] instanceof King blackKing && blackKing.color.equals("Black"))) {
                return false;
            }

            // Проверка, что Король и Ладья не двигались
            if (!blackRook.check || !blackKing.check) {
                return false;
            }

            // Проверка на пустые поля между Ладьей и Королем
            if (board[7][5] != null || board[7][6] != null) {
                return false;
            }

            // Проверка на нахождение короля под ударом
            if (blackKing.isUnderAttack(this, 7, 4)
                    || blackKing.isUnderAttack(this, 7, 5)
                    || blackKing.isUnderAttack(this, 7, 6)) {
                return false;
            }

            // Делаем рокировку
            board[7][4] = null;
            board[7][6] = blackKing;
            blackKing.check = false;
            board[7][7] = null;
            board[7][5] = blackRook;
            blackRook.check = false;

            nowPlayer = "White";

        }
        return true;
    }

    public boolean isOutOfBoard(int pos) {
        return pos < 0 || pos > 8;
    }

    // Проверка на шах
    public boolean isKingInCheck(String kingColor) {

        // Зададим ошибочную позицию Короля
        int kingLine = -1;
        int kingColumn = -1;

        // Найдем позицию короля
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.color.equals(kingColor)) {
                    kingLine = i;
                    kingColumn = j;
                    break;
                }
            }
        }
        return ((King) board[kingLine][kingColumn]).isUnderAttack(this, kingLine, kingColumn);
    }

    // Проверка на мат
    public boolean isCheckMate(String kingColor) {

        // Проверка шаха Короля
        if (!isKingInCheck(kingColor)) {
            return false;
        }

        // Зададим ошибочную позицию Короля
        int kingLine = -1;
        int kingColumn = -1;

        // Найдем позицию короля
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.color.equals(kingColor)) {
                    kingLine = i;
                    kingColumn = j;
                    break;
                }
            }
        }

        // Проверяем все возможные ходы короля
        King king = (King) board[kingLine][kingColumn];
        for (int di = -1; di < 1; di++) {
            for (int dj = -1; dj < 1; dj++) {
                if (di == 0 && dj == 0) {
                    continue;
                }
                int toLine = kingLine + di;
                int toColumn = kingColumn + dj;

                if (king.canMoveToPosition(this, kingLine, kingColumn, toLine, toColumn)) {
                    // Выполняем временный ход короля для проверки
                    ChessPiece tempPiece = board[toLine][toColumn];

                    board[toLine][toColumn] = king;
                    board[kingLine][kingColumn] = null;

                    // Проверяем под атакой король после хода
                    boolean isStillInCheck = king.isUnderAttack(this, toLine, toColumn);

                    // Возвращаем короля обратно на свое поле
                    board[kingLine][kingColumn] = king;
                    board[toLine][toColumn] = tempPiece;

                    if (!isStillInCheck) {
                        return false; // Кароль может уйти от шаха
                    }

                }
            }
        }

        // Проверка, могут ли другие фигуры блокировать шах или захватить атакующую фигуру
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.color.equals(kingColor) && !(piece instanceof King)) {
                    // Проверяем все возможные ходы фигуры
                    for (int di = 0; di < 8; di++) {
                        for (int dj = 0; dj < 8; dj++) {
                            if (piece.canMoveToPosition(this, i, j, di, dj)) {
                                // Выполняем временный ход для проверки
                                ChessPiece tempPiece = board[di][dj];
                                board[di][dj] = piece;
                                board[i][j] = null;

                                boolean isStillInCheck = king.isUnderAttack(this, kingLine, kingColumn);

                                // Возвращаем фигуру на свое поле
                                board[i][j] = piece;
                                board[di][dj] = tempPiece;

                                if (!isStillInCheck) {
                                    return false; // Фигура может предотвратить шах
                                }
                            }
                        }
                    }
                }
            }
        }
        return true; // У короля нет ходов и никакая фигура не может предотвратить шах Королю (Мат!!!)
    }
}


