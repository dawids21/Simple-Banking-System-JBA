package banking;

import java.util.HashMap;
import java.util.Scanner;

public class App {

    private static final String IIN = "400000";

    public static void main(String[] args) {
        var state = AppStates.MENU;
        final var accounts = new HashMap<Card, Account>();
        final var generator = new CardGenerator(IIN);
        final var validator = new CardValidator(accounts.keySet());
        final var input = new Scanner(System.in);
        Account loggedAccount = null;

    private static String getMenuText(AppStates state) {
        String menu;
        switch (state) {

            case MENU:
                menu = "1. Create an account\n2. Log into account\n0. Exit";
                break;
            case LOGGED:
                menu = "1. Balance\n2.Log out\n0. Exit";
                break;
            default:
                menu = "";
        }
        return menu;
    }

    private enum AppStates {
        MENU, LOGGED
    }
}
