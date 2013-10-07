/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.InflaterInputStream;

/**
 * @author nikhillo This class is used to introspect a given index The
 *         expectation is the class should be able to read the index and all
 *         associated dictionaries.
 */
public class IndexReader {
	/**
	 * Constructor to create an instance
	 * 
	 * @param props
	 *            : The properties file
	 * @param field
	 *            : The index field whose index is to be read
	 */
	private INDEXFIELD field;

	private String tempDir;

	private int occurances;
	private String authorIndexFile = "Author_Index.dat";
	private String termIndexFile = "Term_Index.dat";
	private String categoryIndexFile = "Category_Index.dat";
	private String linkIndexFile = "Link_Index.dat";

	private String dictionaryFile = "Dictionary.dat";
	private String titleTermFile = "Title_Term_Index.dat";
	private Map<String, Integer> dictionary = new HashMap<String, Integer>();
	private Map<String, Integer> titleTermMap = new HashMap<String, Integer>();

	private List<SObject> authorList = new ArrayList<SObject>();
	private List<SObject> termList = new ArrayList<SObject>();
	private List<SObject> categoryList = new ArrayList<SObject>();
	private List<SObject> linkList = new ArrayList<SObject>();
	private List<TitleTerm> titleTermList = new ArrayList<TitleTerm>();
	private String INDEX;

	@SuppressWarnings("unchecked")
	public IndexReader(Properties props, INDEXFIELD field) {
		this.field = field;
		this.tempDir = props.getProperty("root.dir");
		try {

			ObjectInputStream dicOos = new ObjectInputStream(
					new InflaterInputStream(new FileInputStream(this.tempDir
							+ dictionaryFile)));
			dictionary = (Map<String, Integer>) dicOos.readObject();
			dicOos.close();

			ObjectInputStream termOos = new ObjectInputStream(
					new InflaterInputStream(new FileInputStream(this.tempDir
							+ titleTermFile)));
			titleTermMap = (Map<String, Integer>) termOos.readObject();

			termOos.close();
			ValueComparator bvc = new ValueComparator(titleTermMap);
			TreeMap<String, Integer> sortedTitleTermMap = new TreeMap<String, Integer>(
					bvc);

			sortedTitleTermMap.putAll(titleTermMap);
			List<String> a = new ArrayList<String>();
			for (Map.Entry<String, Integer> entry : sortedTitleTermMap
					.entrySet()) {
				a.add(entry.getValue().toString());
			}

			for (int i = 0; i < a.size(); i++) {
				if (i < a.size() - 1)
					a.set(i, a.get(i) + " " + a.get(i + 1));
				else
					a.set(i, a.get(i) + " " + Integer.MAX_VALUE);
			}
			int i = 0;
			for (Map.Entry<String, Integer> entry : sortedTitleTermMap
					.entrySet()) {
				String[] x = a.get(i).split(" ");
				TitleTerm m = new TitleTerm(entry.getKey(),
						Integer.parseInt(x[0]), Integer.parseInt(x[1]));
				titleTermList.add(m);
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (field) {
		case AUTHOR: {

			this.authorIndexFile = this.tempDir + this.authorIndexFile;
			try {
				ObjectInputStream oos = new ObjectInputStream(
						new InflaterInputStream(new FileInputStream(
								authorIndexFile)));
				authorList = (List<SObject>) oos.readObject();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

		case TERM: {

			this.termIndexFile = this.tempDir + this.termIndexFile;
			try {
				ObjectInputStream oos = new ObjectInputStream(
						new InflaterInputStream(new FileInputStream(
								termIndexFile)));
				termList = (List<SObject>) oos.readObject();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case CATEGORY: {

			this.categoryIndexFile = this.tempDir + this.categoryIndexFile;
			try {
				ObjectInputStream oos = new ObjectInputStream(
						new InflaterInputStream(new FileInputStream(
								categoryIndexFile)));
				categoryList = (List<SObject>) oos.readObject();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case LINK: {

			this.linkIndexFile = this.tempDir + this.linkIndexFile;
			try {
				ObjectInputStream oos = new ObjectInputStream(
						new InflaterInputStream(new FileInputStream(
								linkIndexFile)));
				linkList = (List<SObject>) oos.readObject();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		}

	}

	/**
	 * Method to get the total number of terms in the key dictionary
	 * 
	 * @return The total number of terms as above
	 */
	public int getTotalKeyTerms() {
		Set<String> s = new HashSet<String>();
		switch (field) {
		case AUTHOR: {
			for (SObject obj : authorList) {
				s.add(obj.getKeyId());
			}
			return s.size();
		}
		case TERM: {
			for (SObject obj : termList) {
				s.add(obj.getKeyId());
			}
			return s.size();
		}
		case CATEGORY: {
			for (SObject obj : categoryList) {
				s.add(obj.getKeyId());
			}
			return s.size();
		}
		case LINK: {
			for (SObject obj : linkList) {
				s.add(obj.getKeyId());
			}
			return s.size();
		}
		default:
			return 0;
		}

	}

	/**
	 * Method to get the total number of terms in the value dictionary
	 * 
	 * @return The total number of terms as above
	 */
	public int getTotalValueTerms() {
		Set<Integer> s = new HashSet<Integer>();
		switch (field) {
		case AUTHOR: {
			for (SObject obj : authorList) {
				s.add(obj.getValue());
			}
			return s.size();
		}
		case TERM: {
			for (SObject obj : termList) {
				s.add(obj.getValue());
			}
			return s.size();
		}
		case CATEGORY: {
			for (SObject obj : categoryList) {
				s.add(obj.getValue());
			}
			return s.size();
		}
		case LINK: {
			for (SObject obj : linkList) {
				s.add(obj.getValue());
			}
			return s.size();
		}
		default:
			return 0;
		}

	}

	/**
	 * Method to retrieve the postings list for a given dictionary term
	 * 
	 * @param key
	 *            : The dictionary term to be queried
	 * @return The postings list with the value term as the key and the number
	 *         of occurrences as value. An ordering is not expected on the map
	 */
	public Map<String, Integer> getPostings(String key) {

		Map<String, Integer> postingsMap = new HashMap<String, Integer>();

		switch (field) {
		case AUTHOR: {

			for (SObject auList : authorList) {

				if (key.equalsIgnoreCase(auList.getKeyId()))
					postingsMap.put(String.valueOf(auList.getValue()),
							auList.getOccurences());
			}

			return postingsMap;
		}
		case TERM: {

			for (SObject auList : termList) {

				if (key.equalsIgnoreCase(auList.getKeyId()))
					postingsMap.put(String.valueOf(auList.getValue()),
							auList.getOccurences());
			}

			return postingsMap;
		}
		case CATEGORY: {

			for (SObject auList : categoryList) {

				if (key.equalsIgnoreCase(auList.getKeyId()))
					postingsMap.put(String.valueOf(auList.getValue()),
							auList.getOccurences());
			}

			return postingsMap;
		}
		case LINK: {

			for (SObject auList : linkList) {

				if (key.equalsIgnoreCase(auList.getKeyId()))
					postingsMap.put(String.valueOf(auList.getValue()),
							auList.getOccurences());
			}

			return postingsMap;
		}
		default:

			return postingsMap;

		}
	}

	/**
	 * Method to get the top k key terms from the given index The top here
	 * refers to the largest size of postings.
	 * 
	 * @param k
	 *            : The number of postings list requested
	 * @return An ordered collection of dictionary terms that satisfy the
	 *         requirement If k is more than the total size of the index, return
	 *         the full index and don't pad the collection. Return null in case
	 *         of an error or invalid inputs
	 */
	public Collection<String> getTopK(int k) {
		List<String> s = new ArrayList<String>();
		switch (field) {
		case AUTHOR: {
			int listSize = authorList.size();

			for (SObject obj : authorList) {
				s.add(obj.getKeyId());
			}

			Collections.sort(s);
			if (k > listSize)
				return s;
			else
				return s.subList(0, k);
		}
		case TERM: {
			int listSize = termList.size();

			for (SObject obj : termList) {
				s.add(obj.getKeyId());
			}

			if (k > listSize)
				return s;
			else
				return s.subList(0, k);
		}
		case CATEGORY: {
			int listSize = categoryList.size();

			for (SObject obj : categoryList) {
				s.add(obj.getKeyId());
			}

			if (k > listSize)
				return s;
			else
				return s.subList(0, k);
		}
		case LINK: {
			int listSize = linkList.size();

			for (SObject obj : linkList) {
				s.add(obj.getKeyId());
			}

			if (k > listSize)
				return s;
			else
				return s.subList(0, k);
		}
		default:
			return null;
		}

	}

	/**
	 * Method to execute a boolean AND query on the index
	 * 
	 * @param terms
	 *            The terms to be queried on
	 * @return An ordered map containing the results of the query The key is the
	 *         value field of the dictionary and the value is the sum of
	 *         occurrences across the different postings. The value with the
	 *         highest cumulative count should be the first entry in the map.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> query(String... terms) {
		List<QueryObject> a = new ArrayList<QueryObject>();
		List<String> xy = Arrays.asList(terms);
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		switch (field) {
		case AUTHOR: {
			for (SObject obj : authorList) {
				for (String term : terms) {
					if (obj.getKeyId().equalsIgnoreCase(term) == true) {
						QueryObject ob = new QueryObject(obj.getKeyId(),
								obj.getValue(), obj.getOccurences());
						a.add(ob);
					}
				}
			}

			Map<Integer, PairedObject> map = new HashMap<Integer, PairedObject>();
			for (QueryObject obj : a) {
				PairedObject pairedObject;
				if (map.containsKey(obj.getValue())) {
					pairedObject = map.get(obj.getValue());
				} else {
					pairedObject = new PairedObject();
					map.put(obj.getValue(), pairedObject);
				}
				pairedObject.adV1(obj.getKey());
				pairedObject.setAdd(obj.getOccurances());
			}
			for (Entry<Integer, PairedObject> entry : map.entrySet()) {
				boolean found = true;
				for (String reqKey : xy) {
					found &= entry.getValue().getKeys().contains(reqKey);
				}
				if (found) {
					myMap.put(String.valueOf(entry.getKey()), entry.getValue()
							.getAdd());
				}
			}
			Map<String, Integer> newMap = new HashMap<String, Integer>();
			for (int i = 0; i < titleTermList.size(); i++) {
				for (Map.Entry<String, Integer> entry : myMap.entrySet()) {

					int temp1 = Integer.parseInt(entry.getKey());

					int lowerBound = titleTermList.get(i).getLowerBound();
					int upperBound = titleTermList.get(i).getUpperBound();

					if (temp1 > lowerBound && temp1 < upperBound)
						newMap.put(
								titleTermList.get(i).getTitle()
										.replaceAll("#", ""), entry.getValue());
				}
			}
			return sortByValue(newMap);
		}
		case TERM: {
			for (SObject obj : termList) {
				for (String term : terms) {
					if (obj.getKeyId().equalsIgnoreCase(term) == true) {
						QueryObject ob = new QueryObject(obj.getKeyId(),
								obj.getValue(), obj.getOccurences());
						a.add(ob);
					}
				}
			}

			Map<Integer, PairedObject> map = new HashMap<Integer, PairedObject>();
			for (QueryObject obj : a) {
				PairedObject pairedObject;
				if (map.containsKey(obj.getValue())) {
					pairedObject = map.get(obj.getValue());
				} else {
					pairedObject = new PairedObject();
					map.put(obj.getValue(), pairedObject);
				}
				pairedObject.adV1(obj.getKey());
				pairedObject.setAdd(obj.getOccurances());
			}
			for (Entry<Integer, PairedObject> entry : map.entrySet()) {
				boolean found = true;
				for (String reqKey : xy) {
					found &= entry.getValue().getKeys().contains(reqKey);
				}
				if (found) {
					myMap.put(String.valueOf(entry.getKey()), entry.getValue()
							.getAdd());
				}
			}

			// System.out.println(myMap);
			// for (TitleTerm t : titleTermList) {
			// System.out.println(t.getTitle() + " " +t.getLowerBound() + " "+
			// t.getUpperBound());
			// }
			Map<String, Integer> newMap = new HashMap<String, Integer>();
			for (int i = 0; i < titleTermList.size(); i++) {
				for (Map.Entry<String, Integer> entry : myMap.entrySet()) {

					int temp1 = Integer.parseInt(entry.getKey());

					int lowerBound = titleTermList.get(i).getLowerBound();
					int upperBound = titleTermList.get(i).getUpperBound();

					if (temp1 > lowerBound && temp1 < upperBound)
						newMap.put(
								titleTermList.get(i).getTitle()
										.replaceAll("#", ""), entry.getValue());
				}
			}
			return sortByValue(newMap);
		}
		case CATEGORY: {
			for (SObject obj : categoryList) {
				for (String term : terms) {
					if (obj.getKeyId().equalsIgnoreCase(term) == true) {
						QueryObject ob = new QueryObject(obj.getKeyId(),
								obj.getValue(), obj.getOccurences());
						a.add(ob);
					}
				}
			}

			Map<Integer, PairedObject> map = new HashMap<Integer, PairedObject>();
			for (QueryObject obj : a) {
				PairedObject pairedObject;
				if (map.containsKey(obj.getValue())) {
					pairedObject = map.get(obj.getValue());
				} else {
					pairedObject = new PairedObject();
					map.put(obj.getValue(), pairedObject);
				}
				pairedObject.adV1(obj.getKey());
				pairedObject.setAdd(obj.getOccurances());
			}
			for (Entry<Integer, PairedObject> entry : map.entrySet()) {
				boolean found = true;
				for (String reqKey : xy) {
					found &= entry.getValue().getKeys().contains(reqKey);
				}
				if (found) {
					myMap.put(String.valueOf(entry.getKey()), entry.getValue()
							.getAdd());
				}
			}
			Map<String, Integer> newMap = new HashMap<String, Integer>();
			for (int i = 0; i < titleTermList.size(); i++) {
				for (Map.Entry<String, Integer> entry : myMap.entrySet()) {

					int temp1 = Integer.parseInt(entry.getKey());

					int lowerBound = titleTermList.get(i).getLowerBound();
					int upperBound = titleTermList.get(i).getUpperBound();

					if (temp1 > lowerBound && temp1 < upperBound)
						newMap.put(
								titleTermList.get(i).getTitle()
										.replaceAll("#", ""), entry.getValue());
				}
			}
			return sortByValue(newMap);
		}
		case LINK: {
			for (SObject obj : linkList) {
				for (String term : terms) {
					if (obj.getKeyId().equalsIgnoreCase(term) == true) {
						QueryObject ob = new QueryObject(obj.getKeyId(),
								obj.getValue(), obj.getOccurences());
						a.add(ob);
					}
				}
			}

			Map<Integer, PairedObject> map = new HashMap<Integer, PairedObject>();
			for (QueryObject obj : a) {
				PairedObject pairedObject;
				if (map.containsKey(obj.getValue())) {
					pairedObject = map.get(obj.getValue());
				} else {
					pairedObject = new PairedObject();
					map.put(obj.getValue(), pairedObject);
				}
				pairedObject.adV1(obj.getKey());
				pairedObject.setAdd(obj.getOccurances());
			}
			for (Entry<Integer, PairedObject> entry : map.entrySet()) {
				boolean found = true;
				for (String reqKey : xy) {
					found &= entry.getValue().getKeys().contains(reqKey);
				}
				if (found) {
					myMap.put(String.valueOf(entry.getKey()), entry.getValue()
							.getAdd());
				}
			}
			Map<String, Integer> newMap = new HashMap<String, Integer>();
			for (int i = 0; i < titleTermList.size(); i++) {
				for (Map.Entry<String, Integer> entry : myMap.entrySet()) {

					int temp1 = Integer.parseInt(entry.getKey());

					int lowerBound = titleTermList.get(i).getLowerBound();
					int upperBound = titleTermList.get(i).getUpperBound();

					if (temp1 > lowerBound && temp1 < upperBound)
						newMap.put(
								titleTermList.get(i).getTitle()
										.replaceAll("#", ""), entry.getValue());
				}
			}
			return sortByValue(newMap);
		}
		default:
			return null;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static Map sortByValue(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o2, Object o1) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}

class ValueComparator implements Comparator<String> {

	Map<String, Integer> base;

	public ValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(String a, String b) {
		if (base.get(a) <= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}
