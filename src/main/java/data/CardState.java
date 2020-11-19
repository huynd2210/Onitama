package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import pojo.Card;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CardState {
    private List<Card> currentBlueHand;
    private List<Card> currentRedHand;
    private Card currentNeutralCard;

    public CardState (CardState other){
        this.currentBlueHand = new ArrayList<>();
        this.currentRedHand = new ArrayList<>();
        for (Card c : other.currentBlueHand){
            this.currentBlueHand.add(new Card(c));
        }

        for (Card c : other.currentRedHand){
            this.currentRedHand.add(new Card(c));
        }
        this.currentNeutralCard = new Card(other.currentNeutralCard);
    }

    public void print(){
        System.out.println("Blue Hand");
        for (Card c : currentBlueHand){
            c.print();
        }
        System.out.println("Red Hand:");
        for (Card c : currentRedHand){
            c.print();
        }
        System.out.println("Neutral Card:");
        this.currentNeutralCard.print();
    }



}
