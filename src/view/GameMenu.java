package view;

import controller.GameMenuController;
import controller.RegisterController;
import model.Cell;
import model.CellType;
import model.Player;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu {
    private static int gameCondition = 0;

    public static void runGameMenu(Player playerOne, Player playerTwo, Scanner scanner) {
        while (true) {
            if (getGameCondition() != 0) break;
            playGame(playerOne, playerTwo, scanner);

            if (getGameCondition() != 0) break;
            playGame(playerTwo, playerOne, scanner);
        }

        if (getGameCondition() == 1) {
            Player winnerPlayer, loserPlayer;
            User winnerUser, loserUser;
            if (playerOne.getWinner()) {
                winnerPlayer = playerOne;
                winnerUser = RegisterController.getUserByUsername(playerOne.getUsername());
                loserPlayer = playerTwo;
                loserUser = RegisterController.getUserByUsername(playerTwo.getUsername());
            } else {
                winnerPlayer = playerTwo;
                winnerUser = RegisterController.getUserByUsername(playerTwo.getUsername());
                loserPlayer = playerOne;
                loserUser = RegisterController.getUserByUsername(playerOne.getUsername());
            }

            winnerUser.increaseWins();
            winnerUser.increaseMoney(50 + calculateScore(loserPlayer));
            winnerUser.increaseScore(3);
            checkout(winnerUser, winnerPlayer);

            loserUser.increaseLoses();
            loserUser.increaseMoney(calculateScore(winnerPlayer));
            checkout(loserUser, loserPlayer);
            setGameCondition(0);

        } else if (getGameCondition() == 2) {
            User userOne = RegisterController.getUserByUsername(playerOne.getUsername());
            User userTwo = RegisterController.getUserByUsername(playerTwo.getUsername());
            userOne.increaseDraws();
            userTwo.increaseDraws();

            userOne.increaseMoney(25 + calculateScore(playerTwo));
            userTwo.increaseMoney(25 + calculateScore(playerOne));

            userOne.increaseScore(1);
            userTwo.increaseScore(1);

            checkout(userOne, playerOne);
            checkout(userTwo, playerTwo);
            setGameCondition(0);

        } else {
            Player winnerPlayer, loserPlayer;
            User winnerUser, loserUser;
            if (playerOne.getWinner()) {
                winnerPlayer = playerOne;
                winnerUser = RegisterController.getUserByUsername(playerOne.getUsername());
                loserPlayer = playerTwo;
                loserUser = RegisterController.getUserByUsername(playerTwo.getUsername());
            } else {
                winnerPlayer = playerTwo;
                winnerUser = RegisterController.getUserByUsername(playerTwo.getUsername());
                loserPlayer = playerOne;
                loserUser = RegisterController.getUserByUsername(playerOne.getUsername());
            }

            winnerUser.increaseWins();
            winnerUser.increaseMoney(calculateScore(loserPlayer));
            winnerUser.increaseScore(2);
            checkout(winnerUser, winnerPlayer);

            loserUser.increaseLoses();
            loserUser.decreaseMoney(50);
            loserUser.decreaseScore(1);
            checkout(loserUser, loserPlayer);
            setGameCondition(0);

        }
    }

    public static void playGame(Player attacker, Player defender, Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("show-turn")) {
                System.out.println(attacker.getUsername() + "'s turn");

            } else if (input.startsWith("bomb")) {
                if (GameMenuController.bomb(attacker,
                        defender,
                        getCommandMatcher(input, "^bomb (\\d+),(\\d+)$")))
                    break;

            } else if (input.startsWith("put-airplane")) {
                if (GameMenuController.putAirplane(attacker,
                        defender,
                        getCommandMatcher(input, "^put-airplane (\\d+),(\\d+) (-[^\\s])$")))
                    break;

            } else if (input.startsWith("scanner")) {
                GameMenuController.scanEnemy(attacker,
                        defender,
                        getCommandMatcher(input, "^scanner (\\d+),(\\d+)$"));

            } else if (input.equals("forfeit")) {
                GameMenuController.forfeit(attacker, defender);
                break;

            } else if (input.equals("show-my-board")) {
                GameMenuController.printMyBoard(attacker);

            } else if (input.equals("show-rival-board")) {
                GameMenuController.printRivalBoard(defender);

            } else if (input.equals("help")) {
                System.out.println("bomb [x],[y]\n" +
                        "put-airplane [x],[y] [-h|-v]\n" +
                        "scanner [x],[y]\n" +
                        "show-turn\n" +
                        "show-my-board\n" +
                        "show-rival-board\n" +
                        "help\n" +
                        "forfeit");

            } else {
                System.out.println("invalid command");

            }
        }
    }

    public static void setGameCondition(int gameCondition) {
        GameMenu.gameCondition = gameCondition;
    }

    public static int getGameCondition() {
        return gameCondition;
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    private static void checkout(User user, Player player) {
        user.setMine(player.getMine());
        user.setAirplane(player.getAirplane());
        user.setAntiaircraft(player.getAntiaircraft());
        user.setScanner(player.getScanner());
        user.setInvisible(player.getInvisible());
    }

    private static int calculateScore(Player opponent) {
        int output = 0;
        Cell[][] cells = opponent.getCells();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i][j].getId() != 0 && cells[i][j].getIsShot()) output++;
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i][j].getCellType() == CellType.MINE && cells[i][j].getIsShot()) output--;
            }
        }
        return output;
    }
}
