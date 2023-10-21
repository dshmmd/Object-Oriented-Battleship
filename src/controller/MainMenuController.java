package controller;

import model.User;
import view.BoardLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenuController {
    public static void newGame(Matcher matcher, User user, Scanner scanner) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (!matcher.group(1).matches("[\\w]+")) {
            System.out.println("username format is invalid");

        } else if (matcher.group(1).equals(user.getUsername())) {
            System.out.println("you must choose another player to start a game");

        } else if (RegisterController.getUserByUsername(matcher.group(1)) == null) {
            System.out.println("no user exists with this username");

        } else {
            System.out.println("new game started successfully between " +
                    user.getUsername() +
                    " and " +
                    matcher.group(1));
            BoardLayout.runBoardLayout(user,
                    RegisterController.getUserByUsername(matcher.group(1)),
                    scanner);
        }
    }

    public static ArrayList<User> scoreBoard() {
        ArrayList<User> users = RegisterController.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                if (users.get(i).getUsername().compareToIgnoreCase(users.get(j).getUsername()) > 0) {
                    Collections.swap(users, i, j);
                }
            }
        }
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size() - 1; j++) {
                if (users.get(j).getLoses() > users.get(j + 1).getLoses()) {
                    Collections.swap(users, j, j + 1);
                }
            }
        }
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size() - 1; j++) {
                if (users.get(j).getDraws() < users.get(j + 1).getDraws()) {
                    Collections.swap(users, j, j + 1);
                }
            }
        }
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size() - 1; j++) {
                if (users.get(j).getWins() < users.get(j + 1).getWins()) {
                    Collections.swap(users, j, j + 1);
                }
            }
        }
        return users;
    }
}
