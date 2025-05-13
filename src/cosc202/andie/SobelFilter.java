package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Sobel filter for edge detection in images.
 * </p>
 * 
 * <p>
 * The Sobel filter detects edges or boundaries within an image by highlighting intensity gradients. 
 * It is implemented via convolution.
 * </p>
 * 
 * <p>
 * The Sobel filter can be applied in different directions: horizontal, vertical, or combined. 
 * Horizontal filtering highlights vertical edges, vertical filtering highlights horizontal edges, 
 * and combined filtering highlights both vertical and horizontal edges.
 * </p>
 * 
 * @author Jasmine Smith / Team Octagon
 */

public class SobelFilter implements ImageOperation, java.io.Serializable {

     /** The values for the Sobel filter kernels as 9-element arrays */ 
     private static final float[] HORIZONTAL_KERNEL = {-1 / 2f, 0, 1 / 2f, -1, 0, 1, -1 / 2f, 0, 1 / 2f};
     private static final float[] VERTICAL_KERNEL = {-1 / 2f, -1, -1 / 2f, 0, 0, 0, 1 / 2f, 1, 1 / 2f};
     
     /** Direction of sobel filter */ 
     int direction;
     /** Flag to determine whether to use offset or not */
     private boolean useOffset;  


    /**
     * <p>
     * Default constructor.
     * Initializes direction to 0 - no operation.
     * </p>
     */
    public SobelFilter(){
        this.direction = 0;
        this.useOffset = false; //Default to not using offset
    }

    /**
     * <p>
     * Constructor for SobelFilter class with a specified direction.
     * </p>
     * @param direction The direction for applying the Sobel filter:
     *                  0 - no operation, 1 - horizontal filter, 2 - vertical filter, 3 - combined horizontal and vertical filter
     * @param useOffset Boolean flag to specify whether to use offset or not.
     * @throws IllegalArgumentException if the provided direction is not between 0 and 3
     */
    public SobelFilter(int direction, boolean useOffset){
        if (direction < 0 || direction > 3) {
                throw new IllegalArgumentException("Direction must be between 0 and 3.");
            }
            this.direction = direction;
            this.useOffset = useOffset;
        }

    /**
     * <p>
     * Applies the Sobel filter to the input image based on the specified direction.
     * </p>
     * 
     * @param input The input BufferedImage to which the filter will be applied.
     * @return The resulting BufferedImage after applying the Sobel filter.
     * @throws NullPointerException if the input image is null
     */
    public BufferedImage apply(BufferedImage input) {
        if (input == null) {
                throw new NullPointerException("Input image cannot be null.");
        }
           

        // Check the specified direction and apply the corresponding filter
        if (direction == 0){
                return input;
        } else if (direction == 1){
                return applySingle(HORIZONTAL_KERNEL, input);

        } else if (direction == 2){
                return applySingle(VERTICAL_KERNEL, input);

        } else {
                return applyCombined(input);
        }
        
                
    }

    /**
     * <p>
     * Applies the Sobel filter to the input image in both horizontal and vertical directions,
     * and then combines the results.
     * </p>
     * 
     * @param input The input BufferedImage to which the filter will be applied.
     * @return resultImage The resulting BufferedImage after applying the Sobel filter in both directions and combining the results.
     */
    private BufferedImage applyCombined(BufferedImage input){
        // Create kernel objects
        Kernel horizontalKernel = new Kernel(3, 3, HORIZONTAL_KERNEL);
        Kernel verticalKernel = new Kernel(3, 3, VERTICAL_KERNEL);
        // Create convolution operation objects
        OffsetConvolveOp horizontalConvOp = new OffsetConvolveOp(horizontalKernel, useOffset);
        OffsetConvolveOp verticalConvOp = new OffsetConvolveOp(verticalKernel, useOffset);
        // Create output images for horizontal and vertical filtering
        BufferedImage horizontalOutput = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        BufferedImage verticalOutput = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);  

        // Apply convolution operations to input image
        horizontalConvOp.filter(input, horizontalOutput);
        verticalConvOp.filter(input, verticalOutput);

        //Combine the resulting images
        BufferedImage resultImage = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        int width = input.getWidth();
        int height = input.getHeight();
        
        // Calculate magnitude and set combined pixel values
        for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x++){
                        int hPixel = getPixel(input, x, y, HORIZONTAL_KERNEL);
                        int vPixel = getPixel(input, x, y, VERTICAL_KERNEL);
                        int magnitude = (int) Math.sqrt((hPixel * hPixel) + (vPixel * vPixel));
                        
                        // Set alpha to fully opaque so no issues when combining
                        int combinedPixel = (0xFF << 24) | (magnitude << 16) | (magnitude << 8) | magnitude;
                        resultImage.setRGB(x, y, combinedPixel);
                }
        }
        return resultImage;
    }

    /**
     * <p>
     * Applies the Sobel filter to the input image in a single direction.
     * </p>
     * 
     * @param k The kernel representing the direction of the Sobel filter.
     * @param input The input BufferedImage to which the filter will be applied.
     * @return The resulting BufferedImage after applying the Sobel filter in the specified direction.
     */
    private BufferedImage applySingle(float[] k, BufferedImage input){
        // Create kernel object for the specified direction
        Kernel kernel = new Kernel(3, 3, k);
        // Create convolution operation object
        OffsetConvolveOp verticalConvOp = new OffsetConvolveOp(kernel, useOffset);
        // Create output BufferedImage
        BufferedImage output = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        // Apply convolution operation to input image
        verticalConvOp.filter(input, output);
        return output;
    }

    /**
     * <p>
     * Retrieves the pixel value based on the convolution of the input image and the kernel.
     * </p>
     * 
     * @param input The input BufferedImage from which the pixel value will be retrieved.
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @param kernel The kernel representing the convolution operation.
     * @return sum The resulting pixel value after convolution.
     */
    private int getPixel(BufferedImage input, int x, int y, float[] kernel){

        int width = input.getWidth();
        int height = input.getHeight();
        int sum = 0;

        // Perform convolution operation to calculate pixel value at x and y coordinates
        for(int ky = -1; ky <= 1; ky++) {
                for(int kx = -1; kx <= 1; kx++) {
                    int pixelX = Math.min(Math.max(x + kx, 0), width - 1);
                    int pixelY = Math.min(Math.max(y + ky, 0), height - 1);
                    int rgb = input.getRGB(pixelX, pixelY) & 0xFF;
                    sum += kernel[(ky + 1) * 3 + (kx + 1)] * rgb;
                }
        }
    
        return sum;
    }

}
