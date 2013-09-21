/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class represents a stream of tokens as the name suggests. It wraps the
 * token stream and provides utility methods to manipulate it
 * 
 * @author nikhillo
 * 
 */
public class TokenStream implements Iterator<String> {

	private StringBuilder stringBuilder;
	private String token;
	private List<String> stream;
	private int position;
	private boolean streamCheck = false;

	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	public void setStringBuilder(StringBuilder stringBuilder) {
		this.stringBuilder = stringBuilder;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getStream() {
		return stream;
	}

	public void setStream(List<String> stream) {
		this.stream = stream;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Default constructor
	 * 
	 * @param bldr
	 *            : THe stringbuilder to seed the stream
	 */
	public TokenStream(StringBuilder bldr) {
		stream = new ArrayList<String>();
		stream.add(bldr.toString());
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param bldr
	 *            : THe stringbuilder to seed the stream
	 */
	public TokenStream(String string) {
		if (string == null || string.equals("")) {
			stream = new ArrayList<String>();
			streamCheck = false;
			return;
		} else if (string != null) {
			streamCheck = true;
			stream = new ArrayList<String>();
			stream.add(string);
		}
		position = 0;
	}

	/**
	 * Method to append tokens to the stream
	 * 
	 * @param tokens
	 *            : The tokens to be appended
	 */
	public void append(String... tokens) {
		if (tokens == null || tokens[0].equals(""))
			return;
		if (tokens != null) {
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i] == null || tokens[i].equals(""))
					continue;
				this.stream.add(tokens[i]);
			}
		} else
			stream = null;
	}

	/**
	 * Method to retrieve a map of token to count mapping This map should
	 * contain the unique set of tokens as keys The values should be the number
	 * of occurrences of the token in the given stream
	 * 
	 * @return The map as described above, no restrictions on ordering
	 *         applicable
	 */
	public Map<String, Integer> getTokenMap() {
		if (stream == null || !streamCheck)
			return null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < stream.size(); i++) {
			int occurences = Collections.frequency(stream, stream.get(i));
			if (!map.containsKey(stream.get(i)))
				map.put(stream.get(i), occurences);
		}
		return map;
	}

	/**
	 * Method to get the underlying token stream as a collection of tokens
	 * 
	 * @return A collection containing the ordered tokens as wrapped by this
	 *         stream Each token must be a separate element within the
	 *         collection. Operations on the returned collection should NOT
	 *         affect the token stream
	 */
	public Collection<String> getAllTokens() {
		if (stream == null)
			return null;
		if(stream.isEmpty())
			return null;
		if (stream != null) {
			List<String> temp = new ArrayList<String>();
			if (stream.isEmpty())
				return temp;

			temp.addAll(stream);
			return temp;
		} else
			return null;
	}

	/**
	 * Method to query for the given token within the stream
	 * 
	 * @param token
	 *            : The token to be queried
	 * @return: THe number of times it occurs within the stream, 0 if not found
	 */
	public int query(String token) {
		int count = 0;
		if (stream != null) {
			if (stream.contains(token)) {
				count = Collections.frequency(stream, token);
				return count;
			}
		} else
			return 0;
		return count;
	}

	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * 
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasNext() {
		if (stream == null)
			return false;
		if (position == stream.size())
			return false;

		return true;
	}

	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * 
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasPrevious() {
		if (stream == null || position <= 0)
			return false;
		else
			return true;
	}

	/**
	 * Iterator method: Method to get the next token from the stream Callers
	 * must call the set method to modify the token, changing the value of the
	 * token returned by this method must not alter the stream
	 * 
	 * @return The next token from the stream, null if at the end
	 */
	public String next() {
		if (stream == null || position >= stream.size())
			return null;
		else {
			String x = stream.get(position);
			position = position + 1;
			return x;
		}
	}

	/**
	 * Iterator method: Method to get the previous token from the stream Callers
	 * must call the set method to modify the token, changing the value of the
	 * token returned by this method must not alter the stream
	 * 
	 * @return The next token from the stream, null if at the end
	 */
	public String previous() {
		// if position is 0, no element is there behind 1st element
		// if position is -1, no element is there at all
		if (stream == null || position <= 0)
			return null;
		else {

			position = position - 1;
			return stream.get(position);

		}
	}

	/**
	 * Iterator method: Method to remove the current token from the stream. The
	 * iterator should point to the next token in forward iteration.
	 */
	public void remove() {
		if (stream == null || position == stream.size())
			return;
		if (stream != null) {
			stream.remove(position);
		}

	}

	/**
	 * Method to merge the current token with the previous token, assumes
	 * whitespace separator between tokens when merged. The token iterator
	 * should now point to the newly merged token (i.e. the previous one)
	 * 
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithPrevious() {
		try {
			if (position == 0)
				return false;
			int prevPosition = position - 1;
			String currentString = stream.get(position);
			String prevString = stream.get(prevPosition);
			stream.set(position, prevString + " " + currentString);
			stream.remove(prevPosition);
			position = position - 1;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Method to merge the current token with the next token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the current one)
	 * 
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithNext() {
		try {
			int nextPosition = position + 1;
			String currentString = stream.get(position);
			String nextString = stream.get(nextPosition);
			stream.set(position, currentString + " " + nextString);
			stream.remove(nextPosition);
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Method to replace the current token with the given tokens The stream
	 * should be manipulated accordingly based upon the number of tokens set It
	 * is expected that remove will be called to delete a token instead of
	 * passing null or an empty string here. The iterator should point to the
	 * last set token, i.e, last token in the passed array.
	 * 
	 * @param newValue
	 *            : The array of new values with every new token as a separate
	 *            element within the array
	 */
	public void set(String... newValue) {
		if (stream == null || newValue == null || newValue[0] == null
				|| newValue[0].equals("") || position == stream.size()) {
			return;
		} else {
			int temppos = position;
			for (String string : newValue) {

				if (temppos == position) {
					stream.set(temppos, string);
					temppos = temppos + 1;
				} else {
					stream.add(temppos, string);
					temppos = temppos + 1;
				}

				/*
				 * if (temppos >= stream.size()) { stream.add(string); temppos =
				 * temppos + 1; } else { if(newValue.length==1){
				 * stream.set(temppos, string); temppos = temppos + 1; } else {
				 * stream.add(temppos,string); temppos=temppos+1; }
				 */
			}
			// position = stream.indexOf(newValue[newValue.length]);
			position = temppos - 1;
		}
	}

	/**
	 * Iterator method: Method to reset the iterator to the start of the stream
	 * next must be called to get a token
	 */
	public void reset() {
		position = 0;
	}

	/**
	 * Iterator method: Method to set the iterator to beyond the last token in
	 * the stream previous must be called to get a token
	 */
	public void seekEnd() {
		if (stream == null)
			return;
		position = stream.size();
	}

	/**
	 * Method to merge this stream with another stream
	 * 
	 * @param other
	 *            : The stream to be merged
	 */
	public void merge(TokenStream other) {
		if (other == null || stream == null)
			return;
		if (this.stream.isEmpty())
			this.stream = other.stream;
		else
			stream.addAll(other.stream);
	}
}