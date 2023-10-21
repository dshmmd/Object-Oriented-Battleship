package model;

public class User {
    private final String username;

    private final String password;

    private int mine;

    private int antiaircraft;

    private int airplane;

    private int scanner;

    private int invisible;

    private int money;

    private int score;

    private int wins;

    private int loses;

    private int draws;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        mine = 0;
        antiaircraft = 0;
        airplane = 0;
        scanner = 0;
        invisible = 0;
        money = 50;
        score = 0;
        wins = 0;
        loses = 0;
        draws = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAirplane() {
        return airplane;
    }

    public int getAntiaircraft() {
        return antiaircraft;
    }

    public int getDraws() {
        return draws;
    }

    public int getInvisible() {
        return invisible;
    }

    public int getLoses() {
        return loses;
    }

    public int getMine() {
        return mine;
    }

    public int getMoney() {
        return money;
    }

    public int getScanner() {
        return scanner;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public void increaseDraws() {
        this.draws += 1;
    }

    public void increaseLoses() {
        this.loses += 1;
    }

    public void increaseWins() {
        this.wins += 1;
    }

    public void increaseAirplane(int airplane) {
        this.airplane += airplane;
    }

    public void increaseAntiaircraft(int antiaircraft) {
        this.antiaircraft += antiaircraft;
    }

    public void increaseInvisible(int invisible) {
        this.invisible += invisible;
    }

    public void increaseMine(int mine) {
        this.mine += mine;
    }

    public void increaseScanner(int scanner) {
        this.scanner += scanner;
    }

    public void increaseMoney(int money) {
        this.money += money;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void decreaseMoney(int money) {
        this.money -= money;
    }

    public void decreaseScore(int score) {
        this.score -= score;
    }

    public void setMine(int mine) {
        this.mine = mine;
    }

    public void setAntiaircraft(int antiaircraft) {
        this.antiaircraft = antiaircraft;
    }

    public void setInvisible(int invisible) {
        this.invisible = invisible;
    }

    public void setScanner(int scanner) {
        this.scanner = scanner;
    }

    public void setAirplane(int airplane) {
        this.airplane = airplane;
    }
}