/**
 *
 */
package edu.ncsu.csc492.team8.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import edu.ncsu.csc492.team8.ocr.GoogleOCR;
import edu.ncsu.csc492.team8.ocr.OCRResult;
import edu.ncsu.csc492.team8.ocr.OMR_AvgColor;
import edu.ncsu.csc492.team8.ocr.TesseractOCR;
import edu.ncsu.csc492.team8.postprocessing.BasicPostprocessor;
import edu.ncsu.csc492.team8.preprocessing.ICDPreprocessor;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class OCRController {
    public static OCRResult runOCR ( final byte[] image, final String ocr_option ) {
        final OCRResult ocrr = new OCRResult();

        // Run OCR
        if ( ocr_option.contains( "tesseract" ) ) {
            ocrr.ocr_text = ( new TesseractOCR() ).runOCR( image, "default" );
        }
        else if ( ocr_option.contains( "google" ) ) {
            ocrr.ocr_text = ( new GoogleOCR() ).runOCR( image, "default" );
        }
        else if ( ocr_option.contains( "icd" ) ) {
            // Try with google vision first, expect perfection
            String google_icd_output = ( new GoogleOCR() ).runOCR( image, "icd" );
            // Postprocess google
            if ( google_icd_output != null ) {
                google_icd_output = ( new BasicPostprocessor() ).postprocess( google_icd_output );
            }

            System.out.println( google_icd_output );
            if ( google_icd_output != null
                    && matches( google_icd_output, "([A-TV-Z][0-9][A-Z0-9](\\.?[A-Z0-9]{0,4})?)" ) ) {
                ocrr.ocr_text = google_icd_output;
            }
            else {
                // If it fails, use tesseract */
                final byte[] adjusted_image = ( new ICDPreprocessor() ).adjust( 1.2f, 20.0f, 2.0f, image );
                ocrr.ocr_text = ( new TesseractOCR() ).runOCR( adjusted_image, "icd" );
                ocrr.ocr_text = ( new BasicPostprocessor() ).postprocess( ocrr.ocr_text );
            }
        }

        // Run OMR
        if ( ocr_option.toLowerCase().contains( "omr" ) ) {
            final String input = ( new GoogleOCR() ).getFullJSON( image );

            if ( ocr_option.toLowerCase().contains( "a" ) ) {
          //      ( new OMR_AvgColor() ).formOffset( "A-1.PNG" );
            }
            else if ( ocr_option.toLowerCase().contains( "b" ) ) {
         //       ( new OMR_AvgColor() ).formOffset( "B-1.PNG" );
            }
            else if ( ocr_option.toLowerCase().contains( "c" ) ) {
         //       ( new OMR_AvgColor() ).formOffset( "C-1.PNG" );
            }
            else if ( ocr_option.toLowerCase().contains( "d" ) ) {
         //       ( new OMR_AvgColor() ).formOffset( "D-1.PNG" );
            }
            else if ( ocr_option.toLowerCase().contains( "e" ) ) {
         //       ( new OMR_AvgColor() ).formOffset( "E-1.PNG" );
            }
            else {
                ocrr.ocr_text = "Invalid form"; // For unknown form
            }
            ( new OMR_AvgColor() ).checkedList( convertImage(image), input );
            ocrr.ocr_text = ( new OMR_AvgColor() ).OmrFromString( input );


            // if ( !ocrr.ocr_text.isEmpty() ) {
            // ocrr.omr_json = ( new OMR_Naive() ).OmrFromString( ocrr.ocr_text
            // );
            // }
        }

        return ocrr;
    }

    public static OMR_AvgColor checkBox ( final OCRResult ocrr, final String option ) {
        final OMR_AvgColor omr = new OMR_AvgColor();
        if ( option.toLowerCase().contains( "a" ) ) {
            omr.formOffset( "A-1.PNG" );
        }
        else if ( option.toLowerCase().contains( "b" ) ) {
            omr.formOffset( "B-1.PNG" );
        }
        else if ( option.toLowerCase().contains( "c" ) ) {
            omr.formOffset( "C-1.PNG" );
        }
        else if ( option.toLowerCase().contains( "d" ) ) {
            omr.formOffset( "D-1.PNG" );
        }
        else if ( option.toLowerCase().contains( "e" ) ) {
            omr.formOffset( "E-1.PNG" );
        }
        else {
            ocrr.ocr_text = "Invalid form"; // For unknown form
        }
        return omr;
    }

    public static BufferedImage convertImage ( final byte[] image ) {
        if ( image.length < 0 ) {
            return null;
        }
        try {
            // convert byte array back to BufferedImage
            final InputStream in = new ByteArrayInputStream( image );
            return ImageIO.read( in );
        }
        catch ( final IOException e ) {
            return null;
        }

    }

    private static boolean matches ( final String input, final String pattern ) {
        final Pattern p = Pattern.compile( pattern );
        final Matcher m = p.matcher( input );
        return m.matches();
    }

}
