package com.example.advent19.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class SolverInterfaceImpl implements SolverInterface {

    private final BlueprintParser blueprintParser;

    HashMap<State, Integer> cache = new HashMap<>();

    @Autowired
    public SolverInterfaceImpl(BlueprintParser blueprintParser) {
        this.blueprintParser = blueprintParser;
    }

    private Integer dfs(State state, BuyCosts buyCosts) {
        if (state.getTimeLeft() == 0) {
            return state.getDiamondCount();
        }

        State key = state.duplicate();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        ArrayList<Integer> bestOfTree = new ArrayList<>();

        if (state.getGeodeCount() >= buyCosts.getDiamondGeodeRobotCost() && state.getClayCount() >= buyCosts.getDiamondClayRobotCost() && state.getObsidianCount() >= buyCosts.getDiamondObsidianRobotCost()) {
            State newState = state.duplicate();
            newState.setDiamondRobotCount(state.getDiamondRobotCount() + 1);
            newState.setOreCount(state.getOreCount() + state.getOreRobotCount());
            newState.setClayCount(state.getClayCount() - buyCosts.getDiamondClayRobotCost() + state.getClayRobotCount());
            newState.setObsidianCount(state.getObsidianCount() - buyCosts.getDiamondObsidianRobotCost() + state.getObsidianRobotCount());
            newState.setGeodeCount(state.getGeodeCount() - buyCosts.getDiamondGeodeRobotCost() + state.getGeodeRobotCount());
            newState.setDiamondCount(state.getDiamondCount() + state.getDiamondRobotCount());
            newState.setTimeLeft(state.getTimeLeft() - 1);
            Integer result = dfs(newState, buyCosts);
            bestOfTree.add(result);
            cache.put(key, result);
            return result;
        }

        if (state.getOreCount() >= buyCosts.getGeodeOreRobotCost() && state.getObsidianCount() >= buyCosts.getGeodeObsidianRobotCost()) {
            State newState = state.duplicate();
            newState.setGeodeRobotCount(state.getGeodeRobotCount() + 1);
            newState.setOreCount(state.getOreCount() - buyCosts.getGeodeOreRobotCost() + state.getOreRobotCount());
            newState.setClayCount(state.getClayCount() + state.getClayRobotCount());
            newState.setObsidianCount(state.getObsidianCount() - buyCosts.getGeodeObsidianRobotCost() + state.getObsidianRobotCount());
            newState.setGeodeCount(state.getGeodeCount() + state.getGeodeRobotCount());
            newState.setDiamondCount(state.getDiamondCount() + state.getDiamondRobotCount());
            newState.setTimeLeft(state.getTimeLeft() - 1);
            Integer result = dfs(newState, buyCosts);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= buyCosts.getOreRobotCost()) {
            State buildOre = state.duplicate();
            buildOre.setOreRobotCount(state.getOreRobotCount() + 1);
            buildOre.setOreCount(state.getOreCount() - buyCosts.getOreRobotCost() + state.getOreRobotCount());
            buildOre.setClayCount(state.getClayCount() + state.getClayRobotCount());
            buildOre.setObsidianCount(state.getObsidianCount() + state.getObsidianRobotCount());
            buildOre.setGeodeCount(state.getGeodeCount() + state.getGeodeRobotCount());
            buildOre.setDiamondCount(state.getDiamondCount() + state.getDiamondRobotCount());
            buildOre.setTimeLeft(state.getTimeLeft() - 1);
            Integer result = dfs(buildOre, buyCosts);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= buyCosts.getClayRobotCost()) {
            State buildClay = state.duplicate();
            buildClay.setClayRobotCount(state.getClayRobotCount() + 1);
            buildClay.setOreCount(state.getOreCount() - buyCosts.getClayRobotCost() + state.getOreRobotCount());
            buildClay.setClayCount(state.getClayCount() + state.getClayRobotCount());
            buildClay.setObsidianCount(state.getObsidianCount() + state.getObsidianRobotCount());
            buildClay.setGeodeCount(state.getGeodeCount() + state.getGeodeRobotCount());
            buildClay.setDiamondCount(state.getDiamondCount() + state.getDiamondRobotCount());
            buildClay.setTimeLeft(state.getTimeLeft() - 1);
            Integer result = dfs(buildClay, buyCosts);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= buyCosts.getObsidianOreRobotCost() && state.getClayCount() >= buyCosts.getObsidianClayRobotCost()) {
            State buildObsidian = state.duplicate();
            buildObsidian.setObsidianRobotCount(state.getObsidianRobotCount() + 1);
            buildObsidian.setOreCount(state.getOreCount() - buyCosts.getObsidianOreRobotCost() + state.getOreRobotCount());
            buildObsidian.setClayCount(state.getClayCount() - buyCosts.getObsidianClayRobotCost() + state.getClayRobotCount());
            buildObsidian.setObsidianCount(state.getObsidianCount() + state.getObsidianRobotCount());
            buildObsidian.setGeodeCount(state.getGeodeCount() + state.getGeodeRobotCount());
            buildObsidian.setDiamondCount(state.getDiamondCount() + state.getDiamondRobotCount());
            buildObsidian.setTimeLeft(state.getTimeLeft() - 1);
            Integer result = dfs(buildObsidian, buyCosts);
            bestOfTree.add(result);
        }

        State copy = state.duplicate();
        copy.setOreCount(state.getOreCount() + state.getOreRobotCount());
        copy.setClayCount(state.getClayCount() + state.getClayRobotCount());
        copy.setObsidianCount(state.getObsidianCount() + state.getObsidianRobotCount());
        copy.setGeodeCount(state.getGeodeCount() + state.getGeodeRobotCount());
        copy.setDiamondCount(state.getDiamondCount() + state.getDiamondRobotCount());
        copy.setTimeLeft(state.getTimeLeft() - 1);
        Integer result = dfs(copy, buyCosts);

        if (bestOfTree.isEmpty()) {
            cache.put(key, result);
        }
        else {
            Integer maxSolve = Collections.max(bestOfTree);
            Integer max = Math.max(maxSolve, result);
            cache.put(key, max);
        }
        return result;
    }

    private Integer solve(Blueprint blueprint, Integer timeLimit) {
        Integer oreRobotCost = blueprint.getCost(ResourceType.ORE).getCost(ResourceType.ORE);
        Integer clayRobotCost = blueprint.getCost(ResourceType.CLAY).getCost(ResourceType.ORE);
        Integer obsidianOreRobotCost = blueprint.getCost(ResourceType.OBSIDIAN).getCost(ResourceType.ORE);
        Integer obsidianClayRobotCost = blueprint.getCost(ResourceType.OBSIDIAN).getCost(ResourceType.CLAY);
        Integer geodeOreRobotCost = blueprint.getCost(ResourceType.GEODE).getCost(ResourceType.ORE);
        Integer geodeObsidianRobotCost = blueprint.getCost(ResourceType.GEODE).getCost(ResourceType.OBSIDIAN);
        Integer diamondGeodeRobotCost = blueprint.getCost(ResourceType.DIAMOND).getCost(ResourceType.GEODE);
        Integer diamondClayRobotCost = blueprint.getCost(ResourceType.DIAMOND).getCost(ResourceType.CLAY);
        Integer diamondObsidianRobotCost = blueprint.getCost(ResourceType.DIAMOND).getCost(ResourceType.OBSIDIAN);

        BuyCosts buyCosts = new BuyCosts(oreRobotCost, clayRobotCost, obsidianOreRobotCost, obsidianClayRobotCost,
                geodeOreRobotCost, geodeObsidianRobotCost, diamondGeodeRobotCost, diamondClayRobotCost, diamondObsidianRobotCost);

        State firstState = new State(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, timeLimit);
        dfs(firstState, buyCosts);

        // return the max of cache
        int max = this.cache.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        return max;
    }

    @Override
    public Integer solve(String string) {
        List<Blueprint> blueprints = blueprintParser.parseBlueprints(string);
        Integer totalQuality = 0;
        for (Blueprint blueprint : blueprints) {
            cache.clear();
            Integer max = solve(blueprint, 24);
            System.out.println("ID: " + blueprint.getId() + " - Max quality: " + max);
            totalQuality += (max * blueprint.getId());
            System.out.println("Total quality: " + totalQuality);
        }

        return totalQuality;
    }
}
