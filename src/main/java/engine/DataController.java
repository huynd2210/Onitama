package engine;

import data.CardState;
import data.StateData;
import pojo.Card;
import pojo.Coordinate;

public class DataController {

    public static void getNextCardState(CardState cardState, Card playedCard){
        if (cardState.getCurrentRedHand().contains(playedCard)){
            Card tmp = cardState.getCurrentNeutralCard();
            cardState.getCurrentRedHand().add(tmp);
            cardState.setCurrentNeutralCard(playedCard);
            cardState.getCurrentRedHand().remove(playedCard);
        }else if (cardState.getCurrentBlueHand().contains(playedCard)){
            Card tmp = cardState.getCurrentNeutralCard();
            cardState.getCurrentBlueHand().add(tmp);
            cardState.setCurrentNeutralCard(playedCard);
            cardState.getCurrentBlueHand().remove(playedCard);
        }else{
            System.out.println("Card not found in hand");
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
