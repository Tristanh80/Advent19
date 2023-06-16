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

    public State simulateBuyingRobotAndIterate(ResourceType resourceType, BuyCosts buyCosts) {
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
                newState.setOreCount(newState.getOreCount() - buyCosts.getOreRobotCost());
                newState.setClayCount(this.getClayCount() + this.getClayRobotCount());
                newState.setObsidianCount(this.getObsidianCount() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
            case CLAY:
                newState.setClayRobotCount(this.getClayRobotCount() + 1);
                newState.setClayCount(this.getClayCount() - buyCosts.getClayRobotCost());
                newState.setOreCount(this.getOreCount() + this.getOreRobotCount());
                newState.setObsidianCount(this.getObsidianCount() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
            case OBSIDIAN:
                newState.setObsidianRobotCount(this.getObsidianRobotCount() + 1);
                newState.setOreCount(this.getOreCount() - buyCosts.getOreRobotCost() + this.getOreCount());
                newState.setClayCount(this.getClayCount() - buyCosts.getObsidianClayRobotCost() + this.getClayCount());
                newState.setObsidianCount(this.getObsidianCount() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
            case GEODE:
                newState.setGeodeRobotCount(this.getGeodeRobotCount() + 1);
                newState.setOreCount(this.getOreCount() - buyCosts.getOreRobotCost() + this.getOreRobotCount());
                newState.setClayCount(this.getClayCount() + this.getClayRobotCount());
                newState.setObsidianCount(this.getObsidianCount() - buyCosts.getGeodeObsidianRobotCost() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                break;
            case DIAMOND:
                newState.setDiamondRobotCount(this.getDiamondRobotCount() + 1);
                newState.setClayCount(this.getClayCount() - buyCosts.getDiamondClayRobotCost() + this.getClayRobotCount());
                newState.setObsidianCount(this.getObsidianCount() - buyCosts.getDiamondObsidianRobotCost() + this.getObsidianRobotCount());
                newState.setGeodeCount(this.getGeodeCount() - buyCosts.getDiamondGeodeRobotCost() + this.getGeodeRobotCount());
                newState.setDiamondCount(this.getDiamondCount() + this.getDiamondRobotCount());
                newState.setOreCount(this.getOreCount() + this.getOreRobotCount());
                break;
        }
        newState.setTimeLeft(this.getTimeLeft() - 1);
        return newState;
    }
}
