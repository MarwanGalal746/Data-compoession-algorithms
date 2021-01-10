
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class img {
    public static int h;
    public static int w;

    public static int[][] readImage(String path) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
            h = img.getHeight();
            w = img.getWidth();
            int[][] imagePixels = new int[h][w];
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    int pixel = img.getRGB(x, y);
                    int red = (pixel & 0x00ff0000) >> 16;
                    imagePixels[y][x] = red;
                }
            }
            return imagePixels;
        } catch (IOException e) {
            return null;
        }
    }

    public static BufferedImage getBufferedImage(int[][] imagePixels, int w, int h) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int value = 0xff000000 | (imagePixels[y][x] << 16) | (imagePixels[y][x] << 8) | (imagePixels[y][x]);
                image.setRGB(x, y, value);
            }
        }
        return image;
    }

    public static void writeImage(int[][] imagePixels, int w, int h, String outPath) {
        BufferedImage image = getBufferedImage(imagePixels, w, h);
        File ImageFile = new File(outPath);
        try {
            ImageIO.write(image, "jpg", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}