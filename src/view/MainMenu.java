package view;

import controller.MainMenuController;
import controller.RegisterController;
import model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    public static void runMainMenu(User user, Scanner scanner) {

        while (true) {
            String input = scanner.nextLine();

            if (input.startsWith("new_game")) {
                MainMenuController.newGame(getCommandMatcher(input), user, scanner);

            } else if (input.equals("scoreboard")) {
                ArrayList<User> users = MainMenuController.scoreBoard();
                for (User eachUser : users) {
                    System.out.println(eachUser.getUsername() + " " +
                            eachUser.getScore() + " " +
                            eachUser.getWins() + " " +
                            eachUser.getDraws() + " " +
                            eachUser.getLoses());
                }

            } else if (input.equals("list_users")) {
                ArrayList<String> usernames = RegisterController.getAllUsernames();
                for (String eachUsername : usernames) {
                    System.out.println(eachUsername);
                }

            } else if (input.equals("shop")) {
                ShopMenu.runShopMenu(user, scanner);

            } else if (input.equals("help")) {
                System.out.println("new_game [username]\n" +
                        "scoreboard\n" +
                        "list_users\n" +
                        "shop\n" +
                        "help\n" +
                        "logout");

            } else if (input.equals("logout")) {
                System.out.println("logout successful");
                break;

            } else {
                System.out.println("invalid command");

            }
        }
    }

    private static Matcher getCommandMatcher(String input) {
        Pattern pattern = Pattern.compile("^new_game ([^\\s]+)$");
        return pattern.matcher(input);
    }
}
