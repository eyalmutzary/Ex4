package image;

import java.awt.*;


class SubImage implements Image {
    private final Color[][] pixelArray;

    public SubImage(Image image,int top_left_x, int top_left_y, int size){
        pixelArray = new Color[size][size];
        fillPixelArray(image,top_left_x,top_left_y,size);
    }

    private void fillPixelArray(Image image,int top_left_x, int top_left_y, int size){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
//                TODO: what if the sub-image is out of bounds?
                pixelArray[i][j] = image.getPixel(top_left_x+j, top_left_y+i);
            }
        }
    }

    @Override
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    @Override
    public int getWidth() {
        return pixelArray[0].length;
    }

    @Override
    public int getHeight() {
        return pixelArray.length;
    }

}
