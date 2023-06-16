package com.example.advent19.controller;

import com.example.advent19.business.solver.SolverInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final SolverInterface solverInterface;

    private final String inputString = "Blueprint 1:Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian. Each diamond robot costs 1 geode, 8 clay and 7 obsidian.\n" +
            "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian. Each diamond robot costs 3 geode, 2 clay and 3 obsidian.\n";

    @Autowired
    public Controller(SolverInterface solverInterface) {
        this.solverInterface = solverInterface;
    }

    @GetMapping("/solve")
    public final ResponseEntity<Integer> solveTask() {
        Integer quality = solverInterface.solve(inputString);
        return ResponseEntity.ok(quality);
    }

}
