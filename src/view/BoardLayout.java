package view;

import controller.BoardLayoutController;
import model.Player;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoardLayout {
    public static void runBoardLayout(User userOne, User userTwo, Scanner scanner) {
        Player playerOne = new Player(userOne);
        Player playerTwo = new Player(userTwo);

        setBoardForPlayer(playerOne, scanner);
        setBoardForPlayer(playerTwo, scanner);
        GameMenu.runGameMenu(playerOne, playerTwo, scanner);
    }

    private static void setBoardForPlayer(Player player, Scanner scanner) {

        while (true) {
            String input = scanner.nextLine();

            if (input.startsWith("put S")) {
                BoardLayoutController.putShip(player, getCommandMatcher(input,
                        "^put S([\\d]+) ([\\d]+),([\\d]+) (-[^\\s])$"));

            } else if (input.startsWith("put-mine")) {
                BoardLayoutController.putMine(player, getCommandMatcher(input,
                        "^put-mine ([\\d]+),([\\d]+)$"));

            } else if (input.startsWith("put-antiaircraft")) {
                BoardLayoutController.putAntiaircraft(player, getCommandMatcher(input,
                        "^put-antiaircraft ([\\d]+) (-[^\\s])$"));

            } else if (input.startsWith("invisible")) {
                BoardLayoutController.putInvisible(player, getCommandMatcher(input,
                        "^invisible ([\\d]+),([\\d]+)$"));

            } else if (input.equals("finish-arranging")) {
                if (BoardLayoutController.canFinishArranging(player)) {
                    System.out.println("turn completed");
                    break;
                } else {
                    System.out.println("you must put all ships on the board");
                }

            } else if (input.equals("show-my-board")) {
                BoardLayoutController.printBoard(player);

            } else if (input.equals("help")) {
                System.out.println("put S[number] [x],[y] [-h|-v]\n" +
                        "put-mine [x],[y]\n" +
                        "put-antiaircraft [s] [-h|-v]\n" +
                        "invisible [x],[y]\n" +
                        "show-my-board\n" +
                        "help\n" +
                        "finish-arranging");

            } else {
                System.out.println("invalid command");

            }
        }
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
