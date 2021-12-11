package com.springbootstarter.topics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void TestGetAllTopics() {
        HttpEntity<String> entity = new HttpEntity<String>(null, null);

        ResponseEntity<Topic[]> response = restTemplate.exchange(
                createURLWithPort("/topics"), HttpMethod.GET, entity, Topic[].class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(3, response.getBody().length);
        Arrays.stream(response.getBody()).forEach(e -> {
            Assertions.assertTrue(e.getId().contains("spring") ||
                    e.getId().contains("laravel") ||
                    e.getId().contains("angular")
            );
        });
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}