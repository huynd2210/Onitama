package pojo;

import lombok.Data;
import pojo.Coordinate;


@Data
public class Piece {
    private String color;
    private boolean isMaster;
    private String abbreviation;
    private Coordinate currentCoordinate;

    public Piece(String color, boolean isMaster, String abbreviation){
        this.color = color;
        this.isMaster = isMaster;
        this.abbreviation = abbreviation;
        this.currentCoordinate = null;
    }

    public Piece(Piece other){
        this.color = other.color;
        this.isMaster = other.isMaster;
        this.abbreviation = other.abbreviation;
        if (other.currentCoordinate == null){
            this.currentCoordinate = null;
        }else{
            this.currentCoordinate = new Coordinate(other.currentCoordinate);
        }
    }

    @Override
    public String toString() {
        return this.abbreviation;
    }
}
