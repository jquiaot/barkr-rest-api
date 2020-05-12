package com.example.barkr.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
public class BarkrControllerTests {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnSavedBark() throws Exception {
        Bark incomingBark = new Bark(null, null, "Test Bark", "This is my test Bark.");
        this.mockMvc.perform(
                post("/barks")
                        .content(toJsonString(incomingBark))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creation_ts").exists())
        ;
    }

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        JavaTimeModule jtm = new JavaTimeModule();
        jtm.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DTF));
        mapper.registerModule(jtm);
    }

    private static String toJsonString(Object o) throws Exception {
        return mapper.writeValueAsString(o);
    }

    @Test
    public void shouldReturnStoredBark() throws Exception {
        Bark incomingBark = new Bark(null, null, "Test Bark", "This is my test Bark.");
        MvcResult result = this.mockMvc.perform(
                post("/barks")
                        .content(toJsonString(incomingBark))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creation_ts").exists())
                .andReturn();
        assertNotNull(result);
        Bark storedBark = mapper.readValue(result.getResponse().getContentAsString(), Bark.class);
        assertNotNull(storedBark);
        assertNotNull(storedBark.getId());
        assertNotNull(storedBark.getCreationTs());

        this.mockMvc.perform(
                get("/barks/{barkId}", storedBark.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(storedBark.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creation_ts").value(storedBark.getCreationTs().format(DTF)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(storedBark.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(storedBark.getContent()))
        ;
    }

}