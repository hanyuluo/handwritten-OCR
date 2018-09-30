package edu.ncsu.csc492.team8.api;

import java.io.IOException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import edu.ncsu.csc492.team8.api.response.ErrorResponse;
import edu.ncsu.csc492.team8.api.response.OKResponse;
import edu.ncsu.csc492.team8.controllers.OCRController;
import edu.ncsu.csc492.team8.ocr.OCRResult;

/**
 * Rest API Controller - Handles the REST Interface
 *
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
@RestController
public class RestAPIController {

    @RequestMapping ( value = "/**", method = RequestMethod.POST )
    public String singleFileUpload ( @RequestParam ( "file" ) final MultipartFile file,
            @RequestParam ( "ocr_option" ) final String ocr_option, final Model model ) throws IOException {

        if ( file.isEmpty() ) {
            return ( new Gson() ).toJson( new ErrorResponse( "400", "Empty Input" ) );
        }

        // Get string results from OCRController
        final OCRResult ocrr = OCRController.runOCR( file.getBytes(), ocr_option );
        if ( ocrr.ocr_text == null ) {
            return ( new Gson() ).toJson( new ErrorResponse( "500", "Error while running OCR" ) );
        }

        // Construct response
        final OKResponse okr = new OKResponse( file.getOriginalFilename(), ocrr.ocr_text, ocrr.omr_json );

        // Return
        return ( new Gson() ).toJson( okr );
    }

}
