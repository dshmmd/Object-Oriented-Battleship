package model;

public class Antiaircraft {
    private int start;

    private String direction;

    Antiaircraft(int start, String direction) {
        this.start = start;
        this.direction = direction;
    }

    public int getStart() {
        return start;
    }

    public String getDirection() {
        return direction;
    }
}
