package org.example.wordGenerator;

import org.apache.poi.common.usermodel.PictureType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElectricalDiagramGenerator {

    // =====================================================
    // 1. CONFIGURATION
    // =====================================================

    private static final int MARGIN = 60;

    private static final int FEEDER_SPACING = 180;

    private static final int TOP_Y = 40;

    private static final int BUS_Y = 350;

    private static final int BOTTOM_Y = 650;

    private static final int IMAGE_HEIGHT = 720;

    private static final Font LABEL_FONT =
            new Font("Arial", Font.PLAIN, 25);

    private static final Font SMALL_FONT =
            new Font("Arial", Font.PLAIN, 19);

    private static final Color LINE_COLOR = Color.BLACK;

    // =====================================================
    // 2. FEEDER MODEL
    // =====================================================

    public record Feeder(
            String breaker,
            String cable,
            String length
    ) {
        public Feeder {
            if (breaker == null || breaker.isBlank()) {
                throw new IllegalArgumentException(
                        "Breaker designation is required");
            }

            if (cable == null || cable.isBlank()) {
                throw new IllegalArgumentException(
                        "Cable designation is required");
            }

            if (length == null) {
                length = "";
            }
        }
    }

    // =====================================================
    // 3. CREATE WORD DOCUMENT
    // =====================================================

    public static void createWord(
            String folderPath,
            String fileName,
            List<Feeder> feeders
    ) throws IOException {

        if (feeders == null || feeders.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one feeder is required");
        }

        Path folder = Path.of(folderPath);

        Files.createDirectories(folder);

        if (!fileName.toLowerCase().endsWith(".docx")) {
            fileName += ".docx";
        }

        Path outputFile = folder.resolve(fileName);

        BufferedImage image = drawDiagram(feeders);

        ByteArrayOutputStream imageStream =
                new ByteArrayOutputStream();

        ImageIO.write(image, "png", imageStream);

        byte[] imageBytes = imageStream.toByteArray();

        try (XWPFDocument document = new XWPFDocument()) {

            XWPFParagraph paragraph = document.createParagraph();

            paragraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun run = paragraph.createRun();

            // Maximum size of diagram inside Word
            double maxWidthPoints = 680;
            double maxHeightPoints = 470;

            // Preserve aspect ratio
            double scale = Math.min(
                    maxWidthPoints / image.getWidth(),
                    maxHeightPoints / image.getHeight()
            );

            int widthEMU = Units.toEMU(
                    image.getWidth() * scale
            );

            int heightEMU = Units.toEMU(
                    image.getHeight() * scale
            );

            run.addPicture(
                    new ByteArrayInputStream(imageBytes),
                    PictureType.PNG,
                    "electrical-diagram.png",
                    widthEMU,
                    heightEMU
            );

            try (OutputStream output =
                         Files.newOutputStream(outputFile)) {

                document.write(output);
            }
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        System.out.println(
                "Created: " + outputFile.toAbsolutePath());
    }

    // =====================================================
    // 4. DRAW DIAGRAM
    // =====================================================

    public static BufferedImage drawDiagram(
            List<Feeder> feeders
    ) {

        int count = feeders.size();

        int imageWidth =
                MARGIN * 2 + (count - 1) * FEEDER_SPACING + 100;

        BufferedImage image = new BufferedImage(
                imageWidth,
                IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth, IMAGE_HEIGHT);

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        g.setColor(LINE_COLOR);
        g.setStroke(new BasicStroke(2));

        // Draw horizontal bus
        drawBusbar(g, imageWidth);

        // Draw all outgoing feeders
        for (int i = 0; i < count; i++) {

            Feeder feeder = feeders.get(i);

            int x = MARGIN + i * FEEDER_SPACING;

            drawFeeder(g, x, feeder);
        }

        g.dispose();

        return image;
    }

    // =====================================================
    // 5. DRAW HORIZONTAL BUSBAR
    // =====================================================

    private static void drawBusbar(
            Graphics2D g,
            int imageWidth
    ) {

        g.setColor(LINE_COLOR);

        // Dashed horizontal line
        g.setStroke(new BasicStroke(
                2,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10,
                new float[]{18, 16},
                0
        ));

        g.drawLine(
                0,
                BUS_Y,
                imageWidth,
                BUS_Y
        );

        g.setStroke(new BasicStroke(2));
    }

    // =====================================================
    // 6. DRAW ONE FEEDER
    // =====================================================

    private static void drawFeeder(
            Graphics2D g,
            int x,
            Feeder feeder
    ) {

        g.setColor(LINE_COLOR);
        g.setStroke(new BasicStroke(2));

        // ---------------------------------------------
        // Top terminal
        // ---------------------------------------------

        g.drawLine(x, TOP_Y, x, 150);

        g.drawOval(x - 3, TOP_Y - 3, 6, 6);

        // ---------------------------------------------
        // Breaker symbol
        // ---------------------------------------------

        drawBreakerSymbol(g, x, 150);

        // ---------------------------------------------
        // Vertical conductor to busbar
        // ---------------------------------------------

        g.drawLine(
                x,
                190,
                x,
                BUS_Y
        );

        // ---------------------------------------------
        // Vertical breaker label
        // ---------------------------------------------

        drawVerticalText(
                g,
                feeder.breaker(),
                x - 28,
                295,
                LABEL_FONT
        );

        // ---------------------------------------------
        // Connection to cable below busbar
        // ---------------------------------------------

        g.drawLine(
                x,
                BUS_Y,
                x,
                BOTTOM_Y
        );

        // ---------------------------------------------
        // Cable label
        // ---------------------------------------------

        drawVerticalText(
                g,
                feeder.cable(),
                x - 25,
                BOTTOM_Y - 15,
                LABEL_FONT
        );

        // ---------------------------------------------
        // Cable length label
        // ---------------------------------------------

        if (!feeder.length().isBlank()) {

            drawVerticalText(
                    g,
                    feeder.length(),
                    x + 27,
                    BUS_Y + 145,
                    SMALL_FONT
            );
        }

        // ---------------------------------------------
        // Cable marking
        // ---------------------------------------------

        drawCableMarking(g, x, BOTTOM_Y - 20);
    }

    // =====================================================
    // 7. DRAW BREAKER SYMBOL
    // =====================================================

    private static void drawBreakerSymbol(
            Graphics2D g,
            int x,
            int y
    ) {

        // Fixed contact
        g.drawLine(
                x - 8, y,
                x + 3, y - 5
        );

        // Moving contact
        g.drawLine(
                x - 8, y + 2,
                x + 10, y + 36
        );

        // Lower conductor
        g.drawLine(
                x, y + 40,
                x, y + 40
        );

        // Small contact mark
        g.drawLine(
                x + 3, y + 34,
                x + 12, y + 30
        );
    }

    // =====================================================
    // 8. DRAW VERTICAL TEXT
    // =====================================================

    private static void drawVerticalText(
            Graphics2D g,
            String text,
            int x,
            int y,
            Font font
    ) {

        AffineTransform oldTransform =
                g.getTransform();

        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics();

        g.setColor(LINE_COLOR);

        // Rotate text by 90 degrees
        g.rotate(-Math.PI / 2, x, y);

        g.drawString(
                text,
                x - metrics.stringWidth(text),
                y
        );

        g.setTransform(oldTransform);
    }

    // =====================================================
    // 9. DRAW CABLE MARKING
    // =====================================================

    private static void drawCableMarking(
            Graphics2D g,
            int x,
            int y
    ) {

        g.setColor(LINE_COLOR);

        g.setStroke(new BasicStroke(2));

        // Three diagonal cable marks
        for (int i = 0; i < 3; i++) {

            int offset = i * 6;

            g.drawLine(
                    x - 9 + offset,
                    y + 9,
                    x + 9 + offset,
                    y - 9
            );
        }
    }
}
