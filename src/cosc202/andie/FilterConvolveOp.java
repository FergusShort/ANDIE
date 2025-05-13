
/*
 * This file incorporates code from Oracle, which is licensed under the GNU General Public License version 2
 * with the Classpath Exception.
 *
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA or visit www.oracle.com if you need additional information or have any questions.
 */

 /**
  * Rest of code modified by Liam Bland / Team Octagon
  */
  
 package cosc202.andie;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;



/**
 * 
 * This class contains modified code from the original ConvolveOp/
 * It includes edge detection to stop the black border around the edge of the images.
 * When the kernel hangs over the edge of the image, the closest pixel is taken.
 * 
 */

public class FilterConvolveOp implements BufferedImageOp{
    private Kernel kernel;


    /** 
     * <p>
     * Construct FilterConvolveOp
     * </p>
     * @param kernel
     * @see Kernel
     */
    FilterConvolveOp(Kernel kernel){
        this.kernel = kernel;
    }

    /**
     * Performs a convolution on BufferedImages.  Each component of the
     * source image will be convolved (including the alpha component, if
     * present).
     * If the color model in the source image is not the same as that
     * in the destination image, the pixels will be converted
     * in the destination.  If the destination image is null,
     * a BufferedImage will be created with the source ColorModel.
     * The IllegalArgumentException may be thrown if the source is the
     * same as the destination.
     * @param src the source <code>BufferedImage</code> to filter
     * @param dst the destination <code>BufferedImage</code> for the
     *        filtered <code>src</code>
     * @return the filtered <code>BufferedImage</code>
     * @throws NullPointerException if <code>src</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>src</code> equals
     *         <code>dst</code>
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dst){
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (src == dst) {
            throw new IllegalArgumentException("src image cannot be the "+
                                               "same as the dst image");
        }
        return filter(src, dst, 0, 0, src.getWidth(), src.getHeight());
    }


    /***
     * 
     * @param src Code to filter
     * @param dst Destination
     * @param Xi The X pos at the start
     * @param Yi The Y pos at the start
     * @param Xf The X pos at the end
     * @param Yf The Y pos at the end
     * 
     * @return dst
     * 
     * @throws NullPointerException if {@code src} is null.
     * @throws IllegalArgumentException if {@code src} equals {@code dst}.
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dst, int Xi, int Yi, int Xf, int Yf){
        if(src == null) throw new NullPointerException("src image is null");
        if(src == dst) throw new IllegalArgumentException("src image cannot be the same as the dst image");

        // Get image information
        int height = src.getHeight();
        int width = src.getWidth();        
        
        // Create arrays for the inputs and outputs
        int[] srcArray = new int[height*width];
        int[] dstArray = new int[height*width];

        src.getRGB(0, 0, width, height, srcArray, 0, width);
        convolve(srcArray, dstArray, Xi, Yi, Xf, Yf, width, height);
        dst.setRGB(0, 0, width, height, dstArray, 0, width);
        return dst;
    }

    /**
     * <p>
     * Perform the convolve operation on the image
     * </p>
     * 
     * @param srcArray pixels from source.
     * @param dstArray the destination
     * @param Xi X lower bound on selection (inclusive).
     * @param Yi Y lower bound on selection (inclusive)
     * @param Xf X upper bound on selection (exclusive).
     * @param Yf Y upper bound on selection (exclusive).
     */
    private void convolve(int[] srcArray, int[] dstArray, int Xi, int Yi, int Xf, int Yf, int width, int height){

        float[] matrix = kernel.getKernelData(null);
        int radius = kernel.getWidth() / 2;

        //pixel index
        int imgIdx = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y < Yi || y > Yf || x < Xi || x > Xf) {
                    dstArray[imgIdx] = srcArray[imgIdx];
                    imgIdx++;
                    continue;
                }

                float r = 0, g = 0, b = 0;

                 //matrix index
                int matIdx = 0;

                for (int j = -radius; j <= radius; j++) {
                    for (int i = - radius; i <= radius; i++) {
                        int rgbIdx;

                        //now handle edge cases, going thruogh each side/corner of the image.
                        if(y + j < 0){
                            if(x + i < 0) rgbIdx = imgIdx; 
                            else if(x + i >= width) rgbIdx = width - 1; 
                            else rgbIdx = imgIdx + i; 
                        } else if (y + j >= height) {
                            if(x + i < 0) rgbIdx = imgIdx; 
                            else if(x + i >= width) rgbIdx = width * height - 1; 
                            else rgbIdx = imgIdx + i; 
                        } else if (x + i < 0 || x + i >= width) {
                            rgbIdx = imgIdx + (j * width);
                        } else rgbIdx = imgIdx + i + (j * width); 

                        Color srcColour = new Color(srcArray[rgbIdx]);

                        r += matrix[matIdx] * (srcColour.getRed());
                        g += matrix[matIdx] * (srcColour.getGreen());
                        b += matrix[matIdx] * (srcColour.getBlue());

                        matIdx++;
                    }
                }

                Color dstColor;

                int finalR = (int) Math.round(r);
                int finalG = (int) Math.round(g);
                int finalB = (int) Math.round(b);

                 
                // fix colours if they are outside of 0 - 255      
                if (finalR > 255) {
                    finalR = 255;
                } else if (finalR < 0) finalR = 0;
                if (finalG > 255) {
                    finalG = 255;
                } else if (finalG < 0) finalG = 0;
                if (finalB > 255) {
                    finalB = 255;
                } else if (finalB < 0) finalB = 0;
                
                
                dstColor = new Color(finalR, finalG, finalB);
                dstArray[imgIdx] = dstColor.getRGB();
                imgIdx++;
            }
        }

    }

    /**
     * Creates a zeroed destination image with the correct size and number
     * of bands.  If destCM is null, an appropriate ColorModel will be used.
     * @param src       Source image for the filter operation.
     * @param destCM    ColorModel of the destination.  Can be null.
     * @return a destination <code>BufferedImage</code> with the correct
     *         size and number of bands.
     */
    public BufferedImage createCompatibleDestImage(BufferedImage src,
                                                   ColorModel destCM) {
        BufferedImage image;

        int w = src.getWidth();
        int h = src.getHeight();

        WritableRaster wr = null;

        if (destCM == null) {
            destCM = src.getColorModel();
            // Not much support for ICM
            if (destCM instanceof IndexColorModel) {
                destCM = ColorModel.getRGBdefault();
            } else {
                /* Create destination image as similar to the source
                 *  as it possible...
                 */
                wr = src.getData().createCompatibleWritableRaster(w, h);
            }
        }

        if (wr == null) {
            /* This is the case when destination color model
             * was explicitly specified (and it may be not compatible
             * with source raster structure) or source is indexed image.
             * We should use destination color model to create compatible
             * destination raster here.
             */
            wr = destCM.createCompatibleWritableRaster(w, h);
        }

        image = new BufferedImage (destCM, wr,
                                   destCM.isAlphaPremultiplied(), null);

        return image;
    }

    /**
     * Creates a zeroed destination Raster with the correct size and number
     * of bands, given this source.
     * @param src 
     */
    public WritableRaster createCompatibleDestRaster(Raster src) {
        return src.createCompatibleWritableRaster();
    }

    /**
     * Returns the bounding box of the filtered destination image.  Since
     * this is not a geometric operation, the bounding box does not
     * change.
     */
    public final Rectangle2D getBounds2D(BufferedImage src) {
        return getBounds2D(src.getRaster());
    }

    /**
     * Returns the bounding box of the filtered destination Raster.  Since
     * this is not a geometric operation, the bounding box does not
     * change.
     */
    public final Rectangle2D getBounds2D(Raster src) {
        return src.getBounds();
    }

    /**
     * Returns the location of the destination point given a
     * point in the source.  If dstPt is non-null, it will
     * be used to hold the return value.  Since this is not a geometric
     * operation, the srcPt will equal the dstPt.
     */
    public final Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Point2D.Float();
        }
        dstPt.setLocation(srcPt.getX(), srcPt.getY());

        return dstPt;
    }

    /**
     * Returns the rendering hints for this op.
     */
    public final RenderingHints getRenderingHints() {
        return null;
    }


}   