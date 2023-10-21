package view;

import controller.ShopMenuController;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenu {
    public static void runShopMenu(User user, Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            if (input.startsWith("buy")) {
                ShopMenuController.buy(getCommandMatcher(input), user);

            } else if (input.equals("show-amount")) {
                System.out.println(user.getMoney());

            } else if (input.equals("help")) {
                System.out.println("buy [product] [number]\n" +
                        "show-amount\n" +
                        "help\n" +
                        "back");

            } else if (input.equals("back")) {
                break;

            } else {
                System.out.println("invalid command");

            }
        }
    }

    private static Matcher getCommandMatcher(String input) {
        Pattern pattern = Pattern.compile("^buy ([^\\s]+) ([0-9]\\d*(\\.\\d+)?)$");
        return pattern.matcher(input);
    }
}
