/**
 * 
 */
package edu.ncsu.csc492.team8.postprocessing;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class BasicPostprocessor implements HandlesPostprocessing {

     ArrayList<HashMap<Character, Character>> allCharFixMaps = new ArrayList<HashMap<Character, Character>>();
    public BasicPostprocessor() {
        HashMap<Character, Character> c0_fixes = new HashMap<Character, Character>();
        //Initialize Character 0 fixes
        c0_fixes.put( '+', 'T' );
        c0_fixes.put( '4',  'A' );
        c0_fixes.put( '6', 'B' );
        c0_fixes.put('8', 'B' );
           //...
        //Add char 0 fixes to arraylist
        allCharFixMaps.add( c0_fixes );
        
        HashMap<Character, Character> c1_fixes = new HashMap<Character, Character>();
        //Initialize Character 1 fixes
        c1_fixes.put( 'o', '0' );
        c1_fixes.put( 'l',  '1' );
          //...
        allCharFixMaps.add( c1_fixes );
     
        HashMap<Character, Character> c2_fixes = new HashMap<Character, Character>();
        //Initialize Character 2 fixes
            //...
        allCharFixMaps.add( c2_fixes );        
        
        HashMap<Character, Character> c3_fixes = new HashMap<Character, Character>();
        //Initialize Character 3 fixes
            c3_fixes.put(' ', '.');
        allCharFixMaps.add( c3_fixes );    
    }
    /* Make extremely simple fixes to ICD codes (UNDER DEVELOPMENT)
     * 
     */
    @Override
    public String postprocess ( String input ) {
        if (input == null || input.length() == 0)
            return input;
       
        
        char[] car = input.toCharArray();
        int maxLen = (allCharFixMaps.size() <= car.length) ? allCharFixMaps.size() : car.length;
        for(int i = 0; i < maxLen; i++) {
            HashMap<Character, Character> fixes = allCharFixMaps.get( i );
            if (fixes.containsKey( car[i] ))
                car[i] = fixes.get( car[i] );
        }
        return String.valueOf( car ).replaceAll( " ", "" );
    }

}
