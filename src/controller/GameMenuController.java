package controller;

import model.Antiaircraft;
import model.Cell;
import model.CellType;
import model.Player;
import view.GameMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameMenuController {
    public static boolean bomb(Player attacker, Player defender, Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");
            return false;

        } else if (isCoordinationWrong(matcher.group(1), matcher.group(2))) {
            System.out.println("wrong coordination");
            return false;

        } else if (isThereShot(matcher.group(1), matcher.group(2), defender)) {
            System.out.println("this place has already destroyed");
            return false;

        } else if (damageAPieceOfShip(matcher.group(1), matcher.group(2), defender)) {
            System.out.println("the rival's ship was damaged");
            return false;

        } else if (destroyAShip(matcher.group(1), matcher.group(2), defender)) {
            int x = Integer.parseInt(matcher.group(1)) - 1;
            int y = Integer.parseInt(matcher.group(2)) - 1;
            Cell cell = defender.getCell(x, y);
            int shipModel = defender.getModelById(cell.getId());
            System.out.println("the rival's ship" + shipModel + " was destroyed");

            if (isGameEnded(defender)) {
                GameMenu.setGameCondition(1);
                attacker.setWinner(true);
                defender.setWinner(false);
                System.out.println(attacker.getUsername() + " is winner");
                return true;
            }
            return false;

        } else if (shootMine(matcher.group(1), matcher.group(2), attacker, defender)) {
            System.out.println("you destroyed the rival's mine");
            System.out.println("turn completed");
            return true;

        } else {
            int x = Integer.parseInt(matcher.group(1)) - 1;
            int y = Integer.parseInt(matcher.group(2)) - 1;
            Cell cell = defender.getCell(x, y);
            cell.setIsShot(true);
            System.out.println("the bomb fell into sea");
            System.out.println("turn completed");
            return true;
        }
    }

    public static boolean putAirplane(Player attacker, Player defender, Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (isCoordinationWrong(matcher.group(1), matcher.group(2))) {
            System.out.println("wrong coordination");

        } else if (!matcher.group(3).matches("^(-h|-v)$")) {
            System.out.println("invalid direction");

        } else if (isAirplaneOutOfBoard(matcher.group(1), matcher.group(2), matcher.group(3))) {
            System.out.println("off the board");

        } else if (attacker.getAirplane() < 1) {
            System.out.println("you don't have airplane");

        } else if (antiaircraftActed(matcher.group(1), matcher.group(2), matcher.group(3), defender)) {
            attacker.decreaseAirplane();
            System.out.println("the rival's antiaircraft destroyed your airplane");

        } else {
            airplaneSuccessfullyActed(attacker, defender, matcher);

            if (isGameEnded(attacker) && isGameEnded(defender)) {
                GameMenu.setGameCondition(2);
                attacker.setWinner(false);
                defender.setWinner(false);
                System.out.println("draw");
                return true;

            } else if (isGameEnded(defender)) {
                GameMenu.setGameCondition(1);
                attacker.setWinner(true);
                defender.setWinner(false);
                System.out.println(attacker.getUsername() + " is winner");
                return true;
            }
        }

        return false;
    }

    private static void airplaneSuccessfullyActed(Player attacker, Player defender, Matcher matcher) {
        int destroyCount = 0;
        attacker.decreaseAirplane();
        int x = Integer.parseInt(matcher.group(1)) - 1;
        int y = Integer.parseInt(matcher.group(2)) - 1;

        if (matcher.group(3).equals("-h")) {
            for (int i = y; i < y + 2; i++) {
                for (int j = x; j < x + 5; j++) {
                    Cell cell = defender.getCell(j, i);
                    if ((cell.getCellType() == CellType.SHIP ||
                            cell.getCellType() == CellType.INVISIBLE_SHIP) &&
                            !cell.getIsShot()) {
                        destroyCount++;
                        cell.setIsShot(true);
                    } else if (cell.getCellType() == CellType.MINE && !cell.getIsShot()) {
                        cell.setIsShot(true);
                        attacker.getCell(j, i).setIsShot(true);
                    } else {
                        cell.setIsShot(true);
                    }
                }
            }

        } else {
            for (int i = y; i < y + 5; i++) {
                for (int j = x; j < x + 2; j++) {
                    Cell cell = defender.getCell(j, i);
                    if ((cell.getCellType() == CellType.SHIP ||
                            cell.getCellType() == CellType.INVISIBLE_SHIP) &&
                            !cell.getIsShot()) {
                        destroyCount++;
                        cell.setIsShot(true);
                    } else if (cell.getCellType() == CellType.MINE && !cell.getIsShot()) {
                        cell.setIsShot(true);
                        attacker.getCell(j, i).setIsShot(true);
                    } else {
                        cell.setIsShot(true);
                    }
                }
            }

        }

        if (destroyCount > 0) {
            System.out.println(destroyCount + " pieces of rival's ships was damaged");
        } else {
            System.out.println("target not found");
        }
    }

    public static void scanEnemy(Player attacker, Player defender, Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (isCoordinationWrong(matcher.group(1), matcher.group(2))) {
            System.out.println("wrong coordination");

        } else if (isScannerOutOfBoard(matcher.group(1), matcher.group(2))) {
            System.out.println("off the board");

        } else if (attacker.getScanner() < 1) {
            System.out.println("you don't have scanner");

        } else {
            attacker.decreaseScanner();
            Cell[][] cells = defender.getCells();
            int x = Integer.parseInt(matcher.group(1)) - 1;
            int y = Integer.parseInt(matcher.group(2)) - 1;

            for (int i = y; i < y + 3; i++) {
                System.out.print("|");
                for (int j = x; j < x + 3; j++) {
                    if (cells[i][j].getCellType() == CellType.SHIP && !cells[i][j].getIsShot())
                        System.out.print("SX");
                    else
                        System.out.print("  ");
                    System.out.print("|");
                }
                System.out.println("");
            }
        }
    }

    public static void forfeit(Player loser, Player winner) {
        GameMenu.setGameCondition(3);
        loser.setWinner(false);
        winner.setWinner(true);
        System.out.println(loser.getUsername() + " is forfeited");
        System.out.println(winner.getUsername() + " is winner");
    }

    public static void printMyBoard(Player player) {
        for (int i = 0; i < 10; i++) {
            System.out.print("|");
            for (int j = 0; j < 10; j++) {
                System.out.print(getMyBoardValue(player, player.getCell(j, i), player.getAntiaircrafts()[i][j]));
                System.out.print("|");
            }
            System.out.println("");
        }
    }

    public static String getMyBoardValue(Player player, Cell cell, int antiaircraft) {
        if (cell.getCellType() == CellType.EMPTY) {
            if (cell.getIsShot()) return "XX";
            else if (antiaircraft == 1) return "AA";
            else return "  ";
        } else if (cell.getCellType() == CellType.SHIP) {
            if (cell.getIsShot()) return "D" + player.getModelById(cell.getId());
            else return "S" + player.getModelById(cell.getId());
        } else if (cell.getCellType() == CellType.INVISIBLE_SHIP) {
            if (cell.getIsShot()) return "D" + player.getModelById(cell.getId());
            else return "I" + player.getModelById(cell.getId());
        } else {
            if (cell.getIsShot()) return "MX";
            else return "Mm";
        }
    }

    public static void printRivalBoard(Player player) {
        for (int i = 0; i < 10; i++) {
            System.out.print("|");
            for (int j = 0; j < 10; j++) {
                System.out.print(getRivalBoardValue(player, player.getCell(j, i)));
                System.out.print("|");
            }
            System.out.println("");
        }
    }

    private static String getRivalBoardValue(Player player, Cell cell) {
        if (cell.getCellType() == CellType.EMPTY) {
            if (cell.getIsShot()) return "XX";
            else return "  ";
        } else if (cell.getCellType() == CellType.SHIP) {
            if (player.haveThisShip(cell.getId()) && cell.getIsShot()) return "DX";
            else if (cell.getIsShot()) return "D" + player.getModelById(cell.getId());
            else return "  ";
        } else if (cell.getCellType() == CellType.INVISIBLE_SHIP) {
            if (player.haveThisShip(cell.getId()) && cell.getIsShot()) return "DX";
            else if (cell.getIsShot()) return "D" + player.getModelById(cell.getId());
            else return "  ";
        } else {
            if (cell.getIsShot()) return "MX";
            else return "  ";
        }
    }

    private static boolean isCoordinationWrong(String xString, String yString) {
        return !(xString.matches("^[1-9]$|^10$") &&
                yString.matches("^[1-9]$|^10$"));
    }

    private static boolean isThereShot(String xString, String yString, Player player) {
        int x = Integer.parseInt(xString) - 1;
        int y = Integer.parseInt(yString) - 1;
        Cell cell = player.getCell(x, y);
        return cell.getIsShot();
    }

    private static boolean damageAPieceOfShip(String xString, String yString, Player defender) {
        int x = Integer.parseInt(xString) - 1;
        int y = Integer.parseInt(yString) - 1;
        Cell cell = defender.getCell(x, y);
        if (cell.getCellType() != CellType.SHIP && cell.getCellType() != CellType.INVISIBLE_SHIP) return false;
        cell.setIsShot(true);
        int shipId = cell.getId();
        return defender.haveThisShip(shipId);
    }

    private static boolean destroyAShip(String xString, String yString, Player defender) {
        int x = Integer.parseInt(xString) - 1;
        int y = Integer.parseInt(yString) - 1;
        Cell cell = defender.getCell(x, y);
        if (cell.getCellType() != CellType.SHIP && cell.getCellType() != CellType.INVISIBLE_SHIP) return false;
        int shipId = cell.getId();
        cell.setIsShot(true);
        return !defender.haveThisShip(shipId);
    }

    private static boolean isGameEnded(Player defender) {
        Cell[][] cells = defender.getCells();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i][j].getId() != 0 && !cells[i][j].getIsShot()) return false;
            }
        }
        return true;
    }

    private static boolean shootMine(String xString, String yString, Player attacker, Player defender) {
        int x = Integer.parseInt(xString) - 1;
        int y = Integer.parseInt(yString) - 1;
        Cell cell = defender.getCell(x, y);
        if (cell.getCellType() != CellType.MINE) return false;
        cell.setIsShot(true);

        cell = attacker.getCell(x, y);
        cell.setIsShot(true);
        return true;
    }

    private static boolean isAirplaneOutOfBoard(String xString, String yString, String direction) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);

        if (direction.equals("-h")) {
            return (x + 5 > 11 || y + 2 > 11);
        } else {
            return (x + 2 > 11 || y + 5 > 11);
        }
    }

    private static boolean antiaircraftActed(String xString, String yString, String direction, Player defender) {
        int x = Integer.parseInt(xString) - 1;
        int y = Integer.parseInt(yString) - 1;
        ArrayList<Antiaircraft> antiaircraftArrayList = defender.getAntiaircraftArrayList();

        for (int i = 0; i < antiaircraftArrayList.size(); i++) {
            Antiaircraft eachAntiaircraft = antiaircraftArrayList.get(i);
            if (doesAntiaircraftAct(eachAntiaircraft, x, y, direction)) {
                antiaircraftArrayList.remove(i);
                defender.setAntiaircraftArrayList(antiaircraftArrayList);
                defender.updateAntiaircraft();
                return true;
            }
        }
        return false;
    }

    private static boolean doesAntiaircraftAct(Antiaircraft antiaircraft, int x, int y, String direction) {
        int[][] map = new int[11][11];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = 0;
            }
        }

        int start = antiaircraft.getStart();
        String antiaircraftDirection = antiaircraft.getDirection();

        if (antiaircraftDirection.equals("-h")) {
            for (int i = start; i < start + 3; i++) {
                for (int j = 0; j < 10; j++) {
                    map[i][j] = 1;
                }
            }

        } else {
            for (int i = 0; i < 10; i++) {
                for (int j = start; j < start + 3; j++) {
                    map[i][j] = 1;
                }
            }

        }

        if (direction.equals("-h")) {
            for (int i = y; i < y + 2; i++) {
                for (int j = x; j < x + 5; j++) {
                    if (map[i][j] == 1) return true;
                }
            }
            return false;
        } else {
            for (int i = y; i < y + 5; i++) {
                for (int j = x; j < x + 2; j++) {
                    if (map[i][j] == 1) return true;
                }
            }
            return false;
        }
    }

    private static boolean isScannerOutOfBoard(String xSring, String yString) {
        int x = Integer.parseInt(xSring);
        int y = Integer.parseInt(yString);

        return (x + 3 > 11 || y + 3 > 11);
    }
}
