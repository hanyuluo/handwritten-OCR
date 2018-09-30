package edu.ncsu.csc492.team8.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import edu.ncsu.csc492.team8.ocr.FormInfo;

/**
 *
 * @author Shruti Patel
 *
 */

public class FormInfoTest {

    FormInfo fi = new FormInfo();

    @Test
    public void testDigitToBox () {

        String file = "A-1.PNG";
        // final int space = 6;
        assertEquals( fi.digitToBoxSpace( file, 6 ), 6 );
        assertNotEquals( fi.digitToBoxSpace( file, 6 ), 5 );
        assertNotEquals( fi.digitToBoxSpace( file, 6 ), -5 );

        file = "B-1.PNG";
        assertEquals( fi.digitToBoxSpace( file, 12 ), 12 );
        assertNotEquals( fi.digitToBoxSpace( file, 12 ), 5 );
        assertNotEquals( fi.digitToBoxSpace( file, 12 ), -5 );

        file = "C-1.PNG";
        assertEquals( fi.digitToBoxSpace( file, 16 ), 16 );
        assertNotEquals( fi.digitToBoxSpace( file, 16 ), 5 );
        assertNotEquals( fi.digitToBoxSpace( file, 16 ), -5 );

        file = "D-1.PNG";
        assertEquals( fi.digitToBoxSpace( file, 7 ), 7 );
        assertNotEquals( fi.digitToBoxSpace( file, 7 ), 5 );
        assertNotEquals( fi.digitToBoxSpace( file, 7 ), -5 );

        file = "E-1.PNG";
        assertEquals( fi.digitToBoxSpace( file, 6 ), 6 );
        assertNotEquals( fi.digitToBoxSpace( file, 6 ), 5 );
        assertNotEquals( fi.digitToBoxSpace( file, 6 ), -5 );

        file = "F-1.PNG"; // if the files are unknown or does not exist
        assertEquals( fi.digitToBoxSpace( file, 0 ), 0 );

    }

    @Test
    public void testBoxToString () {
        String file = "A-1.PNG";
        assertEquals( fi.boxToStringSpace( file, 9 ), 9 );
        assertNotEquals( fi.boxToStringSpace( file, 9 ), 4 );
        assertNotEquals( fi.boxToStringSpace( file, 9 ), -4 );

        file = "B-1.PNG";
        assertEquals( fi.boxToStringSpace( file, 11 ), 11 );
        assertNotEquals( fi.boxToStringSpace( file, 11 ), 4 );
        assertNotEquals( fi.boxToStringSpace( file, 11 ), -4 );

        file = "C-1.PNG";
        assertEquals( fi.boxToStringSpace( file, 18 ), 18 );
        assertNotEquals( fi.boxToStringSpace( file, 18 ), 9 );
        assertNotEquals( fi.boxToStringSpace( file, 18 ), -9 );

        file = "D-1.PNG";
        assertEquals( fi.boxToStringSpace( file, 5 ), 5 );
        assertNotEquals( fi.boxToStringSpace( file, 5 ), 9 );
        assertNotEquals( fi.boxToStringSpace( file, 5 ), -4 );

        file = "E-1.PNG";
        assertEquals( fi.boxToStringSpace( file, 3 ), 3 );
        assertNotEquals( fi.boxToStringSpace( file, 3 ), 9 );
        assertNotEquals( fi.boxToStringSpace( file, 3 ), -9 );

        file = "F-1.PNG"; // if the files are unknown or does not exist
        assertEquals( fi.boxToStringSpace( file, 0 ), 0 );
    }

    @Test
    public void testBoxSize () {
        String file = "A-1.PNG";
        assertEquals( fi.boxSize( file, 15, 43 ), 645 );
        assertNotEquals( fi.boxSize( file, 15, 43 ), 420 );

        file = "B-1.PNG";
        assertEquals( fi.boxSize( file, 15, 43 ), 645 );
        assertNotEquals( fi.boxSize( file, 15, 43 ), 420 );

        file = "C-1.PNG";
        assertEquals( fi.boxSize( file, 13, 23 ), 299 );
        assertNotEquals( fi.boxSize( file, 13, 23 ), 200 );

        file = "D-1.PNG";
        assertEquals( fi.boxSize( file, 19, 21 ), 399 );
        assertNotEquals( fi.boxSize( file, 19, 21 ), 400 );

        file = "E-1.PNG";
        assertEquals( fi.boxSize( file, 21, 23 ), 483 );
        assertNotEquals( fi.boxSize( file, 21, 23 ), 482 );

        file = "F-1.PNG"; // if the files are unknown or does not exist
        assertEquals( fi.boxSize( file, 0, 0 ), 0 );

    }

}
