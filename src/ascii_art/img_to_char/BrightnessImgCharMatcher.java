package ascii_art.img_to_char;

import image.Image;
import image.ImageIterator;

import java.awt.*;
import java.util.HashMap;

public class BrightnessImgCharMatcher {
    private static final int CHAR_PIXELS = 16;

    private String font;
    private Image img;
    private HashMap<Character, Double> letterToBrightnessMap;
    private HashMap<Integer, Double> subImageBrightnessMap;


    public BrightnessImgCharMatcher(Image img, String font) {
        this.font = font;
        this.img = img;
        calcAllCharsBrightness();
    }


    private void calcAllCharsBrightness() {
        this.letterToBrightnessMap = new HashMap<>();
        for (char c = ' '; c <= '~'; c++) {
            calcCharBrightness(c);
        }
        linearScaleBrightness();
    }

    private void calcCharBrightness(char c) {
        boolean[][] charImage = CharRenderer.getImg(c, CHAR_PIXELS, font);
        int truePixelsCount = countTrueValues(charImage);
        double brightness = (double) truePixelsCount / CHAR_PIXELS;
        letterToBrightnessMap.put(c, brightness);
    }

    private int countTrueValues(boolean[][] charImage) {
        int count = 0;
        for (boolean[] row : charImage) {
            for (boolean pixel : row) {
                if (pixel) {
                    count++;
                }
            }
        }
        return count;
    }

    private void linearScaleBrightness() {
        double minBrightness = Double.MAX_VALUE;
        double maxBrightness = Double.MIN_VALUE;
        for (double brightness : letterToBrightnessMap.values()) {
            if (brightness < minBrightness) {
                minBrightness = brightness;
            }
            if (brightness > maxBrightness) {
                maxBrightness = brightness;
            }
        }
        for (char letter : letterToBrightnessMap.keySet()) {
            double brightness = letterToBrightnessMap.get(letter);
            double scaledBrightness = (brightness - minBrightness) / (maxBrightness - minBrightness);
            letterToBrightnessMap.put(letter, scaledBrightness);
        }
    }

    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        int subImageSize = img.getWidth() / numCharsInRow;
        int numCharsInCol = img.getHeight() / subImageSize;
        System.out.println(numCharsInCol + " " + numCharsInRow);
        char[][] asciiImage = new char[numCharsInCol][numCharsInRow];

        createSubImageBrightnessMap(this.img.getWidth() / numCharsInRow);
        int subImageIndex = 0;
        for (int i = 0; i < numCharsInCol; i++) {
            for (int j = 0; j < numCharsInRow; j++) {
                asciiImage[i][j] = fitCharToSubImage(charSet, subImageIndex++);
            }
        }
        return asciiImage;
    }

    private void createSubImageBrightnessMap(int subImageSize) {
        boolean isValidSubImageMapExists = subImageBrightnessMap != null && subImageBrightnessMap.size() == (this.img.getHeight() / subImageSize) * (this.img.getWidth() / subImageSize);
        if (isValidSubImageMapExists) {
            return;
        }
        subImageBrightnessMap = new HashMap<>();
        ImageIterator subImages = new ImageIterator(img, subImageSize);
        int subImageIndex = 0;
        for (Image subImage : subImages) {
            double brightness = calcAvgSubImageBrightness(subImage);
            subImageBrightnessMap.put(subImageIndex, brightness);
            subImageIndex++;
        }

    }

    private double calcAvgSubImageBrightness(Image subImg) {
        double sum = 0;
        for (int i = 0; i < subImg.getHeight(); i++) {
            for (int j = 0; j < subImg.getWidth(); j++) {
                sum += getPixelGreyLevel(subImg.getPixel(i, j));
            }
        }
        int totalPixels = subImg.getHeight() * subImg.getWidth();
        return (sum / totalPixels) / 255;
    }

    private double getPixelGreyLevel(Color pixel) {
        return (pixel.getRed() * 0.2126) + (pixel.getGreen() * 0.7152) + (pixel.getBlue() * 0.0722);
    }

    private char fitCharToSubImage(Character[] charSet, int subImageIndex) {
        double subImageBrightness = subImageBrightnessMap.get(subImageIndex);
        return findClosetBrightnessChar(subImageBrightness, charSet);
    }

    private char findClosetBrightnessChar(double brightnessVal, Character[] charSet) {
        double minDiff = 1;
        char nearestChar = 0;

        for (Character c: charSet){
            double charBrightness = letterToBrightnessMap.get(c);
            double diff = Math.abs(brightnessVal - charBrightness);

            if (diff < minDiff) {
                nearestChar = c;
                minDiff = diff;
            }
        }
        return nearestChar;
    }

}
