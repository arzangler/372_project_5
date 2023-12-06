import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.Random;

public class Main {

    static Map<String, Double> qTable;

    public static void main(String[] args) {
        System.out.println("Hello World");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the starting number for pile 0: ");
        int pile0 = scanner.nextInt();
        System.out.print("Enter the starting number for pile 1: ");
        int pile1 = scanner.nextInt();
        System.out.print("Enter the starting number for pile 2: ");
        int pile2 = scanner.nextInt();

        System.out.print("How many games do you want to run via q-learning: ");
        int gamesToRun = scanner.nextInt();

        System.out.println("Q-learning games to run " + gamesToRun);

        qTable = new HashMap<>();

        // TODO train
        setQTable(pile0, pile1, pile2);
        printQTable(qTable);

        // randomize moves for state to pass to train fxn
        ArrayList<Integer> pilesToChoose = new ArrayList<>();
        if (pile0 > 0) {pilesToChoose.add(0);}
        if (pile1 > 0) {pilesToChoose.add(1);}
        if (pile2 > 0) {pilesToChoose.add(2);}

        // pick random pile from the list
        int pileIdx = (int) (Math.random() * pilesToChoose.size());
        int pile = pilesToChoose.get(pileIdx);
        int numToTake = 0;

        if (pile == 0) {
            // randomize from pile0
            numToTake = (int) (Math.random() * pile0 + 1);
        }
        else if (pile == 1) {
            // randomize from pile1
            numToTake = (int) (Math.random() * pile1 + 1);
        }
        else if (pile == 2) {
            // randomize from pile2
            numToTake = (int) (Math.random() * pile2 + 1);
        }

        String stateToTrain = "A" + pile0 + pile1 + pile2 + pile + numToTake;

        System.out.println(stateToTrain);

        train(stateToTrain);

        printQTable(qTable);

        // TODO play
        // TODO make play loop
    }

    // return a value so that you know if game is continuing or not. 100k games not moves.
    public static int train(String state) {
        Double stateVal = qTable.get(state);
        // check if final state in order to define r
        // TODO might need to change -1000 and 1000.
        if (finalCheck(state)) {
            if (state.charAt(0) == 'A'){
                qTable.put(state, 1000.0);
            } else {
                qTable.put(state, -1000.0);
            }

            return 1;
        }



        return 0;
    }

    public static boolean finalCheck(String state) {
        ArrayList<Integer> pileNums = new ArrayList<>();

        Integer p0 = Integer.valueOf(state.substring(1, 2));
        pileNums.add(p0);
        Integer p1 = Integer.valueOf(state.substring(2, 3));
        pileNums.add(p1);
        Integer p2 = Integer.valueOf(state.substring(3, 4));
        pileNums.add(p2);

        Integer pile = Integer.valueOf(state.substring(4, 5));
        Integer numToTake = Integer.valueOf(state.substring(5));

        return pileNums.get(pile) - numToTake == 0;
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

            System.out.println("Q[" + name.substring(0,4) + ", " + name.substring(4) + "] = " + qTablePrint.get(name));
        }
    }

}
