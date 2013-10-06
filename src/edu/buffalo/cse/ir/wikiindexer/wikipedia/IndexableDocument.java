/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.HashMap;
import java.util.Map;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;

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
	Map<INDEXFIELD, TokenStream> tmap;

	public IndexableDocument(DocumentTransformer dt) {
		tmap = new HashMap<INDEXFIELD,TokenStream>();
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
		if (tmap.containsKey(field)) {
			TokenStream s = tmap.get(field);
			if (s != null)
				s.merge(stream);
			else
				s = stream;
		} else {
			if (stream != null)
				tmap.put(field, stream);
		}

	}

	/**
	 * Method to return the stream for a given field
	 * 
	 * @param key
	 *            : The field for which the stream is requested
	 * @return The underlying stream if the key exists, null otherwise
	 */
	public TokenStream getStream(INDEXFIELD key) {
	
			return tmap.get(key);
	}

	/**
	 * Method to return a unique identifier for the given document. It is left
	 * to the student to identify what this must be But also look at how it is
	 * referenced in the indexing process
	 * 
	 * @return A unique identifier for the given document
	 */
	public String getDocumentIdentifier() {
		return docTrans.getDocument().getTitle()+"####";

	}

}
