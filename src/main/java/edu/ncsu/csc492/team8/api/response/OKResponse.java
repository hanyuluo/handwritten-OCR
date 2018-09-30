package edu.ncsu.csc492.team8.api.response;

public class OKResponse {
    /**
     * @return the input_filename
     */
    public String getInput_filename () {
        return input_filename;
    }

    /**
     * @param input_filename the input_filename to set
     */
    public void setInput_filename ( String input_filename ) {
        this.input_filename = input_filename;
    }

    /**
     * @return the ocr_text
     */
    public String getOcr_text () {
        return ocr_text;
    }

    /**
     * @param ocr_text the ocr_text to set
     */
    public void setOcr_text ( String ocr_text ) {
        this.ocr_text = ocr_text;
    }

    String input_filename;
    String ocr_text;
    String addl_data;
    
    public OKResponse(String input_filename, String ocr_text, String addl_data) {
        setInput_filename(input_filename);
        setOcr_text(ocr_text);
        this.addl_data = addl_data;
    }
}
