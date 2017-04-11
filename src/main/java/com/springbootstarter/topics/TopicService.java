package com.springbootstarter.topics;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

    private List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("Spring Framework Description", "Spring Framework", "spring"),
            new Topic("Angular Framework Description", "Angular Framework", "angular"),
            new Topic("Laravel Framework Description", "Laravel Framework", "laravel")
    ));

    public List<Topic> getAllTopics() {
        return topics;
    }

    public Topic getTopic(String id) {
        System.out.println("here");
        return topics
                .stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .get();
    }


    public void addTopic(Topic topic){
        topics.add(topic);
    }

    public void updateTopic(String id , Topic topic){
        for (Topic t : topics){
            if(t.getId().equals(id)){
                t.setDescription(topic.getDescription());
                t.setId(topic.getId());
                t.setName(topic.getName());
                return;
            }
        }
    }

    public void deleteTopic(String id){
        topics.removeIf(t -> t.getId().equals(id));
    }
}
