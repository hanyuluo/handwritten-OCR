/*package edu.ncsu.csc492.team8.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.TestCase;

public class APITest extends TestCase{
    
    @Autowired
    private WebApplicationContext context;
    private MockMvc               mvc;

    @Before
    public void setup () {
        context = 
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

    }
    
    @Test
    public void test() throws Exception {
        Path path = Paths.get( "test_resources/good_light.jpg" );
        MockMultipartFile firstFile = new MockMultipartFile("file", "good_light.jpg", "image/jpg", Files.readAllBytes( path ));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/handwritten_ocr_api_spring/test")
                        .file(firstFile)
                        .param("ocr_option", "tesseract"))
                    .andExpect(status().is(200));
    }
}*/
