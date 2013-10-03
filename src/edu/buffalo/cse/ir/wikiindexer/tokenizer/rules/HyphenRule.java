package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.HYPHEN)
public class HyphenRule implements TokenizerRule {

	public static void main(String[] args) {
		System.out.println(removeHyphen("212-asdmm"));
	}

	private static String removeHyphen(String input) {
		if (input.matches(" *\\-+\\*"))
			return input.replaceAll("-", "");
		if (input.matches("\\-*[a-zA-Z]+\\-*"))
			return input.replaceAll("-", "");
		if (input.matches("[a-zA-Z]+\\-[a-zA-Z]+"))
			return input.replaceAll("-", " ");
		else if (input.matches("[a-zA-Z]+\\-[0-9]+")
				|| input.matches("[0-9]+\\-[a-zA-Z]+"))
			return input;
		else
			return input;

	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				token = token.trim();
				stream.previous();
				if (token.matches("\\-+")) {
					stream.remove();
					continue;
				}
				if (token != null) {
					token = removeHyphen(token);
					stream.set(token);
					stream.next();
				}
			}
		}
		stream.reset();
	}

}
