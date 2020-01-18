package com.company.game;

import java.io.File;
import java.io.IOException;

public class EvolutionMachine {
    private Field field;
    private int totalTime;

    public EvolutionMachine(String fileName, int totalTimeSec) throws IOException {
        field = new Field(new File(fileName));
        this.totalTime = totalTimeSec;
    }

    public EvolutionMachine(int N, int M, int totalTimeSec){
        field = new Field(N, M);
        this.totalTime = totalTimeSec;
    }

    public void runEvolution() throws InterruptedException {
        while(totalTime > 0){
            field.nextGeneration();
            logEvolutionStep();
            Thread.sleep(1000);
            totalTime -= 1;
        }
    }

    private void logEvolutionStep(){
        System.out.println(field.toString());
    }
}
