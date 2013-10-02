/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * @author nikhillo This class is used to write an index to the disk
 * 
 */
public class IndexWriter implements Writeable {

	SObject obj;
	ObjectOutputStream oos;

	static List<SObject> authorList = new ArrayList<SObject>();
	static List<SObject> termList = new ArrayList<SObject>();
	static List<SObject> categoryList = new ArrayList<SObject>();
	static List<SObject> linkList = new ArrayList<SObject>();

	private final String authorIndex = "/Users/naren/folder/author(compressed).txt";
	private final String termIndex = "/Users/naren/folder/term(compressed).txt";
	private final String categoryIndex = "/Users/naren/folder/category(compressed).txt";
	private final String linkIndex = "/Users/naren/folder/link(compressed).txt";

	private INDEXFIELD field;

	/**
	 * Constructor that assumes the underlying index is inverted Every index
	 * (inverted or forward), has a key field and the value field The key field
	 * is the field on which the postings are aggregated The value field is the
	 * field whose postings we are accumulating For term index for example: Key:
	 * Term (or term id) - referenced by TERM INDEXFIELD Value: Document (or
	 * document id) - referenced by LINK INDEXFIELD
	 * 
	 * @param props
	 *            : The Properties file
	 * @param keyField
	 *            : The index field that is the key for this index
	 * @param valueField
	 *            : The index field that is the value for this index
	 */
	public IndexWriter(Properties props, INDEXFIELD keyField,
			INDEXFIELD valueField) {
		this.field = keyField;
		System.out.println(keyField);
	}

	/**
	 * Overloaded constructor that allows specifying the index type as inverted
	 * or forward Every index (inverted or forward), has a key field and the
	 * value field The key field is the field on which the postings are
	 * aggregated The value field is the field whose postings we are
	 * accumulating For term index for example: Key: Term (or term id) -
	 * referenced by TERM INDEXFIELD Value: Document (or document id) -
	 * referenced by LINK INDEXFIELD
	 * 
	 * @param props
	 *            : The Properties file
	 * @param keyField
	 *            : The index field that is the key for this index
	 * @param valueField
	 *            : The index field that is the value for this index
	 * @param isForward
	 *            : true if the index is a forward index, false if inverted
	 */
	public IndexWriter(Properties props, INDEXFIELD keyField,
			INDEXFIELD valueField, boolean isForward) {
System.out.println(keyField);
		this.field = keyField;
	}

	/**
	 * Method to make the writer self aware of the current partition it is
	 * handling Applicable only for distributed indexes.
	 * 
	 * @param pnum
	 *            : The partition number
	 */
	public void setPartitionNumber(int pnum) {
		// TODO: Optionally implement this method
	}

	/**
	 * Method to add a given key - value mapping to the index
	 * 
	 * @param keyId
	 *            : The id for the key field, pre-converted
	 * @param valueId
	 *            : The id for the value field, pre-converted
	 * @param numOccurances
	 *            : Number of times the value field is referenced by the key
	 *            field. Ignore if a forward index
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, int valueId, int numOccurances)
			throws IndexerException {
		System.out.println("inside add to index 1");

		// TODO: Implement this method
	}

	/**
	 * Method to add a given key - value mapping to the index
	 * 
	 * @param keyId
	 *            : The id for the key field, pre-converted
	 * @param value
	 *            : The value for the value field
	 * @param numOccurances
	 *            : Number of times the value field is referenced by the key
	 *            field. Ignore if a forward index
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, String value, int numOccurances)
			throws IndexerException {
		System.out.println("inside add to index 2");
	}

	/**
	 * Method to add a given key - value mapping to the index
	 * 
	 * @param key
	 *            : The key for the key field
	 * @param valueId
	 *            : The id for the value field, pre-converted
	 * @param numOccurances
	 *            : Number of times the value field is referenced by the key
	 *            field. Ignore if a forward index
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */
	public void addToIndex(String key, int valueId, int numOccurances)
			throws IndexerException {
		switch (this.field) {
		case TERM: {
			SObject obj = new SObject();
			obj.setKeyId(key);
			obj.setOccurences(numOccurances);
			obj.setValue(valueId);

			termList.add(obj);
			break;
		}
		case AUTHOR: {
			SObject obj = new SObject();
			obj.setKeyId(key);
			obj.setOccurences(numOccurances);
			obj.setValue(valueId);

			authorList.add(obj);
			break;
		}
		case CATEGORY: {
			SObject obj = new SObject();
			obj.setKeyId(key);
			obj.setOccurences(numOccurances);
			obj.setValue(valueId);

			categoryList.add(obj);
			break;
		}
		case LINK:
			// System.out.println("case link");
			break;
		}

	}

	/**
	 * Method to add a given key - value mapping to the index
	 * 
	 * @param key
	 *            : The key for the key field
	 * @param value
	 *            : The value for the value field
	 * @param numOccurances
	 *            : Number of times the value field is referenced by the key
	 *            field. Ignore if a forward index
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */
	public void addToIndex(String key, String value, int numOccurances)
			throws IndexerException {
		System.out.println("inside add to index3");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new DeflaterOutputStream(new FileOutputStream(authorIndex)));
			oos.writeObject(authorList);
			oos.close();

			ObjectOutputStream oos1 = new ObjectOutputStream(
					new DeflaterOutputStream(new FileOutputStream(termIndex)));
			oos1.writeObject(termList);
			oos1.close();

			ObjectOutputStream oos2 = new ObjectOutputStream(
					new DeflaterOutputStream(
							new FileOutputStream(categoryIndex)));
			oos2.writeObject(categoryList);
			oos2.close();

			ObjectOutputStream oos3 = new ObjectOutputStream(
					new DeflaterOutputStream(new FileOutputStream(linkIndex)));
			oos3.writeObject(linkList);
			oos3.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		// TODO Implement this method

	}

}
