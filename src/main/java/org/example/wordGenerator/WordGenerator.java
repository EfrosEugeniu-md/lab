package org.example.wordGenerator;

import org.apache.poi.common.usermodel.PictureType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class WordGenerator {

    public static void createWordWithLineText(
            String folderPath,
            String fileName,
            String text) throws IOException, InvalidFormatException {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }

        // 1. Create output directory
        Path folder = Path.of(folderPath);
        Files.createDirectories(folder);

        // 2. Create Word file path
        if (!fileName.toLowerCase().endsWith(".docx")) {
            fileName += ".docx";
        }

        Path outputFile = folder.resolve(fileName);

        // 3. Draw image
        BufferedImage temp = new BufferedImage(
                1, 1, BufferedImage.TYPE_INT_RGB);

        Graphics2D tempGraphics = temp.createGraphics();
        tempGraphics.setFont(new Font("Arial", Font.PLAIN, 24));

        int textWidth = tempGraphics.getFontMetrics()
                .stringWidth(text);

        tempGraphics.dispose();

        int imageWidth = Math.max(600, textWidth + 80);
        int imageHeight = 130;

        BufferedImage image = new BufferedImage(
                imageWidth,
                imageHeight,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();

        // Image settings
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth, imageHeight);

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.BLACK);

        // Center text
        FontMetrics metrics = g.getFontMetrics();
        int x = (imageWidth - metrics.stringWidth(text)) / 2;

        // Draw text
        g.drawString(text, x, 75);

        // Draw horizontal line
        g.setColor(new Color(40, 120, 200));
        g.setStroke(new BasicStroke(2));

        g.drawLine(30, 88, imageWidth - 30, 88);

        g.dispose();

        // 4. Convert image to PNG bytes
        ByteArrayOutputStream imageStream =
                new ByteArrayOutputStream();

        ImageIO.write(image, "png", imageStream);

        byte[] imageBytes = imageStream.toByteArray();

        // 5. Create Word document
        try (XWPFDocument document = new XWPFDocument()) {

            XWPFParagraph paragraph =
                    document.createParagraph();

            paragraph.setAlignment(
                    ParagraphAlignment.CENTER);

            XWPFRun run = paragraph.createRun();

            // Image dimensions in Word
            double widthPoints = 450;
            double heightPoints =
                    widthPoints * imageHeight / imageWidth;

            run.addPicture(
                    new ByteArrayInputStream(imageBytes),
                    PictureType.PNG,
                    "line-text.png",
                    Units.toEMU(widthPoints),
                    Units.toEMU(heightPoints)
            );

            // 6. Save Word document
            try (OutputStream output =
                         Files.newOutputStream(outputFile)) {

                document.write(output);
            }
        }

        System.out.println(
                "Word file created: " + outputFile);
    }
}
