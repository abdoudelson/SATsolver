import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
public class CNF {

	public int num_val ;
	public int num_clause;
	public HashMap<String, Integer> posOc = new HashMap<String, Integer>();
	public HashMap<String, Integer> negOc = new HashMap<>();
	public HashMap<String, Boolean> model = new HashMap<String, Boolean>();
	public  ArrayList<Clause> CNF = new ArrayList<Clause>();
	public HashMap<String, Boolean> pure = new HashMap<String, Boolean>();
	
	/**
	 * 
	 * @param st the first line of the file
	 */
	public CNF(String st) {
		String[] array = st.split(" "); 
		this.num_clause = Integer.parseInt(array[3]);
		this.num_val = Integer.parseInt(array[2]);
		for (int i = 1; i <= num_val; i++) {
			posOc.put(""+i, 0);
			negOc.put(""+i, 0);
		}
		
	}
	/**
	 * 
	 * @param c the clause to be added to the CNF
	 */
	public void addClause(Clause c) {
		this.CNF.add(c);
	}
	
	//methode qui retourne les literaux pure de la CNF 
	/**
	 * 
	 * @param posOc the list of the positive literals 
	 * @param negOc the list of the negative literals 
	 * @return
	 */
	public HashMap<String, Boolean> getPure(HashMap<String, Integer>  posOc,HashMap<String, Integer>  negOc){
		HashMap<String, Boolean> pure1 = new HashMap<String, Boolean>();
		for (String key : posOc.keySet()) {
			if(!negOc.containsKey(key)) {
				pure1.put(key,true);
			}
		}
		for (String key : posOc.keySet()) {
			if(!negOc.containsKey(key)) {
				pure1.put(key,false);
			}
		}
	
		
		return pure1;
		
	}
	//PROPAGATION OF PURE CLAUSE LIST
	/**
	 * 
	 * @param CNF the main CNF formula 
	 * @param key the key of the literal
	 * @param value the value of the literal
	 * @return
	 */
	public  ArrayList<Clause> propagetePure(ArrayList<Clause> CNF,String key,Boolean value){
		
			for (int i = 0; i < CNF.size(); i++) {
					
						Clause clause = CNF.get(i);
						if(clause == null) continue;
						if(clause.contains(key))
							if(clause.getLiteral(key) == value) {
								CNF.set(i, null);
							}
			}
						if(posOc.containsKey(key))posOc.remove(key);
						else if(negOc.containsKey(key))negOc.remove(key);
						return CNF;
	}
	
		
	//methode qui retourne les indices des clauses unitaire
	/**
	 * 
	 * @param CNF the main CNF formula
	 * @return
	 */
	public  ArrayList<Integer> setUnit(ArrayList<Clause> CNF){
		 ArrayList<Integer> unit =new ArrayList<>();
		 
		 for (int i = 0; i < CNF.size(); i++) {
				if(CNF.get(i) == null) continue;
			if(CNF.get(i).size() == 1)unit.add(i);
		}
		 
		return unit;
	}
	
	
	// PROPAGATION FUNCTION 
	 /**
	  * 
	 * @param CNF the main CNF formula 
	 * @param key the key of the literal
	 * @param value the value of the literal
	  * @return
	  */
	public  ArrayList<Clause> PROPAGATE(ArrayList<Clause> CNF,String key,boolean value){
		for (int i = 0; i < CNF.size(); i++) {
			
			Clause clause = CNF.get(i);
			if(clause == null) continue;
			if(clause.contains(key))
				if(clause.getLiteral(key) == value) {
					CNF.set(i, null);
				}else {
					clause.deleteLiteral(key, !value);
				}
		}
	
		
		return CNF;
	}
	
	
	
	/*
	 * methode qui Affiche la CNF 
	 */
	public void print() {
		int index = 0;
		
		for (Clause clause : this.CNF ) {
			System.out.println("index :" + index);
			index++;
			clause.print();
			
		}
	}
	
	//initialisation
	/**
	 * 
	 * @param br the buffer that reads the file 
	 * @throws IOException
	 */
	public void intit(BufferedReader br) throws IOException {
		String st;
		
		for (int i = 0; i < num_clause; i++) {
			st = br.readLine();
			
			Clause c = new Clause(st);

			HashMap<String,Boolean> cl = c.getClause();
			
			for (Entry<String, Boolean> l : cl.entrySet()) {
				
				if(l.getValue()) {
					int oc =posOc.get(l.getKey());
					oc++;
					posOc.put(l.getKey(), oc);
				}else{
					int oc = negOc.get(l.getKey());
					oc++;
					negOc.put(l.getKey(), oc);
				}
			}
			
			this.addClause(c);
		}
		
		posOc = sortByValue(posOc);
		negOc = sortByValue(negOc);
		pure = getPure(posOc, negOc);
	
		

		
	}
  /*
   *  END OF INIT 
   */
	
	
	/*
	 * Solving section 
	 */
	public boolean isunsatisfiable() {
		for (Clause clause : CNF) {
			if(clause != null) {
				if(clause.isEmpty())return true;
			}
		}
		return false;
	}
	
	public boolean isSatisfied() {
		for (Clause clause : CNF) {
			if(clause != null)
				{
				return false;
				}
		}
		return true;
	}
	


	public HashMap<String, Boolean> solve() {
		HashMap<String, Boolean> model = new HashMap<String , Boolean>();
		while(!isSatisfied()) {
			 ArrayList<Integer> unit = setUnit(this.CNF);
			 HashMap<String, Boolean> pure = getPure(posOc, negOc);
			 
			 
				for (String key : pure.keySet()) {
					this.CNF = propagetePure(this.CNF, key, pure.get(key));
					if(isunsatisfiable()) return null;
					model.put(key, pure.get(key));
					
					if(posOc.containsKey(key))posOc.remove(key);
					else if(negOc.containsKey(key))negOc.remove(key);
						
				}
				
				for (int key : unit) {
					Clause c = this.CNF.get(key);
					if(c == null) continue;
					String l = c.last();
					Boolean value = c.getLiteral(l);
					this.CNF = PROPAGATE(this.CNF, l, value);
					if(isunsatisfiable()) return null;
					model.put(l, value);
					if(posOc.containsKey(l))posOc.remove(l);
					else if(negOc.containsKey(l))negOc.remove(l);
						
				}
			if(isSatisfied())return model;
			
			String literal = null;
			Boolean value = null;
			if(posOc.get(last(posOc))>negOc.get(last(negOc)))
			{
				literal = last(posOc);
				value = true ;
			}
			else {
				literal = last(posOc);
				value = false;
			}
			posOc.remove(literal);
			negOc.remove(literal);
			
			this.CNF = PROPAGATE(this.CNF, literal, value);
			if(isunsatisfiable()) return null;

			model.put(literal, value);
			 	
		}
	
		return model;
		
	}
	
	  /*
	   *  END OF SOLVING SECTION 
	   */	
	
	
	
	// Utiles : Additional functions 
	// returning last index 
	/**
	 * 
	 * @param map a hashmap 
	 * @return
	 */
	public String last(HashMap<String, Integer> map) {
		String last = null;
		for (Entry<String, Integer> l : map.entrySet()) {
			last = l.getKey() ;
		}
		return last;
		
	}
	  // function to sort hashmap by values 
	/**
	 * 
	 * @param hm a hashmap to be sorted
	 * @return
	 */
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Integer> sorted = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
        	sorted.put(aa.getKey(), aa.getValue()); 
        } 
        return sorted; 
    } 
	
}
