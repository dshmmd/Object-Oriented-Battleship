package controller;

import model.User;

import java.util.regex.Matcher;

public class ShopMenuController {
    public static void buy(Matcher matcher, User user) {
        if (!matcher.find()) {
            System.out.println("invalid command");

        } else if (!matcher.group(1).matches("mine|antiaircraft|airplane|scanner|invisible")) {
            System.out.println("there is no product with this name");

        } else if (!matcher.group(2).matches("^(\\d*[1-9]\\d*)$")) {
            System.out.println("invalid number");

        } else if (!haveEnoughMoney(matcher.group(1), Integer.parseInt(matcher.group(2)), user.getMoney())) {
            System.out.println("you don't have enough money");

        } else {
            buyTheProduct(matcher, user);

        }
    }

    private static void buyTheProduct(Matcher matcher, User user) {
        String product = matcher.group(1);
        int howMany = Integer.parseInt(matcher.group(2));

        switch (product) {
            case "mine":
                user.decreaseMoney(howMany);
                user.increaseMine(howMany);
                break;
            case "antiaircraft":
                user.decreaseMoney(howMany * 30);
                user.increaseAntiaircraft(howMany);
                break;
            case "airplane":
                user.decreaseMoney(howMany * 10);
                user.increaseAirplane(howMany);
                break;
            case "scanner":
                user.decreaseMoney(howMany * 9);
                user.increaseScanner(howMany);
                break;
            default:
                user.decreaseMoney(howMany * 20);
                user.increaseInvisible(howMany);
                break;
        }
    }

    private static boolean haveEnoughMoney(String product, int howMany, int money) {
        int finalPayment;

        switch (product) {
            case "mine":
                finalPayment = howMany;
                break;
            case "antiaircraft":
                finalPayment = howMany * 30;
                break;
            case "airplane":
                finalPayment = howMany * 10;
                break;
            case "scanner":
                finalPayment = howMany * 9;
                break;
            default:
                finalPayment = howMany * 20;
                break;
        }

        return finalPayment <= money;
    }
}
