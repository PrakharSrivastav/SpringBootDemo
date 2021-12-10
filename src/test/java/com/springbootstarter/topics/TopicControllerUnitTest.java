package com.springbootstarter.topics;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class TopicControllerUnitTest {

    @InjectMocks
    TopicController topicController;
    @Mock
    TopicService topicService;

    private List<Topic> topics;


    @BeforeEach
    public void setup() {
        openMocks(this);
        topics = new ArrayList<>(Arrays.asList(
                new Topic("Spring Framework Description", "Spring Framework", "spring"),
                new Topic("Angular Framework Description", "Angular Framework", "angular"),
                new Topic("Laravel Framework Description", "Laravel Framework", "laravel")
        ));
    }

    @Test
    public void TestAllTopics() {
        when(topicService.getAllTopics()).thenReturn(topics);

        List<Topic> allTopics = topicController.getAllTopics();

        assertEquals(3, topics.size());
        allTopics.stream().forEach(e -> {
            assertTrue(topics.contains(e));
        });

        verify(topicService, Mockito.times(1)).getAllTopics();
    }

    @Test
    public void GetTopic() {
        Topic mockTopic = new Topic("Spring Framework Description", "Spring Framework", "spring");
        when(topicService.getTopic("spring")).thenReturn(mockTopic);

        Topic t = topicController.getTopic("spring");

        assertNotNull(t);
        assertEquals(mockTopic.getDescription(), t.getDescription());
        assertEquals(mockTopic.getName(), t.getName());
        assertEquals(mockTopic.getId(), t.getId());

        verify(topicService, times(1)).getTopic(mockTopic.getId());
    }

    @Test
    public void AddTopic() {
        Topic newTopic = new Topic("Go Description", "Go", "go");
        doNothing().when(topicService).addTopic(newTopic);

        topicController.addTopic(newTopic);

        verify(topicService, times(1)).addTopic(newTopic);
    }

    @Test
    public void UpdateTopic() {
        Topic mockTopic = new Topic("Spring Framework Description", "Spring Framework", "spring");

        doNothing().when(topicService).updateTopic(mockTopic.getId(), mockTopic);

        topicController.updateTopic(mockTopic, mockTopic.getId());

        verify(topicService, times(1)).updateTopic(mockTopic.getId(), mockTopic);
    }

    @Test
    public void DeleteTopic() {
        Topic mockTopic = new Topic("Spring Framework Description", "Spring Framework", "spring");

        doNothing().when(topicService).deleteTopic(mockTopic.getId());

        topicController.deleteTopic(mockTopic.getId());

        verify(topicService, times(1)).deleteTopic(mockTopic.getId());
    }
}
