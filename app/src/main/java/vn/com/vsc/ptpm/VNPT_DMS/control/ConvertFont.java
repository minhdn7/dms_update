package vn.com.vsc.ptpm.VNPT_DMS.control;

public class ConvertFont {

	public static String def = " 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'~!@#$%^&*()-_=+\\|]}[{?/>.,<:;'\"";
	public static String vn_lower = "à,á,ả,ã,ạ,â,ầ,ấ,ẩ,ẫ,ậ,ă,ằ,ắ,ẳ,ẵ,ặ,è,é,ẻ,ẽ,ẹ,ê,ề,ế,ể,ễ,ệ,ì,í,ỉ,ĩ,ị,ò,ó,ỏ,õ,ọ,ô,ồ,ố,ổ,ỗ,ộ,ơ,ờ,ớ,ở,ỡ,ợ,ù,ú,ủ,ũ,ụ,ư,ừ,ứ,ử,ữ,ự,ỳ,ý,ỷ,ỹ,ỵ,đ";
	public static String vn_upper = "À,Á,Ả,Ã,Ạ,Â,Ầ,Ấ,Ẩ,Ẫ,Ậ,Ă,Ằ,Ắ,Ẳ,Ẵ,Ặ,È,É,Ẻ,Ẽ,Ẹ,Ê,Ề,Ế,Ể,Ễ,Ệ,Ì,Í,Ỉ,Ĩ,Ị,Ò,Ó,Ỏ,Õ,Ọ,Ô,Ồ,Ố,Ổ,Ỗ,Ộ,Ơ,Ờ,Ớ,Ở,Ỡ,Ợ,Ù,Ú,Ủ,Ũ,Ụ,Ư,Ừ,Ứ,Ử,Ữ,Ự,Ỳ,Ý,Ỷ,Ỹ,Ỵ,Đ";
	public static String en_lower = "a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,e,e,e,e,e,e,e,e,e,e,e,i,i,i,i,i,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,u,u,u,u,u,u,u,u,u,u,u,y,y,y,y,y,d";
	public static String en_upper = "A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,E,E,E,E,E,E,E,E,E,E,E,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,U,U,U,U,U,U,U,U,U,U,U,Y,Y,Y,Y,Y,D";
	public static String ncr_lower = "&#224;,&#225;,&#7843;,&#227;,&#7841;,&#226;,&#7847;,&#7845;,&#7849;,&#7851;,&#7853;,&#259;,&#7857;,&#7855;,&#7859;,&#7861;,&#7863;,&#232;,&#233;,&#7867;,&#7869;,&#7865;,&#234;,&#7873;,&#7871;,&#7875;,&#7877;,&#7879;,&#236;,&#237;,&#7881;,&#297;,&#7883;,&#242;,&#243;,&#7887;,&#245;,&#7885;,&#244;,&#7891;,&#7889;,&#7893;,&#7895;,&#7897;,&#417;,&#7901;,&#7899;,&#7903;,&#7905;,&#7907;,&#249;,&#250;,&#7911;,&#361;,&#7909;,&#432;,&#7915;,&#7913;,&#7917;,&#7919;,&#7921;,&#7923;,&#253;,&#7927;,&#7929;,&#7925;,&#273;";
	public static String ncr_upper = "&#192;,&#193;,&#7842;,&#195;,&#7840;,&#194;,&#7846;,&#7844;,&#7848;,&#7850;,&#7852;,&#258;,&#7856;,&#7854;,&#7858;,&#7860;,&#7862;,&#200;,&#201;,&#7866;,&#7868;,&#7864;,&#202;,&#7872;,&#7870;,&#7874;,&#7876;,&#7878;,&#204;,&#205;,&#7880;,&#296;,&#7882;,&#210;,&#211;,&#7886;,&#213;,&#7884;,&#212;,&#7890;,&#7888;,&#7892;,&#7894;,&#7896;,&#416;,&#7900;,&#7898;,&#7902;,&#7904;,&#7906;,&#217;,&#218;,&#7910;,&#360;,&#7908;,&#431;,&#7914;,&#7912;,&#7916;,&#7918;,&#7920;,&#7922;,&#221;,&#7926;,&#7928;,&#7924;,&#272;";

	public static String vn_char_lower = vn_lower.replaceAll(",", "");
	public static String vn_char_upper = vn_upper.replaceAll(",", "");
	public static String en_char_lower = en_lower.replaceAll(",", "");
	public static String en_char_upper = en_upper.replaceAll(",", "");

	public static String[] ncr_arr_lower = ncr_lower.split(",");
	public static String[] ncr_arr_upper = ncr_upper.split(",");
	public static String[] en_char_lower_arr = en_lower.split(",");
	public static String[] en_char_upper_arr = en_upper.split(",");

	// iso 8859_1
	// http://www.w3schools.com/tags/ref_entities.asp
	public static String character_unicode = "\",',&,<,>, ,¡,¢,£,¤,¥,¦,§,¨,©,ª,«,¬,�­,®,¯,°,±,²,³,´,µ,¶,·,¸,¹,º,»,¼,½,¾,¿,×,÷,À,Á,Â,Ã,Ä,Å,Æ,Ç,È,É,Ê,Ë,Ì,Í,Î,Ï,Ð,Ñ,Ò,Ó,Ô,Õ,Ö,Ø,Ù,Ú,Û,Ü,Ý,Þ,ß,à,á,â,ã,ä,å,æ,ç,è,é,ê,ë,ì,í,î,ï,ð,ñ,ò,ó,ô,õ,ö,ø,ù,ú,û,ü,ý,þ,ÿ";
	public static String character_ncr_map = "&#34;,&#39;,&#38;,&#60;,&#62;,&#160;,&#161;,&#162;,&#163;,&#164;,&#165;,&#166;,&#167;,&#168;,&#169;,&#170;,&#171;,&#172;,&#173;,&#174;,&#175;,&#176;,&#177;,&#178;,&#179;,&#180;,&#181;,&#182;,&#183;,&#184;,&#185;,&#186;,&#187;,&#188;,&#189;,&#190;,&#191;,&#215;,&#247;,&#192;,&#193;,&#194;,&#195;,&#196;,&#197;,&#198;,&#199;,&#200;,&#201;,&#202;,&#203;,&#204;,&#205;,&#206;,&#207;,&#208;,&#209;,&#210;,&#211;,&#212;,&#213;,&#214;,&#216;,&#217;,&#218;,&#219;,&#220;,&#221;,&#222;,&#223;,&#224;,&#225;,&#226;,&#227;,&#228;,&#229;,&#230;,&#231;,&#232;,&#233;,&#234;,&#235;,&#236;,&#237;,&#238;,&#239;,&#240;,&#241;,&#242;,&#243;,&#244;,&#245;,&#246;,&#248;,&#249;,&#250;,&#251;,&#252;,&#253;,&#254;,&#255;";
	public static String character_iso_8859_1_map = "&quot;,&apos;,&amp;,&lt;,&gt;,&nbsp;,&iexcl;,&cent;,&pound;,&curren;,&yen;,&brvbar;,&sect;,&uml;,&copy;,&ordf;,&laquo;,&not;,&shy;,&reg;,&macr;,&deg;,&plusmn;,&sup2;,&sup3;,&acute;,&micro;,&para;,&middot;,&cedil;,&sup1;,&ordm;,&raquo;,&frac14;,&frac12;,&frac34;,&iquest;,&times;,&divide;,&Agrave;,&Aacute;,&Acirc;,&Atilde;,&Auml;,&Aring;,&AElig;,&Ccedil;,&Egrave;,&Eacute;,&Ecirc;,&Euml;,&Igrave;,&Iacute;,&Icirc;,&Iuml;,&ETH;,&Ntilde;,&Ograve;,&Oacute;,&Ocirc;,&Otilde;,&Ouml;,&Oslash;,&Ugrave;,&Uacute;,&Ucirc;,&Uuml;,&Yacute;,&THORN;,&szlig;,&agrave;,&aacute;,&acirc;,&atilde;,&auml;,&aring;,&aelig;,&ccedil;,&egrave;,&eacute;,&ecirc;,&euml;,&igrave;,&iacute;,&icirc;,&iuml;,&eth;,&ntilde;,&ograve;,&oacute;,&ocirc;,&otilde;,&ouml;,&oslash;,&ugrave;,&uacute;,&ucirc;,&uuml;,&yacute;,&thorn;,&yuml;";
	public static String[] character_unicode_arr = character_unicode.split(",");
	public static String[] character_ncr_map_arr = character_ncr_map.split(",");
	public static String[] character_iso_8859_1_map_arr = character_iso_8859_1_map
			.split(",");

	public static void main(String[] args) {
		/*
		 * String to_convert =
		 * "Nguy&#7877;n Tr&#237; Thanh Ch&#237; &#272;&#224; &#273;&#224; Ph&amp;#432;&#417;ng th&#7913;c m&#7899;i - n&#7841;p ti&#7873;n b&#7857;ng m&#227; th&#7867; tr&#7843; tr&#432;&#7899;c:  ti&#7879;n l&#7907;i h&#417;n, hi&#7879;u qu&#7843; h&#417;n."
		 * ; System.out.println(to_convert); String a =
		 * getUTF8StringFromNCR(to_convert); System.out.println(a);
		 * 
		 * a = getEnStringFromNCRString(to_convert);
		 * System.out.println("tieng viet khong dau: " + a);
		 * 
		 * System.out.println("nguyễn công tuấn"); to_convert =
		 * "nguyễn công tuấn"; a = GetNCRDecimalString(to_convert);
		 * System.out.println(a); a = getEnStringFromNCRString(to_convert);
		 * System.out.println(a);
		 */

		System.out.println("length: " + character_unicode_arr.length + "/"
				+ character_ncr_map_arr.length + "/"
				+ character_iso_8859_1_map_arr.length);
		String a = "\r<p abcd=\"a\"";
		System.out.println("=================" + a);
		System.out.println(GetNCRDecimalString(a));

		System.out.println("aaa " + toEnglish("Nguyễn Công Tuấn"));

		String to_convert = "Nguy&#7877;n Tr&#237; Thanh Ch&#237; &#272;&#224; &#273;&#224; Ph&amp;#432;&#417;ng th&#7913;c m&#7899;i - n&#7841;p ti&#7873;n b&#7857;ng m&#227; th&#7867; tr&#7843; tr&#432;&#7899;c:  ti&#7879;n l&#7907;i h&#417;n, hi&#7879;u qu&#7843; h&#417;n.";
		System.out.println("en_string " + getEnStringFromNCRString(to_convert));

		a = "<p>Doi voi kh&aacute;ch h&agrave;ng l&agrave; doanh nghiep su dung dich vu tra sau tu 10 thu&ecirc; bao tro l&ecirc;n, se duoc tang 1 lang hoa tri gi&aacute; 250.000d v&agrave;o dip ky niem ng&agrave;y th&agrave;nh lap doanh nghiep cho doanh nghiep, v&agrave; nguoi dung dau doanh nghiep.</p>";
		System.out.println("en_string " + getEnStringFromVnString(a));
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

	public static String getUTF8StringFromNCR(String s) {
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
				 * if(i<ab.length-1){ char2 = ab[i+1]; if(char2==(char)10){ a =
				 * a + '\r' + '\n'; i++; } else a = a + '\r'; } else a = a +
				 * '\n';
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

	public static String toEnglish(String vnString) {
		return getEnStringFromVnString(vnString);
	}

	public static String getEnStringFromVnString(String vnUtf8String) {
		if (vnUtf8String == null)
			return "";
		if (vnUtf8String.length() == 0)
			return "";

		String a = "";
		String s = ClearISO_8859_1_From_UTF8String(vnUtf8String);

		char b;
		for (int i = 0; i < s.length(); i++) {
			b = s.charAt(i);
			for (int j = 0; j < vn_char_lower.length(); j++) {
				if (b == vn_char_lower.charAt(j)) {
					b = en_char_lower.charAt(j);
					break;
				} else if (b == vn_char_upper.charAt(j)) {
					b = en_char_upper.charAt(j);
					break;
				}
			}

			a = a + Character.toString(b);
		}
		// String a = new String(vnChar);
		return a;
	}

	public static String getEnStringFromNCRString(String vnNCRString) {
		if (vnNCRString == null)
			return "";
		if (vnNCRString.length() == 0)
			return "";

		String a = ClearISO_8859_1_From_NCRString(vnNCRString);

		for (int i = 0; i < ncr_arr_lower.length; i++) {
			a = a.replaceAll(ncr_arr_lower[i], en_char_lower_arr[i]);
			a = a.replaceAll(ncr_arr_upper[i], en_char_upper_arr[i]);
		}

		// replace cac ky tu dac biet
		for (int i = 0; i < character_ncr_map_arr.length; i++) {
			a = a.replaceAll(character_ncr_map_arr[i], character_unicode_arr[i]);
		}

		return a;
	}

	/**
	 * This method is employed to convert the input string into a String of
	 * Decimal NCR.
	 *
	 * @param str : Input String which is to be converted into a String of
	 *            Decimal NCR.
	 * @return: Decimal NCR String.
	 */
	public String toDecimalNCR(String str) {
		String outputString = "";
		int haut = 0;
		for (int i = 0; i < str.length(); i++) {
			char b = str.charAt(i);
			if (b < 0 || b > 0xFFFF) {
				outputString += "!error " + dec2hex(b) + "!";
			}
			if (haut != 0) {
				if (0xDC00 <= b && b <= 0xDFFF) {
					outputString += dec2hex(0x10000 + ((haut - 0xD800) << 10)
							+ (b - 0xDC00))
							+ "";
					haut = 0;
					continue;
				} else {
					outputString += "!error " + dec2hex(haut) + "!";
					haut = 0;
				}
			}
			if (0xD800 <= b && b <= 0xDBFF) {
				haut = b;
			} else {
				outputString += dec2hex(b) + "";
			}
		}
		return outputString;
	}

	private String dec2hex(int textString) {
		int t = textString + 0;
		String temp = "&#" + Integer.toString(t).toUpperCase() + ";";
		return temp.trim();
	}
}
