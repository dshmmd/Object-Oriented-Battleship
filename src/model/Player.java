package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private final String username;

    private int numberOfShip1;

    private int numberOfShip2;

    private int numberOfShip3;

    private int numberOfShip4;

    private int mine;

    private int antiaircraft;

    private int invisible;

    private int airplane;

    private int scanner;

    private Cell[][] cells;

    private HashMap<Integer, Integer> shipIds;

    private int[][] antiaircrafts;

    private ArrayList<Antiaircraft> antiaircraftArrayList;

    private boolean isWinner;

    public Player(User user) {
        this.username = user.getUsername();
        this.mine = user.getMine();
        this.antiaircraft = user.getAntiaircraft();
        this.invisible = user.getInvisible();
        this.airplane = user.getAirplane();
        this.scanner = user.getScanner();
        this.numberOfShip1 = 0;
        this.numberOfShip2 = 0;
        this.numberOfShip3 = 0;
        this.numberOfShip4 = 0;

        shipIds = new HashMap<>();
        antiaircraftArrayList = new ArrayList<>();
        cells = new Cell[11][11];
        antiaircrafts = new int[11][11];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell();
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                antiaircrafts[i][j] = 0;
            }
        }
    }

    public int getNumberOfShip(int model) {
        switch (model) {
            case 1:
                return numberOfShip1;
            case 2:
                return numberOfShip2;
            case 3:
                return numberOfShip3;
            default:
                return numberOfShip4;
        }
    }

    public void increaseNumberOfShip(int model) {
        switch (model) {
            case 1:
                this.numberOfShip1++;
                break;
            case 2:
                this.numberOfShip2++;
                break;
            case 3:
                this.numberOfShip3++;
                break;
            default:
                this.numberOfShip4++;
                break;
        }
    }

    public int getMine() {
        return mine;
    }

    public void decreaseMine() {
        this.mine--;
    }

    public int getAntiaircraft() {
        return antiaircraft;
    }

    public void decreaseAntiaircraft() {
        this.antiaircraft--;
    }

    public int getInvisible() {
        return invisible;
    }

    public void decreaseInvisible() {
        this.invisible--;
    }

    public int getAirplane() {
        return airplane;
    }

    public void decreaseAirplane() {
        this.airplane--;
    }

    public int getScanner() {
        return scanner;
    }

    public void decreaseScanner() {
        this.scanner--;
    }

    public String getUsername() {
        return username;
    }

    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    public void addShipIds(int id, int model) {
        this.shipIds.put(id, model);
    }

    public int getModelById(int id) {
        return shipIds.get(id);
    }

    public boolean haveThisShip(int id) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i][j].getId() == id && !cells[i][j].getIsShot()) return true;
            }
        }
        return false;
    }

    public void updateAntiaircraft() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                antiaircrafts[i][j] = 0;
            }
        }

        for (Antiaircraft eachAntiaircraft : antiaircraftArrayList) {
            int start = eachAntiaircraft.getStart();
            String direction = eachAntiaircraft.getDirection();
            if (direction.equals("-h")) {
                for (int i = start; i < start + 3; i++) {
                    for (int j = 0; j < 10; j++) {
                        antiaircrafts[i][j] = 1;
                    }
                }

            } else {
                for (int i = 0; i < 10; i++) {
                    for (int j = start; j < start + 3; j++) {
                        antiaircrafts[i][j] = 1;
                    }
                }

            }
        }
    }

    public void addNewAntiaircraft(int start, String direction) {
        Antiaircraft antiaircraft = new Antiaircraft(start, direction);
        antiaircraftArrayList.add(antiaircraft);
    }

    public ArrayList<Antiaircraft> getAntiaircraftArrayList() {
        return antiaircraftArrayList;
    }

    public void setAntiaircraftArrayList(ArrayList<Antiaircraft> antiaircraftArrayList) {
        this.antiaircraftArrayList = antiaircraftArrayList;
    }

    public int[][] getAntiaircrafts() {
        return antiaircrafts;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public boolean getWinner() {
        return isWinner;
    }
}
