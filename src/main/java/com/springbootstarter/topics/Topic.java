package com.springbootstarter.topics;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Topic {
    private String description;
    private String name;
    private String id;
}
