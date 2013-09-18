/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
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
	public StringBuilder stringBuilder;
	ArrayList<String> list;
	String[] arrayList;
	int currentPosition = 0;

	/**
	 * Default constructor
	 * 
	 * @param bldr
	 *            : THe stringbuilder to seed the stream
	 */

	public static void main(String[] args) {
		TokenStream s = new TokenStream(
				"test the the the is test test of test thing out of box");
		System.out.println(s.query("test"));

	}

	public TokenStream(StringBuilder bldr) {
		this.stringBuilder = new StringBuilder();
		this.stringBuilder.append(bldr);
		this.list.add(bldr.toString());

	}

	/**
	 * Overloaded constructor
	 * 
	 * @param bldr
	 *            : THe stringbuilder to seed the stream
	 */
	public TokenStream(String string) {
		list = new ArrayList<String>();
		this.stringBuilder = new StringBuilder();
		this.stringBuilder.append(string);
		this.list.add(string);
	}

	/**
	 * Method to append tokens to the stream
	 * 
	 * @param tokens
	 *            : The tokens to be appended
	 */
	public void append(String... tokens) {
		for (String string : tokens) {
			list.add(string);
		}
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

		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			int occurences = Collections.frequency(list, list.get(i));
			if (!map.containsKey(list.get(i)))
				map.put(list.get(i), occurences);
		}
		return map;
	}

	/**
	 * Method to get the underlying token stream as a collection of tokens
	 * 
	 * @return A collection containingB the ordered tokens as wrapped by this
	 *         stream Each token must be a separate element within the
	 *         collection. Operations on the returned collection should NOT
	 *         affect the token stream
	 */
	public Collection<String> getAllTokens() {
		// Since the actual list should not be affected, make a copy and return
		// it
		ArrayList<String> newList = this.list;
		return newList;
	}

	/**
	 * Method to query for the given token within the stream
	 * 
	 * @param token
	 *            : The token to be queried
	 * @return: THe number of times it occurs within the stream, 0 if not found
	 */
	public int query(String token) {
		List<String> s = Arrays
				.asList(this.stringBuilder.toString().split(" "));
		return Collections.frequency(s, token);
	}

	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * 
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasNext() {
		if (!list.isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * 
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasPrevious() {
		if (list.size() > 0)
			return true;
		else
			return false;
	}

	/**
	 * Iterator method: Method to get the next token from the stream Callers
	 * must call the set method to modify the token, changing the value of the
	 * token returned by this method must not alter the stream
	 * 
	 * @return The next token from the stream, null if at the end
	 */
	public String next() {
		if (currentPosition == list.size())
			return null;
		else
			return list.get(currentPosition++);
	}

	/**
	 * Iterator method: Method to get the previous token from the stream Callers
	 * must call the set method to modify the token, changing the value of the
	 * token returned by this method must not alter the stream
	 * 
	 * @return The next token from the stream, null if at the end
	 */
	public String previous() {
		if (currentPosition == list.size())
			return null;
		else
			return list.get(currentPosition--);
	}

	/**
	 * Iterator method: Method to remove the current token from the stream
	 */
	public void remove() {
		list.remove(currentPosition);

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
			list.set(currentPosition - 1, list.get(currentPosition - 1) + " "
					+ list.get(currentPosition));
			list.remove(currentPosition);
			currentPosition = currentPosition - 1;
		} catch (Exception e) {
			e.printStackTrace();
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
			list.set(
					currentPosition,
					list.get(currentPosition) + " "
							+ list.get(currentPosition - 1));
		} catch (Exception e) {
			e.printStackTrace();
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

		for (int i = 0; i < newValue.length; i++)
			this.list.add(newValue[i]);
	}

	/**
	 * Iterator method: Method to reset the iterator to the start of the stream
	 * next must be called to get a token
	 */
	public void reset() {
		this.currentPosition = 0;
	}

	/**
	 * Iterator method: Method to set the iterator to beyond the last token in
	 * the stream previous must be called to get a token
	 */
	public void seekEnd() {
		this.currentPosition = currentPosition + 1;
	}

	/**
	 * Method to merge this stream with another stream
	 * 
	 * @param other
	 *            : The stream to be merged
	 */
	public void merge(TokenStream other) {
		this.list.addAll(other.list);
	}
}
