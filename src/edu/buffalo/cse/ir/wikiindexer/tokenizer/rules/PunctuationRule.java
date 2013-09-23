/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

/**
 * @author naren
 * 
 */
@RuleClass(className = RULENAMES.PUNCTUATION)
public class PunctuationRule implements TokenizerRule {
	// Local tester main
	public static void main(String[] args) {
		System.out.println(removePunctuation("tes.t.!"));
	}

	private static String removePunctuation(String text) {

		text = text.replaceAll("\\. |\\? |\\! ", " ");

		if (text.matches("(\\d+\\.\\d+\\.\\d+\\.\\d+)")) {

			return text;
		} else if (text.matches("(\\d+\\.\\d+\\.\\d+\\.\\d+).+")) {
			Pattern regex = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+).+");
			Matcher matcher = regex.matcher(text);
			matcher.find();
			text = matcher.group(1);
			return text;
		}
		else if (text.matches(".+\\?|.+\\!|.+\\."))
			text = text.replaceAll("\\.|\\?|\\!", "");

		return text;

	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
					token = removePunctuation(token);
					stream.set(token);
				}
			}
		}
		stream.reset();
	}
}