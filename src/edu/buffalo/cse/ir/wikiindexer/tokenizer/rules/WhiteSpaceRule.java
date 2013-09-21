package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.WHITESPACE)
public class WhiteSpaceRule implements TokenizerRule {

	public String[] removeWhiteSpace(String sb) {
		sb = sb.replaceAll(" +", " ");
		return sb.split(" ");
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
	//	if (stream != null)
			//stream.set(removeWhiteSpace(stream.stringBuilder.toString()));
	}

}
