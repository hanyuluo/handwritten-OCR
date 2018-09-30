/**
 * 
 */
package edu.ncsu.csc492.team8.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc492.team8.postprocessing.BasicPostprocessor;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class BasicPostprocessorTest {
    BasicPostprocessor bp = new BasicPostprocessor();
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp () throws Exception {
    }

    @Test
    public void testBasicPostprocessor () {
        String test_string_1 = "+oA ";
        String bp_result = bp.postprocess( test_string_1 );
        assertEquals("T0A.", bp_result);
    }
    
    @Test
    public void testBP_Empty() {
        String empty = "";
        assertEquals(empty, bp.postprocess( empty ));
        assertNull(bp.postprocess( null ));
    }

}
