package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.SPECIALCHARS)
public class SpecialCharsRule implements TokenizerRule {

	// Local test main
	public static void main(String[] args) {
		System.out.println(removeSpecialChars("ha12sd\"]{a-+^.:,"));
	}

	private static String removeSpecialChars(String input) {
		return input.replaceAll("[^\\p{L}\\p{Nd}]", "");
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
					token = removeSpecialChars(token);
					if (token.equals("")) {
						stream.remove();
						continue;
					}
					if (token.contains("[a-zA-Z]\\*[a-zA-Z]")
							|| token.contains("[a-zA-Z]\\^[a-zA-Z]")) {
						String[] arr = token.split("\\+|\\-\\*");
						stream.set(arr);
						continue;
					}
					stream.set(token);
				}
			}
		}
		stream.reset();
	}

}
