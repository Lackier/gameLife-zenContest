package com.company;

import com.company.game.EvolutionMachine;

public class Main {

    public static void main(String[] args) {
        boolean fileSim = false;
        String fileName = null;
        int N = 10;
        int M = 10;
        int iterations = 5;

        try {
            switch (args.length) {
                case 0:
                case 1:
                    System.out.println("Input one of the next variants:");
                    System.out.println("-h for help.");
                    System.out.println("-f filename [iterations] to read field from a file.");
                    System.out.println("-s M [N] [iterations] to simulate or random field.");
                    throw new ArrayIndexOutOfBoundsException();
                case 2:
                    if (args[0].equals("-f")) {
                        fileSim = true;
                        fileName = args[1];
                    } else if (args[0].equals("-s"))
                        N = Integer.parseInt(args[1]);
                    break;
                case 3:
                    if (args[0].equals("-f")) {
                        fileSim = true;
                        fileName = args[1];
                        iterations = Integer.parseInt(args[2]);
                    } else if (args[0].equals("-s")) {
                        N = Integer.parseInt(args[1]);
                        M = Integer.parseInt(args[2]);
                    }
                    break;
                case 4:
                    if (args[0].equals("-s")) {
                        N = Integer.parseInt(args[1]);
                        M = Integer.parseInt(args[2]);
                        iterations = Integer.parseInt(args[3]);
                    }
                    break;
            }
        }
        catch (Exception exc){
            System.exit(0);
        }

        try{
            EvolutionMachine evolutionMachine;
            if(fileSim)
                evolutionMachine = new EvolutionMachine(fileName, iterations);
            else
                evolutionMachine = new EvolutionMachine(N, M, iterations);
            evolutionMachine.runEvolution();
        }
        catch (IllegalArgumentException exc){
            System.out.println("Error. Sizes of the field should be at least 3x3.");
        }
        catch (ArrayIndexOutOfBoundsException exc){
            System.out.println("Error. Field is broken.");
        }
        catch (Exception exc){
            System.out.println("Error opening or reading file.");
        }
    }
}
