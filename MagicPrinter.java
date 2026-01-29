package GP;

public class MagicPrinter {
    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String PINK = "\u001B[95m"; // Bright magenta as pink

    // Static methods for printing in various colors
    public static void printBlack(String message) {
        System.out.println(BLACK + message + RESET);
    }

    public static void printRed(String message) {
        System.out.println(RED + message + RESET);
    }

    public static void printGreen(String message) {
        System.out.println(GREEN + message + RESET);
    }

    public static void printYellow(String message) {
        System.out.println(YELLOW + message + RESET);
    }

    public static void printBlue(String message) {
        System.out.println(BLUE + message + RESET);
    }

    public static void printPurple(String message) {
        System.out.println(PURPLE + message + RESET);
    }

    public static void printCyan(String message) {
        System.out.println(CYAN + message + RESET);
    }

    public static void printWhite(String message) {
        System.out.println(WHITE + message + RESET);
    }

    public static void printPink(String message) {
        System.out.println(PINK + message + RESET);
    }

    public static void printError(String message) {
        printRed("ERROR: " + message);
    }

    public static void printRainbow(String message) {
        String[] colors = {RED, YELLOW, GREEN, BLUE, PURPLE, CYAN, PINK};
        StringBuilder rainbowMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            String color = colors[i % colors.length];
            rainbowMessage.append(color).append(message.charAt(i));
        }

        rainbowMessage.append(RESET);
        System.out.println(rainbowMessage);
    }

    public static void printBold(String message) {
        System.out.println("\u001B[1m" + message + RESET);
    }

    public static void printUnderlined(String message) {
        System.out.println("\u001B[4m" + message + RESET);
    }
    public static void printItalic(String message) {
        System.out.println("\u001B[3m" + message + RESET);
    }
    public static void printStrikethrough(String message) {
        System.out.println("\u001B[9m" + message + RESET);
    }
}