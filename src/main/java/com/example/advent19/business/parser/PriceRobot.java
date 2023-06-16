package com.example.advent19.business.parser;

import com.example.advent19.business.ResourceType;
import lombok.Data;

import java.util.HashMap;

@Data
public class PriceRobot {
    private final HashMap<ResourceType, Integer> costs;

    public PriceRobot() {
        this.costs = new HashMap<>();
    }

    public void addCost(ResourceType resourceType, Integer cost) {
        this.costs.put(resourceType, cost);
    }

    public Integer getRessourceCost(ResourceType resourceType) {
        return this.costs.get(resourceType);
    }
}
