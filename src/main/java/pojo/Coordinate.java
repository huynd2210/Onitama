package pojo;

import lombok.Data;

@Data
public class Coordinate {
    private int i;
    private int j;

    public Coordinate(int i, int j){
        this.i = i;
        this.j = j;
    }

    public Coordinate(Coordinate other){
        this.i = other.i;
        this.j = other.j;
    }

    public Coordinate addOffset(Coordinate other){
        return new Coordinate(this.i + other.i, this.j + other.j);
    }

    public void flip180(){
        this.i = -i;
        this.j = -j;
    }

}
