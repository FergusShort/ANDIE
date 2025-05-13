package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply an emboss filter for enhancing edges in images.
 * </p>
 * 
 * <p>
 * The Emboss filter applies a convolution kernel to the image, emphasizing
 * edges and creating a 3D effect.
 * </p>
 * 
 * <p>
 * The direction of the emboss effect can be specified using constants defined
 * in this class:
 * NONE, EMBOSS_LEFT, EMBOSS_TOPLEFT, EMBOSS_TOP, EMBOSS_TOPRIGHT, EMBOSS_RIGHT,
 * EMBOSS_BOTTOMRIGHT, EMBOSS_BOTTOM, EMBOSS_BOTTOMLEFT.
 * </p>
 * 
 * <p>
 * The convolution kernel used for embossing is a 3x3 matrix applied to the
 * image.
 * </p>
 * 
 * @author Jasmine Smith / Team Octagon
 */

public class EmbossFilter implements ImageOperation, java.io.Serializable {
    /** Constants for specifying emboss direction */
    public static final int NONE = 0, EMBOSS_LEFT = 1, EMBOSS_TOPLEFT = 2, EMBOSS_TOP = 3, EMBOSS_TOPRIGHT = 4,
            EMBOSS_RIGHT = 5, EMBOSS_BOTTOMRIGHT = 6, EMBOSS_BOTTOM = 7, EMBOSS_BOTTOMLEFT = 8;

    /** The direction to select */
    private int direction;

    /** Flag to determine whether to use offset or not */
    private boolean useOffset;

    /** The values for the kernel as a 9-element array */
    private float[] left = { 0, 0, 0, +1.0f, 0, -1.0f, 0, 0, 0 };
    /** The values for the kernel as a 9-element array */
    private float[] topLeft = { +1.0f, 0, 0, 0, 0, 0, 0, 0, -1.0f };
    /** The values for the kernel as a 9-element array */
    private float[] top = { 0, +1.0f, 0, 0, 0, 0, 0, -1.0f, 0 };
    /** The values for the kernel as a 9-element array */
    private float[] topRight = { 0, 0, +1.0f, 0, 0, 0, -1.0f, 0, 0 };
    /** The values for the kernel as a 9-element array */
    private float[] right = { 0, 0, 0, -1.0f, 0, +1.0f, 0, 0, 0 };
    /** The values for the kernel as a 9-element array */
    private float[] bottomRight = { -1.0f, 0, 0, 0, 0, 0, 0, 0, +1.0f };
    /** The values for the kernel as a 9-element array */
    private float[] bottom = { 0, -1.0f, 0, 0, 0, 0, 0, +1.0f, 0 };
    /** The values for the kernel as a 9-element array */
    private float[] bottomLeft = { 0, 0, -1.0f, 0, 0, 0, +1, 0, 0 };

    /**
     * <p>
     * Default constructor for EmbossFilter class.
     * Initializes direction to NONE - no operation.
     * </p>
     */
    public EmbossFilter() {
        this.direction = 0;
        this.useOffset = false; // Default to not using offset
    }

    /**
     * <p>
     * Constructor for EmbossFilter class with a specified direction.
     * </p>
     * 
     * @param direction The direction for applying the emboss filter.
     * @param useOffset Boolean flag to specify whether to use offset or not.
     * @throws IllegalArgumentException if the provided direction is not between 0
     *                                  and 8
     */
    public EmbossFilter(int direction, boolean useOffset) {
        if (direction < 0 || direction > 8) {
            throw new IllegalArgumentException("Direction must be between 0 and 3.");
        }
        this.direction = direction;
        this.useOffset = useOffset;
    }

    /**
     * <p>
     * Applies the emboss filter to the input image based on the specified
     * direction.
     * </p>
     * 
     * @param input The input BufferedImage to which the filter will be applied.
     * @return output The resulting BufferedImage after applying the emboss filter.
     * @throws NullPointerException if the input image is null
     */
    public BufferedImage apply(BufferedImage input) {
        /** If input is null, stop running and return exception message */
        if (input == null) {
            throw new NullPointerException("Input image cannot be null.");
        }

        /** If direction is 0 (NONE), do nothing and return the input image unchanged */
        if (direction == 0) {
            return input;
        }

        Kernel kernel;
        switch (direction) {
            case EMBOSS_LEFT:
                kernel = new Kernel(3, 3, left);
                break;
            case EMBOSS_TOPLEFT:
                kernel = new Kernel(3, 3, topLeft);
                break;
            case EMBOSS_TOP:
                kernel = new Kernel(3, 3, top);
                break;
            case EMBOSS_TOPRIGHT:
                kernel = new Kernel(3, 3, topRight);
                break;
            case EMBOSS_RIGHT:
                kernel = new Kernel(3, 3, right);
                break;
            case EMBOSS_BOTTOMRIGHT:
                kernel = new Kernel(3, 3, bottomRight);
                break;
            case EMBOSS_BOTTOM:
                kernel = new Kernel(3, 3, bottom);
                break;
            case EMBOSS_BOTTOMLEFT:
                kernel = new Kernel(3, 3, bottomLeft);
                break;
            default:
                throw new IllegalArgumentException("Unknown filter type");

        }

        OffsetConvolveOp convOp = new OffsetConvolveOp(kernel, useOffset);
        BufferedImage output = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);
        return output;
    }

}
