package com.company.game;

public class Cell {
    private CellState state;
    private CellState nextState;
    private Cell[] neighbors;

    Cell(CellState state){
        this.state = state;
        neighbors = new Cell[8];
        nextState = state;
    }

    private CellState getState(){
        return state;
    }

    void setNeighbors(Cell[] neighbors){
        this.neighbors = neighbors;
    }

    void newState() {
        int living = findLivingNeighbors();
        nextState = state;

        if(state == CellState.ALIVE)
            if (living < 2 || living > 3)
                nextState = CellState.DEAD;
        else
            if (living == 3)
                nextState = CellState.ALIVE;
    }

    void changeState(){
        state = nextState;
    }

    private int findLivingNeighbors(){
        int alives = 0;
        for (Cell neighbor : neighbors){
            if (neighbor.state == CellState.ALIVE)
                alives += 1;
        }
        return alives;
    }

    @Override
    public String toString() {
        return String.valueOf(getState().getCellState());
    }
}
