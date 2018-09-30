/**
 * 
 */
package edu.ncsu.csc492.team8.ocr;

import java.awt.image.BufferedImage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class OMR_Naive implements HandlesOMR {

    /** Return JSON representing result of "Naive" OMR on input
     * "Naive" OMR, in this case, looks for "C", "O", or "D" to represent
     * an empty checkbox, or other characters to represent filled ones
     */
    @Override
    public String OmrFromString ( String input ) {
        String resultingJSON = "{\"OMRresults\":{";
        String[] pieces = input.split( "\n" );
        for (int i = 0; i < pieces.length; i++) {
            //Skip this line if it's empty
            if (pieces[i] == null || pieces[i].trim().length() == 0 || pieces[i].isEmpty())
                continue;
            
            //If line starts with 'C', 'D', or 'O', assume is not marked
            if (pieces[i].charAt(0) == 'C')
                resultingJSON+= "{\"isMarked\":\"false\", \"option\":\"" + pieces[i].replaceFirst( "C", "") + "\"}";
            else if (pieces[i].charAt(0) == 'D')
                resultingJSON+= "{\"isMarked\":\"false\", \"option\":\"" + pieces[i].replaceFirst( "D", "") + "\"}";
            else if (pieces[i].charAt(0) == 'O')
                resultingJSON+= "{\"isMarked\":\"false\", \"option\":\"" + pieces[i].replaceFirst( "O", "") + "\"}";
            else
                resultingJSON+="{\"isMarked\":\"true\", \"option\":\"" + pieces[i].replaceFirst( pieces[i].charAt( 0 ) + "", "") + "\"}";
            if (i < pieces.length-1)
                resultingJSON+=",";
        }
        return resultingJSON + "}}";
    }

    @Override
    public boolean isChecked ( BufferedImage total, int top_left_x, int top_left_y, int width, int height,
            long threshhold ) {
        return false;
    }
    

}
