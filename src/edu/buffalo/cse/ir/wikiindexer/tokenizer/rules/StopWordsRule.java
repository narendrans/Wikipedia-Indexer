package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.STOPWORDS)
public class StopWordsRule implements TokenizerRule {

	private final static String stopwords = "a,about,above,after,again,against,all,am,an,and,any,are,aren't,as,at,be,because,been,before,being,below,between,both,but,by,can't,cannot,could,couldn't,did,didn't,do,does,doesn't,doing,don't,down,during,each,few,for,from,further,had,hadn't,has,hasn't,have,haven't,having,he,he'd,he'll,he's,her,here,here's,hers,herself,him,himself,his,how,how's,i,i'd,i'll,i'm,i've,if,in,into,is,isn't,it,it's,its,itself,let's,me,more,most,mustn't,my,myself,no,nor,not,of,off,on,once,only,or,other,ought,our,ours,ourselves,out,over,own,same,shan't,she,she'd,she'll,she's,should,shouldn't,so,some,such,than,that,that's,the,their,theirs,them,themselves,then,there,there's,these,they,they'd,they'll,they're,they've,this,those,through,to,too,under,until,up,very,was,wasn't,we,we'd,we'll,we're,we've,were,weren't,what,what's,when,when's,where,where's,which,while,who,who's,whom,why,why's,with,won't,would,wouldn't,you,you'd,you'll,you're,you've,your,yours,yourself,yourselves";
	private final static List<String> stopwordsList = Arrays.asList(stopwords
			.split(","));

	// Local test mainF
	public static void main(String[] args) {
		System.out
				.println(removeStopWords("this. is. above do not what? do this. got it? is? all do do not do this above is is is english is is is stopwords hoho all aalk englis is"));
		System.out
				.println(removeStopWords("a asd asdas alps do not do is. this."));
		System.out.println(removeStopWords("is is is am all and."));
		System.out.println(removeStopWords("english"));
		System.out.println(removeStopWords("ask"));

	}

	public static String removeStopWords(String text) {

		String[] splitText = text.split("\\s+");
		if (isStopWord(splitText[splitText.length - 1])) {
			text = text.replaceAll(" [^ ]+$", "");
		}
		boolean fullOfStopWords = true;
		for (String str : splitText) {
			str = str.replaceAll("[.!?]", "");
			if (isStopWord(str)) {
				text = text.replaceAll("\\b" + str + "\\b", "");
			} else
				fullOfStopWords = false;
		}
		if (fullOfStopWords == true) {
			text = "";
			return text;
		}
		text = text.replaceAll("\\s+", " ");
		text = text.trim();

		/*
		 * if (isStopWord(text)) { //text = regChecker("[\\s+[a-zA-Z]+\\s+",
		 * text, "'s", ""); for (int i = -1; (i = text.indexOf(text, i + 1)) !=
		 * -1;) { char tempChar = text.charAt(i - 1); if (tempChar == ' ')
		 * 
		 * return text; } }
		 */
		return text;
	}

	private static boolean isStopWord(String word) {
		return stopwordsList.contains(word);
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
					token = removeStopWords(token);
					if (token.isEmpty())
						stream.remove();
					else
						stream.set(token);
					stream.set(token.split(" "));
				}
			}
		}
		stream.reset();
	}

}
