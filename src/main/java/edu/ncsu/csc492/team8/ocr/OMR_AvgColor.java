/**
 *
 */
package edu.ncsu.csc492.team8.ocr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class OMR_AvgColor implements HandlesOMR {

    /*
     * (non-Javadoc)
     * @see edu.ncsu.csc492.team8.ocr.HandlesOMR#OmrFromString(java.lang.String)
     */
    @Override
    public String OmrFromString ( String input ) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)

     * @see edu.ncsu.csc492.team8.ocr.HandlesOMR
     * #isChecked(java.awt.image.BufferedImage, int, int, int, int, long)

     */
    @Override
    public boolean isChecked ( BufferedImage total, int top_left_x, int top_left_y, int width, int height,
            long threshold ) {
        return averageDarkness( total, top_left_x, top_left_y, width, height ) <= threshold;
    }


    public long averageDarkness ( BufferedImage bi, int x0, int y0, int w, int h ) {
        int x1 = x0 + w;
        int y1 = y0 + h;
        long sumr = 0, sumg = 0, sumb = 0;
        for ( int x = x0; x < x1; x++ ) {
            for ( int y = y0; y < y1; y++ ) {
                Color pixel = new Color( bi.getRGB( x, y ) );
                sumr += pixel.getRed();
                sumg += pixel.getGreen();
                sumb += pixel.getBlue();
            }
        }
        int num = w * h;
        long retval = ( sumr + sumg + sumb ) / num;

        return retval;
    }

    final static int               CODE_DIGIT = 6;

    private final ArrayList<Block> listCode   = new ArrayList<Block>();
    private final ArrayList<Block> listItem   = new ArrayList<Block>();
    ArrayList<Block>               listBlock  = new ArrayList<Block>();

    BufferedImage                  image;


    public ArrayList<Block> getListCode () {
        return listCode;
    }

    public ArrayList<Block> getListItem () {
        return listItem;
    }


    public int formOffset ( final String formOption ) {
        return offset = ( new FormInfo() ).digitToBoxSpace( formOption, offset );
    }


    public ArrayList<Block> getListBlock () {
        return listBlock;
}

int offset;


    /**
     * find coordinate of blocks.
     *
     * @param text
     */
    public void findCoordinate ( String text ) {
        // System.out.println( text );
        // just want to parse a substring of full json
        if ( text.indexOf( "\"description\":" ) >= 0 && text.indexOf( "\"fullTextAnnotation\":" ) >= 0 ) {
            text = text.substring( text.indexOf( "\"description\":" ), text.indexOf( "\"fullTextAnnotation\":" ) );
        }
        else {
            throw new IllegalArgumentException( "Google gives bad json format." );
        }


        // each description information will be added to a block
        String[] list = text.split( "\"description\": " );

        // get coordinates
        for ( int i = 1; i < list.length; i++ ) {
            String description = list[i].substring( 0, list[i].indexOf( "," ) );

            // System.out.println( "line" + i + "=" + list[i] );
            final Block b = new Block();

            b.description = description;
            int n = 0;

            for ( int j = 0; j < 4; j++ ) {
                // parse x coordinate for each block
                n = list[i].indexOf( "\"x\": ", n ) + 5;
                String x = list[i].substring( n, list[i].indexOf( ",", n ) );
                // System.out.println( "list=" + list[i] );

                b.x[j] = Integer.parseInt( x );

                // parse y coordinate for each block
                n = list[i].indexOf( "\"y\": ", n ) + 5;
                final String y = list[i].substring( n, list[i].indexOf( "}", n ) ).trim();
                b.y[j] = Integer.parseInt( y );
            }
            listBlock.add( b );

        }
    }

    /**
     * filter the blocks possible well isolated by google run after
     * findCoordinate.
     */
    public void findWellRecognizedRow () {

        for ( int i = 0; i < listBlock.size(); i++ ) {
            if ( Pattern.matches( "\"\\d{6}\"", listBlock.get( i ).description )
                    && Pattern.matches( "\"[A-Za-z]+\"", listBlock.get( i + 1 ).description ) ) {
                // !hard digit

                // System.out.println( "line" + i + "=" + listBlock.get( i ) );
                // System.out.println( "line" + i + "=" + listBlock.get( i + 1 )
                // );

                listCode.add( listBlock.get( i ) );
                listItem.add( listBlock.get( i + 1 ) );

            }
        }

    }

    /**
     * calculate the possible size of checkbox.
     *
     * run after findWellRecognizedRow.
     *
     * @return [width, height, Left Coordinate of checkbox], if return [0,0,0],
     *         means no data is good to judge size.
     */

    public int[] findSize () {
        int size[] = new int[3];
        int width = 0;
        int height = 0;
        int cX0 = 0;
        int n = listCode.size();
        int[] listWidth = new int[n];
        int[] listHeight = new int[n];
        int[] listCX0 = new int[n];

        for ( int i = 0; i < n; i++ ) {
            // System.out.println( listItem.get( i ).x[0] - listCode.get( i
            // ).x[1] );
            // System.out.println( listCode.get( i ).y[2] - listCode.get( i
            // ).y[1] );

            width = listItem.get( i ).x[0] - listCode.get( i ).x[1];
            height = listCode.get( i ).y[2] - listCode.get( i ).y[1];
            cX0 = listCode.get( i ).x[1];
            listWidth[i] = width * 2 / 3; // ? should narrow down
            listHeight[i] = height * 3 / 2; // ? box should higher than text
            listCX0[i] = cX0; // ? x of checkbox should bigger than text

            // System.out.println( listCode.get( i ).description + "=" + width +
            // "," + height + "," + x0 );
        }

        // median, ? maybe use better algorithm in the future
        // best to use frequency of bin
        if ( n > 0 ) {
            Arrays.sort( listWidth );
            size[0] = listWidth[n / 2];
            if ( n >= 3 ) {
                size[0] = ( listWidth[n / 2] + listWidth[n / 2 - 1] + listWidth[n / 2 + 1] ) / 3;
            }

            Arrays.sort( listHeight );
            size[1] = listHeight[n / 2];
            if ( n >= 3 ) {
                size[0] = ( listHeight[n / 2] + listHeight[n / 2 - 1] + listHeight[n / 2 + 1] ) / 3;
            }

            Arrays.sort( listCX0 );
            size[2] = listCX0[n / 2];
        }

        // System.out.println( size[0] + "," + size[1] + "," + size[2] );
        return size;
    }
    /**
     * run after findWellRecognizedRow.
     *
     * @return threshold
     */

    public long calculateThreshold ( BufferedImage image ) {
        long threshold = 999;
        // well-recognized rows highly possible empty checkbox.
        // ? calculate the min averageDarkness of well-recognized rows to know
        // empty checkboxs.
        for ( int i = 0; i < listCode.size(); i++ ) {
            // System.out.printf( "description=%s, %d, %d, %d, %d\n",
            // listCode.get( i ).description,
            // listCode.get( i ).x[1], listCode.get( i ).y[1], listItem.get( i
            // ).x[0] - listCode.get( i ).x[1],
            // listCode.get( i ).y[2] - listCode.get( i ).y[1] );

            threshold = Math.min( threshold,
                    averageDarkness( image, listCode.get( i ).x[1], listCode.get( i ).y[1],
                            listItem.get( i ).x[0] - listCode.get( i ).x[1],
                            listCode.get( i ).y[2] - listCode.get( i ).y[1] ) );
        }

        // System.out.println( "threshold=" + threshold );

        return threshold + 10;// ! hard, if min is checked box, threshold should
                              // higher
    }

    /**
     * if coordinate y calculated with possible size matches the blocks of code
     *
     * @param y,
     *            coordinate y
     * @param precision
     * @return index, -1 if nothing match
     */
    public int match ( final int y, final int precision ) {
        // point 1: left up

        int x0 = listCode.get( 0 ).x[0];
        // have at least one match
        // ? not a efficient search, improve in the future

        for ( int i = 0; i < listBlock.size(); i++ ) {
            // if the coordinate of point 1(left up) of code is in range
            // maybe handle precisions differently in the future
            // ! hard code
            if ( x0 - 3 <= listBlock.get( i ).x[0] && x0 + 3 >= listBlock.get( i ).x[0]
                    && listBlock.get( i ).description.matches( "\"\\d{6}.*\"" ) ) {
                // System.out.println( "i: " + i + "Y0=" + listBlock.get( i
                // ).y[0] + ",y=" + y );

                if ( y - precision <= listBlock.get( i ).y[0] && y + precision >= listBlock.get( i ).y[0] ) {
                    return i;
                }
            }
        }

        return -1;
    }

    public ArrayList<String> checkedList ( BufferedImage image, String text ) {
        // System.out.println( text );
        findCoordinate( text );
        findWellRecognizedRow();
        long threshold = calculateThreshold( image );
        int[] size = findSize();
        int width = size[0];
        int height = size[1];
        int cX0 = size[2];
        if ( width == 0 || height == 0 || cX0 == 0 ) {
            throw new IllegalArgumentException( "There are no good samples to infer a size." );
            // ? maybe set a hard code for range of size
        }

        // locate a reliable checkbox
        int cY0 = listCode.get( 0 ).y[1];
        // find the range of the form
        int cMinY = listBlock.get( 0 ).y[0];
        int cMaxY = listBlock.get( 0 ).y[2];

        ArrayList<String> listChecked = new ArrayList<String>();

        int index = -1;

        // guess coordinate of each row from the reliable checkbox to top
        // cY is the y coordinate of upper line
        for ( int cY = cY0; cY > cMinY; cY -= height ) {
            index = match( cY, height / 2 ); // ?how to decide precision

            if ( index > -1 && isChecked( image, cX0, listBlock.get( index ).y[0], width, height, threshold ) ) {
                listChecked.add( listBlock.get( index ).toString() );
            }
        }

        // guess coordinate of each row from the reliable checkbox to bottom
        for ( int cY = cY0; cY < cMaxY; cY += height ) {
            index = match( cY, height / 2 ); // ?how to decide precision
            // System.out.println( "cY=" + cY );
            if ( index != -1 ) {
                // System.out.println( "index=" + index + "=" + listBlock.get(
                // index ).toString() );

                // System.out.println( listBlock.get( index ).description +
                // "averageDarkness="
                // + averageDarkness( total, cX0, listBlock.get( index ).y[0],
                // width, height ) );

            }

            // if match and checked, add to checked box list
            if ( index > -1 && isChecked( image, cX0, listBlock.get( index ).y[0], width, height, threshold ) ) {
                listChecked.add( listBlock.get( index ).toString() );
            }
        }

        image.flush();

        return listChecked;
    }

    /**
     * imcomplete for hard code
     *
     * @param total
     * @param input
     * @param width
     * @param height
     * @param cX0
     * @param threshold
     * @return
     */
    public ArrayList<String> checkedList ( BufferedImage image, String input, int width, int height, int cX0,
            long threshold ) {
        findCoordinate( input );
        findWellRecognizedRow();
        ArrayList<String> listChecked = new ArrayList<String>();
        image.flush();

        return listChecked;
    }

    public class Block {
        int[]  x = new int[4];
        int[]  y = new int[4];
        /** description in Json */
        String description;

        public int getY ( int index ) {
            return y[index];
        }

        @Override
        public String toString () {
            // may be improved, but I'm tired of looping
            return description + ": {" + x[0] + "," + y[0] + "}; " + "{" + x[1] + "," + y[1] + "}; " + "{" + x[2] + ","
                    + y[2] + "}; " + "{" + x[3] + "," + y[3] + "}";
        }
    }

}
