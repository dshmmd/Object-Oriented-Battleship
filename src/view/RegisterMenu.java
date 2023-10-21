package view;

import controller.RegisterController;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterMenu {
    public static void runRegisterMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.startsWith("register")) {
                RegisterController.register(getCommandMatcher(input,
                        "^register ([^\\s]+) ([^\\s]+)$"));

            } else if (input.startsWith("login")) {
                RegisterController.login(getCommandMatcher(input,
                        "^login ([^\\s]+) ([^\\s]+)$"), scanner);

            } else if (input.startsWith("remove")) {
                RegisterController.remove(getCommandMatcher(input,
                        "^remove ([^\\s]+) ([^\\s]+)$"));

            } else if (input.equals("list_users")) {
                ArrayList<String> usernames = RegisterController.getAllUsernames();
                for (String eachUsername : usernames) {
                    System.out.println(eachUsername);
                }

            } else if (input.equals("help")) {
                System.out.println("register [username] [password]\n" +
                        "login [username] [password]\n" +
                        "remove [username] [password]\n" +
                        "list_users\n" +
                        "help\n" +
                        "exit");

            } else if (input.equals("exit")) {
                System.out.println("program ended");
                System.exit(0);

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
