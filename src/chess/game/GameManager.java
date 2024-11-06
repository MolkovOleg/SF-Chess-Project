package chess.game;

import java.util.Scanner;

import chess.board.ChessBoard;
import chess.pieces.*;

public class GameManager {

    public static ChessBoard buildBoard() {
        ChessBoard chessBoard = new ChessBoard("White");

        chessBoard.board[0][0] = new Rook("White");
        chessBoard.board[0][1] = new Horse("White");
        chessBoard.board[0][2] = new Bishop("White");
        chessBoard.board[0][3] = new Queen("White");
        chessBoard.board[0][4] = new King("White");
        chessBoard.board[0][5] = new Bishop("White");
        chessBoard.board[0][6] = new Horse("White");
        chessBoard.board[0][7] = new Rook("White");
        chessBoard.board[1][0] = new Pawn("White");
        chessBoard.board[1][1] = new Pawn("White");
        chessBoard.board[1][2] = new Pawn("White");
        chessBoard.board[1][3] = new Pawn("White");
        chessBoard.board[1][4] = new Pawn("White");
        chessBoard.board[1][5] = new Pawn("White");
        chessBoard.board[1][6] = new Pawn("White");
        chessBoard.board[1][7] = new Pawn("White");

        chessBoard.board[7][0] = new Rook("Black");
        chessBoard.board[7][1] = new Horse("Black");
        chessBoard.board[7][2] = new Bishop("Black");
        chessBoard.board[7][3] = new Queen("Black");
        chessBoard.board[7][4] = new King("Black");
        chessBoard.board[7][5] = new Bishop("Black");
        chessBoard.board[7][6] = new Horse("Black");
        chessBoard.board[7][7] = new Rook("Black");
        chessBoard.board[6][0] = new Pawn("Black");
        chessBoard.board[6][1] = new Pawn("Black");
        chessBoard.board[6][2] = new Pawn("Black");
        chessBoard.board[6][3] = new Pawn("Black");
        chessBoard.board[6][4] = new Pawn("Black");
        chessBoard.board[6][5] = new Pawn("Black");
        chessBoard.board[6][6] = new Pawn("Black");
        chessBoard.board[6][7] = new Pawn("Black");

        return chessBoard;
    }

    public static void main(String[] args) {
        ChessBoard chessBoard = buildBoard();
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                Чтобы проверить игру надо вводить такие команды:
                'exit' - для выхода
                'replay' - для перезапуска игры
                'castling0' или 'castling7' - для рокировки по соответствующей линии
                'move 1 1 2 3' - для передвижения фигуры с позиции 1 1 на позицию 2 3
                (поле это двухмерный массив от 0 до 7)
                Проверьте могут ли фигуры ходить друг сквозь друга, корректно ли съедают друг друга,
                можно ли поставить шах и сделать рокировку?""");
        System.out.println();
        chessBoard.printBoard();

        // Реализация команд
        while (true) {
            String command = scanner.nextLine();

            if (command.equals("exit")) {
                break;
            } else if (command.equals("replay")) {
                System.out.println("Перезапуск партии...");
                chessBoard = buildBoard();
                chessBoard.printBoard();
            } else {
                if (command.equals("castling0")) {
                    if (chessBoard.castling0()) {
                        System.out.println("Рокировка удалась");
                        chessBoard.printBoard();
                    } else {
                        System.out.println("Рокировка не удалась");
                    }
                } else if (command.equals("castling7")) {
                    if (chessBoard.castling7()) {
                        System.out.println("Рокировка удалась");
                        chessBoard.printBoard();
                    } else {
                        System.out.println("Рокировка не удалась");
                    }
                } else if (command.contains("move")) {
                    String[] str = command.split(" ");

                    try {
                        int line = Integer.parseInt(str[1]);
                        int column = Integer.parseInt(str[2]);
                        int toLine = Integer.parseInt(str[3]);
                        int toColumn = Integer.parseInt(str[4]);
                        if (chessBoard.moveToPosition(line, column, toLine, toColumn)) {
                            System.out.println("Успешный ход фигурой");
                            chessBoard.printBoard();
                        } else {
                            System.out.println("Вы не можете сделать данный ход");
                        }
                    } catch (Exception e) {
                        System.out.println("Вы что-то ввели не так, проверьте условия!");
                    }
                }
            }
        }
    }
}
