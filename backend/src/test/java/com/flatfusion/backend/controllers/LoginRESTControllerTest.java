package com.flatfusion.backend.controllers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(LoginRESTControllerTest.class)
public class LoginRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Logger logger;

    private String loginUrl = "/v1/login";

    @Test
    void getByEmail_ReturnsUnauthorized() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(loginUrl)
                        // Set Authorization header with any dummy value, for example:
                        //Encoded in Basic64 jane.doe@example.com:wrongpassword
                        .header("Authorization", "Basic amFuZS5kb2VAZXhhbXBsZS5jb206d3JvbmdwYXNzd29yZA===="))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
