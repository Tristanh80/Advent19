package com.example.advent19.business;

import lombok.Data;

import java.util.HashMap;

@Data
public class Blueprint {
    private Integer id;
    private HashMap<ResourceType, Costs> costs;

    public Blueprint(int id) {
        this.id = id;
        this.costs = new HashMap<>();
    }


    public void addCost(ResourceType resourceType, Costs cost) {
        this.costs.put(resourceType, cost);
    }

    public Costs getRobotCost(ResourceType resourceType) {
        return this.costs.get(resourceType);
    }

}
