package ai.assign.pkg2;
import java.util.*;


public abstract class InferenceMethod {
	public String code;				
	public String longName;			
	
	public abstract List<String> Solve(List<String> facts, List<String> rules, String q);
	

	public boolean CheckIfInFacts(List<String> facts, String query)
	{
		String[] temp = query.split("&");
		
		for (int i = 0; i < temp.length; i++)
		{
			for (int j = 0; j < facts.size(); j++)
			{
				if(temp[i].equals(facts.get(j)))			//if left side = fact[j]
				{
					break;										//break out of this loop (j)
				}
				if(j == (facts.size()-1))
					return false;								//went through all facts and couldnt match to this subpart
			}
		}
		return true;
	}

	
	public String[] SplitRule(String rule,String aString)
	{
		String[] trimmed = rule.split(aString);
		
		for (int i=0; i < trimmed.length; i++)
			
			trimmed[i] = trimmed[i].trim();	

		return trimmed;
	}
}