package com.nekodream.ai;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageToAsciiArt {

    private static final String ASCII_CHARS = "@%#*+=-:. ";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java ImageToAsciiArt <input_image_path> <output_file_path>");
            return;
        }

        String inputImagePath = args[0];
        String outputFilePath = args[1];

        try {
            BufferedImage image = ImageIO.read(new File(inputImagePath));
            if (image == null) {
                System.out.println("Error: Image file not found or unable to read.");
                return;
            }

            String asciiArt = convertImageToAscii(image);
            saveAsciiArtToFile(asciiArt, outputFilePath);
            System.out.println("ASCII art saved to " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertImageToAscii(BufferedImage image) {
        StringBuilder asciiArt = new StringBuilder();
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int gray = (int) (0.299 * ((pixel >> 16) & 0xff) + 0.587 * ((pixel >> 8) & 0xff) + 0.114 * (pixel & 0xff));
                int index = gray * (ASCII_CHARS.length() - 1) / 255;
                asciiArt.append(ASCII_CHARS.charAt(index));
            }
            asciiArt.append("\n");
        }

        return asciiArt.toString();
    }

    private static void saveAsciiArtToFile(String asciiArt, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(asciiArt);
        }
    }
}
