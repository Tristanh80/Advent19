package com.example.advent19.business.parser;

import com.example.advent19.business.ResourceType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlueprintParserImpl implements BlueprintParser{

    public List<Blueprint> parseBlueprints(String input) {
        String[] lines = input.split("\n");

        List<Blueprint> blueprints = new ArrayList<>();

        for (String line : lines) {
            blueprints.add(parseOneLine(line));
        }

        return blueprints;
    }

    private Blueprint parseOneLine(String line) {
        String[] numbers = line.replaceAll("[^0-9]+", " ").trim().split(" ");

        int[] numbersInt = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            numbersInt[i] = Integer.parseInt(numbers[i]);
        }

        Blueprint blueprint = new Blueprint(numbersInt[0]);

        PriceRobot oreCost = new PriceRobot();
        oreCost.addCost(ResourceType.ORE, numbersInt[1]);

        PriceRobot clayCost = new PriceRobot();
        clayCost.addCost(ResourceType.ORE, numbersInt[2]);

        PriceRobot obsidianCost = new PriceRobot();
        obsidianCost.addCost(ResourceType.ORE, numbersInt[3]);
        obsidianCost.addCost(ResourceType.CLAY, numbersInt[4]);

        PriceRobot geodeCost = new PriceRobot();
        geodeCost.addCost(ResourceType.ORE, numbersInt[5]);
        geodeCost.addCost(ResourceType.OBSIDIAN, numbersInt[6]);

        PriceRobot diamondCost = new PriceRobot();
        diamondCost.addCost(ResourceType.GEODE, numbersInt[7]);
        diamondCost.addCost(ResourceType.CLAY, numbersInt[8]);
        diamondCost.addCost(ResourceType.OBSIDIAN, numbersInt[9]);

        blueprint.addCost(ResourceType.ORE, oreCost);
        blueprint.addCost(ResourceType.CLAY, clayCost);
        blueprint.addCost(ResourceType.OBSIDIAN, obsidianCost);
        blueprint.addCost(ResourceType.GEODE, geodeCost);
        blueprint.addCost(ResourceType.DIAMOND, diamondCost);

        return blueprint;

    }
}
