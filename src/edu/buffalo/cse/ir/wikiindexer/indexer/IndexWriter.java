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

	private static Object sharedLock = new Object();
	private String authorIndexFile = "Author_Index.dat";
	private String termIndexFile = "Term_Index.dat";
	private String categoryIndexFile = "Category_Index.dat";
	private String linkIndexFile = "Link_Index.dat";

	private INDEXFIELD field;

	private String tempDir;

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

		this.tempDir = props.getProperty("tmp.dir");

		this.authorIndexFile = this.tempDir + this.authorIndexFile;
		this.categoryIndexFile = this.tempDir + this.categoryIndexFile;
		this.termIndexFile = this.tempDir + this.termIndexFile;
		this.linkIndexFile = this.tempDir + this.linkIndexFile;
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
		this.field = keyField;

		this.tempDir = props.getProperty("tmp.dir");

		this.authorIndexFile = this.tempDir + this.authorIndexFile;
		this.categoryIndexFile = this.tempDir + this.categoryIndexFile;
		this.termIndexFile = this.tempDir + this.termIndexFile;
		this.linkIndexFile = this.tempDir + this.linkIndexFile;
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
		synchronized (sharedLock) {
			switch (this.field) {
			case TERM: {
				SObject obj = new SObject(String.valueOf(keyId), valueId,
						numOccurances);

				termList.add(obj);
				break;
			}
			case AUTHOR: {
				SObject obj = new SObject(String.valueOf(keyId), valueId,
						numOccurances);
				authorList.add(obj);
				System.out.println(keyId+" " + valueId+" " + numOccurances);
				break;
			}
			case CATEGORY: {
				SObject obj = new SObject(String.valueOf(keyId), valueId,
						numOccurances);
				categoryList.add(obj);
				break;
			}
			case LINK:
				SObject obj = new SObject(String.valueOf(keyId), valueId,
						numOccurances);
				linkList.add(obj);
				break;
			}
		}
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
		synchronized (sharedLock) {
			switch (this.field) {
			case TERM: {
				SObject obj = new SObject(String.valueOf(keyId),
						Integer.parseInt(value), numOccurances);

				termList.add(obj);
				break;
			}
			case AUTHOR: {
				SObject obj = new SObject(String.valueOf(keyId),
						Integer.parseInt(value), numOccurances);
				System.out.println(keyId+" " + value+" " + numOccurances);
				authorList.add(obj);
				break;
			}
			case CATEGORY: {
				SObject obj = new SObject(String.valueOf(keyId),
						Integer.parseInt(value), numOccurances);
				categoryList.add(obj);
				break;
			}
			case LINK:
				SObject obj = new SObject(String.valueOf(keyId),
						Integer.parseInt(value), numOccurances);
				linkList.add(obj);
				break;
			}
		}
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
	 * @return
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */

	public synchronized void addToIndex(String key, int valueId,
			int numOccurances) throws IndexerException {
		synchronized (sharedLock) {
			switch (this.field) {
			case TERM: {
				SObject obj = new SObject(key, valueId, numOccurances);

				termList.add(obj);
				break;
			}
			case AUTHOR: {
				SObject obj = new SObject(key, valueId, numOccurances);
				
				authorList.add(obj);
				break;
			}
			case CATEGORY: {
				SObject obj = new SObject(key, valueId, numOccurances);
				categoryList.add(obj);
				break;
			}
			case LINK:
				SObject obj = new SObject(key, valueId, numOccurances);
				linkList.add(obj);
				break;
			}
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
		synchronized (sharedLock) {
			switch (this.field) {
			case TERM: {
				SObject obj = new SObject(key, Integer.parseInt(value),
						numOccurances);

				termList.add(obj);
				break;
			}
			case AUTHOR: {
				SObject obj = new SObject(key, Integer.parseInt(value),
						numOccurances);
				authorList.add(obj);
				break;
			}
			case CATEGORY: {
				SObject obj = new SObject(key, Integer.parseInt(value),
						numOccurances);
				categoryList.add(obj);
				break;
			}
			case LINK:
				SObject obj = new SObject(key, Integer.parseInt(value),
						numOccurances);
				linkList.add(obj);
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		synchronized (sharedLock) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						new DeflaterOutputStream(new FileOutputStream(
								authorIndexFile)));
				oos.writeObject(authorList);
				oos.close();

				ObjectOutputStream oos1 = new ObjectOutputStream(
						new DeflaterOutputStream(new FileOutputStream(
								termIndexFile)));
				oos1.writeObject(termList);
				oos1.close();

				ObjectOutputStream oos2 = new ObjectOutputStream(
						new DeflaterOutputStream(new FileOutputStream(
								categoryIndexFile)));
				oos2.writeObject(categoryList);
				oos2.close();

				ObjectOutputStream oos3 = new ObjectOutputStream(
						new DeflaterOutputStream(new FileOutputStream(
								linkIndexFile)));
				oos3.writeObject(linkList);
				oos3.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
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
