package engine;

import data.CardState;
import data.State;
import pojo.Board;
import pojo.Card;
import pojo.Coordinate;
import pojo.PieceList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataController {

    public static void getNextCardState(CardState cardState, Card playedCard) {

        if (cardState.getCurrentRedHand().contains(playedCard)) {
            Card tmp = cardState.getCurrentNeutralCard();
            cardState.getCurrentRedHand().add(tmp);
            cardState.setCurrentNeutralCard(playedCard);
            cardState.getCurrentRedHand().remove(playedCard);
        } else if (cardState.getCurrentBlueHand().contains(playedCard)) {
            Card tmp = cardState.getCurrentNeutralCard();
            cardState.getCurrentBlueHand().add(tmp);
            cardState.setCurrentNeutralCard(playedCard);
            cardState.getCurrentBlueHand().remove(playedCard);
        } else {
            System.out.println("CardState status: ");
            cardState.print();
            System.out.println("Played Card: ");
            playedCard.print();
            System.out.println("Card not found in hand");
        }
    }

    public static State offSpring(State parent) {
        State child = new State(parent);
        child.addParent(parent);
        return child;
    }

    public static void flipMovement(Card card) {
        for (Coordinate coordinate : card.getAvailableMoves()) {
            coordinate.flip180();
        }
    }

    public static String boardToNotation(Board board) {
        StringBuilder notation = new StringBuilder();
        for (int i = 0; i < board.getBoardSize(); i++) {
            int emptySpot = 0;
            for (int j = 0; j < board.getBoardSize(); j++) {
                if (!board.getCell(new Coordinate(i, j)).isEmpty()) {
                    if (emptySpot != 0) {
                        notation.append(emptySpot);
                    }
                    emptySpot = 0;
                    notation.append(board.getCell(new Coordinate(i, j)).getPiece().getAbbreviation());
                } else {
                    emptySpot += 1;
                }
            }
            if (emptySpot != 0) {
                notation.append(emptySpot);
            }
            notation.append("/");
        }
        notation.deleteCharAt(notation.length() - 1);
        return notation.toString();
    }

    private static List<String> splitString(String input, String regex) {
        return new ArrayList<>(Arrays.asList(input.split(regex)));
    }

    public static Board parseNotation(String notation) {
        List<String> notationSplit = splitString(notation, "/");
        Board board = new Board();
        board.initEmptyBoard();
        for (int i = 0; i < notationSplit.size(); i++) {
            int emptySpot = 0;
            for (int j = 0; j < notationSplit.get(i).length(); j++) {
                if (notationSplit.get(i).charAt(j) == 'p') {
                    board.setPiece(PieceList.redStudent, new Coordinate(i, j + emptySpot));
                } else if (notationSplit.get(i).charAt(j) == 'm') {
                    board.setPiece(PieceList.redMaster, new Coordinate(i, j + emptySpot));
                } else if (notationSplit.get(i).charAt(j) == 'P') {
                    board.setPiece(PieceList.blueStudent, new Coordinate(i, j + emptySpot));
                } else if (notationSplit.get(i).charAt(j) == 'M') {
                    board.setPiece(PieceList.blueMaster, new Coordinate(i, j + emptySpot));
                } else {
                    emptySpot = Character.getNumericValue(notationSplit.get(i).charAt(j)) - 1;
                }
            }
        }
        return board;
    }


    public static String getCardStateNotation(CardState cardState){
        StringBuilder sb = new StringBuilder();
        sb.append(cardState.getCurrentNeutralCard().getName());
        sb.append("/");
        for (Card c : cardState.getCurrentBlueHand()){
            sb.append(c.getName());
            sb.append("/");
        }
        for (Card c : cardState.getCurrentRedHand()){
            sb.append(c.getName());
            sb.append("/");
        }
        return sb.toString();
    }
}
