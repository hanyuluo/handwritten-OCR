/**
 *
 */
package edu.ncsu.csc492.team8.ocr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Uses the native Tesseract installation. First, writes the image to a
 * temporary file, runs the system version of tesseract, then returns the
 * result.
 * 
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class TesseractOCR implements HandlesOCR {

    // Values to be adjusted for deployment
    static final String tesseract_executable_location = "C:\\Program Files (x86)\\Tesseract-OCR/tesseract";
    static final String lang_code                     = "eng";
    static final String img_type                      = "jpg";

    static final String char_whitelist                = "tessedit_char_whitelist=ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static final String char_patterns                 = "\\c\\d\\d.\\n\\n\\n\\n\n" + "\\c\\d\\d.\\n\\n\\n\n"
            + "\\c\\d\\d.\\n\\n\n" + "\\c\\d\\d.\\n";

    /*
     * (non-Javadoc)
     * @see edu.ncsu.csc492.team8.ocr.HandlesOCR#runOCR(byte[])
     */
    @Override
    public String runOCR ( final byte[] image, final String mode ) {
        // TODO: Does using a specific filename potentially not allow for
        // concurrency?
        try {
            // Do not accept null or empty input
            if ( image == null || image.length == 0 ) {
                return null;
            }

            // Create temporary file to store the image and output TODO: Remove
            // .jpg
            final File tempImageFile = File.createTempFile( "image", img_type );
            final File tempOutFile = File.createTempFile( "out", null );

            // Write patterns file
            final File patternsFile = File.createTempFile( "patterns", null );
            try ( PrintWriter out = new PrintWriter( patternsFile.getAbsolutePath() ) ) {
                out.print( char_patterns );
            }
            // Write the image
            final FileOutputStream fos = new FileOutputStream( tempImageFile.getAbsolutePath() );
            fos.write( image );
            fos.close();

            Process tesseract;
            if ( mode == "icd" ) {
                tesseract = new ProcessBuilder( tesseract_executable_location, tempImageFile.getAbsolutePath(),
                        tempOutFile.getAbsolutePath(), "-l", lang_code, "--oem", "0", "-c", char_whitelist,
                        "--user-patterns", patternsFile.getAbsolutePath(), "-c", "load_system_dawg=false", "-c",
                        "load_freq_dawg=false" ).start();
            }
            else {
                tesseract = new ProcessBuilder( tesseract_executable_location, tempImageFile.getAbsolutePath(),
                        tempOutFile.getAbsolutePath(), "-l", lang_code ).start();
            }

            tesseract.waitFor();

            return new String( Files.readAllBytes( Paths.get( tempOutFile.getAbsolutePath() + ".txt" ) ),
                    Charset.defaultCharset() );
        }
        catch ( final IOException e ) {

            e.printStackTrace();
            return null;
        }
        catch ( final InterruptedException e ) {
            e.printStackTrace();
            return null;
        }
    }

}
