/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * @author nikhillo This class represents a subclass of a Dictionary class that
 *         is local to a single thread. All methods in this class are assumed
 *         thread safe for the same reason.
 */
public class LocalDictionary extends Dictionary {
	Map<String, Integer> myMap;

	Properties props;
	INDEXFIELD fld;

	/**
	 * Public default constructor
	 * 
	 * @param props
	 *            : The properties file
	 * @param field
	 *            : The field being indexed by this dictionary
	 */
	public LocalDictionary(Properties props, INDEXFIELD field) {
		super(props, field);
		this.props = props;
		this.fld = field;
		myMap = new HashMap<String, Integer>();
		super.setMyMap(myMap);

	}

	/**
	 * Method to lookup and possibly add a mapping for the given value in the
	 * dictionary. The class should first try and find the given value within
	 * its dictionary. If found, it should return its id (Or hash value). If not
	 * found, it should create an entry and return the newly created id.
	 * 
	 * @param value
	 *            : The value to be looked up
	 * @return The id as explained above.
	 */
	public int lookup(String value) {
		if (myMap.containsKey(value))
			return myMap.get(value);
		else {
			Random r = new Random();
			int rnd = r.nextInt();
			if(rnd<0)
				rnd = Math.abs(rnd);
			myMap.put(value, rnd);
			return rnd;
		}
	}
}
