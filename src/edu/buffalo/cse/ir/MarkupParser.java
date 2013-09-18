/* 
 * Team Name: Infinite Loop
 * Project: UB_IR
 * File name: MarkupParser.java
 */
package edu.buffalo.cse.ir;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkupParser {

	public static void main(String[] args) throws ParseException {
	//	System.out.println("<random> I should not vanish </random>".replaceAll(
	//			"\\u0020*<[^>]*>\\u0020*", ""));
	//	System.out
	//			.println("&lt;painful attr1='yes' attr2='no' &gt;Did you get me right?&lt;/pain&gt;"
	//					.replaceAll("&lt;([^.]*?)&gt;", "$1"));
	//	System.out.println("watch  disappear".replaceAll("  ", " "));
	//	System.out.println(getCategories(texts));
System.out.println(UUID.randomUUID().toString());

	}

	public static Collection<String> getCategories(String input) {
		Collection<String> categories = new ArrayList<String>();

		if (input.contains("Category")) {
			Pattern pattern = Pattern.compile("\\[\\[Category:(.+)\\]\\]");

			Matcher matcher = pattern.matcher(input);
			while (matcher.find()) {
				System.out.println(matcher.group(1));
				categories.add(matcher.group(1));
			}
		}
		return categories;
	}

	private final static String texts = "{{Use dmy dates|date=November 2012}}\n"
			+ "{{Infobox musical artist &lt;!-- See Wikipedia:WikiProject_Musicians --&gt;\n"
			+ "| name                = Russ Conway\n"
			+ "| image                 =\n"
			+ "| caption            = Russ Conway, pictured on the front of his 1959 [[Extended play|EP]] ''More Party Pops''.\n"
			+ "| image_size            = \n"
			+ "| background          = non_vocal_instrumentalist\n"
			+ "| birth_name          = Trevor Herbert Stanford\n"
			+ "| alias               = \n"
			+ "| birth_date          = {{birth date|1925|09|2|df=y}}\n"
			+ "| birth_place         = [[Bristol]], [[England]], UK\n"
			+ "| death_date          = {{death date and age|2000|11|16|1925|09|02|df=y}}\n"
			+ "| death_place         = [[Eastbourne]], [[Sussex]], England, UK\n"
			+ "| origin              = \n"
			+ "| instrument          = [[Piano]]\n"
			+ "| genre               = \n"
			+ "| occupation          = [[Musician]]\n"
			+ "| years_active        = \n"
			+ "| label               =  EMI (Columbia), Pye, MusicMedia, Churchill\n"
			+ "| associated_acts     = \n"
			+ "| website                 = \n"
			+ "| notable_instruments = \n"
			+ "}}\n"
			+ "\n"
			+ "'''Russ Conway''' (2 September 1925 – 16 November 2000) was a British [[popular music]] [[pianist]].&lt;ref name=&quot;British Hit Singles &amp; Albums&quot;&gt;{{cite book\n"
			+ "| first= David\n"
			+ "| last= Roberts\n"
			+ "| year= 2006\n"
			+ "| title= British Hit Singles &amp; Albums\n"
			+ "| edition= 19th\n"
			+ "| publisher= Guinness World Records Limited \n"
			+ "| location= London\n"
			+ "| isbn= 1-904994-10-5\n"
			+ "| pages= 118/9}}&lt;/ref&gt;  Conway had 20 [[piano]] [[instrumental]]s in the [[UK Singles Chart]] between 1957 and 1963, including two [[Chart-topper|number one]] [[hit record|hits]].&lt;ref name=&quot;British Hit Singles &amp; Albums&quot;/&gt;\n"
			+ "\n"
			+ "==Career==\n"
			+ "Conway was born '''Trevor Herbert Stanford''' in [[Bristol]] England.&lt;ref name=&quot;Larkin&quot;&gt;Larkin C 'Virgin Encyclopedia of Sixties Music' (Muze UK Ltd, 1997) ISBN 0-7535-0149-X p125&lt;/ref&gt; He won a scholarship to [[Bristol Cathedral Choir School]]&lt;ref name=&quot;Larkin&quot; /&gt; and was largely self-taught on piano as he whiled away hours as a youngster during a three-year term in borstal. His father then let him join the [[Merchant Navy (United Kingdom)|Merchant Navy]]. \n"
			+ "\n"
			+ "Conscripted into the [[Royal Navy]] in 1942, he served in the Merchant Navy from 1942 to 1948, and was awarded the [[Distinguished Service Medal (United Kingdom)|Distinguished Service Medal]] as signalman in a minesweeping flotilla &quot;for distinguished service, efficiency and zeal&quot; in clearance of mines in the [[Aegean Sea|Aegean]] and operations during the relief of [[Greece]] 1944-45. During his Navy service, he lost the tip of the 3rd finger of his right hand while using a bread slicer.&lt;ref name=&quot;Larkin&quot; /&gt; He was discharged on health grounds because of a stomach ulcer.\n"
			+ "\n"
			+ "Conway was talent-spotted while playing in a [[London]] [[nightclub|club]], signed to EMI's [[Columbia Graphophone Company|Columbia]] label and spent the mid-1950s providing backing for artists on their roster, including [[Gracie Fields]] and [[Joan Regan]].&lt;ref name=&quot;Larkin&quot; /&gt; He recorded his first [[solo (music)|solo]] [[single (music)|single]] &quot;Party Pops&quot; in 1957, a &quot;medley of standard songs&quot;&lt;ref name=&quot;Larkin&quot; /&gt; which included &quot;Roll the Carpet Up&quot; and &quot;The Westminster Waltz&quot;.\n"
			+ "\n"
			+ "Between 1957 and 1963, Conway had 20 U.K. chart hits, achieving a cumulative total of 83 weeks on the [[UK Singles Chart]] in 1959 alone.&lt;ref name=&quot;British Hit Singles &amp; Albums&quot;/&gt;  This included two self-penned number one [[instrumental]]s, &quot;Side Saddle&quot; and &quot;Roulette&quot;, the latter deposing [[Elvis Presley]]'s &quot;[[A Fool Such As I]]&quot;. He was a fixture on light entertainment [[Television|TV]] shows and [[radio]] for many years afterwards, appearing at the [[London Palladium]] on a number of occasions&lt;ref name=&quot;Larkin&quot; /&gt; and becoming a regular on the [[Billy Cotton Band Show]] for several seasons. He also made recordings as a vocalist.\n"
			+ "\n"
			+ "He was the subject of ''[[This Is Your Life (UK TV series)|This Is Your Life]]'' in 1959, when he was surprised by [[Eamonn Andrews]] during a recording session at the BBC’s Studio 1 at 201 Piccadilly, London.\n"
			+ "\n"
			+ "His career was blighted by ill health, including a nervous breakdown and subsequently a stroke, which prevented him from performing between 1968 and 1971.&lt;ref name=&quot;Larkin&quot; /&gt; He also at times drank heavily and smoked up to 80 cigarettes a day. He was prescribed anti-depressants and had periods of severe self-doubt. But he kept up playing. Having been diagnosed with stomach cancer in the late 1980s, in 1990 he founded the Russ Conway Cancer Fund with his friend, writer and [[Presenter|broadcaster]] Richard Hope-Hawkins, and they staged [[charitable organization|charity]] gala shows in major theatres that raised thousands of pounds for [[cancer]] charities.{{citation needed|date=August 2011}}\n"
			+ "\n"
			+ "He appeared as himself in [[French and Saunders]]' 1994 Christmas special, playing &quot;I Like It&quot; in their spoof of ''[[The Piano]]''.&lt;ref&gt;{{cite episode |title= 1994 Christmas Special|episodelink= |series= [[French and Saunders]]|serieslink= |credits= |network= [[Gold (TV channel)|Gold]]|station= |airdate= 24 April 2009|season= |seriesno= |number= |minutes= }}&lt;/ref&gt;\n"
			+ "\n"
			+ "Conway, who never married, died on 16 November 2000.&lt;ref&gt;GRO Register of Deaths NOV 2000 C48E 22 EASTBOURNE. DoB = 2 September 1925&lt;/ref&gt; Richard Hope-Hawkins delivered the main eulogy at Conway's [[funeral]] held at the historic [[St Mary Redcliffe|St Mary's Church]], [[Redcliffe, Bristol|Redcliffe]], Bristol. [[Elton John]] sent a wreath. In 2001 Hope-Hawkins devised, staged and directed a tribute to Conway at the [[Colston Hall]], Bristol, with an all-star cast. The £11,000 raised by the event was donated to St Peter's Hospice, Bristol.\n"
			+ "\n"
			+ "==Discography==\n"
			+ "\n"
			+ "===LPs===\n"
			+ "* ''Pack Up Your Troubles'' (1958) - [[UK Albums Chart]] No.9	\n"
			+ "* ''Songs To Sing In Your Bath'' (1959) - UK No.8\n"
			+ "* ''Family Favourites'' (1959) - UK No.3\n"
			+ "* ''Time To Celebrate'' (1959) - UK No.3		\n"
			+ "* ''My Concerto For You'' (1960) - UK No.5 	\n"
			+ "* ''Party Time'' (1960) - UK No.7\n"
			+ "* ''At The Cinema'' (1961)\n"
			+ "* ''Time To Play'' (1966)			\n"
			+ "* ''Russ Conway Presents 24 Piano Greats'' (1977) - UK No.25\n"
			+ "&lt;ref name=&quot;British Hit Singles &amp; Albums&quot;/&gt;\n"
			+ "\n"
			+ "===Singles===\n"
			+ "UK singles with highest position in the [[UK Singles Chart]]\n"
			+ "\n"
			+ "* &quot;Party Pops&quot; (1957) No.24\n"
			+ "* &quot;Got a Match&quot; (1958) No.30\n"
			+ "* &quot;More Party Pops&quot; (1958) No.10\n"
			+ "* &quot;The World Outside&quot; (1959) No.24\n"
			+ "* &quot;[[Side Saddle]]&quot; (1959) No.1\n"
			+ "* &quot;[[Roulette (instrumental)|Roulette]]&quot; (1959) No.1\n"
			+ "* &quot;China Tea&quot; (1959) No.5\n"
			+ "* &quot;Snow Coach&quot; (1959) No.7\n"
			+ "* &quot;More And More Party Pops&quot; (1959) No.5\n"
			+ "* &quot;Royal Event&quot; (1960) No.15\n"
			+ "* &quot;Fings Ain't Wot They Used To Be&quot; (1960) No.47\n"
			+ "* &quot;Lucky Five&quot; (1960) No.14\n"
			+ "* &quot;Passing Breeze&quot; (1960) No.16\n"
			+ "* &quot;Even More Party Pops&quot; (1960) No.27\n"
			+ "* &quot;Pepe&quot; (1961) No.19\n"
			+ "* &quot;Pablo&quot; (1961) No.45\n"
			+ "* &quot;Say It With Flowers&quot; (1961) No.23\n"
			+ "* &quot;Toy Balloons&quot; (1961) No.7\n"
			+ "* &quot;Lesson One&quot; (1962) No.21\n"
			+ "* &quot;Always You And Me&quot; (1962) No.33\n"
			+ "&lt;ref name=&quot;British Hit Singles &amp; Albums&quot;/&gt;\n"
			+ "\n"
			+ "==See also==\n"
			+ "* [[List of best-selling music artists]]\n"
			+ "\n"
			+ "==References==\n"
			+ "{{reflist}}\n"
			+ "\n"
			+ "==External links==\n"
			+ "*[http://www.russconway.co.uk/ Russ Conway]\n"
			+ "*[http://www.onlineweb.com/theones/conway/russ_conway.htm Russ Conway - British Pianist]\n"
			+ "*[http://www.bigredbook.info/russ_conway.html Russ Conway's appearance on This Is Your Life]\n"
			+ "*{{YouTube|TnIpQhDn4Zg|Russ Conway playing Side Saddle}}\n"
			+ "\n"
			+ "{{Authority control|VIAF=41343596}}\n"
			+ "\n"
			+ "&lt;!-- Metadata: see [[Wikipedia:Persondata]] --&gt;\n"
			+ "{{Persondata\n"
			+ "| NAME              =Conway, Russ\n"
			+ "| ALTERNATIVE NAMES =Stanford, Trevor Herbert\n"
			+ "| SHORT DESCRIPTION =Pianist\n"
			+ "| DATE OF BIRTH     = 2 September 1925\n"
			+ "| PLACE OF BIRTH    = [[Bristol]], [[Gloucestershire]], [[England]], UK\n"
			+ "| DATE OF DEATH     = 16 November 2000\n"
			+ "| PLACE OF DEATH    = [[Eastbourne]], [[Sussex]], [[England]], UK\n"
			+ "\n"
			+ "}}\n"
			+ "{{DEFAULTSORT:Conway, Russ}}\n"
			+ "[[Category:1925 births]]\n"
			+ "[[Category:2000 deaths]]\n"
			+ "[[Category:English pianists]]\n"
			+ "[[Category:English songwriters]]\n"
			+ "[[Category:People from Bristol]]\n"
			+ "[[Category:Billy Cotton Band Show]]\n"
			+ "[[Category:Cub Records artists]]\n"
			+ "[[Category:Music in Bristol]]";
}
