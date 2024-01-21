package test;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import image.Image;
import image.ImageIterator;

import java.awt.*;
import java.util.Arrays;

public class main {

    public static void main(String[] args) {
//        String filename = "dino.png";
        String filename = "board.jpeg";
        Image image = Image.fromFile(filename);

        if (image != null) {
            // Use methods from the Image interface
            int width = image.getWidth();
            int height = image.getHeight();
            System.out.println("Image dimensions: " + width + " x " + height);

            BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(image, "Ariel");
            Character[] charSet = {'m','o'};
            var chars = charMatcher.chooseChars(2, charSet);
            var chars1 = charMatcher.chooseChars(2, charSet);
            var chars2 = charMatcher.chooseChars(4, charSet);
            var chars3 = charMatcher.chooseChars(2, charSet);
            var chars4 = charMatcher.chooseChars(2, charSet);

            System.out.println(Arrays.deepToString(chars));




//             Access a specific pixel color
//            Color pixelColor = image.getPixel(2048, 1024);
//            System.out.println("Color of pixel at (0, 0): " + pixelColor);
//
//            // Iterate over all pixels using the pixels() method
//            for (Color pixel : image.pixels()) {
//                // Do something with each pixel color
//                System.out.print("Pixel color: " + pixel);
//            }

            // Iterate over all sub-images using the ImageIterator
//            ImageIterator subImages = new ImageIterator(image, 64);
//            for (Image subImage : subImages) {
//                // Do something with each sub-image
//                System.out.println("Sub-image dimensions: " + subImage.getWidth() + " x " + subImage.getHeight());
//            }
        } else {
            System.out.println("Failed to load the image from the file.");
        }
    }
}
