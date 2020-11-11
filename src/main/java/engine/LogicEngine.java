package engine;

import data.CardState;
import pojo.*;

public class LogicEngine {
    public static void movePiece(Board board, Piece piece, Card card, int moveIndex){
        Coordinate currentPieceCoordinate = new Coordinate(piece.getCurrentCoordinate());
        Coordinate destination = piece.getCurrentCoordinate().addOffset(card.getAvailableMoves().get(moveIndex));
        if (isLegalMove(destination, board)){
            piece.setCurrentCoordinate(destination);
            board.resetCell(currentPieceCoordinate);
            board.setPiece(piece, piece.getCurrentCoordinate());
        }else {
            System.out.println("Move Not Legal");
        }
    }

    private static boolean isLegalMove(Coordinate destination, Board board){
        return (isInbound(destination, board.getBoardSize()) && board.getCell(destination).isEmpty());
    }

    private static boolean isInbound(Coordinate destination, int boardSize){
         return (destination.getI() >= 0 && destination.getI() < boardSize && destination.getJ() >= 0 && destination.getJ() < boardSize);

    }
}
