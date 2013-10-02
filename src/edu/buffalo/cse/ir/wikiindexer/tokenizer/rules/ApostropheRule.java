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
@RuleClass(className = RULENAMES.APOSTROPHE)
public class ApostropheRule implements TokenizerRule {

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule#apply(edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream)
	 */
	
	// input: isn't output: is , not
	
	public static String regChecker(String regExp, String text, String str1, String str2) {
		
		String result = text;
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		if (m.find())
			result = result.replaceAll(str1, str2);
		return result;
		/*while(m.find()){
			if(m.group().length() !=0){
				m.group().replaceAll(str1, str2);
			}
		}*/
	}
	
	public static String removeApostrophe(String text) {
		
		text = regChecker("[a-zA-Z]{2,4}'re", text, "'re", " are");
		text = regChecker("[a-zA-Z]{1,4}'ll", text, "'ll", " will");
		text = regChecker("[a-zA-Z]{1,6}'ve", text, "'ve", " have");
		text = regChecker("[a-zA-Z]{3}'t", text, "won't", "will not");
		text = regChecker("[a-zA-Z]{4}'t", text, "shan't", "shall not");
		text = regChecker("[a-zA-Z]{1,4}'d", text, "'d", " would");
		text = regChecker("I'm", text, "I'm", "I am");
		text = regChecker("[a-zA-Z]{3}'s", text, "let's", "let us");
		text = regChecker("[\\w+'s{1}]", text, "'s", "");
		text = regChecker("[a-zA-Z]+n't", text, "n't", " not");
		text = regChecker("[a-zA-Z]{3}'em", text, "'em", " them");
		text = regChecker("[\\w+'\\w+]|[\\s+'\\w+]|\\w+'\\s+]", text, "'", "");
		text = regChecker("[\\w+'[s]{1,2}]", text, "'s", "");
		
		return text;
	}
	
	public static void main(String[] args) {
		System.out.println(removeApostrophe("This isn't apostro'e who's friends'"));
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
					token = removeApostrophe(token);
					
					// isn't -> token = is not, so that output is is, not// if noun 
					 stream.set(token.split(" "));
				}
			}
		}
		stream.reset();
	}

}
