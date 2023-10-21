package model;

public class Cell {
    private CellType cellType;

    private boolean isShot;

    private static int IdCounter = 1;

    private int id;

    Cell() {
        setCellType(CellType.EMPTY);
        setIsShot(false);
        this.id = 0;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setIsShot(boolean shot) {
        isShot = shot;
    }

    public boolean getIsShot() {
        return isShot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdCounter() {
        return IdCounter;
    }

    public static void increaseIdCounter() {
        IdCounter++;
    }
}
