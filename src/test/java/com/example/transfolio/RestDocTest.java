package com.example.transfolio;

import com.example.transfolio.common.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class RestDocTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void RestDocsTest() throws Exception {
        this.mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("restdocTest",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))

                );
    }

}
