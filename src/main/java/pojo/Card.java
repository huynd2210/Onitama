package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
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

    public void print(){
        System.out.println(name);
    }

}
