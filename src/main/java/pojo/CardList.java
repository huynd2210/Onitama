package pojo;

import java.util.Arrays;

public class CardList {
    public static final Card tigerCard = new Card("tiger", "blue", Arrays.asList(new Coordinate(1, 0), new Coordinate(-2, 0)));
    public static final Card rabbitCard = new Card("rabbit", "blue", Arrays.asList(new Coordinate(1, -1), new Coordinate(-1, 1), new Coordinate(0, 2)));
    public static final Card craneCard = new Card("crane", "blue", Arrays.asList(new Coordinate(1, -1), new Coordinate(-1, 0), new Coordinate(1, 1)));
    public static final Card crabCard = new Card("crab", "blue", Arrays.asList(new Coordinate(-1, 0), new Coordinate(0, -2), new Coordinate(0, 2)));
    public static final Card horseCard = new Card("horse", "red", Arrays.asList(new Coordinate(-1, 0), new Coordinate(1, 0), new Coordinate(0, -1)));

}
