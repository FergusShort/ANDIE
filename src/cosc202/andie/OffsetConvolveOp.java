package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * A class for performing convolution operations with an optional offset.
 * </p>
  * 
 * <p>
 * This class encapsulates functionality for applying convolution operations 
 * to images while incorporating an offset to balance color values. Convolution 
 * is a mathematical operation commonly used in image processing for tasks such 
 * as blurring, sharpening, edge detection, and more.
 * </p>
 * 
 * <p>
 * Convolution involves applying a kernel, a small matrix of weights, to 
 * pixels in an image. The values of the kernel determine the effect of the 
 * convolution. Additionally, this class allows for offsetting color values 
 * within a balanced range, ensuring that resulting pixel values are within 
 * appropriate bounds (0, 255) and preserving image quality.
 * </p>
 * 
 * <p>
 * The {@code OffsetConvolveOp} class provides methods for creating an 
 * instance with a specified kernel and applying convolution to input images, 
 * producing filtered output images with the convolution effect applied.
 * </p>
 ** 
 * @author Jasmine Smith / Team Octagon
 */
public class OffsetConvolveOp {    
    // midVal This is the middle value in range (0, 255)
    private int midVal = 127;
    private Kernel kernel;
    private boolean useOffset; // Flag to determine whether to use offset or not

    /**
     * <p>
     * Constructs an OffsetConvolveOp object with the specified kernel.
     * </p>
     * 
     * @param kernel The kernel for convolution operation. It should be a 3x3 kernel.
     * @param useOffset Boolean flag to specify whether to use offset or not.
     * @throws NullPointerException If the kernel is null.
     * @throws IllegalArgumentException If the kernel is not a 3x3 kernel.
     */
    public OffsetConvolveOp(Kernel kernel, boolean useOffset){
        if (kernel == null) {
            throw new NullPointerException("Kernel cannot be null.");
        }
        if (kernel.getWidth() != 3 || kernel.getHeight() != 3) {
            throw new IllegalArgumentException("Kernel must be a 3x3 matrix.");
        }
        this.kernel = kernel;
        this.useOffset = useOffset;
    }

    /**
     * <p>
     * Applies the convolution operation to the input image and stores the result in the output image.
     * /p>
     * 
     * <p>
     * This method applies the convolution operation to each pixel in the input image.
     * When processing pixels near the image boundaries, the filter extends beyond
     * the image boundaries by reflecting pixel values at the boundaries.
     * </p>
     * 
     * @param input The input image to be convolved.
     * @param output The output image to store the result of convolution.
     * @return The filtered output image after convolution.
     * @throws NullPointerException if the input or output image is null
     */
    public BufferedImage filter(BufferedImage input, BufferedImage output){
        if (input == null || output == null) {
            throw new NullPointerException("Input and output images cannot be null.");
        }

        int height = input.getHeight();
        int width = input.getWidth();
        float[] kernelData = kernel.getKernelData(null);

        // Check if input image has an alpha channel
        boolean hasAlpha = input.getColorModel().hasAlpha();

        // Apply convolution to each pixel
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int sumR = 0, sumG = 0, sumB = 0; // Sum for each color channel
                int idK = 0; // Index for accessing kernel data
                int alpha = 0; // Alpha channel value
                
                // Kernel Iteration
                for(int i = -1; i <= 1; i++){ // leaving as <= 1 because only using 3 by 3 kernels
                    for(int j = -1; j <= 1; j++){
                        //Reflect pixel values on x-axis (px) and y-axis (py)
                        int px = Math.min(Math.max(x + j, 0), width - 1);
                        int py = Math.min(Math.max(y + i, 0), height - 1);
                        // Get pixel value
                        int rgb = input.getRGB(px, py);
                        // Extract colour channels and apply convolution
                        if (hasAlpha && i == 0 && j == 0) {
                            alpha = (rgb & 0xFF000000) >> 24; // Extract alpha channel
                        }
                        sumR += ((rgb & 0x00FF0000) >> 16) * kernelData[idK];
                        sumG += ((rgb & 0x0000FF00) >> 8) * kernelData[idK];
                        sumB += (rgb & 0x000000FF) * kernelData[idK];
                        idK++;
                        
                    }
                }

                // Optionally apply offset, assign color channels and clipped to [0, 255] range
                if (useOffset) {
                    sumR = Math.min(Math.max(sumR, -midVal), midVal) + midVal;
                    sumG = Math.min(Math.max(sumG, -midVal), midVal) + midVal;
                    sumB = Math.min(Math.max(sumB, -midVal), midVal) + midVal;
                } else {
                    sumR = Math.min(Math.max(sumR, 0), 255);
                    sumG = Math.min(Math.max(sumG, 0), 255);
                    sumB = Math.min(Math.max(sumB, 0), 255);
                }
                // Combine colour channels and set pixel value in output image
                int rgbResult; 
                if (hasAlpha) {
                    rgbResult = (alpha << 24) | (sumR << 16) | (sumG << 8) | sumB; // Include alpha
                } else {
                    rgbResult = (sumR << 16) | (sumG << 8) | sumB; // Without alpha
                }                
                output.setRGB(x, y, rgbResult);
                
            }
        }
        return output;
    }
}