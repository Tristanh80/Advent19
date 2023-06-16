package com.example.advent19.business.solver;

import com.example.advent19.business.ResourceType;
import com.example.advent19.business.parser.Blueprint;
import com.example.advent19.business.parser.BlueprintParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class SolverInterfaceImpl implements SolverInterface {

    private final BlueprintParser blueprintParser;

    HashMap<State, Integer> dfsCache = new HashMap<>();

    @Autowired
    public SolverInterfaceImpl(BlueprintParser blueprintParser) {
        this.blueprintParser = blueprintParser;
    }

    private Integer dfs(State state, AllPrices allPrices, MaxRobot maxRobot) {

        if (state.getTimeLeft() == 0) {
            return state.getDiamondCount();
        }

        State key = state.duplicate();
        if (dfsCache.containsKey(key)) {
            return dfsCache.get(key);
        }

        ArrayList<Integer> bestOfTree = new ArrayList<>();

        if (state.getGeodeCount() >= allPrices.getDiamondGeodeRobotCost() && state.getClayCount() >= allPrices.getDiamondClayRobotCost() && state.getObsidianCount() >= allPrices.getDiamondObsidianRobotCost()) {
            State diamondOre = state.simulateBuyingRobotAndIterate(ResourceType.DIAMOND, allPrices);
            Integer result = dfs(diamondOre, allPrices, maxRobot);
            bestOfTree.add(result);
            dfsCache.put(key, result);
            return result;
        }

        if (state.getOreCount() >= allPrices.getGeodeOreRobotCost() && state.getObsidianCount() >= allPrices.getGeodeObsidianRobotCost() && state.getGeodeRobotCount() < maxRobot.getMaxGeodeRobot()) {
            State buildGeode = state.simulateBuyingRobotAndIterate(ResourceType.GEODE, allPrices);
            Integer result = dfs(buildGeode, allPrices, maxRobot);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= allPrices.getOreRobotCost() && state.getOreRobotCount() < maxRobot.getMaxOreRobot()) {
            State buildOre = state.simulateBuyingRobotAndIterate(ResourceType.ORE, allPrices);
            Integer result = dfs(buildOre, allPrices, maxRobot);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= allPrices.getClayRobotCost() && state.getClayCount() < maxRobot.getMaxClayRobot()) {
            State buildClay = state.simulateBuyingRobotAndIterate(ResourceType.CLAY, allPrices);
            Integer result = dfs(buildClay, allPrices, maxRobot);
            bestOfTree.add(result);
        }

        if (state.getOreCount() >= allPrices.getObsidianOreRobotCost() && state.getClayCount() >= allPrices.getObsidianClayRobotCost() && state.getObsidianRobotCount() < maxRobot.getMaxObsidianRobot()) {
            State buildObsidian = state.simulateBuyingRobotAndIterate(ResourceType.OBSIDIAN, allPrices);
            Integer result = dfs(buildObsidian, allPrices, maxRobot);
            bestOfTree.add(result);
        }

        State copy = state.simulateBuyingRobotAndIterate(null, allPrices);
        Integer result = dfs(copy, allPrices, maxRobot);

        if (bestOfTree.isEmpty()) {
            dfsCache.put(key, result);
        }
        else {
            Integer maxSolve = Collections.max(bestOfTree);
            Integer max = Math.max(maxSolve, result);
            dfsCache.put(key, max);
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

        AllPrices allPrices = new AllPrices(oreRobotCost, clayRobotCost, obsidianOreRobotCost, obsidianClayRobotCost,
                geodeOreRobotCost, geodeObsidianRobotCost, diamondGeodeRobotCost, diamondClayRobotCost, diamondObsidianRobotCost);

        State firstState = new State(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, timeLimit);
        Integer maxOreRobot = Math.max(clayRobotCost, obsidianOreRobotCost);
        maxOreRobot = Math.max(maxOreRobot, geodeOreRobotCost);
        Integer maxClayRobot = Math.max(obsidianClayRobotCost, diamondClayRobotCost);
        Integer maxObsidianRobot = Math.max(geodeObsidianRobotCost, diamondObsidianRobotCost);
        Integer maxGeodeRobot = diamondGeodeRobotCost;

        MaxRobot maxRobot = new MaxRobot(maxOreRobot, maxClayRobot, maxObsidianRobot, maxGeodeRobot);

        dfs(firstState, allPrices, maxRobot);

        // return the max of cache
        int max = this.dfsCache.values().stream().mapToInt(Integer::intValue).max().orElse(0);
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
            dfsCache.clear();
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
