package engine;

import data.CardState;
import data.State;
import pojo.Board;
import pojo.Card;
import pojo.Coordinate;

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
        child.setParent(parent);
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
                if (!board.getCell(new Coordinate(i,j)).isEmpty()){
                    if (emptySpot != 0){
                        notation.append(emptySpot);
                    }
                    emptySpot = 0;
                    notation.append(board.getCell(new Coordinate(i, j)).getPiece().getAbbreviation());
                }else{
                    emptySpot += 1;
                }
            }
            if (emptySpot != 0){
                notation.append(emptySpot);
            }
            notation.append("/");
        }
        notation.deleteCharAt(notation.length() - 1);
        return notation.toString();
    }

}
