import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class Solver {

	public static void main(String[] args) throws IOException 
	{
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter the path to the CNF file : ");
		  System.out.println("\n");
		String f = reader.next();
		
			  @SuppressWarnings("resource")
			  BufferedReader br = new BufferedReader(new FileReader(f)); 
			  String st = br.readLine(); 
				String[] first = st.split(" "); 
				
			  CNF cnf = new CNF(st);
			  cnf.intit(br); 
			  //cnf.print();
			  
			  HashMap<String, Boolean> model = cnf.solve();
			  
			  
			  ArrayList<Integer> solution = new ArrayList<Integer>();
			  for (int i = 0; i < Integer.parseInt(first[2])+1; i++) {
				solution.add(-(i+1));
			 }
			
			  if(model == null) {
				
				  System.out.println("s UNSATISFIABLE");
				  // System.out.println("Time : " + duration+" x 10^-3 ms");
				  System.out.println("\n");
			  }
			  else {
				  System.out.println("s SATISFIABLE : ");
				  System.out.print("v  ");
				  for (Entry<String, Boolean> l : model.entrySet()) {
					  if(l.getValue())
						  solution.set(Integer.parseInt(l.getKey())-1, Integer.parseInt(l.getKey()));
					}
				  for (int i = 0; i < solution.size(); i++) {
					System.out.print(solution.get(i)+" ");
				}
				//  System.out.println("Time : " + duration +"ms  x 10^-3 ms");
				  System.out.println("\n");
			  }
			 
			  System.out.println();
			  
			  
		}
		

	

}