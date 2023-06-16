package com.example.advent19.business.parser;

import com.example.advent19.business.ResourceType;
import lombok.Data;

import java.util.HashMap;

@Data
public class Blueprint {
    private Integer id;
    private HashMap<ResourceType, PriceRobot> costs;

    public Blueprint(int id) {
        this.id = id;
        this.costs = new HashMap<>();
    }


    public void addCost(ResourceType resourceType, PriceRobot cost) {
        this.costs.put(resourceType, cost);
    }

    public PriceRobot getRobotCost(ResourceType resourceType) {
        return this.costs.get(resourceType);
    }

}
