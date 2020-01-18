package com.company.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Field {
    private Cell[][] field;
    private Random rnd = new Random();

    Field(int N, int M) throws IllegalArgumentException{
        if (N < 3 || M < 3)
            throw new IllegalArgumentException();
        generateNewField(N, M);
        setNeighborsForAll();
    }

    Field(File file) throws IllegalArgumentException, IOException, ArrayIndexOutOfBoundsException {
        generateNewField(file);
        setNeighborsForAll();
    }

    private void generateNewField(int N, int M){
        field = new Cell[N][M];

        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                field[i][j] = randomCell();
    }

    private void generateNewField(File file) throws IOException, ArrayIndexOutOfBoundsException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        List<String> lines = new ArrayList<>();
        while (br.ready()) {
            lines.add(br.readLine());
        }
        br.close();

        int N = lines.get(0).split(" ").length;
        int M = lines.size();
        field = new Cell[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                String[] line = lines.get(i).split(" ");

                int state = Integer.parseInt(line[j]);
                if (state == 0)
                    field[i][j] = new Cell(CellState.DEAD);
                else if (state == 1)
                    field[i][j] = new Cell(CellState.ALIVE);
                else
                    throw new IllegalArgumentException();
            }
        }
    }

    private void setNeighborsForAll(){
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++) {
                Cell[] neighbors = findNeighbors(i, j);
                field[i][j].setNeighbors(neighbors);
            }
    }

    void nextGeneration(){
        for (Cell[] cells : field)
            for (int j = 0; j < field[0].length; j++)
                cells[j].newState();

        for (Cell[] cells : field)
            for (int j = 0; j < field[0].length; j++)
                cells[j].changeState();
    }

    private Cell[] findNeighbors(int i, int j){
        Cell[] neighbors = new Cell[8];
        int LAST_LINE = field.length - 1;
        int LAST_COL = field[0].length - 1;

        boolean firstLine = i == 0;
        boolean lastLine = i == LAST_LINE;
        boolean firstCol = j == 0;
        boolean lastCol = j == LAST_COL;

        if (firstLine && firstCol) {
            neighbors[0] = field[LAST_LINE][LAST_COL];
            neighbors[1] = field[LAST_LINE][0];
            neighbors[2] = field[LAST_LINE][1];
            neighbors[3] = field[0][LAST_COL];
            neighbors[4] = field[0][1];
            neighbors[5] = field[1][LAST_COL];
            neighbors[6] = field[1][0];
            neighbors[7] = field[1][1];
        }
        else if (firstLine && lastCol) {
            neighbors[0] = field[LAST_LINE][LAST_COL - 1];
            neighbors[1] = field[LAST_LINE][LAST_COL];
            neighbors[2] = field[LAST_LINE][0];
            neighbors[3] = field[0][LAST_COL - 1];
            neighbors[4] = field[0][0];
            neighbors[5] = field[1][LAST_COL - 1];
            neighbors[6] = field[1][LAST_COL];
            neighbors[7] = field[1][0];
        }
        else if (lastLine && firstCol) {
            neighbors[0] = field[LAST_LINE - 1][LAST_COL];
            neighbors[1] = field[LAST_LINE - 1][0];
            neighbors[2] = field[LAST_LINE - 1][1];
            neighbors[3] = field[LAST_LINE][LAST_COL];
            neighbors[4] = field[LAST_LINE][1];
            neighbors[5] = field[0][LAST_COL];
            neighbors[6] = field[0][0];
            neighbors[7] = field[0][1];
        }
        else if (lastLine && lastCol) {
            neighbors[0] = field[LAST_LINE - 1][LAST_COL - 1];
            neighbors[1] = field[LAST_LINE - 1][LAST_COL];
            neighbors[2] = field[LAST_LINE - 1][0];
            neighbors[3] = field[LAST_LINE][LAST_COL - 1];
            neighbors[4] = field[LAST_LINE][0];
            neighbors[5] = field[0][LAST_COL - 1];
            neighbors[6] = field[0][LAST_COL];
            neighbors[7] = field[0][0];
        }
        else if (firstLine) {
            neighbors[0] = field[LAST_LINE][j - 1];
            neighbors[1] = field[LAST_LINE][j];
            neighbors[2] = field[LAST_LINE][j + 1];
            neighbors[3] = field[0][j - 1];
            neighbors[4] = field[0][j + 1];
            neighbors[5] = field[1][j - 1];
            neighbors[6] = field[1][j];
            neighbors[7] = field[1][j + 1];
        }
        else if (lastLine) {
            neighbors[0] = field[LAST_LINE - 1][j - 1];
            neighbors[1] = field[LAST_LINE - 1][j];
            neighbors[2] = field[LAST_LINE - 1][j + 1];
            neighbors[3] = field[LAST_LINE][j - 1];
            neighbors[4] = field[LAST_LINE][j + 1];
            neighbors[5] = field[0][j - 1];
            neighbors[6] = field[0][j];
            neighbors[7] = field[0][j + 1];
        }
        else if (firstCol) {
            neighbors[0] = field[i - 1][LAST_COL];
            neighbors[1] = field[i - 1][0];
            neighbors[2] = field[i - 1][1];
            neighbors[3] = field[i][LAST_COL];
            neighbors[4] = field[i][1];
            neighbors[5] = field[i + 1][LAST_COL];
            neighbors[6] = field[i + 1][0];
            neighbors[7] = field[i + 1][1];
        }
        else if (lastCol) {
            neighbors[0] = field[i - 1][LAST_COL - 1];
            neighbors[1] = field[i - 1][LAST_COL];
            neighbors[2] = field[i - 1][0];
            neighbors[3] = field[i][LAST_COL - 1];
            neighbors[4] = field[i][0];
            neighbors[5] = field[i + 1][LAST_COL - 1];
            neighbors[6] = field[i + 1][LAST_COL];
            neighbors[7] = field[i + 1][0];
        }
        else {
            neighbors[0] = field[i - 1][j - 1];
            neighbors[1] = field[i - 1][j];
            neighbors[2] = field[i - 1][j + 1];
            neighbors[3] = field[i][j - 1];
            neighbors[4] = field[i][j + 1];
            neighbors[5] = field[i + 1][j - 1];
            neighbors[6] = field[i + 1][j];
            neighbors[7] = field[i + 1][j + 1];
        }

        return neighbors;
    }

    private Cell randomCell(){
        if(rnd.nextInt(3) == 1)
            return new Cell(CellState.ALIVE);
        else
            return new Cell(CellState.DEAD);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell[] line : field)
            stringBuilder.append(Arrays.toString(line)).append('\n');
        return stringBuilder.toString();
    }
}
