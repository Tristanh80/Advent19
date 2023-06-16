package com.example.advent19.business.solver;

import com.example.advent19.business.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class State {
    Integer oreRobotCount;
    Integer clayRobotCount;
    Integer obsidianRobotCount;
    Integer geodeRobotCount;
    Integer diamondRobotCount;
    Integer oreCount;
    Integer clayCount;
    Integer obsidianCount;
    Integer geodeCount;
    Integer diamondCount;
    Integer timeLeft;

    public State duplicate() {
        return new State(oreRobotCount, clayRobotCount, obsidianRobotCount, geodeRobotCount, diamondRobotCount,
                oreCount, clayCount,
                obsidianCount, geodeCount, diamondCount, timeLeft);
    }

    public State simulateBuyingRobotAndIterate(ResourceType resourceType, AllPrices allPrices) {
        State newState = this.duplicate();
        if (resourceType == null) {
            newState.setOreCount(this.getOreCount() + this.getOreRobotCount());
            newState.setClayCount(this.getClayCount() + this.getClayRobotCount());
            newState.setObsidianCount(this.getObsidianCount() + this.getObsidianRobotCount());
            newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
            newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
            newState.setTimeLeft(this.getTimeLeft() - 1);
            return newState;
        }
        switch (resourceType) {
            case ORE:
                newState.setOreRobotCount(this.getOreRobotCount() + 1);
                newState.setOreCount(this.getOreCount() - allPrices.getOreRobotCost() + this.getOreRobotCount());
                newState.setClayCount(this.getClayCount() + this.getClayRobotCount());
                newState.setObsidianCount(this.getObsidianCount() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
            case CLAY:
                newState.setClayRobotCount(this.getClayRobotCount() + 1);
                newState.setOreCount(this.getOreCount() - allPrices.getClayRobotCost() + this.getOreRobotCount());
                newState.setClayCount(this.getClayCount() + this.getClayRobotCount());
                newState.setObsidianCount(this.getObsidianCount() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
            case OBSIDIAN:
                newState.setObsidianRobotCount(this.getObsidianRobotCount() + 1);
                newState.setOreCount(this.getOreCount() - allPrices.getObsidianOreRobotCost() + this.getOreRobotCount());
                newState.setClayCount(this.getClayCount() - allPrices.getObsidianClayRobotCost() + this.getClayRobotCount());
                newState.setObsidianCount(this.getObsidianCount() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
            case GEODE:
                newState.setGeodeRobotCount(this.getGeodeRobotCount() + 1);
                newState.setOreCount(this.getOreCount() - allPrices.getGeodeOreRobotCost() + this.getOreRobotCount());
                newState.setClayCount(this.getClayCount() + this.getClayRobotCount());
                newState.setObsidianCount(this.getObsidianCount() - allPrices.getGeodeObsidianRobotCost() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
            case DIAMOND:
                newState.setDiamondRobotCount(this.getDiamondRobotCount() + 1);
                newState.setOreCount(this.getOreCount() + this.getOreRobotCount());
                newState.setClayCount(this.getClayCount() - allPrices.getDiamondClayRobotCost() + this.getClayRobotCount());
                newState.setObsidianCount(this.getObsidianCount() - allPrices.getDiamondObsidianRobotCost() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() - allPrices.getDiamondGeodeRobotCost() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
        }
        newState.setTimeLeft(this.getTimeLeft() - 1);
        return newState;
    }
}
