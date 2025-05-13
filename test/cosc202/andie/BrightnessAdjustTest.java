package cosc202.andie;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrightnessAdjustTest {

    @Test
    public void testBrightnessAdjustZeroPercent() {
        BufferedImage input = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        input.setRGB(0, 0, 0xFF000000); // Black pixel
        input.setRGB(0, 1, 0xFFFFFFFF); // White pixel
        input.setRGB(1, 0, 0xFF7F7F7F); // Gray pixel
        input.setRGB(1, 1, 0xFF123456); // Arbitrary color

        BrightnessAdjust brightnessAdjust = new BrightnessAdjust(0);
        BufferedImage result = brightnessAdjust.apply(input);

        assertEquals(0xFF000000, result.getRGB(0, 0)); // Black pixel should remain black
        assertEquals(0xFFFFFFFF, result.getRGB(0, 1)); // White pixel should remain white
        assertEquals(0xFF7F7F7F, result.getRGB(1, 0)); // Gray pixel should remain gray
        assertEquals(0xFF123456, result.getRGB(1, 1)); // Arbitrary color should remain the same
    }

    @Test
    public void testBrightnessAdjustIncrease() {
        BufferedImage input = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        input.setRGB(0, 0, 0xFF000000); // Black pixel
        input.setRGB(0, 1, 0xFFFFFFFF); // White pixel
        input.setRGB(1, 0, 0xFF7F7F7F); // Gray pixel
        input.setRGB(1, 1, 0xFF123456); // Arbitrary color

        BrightnessAdjust brightnessAdjust = new BrightnessAdjust(50); // Increase brightness by 50%
        BufferedImage result = brightnessAdjust.apply(input);

        // Calculate expected values
        int expectedBlack = calculateNewValue(0x00, 50);
        int expectedWhite = calculateNewValue(0xFF, 50);
        int expectedGray = calculateNewValue(0x7F, 50);
        int expectedR = calculateNewValue(0x12, 50);
        int expectedG = calculateNewValue(0x34, 50);
        int expectedB = calculateNewValue(0x56, 50);

        // Combine expected RGB values
        int expectedColor = (0xFF << 24) | (expectedR << 16) | (expectedG << 8) | expectedB;

        assertEquals((0xFF << 24) | (expectedBlack << 16) | (expectedBlack << 8) | expectedBlack, result.getRGB(0, 0)); // Black pixel
        assertEquals((0xFF << 24) | (expectedWhite << 16) | (expectedWhite << 8) | expectedWhite, result.getRGB(0, 1)); // White pixel
        assertEquals((0xFF << 24) | (expectedGray << 16) | (expectedGray << 8) | expectedGray, result.getRGB(1, 0)); // Gray pixel
        assertEquals(expectedColor, result.getRGB(1, 1)); // Arbitrary color
    }

    private int calculateNewValue(int oldValue, int brightnessPercent) {
        return Math.max(0, Math.min(255, (int) ((oldValue - 127.5) + 127.5 * (1.0 + brightnessPercent / 100.0))));
    }

    @Test
    public void testBrightnessAdjustDecrease() {
        BufferedImage input = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        input.setRGB(0, 0, 0xFF000000); // Black pixel
        input.setRGB(0, 1, 0xFFFFFFFF); // White pixel
        input.setRGB(1, 0, 0xFF7F7F7F); // Gray pixel
        input.setRGB(1, 1, 0xFF123456); // Arbitrary color

        BrightnessAdjust brightnessAdjust = new BrightnessAdjust(-50); // Decrease brightness by 50%
        BufferedImage result = brightnessAdjust.apply(input);

        // Calculate expected values
        int expectedBlack = calculateNewValue(0x00, -50);
        int expectedWhite = calculateNewValue(0xFF, -50);
        int expectedGray = calculateNewValue(0x7F, -50);
        int expectedR = calculateNewValue(0x12, -50);
        int expectedG = calculateNewValue(0x34, -50);
        int expectedB = calculateNewValue(0x56, -50);

        // Combine expected RGB values
        int expectedColor = (0xFF << 24) | (expectedR << 16) | (expectedG << 8) | expectedB;

        assertEquals((0xFF << 24) | (expectedBlack << 16) | (expectedBlack << 8) | expectedBlack, result.getRGB(0, 0)); // Black pixel
        assertEquals((0xFF << 24) | (expectedWhite << 16) | (expectedWhite << 8) | expectedWhite, result.getRGB(0, 1)); // White pixel
        assertEquals((0xFF << 24) | (expectedGray << 16) | (expectedGray << 8) | expectedGray, result.getRGB(1, 0)); // Gray pixel
        assertEquals(expectedColor, result.getRGB(1, 1)); // Arbitrary color
    }
}

