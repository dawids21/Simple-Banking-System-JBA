package banking;

public class App {

    private static AppStates state;

    public static void main(String[] args) {
        state = AppStates.MENU;
        System.out.println("Hello World!");
    }

    private enum AppStates {
        MENU, LOGGED
    }
}
