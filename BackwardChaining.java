package ai.assign.pkg2;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Bentley Turner
 *
 */
public class BackwardChaining extends InferenceMethod {
	
	private List<String> inferred;
	
	public BackwardChaining()
	{
		inferred = new ArrayList<String>();
		code = "BC";
		longName = "Backward Chaining";
	}

	
	@Override
	public List<String> Solve(List<String> Facts, List<String> Rules, String q)
	{
		if (!CheckIfInFacts(Facts,q))									//Check if query is not in facts
		{	
			for (int i=0;i<Rules.size();i++)							//iterate through rules until q is found on RHS of a rule
			{														
				String[] tempRules = SplitRule(Rules.get(i),"=>");                              //splits rule into LHS and RHS around '=>'
				
				if(tempRules[1].equals(q))							//if q is equal to right side of a rule
				{
					String[] temp = SplitRule(tempRules[0],"&");                            //LHS does contain '&'
					for (int j =0;j<temp.length;j++)
					{
						Solve(Facts,Rules,temp[j]);					//solve for each variable on LHS
					}
				}
			}
			
		}
		inferred.add(q);
		return inferred;
	}
}