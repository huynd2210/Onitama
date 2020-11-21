package pojo;

import lombok.*;

@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate that = (Coordinate) o;

        if (i != that.i) return false;
        return j == that.j;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }
}
