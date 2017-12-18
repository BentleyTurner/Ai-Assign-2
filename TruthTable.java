package ai.assign.pkg2;
import ai.assign.pkg2.InferenceMethod;
import java.util.ArrayList;
import java.util.List;

public class TruthTable extends InferenceMethod {

    public TruthTable() {
        code = "TT";
        longName = "Truth Table";
    }

    @Override
    public List<String> Solve(List<String> Facts, List<String> Rules, String q) {

        List<String> uniqueStrings = FindUniqueVariables(Facts, Rules);                      // populate uniqueStrings with variables (a,c,p2,p1) etc

        int[][] TruthTable = CreateTruthTable(uniqueStrings.size());					//array of all possible variable combinations

        List<int[]> SolutionSpace = CreateSolutionSpace(TruthTable, uniqueStrings, Facts, Rules);		//list containing all rows from TruthTable that meet
        //conform to rule set

        //Testing
        //uniqueStrings = CreateDummyUniqueStrings();
        //	PrintTruthTable(TruthTable,uniqueStrings);
        PrintSolutionSpace(SolutionSpace, uniqueStrings);

        //FORMAT OUTPUT	
        if (SolutionSpace.size() > 0) {
            List<String> toReturn = new ArrayList<String>();
            toReturn.add(Integer.toString(SolutionSpace.size()));
            return toReturn;
        }
        return null;
    }

    /**
     * find unique instances of alpha strings, put in list, turns a&e => p4 into
     * [a,e,p4]
     *
     * @return List containing alpha strings
     */
    private List<String> FindUniqueVariables(List<String> facts, List<String> rules) {
        List<String> uniqueFacts = new ArrayList<String>();

        for (int i = 0; i < rules.size(); i++) //adding rules
        {

            String[] temp = SplitRule(rules.get(i), "=>");
            String[] subparts = SplitRule(temp[0], "&");

            //try and add each subpart to unique
            for (int y = 0; y < subparts.length; y++) {
                if (!uniqueFacts.contains(subparts[y])) {
                    uniqueFacts.add(subparts[y]);
                }
            }

            //Now add try to add the RHS of rules
            if (!uniqueFacts.contains(temp[1])) {
                uniqueFacts.add(temp[1]);
            }
        }

        for (int i = 0; i < facts.size(); i++) {
            if (!uniqueFacts.contains(facts.get(i))) {
                uniqueFacts.add(facts.get(i));
            }
        }

        return uniqueFacts;
    }

    /*
    * Source: http://stackoverflow.com/questions/10723168/generating-truth-tables-in-java
    */
    private int[][] CreateTruthTable(int length) {
        int rows = (int) Math.pow(2, length);
        int[][] result = new int[length][rows]; //array of all possible variable combinations

        for (int i = 0; i < rows; i++) {
            for (int j = length - 1; j >= 0; j--) {
                int temp = (int) ((i / Math.pow(2, j)) % 2);
                result[j][i] = temp;

            }

        }

        return result;
    }

    private List<int[]> CreateSolutionSpace(int[][] aTruthTable, List<String> uniqueStrings, List<String> aSetofFacts, List<String> aSetofRules) {
        List<int[]> SolutionSpace = new ArrayList<int[]>();

        for (int i = 0; i < aTruthTable[0].length; i++) {
            boolean invalid = false;			//this variable tracks whether a row does not comply with ruleset

            //check against all facts, facts are always single variables
            for (int k = 0; k < aSetofFacts.size(); k++) {
                //find column position of variable in TruthTable
                int temp = uniqueStrings.indexOf(aSetofFacts.get(k));

                if (aTruthTable[temp][i] == 0) //if value in TT is false
                {
                    invalid = true;			//disregard entire row
                    break;
                }
            }

            //check against all rules, only if all facts were true
            if (!invalid) {
                for (int j = 0; j < aSetofRules.size(); j++) //for each rule in rules 
                {
                    String[] tempRule = SplitRule(aSetofRules.get(j), "=>");     //splits rule into LHS and RHS around '=>' and trim each side
                    String[] subparts = SplitRule(tempRule[0], "&");             //splits LHS of rule around '&'
                    boolean check = true;

                    for (int k = 0; k < subparts.length; k++) //for each variable on LHS of rule
                    {
                        int temp = uniqueStrings.indexOf(subparts[k]);      //find column of variable in TT

                        if (aTruthTable[temp][i] == 0) //if value in TT is false, 
                        {
                            check = false;
                            break;                                      //dont need to check other variables
                        }

                    }

                    if (check) //if all variables in subpart were true
                    {
                        int temp = uniqueStrings.indexOf(tempRule[1]);	//find column of RHS of rule, find if implication is true in TT
                        if (aTruthTable[temp][i] == 0) //if value in TT is false
                        {
                            invalid = true;			//disregard entire row
                        }

                    }

                }

                //If all facts and rules hold true then add row into solution space
                if (!invalid) {

                    int[] temp = new int[aTruthTable.length];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = aTruthTable[j][i];
                    }
                    SolutionSpace.add(temp);
                }

            }

        }

        return SolutionSpace;
    }

    private void PrintSolutionSpace(List<int[]> aSolution, List<String> values) 
    {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).length() < 2) {
                System.out.print(values.get(i) + "   ");	//simple formatting
            } else {
                System.out.print(values.get(i) + "  ");	//simple formatting
            }
        }

        System.out.println();

        for (int i = 0; i < aSolution.size(); i++) {

            int[] temp = aSolution.get(i);
            for (int j = 0; j < temp.length; j++) {

                if (temp[j] == 0) {
                    System.out.print("F" + "   ");
                } else {
                    System.out.print("T" + "   ");
                }

            }
            System.out.println();
        }

    }

}
