package com.example.advent19.business;

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

    public State SimulateBuyingRobotAndIterate(ResourceType resourceType, BuyCosts buyCosts) {
        State newState = this.duplicate();
        switch (resourceType) {
            case ORE:
                newState.setOreRobotCount(this.getOreRobotCount() + 1);
                newState.setOreCount(newState.getOreCount() - buyCosts.getOreRobotCost());
                break;
            case CLAY:
                newState.setClayRobotCount(this.getClayRobotCount() + 1);
                newState.setClayCount(newState.getClayCount() - buyCosts.getClayRobotCost());
                break;
            case OBSIDIAN:
                newState.setObsidianRobotCount(this.getObsidianRobotCount() + 1);
                newState.setOreCount(this.getOreCount() - buyCosts.getOreRobotCost() + this.getOreCount());
                newState.setClayCount(this.getClayCount() - buyCosts.getObsidianClayRobotCost() + this.getClayCount());
                newState.setObsidianCount(this.getObsidianCount() + this.getObsidianRobotCount());
                break;
            case GEODE:
                newState.setGeodeRobotCount(this.getGeodeRobotCount() + 1);

                break;
            case DIAMOND:
                newState.setDiamondRobotCount(this.getDiamondRobotCount() + 1);
                newState.setDiamondCount(this.getDiamondCount() - 1);
                break;
        }
        newState.setTimeLeft(this.getTimeLeft() - 1);
        return newState;
    }
}
