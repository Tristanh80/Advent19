package com.example.advent19.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    private Integer dfs(State state, BuyCosts buyCosts, MaxRobot maxRobot) {
        if (state.getTimeLeft() == 0) {
            return state.getDiamondCount();
        }

        State key = state.duplicate();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        ArrayList<Integer> bestOfTree = new ArrayList<>();

        if (state.getGeodeCount() >= buyCosts.getDiamondGeodeRobotCost() && state.getClayCount() >= buyCosts.getDiamondClayRobotCost() && state.getObsidianCount() >= buyCosts.getDiamondObsidianRobotCost()) {
            State diamondOre = state.simulateBuyingRobotAndIterate(ResourceType.DIAMOND, buyCosts);
            Integer result = dfs(diamondOre, buyCosts, maxRobot);
            bestOfTree.add(result);
            cache.put(key, result);
            return result;
        }

        if (state.getOreCount() >= buyCosts.getGeodeOreRobotCost() && state.getObsidianCount() >= buyCosts.getGeodeObsidianRobotCost() && state.getGeodeRobotCount() < maxRobot.getMaxGeodeRobot()) {
            State buildGeode = state.simulateBuyingRobotAndIterate(ResourceType.GEODE, buyCosts);
            Integer result = dfs(buildGeode, buyCosts, maxRobot);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= buyCosts.getOreRobotCost() && state.getOreRobotCount() < maxRobot.getMaxOreRobot()) {
            State buildOre = state.simulateBuyingRobotAndIterate(ResourceType.ORE, buyCosts);
            Integer result = dfs(buildOre, buyCosts, maxRobot);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= buyCosts.getClayRobotCost() && state.getClayCount() < maxRobot.getMaxClayRobot()) {
            State buildClay = state.simulateBuyingRobotAndIterate(ResourceType.CLAY, buyCosts);
            Integer result = dfs(buildClay, buyCosts, maxRobot);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= buyCosts.getObsidianOreRobotCost() && state.getClayCount() >= buyCosts.getObsidianClayRobotCost() && state.getObsidianRobotCount() < maxRobot.getMaxObsidianRobot()) {
            State buildObsidian = state.simulateBuyingRobotAndIterate(ResourceType.OBSIDIAN, buyCosts);
            Integer result = dfs(buildObsidian, buyCosts, maxRobot);
            bestOfTree.add(result);
        }

        State copy = state.simulateBuyingRobotAndIterate(null, buyCosts);
        Integer result = dfs(copy, buyCosts, maxRobot);

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
        Integer oreRobotCost = blueprint.getRobotCost(ResourceType.ORE).getRessourceCost(ResourceType.ORE);
        Integer clayRobotCost = blueprint.getRobotCost(ResourceType.CLAY).getRessourceCost(ResourceType.ORE);
        Integer obsidianOreRobotCost = blueprint.getRobotCost(ResourceType.OBSIDIAN).getRessourceCost(ResourceType.ORE);
        Integer obsidianClayRobotCost = blueprint.getRobotCost(ResourceType.OBSIDIAN).getRessourceCost(ResourceType.CLAY);
        Integer geodeOreRobotCost = blueprint.getRobotCost(ResourceType.GEODE).getRessourceCost(ResourceType.ORE);
        Integer geodeObsidianRobotCost = blueprint.getRobotCost(ResourceType.GEODE).getRessourceCost(ResourceType.OBSIDIAN);
        Integer diamondGeodeRobotCost = blueprint.getRobotCost(ResourceType.DIAMOND).getRessourceCost(ResourceType.GEODE);
        Integer diamondClayRobotCost = blueprint.getRobotCost(ResourceType.DIAMOND).getRessourceCost(ResourceType.CLAY);
        Integer diamondObsidianRobotCost = blueprint.getRobotCost(ResourceType.DIAMOND).getRessourceCost(ResourceType.OBSIDIAN);

        BuyCosts buyCosts = new BuyCosts(oreRobotCost, clayRobotCost, obsidianOreRobotCost, obsidianClayRobotCost,
                geodeOreRobotCost, geodeObsidianRobotCost, diamondGeodeRobotCost, diamondClayRobotCost, diamondObsidianRobotCost);

        State firstState = new State(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, timeLimit);
        Integer maxOreRobot = Math.max(clayRobotCost, obsidianOreRobotCost);
        maxOreRobot = Math.max(maxOreRobot, geodeOreRobotCost);
        Integer maxClayRobot = Math.max(obsidianClayRobotCost, diamondClayRobotCost);
        Integer maxObsidianRobot = Math.max(geodeObsidianRobotCost, diamondObsidianRobotCost);
        Integer maxGeodeRobot = diamondGeodeRobotCost;

        MaxRobot maxRobot = new MaxRobot(maxOreRobot, maxClayRobot, maxObsidianRobot, maxGeodeRobot);

        dfs(firstState, buyCosts, maxRobot);

        // return the max of cache
        int max = this.cache.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        return max;
    }

    private void writeInFile(String string) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("analysis.txt", true));
            writer.write(string);
            writer.close();
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture du fichier : " + e);
        }
    }

    @Override
    public Integer solve(String string) {
        List<Blueprint> blueprints = blueprintParser.parseBlueprints(string);
        Integer totalQuality = 0;
        Integer bestBlueprintId = 0;
        for (Blueprint blueprint : blueprints) {
            cache.clear();
            Integer max = solve(blueprint, 24);
            String str = "Blueprint " + blueprint.getId() + ": " + (max * blueprint.getId()) + "\n";
            System.out.println(str);
            // Write string to file
            writeInFile(str);
            totalQuality += (max * blueprint.getId());
            if (max > bestBlueprintId) {
                bestBlueprintId = blueprint.getId();
            }
        }
        String bestBlueprint = "Best Blueprint is the blueprint " + bestBlueprintId + ".\n";
        System.out.println(bestBlueprint);
        // Write string to file
        writeInFile(bestBlueprint);
        return totalQuality;
    }
}
