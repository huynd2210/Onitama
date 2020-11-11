package pojo;

import lombok.Data;

@Data
public class Cell {
    private Piece piece;
    private boolean isEmpty;

    public Cell(Piece piece){
        this.piece = piece;
        this.isEmpty = false;
    }

    public Cell(){
        this.isEmpty = true;
    }

    public Cell(Cell other){
        if (other.isEmpty){
            this.isEmpty = true;
        }else{
            this.piece = new Piece(other.getPiece());
        }

    }


}
