package com.example.advent19.business;

import lombok.Data;

import java.util.HashMap;

@Data
public class Costs {
    private final HashMap<ResourceType, Integer> costs;

    public Costs() {
        this.costs = new HashMap<>();
    }

    public void addCost(ResourceType resourceType, Integer cost) {
        this.costs.put(resourceType, cost);
    }

    public Integer getRessourceCost(ResourceType resourceType) {
        return this.costs.get(resourceType);
    }
}
