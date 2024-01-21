package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
class FileImage implements Image {
    private static final Color DEFAULT_COLOR = Color.WHITE;

    private final Color[][] pixelArray;

    public FileImage(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        int origWidth = im.getWidth(), origHeight = im.getHeight();
        //im.getRGB(x, y)); getter for access to a specific RGB rates

        int newWidth = ceilToPowerOfTwo(origWidth); //TODO: change
        int newHeight = ceilToPowerOfTwo(origHeight); //TODO: change

        pixelArray = new Color[newHeight][newWidth];
        fillImageInArray(im);
    }

    private void fillImageInArray (BufferedImage img) {
        int origWidth = img.getWidth(), origHeight = img.getHeight();
        int newWidth = ceilToPowerOfTwo(origWidth);
        int newHeight = ceilToPowerOfTwo(origHeight);
        int widthPadding = calcPadding(origWidth, newWidth);
        int heightPadding = calcPadding(origHeight, newHeight);
        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {
                if (isPixelInPadding(row, col, widthPadding, heightPadding, origWidth, origHeight)) {
                    pixelArray[row][col] = DEFAULT_COLOR;
                } else {
                    pixelArray[row][col] = new Color(img.getRGB(col - widthPadding, row - heightPadding));
                }
            }
        }
    }

    private int ceilToPowerOfTwo(int num) {
        int power = 1;
        while (power < num) {
            power *= 2;
        }
        return power;
    }



    private int calcPadding(int origSize, int newSize) {
        return (newSize - origSize) / 2;
    }

    private boolean isPixelInPadding(int row, int col, int widthPadding, int heightPadding, int origWidth, int origHeight) {
        return row < heightPadding || row >= heightPadding + origHeight ||
                col < widthPadding || col >= widthPadding + origWidth;
    }

    @Override
    public int getWidth() {
        return pixelArray[0].length;
    }

    @Override
    public int getHeight() {
        return pixelArray.length;
    }

    @Override
    public Color getPixel(int x, int y) {
        return pixelArray[y][x];
    }

}
