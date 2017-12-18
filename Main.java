package ai.assign.pkg2;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Main {

		//the number of methods programmed into the Inference Method
		public static final int METHOD_COUNT = 3;
		public static InferenceEngine iEngine;
		public static InferenceMethod[] lMethods;

		
		public static void main(String[] args)
		{
			
			//arguements for Running
			// [0] - Algorithm to use 
			// [1] - filename containing knowledgebase and query
	
			InitMethods();
						
		
			iEngine = readProblemFile(args[1]);
			
			String method = args[0];
			InferenceMethod thisMethod = null;
			
			
			for(int i = 0; i < METHOD_COUNT; i++)
			{
				//do they want this one?
				if(lMethods[i].code.equals(method) )
				{
					//yes, use this method.
					thisMethod = lMethods[i];
				}
			}
			
			//Has the method been implemented?
			if(thisMethod == null)
			{
				//No, give an error
				System.out.println("Search method identified by " + method + " not implemented. Methods are case sensitive.");
				System.exit(1);
			}
			
			//Solve the puzzle, using the method that the user chose
			List<String>  thisSolution = iEngine.Solve(thisMethod);
			
			
			if(thisSolution == null)
			{
				//No solution found
				System.out.println("NO");
			}
			else
			{
				//Solution Found!
				System.out.print("Yes: ");
				for(int j = 0; j < thisSolution.size(); j++)
				{
					if(!(j == (thisSolution.size()-1)))
						System.out.print(thisSolution.get(j).toString() + ", ");
					else
						System.out.print(thisSolution.get(j).toString());	
				}
				System.out.println();
			}
	
			//Reset the search method for next use.
			System.exit(0);
		}
		
		private static void InitMethods()
		{
			lMethods = new InferenceMethod[METHOD_COUNT];
			lMethods[0] = new ForwardChaining();
			lMethods[1] = new BackwardChaining();
			lMethods[2] = new TruthTable();
			
		}
	
		private static InferenceEngine readProblemFile(String fileName) // this allow only one puzzle to be specified in a problem file 
		{
			
			try
			{
				//create file reading objects
				FileReader reader = new FileReader(fileName);
				BufferedReader breader = new BufferedReader(reader);
				InferenceEngine result;
				
								
				//read txt file into InferenceEngine
				String checkformat = breader.readLine();
				if (checkformat.equalsIgnoreCase("TELL"))	//check fomatting
				{
					
					String kb = breader.readLine();
					checkformat = breader.readLine();
					if (checkformat.equalsIgnoreCase("ASK"))	//check formatting
					{
						String q = breader.readLine();
						
						result = new InferenceEngine(kb,q);	//Create InferenceEngine
						breader.close();
						return result;
					}
				}
				breader.close();
				System.out.println("Formatting Error in file: " + fileName);
				System.exit(1);
				
			}
			catch(FileNotFoundException ex)
			{
				//The file didn't exist, show an error
				System.out.println("Error: File \"" + fileName + "\" not found.");
				System.out.println("Please check the path to the file.");
				System.exit(1);
			}
			catch(IOException ex)
			{
				//There was an IO error, show and error message
				System.out.println("Error in reading \"" + fileName + "\". Try closing it and programs that may be accessing it.");
				System.out.println("If you're accessing this file over a network, try making a local copy.");
				System.exit(1);
			}
			
			//this code should be unreachable. This statement is simply to satisfy Eclipse.
			return null;
		}
		
}
