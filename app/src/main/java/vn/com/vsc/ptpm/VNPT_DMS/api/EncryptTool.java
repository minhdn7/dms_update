package vn.com.vsc.ptpm.VNPT_DMS.api;

/**
 * Created by LONGPD on 12/17/2015.
 */
public class EncryptTool {

	private static String def = " 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'~!@#$%^&*()-_=+\\|]}[{?/>.,<:;'\"";
	private static String character_unicode = "\",',&,<,>, ,¡,¢,£,¤,¥,¦,§,¨,©,ª,«,¬,�­,®,¯,°,±,²,³,´,µ,¶,·,¸,¹,º,»,¼,½,¾,¿,×,÷,À,Á,Â,Ã,Ä,Å,Æ,Ç,È,É,Ê,Ë,Ì,Í,Î,Ï,Ð,Ñ,Ò,Ó,Ô,Õ,Ö,Ø,Ù,Ú,Û,Ü,Ý,Þ,ß,à,á,â,ã,ä,å,æ,ç,è,é,ê,ë,ì,í,î,ï,ð,ñ,ò,ó,ô,õ,ö,ø,ù,ú,û,ü,ý,þ,ÿ";
	private static String character_ncr_map = "&#34;,&#39;,&#38;,&#60;,&#62;,&#160;,&#161;,&#162;,&#163;,&#164;,&#165;,&#166;,&#167;,&#168;,&#169;,&#170;,&#171;,&#172;,&#173;,&#174;,&#175;,&#176;,&#177;,&#178;,&#179;,&#180;,&#181;,&#182;,&#183;,&#184;,&#185;,&#186;,&#187;,&#188;,&#189;,&#190;,&#191;,&#215;,&#247;,&#192;,&#193;,&#194;,&#195;,&#196;,&#197;,&#198;,&#199;,&#200;,&#201;,&#202;,&#203;,&#204;,&#205;,&#206;,&#207;,&#208;,&#209;,&#210;,&#211;,&#212;,&#213;,&#214;,&#216;,&#217;,&#218;,&#219;,&#220;,&#221;,&#222;,&#223;,&#224;,&#225;,&#226;,&#227;,&#228;,&#229;,&#230;,&#231;,&#232;,&#233;,&#234;,&#235;,&#236;,&#237;,&#238;,&#239;,&#240;,&#241;,&#242;,&#243;,&#244;,&#245;,&#246;,&#248;,&#249;,&#250;,&#251;,&#252;,&#253;,&#254;,&#255;";
	private static String character_iso_8859_1_map = "&quot;,&apos;,&amp;,&lt;,&gt;,&nbsp;,&iexcl;,&cent;,&pound;,&curren;,&yen;,&brvbar;,&sect;,&uml;,&copy;,&ordf;,&laquo;,&not;,&shy;,&reg;,&macr;,&deg;,&plusmn;,&sup2;,&sup3;,&acute;,&micro;,&para;,&middot;,&cedil;,&sup1;,&ordm;,&raquo;,&frac14;,&frac12;,&frac34;,&iquest;,&times;,&divide;,&Agrave;,&Aacute;,&Acirc;,&Atilde;,&Auml;,&Aring;,&AElig;,&Ccedil;,&Egrave;,&Eacute;,&Ecirc;,&Euml;,&Igrave;,&Iacute;,&Icirc;,&Iuml;,&ETH;,&Ntilde;,&Ograve;,&Oacute;,&Ocirc;,&Otilde;,&Ouml;,&Oslash;,&Ugrave;,&Uacute;,&Ucirc;,&Uuml;,&Yacute;,&THORN;,&szlig;,&agrave;,&aacute;,&acirc;,&atilde;,&auml;,&aring;,&aelig;,&ccedil;,&egrave;,&eacute;,&ecirc;,&euml;,&igrave;,&iacute;,&icirc;,&iuml;,&eth;,&ntilde;,&ograve;,&oacute;,&ocirc;,&otilde;,&ouml;,&oslash;,&ugrave;,&uacute;,&ucirc;,&uuml;,&yacute;,&thorn;,&yuml;";
	private static String[] character_unicode_arr = character_unicode.split(",");
	private static String[] character_ncr_map_arr = character_ncr_map.split(",");
	private static String[] character_iso_8859_1_map_arr = character_iso_8859_1_map.split(",");
	private static EncryptTool mEncryptTool;

	public EncryptTool getInstance() {
		if (mEncryptTool == null) {
			mEncryptTool = new EncryptTool();
		}
		return mEncryptTool;
	}

	public static String getUTF8StringFromNCR(String s) {
		{
			try {
				if (s == null)
					return "";
				if (s.length() == 0)
					return "";

				String a = ClearISO_8859_1_From_NCRString(s);

				String tmp;
				int tmp1;

				char[] ab = a.toCharArray();

				a = "";
				for (int i = 0; i < ab.length; i++) {
					char c = ab[i];
					if (Character.toString(c).equals("&")) {
						if (Character.toString(ab[i + 1]).equals("#")) {
							// System.out.println("abc");
							i = i + 2;
							c = ab[i];
							tmp = "";
							while (!Character.toString(ab[i]).equals(";")) {
								c = ab[i];
								tmp = tmp + Character.toString(c);
								i = i + 1;
							}
							// System.out.println("")
							tmp1 = Integer.parseInt(tmp);
							c = (char) tmp1;

							// System.out.println("tmp: " + tmp + " tmp1: " +
							// tmp1 + " char: " + c);
							a = a + c;
						}
					} else {
						a = a + c;
					}
				}
				return a;
			} catch (Exception ex) {
				ex.printStackTrace();
				return "";
			}
		}
	}

	public static String ClearISO_8859_1_From_NCRString(String s) {
		try {
			if (s == null)
				return "";
			if (s.length() == 0)
				return "";

			String result = s;

			for (int i = 0; i < character_iso_8859_1_map_arr.length; i++) {
				String s1 = character_iso_8859_1_map_arr[i];

				// replace iso 8859_1 string by ncr string
				result = result.replaceAll(s1, character_ncr_map_arr[i]);
			}

			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public static String GetNCRDecimalString(String s) {
		if (s == null)
			return "";
		if (s.length() == 0)
			return "";
		String a = ClearISO_8859_1_From_UTF8String(s);

		char[] ab = a.toCharArray();
		// char char2;
		// String c2="";
		a = "";
		for (int i = 0; i < ab.length; i++) {
			char c = ab[i];
			String c1 = Character.toString(c);
			// System.out.println("char: " + i +" : " + c1);
			if (c == (char) 13) {
				a = a + '\r';
				/*
				 * if(i<ab.length-1){ char2 = ab[i+1]; if(char2==(char)10){ a = a + '\r' + '\n'; i++; } else a = a + '\r'; } else a = a + '\n';
				 */
			} else if (c == (char) 10) {
				a = a + '\n';
			} else {
				if (def.indexOf(c1) >= 0) {
					a = a + c1;// System.out.println(c1);
				} else {
					a = a + "&#" + (int) c + ";";
				}
			}
		}
		return a;
	}

	public static String ClearISO_8859_1_From_UTF8String(String s) {
		try {
			if (s == null)
				return "";
			if (s.length() == 0)
				return "";

			String result = s;

			for (int i = 0; i < character_iso_8859_1_map_arr.length; i++) {
				String s1 = character_iso_8859_1_map_arr[i];
				// replace iso 8859_1 string by unicode string
				result = result.replaceAll(s1, character_unicode_arr[i]);
			}

			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
}
