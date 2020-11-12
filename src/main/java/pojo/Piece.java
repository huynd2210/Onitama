package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pojo.Coordinate;


@Getter
@Setter
@AllArgsConstructor
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;

        Piece piece = (Piece) o;

        return abbreviation != null ? abbreviation.equals(piece.abbreviation) : piece.abbreviation == null;
    }

    @Override
    public int hashCode() {
        return abbreviation != null ? abbreviation.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.abbreviation;
    }
}
