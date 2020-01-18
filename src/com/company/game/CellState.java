package com.company.game;

public enum CellState {
    ALIVE(1),
    DEAD(0);

    private int cellState;

    CellState(int fieldName) {
        this.cellState = fieldName;
    }

    public int getCellState() {
        return cellState;
    }
}
