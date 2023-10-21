package controller;

import model.Cell;
import model.CellType;
import model.Player;

import java.util.regex.Matcher;

public class BoardLayoutController {
    public static void putShip(Player player, Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (!matcher.group(1).matches("^[1-4]$")) {
            System.out.println("invalid ship number");

        } else if (isCoordinationWrong(matcher.group(2), matcher.group(3))) {
            System.out.println("wrong coordination");

        } else if (!matcher.group(4).matches("^(-h|-v)$")) {
            System.out.println("invalid direction");

        } else if (isShipOutOfBoard(matcher.group(1),
                matcher.group(2),
                matcher.group(3),
                matcher.group(4))) {
            System.out.println("off the board");

        } else if (!canUseThisShip(matcher.group(1), player)) {
            System.out.println("you don't have this type of ship");

        } else if (doesItIntersectWithSomething(matcher, player)) {
            System.out.println("collision with the other ship or mine on the board");

        } else {
            putShipSuccessfully(player, matcher);

        }
    }

    private static void putShipSuccessfully(Player player, Matcher matcher) {
        int shipModel = Integer.parseInt(matcher.group(1));
        int x = Integer.parseInt(matcher.group(2)) - 1;
        int y = Integer.parseInt(matcher.group(3)) - 1;
        String direction = matcher.group(4);

        if (direction.equals("-h")) {
            for (int i = x; i < x + shipModel; i++) {
                Cell cell = player.getCell(i, y);
                cell.setCellType(CellType.SHIP);
                cell.setId(Cell.getIdCounter());
            }
            player.addShipIds(Cell.getIdCounter(), shipModel);
            player.increaseNumberOfShip(shipModel);
            Cell.increaseIdCounter();

        } else {
            for (int i = y; i < y + shipModel; i++) {
                Cell cell = player.getCell(x, i);
                cell.setCellType(CellType.SHIP);
                cell.setId(Cell.getIdCounter());
            }
            player.addShipIds(Cell.getIdCounter(), shipModel);
            player.increaseNumberOfShip(shipModel);
            Cell.increaseIdCounter();

        }
    }

    public static void putMine(Player player, Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (isCoordinationWrong(matcher.group(1), matcher.group(2))) {
            System.out.println("wrong coordination");

        } else if (player.getMine() < 1) {
            System.out.println("you don't have enough mine");

        } else if (!canPutMineHear(player, matcher.group(1), matcher.group(2))) {
            System.out.println("collision with the other ship or mine on the board");

        } else {
            int x = Integer.parseInt(matcher.group(1)) - 1;
            int y = Integer.parseInt(matcher.group(2)) - 1;
            Cell cell = player.getCell(x, y);
            cell.setCellType(CellType.MINE);
            player.decreaseMine();
        }
    }

    public static void putAntiaircraft(Player player, Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (Integer.parseInt(matcher.group(1)) < 1 ||
                Integer.parseInt(matcher.group(1)) > 10) {
            System.out.println("wrong coordination");

        } else if (isAntiaircraftOutOfBoard(matcher.group(1))) {
            System.out.println("off the board");

        } else if (!matcher.group(2).matches("^(-h|-v)$")) {
            System.out.println("invalid direction");

        } else if (player.getAntiaircraft() < 1) {
            System.out.println("you don't have enough antiaircraft");

        } else {
            player.addNewAntiaircraft(Integer.parseInt(matcher.group(1)) - 1, matcher.group(2));
            player.updateAntiaircraft();
            player.decreaseAntiaircraft();
        }
    }

    public static void putInvisible(Player player, Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (isCoordinationWrong(matcher.group(1), matcher.group(2))) {
            System.out.println("wrong coordination");

        } else if (player.getInvisible() < 1) {
            System.out.println("you don't have enough invisible");

        } else if (thereIsNoShip(player, matcher.group(1), matcher.group(2))) {
            System.out.println("there is no ship on this place on the board");

        } else if (isItInvisibleThere(player, matcher.group(1), matcher.group(2))) {
            System.out.println("this place has already made invisible");

        } else {
            int x = Integer.parseInt(matcher.group(1)) - 1;
            int y = Integer.parseInt(matcher.group(2)) - 1;
            Cell cell = player.getCell(x, y);
            cell.setCellType(CellType.INVISIBLE_SHIP);
            player.decreaseInvisible();
        }
    }

    private static boolean isCoordinationWrong(String xString, String yString) {
        return !(xString.matches("^[1-9]$|^10$") &&
                yString.matches("^[1-9]$|^10$"));
    }

    private static boolean isShipOutOfBoard(String ship, String xString, String yString, String direction) {
        int shipModel = Integer.parseInt(ship);
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);

        if (direction.equals("-h"))
            return x + shipModel > 11;
        else
            return y + shipModel > 11;
    }

    private static boolean canUseThisShip(String ship, Player player) {
        int shipModel = Integer.parseInt(ship);
        switch (shipModel) {
            case 1:
                return player.getNumberOfShip(1) < 4;
            case 2:
                return player.getNumberOfShip(2) < 3;
            case 3:
                return player.getNumberOfShip(3) < 2;
            default:
                return player.getNumberOfShip(4) < 1;
        }
    }

    private static boolean doesItIntersectWithSomething(Matcher matcher, Player player) {
        int shipModel = Integer.parseInt(matcher.group(1));
        int x = Integer.parseInt(matcher.group(2)) - 1;
        int y = Integer.parseInt(matcher.group(3)) - 1;
        String direction = matcher.group(4);

        if (direction.equals("-h")) {
            for (int i = x; i < x + shipModel; i++) {
                Cell cell = player.getCell(i, y);
                if (cell.getCellType() != CellType.EMPTY) return true;
            }

        } else {
            for (int i = y; i < y + shipModel; i++) {
                Cell cell = player.getCell(x, i);
                if (cell.getCellType() != CellType.EMPTY) return true;
            }

        }
        return false;
    }

    private static boolean canPutMineHear(Player player, String xString, String yString) {
        int x = Integer.parseInt(xString) - 1;
        int y = Integer.parseInt(yString) - 1;
        return player.getCell(x, y).getCellType() == CellType.EMPTY;
    }

    private static boolean thereIsNoShip(Player player, String xString, String yString) {
        int x = Integer.parseInt(xString) - 1;
        int y = Integer.parseInt(yString) - 1;
        Cell cell = player.getCell(x, y);
        return (cell.getCellType() != CellType.SHIP && cell.getCellType() != CellType.INVISIBLE_SHIP);
    }

    private static boolean isItInvisibleThere(Player player, String xString, String yString) {
        int x = Integer.parseInt(xString) - 1;
        int y = Integer.parseInt(yString) - 1;
        return player.getCell(x, y).getCellType() == CellType.INVISIBLE_SHIP;
    }

    private static boolean isAntiaircraftOutOfBoard(String coordination) {
        int startRange = Integer.parseInt(coordination);
        return startRange + 3 > 11;
    }

    public static boolean canFinishArranging(Player player) {
        return player.getNumberOfShip(1) == 4 &&
                player.getNumberOfShip(2) == 3 &&
                player.getNumberOfShip(3) == 2 &&
                player.getNumberOfShip(4) == 1;
    }

    public static void printBoard(Player player) {
        for (int i = 0; i < 10; i++) {
            System.out.print("|");
            for (int j = 0; j < 10; j++) {
                System.out.print(getBoardValue(player,
                        player.getCell(j, i),
                        player.getAntiaircrafts()[i][j]));
                System.out.print("|");
            }
            System.out.println("");
        }
    }

    public static String getBoardValue(Player player, Cell cell, int antiaircraft) {
        switch (cell.getCellType()) {
            case SHIP:
                return "S" + player.getModelById(cell.getId());
            case INVISIBLE_SHIP:
                return "I" + player.getModelById(cell.getId());
            case MINE:
                return "Mm";
            default:
                return antiaircraft == 1 ? "AA" : "  ";
        }
    }
}
