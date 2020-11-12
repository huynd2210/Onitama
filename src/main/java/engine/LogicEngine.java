package engine;

import data.CardState;
import pojo.*;

public class LogicEngine {
    private static boolean isCellEmpty(Board board, Coordinate c) {
        return board.getCell(c).isEmpty();
    }

    private static boolean isRedMasterOnBlueThrone(Board board) {
        if (!isCellEmpty(board, new Coordinate(board.getBoardSize() - 1, 2))) {
            if (board.getCell(new Coordinate(board.getBoardSize() - 1, 2)).getPiece().equals(PieceList.redMaster)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean isBlueMasterOnRedThrone(Board board) {
        if (!isCellEmpty(board, new Coordinate(0, 2))) {
            if (board.getCell(new Coordinate(0, 2)).getPiece().equals(PieceList.blueMaster)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isEnd(Board board) {
        if (isRedMasterOnBlueThrone(board)) {
            return true;
        } else if (isBlueMasterOnRedThrone(board)) {
            return true;
        } else {
            return !(board.getRedPieces().contains(PieceList.redMaster) && board.getBluePieces().contains(PieceList.blueMaster));
        }
    }

    public static String determineWinner(Board board) {
        if (!board.getBluePieces().contains(PieceList.blueMaster) || board.getCell(new Coordinate(board.getBoardSize() - 1, 2)).getPiece().equals(PieceList.redMaster)) {
            return "red";
        } else if (!board.getRedPieces().contains(PieceList.redMaster) || board.getCell(new Coordinate(0, 2)).getPiece().equals(PieceList.blueMaster)) {
            return "blue";
        } else {
            return "";
        }
    }

    public static boolean movePiece(Board board, Piece piece, Card card, int moveIndex) {
        Coordinate currentPieceCoordinate = new Coordinate(piece.getCurrentCoordinate());
        Coordinate destination = piece.getCurrentCoordinate().addOffset(card.getAvailableMoves().get(moveIndex));
        if (isLegalMove(destination, board)) {
            piece.setCurrentCoordinate(destination);
            board.resetCell(currentPieceCoordinate);
            board.setPiece(piece, piece.getCurrentCoordinate());
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLegalMove(Coordinate destination, Board board) {
        return (isInbound(destination, board.getBoardSize()) && board.getCell(destination).isEmpty());
    }

    private static boolean isInbound(Coordinate destination, int boardSize) {
        return (destination.getI() >= 0 && destination.getI() < boardSize && destination.getJ() >= 0 && destination.getJ() < boardSize);
    }
}
