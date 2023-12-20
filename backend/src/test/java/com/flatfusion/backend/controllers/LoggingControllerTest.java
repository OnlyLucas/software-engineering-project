package com.flatfusion.backend.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(LoggingController.class)
class LoggingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Logger logger;

    //@Test
    //public void testIndexEndpoint_LoggingLevels() throws Exception {
     //
    //    mockMvc.perform(MockMvcRequestBuilders.get("/")
    //                    // Set Authorization header with any dummy value, for example:
    //                    //Encoded in Basic64 jane.doe@example.com:password2
    //                    .header("Authorization", "Basic amFuZS5kb2VAZXhhbXBsZS5jb206cGFzc3dvcmQy"))
    //            .andExpect(MockMvcResultMatchers.status().isOk());

    //}
}