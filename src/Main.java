// I have neither given nor received unauthorized aid on this program

import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.Random;

public class Main {

    static Map<String, Double> qTable;

    public static void main(String[] args) {
        System.out.println("Hello World");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the starting number for pile 0: ");
        Integer pile0 = scanner.nextInt();
        System.out.print("Enter the starting number for pile 1: ");
        Integer pile1 = scanner.nextInt();
        System.out.print("Enter the starting number for pile 2: ");
        Integer pile2 = scanner.nextInt();

        System.out.print("How many games do you want to run via q-learning: ");
        int gamesToRun = scanner.nextInt();



        qTable = new HashMap<>();

        setQTable(pile0, pile1, pile2);


        String response;
        String initialState = "A" + pile0.toString() + pile1.toString() + pile2.toString();

        for (int i = 0; i < gamesToRun; i++) {
            response = train(initialState);
            while (!response.equals("")) {
                response = train(response);
            }

        }

        System.out.println();
        System.out.println("Final Q-Table: ");
        System.out.println();
        printQTable(qTable);

        boolean contGame = true;
        while (contGame) {
            System.out.println("Who moves first, (1) User or (2) Computer? ");
            int player = scanner.nextInt();
            System.out.println();
            if (player == 2) {
                playComputerA(initialState);
            } else {
                playComputerB(initialState);
            }

            System.out.println();
            System.out.print("Do you want to play again? (1) Yes (2) No: ");
            int contGameInt = scanner.nextInt();
            // update contGame
            if (contGameInt == 2) {
                contGame = false;
            }
        }


    }

    public static void playComputerA(String stateOG) {
        Scanner scanner = new Scanner(System.in);

        boolean game = true;
        Integer pile0 = Integer.valueOf(stateOG.substring(1, 2));
        Integer pile1 = Integer.valueOf(stateOG.substring(2, 3));
        Integer pile2 = Integer.valueOf(stateOG.substring(3));

        int pile;
        int amount;
        String currPlayer = "A";
        ArrayList<Integer> gamePiles = new ArrayList<>();
        gamePiles.add(pile0);
        gamePiles.add(pile1);
        gamePiles.add(pile2);

        while (game) {
            if (currPlayer.equals("A")) { // computer goes first
                ArrayList<String> nextActions = new ArrayList<>();
                System.out.println("Player A (computer)'s turn; board is (" + gamePiles.get(0) + ", " + gamePiles.get(1) + ", " + gamePiles.get(2) + ").");
                String state = "A" + gamePiles.get(0).toString() + gamePiles.get(1) + gamePiles.get(2);
                for (String name : qTable.keySet()) {
                    if (name.substring(0,4).equals(state)) {
                        nextActions.add(name);
                    }
                }

                // get max move
                Double max = -20000.0;
                String move = "";
                for (String action : nextActions) {
                    if (qTable.get(action) > max) {
                        move = action;
                        max = qTable.get(action);
                    }
                }

                System.out.println("Computer chooses pile " + move.charAt(4) + " and removes " + move.charAt(5) + ".");

                // make move
                pile = Integer.parseInt(move.substring(4, 5));
                amount =  Integer.parseInt(move.substring(5));
                gamePiles.set(pile, gamePiles.get(pile) - amount);


                // update game
                if (gamePiles.get(0) + gamePiles.get(1) + gamePiles.get(2) == 0) {
                    game = false;
                }

                currPlayer = "B";

            } else { // user is player B
                System.out.println("Player B (user)'s turn; board is (" + gamePiles.get(0) + ", " + gamePiles.get(1) + ", " + gamePiles.get(2)+ ").");
                System.out.print("What pile? ");
                pile = scanner.nextInt();
                System.out.print("How many? ");
                amount = scanner.nextInt();

                // make move
                gamePiles.set(pile, gamePiles.get(pile) - amount);

                // Update game
                if (gamePiles.get(0) + gamePiles.get(1) + gamePiles.get(2) == 0) {
                    game = false;
                }
                // Update player
                currPlayer = "A";
            }
        }
        System.out.println("Game over.");
        if (currPlayer.equals("A")) {
            System.out.println("Winner is " + currPlayer + " (computer)");
        } else {
            System.out.println("Winner is " + currPlayer + " (user)");
        }
    }

    public static void playComputerB(String stateOG) {
        Scanner scanner = new Scanner(System.in);

        boolean game = true;
        Integer pile0 = Integer.valueOf(stateOG.substring(1, 2));
        Integer pile1 = Integer.valueOf(stateOG.substring(2, 3));
        Integer pile2 = Integer.valueOf(stateOG.substring(3));

        int player = 1;
        int pile;
        int amount;
        String currPlayer = "A";
        ArrayList<Integer> gamePiles = new ArrayList<>();
        gamePiles.add(pile0);
        gamePiles.add(pile1);
        gamePiles.add(pile2);

        while (game) {
            if (currPlayer.equals("A")) { // User goes first
                System.out.println("Player A (user)'s turn; board is (" + gamePiles.get(0) + ", " + gamePiles.get(1) + ", " + gamePiles.get(2)+ ").");
                System.out.print("What pile? ");
                pile = scanner.nextInt();
                System.out.print("How many? ");
                amount = scanner.nextInt();

                // make move
                gamePiles.set(pile, gamePiles.get(pile) - amount);

                // Update game
                if (gamePiles.get(0) + gamePiles.get(1) + gamePiles.get(2) == 0) {
                    game = false;
                }
                // Update player
                currPlayer = "B";

            } else { // computer is player B
                ArrayList<String> nextActions = new ArrayList<>();
                System.out.println("Player B (computer)'s turn; board is (" + gamePiles.get(0) + ", " + gamePiles.get(1) + ", " + gamePiles.get(2) + ").");
                String state = "B" + gamePiles.get(0).toString() + gamePiles.get(1) + gamePiles.get(2);
                for (String name : qTable.keySet()) {
                    if (name.substring(0,4).equals(state)) {
                        nextActions.add(name);
                    }
                }

                // get min move
                Double min = 20000.0;
                String move = "";
                for (String action : nextActions) {
                    if (qTable.get(action) < min) {
                        move = action;
                        min = qTable.get(action);
                    }
                }

                System.out.println("Computer chooses pile " + move.charAt(4) + " and removes " + move.charAt(5) + ".");

                // make move
                pile = Integer.parseInt(move.substring(4, 5));
                amount =  Integer.parseInt(move.substring(5));
                gamePiles.set(pile, gamePiles.get(pile) - amount);


                // update game
                if (gamePiles.get(0) + gamePiles.get(1) + gamePiles.get(2) == 0) {
                    game = false;
                }

                currPlayer = "A";
            }
        }
        System.out.println("Game over.");
        if (currPlayer.equals("A")) {
            System.out.println("Winner is " + currPlayer + " (user)");
        } else {
            System.out.println("Winner is " + currPlayer + " (computer)");
        }
    }

    // return a value so that you know if game is continuing or not. 100k games not moves.
    public static String train(String stateOG) {
        boolean playerA = stateOG.charAt(0) == 'A';

        // pick random move:
        // TODO
        Integer pile0 = Integer.valueOf(stateOG.substring(1, 2));
        Integer pile1 = Integer.valueOf(stateOG.substring(2, 3));
        Integer pile2 = Integer.valueOf(stateOG.substring(3));

        ArrayList<Integer> pilesToChoose = new ArrayList<>();
        if (pile0 > 0) {
            pilesToChoose.add(0);
        }
        if (pile1 > 0) {
            pilesToChoose.add(1);
        }
        if (pile2 > 0) {
            pilesToChoose.add(2);
        }

        // pick random pile from list
        int pileIdx = (int) (Math.random() * pilesToChoose.size());
        int pilePicked = pilesToChoose.get(pileIdx);

        int numberToTake = 0;
        if (pilePicked == 0) {
            numberToTake = (int) (Math.random() * pile0 + 1);
        }
        else if (pilePicked == 1) {
            numberToTake = (int) (Math.random() * pile1 + 1);
        }
        else if (pilePicked == 2) {
            numberToTake = (int) (Math.random() * pile2 + 1);
        }

        String state;
        if (playerA) {
            state = "A" + pile0 + pile1 + pile2 + pilePicked + numberToTake;
        }
        else {
            state = "B" + pile0 + pile1 + pile2 + pilePicked + numberToTake;

        }


        // check if final state in order to define r
        if (finalCheck(state)) {
            if (state.charAt(0) == 'A'){
                qTable.put(state, -1000.0);
            } else {
                qTable.put(state, 1000.0);
            }

            return ""; // empty string to beak out of loop
        }

        double newVal = minMaxVals(state);

        double update = 0.9 * (newVal);

        qTable.put(state, update); // update value in table if not an end state
        // System.out.println(state);

        // get next move:
        ArrayList<Integer> pileNums = new ArrayList<>();

        Integer p0 = Integer.valueOf(state.substring(1, 2));
        pileNums.add(p0);
        Integer p1 = Integer.valueOf(state.substring(2, 3));
        pileNums.add(p1);
        Integer p2 = Integer.valueOf(state.substring(3, 4));
        pileNums.add(p2);

        Integer pile = Integer.valueOf(state.substring(4, 5));
        Integer numToTake = Integer.valueOf(state.substring(5));

        Integer newPileNum = pileNums.get(pile) - numToTake;

        pileNums.set(pile, newPileNum); // should update the pile with new amt
        // need to add all to a return state

        String updateState;
        if (playerA) {
            updateState = "B" + pileNums.get(0).toString() + pileNums.get(1) + pileNums.get(2);

        }
        else {updateState = "A" + pileNums.get(0).toString() + pileNums.get(1) + pileNums.get(2);}

        // System.out.println(updateState);

        return updateState;
    }

    public static Double minMaxVals(String state) {
        ArrayList<Integer> pileNums = new ArrayList<>();
        boolean playerA = state.charAt(0) == 'A';

        Integer p0 = Integer.valueOf(state.substring(1, 2));
        pileNums.add(p0);
        Integer p1 = Integer.valueOf(state.substring(2, 3));
        pileNums.add(p1);
        Integer p2 = Integer.valueOf(state.substring(3, 4));
        pileNums.add(p2);

        Integer pile = Integer.valueOf(state.substring(4, 5));
        Integer numToTake = Integer.valueOf(state.substring(5));

        int newPileNum = pileNums.get(pile) - numToTake;

        pileNums.set(pile, newPileNum);

        // next state should have a different letter @start
        String updateState;
        if (playerA) {
            updateState = "B" + pileNums.get(0).toString() + pileNums.get(1) + pileNums.get(2);

        } else {
            updateState = "A" + pileNums.get(0).toString() + pileNums.get(1) + pileNums.get(2);
        }


        ArrayList<String> nextActions = new ArrayList<>();
        for (String name : qTable.keySet()) {
            if (name.substring(0, 4).equals(updateState)) {
                nextActions.add(name);
            }
        }
        // checks player on original state
        if (playerA) {
            Double min = Double.MAX_VALUE;
            for (String actions : nextActions) {
                if (qTable.get(actions) < min) {
                    min = qTable.get(actions);
                }
            }
            return min;
        } else {
            Double max = -200000.0;
            for (String actions : nextActions) {
                if (qTable.get(actions) > max) {
                    max = qTable.get(actions);
                }
            }
            return max;

        }
    }

    public static boolean finalCheck(String state) {
        int totalInPiles = 0;

        Integer p0 = Integer.valueOf(state.substring(1, 2));
        // pileNums.add(p0);
        totalInPiles += p0;
        Integer p1 = Integer.valueOf(state.substring(2, 3));
        // pileNums.add(p1);
        totalInPiles += p1;
        Integer p2 = Integer.valueOf(state.substring(3, 4));
        // pileNums.add(p2);
        totalInPiles += p2;

        Integer pile = Integer.valueOf(state.substring(4, 5));
        Integer numToTake = Integer.valueOf(state.substring(5));

        return totalInPiles - numToTake == 0;
    }

    public static void setQTable(int pile0, int pile1, int pile2) {

        for (int p0 = 0; p0 <= pile0; p0++) {
            for (int p1 = 0; p1 <= pile1; p1++) {
                for (int p2 = 0; p2 <= pile2; p2++) {
                    for (int i = p0; i > 0; i--){
                        qTable.put("A" + p0 + p1 + p2 + "0" + i, 0.0);
                        qTable.put("B" + p0 + p1 + p2 + "0" + i, 0.0);
                    }

                    for (int i = p1; i > 0; i--){
                        qTable.put("A" + p0 + p1 + p2 + "1" + i, 0.0);
                        qTable.put("B" + p0 + p1 + p2 + "1" + i, 0.0);
                    }

                    for (int i = p2; i > 0; i--){
                        qTable.put("A" + p0 + p1 + p2 + "2" + i, 0.0);
                        qTable.put("B" + p0 + p1 + p2 + "2" + i, 0.0);
                    }
                }
            }
        }
    }

    public static void printQTable(Map<String, Double> qTablePrint) {
        ArrayList<String> statesToPrint = new ArrayList<>(qTablePrint.keySet());
        Collections.sort(statesToPrint);

        for (String name : statesToPrint) {
            if (qTablePrint.get(name) == 0.0) {continue;}
            System.out.println("Q[" + name.substring(0,4) + ", " + name.substring(4) + "] = " + qTablePrint.get(name));
        }
    }
}
