package engine;

import data.CardState;
import data.StateData;
import pojo.Card;
import pojo.Coordinate;

public class DataController {
    public static void getNextCardState(CardState cardState, Card playedCard){
        if (cardState.getCurrentBlueHand().contains(playedCard)){
            cardState.getCurrentBlueHand().add(cardState.getCurrentNeutralCard());
            cardState.getCurrentBlueHand().remove(playedCard);
            cardState.setCurrentNeutralCard(playedCard);
        }

        if (cardState.getCurrentRedHand().contains(playedCard)){
            cardState.getCurrentRedHand().add(cardState.getCurrentNeutralCard());
            cardState.getCurrentRedHand().remove(playedCard);
            cardState.setCurrentNeutralCard(playedCard);
        }
    }

    public static StateData offSpring(StateData parent){
        StateData child = new StateData(parent);
        child.setParent(parent);
        return child;
    }

    public static void flipMovement(Card card){
        for (Coordinate coordinate : card.getAvailableMoves()){
            coordinate.flip180();
        }
    }
}
