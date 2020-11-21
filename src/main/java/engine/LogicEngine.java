package engine;

import data.CardState;
import data.State;
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

    private static boolean isRedWin(Board board) {
        if (!board.getBluePieces().contains(PieceList.blueMaster)) {
            return true;
        } else if (!board.getCell(new Coordinate(board.getBoardSize() - 1, 2)).isEmpty()) {
            if (board.getCell(new Coordinate(board.getBoardSize() - 1, 2)).getPiece().equals(PieceList.redMaster)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean isBlueWin(Board board) {
        if (!board.getRedPieces().contains(PieceList.redMaster)) {
            return true;
        } else if (!board.getCell(new Coordinate(0, 2)).isEmpty()) {
            if (board.getCell(new Coordinate(0, 2)).getPiece().equals(PieceList.blueMaster)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String determineWinner(Board board) {
        if (isRedWin(board)) {
            return "red";
        } else if (isBlueWin(board)) {
            return "blue";
        } else {
            System.out.println("determineWinner doesnt return winner");
            return "";
        }
    }

    public static boolean canCapture(Coordinate destination, Board board, boolean isBlueTurn) {
        if (isInbound(destination, board.getBoardSize())) {
            if (!board.getCell(destination).isEmpty()) {
                if (isBlueTurn) {
                    if (board.getCell(destination).getPiece().getColor().equalsIgnoreCase("red")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (board.getCell(destination).getPiece().getColor().equalsIgnoreCase("blue")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void makePlayerMove(State state, Coordinate origin, Coordinate destination, Card card, boolean isBlueTurn){
        Board board = state.getBoard();
        Piece piece = board.getCell(origin).getPiece();
        Coordinate currentPieceCoordinate = new Coordinate(piece.getCurrentCoordinate());

        if (canCapture(destination, board, isBlueTurn)) {
            board.removePieceOnCapture(destination);
            piece.setCurrentCoordinate(destination);
            board.setPiece(piece, piece.getCurrentCoordinate());
            board.resetCell(currentPieceCoordinate);
        } else if (isLegalMove(destination, board)) {
            piece.setCurrentCoordinate(destination);
            board.resetCell(currentPieceCoordinate);
            board.setPiece(piece, piece.getCurrentCoordinate());
        }
        if (isBlueTurn){
            state.setCurrentPlayerTurn("red");
        }else{
            state.setCurrentPlayerTurn("blue");
        }
        DataController.getNextCardState(state.getCardState(), card);
    }

    public static boolean movePiece(Board board, Piece piece, Card card, int moveIndex, boolean isBlueTurn) {
        Coordinate currentPieceCoordinate = new Coordinate(piece.getCurrentCoordinate());
        Coordinate destination = piece.getCurrentCoordinate().addOffset(card.getAvailableMoves().get(moveIndex));

        if (canCapture(destination, board, isBlueTurn)) {
            board.removePieceOnCapture(destination);
            piece.setCurrentCoordinate(destination);
            board.setPiece(piece, piece.getCurrentCoordinate());
            board.resetCell(currentPieceCoordinate);
            return true;
        } else if (isLegalMove(destination, board)) {
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

    public static boolean isPlayerMoveLegal(Coordinate origin, Coordinate destination, Board board, String playerColor, Card card) {
        if (!board.getCell(destination).isEmpty()){
            if (playerColor.equalsIgnoreCase("blue")) {
                System.out.println("4");
                return canCapture(destination, board, true);
            } else {
                System.out.println("5");
                return canCapture(destination, board, false);
            }
        }

        if (!board.getCell(origin).getPiece().getColor().equalsIgnoreCase(playerColor)) {
            System.out.println("1");
            return false;
        }
        if (board.getCell(origin).isEmpty()) {
            System.out.println("2");

            return false;
        }
        if (!isLegalMove(destination, board)) {
            System.out.println("3");

            return false;
        }
        for (Coordinate c : card.getAvailableMoves()){
            if (origin.addOffset(c).equals(destination)){
                return true;
            }
        }
        return false;



    }
}
