package image;

import java.util.Iterator;


public class ImageIterator implements Iterable<Image> {
    private final Image img;
    private final int subImageSize;

    public ImageIterator(Image img, int subImageSize) {
        this.img = img;
        this.subImageSize = subImageSize;
    }

    @Override
    public Iterator<Image> iterator() {
        return new Iterator<>() {
            private int top_left_x = 0, top_left_y= 0;

            @Override
            public boolean hasNext() {
                return top_left_y < img.getHeight();
            }

            @Override
            public Image next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                SubImage next = new SubImage(img, top_left_x, top_left_y, subImageSize);
                top_left_x += subImageSize;
                if (top_left_x >= img.getWidth()) { // TODO: check edge case where top_left_y + subImageSize > img.getWidth()
                    top_left_x = 0;
                    top_left_y += subImageSize;
                }
                return next;
            }
        };
    }

}
