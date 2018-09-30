/**
 *
 */
package edu.ncsu.csc492.team8.ocr;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class GoogleOCR implements HandlesOCR {
    private static final String TARGET_URL = "https://vision.googleapis.com/v1/images:annotate?key=";
    private static final String API_KEY    = "AIzaSyClzYDPl4xWeQx_KI5LXdTakH2ffSotB1Y";

    @Override
    public String runOCR ( byte[] image, String mode ) {
        String fullJSON = getFullJSON( image );

        // Process, pull out the data we actually need
        int text_tag_index = fullJSON.lastIndexOf( "\"text\"" );

        if ( text_tag_index == -1 )
            return null;

        int end_index = fullJSON.lastIndexOf( "\\n" );

        return fullJSON.substring( text_tag_index + "\"text\": \"".length(), end_index );

    }


    public String getFullJSON ( byte[] image ) {
        try {
            URL serverUrl = new URL( TARGET_URL + API_KEY );
            URLConnection urlConnection = serverUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setRequestMethod( "POST" );
            httpConnection.setRequestProperty( "Content-Type", "application/json" );
            httpConnection.setDoOutput( true );
            BufferedWriter httpRequestBodyWriter = new BufferedWriter(
                    new OutputStreamWriter( httpConnection.getOutputStream() ) );
            httpRequestBodyWriter.write( "{\n" + "  \"requests\": [\n" + "    {\n" + "      \"image\": {\n"
                    + "        \"content\": \"" + Base64.encodeBase64String( image ) + "\"\n" + "      },\n"
                    + "      \"features\": [\n" + "        {\n" + "          \"type\": \"TEXT_DETECTION\"\n"
                    + "        }\n" + "      ]\n" + "    }\n" + "  ]\n" + "}" );
            httpRequestBodyWriter.close();
            try {
            }

            catch ( Exception ioe ) {
                Scanner errorScanner = new Scanner( httpConnection.getErrorStream() );

                while ( errorScanner.hasNext() ) {
                    System.out.println( errorScanner.nextLine() );
                }
                errorScanner.close();
                return "Error";

            }
            if ( httpConnection.getInputStream() == null ) {
                System.out.println( "No stream" );
            }


            Scanner httpResponseScanner = new Scanner( httpConnection.getInputStream() );
            String resp = "";
            while ( httpResponseScanner.hasNext() ) {
                String line = httpResponseScanner.nextLine();

                resp += line;
                // System.out.println(line); // alternatively, print the line of
                // response
            }
            httpResponseScanner.close();
            return resp;
        }

        catch ( Exception ex ) {

            ex.printStackTrace();
            return null;
        }

    }

}
