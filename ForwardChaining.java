package ai.assign.pkg2;
import java.util.ArrayList;
import java.util.List;

public class ForwardChaining extends InferenceMethod {
	
	public ForwardChaining()
	{
		code = "FC";
		longName = "Forward Chaining";
	}

	
	@Override
	public List<String> Solve(List<String> Facts, List<String> Rules, String q)
	{
	
		for (int i = 0; i < Rules.size(); i++)						//iterate through all the rules 
		{
			String[] tempRule = SplitRule(Rules.get(i),"=>"); 		//Seperate rules into LHS (Left Hand Side) and RHS (Right Hand Side) around every 
																	//occurance of '=>' and then trim each side
			
			if(CheckIfInFacts(Facts,tempRule[0]))					//Check if left side of Rule is in facts
			{
				Facts.add(tempRule[1]); 							//put right side of rule in Facts
				
				if(q.equals(tempRule[1]))							//if RHS of rule is the ASK
				{													//Solution is found
					return Facts;
				}
			}
				
		}
	
		return null;												//ASK cannot be found (inferred)
	}
	


}
