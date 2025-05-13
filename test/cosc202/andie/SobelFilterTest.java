package cosc202.andie;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class SobelFilterTest {

    private BufferedImage testImage; // A BufferedImage variable for the test image

    @BeforeEach
    public void setUp() {
        // Create a test image (3x3 pixels) for testing
        testImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                testImage.setRGB(x, y, (x + y) % 2 == 0 ? 0xFFFFFF : 0x000000); // Alternating white and black pixels
            }
        }
    }

    @Test
    public void testConstructorWithInvalidDirection() {
        // Test the constructor with an invalid direction
        assertThrows(IllegalArgumentException.class, () -> {
            new SobelFilter(4, true); // Expect an exception for invalid direction
        });
    }

    @Test
    public void testApplyWithNullImage() {
        // Test the apply method with a null image
        SobelFilter filter = new SobelFilter(1, false);
        assertThrows(NullPointerException.class, () -> {
            filter.apply(null); // Expect a NullPointerException
        });
    }

    @Test
    public void testApplyNoOperation() {
        // Test the apply method with direction 0 (no operation)
        SobelFilter filter = new SobelFilter(0, false);
        BufferedImage result = filter.apply(testImage);
        assertTrue(imagesAreEqual(testImage, result)); // Check if the output is the same as input
    }

    @Test
    public void testApplyHorizontalFilter() {
        // Test the apply method with horizontal filter
        SobelFilter filter = new SobelFilter(1, false);
        BufferedImage result = filter.apply(testImage);
        assertNotNull(result); // Ensure the result is not null
        // Additional checks can be done by comparing the pixel values
    }

    @Test
    public void testApplyVerticalFilter() {
        // Test the apply method with vertical filter
        SobelFilter filter = new SobelFilter(2, false);
        BufferedImage result = filter.apply(testImage);
        assertNotNull(result); // Ensure the result is not null
        
    }

    @Test
    public void testApplyCombinedFilter() {
        // Test the apply method with combined filter (horizontal and vertical)
        SobelFilter filter = new SobelFilter(3, false);
        BufferedImage result = filter.apply(testImage);
        assertNotNull(result); // Ensure the result is not null
        
    }

    // Helper method to check if two images are equal
    private boolean imagesAreEqual(BufferedImage imgA, BufferedImage imgB) {
        // Check if the dimensions of the images are the same
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }
        // Compare the pixel values of both images
        for (int y = 0; y < imgA.getHeight(); y++) {
            for (int x = 0; x < imgA.getWidth(); x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true; // Return true if all pixels are the same
    }
}
