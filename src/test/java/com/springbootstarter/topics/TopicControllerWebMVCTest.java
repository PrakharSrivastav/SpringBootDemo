package com.springbootstarter.topics;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * WebMVCTest runs unit tests with correct application context.
 * This means that the spring context will boot up before running the tests.
 * MockMVCTest will not start the spring server.
 */
@WebMvcTest(TopicController.class)
public class TopicControllerWebMVCTest {

    @Autowired
    private MockMvc mock;
    @MockBean
    private TopicService service;

    private ArrayList<Topic> topics;

    @BeforeEach
    public void setup() {
        topics = new ArrayList<>(Arrays.asList(
                new Topic("Spring Framework Description", "Spring Framework", "spring"),
                new Topic("Angular Framework Description", "Angular Framework", "angular"),
                new Topic("Laravel Framework Description", "Laravel Framework", "laravel")
        ));
    }

    @Test
    public void TestGetAll() throws Exception {
        when(service.getAllTopics()).thenReturn(topics);

        this.mock.perform(get("/topics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Spring Framework Description")))
                .andExpect(content().string(containsString("Angular Framework Description")))
                .andExpect(content().string(containsString("Laravel Framework Description")))
                .andReturn();

        verify(service, times(1)).getAllTopics();
    }

    @Test
    public void TestOne() throws Exception {
        when(service.getTopic("spring")).thenReturn(topics.stream().filter(e -> e.getId() == "spring").findFirst().get());

        this.mock.perform(get("/topics/spring"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Spring Framework Description")))
                .andReturn();

        verify(service, times(1)).getTopic("spring");
    }

    @Test
    public void DeleteOne() throws Exception {
        doNothing().when(service).deleteTopic("spring");

        this.mock.perform(delete("/topics/spring"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(service, times(1)).deleteTopic("spring");
    }

    @Test
    public void UpdateOne() throws Exception {
        Topic mockTopic = new Topic("SpringBoot Framework Description", "SpringBoot Framework", "spring");
        doNothing().when(service).updateTopic(mockTopic.getId(), mockTopic);


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(mockTopic);

        this.mock.perform(put("/topics/{id}", "spring")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andReturn();

        verify(service, times(1)).updateTopic(mockTopic.getId(), mockTopic);
    }

    @Test
    public void AddOne() throws Exception {
        Topic mockTopic = new Topic("SpringBoot Framework Description", "SpringBoot Framework", "springboot");
        doNothing().when(service).addTopic(mockTopic);


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(mockTopic);

        this.mock.perform(post("/topics/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andReturn();

        verify(service, times(1)).addTopic(mockTopic);
    }

}
