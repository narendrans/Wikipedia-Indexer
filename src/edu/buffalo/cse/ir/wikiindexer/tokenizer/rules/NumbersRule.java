package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.NUMBERS)
public class NumbersRule implements TokenizerRule {

	// Local test main
	public static void main(String[] args) {
		System.out.println(removeNumbers("skjd12399jkafdh939"));
	}

	private static String removeNumbers(String input) {
		if (input.contains("%"))
			input = input.replaceAll("[^\\%]", "");
		return input.replaceAll("[0-9]+", "");
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				stream.previous();
				if (token != null) {
					if (token.matches("[0-9]*")
							|| token.matches("[0-9]*\\,[0-9]*")) {
						stream.remove();
					} else {

						token = removeNumbers(token);
						stream.set(token);
						stream.next();
					}
				}
			}
		}
		stream.reset();
	}

}
