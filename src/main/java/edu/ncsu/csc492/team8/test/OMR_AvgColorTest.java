/**
 *
 */
package edu.ncsu.csc492.team8.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc492.team8.ocr.GoogleOCR;
import edu.ncsu.csc492.team8.ocr.OMR_AvgColor;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class OMR_AvgColorTest {
    OMR_AvgColor omr = new OMR_AvgColor();

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp () throws Exception {
    }

    @Test
    public void testOMR_AvgColor_Valid () {

        final BufferedImage testImage = readImage( "test_resources/E-1.png" );
        assertTrue( omr.isChecked( testImage, 55, 130, 20, 20, 550 ) );
        assertFalse( omr.isChecked( testImage, 55, 173, 20, 20, 550 ) );

        final BufferedImage testImage2 = readImage( "test_resources/D-1.png" );
        // assertFalse( omr.isChecked( testImage2, 55, 50, 20, 18, 550 ) ); Not
        // well-recoginzed
        // assertFalse( omr.isChecked( testImage2, 93, 52, 20, 18, 550 ) );
        assertTrue( omr.isChecked( testImage2, 34, 52, 20, 18, 550 ) );
        // assertTrue( omr.isChecked( testImage2, 105, 258, 20, 18, 0 ) );

    }

    private BufferedImage readImage ( final String path ) {
        try {
            return ImageIO.read( new File( path ) );
        }
        catch ( final IOException e ) {
            return null;
        }
    }

    /////////////////////////////////////////////
    private byte[] readImage2 ( final String path ) {
        try {
            return Files.readAllBytes( Paths.get( path ) );
        }
        catch ( final IOException e ) {
            return null;
        }
    }

    @Test
    public void testCheckedList () {
        // use the output file, avoid to use too much google
        // FileInputStream inFile = null;
        // try {
        // inFile = new FileInputStream( "test_resources/json.txt" );
        // }
        // catch ( FileNotFoundException e ) {
        // System.out.println( "Fail to open the text" );
        // e.printStackTrace();
        // }
        //
        // Scanner scanner = new Scanner( inFile );
        // String text = "";
        //
        // try {
        // while ( scanner.hasNextLine() ) {
        // text = scanner.nextLine();
        // }
        // scanner.close();
        //
        // }
        // catch ( NoSuchElementException e ) {
        // // no input
        // }
        //
        // String result1 = text;
        ///////////

        final GoogleOCR cont = new GoogleOCR();

        final String test_image_1_path = "test_resources/E-1.png";
        final byte[] test_image_1 = readImage2( test_image_1_path );
        final String result1 = cont.getFullJSON( test_image_1 );
        final BufferedImage testImage1 = readImage( test_image_1_path );
        System.out.println( "result1=" + omr.checkedList( testImage1, result1 ) );
        System.out.println( "result1 omr =" + omr.OmrFromString( result1 ) );
        // assertTrue(result1.contains( "303756" ));

        final String test_image_2_path = "test_resources/D-1.png";
        final byte[] test_image_2 = readImage2( test_image_1_path );
        final String result2 = cont.getFullJSON( test_image_1 );
        final BufferedImage testImage2 = readImage( test_image_2_path );

        System.out.println( "result2=" + omr.checkedList( testImage2, result2 ) );
        System.out.println( "result1 omr =" + omr.OmrFromString( result2 ) );
    }
}
