package controller;

import model.User;
import view.MainMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterController {

    private RegisterController() {

    }

    private static RegisterController registerController = null;

    public static RegisterController getInstance() {
        if (registerController == null) {
            return new RegisterController();

        } else {
            return registerController;

        }
    }


    public static ArrayList<User> users = new ArrayList<>();

    public static void register(Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (!matcher.group(1).matches("[\\w]+")) {
            System.out.println("username format is invalid");

        } else if (!matcher.group(2).matches("[\\w]+")) {
            System.out.println("password format is invalid");

        } else if (getUserByUsername(matcher.group(1)) != null) {
            System.out.println("a user exists with this username");

        } else {
            users.add(new User(matcher.group(1), matcher.group(2)));
            System.out.println("register successful");
        }
    }

    public static void login(Matcher matcher, Scanner scanner) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (!matcher.group(1).matches("[\\w]+")) {
            System.out.println("username format is invalid");

        } else if (!matcher.group(2).matches("[\\w]+")) {
            System.out.println("password format is invalid");

        } else if (getUserByUsername(matcher.group(1)) == null) {
            System.out.println("no user exists with this username");

        } else if (isPasswordWrong(matcher.group(1), matcher.group(2))) {
            System.out.println("incorrect password");

        } else {
            System.out.println("login successful");
            MainMenu.runMainMenu(getUserByUsername(matcher.group(1)), scanner);

        }
    }

    public static void remove(Matcher matcher) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (!matcher.group(1).matches("[\\w]+")) {
            System.out.println("username format is invalid");

        } else if (!matcher.group(2).matches("[\\w]+")) {
            System.out.println("password format is invalid");

        } else if (getUserByUsername(matcher.group(1)) == null) {
            System.out.println("no user exists with this username");

        } else if (isPasswordWrong(matcher.group(1), matcher.group(2))) {
            System.out.println("incorrect password");

        } else {
            for (User eachUser : users) {
                if (eachUser.getUsername().equals(matcher.group(1))) {
                    users.remove(eachUser);
                    break;
                }
            }
            System.out.println("removed " + matcher.group(1) + " successfully");
        }
    }

    public static ArrayList<User> getAllUsers() {
        return users;
    }

    public static ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();

        for (User eachUsername : users) {
            usernames.add(eachUsername.getUsername());
        }

        Collections.sort(usernames);
        return usernames;
    }

    public static User getUserByUsername(String username) {
        for (User eachUser : users) {
            if (eachUser.getUsername().equals(username))
                return eachUser;
        }
        return null;
    }

    public static boolean isPasswordWrong(String username, String password) {
        for (User eachUser : users) {
            if (eachUser.getUsername().equals(username) && eachUser.getPassword().equals(password)) {
                return false;
            }
        }
        return true;
    }


}
