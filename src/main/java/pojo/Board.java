package pojo;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Board {
    private Cell[][] board;
    private final int boardSize = 5;
    private List<Piece> redPieces;
    private List<Piece> bluePieces;

    public Board() {
        initBoard();
    }

    public Board(Board other) {
        initEmptyBoard();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.board[i][j] = new Cell(other.getBoard()[i][j]);
            }
        }
        this.redPieces = new ArrayList<>();
        this.bluePieces = new ArrayList<>();
        for (Piece p : other.redPieces) {
            this.redPieces.add(new Piece(p));
        }
        for (Piece p : other.bluePieces) {
            this.bluePieces.add(new Piece(p));
        }
    }

    public void initEmptyBoard() {
        this.board = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.board[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(Coordinate coordinate) {
        return this.board[coordinate.getI()][coordinate.getJ()];
    }

    public void resetCell(Coordinate coordinate) {
        this.board[coordinate.getI()][coordinate.getJ()] = new Cell();
    }

    public void removePieceOnCapture(Coordinate destination) {
        if (!this.board[destination.getI()][destination.getJ()].isEmpty()) {
            Coordinate pieceCoordinate = this.getCell(destination).getPiece().getCurrentCoordinate();
            String c = this.getCell(destination).getPiece().getColor();
            if (c.equalsIgnoreCase("red")) {
                this.redPieces.removeIf(p -> p.getCurrentCoordinate().equals(pieceCoordinate));
            } else {
                this.bluePieces.removeIf(p -> p.getCurrentCoordinate().equals(pieceCoordinate));
            }
        }
    }

    public void setPiece(Piece piece, Coordinate coordinate) {
        this.board[coordinate.getI()][coordinate.getJ()] = new Cell(piece);
        piece.setCurrentCoordinate(coordinate);
    }

    private void initRedPieces() {
        this.redPieces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.redPieces.add(new Piece(PieceList.redStudent));
        }
        this.redPieces.add(PieceList.redMaster);
    }

    private void initBluePieces() {
        this.bluePieces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.bluePieces.add(new Piece(PieceList.blueStudent));
        }
        this.bluePieces.add(PieceList.blueMaster);
    }

    public void initBoard() {
        initEmptyBoard();
        initBluePieces();
        initRedPieces();


        setPiece(this.redPieces.get(0), new Coordinate(0, 0));
        setPiece(this.redPieces.get(1), new Coordinate(0, 1));
        setPiece(this.redPieces.get(2), new Coordinate(0, 3));
        setPiece(this.redPieces.get(3), new Coordinate(0, 4));
        setPiece(this.redPieces.get(4), new Coordinate(0, 2));

        setPiece(this.bluePieces.get(0), new Coordinate(boardSize - 1, 0));
        setPiece(this.bluePieces.get(1), new Coordinate(boardSize - 1, 1));
        setPiece(this.bluePieces.get(2), new Coordinate(boardSize - 1, 3));
        setPiece(this.bluePieces.get(3), new Coordinate(boardSize - 1, 4));
        setPiece(this.bluePieces.get(4), new Coordinate(boardSize - 1, 2));


    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (this.board[i][j].isEmpty()) {
                    System.out.print(". ");
                } else {
                    System.out.print(this.board[i][j].getPiece() + " ");
                }

            }
            System.out.println();
        }
    }

    public void printPiecesCoordinate() {
        System.out.println("Red Pieces: ");
        for (Piece p : this.redPieces) {
            System.out.println(p.getCurrentCoordinate());
        }
        System.out.println("Blue Pieces: ");
        for (Piece p : this.bluePieces) {
            System.out.println(p.getCurrentCoordinate());
        }
    }

}
