package arc.packer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import arc.files.Fi;
import arc.graphics.g2d.TextureAtlas;

/**
 *
 */
public class PackerMain {

    static public void main(String[] args) throws Exception{
        new PackerMain();
    }


    TextureUnpacker unpacker = new TextureUnpacker();
    String inFoled = "";
    String outFoled = "";

    public PackerMain() {
        Fi inList = Fi.get("D:\\Develop\\workspace\\libgdx\\zones\\Public\\DiabloTown\\SanGuoTD\\core\\assets-raw\\sprites\\qqtxui\\NinePach\\in\\proce1");
        for (Fi foled : inList.list()) {
            Fi[] foledList = foled.list();
            combineImage(foledList);
            BufferedImage bufferedImage = extractNinePatch(image, region);
            createImageFile(bufferedImage, Fi.get("D:\\Develop\\workspace\\libgdx\\zones\\Public\\DiabloTown\\SanGuoTD\\core\\assets-raw\\sprites\\qqtxui\\NinePach\\out\\" + foled.name() + ".png").file());
            System.out.println();
        }
    }

    TextureAtlas.TextureAtlasData.Region region;
    BufferedImage image;
    private void combineImage(Fi[] list) {
        BufferedImage[] images = new BufferedImage[9];
        for (int i = 0, k = 0; i < 9; i++) {
            if (i == 4 && list.length == 8) continue;
            images[i] = loadImage(list[k++].file());
        }
        int widht = images[0].getWidth() + images[1].getWidth() + images[2].getWidth() + NINEPATCH_PADDING * 2;
        int height = images[0].getHeight() + images[3].getHeight() + images[6].getHeight() + NINEPATCH_PADDING * 2;

        BufferedImage drawImage = createImage(widht, height);
        Graphics2D g = drawImage.createGraphics();
        int sx[] = new int[3];
        int sy[] = new int[3];
        sx[0] = 0;
        sy[0] = 0;
        sx[1] = images[8] == null ? images[5] == null ? images[2] == null ? 0 : images[2].getWidth() : images[5].getWidth() : images[8].getWidth();
        sy[1] = images[8] == null ? images[7] == null ? images[6] == null ? 0 : images[6].getHeight() : images[7].getHeight() : images[8].getHeight();
        sx[2] = images[7] == null ? images[4] == null ? images[1] == null ? 0 : images[1].getWidth() : images[4].getWidth() : images[7].getWidth();
        sy[2] = images[5] == null ? images[4] == null ? images[3] == null ? 0 : images[3].getHeight() : images[4].getHeight() : images[5].getHeight();
        sx[2] += sx[1];
        sy[2] += sy[1];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int index = 9 - (y * 3 + x) -1;
                if (images[index] == null)  continue;

                g.drawImage(images[index], sx[x] + NINEPATCH_PADDING, sy[y] + NINEPATCH_PADDING,
                        sx[x] + NINEPATCH_PADDING + images[index].getWidth(),  sy[y] + NINEPATCH_PADDING + images[index].getHeight(),
                        0, 0,
                        images[index].getWidth(), images[index].getHeight(), null);
            }
        }
        g.dispose();

        image = drawImage;
        region = new TextureAtlas.TextureAtlasData.Region();
        region.width = image.getWidth();
        region.height = image.getHeight();
        region.splits = new int[4];
        region.splits[0] = sx[1];
        region.splits[1] = image.getWidth() - sx[2];
        region.splits[2] = sy[1];
        region.splits[3] = image.getHeight() - sy[2];
    }

    private BufferedImage extractNinePatch(BufferedImage page, TextureAtlas.TextureAtlasData.Region region){
        BufferedImage splitImage = page;
        java.awt.Graphics2D g2 = splitImage.createGraphics();
        g2.setColor(java.awt.Color.black);

        // Draw the four lines to save the ninepatch's padding and splits
        int startX = region.splits[0] + NINEPATCH_PADDING;
        int endX = region.width - region.splits[1] + NINEPATCH_PADDING - 1;
        int startY = region.splits[2] + NINEPATCH_PADDING;
        int endY = region.height - region.splits[3] + NINEPATCH_PADDING - 1;
        if(endX >= startX) g2.drawLine(startX, 0, endX, 0);
        if(endY >= startY) g2.drawLine(0, startY, 0, endY);
        if(region.pads != null){
            int padStartX = region.pads[0] + NINEPATCH_PADDING;
            int padEndX = region.width - region.pads[1] + NINEPATCH_PADDING - 1;
            int padStartY = region.pads[2] + NINEPATCH_PADDING;
            int padEndY = region.height - region.pads[3] + NINEPATCH_PADDING - 1;
            g2.drawLine(padStartX, splitImage.getHeight() - 1, padEndX, splitImage.getHeight() - 1);
            g2.drawLine(splitImage.getWidth() - 1, padStartY, splitImage.getWidth() - 1, padEndY);
        }
        g2.dispose();

        return splitImage;
    }

    public static boolean createImageFile(BufferedImage image, File createFile, String format) {
        try {
            ImageIO.write(image, format, createFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createImageFile(BufferedImage image, File createFile) {
        return createImageFile(image, createFile, "png");
    }

    public static BufferedImage loadImage (File file) {
        BufferedImage image;
//        File file = new File(filepath);
        try {
            image = ImageIO.read(file);
        } catch (IOException ex) {
            throw new RuntimeException("Error reading image: " + file, ex);
        }
        if (image == null) throw new RuntimeException("Unable to read image: " + file);

        return image;
    }

    public static BufferedImage createImage(int width, int height, int format) {
        BufferedImage newImage = new BufferedImage(width, height, format);
        return newImage;
    }

    public static BufferedImage createImage(int width, int height) {
        return createImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    }


    private static final int NINEPATCH_PADDING = 1;
}
