package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.exit;

public class Shell {
    public final String FONT = "Courier New";
    private final String IMAGE_NAME = ".out.html";
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final int INITIAL_CHARS_IN_ROW = 64;

    private CharSet charSet;
    private Image image;
    private BrightnessImgCharMatcher imgCharMatcher;
    private int minCharsInRow;
    private int maxCharsInRow;
    private int charsInRow;
    private boolean isOutputToConsole = false;


    public Shell(Image img) {
        this.charSet = new CharSet();
        image = img;
        imgCharMatcher = new BrightnessImgCharMatcher(image, FONT);
        minCharsInRow = Math.max(1, img.getWidth() / img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
    }

    public void run() {
        String userInput = getUserInput();
        while (true) {
            String[] input = userInput.split(" ");
            handleUserInput(input);
            userInput = getUserInput();
        }
    }

    private String getUserInput() {
        System.out.print(">>> ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        return userInput;
    }

    private void handleUserInput (String[] input) {
        try {
            if (input.length > 2) {
                throw new IllegalArgumentException();
            }
            String command = input[0];
            switch (command) {
                case ("exit"):
                    exit(0);
                case ("chars"):
                    printAllCharsInSet();
                    break;
                case ("add"):
                    handleAddCommand(input[1]);
                    break;
                case ("remove"):
                    handleRemoveCommand(input[1]);
                    break;
                case ("res"):
                    changeResolution(input[1]);
                    break;
                case ("console"):
                    isOutputToConsole = !isOutputToConsole;
                    break;
                case ("render"):
                    renderImage();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command");
        }

    }

    private void printAllCharsInSet() {
        System.out.println(charSet.toString());
    }

    private void handleAddCommand(String arg) {
        charSet.add(arg);
    }

    private void handleRemoveCommand(String arg) {
        charSet.remove(arg);
    }

    private void changeResolution(String changeType) throws IllegalArgumentException {
        if (!changeType.equals("up") && !changeType.equals("down")) {
            throw new IllegalArgumentException();
        }
        try {
            if (changeType.equals("up")) {
                increaseResolution();
            } else {
                decreaseResolution();
            }
            System.out.println("Width set to " + charsInRow);
        } catch (IllegalArgumentException e) {
            System.out.println("Did not change due to exceeding boundaries");
        }
    }

    private void increaseResolution() throws IllegalArgumentException {
        if (charsInRow*2 <= maxCharsInRow) {
            charsInRow *= 2;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void decreaseResolution() throws IllegalArgumentException {
        if (charsInRow > 1 && charsInRow/2 >= minCharsInRow) {
            charsInRow /= 2;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void renderImage() {
        char[][] charImg = imgCharMatcher.chooseChars(charsInRow, charSet.toArray());
        if (isOutputToConsole) {
            new ConsoleAsciiOutput().output(charImg);
        } else {
            new HtmlAsciiOutput(IMAGE_NAME, FONT).output(charImg);
        }
    }
}
