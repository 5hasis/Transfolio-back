package com.example.transfolio.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    /*
    @Test
    @DisplayName("아이디 중복 체크")
    public void DuplicationIDTest() throws Exception{
        mockMvc.perform(get("/login/duplidation/id"))
                .andExpect(status().isOk())
                .andExpect()
    }

    */


}
