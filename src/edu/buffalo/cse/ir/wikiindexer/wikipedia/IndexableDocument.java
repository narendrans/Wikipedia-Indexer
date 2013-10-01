/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument.Section;

/**
 * A simple map based token view of the transformed document
 * 
 * @author nikhillo
 * 
 */
public class IndexableDocument {
	/**
	 * Default constructor
	 */
	DocumentTransformer docTrans;
	Map<INDEXFIELD, Tokenizer> tknizerMap;

	public IndexableDocument(DocumentTransformer dt) {
		this.docTrans = dt;
	}

	/**
	 * MEthod to add a field and stream to the map If the field already exists
	 * in the map, the streams should be merged
	 * 
	 * @param field
	 *            : The field to be added
	 * @param stream
	 *            : The stream to be added.
	 */
	public void addField(INDEXFIELD field, TokenStream stream) {
		// TODO: Implement this method
	}

	/**
	 * Method to return the stream for a given field
	 * 
	 * @param key
	 *            : The field for which the stream is requested
	 * @return The underlying stream if the key exists, null otherwise
	 */
	public TokenStream getStream(INDEXFIELD key) {
		tknizerMap = docTrans.getTokenMap();
		List<Section> s = docTrans.getDocument().getSections();
		//System.out.println("Sections written to disk: "+s.size());
		StringBuffer buf = new StringBuffer();
		for (Section section : s) {
			buf.append(section.getText());
		}
		switch (key) {
		case TERM:
			return new TokenStream(docTrans.getDocument().getAuthor());
		case AUTHOR:
			return new TokenStream(buf.toString());
		default:
			break;

		}
		// TODO: Implement this method
		return null;
	}

	/**
	 * Method to return a unique identifier for the given document. It is left
	 * to the student to identify what this must be But also look at how it is
	 * referenced in the indexing process
	 * 
	 * @return A unique identifier for the given document
	 */
	public String getDocumentIdentifier() {
		return String.valueOf(docTrans.getDocument().getId());

	}

}
