/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.indexer.SharedDictionary;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument.Section;

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
	Properties props;

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

		/* Add Author Streams */
		Tokenizer authorTokenizer = this.tokenMap.get(INDEXFIELD.AUTHOR);

		TokenStream stream = new TokenStream(this.document.getAuthor());
		authorTokenizer.tokenize(stream);

		doc.addField(INDEXFIELD.AUTHOR, stream);

		/* Add Category Streams */
		List<String> cat = this.document.getCategories();
		StringBuilder builder = new StringBuilder();
		for (String string : cat) {
			builder.append(string + " ");
		}
		Tokenizer catTokenizer = this.tokenMap.get(INDEXFIELD.CATEGORY);

		TokenStream catStream = new TokenStream(builder.toString());
		catTokenizer.tokenize(catStream);

		doc.addField(INDEXFIELD.CATEGORY, catStream);

		/* Add Term Streams */
		List<Section> s = this.document.getSections();

		StringBuilder bd = new StringBuilder();
		for (Section section : s) {
			bd.append(section.getTitle() + " " + section.getText());
		}
		Tokenizer termTokenizer = this.tokenMap.get(INDEXFIELD.TERM);

		TokenStream termStream = new TokenStream(bd.toString());
		termTokenizer.tokenize(termStream);

		doc.addField(INDEXFIELD.TERM, termStream);

		/* Add Link streams */
		Set<String> linkSet = this.document.getLinks();

		StringBuilder linkBuilder = new StringBuilder();
		for (String string : linkSet) {
			linkBuilder.append(string + " ");
		}
		Tokenizer linkTokenizer = this.tokenMap.get(INDEXFIELD.LINK);
		TokenStream linkStream = new TokenStream(linkBuilder.toString());
		linkTokenizer.tokenize(linkStream);

		doc.addField(INDEXFIELD.LINK, termStream);

		SharedDictionary docDict = new SharedDictionary(props, INDEXFIELD.LINK);

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
