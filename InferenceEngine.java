package ai.assign.pkg2;
import java.util.ArrayList;
import java.util.List;


public class InferenceEngine {

	private List<String> Facts = null;
	private List<String> Rules = null;
	private String q = null;
	
	public InferenceEngine(String kb, String query)
	{
		Facts = new ArrayList<String>();
		Rules = new ArrayList<String>();
		
		//initialize rules and facts
		CreateKnowledgeBase(kb);
				
		//initialize question
		q = query;
		
	}
	
	public void CreateKnowledgeBase(String myKB)
	{
		String[] newKB = myKB.split(";"); //split around ';'
		
		for (int i = 0; i < newKB.length; i++)
		{
			if (newKB[i].contains("="))
			{
				Rules.add(newKB[i].trim());
				
			}
			else if (!newKB[i].contains("&") || !newKB[i].contains(">"))
			{
				Facts.add(newKB[i].trim());
			}
			else
			{
				System.out.println("ERROR IN KNOWLEDGE BASE");
				System.out.println(myKB);
				System.out.println(newKB[i]);
				System.exit(1);
			}
		}
	}
	
	public List<String> Solve(InferenceMethod aMethod)
	{
		return aMethod.Solve(Facts, Rules, q);
		
	}
	
}
