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

	private static String[] removeChars(String input) {
		String[] x = { "", "" };
		if (input.matches(".+\\@.+\\..+"))
			return input.split("@");
		else
			return x;
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				stream.previous();
				if (token != null) {
					if (token.matches(".+\\@.+")) {
						stream.set(removeChars(token));
						stream.next();
						continue;
					} else if (token.matches("\\#[0-9]*\\-[0-9]*")) {
						stream.set(token.replaceAll("#", ""));
						stream.next();
						continue;
					} 
					else if(token.matches("\\$.+")){
						stream.set(token.replaceAll("\\$", ""));
						stream.next();
						continue;
					}
					else if(token.contains("\\%")){
						stream.set(token.replaceAll("\\%", ""));
						stream.next();
						continue;
					}
					else if(token.matches(".\\*.")){
						stream.set(token.split("\\*"));
						stream.next();
						continue;
					}
					else if(token.matches(".\\^.")){
						stream.set(token.split("\\^"));
						stream.next();
						continue;
					}
					else if(token.matches("\\-.+"))
					{
						stream.set(token);
						stream.next();
						continue;
					}
					else {
						token = removeSpecialChars(token);
						if (token.equals(""))
							stream.remove();
						else {
							stream.set(removeSpecialChars(token));
							stream.next();
							continue;
						}

					}
				}
				stream.reset();

			}

		}
	}
}
