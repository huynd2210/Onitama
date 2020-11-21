package data;

import engine.DataController;
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

    public CardState(CardState other) {
        this.currentBlueHand = new ArrayList<>();
        this.currentRedHand = new ArrayList<>();
        for (Card c : other.currentBlueHand) {
            this.currentBlueHand.add(new Card(c));
        }

        for (Card c : other.currentRedHand) {
            this.currentRedHand.add(new Card(c));
        }
        this.currentNeutralCard = new Card(other.currentNeutralCard);
    }

    public CardState(String notation) {
        String[] tokens = notation.split("/");
        this.currentBlueHand = new ArrayList<>();
        this.currentRedHand = new ArrayList<>();
        for (Card c : DataController.getAllCards()) {
            if (tokens[0].equalsIgnoreCase(c.getName())) {
                this.currentNeutralCard = c;
            } else if (tokens[1].equalsIgnoreCase(c.getName())) {
                this.currentBlueHand.add(c);
            } else if (tokens[2].equalsIgnoreCase(c.getName())) {
                this.currentBlueHand.add(c);
            } else if (tokens[3].equalsIgnoreCase(c.getName())) {
                this.currentRedHand.add(c);
            } else if (tokens[4].equalsIgnoreCase(c.getName())) {
                this.currentRedHand.add(c);
            }
        }
    }

    public void print() {
        System.out.print("Blue Hand: ");
        for (Card c : currentBlueHand) {
            c.print();
        }
        System.out.println();
        System.out.print("Red Hand: ");
        for (Card c : currentRedHand) {
            c.print();
        }
        System.out.println();
        System.out.print("Neutral Card: ");
        this.currentNeutralCard.print();
        System.out.println();
    }


}
