package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.WHITESPACE)
public class WhiteSpaceRule implements TokenizerRule {

	public static String[] removeWhiteSpace(String sb) {
		sb = sb.replaceAll("\\s+", " ");
		sb = sb.trim();
		String[] x = sb.split(" ");
		return x;
	}

	public static void main(String[] args) {
		String[] test = removeWhiteSpace("this is a test");
		for (String string : test) {
			System.out.println(string);
		}
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		System.out.println("inside whitespace rule");
		if (stream != null) {
			String[] token;
			String actual = "";
			while (stream.hasNext()) {
				actual = stream.next();
				if (actual != null) {
					token = removeWhiteSpace(actual);
					stream.previous();
					stream.set(token);
					stream.next();
					// System.out.println(stream.getAllTokens().size());

				}
			}
		}
		stream.reset();
	}

}
