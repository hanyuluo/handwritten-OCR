package edu.ncsu.csc492.team8.ocr;

/**
 * FormInfo class gives the amont of space around the check box in some specific
 * forms and gives the area of the checkbox for those forms.
 *
 * @author Shruti Patel
 *
 */
public class FormInfo {

    /**
     * this method gives the space amount between last digit of 6digit code and
     * the checkbox.
     *
     * @param bi
     *            String bi is the file name
     * @param space
     *            int space is the total amount of space
     * @return int space
     */
    public int digitToBoxSpace ( final String bi, int space ) {

        if ( bi.equals( "A-1.PNG" ) ) {
            space = 6;
        }
        else if ( bi.equals( "B-1.PNG" ) ) {
            space = 12;
        }
        else if ( bi.equals( "C-1.PNG" ) ) {
            space = 16;
        }
        else if ( bi.equals( "D-1.PNG" ) ) {
            space = 7;
        }
        else if ( bi.equals( "E-1.PNG" ) ) {
            space = 6;
        }

        return space;
    }

    /**
     * this method gives the space amount between the checkbox and the text
     * (description of the tests done on patients).
     *
     * @param bi
     *            String bi is the file name
     * @param space
     *            int space is the total amount of space
     * @return int space
     */
    public int boxToStringSpace ( final String bi, int space ) {

        if ( bi.equals( "A-1.PNG" ) ) {
            space = 9;
        }
        else if ( bi.equals( "B-1.PNG" ) ) {
            space = 11;
        }
        else if ( bi.equals( "C-1.PNG" ) ) {
            space = 18;
        }
        else if ( bi.equals( "D-1.PNG" ) ) {
            space = 5;
        }
        else if ( bi.equals( "E-1.PNG" ) ) {
            space = 3;
        }

        return space;
    }

    /**
     * calculates the area of the checkbox.
     *
     * @param bi
     *            String bi is the file name
     * @param height
     *            int height is the height of the checkbox
     * @param width
     *            int width is the width of the checkbox.
     * @return length * width which is the area of the box.
     */
    public int boxSize ( final String bi, int height, int width ) {
        if ( bi.equals( "A-1.PNG" ) ) {
            height = 15;
            width = 43;
        }
        else if ( bi.equals( "B-1.PNG" ) ) {
            height = 15;
            width = 43;
        }
        else if ( bi.equals( "C-1.PNG" ) ) {
            height = 13;
            width = 23;
        }
        else if ( bi.equals( "D-1.PNG" ) ) {
            height = 19;
            width = 21;
        }
        else if ( bi.equals( "E-1.PNG" ) ) {
            height = 21;
            width = 23;
        }
        return height * width;
    }
}
