/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.Map;
import java.util.concurrent.Callable;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;

/**
 * A Callable document transformer that converts the given WikipediaDocument
 * object into an IndexableDocument object using the given Tokenizer
 * 
 * @author nikhillo
 * 
 */
public class DocumentTransformer implements Callable<IndexableDocument> {
	/**
	 * Default constructor, DO NOT change
	 * 
	 * @param tknizerMap
	 *            : A map mapping a fully initialized tokenizer to a given field
	 *            type
	 * @param doc
	 *            : The WikipediaDocument to be processed
	 */
	WikipediaDocument document;
	IndexableDocument indexDocument;
	Map<INDEXFIELD, Tokenizer> tokenMap;

	public DocumentTransformer(Map<INDEXFIELD, Tokenizer> tknizerMap,
			WikipediaDocument doc) {
		this.document = doc;
		this.tokenMap = tknizerMap;

	}

	/**
	 * Method to trigger the transformation
	 * 
	 * @throws TokenizerException
	 *             Inc ase any tokenization error occurs
	 */
	public IndexableDocument call() throws TokenizerException {

		IndexableDocument doc = new IndexableDocument(this);
		return doc;
	}

	public WikipediaDocument getDocument() {
		return document;
	}

	public void setDocument(WikipediaDocument document) {
		this.document = document;
	}

	public IndexableDocument getIndexDocument() {
		return indexDocument;
	}

	public void setIndexDocument(IndexableDocument indexDocument) {
		this.indexDocument = indexDocument;
	}

	public Map<INDEXFIELD, Tokenizer> getTokenMap() {
		return tokenMap;
	}

	public void setTokenMap(Map<INDEXFIELD, Tokenizer> tokenMap) {
		this.tokenMap = tokenMap;
	}

}
