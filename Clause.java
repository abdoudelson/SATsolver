import java.util.HashMap;
import java.util.Map.Entry;

public class Clause {
	
	HashMap<String, Boolean> clause = new HashMap<String , Boolean>();
	/**
	 * 
	 * @param st String line to be spliced
	 */
	public Clause(String st){
		String[] array = st.split(" "); 
		
		for (String literal : array) {
			if(array.length > 1 && Integer.parseInt(literal) != 0)
				if(Integer.parseInt(literal) > 0) {
					addLiteral(""+literal, true);
				}else {
					addLiteral(""+(-1*Integer.parseInt(literal)), false);
				}
		}
	//	System.out.println(" | ");
	}
	/**
	 * 
	 * @param key the key of the literal
	 * @param polarity the value of the literal
	 */
	public void addLiteral(String key,Boolean polarity) {
		clause.put(key, polarity);
		//System.out.print(key+" "+polarity+"  |  ");
	}
	/**
	 * 
	 * @param key the key of the literal
	 * @param polarity the value of the literal
	 */
	public void deleteLiteral(String key,boolean value) {
		clause.remove(key,value);
	}
	
	public HashMap<String,Boolean> getClause() {
		return clause;
	}
	public boolean isEmpty() {
		return clause.isEmpty();
	}
	public int size() {
		return clause.size();
	}
	/**
	 * 
	 * @param key the key of the literal
	 * @return
	 */
	public boolean getLiteral(String key) {
		return clause.get(key);
	}
	/**
	 * 
	 *  * @param key the key of the literal
	 * @return
	 */
	public boolean contains(String key) {
		return clause.containsKey(key);
	}
	public String last() {
		String last = null;
		for (Entry<String, Boolean> l : clause.entrySet()) {
			last = l.getKey() ;
		}
		return last;
		
	}
	public void print() {
	System.out.println('\t');
		for (Entry<String, Boolean> c : clause.entrySet()) {
			System.out.print(""+c.getKey()+ " " + c.getValue()+" | ");
		}
		System.out.println('\t');
	}

}
