/**
 *
 */
package edu.ncsu.csc492.team8.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc492.team8.controllers.OCRController;
import edu.ncsu.csc492.team8.ocr.OCRResult;
import edu.ncsu.csc492.team8.ocr.OMR_AvgColor;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class OCRControllerTest {
    OCRController cont;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp () throws Exception {
        cont = new OCRController();
    }

    /**
     * Test Tesseract on Free Text
     *
     */
    @Test
    public void testTesseractGeneric () {
        final String test_image_1_path = "test_resources/generic_text_1.jpg";
        final byte[] test_image_1 = readImage( test_image_1_path );
        final String tess_result = cont.runOCR( test_image_1, "tesseract" ).ocr_text;

        assertTrue( tess_result.contains( "Writing" ) );
        assertTrue( tess_result.contains( "English" ) );
        assertTrue( tess_result.contains( "book" ) );
    }

    /**
     * Test Tesseract on Empty Input
     *
     */
    @Test
    public void testTesseractEmpty () {
        final byte[] test_image_invalid = null;
        final String tess_result = cont.runOCR( test_image_invalid, "tesseract" ).ocr_text;

        assertEquals( null, tess_result );
    }

    /**
     * Test Google on Free Text
     *
     */
    @Test
    public void testGoogleGeneric () {
        final String test_image_1_path = "test_resources/generic_text_1.jpg";
        final byte[] test_image_1 = readImage( test_image_1_path );
        final String tess_result = cont.runOCR( test_image_1, "google" ).ocr_text;

        assertTrue( tess_result.contains( "Writing" ) );
        assertTrue( tess_result.contains( "English" ) );
        assertTrue( tess_result.contains( "book" ) );
    }

    /**
     * Test ICD Codes, with an input that Google typically returns a nearly
     * sperfect result on
     */
    @Test
    public void testICD_HighAccuracyExpected_1 () {
        final String test_image_1_path = "test_resources/b02.29.PNG";
        final byte[] test_image_1 = readImage( test_image_1_path );
        final String tess_result = cont.runOCR( test_image_1, "icd" ).ocr_text;
        assertTrue( tess_result.contains( "B02.29" ) );
    }

    /**
     * Test ICD Codes, with an input that Tesseract should be used for
     */
    @Test
    public void testICD_LowAccuracyExpected_1 () {
        final String test_image_1_path = "test_resources/h66.002.PNG";
        final byte[] test_image_1 = readImage( test_image_1_path );
        final String tess_result = cont.runOCR( test_image_1, "icd" ).ocr_text;
        assertTrue( tess_result.contains( "H" ) );
    }

    /**
     * Test input in different forms (offset)
     */
    @Test
    public void testCheckBoxOptions () {
        final OCRResult ocrr1 = new OCRResult();
        String option = "omr-E";
        OMR_AvgColor omr = OCRController.checkBox( ocrr1, option );
        assertEquals( omr.formOffset( option ), 6 );

        final OCRResult ocrr2 = new OCRResult();
        option = "omr-D";
        omr = OCRController.checkBox( ocrr2, option );
        assertEquals( omr.formOffset( option ), 7 );

        final OCRResult ocrr3 = new OCRResult();
        option = "omr-C";
        omr = OCRController.checkBox( ocrr3, option );
        assertEquals( omr.formOffset( option ), 16 );

        final OCRResult ocrr4 = new OCRResult();
        option = "omr-B";
        omr = OCRController.checkBox( ocrr4, option );
        assertEquals( omr.formOffset( option ), 12 );

        final OCRResult ocrr5 = new OCRResult();
        option = "omr-A";
        omr = OCRController.checkBox( ocrr5, option );
        assertEquals( omr.formOffset( option ), 6 );

        final OCRResult ocrr6 = new OCRResult();
        option = "omr-1";
        omr = OCRController.checkBox( ocrr6, option );
        assertEquals( ocrr6.ocr_text, "Invalid form" );
    }

    /**
     * Test convertor
     */
    @Test
    public void testConvertorInvalid () {
        final String path = "test_resources/abc.PNG";
        final byte[] test = readImage( path );
        try {
            final BufferedImage bi = OCRController.convertImage( test );
            assertNull( bi );
        }
        catch ( final Exception e ) {
            e.getMessage();
            fail();
        }
    }

    // /**
    // * Test check images
    // */
    // @Test
    // public void testCheckBoxIfChecked () {
    // final String test_image_1_path = "test_resources/D-1.PNG";
    // final byte[] test_image_1 = readImage( test_image_1_path );
    // // assertNotNull( test_image_1 );
    // final String test_result = cont.runOCR( test_image_1, "omr_D-1"
    // ).ocr_text;
    // System.out.println( "check box = " + test_result );
    // // assertTrue( test_result.contains( "3" ) );
    // }

    private byte[] readImage ( final String path ) {
        try {
            return Files.readAllBytes( Paths.get( path ) );
        }
        catch ( final IOException e ) {
            return null;
        }
    }

}
