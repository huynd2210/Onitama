package pojo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Card {
    private String name;
    private String color;
    private List<Coordinate> availableMoves;

    public Card(Card other){
        this.name = other.name;
        this.color = other.color;
        this.availableMoves = new ArrayList<>();
        for (Coordinate coordinate : other.availableMoves){
            this.availableMoves.add(new Coordinate(coordinate));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        return name != null ? name.equals(card.name) : card.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void print(){
        System.out.println(name);
    }

}
