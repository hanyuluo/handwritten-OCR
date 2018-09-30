package edu.ncsu.csc492.team8.preprocessing;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ICDPreprocessor implements HandlesPreprocessing {

    @Override
    public byte[] adjust ( final float value, final float offset, final float scale, final byte[] image ) {

        // Convert byte[] -> BufferedImage
        final BufferedImage bi = bytes_to_bi( image );

        // Adjust rightness and contrast
        final RescaleOp rop = new RescaleOp( value, offset,
                new RenderingHints( RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY ) );
        rop.filter( bi, bi );
        final BufferedImage bii = new BufferedImage( bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_GRAY );

        final Graphics2D graphics = bii.createGraphics();
        graphics.drawImage( bi, 0, 0, null );
        // Adjust size
        // bi = resize(bi, (int) (bi.getWidth() * scale), (int) (bi.getHeight()
        // * scale));
        // Return
        // bi_to_file(bii);
        return bi_to_bytes( bi );
    }

    private BufferedImage bytes_to_bi ( final byte[] image ) {
        final InputStream in = new ByteArrayInputStream( image );
        try {
            final BufferedImage bi = ImageIO.read( in );
            return bi;
        }
        catch ( final IOException e ) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * private byte[] image_to_bytes(Image img) { ByteArrayOutputStream
     * byteArrayOut = new ByteArrayOutputStream(); BufferedImage bimage = new
     * BufferedImage(img.getWidth(null), img.getHeight(null),
     * BufferedImage.TYPE_INT_ARGB); try { ImageIO.write(bimage, "jpg",
     * byteArrayOut); return byteArrayOut.toByteArray(); } catch ( IOException e
     * ) { // TODO Auto-generated catch block e.printStackTrace(); return null;
     * } }
     */

    private byte[] bi_to_bytes ( final BufferedImage bi ) {
        final ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            ImageIO.write( bi, "png", byteArrayOut );
            return byteArrayOut.toByteArray();
        }
        catch ( final IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private void bi_to_file ( final BufferedImage bi ) {
        final File outputfile = new File( "/Users/brent/image.jpg" );
        try {
            ImageIO.write( bi, "png", outputfile );
        }
        catch ( final IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
