/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.DeflaterOutputStream;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;

/**
 * @author nikhillo An abstract class that represents a dictionary object for a
 *         given index
 */
public abstract class Dictionary implements Writeable {
	Map<String, Integer> myMap;
	Map<String, Integer> titleMap;
	
	public static int titleCount = 0;
	private WikipediaDocument document;
	public static int count = 0;
	Properties props;
	INDEXFIELD fld;
	

	public Map<String, Integer> getMyMap() {
		return myMap;
	}

	public void setMyMap(Map<String, Integer> myMap) {
		this.myMap = myMap;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public INDEXFIELD getFld() {
		return fld;
	}

	public void setFld(INDEXFIELD fld) {
		this.fld = fld;
	}

	public Dictionary(Properties props, INDEXFIELD field) {
		this.props = props;
		this.fld = field;
		titleMap = new HashMap<String, Integer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new DeflaterOutputStream(new FileOutputStream(props
							.getProperty("root.dir") + "Dictionary.dat")));
			oos.writeObject(myMap);
			oos.close();
			
			ObjectOutputStream oos1 = new ObjectOutputStream(
					new DeflaterOutputStream(new FileOutputStream(props
							.getProperty("root.dir") + "Title_Term_Index.dat")));
			oos1.writeObject(titleMap);
			oos1.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("inside dictionary write method");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		// TODO Implement this method

	}

	/**
	 * Method to check if the given value exists in the dictionary or not Unlike
	 * the subclassed lookup methods, it only checks if the value exists and
	 * does not change the underlying data structure
	 * 
	 * @param value
	 *            : The value to be looked up
	 * @return true if found, false otherwise
	 */
	public boolean exists(String value) {
		if (myMap.containsKey(value))
			return true;
		else
			return false;
	}

	/**
	 * MEthod to lookup a given string from the dictionary. The query string can
	 * be an exact match or have wild cards (* and ?) Must be implemented ONLY
	 * AS A BONUS
	 * 
	 * @param queryStr
	 *            : The query string to be searched
	 * @return A collection of ordered strings enumerating all matches if found
	 *         null if no match is found
	 */
	public Collection<String> query(String queryStr) {
		List<String> s = new ArrayList<String>();
		if (queryStr.contains("*"))
			queryStr = queryStr.replaceAll("\\*", ".*");
		if (queryStr.contains(""))
			queryStr = queryStr.replaceAll("\\?", ".");

		for (Map.Entry<String, Integer> entry : myMap.entrySet()) {
			if (entry.getKey().matches(queryStr))
				s.add(entry.getKey());
		}
		if (s.isEmpty())
			return null;
		else
			return s;
	}

	/**
	 * Method to get the total number of terms in the dictionary
	 * 
	 * @return The size of the dictionary
	 */
	public int getTotalTerms() {
		return myMap.size();
	}
}
