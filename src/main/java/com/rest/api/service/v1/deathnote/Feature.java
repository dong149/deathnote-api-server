package com.rest.api.service.v1.deathnote;

import lombok.Getter;

@Getter
public class Feature {
    
    private final String name;
    private final int weight;
    
    public Feature(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}