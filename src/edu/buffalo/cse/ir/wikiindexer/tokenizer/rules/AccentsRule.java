package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.ACCENTS)
public class AccentsRule implements TokenizerRule {

	public static String regChecker(String regExp, String text, String str1, String str2) {
		
		String result = text;
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		if (m.find())
			result = result.replaceAll(str1, str2);
		return result;
	}
	
	public static String accentCheck(String text, String accent, String origin){
		text = regChecker("[a-zA-Z0-9]+"+accent+"[a-zA-Z0-9]+", text, accent, origin);
		text = regChecker("\\s+"+accent+"[a-zA-Z0-9]+", text, accent, origin);
		text = regChecker("[a-zA-Z0-9]+"+accent+"(\\s+|\\.|\\?|\\!)", text, accent, origin);
		text = regChecker("[a-zA-Z0-9]+"+accent+"\\s+", text, accent, origin);
		text = regChecker("[a-zA-Z0-9]+"+accent, text, accent, origin);
		text = regChecker(accent+"[a-zA-Z0-9]+", text, accent, origin);
		text = regChecker(accent, text, accent, origin);
		return text;
	}
	
	public static String replaceAccent(String text) {
		
		text = accentCheck(text, "(â|Ã¢)", "a");
		text = accentCheck(text, "(ô|Ã´)", "o");
		text = accentCheck(text, "(é|Ã©)", "e");
		text = accentCheck(text, "(а̀)", "a");
		text = accentCheck(text, "(à|(Ã ))", "a");
		text = accentCheck(text, "(è|Ã¨)", "e");
		text = accentCheck(text, "(é|Ã©)", "e");
		text = accentCheck(text, "(Ã¨|è)", "e");
		text = accentCheck(text, "(û|(Ã»))", "u");
		text = accentCheck(text, "(ü|Ã¼)", "u");
		text = accentCheck(text, "(ë|Ã«)", "e");
		text = accentCheck(text, "Ð¿Ð°Ì€Ñ€Ð°", "Ð¿aÑ€Ð°");
		text = accentCheck(text, "Ð¿Ð°Ñ€Ð°Ì€", "napa");
		
		return text;
	}
	
	public static void main(String[] args) {
		System.out.println(replaceAccent("The urban Ð¿Ð°Ì€Ñ€Ð° counterpart of château is palais aiguÃ«"));
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
					token = replaceAccent(token);
					stream.set(token.split(" "));
				}
			}
		}
		stream.reset();
	}


}
