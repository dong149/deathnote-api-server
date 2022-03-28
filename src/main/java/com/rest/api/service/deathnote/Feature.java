package com.rest.api.service.deathnote;

import lombok.Getter;

@Getter
public class Feature {

    private String name;
    private int weight;

    public Feature(String name, int weight) {
        this.name = name;
        this.weight = weight;

    }
}