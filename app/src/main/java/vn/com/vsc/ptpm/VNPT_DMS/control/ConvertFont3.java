/*******************************************************************************
 *                  Convert: VN8VN3-TCVN3-UNICODE(Windows-1252)                 *
 *   Cac ham:                                                                   *
 *       DtoU:     Convert xau VN8VN3 thanh xau Unicode dang Web                *
 *       DtoUname: Convert xau VN8VN3 thanh xau Unicode dang Web - ten rieng    *
 *       UtoD:     Convert xau Unicode dang Web lai thanh xau VN8VN3 - ten rieng*
 *       UtoDname: Convert xau Unicode do trang Web tra lai thanh xau VN8VN3    *
 *       toV:      Convert xau Unicode dang Web tra lai thanh xau TCVN3         *
 *       Vname:    Convert xau TCVN3 thanh xau TCVN3  - Ten rieng               *
 *       VUpper:   Convert xau TCVN3 thanh xau TCVN3 - Chu hoa                  *
 *       VLower:   Convert xau TCVN3 thanh xau TCVN3 - Chu thuong               *
 *       Uname:    Convert xau Unicode thanh xau Unicode  - Ten rieng           *
 *       UUpper:   Convert xau Unicode thanh xau Unicode - Chu hoa              *
 *       ULower:   Convert xau Unicode thanh xau Unicode - Chu thuong           *
 *       VnonMark: Convert xau TCVN3 thanh xau TCVN3 - Khong dau va chu thuong  *
 *       toU:      Convert xau TCVN3 thanh xau Unicode de ghi ra trang Web      *
 *       ReplaceAll(xau nguon, xau can thay the, xau thay the)                  *
 * Chu y:                                                                       *
 *   Ham DtoUname khong the convert cac ky tu hoa khong thuoc bang ma TCVN3     *
 *   thanh chu hoa. Neu su dung 2 ham Uname(DtoU) thi co the nhung cham hon     *
 *       DtoUname = VN8VN3 -> TCVN3 -> TCVN3 Name -> UNICODE                    *
 *    Uname(DtoU) = VN8VN3 -> TCVN3 ->  UNICODE -> UNICODE Name                 *
 *   Unicode o day la dang ky tu Web - chang han: &#7890; (chuan windows-1252)  *
 *                       Dang Ha Trung                                          *
 * --Ha Thai Bao----
 * --ngay sua doi: 2007/01/14
 * --noi dung sua doi:
 *     +Tao cac mang luu ma font de tuy cap theo index thay cho su dung vong lap FOR de tim ma
 *     +Tao mang tim kiem theo kieu nhi phan khi convert Unicode
 *******************************************************************************/
 package vn.com.vsc.ptpm.VNPT_DMS.control;

 import java.io.FileWriter;
import java.util.ArrayList;
 //import oracle.sql.CharacterSet;
 //import oracle.sql.converter.CharacterConverter;

// import org.htmlparser.util.Translate;

 public class ConvertFont3
 {
     public static final int VN8VN3 = 1;
     public static final int VN8VN3_NAME = 2;
     public static final int VN8VN3_NO_SIGN = 3;
     public static final int VN8VN3_UPPER = 4;
     public static final int VN8VN3_LOWER = 5;

     public static final int TCVN = 6;
     //public static final int TCVN_NAME = 7;
     //public static final int TCVN_NO_SIGN = 8;
     //public static final int TCVN_UPPER = 9;
     //public static final int TCVN_LOWER = 10;

     public static final int UNICODE_2_WEBUNICODE = 7;


     public static String[] _FontArr = {"VN8VN3", "VN8VN3_NAME", "VN8VN3_NO_SIGN", "VN8VN3_UPPER", "VN8VN3_LOWER"
                             , "TCVN", "UNICODE_2_WEBUNICODE", "-8", "-9", "-10"};

     private static int search(int searchKey, int lowerBound, int upperBound)
     {
         int curIn = (lowerBound + upperBound) / 2;
         if(unicode_idx[curIn] == searchKey)
             return curIn;
         if(lowerBound > upperBound)
             return -1;
         if(unicode_idx[curIn] < searchKey)
             return search(searchKey, curIn + 1, upperBound);
         else
             return search(searchKey, lowerBound, curIn - 1);
     }

     private static int binarySearch(int searchKey)
     {
         return search(searchKey, 0, unicode_idx.length - 1);
     }

     public ConvertFont3()
     {
     }

     public static String VN8VN3toNONMARK(String s)
     {
         if(s == null || s.equals(""))
             return "";
         String res = "";
         for(int i = 0; i < s.length(); i++)
         {
             int c = s.charAt(i);
             if(c < 128)
             {
                 res = res + s.charAt(i);
                 continue;
             }
             if(c > 255)
                 res = res + " ";
             else
                 res = res + vn8vn3_nonmark[c - 128];
         }

         return res;
     }

     public static String VN8VN3toUPPER(String s)
     {
         if(s == null || s.equals(""))
             return "";
         String res = "";
         for(int i = 0; i < s.length(); i++)
         {
             int c = s.charAt(i);
             if(c < 32)
             {
                 res = res + s.charAt(i);
                 continue;
             }
             if(c > 255)
                 res = res + " ";
             else
                 res = res + vn8vn3_upper[c - 32];
         }

         return res;
     }

     public static String VN8VN3toLOWER(String s)
     {
         if(s == null || s.equals(""))
             return "";
         String res = "";
         for(int i = 0; i < s.length(); i++)
         {
             int c = s.charAt(i);
             if(c < 32)
             {
                 res = res + s.charAt(i);
                 continue;
             }
             if(c > 255)
                 res = res + " ";
             else
                 res = res + vn8vn3_lower[c - 32];
         }

         return res;
     }

     public static String W1252toVN8VN3(String s)
     {
         if(s == null || s.equals(""))
             return "";
         int i = 0;
         String name = "";
         String s1 = "";
         int c = 0;
         boolean startFound = false;
         for(; i < s.length(); i++)
         {
             if(s.charAt(i) == '&')
             {
                 if(name.length() > 0)
                 {
                     s1 = s1 + name;
                     continue;
                 }
                 if(i < s.length() - 1)
                 {
                     if(s.charAt(i + 1) == '#')
                     {
                         startFound = true;
                         name = "";
                         i++;
                         continue;
                     }
                     if(s.substring(i + 1, i + 6).equalsIgnoreCase("nbsp;"))
                     {
                         s1 = s1 + " ";
                         i += 6;
                     } else
                     {
                         s1 = s1 + s.charAt(i);
                     }
                 } else
                 {
                     s1 = s1 + s.charAt(i);
                 }
                 continue;
             }
             if(s.charAt(i) == ';')
             {
                 if(name.length() > 0)
                 {
                     if(name == "nbsp")
                     {
                         s1 = s1 + " ";
                     } else
                     {
                         c = Integer.parseInt(name);
                         if(c < 32)
                         {
                             s1 = s1 + (char)c;
                         } else
                         {
                             int idx = binarySearch(c);
                             if(idx > 0)
                                 s1 = s1 + (char)vn8vn3_on_unicode_idx[idx];
                             else
                             if(c < 255)
                                 s1 = s1 + (char)c;
                             else
                                 s1 = s1 + "?";
                         }
                         startFound = false;
                     }
                     name = "";
                 } else
                 {
                     s1 = s1 + s.charAt(i);
                 }
                 continue;
             }
             if(!startFound)
                 s1 = s1 + s.charAt(i);
             else
                 name = name + s.charAt(i);
         }

         if(name.length() > 0)
             s1 = s1 + name;
         return s1;
     }

     public static String Utf8ToVN8VN3(String s)
         {
             if(s == null || s.equals(""))
                 return "";

             int i = 0;
             String s1 = "";
             int c = 0;
             boolean startFound = false;
             for(; i < s.length(); i++)
             {
                 c = (int)s.charAt(i);
                 if(c < 128)
                 {
                     s1 = s1 + (char)c;
                 } else
                 {
                     int idx = binarySearch(c);
                     if(idx > 0)
                         s1 = s1 + (char)vn8vn3_on_unicode_idx[idx];
                     else
                         s1 = s1 + (char)c;
                 }
             }

             return s1;
    }
     public static String W1252toUTF8(String s)
     {
         if(s == null || s.equals(""))
             return "";
         int i = 0;
         String name = "";
         String s1 = "";
         int c = 0;
         boolean startFound = false;
         for(; i < s.length(); i++)
         {
             if(s.charAt(i) == '&')
             {
                 if(name.length() > 0)
                     s1 = s1 + name;
                 if(i < s.length() - 1)
                 {
                     if(s.charAt(i + 1) == '#')
                     {
                         startFound = true;
                         name = "";
                         i++;
                     } else
                     {
                         s1 = s1 + s.charAt(i);
                     }
                 } else
                 {
                     s1 = s1 + s.charAt(i);
                 }
                 continue;
             }
             if(s.charAt(i) == ';')
             {
                 if(name.length() > 0)
                 {
                     if(name == "nbsp")
                     {
                         s1 = s1 + " ";
                         continue;
                     }
                     c = Integer.parseInt(name);
                     if(c < 128)
                         s1 = s1 + (char)c;
                     else
                     if(c > 127 && c < 2048)
                     {
                         s1 = s1 + (char)(c >> 6 | 0xc0);
                         s1 = s1 + (char)(c & 0x3f | 0x80);
                     } else
                     {
                         s1 = s1 + (char)(c >> 12 | 0xe0);
                         s1 = s1 + (char)(c >> 6 & 0x3f | 0x80);
                         s1 = s1 + (char)(c & 0x3f | 0x80);
                     }
                     startFound = false;
                     name = "";
                 } else
                 {
                     s1 = s1 + s.charAt(i);
                 }
                 continue;
             }
             if(!startFound)
                 s1 = s1 + s.charAt(i);
             else
                 name = name + s.charAt(i);
         }

         if(name.length() > 0)
             s1 = s1 + name;
         return s1;
     }

     public static String decodeW1252(String s)
     {
         if(s == null || s.equals(""))
             return "";
         int i = 0;
         String name = "";
         StringBuffer sb_ = new StringBuffer();
         int c = 0;
         boolean startFound = false;
         for(; i < s.length(); i++)
         {
             if(s.charAt(i) == '&')
             {
                 if(name.length() > 0)
                     sb_.append(name); // s1 = s1 + name;
                 if(i < s.length() - 1)
                 {
                     if(s.charAt(i + 1) == '#')
                     {
                         startFound = true;
                         name = "";
                         i++;
                     } else
                     {
                         sb_.append(s.charAt(i)); //s1 = s1 + s.charAt(i);
                     }
                 } else
                 {
                     sb_.append(s.charAt(i)); //s1 = s1 + s.charAt(i);
                 }
                 continue;
             }
             if(s.charAt(i) == ';')
             {
                 if(name.length() > 0)
                 {
                     if(name == "nbsp")
                     {
                         sb_.append(" "); //s1 = s1 + " ";
                     } else
                     {
                         c = Integer.parseInt(name);
                         sb_.append((char)c); //s1 = s1 + (char)c;
                         startFound = false;
                         name = "";
                     }
                 } else
                 {
                     sb_.append(s.charAt(i)); //s1 = s1 + s.charAt(i);
                 }
                 continue;
             }
             if(!startFound)
             {
                 if(s.charAt(i) == '\240')
                     sb_.append(" "); //s1 = s1 + " ";
                 else
                     sb_.append(s.charAt(i)); //s1 = s1 + s.charAt(i);
             } else
             {
                 name = name + s.charAt(i);
             }
         }

         if(name.length() > 0) {
             sb_.append(name); // s1 = s1 + name;
         }
         return sb_.toString();
     }

     public static String VN8VN3toW1252(String s)
     {
         if(s == null || s.equals(""))
             return "";
         String result = "";
         for(int i = 0; i < s.length(); i++)
         {
             int c = s.charAt(i);
             if(c == 32)
             {
                 if(i + 1 < s.length() && s.charAt(i + 1) == ' ')
                 {
                     result = result + " &nbsp;";
                     i++;
                 } else
                 {
                     result = result + " ";
                 }
                 continue;
             }
             if(c == 13)
             {
                 result = result + "\n";
                 continue;
             }
             if(c == 10)
                 continue;
             if(c == 9)
             {
                 result = result + "&nbsp;&nbsp;&nbsp;&nbsp;";
                 continue;
             }
             if(c > 254)
             {
                 result = result + "&#" + c + ";";
                 continue;
             }
             if(c < 32)
             {
                 result = result + s.charAt(i);
                 continue;
             }
             int unicode = vn8vn3_unicode[c - 32];
             if(c == unicode)
                 result = result + s.charAt(i);
             else
                 result = result + "&#" + unicode + ";";
         }

         return result;
     }

     public static String encodeW1252(String s)
     {
         if(s == null || s.equals(""))
             return "";
         StringBuffer sb_ = new StringBuffer();

         for(int i = 0; i < s.length(); i++)
         {
             int c = s.charAt(i);
             if(c == 32)
             {
                 if(i + 1 < s.length() && s.charAt(i + 1) == ' ')
                 {
                     sb_.append(" &nbsp;"); // result = result + " &nbsp;";
                     i++;
                 } else
                 {
                     sb_.append(" "); //result = result + " ";
                 }
                 continue;
             }
             if(c == 13)
             {
                 sb_.append("\n"); //result = result + "\n";
                 continue;
             }
             if(c == 10)
                 continue;
             if(c == 9)
             {
                 sb_.append("&nbsp;&nbsp;&nbsp;&nbsp;"); //result = result + "&nbsp;&nbsp;&nbsp;&nbsp;";
                 continue;
             }
             if(c < 128)
                 sb_.append(s.charAt(i)); //result = result + s.charAt(i);
             else
                 sb_.append("&#" + c + ";"); // result = result + "&#" + c + ";";
         }

         return sb_.toString();
     }

     public static String VN8VN3toVname(String s)
     {
         boolean neeedUpper = true;
         String res = "";
         for(int i = 0; i < s.length(); i++)
         {
             char c = s.charAt(i);
             if(c == ' ')
             {
                 res = res + c;
                 neeedUpper = true;
                 continue;
             }
             if(neeedUpper)
             {
                 if(c < ' ')
                 {
                     res = res + c;
                 } else
                 {
                     res = res + vn8vn3_upper[c - 32];
                     neeedUpper = false;
                 }
             } else
             {
                 res = res + c;
             }
         }

         return res;
     }

     public static void insertUnicode()
     {
         String unicode[] = {
             "\301", "\300", "&#7842;", "\303", "&#7840;", "\341", "\340", "&#7843;", "\343", "&#7841;",
             "\302", "&#7844;", "&#7846;", "&#7848;", "&#7850;", "&#7852;", "\342", "&#7845;", "&#7847;", "&#7849;",
             "&#7851;", "&#7853;", "&#258;", "&#7854;", "&#7856;", "&#7858;", "&#7860;", "&#7862;", "&#259;", "&#7855;",
             "&#7857;", "&#7859;", "&#7861;", "&#7863;", "\311", "\310", "&#7866;", "&#7868;", "&#7864;", "\351",
             "\350", "&#7867;", "&#7869;", "&#7865;", "\312", "&#7870;", "&#7872;", "&#7874;", "&#7876;", "&#7878;",
             "\352", "&#7871;", "&#7873;", "&#7875;", "&#7877;", "&#7879;", "\315", "\314", "&#7880;", "&#296;",
             "&#7882;", "\355", "\354", "&#7881;", "&#297;", "&#7883;", "\323", "\322", "&#7886;", "\325",
             "&#7884;", "\363", "\362", "&#7887;", "\365", "&#7885;", "\324", "&#7888;", "&#7890;", "&#7892;",
             "&#7894;", "&#7896;", "\364", "&#7889;", "&#7891;", "&#7893;", "&#7895;", "&#7897;", "&#416;", "&#7898;",
             "&#7900;", "&#7902;", "&#7904;", "&#7906;", "&#417;", "&#7899;", "&#7901;", "&#7903;", "&#7905;", "&#7907;",
             "\332", "\331", "&#7910;", "&#360;", "&#7908;", "\372", "\371", "&#7911;", "&#361;", "&#7909;",
             "&#431;", "&#7912;", "&#7914;", "&#7916;", "&#7918;", "&#7920;", "&#432;", "&#7913;", "&#7915;", "&#7917;",
             "&#7919;", "&#7921;", "\335", "&#7922;", "&#7926;", "&#7928;", "&#7924;", "\375", "&#7923;", "&#7927;",
             "&#7929;", "&#7925;", "&#273;", "&#272;"
         };
         char tcvn3[] = {
             '\270', '\265', '\266', '\267', '\271', '\270', '\265', '\266', '\267', '\271',
             '\242', '\312', '\307', '\310', '\311', '\313', '\251', '\312', '\307', '\310',
             '\311', '\313', '\241', '\276', '\273', '\274', '\275', '\306', '\250', '\276',
             '\273', '\274', '\275', '\306', '\320', '\314', '\316', '\317', '\321', '\320',
             '\314', '\316', '\317', '\321', '\243', '\325', '\322', '\323', '\324', '\326',
             '\252', '\325', '\322', '\323', '\324', '\326', '\335', '\327', '\330', '\334',
             '\336', '\335', '\327', '\330', '\334', '\336', '\343', '\337', '\341', '\342',
             '\344', '\343', '\337', '\341', '\342', '\344', '\244', '\350', '\345', '\346',
             '\347', '\351', '\253', '\350', '\345', '\346', '\347', '\351', '\245', '\355',
             '\352', '\353', '\354', '\356', '\254', '\355', '\352', '\353', '\354', '\356',
             '\363', '\357', '\361', '\362', '\364', '\363', '\357', '\361', '\362', '\364',
             '\246', '\370', '\365', '\366', '\367', '\371', '\255', '\370', '\365', '\366',
             '\367', '\371', '\375', '\372', '\373', '\374', '\376', '\375', '\372', '\373',
             '\374', '\376', '\256', '\247'
         };
         try
         {
             FileWriter writer = new FileWriter("c:\\a.sql");
             for(int i = 0; i < tcvn3.length; i++)
             {
                 int c = tcvn3[i];
                 int u = 0;
                 if(unicode[i].length() > 3)
                 {
                     u = Integer.parseInt(unicode[i].substring(2, unicode[i].length() - 1));
                     if(u == 7892)
                         u = 7892;
                 } else
                 {
                     u = unicode[i].charAt(0);
                 }
                 writer.write("insert into unicode(unicode,vn8vn3) values (" + u + "," + c + ");\n");
             }

             writer.flush();
             writer.close();
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
     }

     public static String toWebUnicode(String s, String fromFont)
     {
         if(fromFont.equalsIgnoreCase("VN8VN3"))
         {
             if(s != null)
                 s = DtoU(s);
         } else
         if(fromFont.equalsIgnoreCase("TCVN") && s != null)
             s = toU(s);
         return s;
     }

     /*public static String convertFromVN8VN3(String s)
     {
         try
         {
             ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
             CharacterSet characterset = CharacterSet.make(44);
             byte abyte0[] = characterset.convert(s);
             bytearrayoutputstream.write(abyte0, 0, abyte0.length);
             return bytearrayoutputstream.toString();
         }
         catch(Exception _ex)
         {
             return s;
         }
     }*/

     /*public static String convertToVN8VN3(String s)
     {
         try
         {
             CharacterConverter characterconverter = CharacterConverter.getInstance(44);
             byte abyte0[] = s.getBytes("windows-1252");
             String s1 = characterconverter.toUnicodeString(abyte0, 0, abyte0.length);
             return s1;
         }
         catch(Exception _ex)
         {
             return s;
         }
     }*/

     public static String DtoU(String s)
     {
        return encodeW1252(s);
     }

     /*public static String DtoUname(String s)
     {
         if(s == null || s.equals(""))
             return "";
         else
             return toU(Vname(convertFromVN8VN3(s)));
     }*/

     public static String UtoD(String s)
     {
         if(s == null || s.equals(""))
             return "";
         else
             return decodeW1252(s);
     }

     /*public static String UtoDname(String s)
     {
         if(s == null || s.equals(""))
             return "";
         else
             return convertToVN8VN3(Vname(toV(s)));
     }*/

     public static String toV(String s)
     {
         if(s == null || s.equals(""))
             return "";
         s = ReplaceAll(s, "&nbsp;", " ");
         String result = "";
         String temp = "";
         String pt = null;
         int i = 0;
         int len = 0;
         for(; i < s.length(); i++)
         {
             pt = "";
             char c = s.charAt(i);
             if(c == '&' && temp.length() > 0)
             {
                 result = result + uChr2tcvn(temp);
                 temp = "";
             }
             len = temp.length();
             switch(len)
             {
             case 0: // '\0'
                 if(c != '&')
                     pt = pt + c;
                 else
                     temp = String.valueOf(c);
                 break;

             case 1: // '\001'
                 if(c != '#')
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 2: // '\002'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 3: // '\003'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 4: // '\004'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 5: // '\005'
                 if(c == ';')
                     pt = temp + c;
                 else
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 6: // '\006'
                 if(c == ';')
                     pt = temp + c;
                 else
                     pt = temp + c;
                 break;
             }
             if(pt.length() != 0)
             {
                 result = result + uChr2tcvn(pt);
                 temp = "";
             }
         }

         return result;
     }

     public static String toUTF(String sUrlEncode)
     {
         String s = sUrlEncode;
         if(s == null || s.equals(""))
             return "";
         s = ReplaceAll(s, "&nbsp;", " ");
         int i = 0;
         String name = "";
         String s1 = "";
         int c = 0;
         boolean startFound = false;
         for(; i < s.length(); i++)
         {
             if(s.charAt(i) == '&')
             {
                 if(name.length() > 0)
                     s1 = s1 + name;
                 if(i < s.length() - 1)
                 {
                     if(s.charAt(i + 1) == '#')
                     {
                         startFound = true;
                         name = "";
                         i++;
                     } else
                     {
                         s1 = s1 + s.charAt(i);
                     }
                 } else
                 {
                     s1 = s1 + s.charAt(i);
                 }
                 continue;
             }
             if(s.charAt(i) == ';')
             {
                 if(name.length() > 0)
                 {
                     c = Integer.parseInt(name);
                     if(c < 128)
                         s1 = s1 + (char)c;
                     else
                     if(c > 127 && c < 2048)
                     {
                         s1 = s1 + (char)(c >> 6 | 0xc0);
                         s1 = s1 + (char)(c & 0x3f | 0x80);
                     } else
                     {
                         s1 = s1 + (char)(c >> 12 | 0xe0);
                         s1 = s1 + (char)(c >> 6 & 0x3f | 0x80);
                         s1 = s1 + (char)(c & 0x3f | 0x80);
                     }
                     startFound = false;
                     name = "";
                 } else
                 {
                     s1 = s1 + s.charAt(i);
                 }
                 continue;
             }
             if(!startFound)
                 s1 = s1 + s.charAt(i);
             else
                 name = name + s.charAt(i);
         }

         if(name.length() > 0)
             s1 = s1 + name;
         return s1;
     }

     public static String toU(String s)
     {
         if(s == null || s.equals(""))
             return "";
         s = ReplaceAll(s, "  ", " &nbsp;");
         String result = s;
         char tcvn3[] = {
             '\270', '\265', '\271', '\267', '\266', '\251', '\312', '\307', '\310', '\311',
             '\313', '\250', '\276', '\273', '\274', '\275', '\306', '\320', '\314', '\316',
             '\317', '\321', '\252', '\325', '\322', '\323', '\324', '\326', '\335', '\327',
             '\330', '\334', '\336', '\343', '\337', '\341', '\342', '\344', '\253', '\350',
             '\345', '\346', '\347', '\351', '\254', '\355', '\352', '\353', '\354', '\356',
             '\363', '\357', '\361', '\362', '\364', '\255', '\370', '\365', '\366', '\367',
             '\371', '\375', '\372', '\373', '\374', '\376', '\242', '\241', '\243', '\244',
             '\245', '\246', '\256', '\247', '\r', '\n', '\t'
         };
         String unicode[] = {
             "&#225;", "&#224;", "&#7841;", "&#227;", "&#7843;", "&#226;", "&#7845;", "&#7847;", "&#7849;", "&#7851;",
             "&#7853;", "&#259;", "&#7855;", "&#7857;", "&#7859;", "&#7861;", "&#7863;", "&#233;", "&#232;", "&#7867;",
             "&#7869;", "&#7865;", "&#234;", "&#7871;", "&#7873;", "&#7875;", "&#7877;", "&#7879;", "&#237;", "&#236;",
             "&#7881;", "&#297;", "&#7883;", "&#243;", "&#242;", "&#7887;", "&#245;", "&#7885;", "&#244;", "&#7889;",
             "&#7891;", "&#7893;", "&#7895;", "&#7897;", "&#417;", "&#7899;", "&#7901;", "&#7903;", "&#7905;", "&#7907;",
             "&#250;", "&#249;", "&#7911;", "&#361;", "&#7909;", "&#432;", "&#7913;", "&#7915;", "&#7917;", "&#7919;",
             "&#7921;", "&#253", "&#7923;", "&#7927;", "&#7929;", "&#7925;", "&#194;", "&#258;", "&#202;", "&#212;",
             "&#416;", "&#431;", "&#273;", "&#272;", "<br>", "", "&nbsp;&nbsp;&nbsp;"
         };
         int len = result.length();
         if(len == 0)
             return result;
         for(int j = 0; j < len; j++)
         {
             char temp = result.charAt(j);
             int i = 0;
             do
             {
                 if(i >= 77)
                     break;
                 if(temp == tcvn3[i])
                 {
                     if(j + 1 < len)
                         result = result.substring(0, j) + unicode[i] + result.substring(j + 1, len);
                     else
                         result = result.substring(0, j) + unicode[i];
                     j = (j + unicode[i].length()) - 1;
                     break;
                 }
                 i++;
             } while(true);
             len = result.length();
         }

         return result;
     }

     public static String VnonMark(String $s)
     {
         char s[] = $s.toCharArray();
         for(int i = 0; i < s.length; i++)
         {
             char c = achrtolower(s[i]);
             if(c == '\270' || c == '\265' || c == '\266' || c == '\267' || c == '\271' || c == '\250' || c == '\276' || c == '\273' || c == '\274' || c == '\275' || c == '\306' || c == '\251' || c == '\312' || c == '\307' || c == '\310' || c == '\311' || c == '\313')
                 s[i] = 'a';
             if(c == '\256')
                 s[i] = 'd';
             if(c == '\320' || c == '\314' || c == '\316' || c == '\317' || c == '\321' || c == '\252' || c == '\325' || c == '\322' || c == '\323' || c == '\324' || c == '\326')
                 s[i] = 'e';
             if(c == '\335' || c == '\327' || c == '\330' || c == '\334' || c == '\336')
                 s[i] = 'i';
             if(c == '\343' || c == '\337' || c == '\341' || c == '\342' || c == '\344' || c == '\253' || c == '\350' || c == '\345' || c == '\346' || c == '\347' || c == '\351' || c == '\254' || c == '\355' || c == '\352' || c == '\353' || c == '\354' || c == '\356')
                 s[i] = 'o';
             if(c == '\363' || c == '\357' || c == '\362' || c == '\361' || c == '\364' || c == '\255' || c == '\370' || c == '\365' || c == '\366' || c == '\367' || c == '\371')
                 s[i] = 'u';
             if(c == '\375' || c == '\372' || c == '\373' || c == '\374' || c == '\376')
                 s[i] = 'y';
         }

         $s = String.valueOf(s);
         $s = $s.trim();
         return $s;
     }

     public static String Uname(String s)
     {
         if(s == null || s.equals(""))
             return "";
         s = ReplaceAll(s, "&nbsp;", " ");
         s = ReplaceAll(s, "  ", " ");
         s = s.trim();
         if(s.equals(""))
             return "";
         ArrayList arr = new ArrayList();
         String result = "";
         String temp = "";
         String pt = null;
         int i = 0;
         int len = 0;
         for(; i < s.length(); i++)
         {
             pt = "";
             char c = s.charAt(i);
             if(c == '&' && temp.length() > 0)
             {
                 arr.add(temp);
                 temp = "";
             }
             len = temp.length();
             switch(len)
             {
             case 0: // '\0'
                 if(c != '&')
                     pt = pt + c;
                 else
                     temp = String.valueOf(c);
                 break;

             case 1: // '\001'
                 if(c != '#')
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 2: // '\002'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 3: // '\003'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 4: // '\004'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 5: // '\005'
                 if(c == ';')
                     pt = temp + c;
                 else
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 6: // '\006'
                 if(c == ';')
                     pt = temp + c;
                 else
                     pt = temp + c;
                 break;
             }
             if(pt.length() != 0)
             {
                 arr.add(pt);
                 temp = "";
             }
         }

         len = arr.size();
         i = 0;
         String index = null;
         String beforeIndex = " ";
         for(; i < len; i++)
         {
             index = (String)arr.get(i);
             if(beforeIndex.equals(" "))
                 result = result + uChr2upper(index);
             else
                 result = result + uChr2lower(index);
             beforeIndex = index;
         }

         return result;
     }

     public static String Vname(String s)
     {
         s = s.trim();
         if(s.equals(""))
             return s;
         char c[] = s.toCharArray();
         int len = c.length;
         for(int i = 0; i < len; i++)
             c[i] = achrtolower(c[i]);

         c[0] = achrtoupper(c[0]);
         for(int i = 0; i < len - 1; i++)
         {
             if(c[i] != ' ')
                 continue;
             if(c[i + 1] != ' ')
             {
                 c[i + 1] = achrtoupper(c[i + 1]);
                 continue;
             }
             for(int j = i; j < len - 1; j++)
                 c[j] = c[j + 1];

             len--;
             i--;
         }

         s = String.valueOf(c, 0, len);
         s = s.trim();
         return s;
     }

     public static String VUpper(String s)
     {
         int len = s.length();
         if(len == 0)
             return s;
         char c[] = s.toCharArray();
         for(int i = 0; i < len; i++)
             c[i] = achrtoupper(c[i]);

         s = String.valueOf(c);
         return s;
     }

     public static String VLower(String s)
     {
         int len = s.length();
         if(len == 0)
             return s;
         char c[] = s.toCharArray();
         for(int i = 0; i < len; i++)
             c[i] = achrtolower(c[i]);

         s = String.valueOf(c);
         return s;
     }

     public static String UUpper(String s)
     {
         if(s == null || s.equals(""))
             return "";
         String result = "";
         String temp = "";
         String pt = null;
         int i = 0;
         int len = 0;
         for(; i < s.length(); i++)
         {
             pt = "";
             char c = s.charAt(i);
             if(c == '&' && temp.length() > 0)
             {
                 result = result + uChr2upper(temp);
                 temp = "";
             }
             len = temp.length();
             switch(len)
             {
             case 0: // '\0'
                 if(c != '&')
                     pt = pt + c;
                 else
                     temp = String.valueOf(c);
                 break;

             case 1: // '\001'
                 if(c != '#')
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 2: // '\002'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 3: // '\003'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 4: // '\004'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 5: // '\005'
                 if(c == ';')
                     pt = temp + c;
                 else
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 6: // '\006'
                 if(c == ';')
                     pt = temp + c;
                 else
                     pt = temp + c;
                 break;
             }
             if(pt.length() != 0)
             {
                 result = result + uChr2upper(pt);
                 temp = "";
             }
         }

         return result;
     }

     public static String ULower(String s)
     {
         if(s == null || s.equals(""))
             return "";
         String result = "";
         String temp = "";
         String pt = null;
         int i = 0;
         int len = 0;
         for(; i < s.length(); i++)
         {
             pt = "";
             char c = s.charAt(i);
             if(c == '&' && temp.length() > 0)
             {
                 result = result + uChr2lower(temp);
                 temp = "";
             }
             len = temp.length();
             switch(len)
             {
             case 0: // '\0'
                 if(c != '&')
                     pt = pt + c;
                 else
                     temp = String.valueOf(c);
                 break;

             case 1: // '\001'
                 if(c != '#')
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 2: // '\002'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 3: // '\003'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 4: // '\004'
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 5: // '\005'
                 if(c == ';')
                     pt = temp + c;
                 else
                 if(!Character.isDigit(c))
                     pt = temp + c;
                 else
                     temp = temp + c;
                 break;

             case 6: // '\006'
                 if(c == ';')
                     pt = temp + c;
                 else
                     pt = temp + c;
                 break;
             }
             if(pt.length() != 0)
             {
                 result = result + uChr2lower(pt);
                 temp = "";
             }
         }

         return result;
     }

     private static String uChr2upper(String c)
     {
         String returnValue = c;
         int len = c.length();
         if(len > 1)
         {
             c = c.substring(2, len - 1);
             int i = Integer.parseInt(c);
             if(i >= 7840 && i <= 7929 && i % 2 != 0 || i == 259 || i == 273 || i == 297 || i == 361 || i == 417 || i == 432)
                 returnValue = "&#" + --i + ";";
         } else
         {
             int i = c.charAt(0);
             if(i >= 97 && i <= 122 || i >= 224 && i <= 253)
                 returnValue = String.valueOf((char)(i - 32));
         }
         if(returnValue.length() == len)
             return returnValue;
         else
             return c;
     }

     private static String uChr2lower(String c)
     {
         String returnValue = c;
         int len = c.length();
         if(len > 1)
         {
             c = c.substring(2, len - 1);
             int i = Integer.parseInt(c);
             if(i >= 7840 && i <= 7929 && i % 2 == 0 || i == 258 || i == 272 || i == 296 || i == 360 || i == 416 || i == 431)
                 returnValue = "&#" + ++i + ";";
         } else
         {
             int i = c.charAt(0);
             if(i >= 65 && i <= 90 || i >= 192 && i <= 221)
                 returnValue = String.valueOf((char)(i + 32));
         }
         if(returnValue.length() == len)
             return returnValue;
         else
             return c;
     }

     private static char achrtoupper(char c)
     {
         if(c >= 'a' && c <= 'z')
             c -= ' ';
         if(c == '\251')
             c = '\242';
         if(c == '\250')
             c = '\241';
         if(c == '\256')
             c = '\247';
         if(c == '\252')
             c = '\243';
         if(c == '\253')
             c = '\244';
         if(c == '\254')
             c = '\245';
         if(c == '\255')
             c = '\246';
         return c;
     }

     private static char achrtolower(char c)
     {
         if(c >= 'A' && c <= 'Z')
             c += ' ';
         if(c == '\241')
             c = '\250';
         if(c == '\242')
             c = '\251';
         if(c == '\247')
             c = '\256';
         if(c == '\243')
             c = '\252';
         if(c == '\244')
             c = '\253';
         if(c == '\245')
             c = '\254';
         if(c == '\246')
             c = '\255';
         return c;
     }

     private static String uChr2tcvn(String s)
     {
         String unicode[] = {
             "\301", "\300", "&#7842;", "\303", "&#7840;", "\341", "\340", "&#7843;", "\343", "&#7841;",
             "\302", "&#7844;", "&#7846;", "&#7848;", "&#7850;", "&#7852;", "\342", "&#7845;", "&#7847;", "&#7849;",
             "&#7851;", "&#7853;", "&#258;", "&#7854;", "&#7856;", "&#7858;", "&#7860;", "&#7862;", "&#259;", "&#7855;",
             "&#7857;", "&#7859;", "&#7861;", "&#7863;", "\311", "\310", "&#7866;", "&#7868;", "&#7864;", "\351",
             "\350", "&#7867;", "&#7869;", "&#7865;", "\312", "&#7870;", "&#7872;", "&#7874;", "&#7876;", "&#7878;",
             "\352", "&#7871;", "&#7873;", "&#7875;", "&#7877;", "&#7879;", "\315", "\314", "&#7880;", "&#296;",
             "&#7882;", "\355", "\354", "&#7881;", "&#297;", "&#7883;", "\323", "\322", "&#7886;", "\325",
             "&#7884;", "\363", "\362", "&#7887;", "\365", "&#7885;", "\324", "&#7888;", "&#7890;", "&#7892;",
             "&#7894;", "&#7896;", "\364", "&#7889;", "&#7891;", "&#7893;", "&#7895;", "&#7897;", "&#416;", "&#7898;",
             "&#7900;", "&#7902;", "&#7904;", "&#7906;", "&#417;", "&#7899;", "&#7901;", "&#7903;", "&#7905;", "&#7907;",
             "\332", "\331", "&#7910;", "&#360;", "&#7908;", "\372", "\371", "&#7911;", "&#361;", "&#7909;",
             "&#431;", "&#7912;", "&#7914;", "&#7916;", "&#7918;", "&#7920;", "&#432;", "&#7913;", "&#7915;", "&#7917;",
             "&#7919;", "&#7921;", "\335", "&#7922;", "&#7926;", "&#7928;", "&#7924;", "\375", "&#7923;", "&#7927;",
             "&#7929;", "&#7925;", "&#273;", "&#272;"
         };
         char tcvn3[] = {
             '\270', '\265', '\266', '\267', '\271', '\270', '\265', '\266', '\267', '\271',
             '\242', '\312', '\307', '\310', '\311', '\313', '\251', '\312', '\307', '\310',
             '\311', '\313', '\241', '\276', '\273', '\274', '\275', '\306', '\250', '\276',
             '\273', '\274', '\275', '\306', '\320', '\314', '\316', '\317', '\321', '\320',
             '\314', '\316', '\317', '\321', '\243', '\325', '\322', '\323', '\324', '\326',
             '\252', '\325', '\322', '\323', '\324', '\326', '\335', '\327', '\330', '\334',
             '\336', '\335', '\327', '\330', '\334', '\336', '\343', '\337', '\341', '\342',
             '\344', '\343', '\337', '\341', '\342', '\344', '\244', '\350', '\345', '\346',
             '\347', '\351', '\253', '\350', '\345', '\346', '\347', '\351', '\245', '\355',
             '\352', '\353', '\354', '\356', '\254', '\355', '\352', '\353', '\354', '\356',
             '\363', '\357', '\361', '\362', '\364', '\363', '\357', '\361', '\362', '\364',
             '\246', '\370', '\365', '\366', '\367', '\371', '\255', '\370', '\365', '\366',
             '\367', '\371', '\375', '\372', '\373', '\374', '\376', '\375', '\372', '\373',
             '\374', '\376', '\256', '\247'
         };
         int len = unicode.length;
         for(int i = 0; i < len; i++)
             if(s.equals(unicode[i]))
                 return String.valueOf(tcvn3[i]);

         return s;
     }

     public static String ReplaceAll(String s, String sn, String sd)
     {
         int i = 0;
         int lenn = sn.length();
         int j = 0;
         String result = "";
         do
         {
             i = s.indexOf(sn);
             if(i == -1)
             {
                 result = result + s.substring(j, s.length());
                 break;
             }
             result = result + s.substring(j, i) + sd;
             s = s.substring(0, i) + s.substring(i + lenn, s.length());
             j = i;
         } while(true);
         if(result.length() == 0)
             result = s;
         return result;
     }

     public static int length(String s)
     {
         if(s == null || s.equals(""))
             return 0;
         try
         {
             int result = 0;
             String temp = "";
             String pt = null;
             int i = 0;
             int len = 0;
             for(; i < s.length(); i++)
             {
                 pt = "";
                 char c = s.charAt(i);
                 if(c == '&' && temp.length() > 0)
                 {
                     result++;
                     temp = "";
                 }
                 len = temp.length();
                 switch(len)
                 {
                 case 0: // '\0'
                     if(c != '&')
                         pt = pt + c;
                     else
                         temp = String.valueOf(c);
                     break;

                 case 1: // '\001'
                     if(c != '#')
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 2: // '\002'
                     if(!Character.isDigit(c))
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 3: // '\003'
                     if(!Character.isDigit(c))
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 4: // '\004'
                     if(!Character.isDigit(c))
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 5: // '\005'
                     if(c == ';')
                         pt = temp + c;
                     else
                     if(!Character.isDigit(c))
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 6: // '\006'
                     if(c == ';')
                         pt = temp + c;
                     else
                         pt = temp + c;
                     break;
                 }
                 if(pt.length() != 0)
                 {
                     result++;
                     temp = "";
                 }
             }

             return result;
         }
         catch(Exception e)
         {
             System.out.println("Error: [com.cdit.portal.convert][length] " + e.getMessage());
         }
         return 0;
     }

     public static String substring(String s, int begin, int end)
     {
         if(begin > end || begin < 0)
             return "";
         if(s == null || s.equals(""))
             return "";
         try
         {
             String result = "";
             String temp = "";
             String pt = null;
             int i = 0;
             int len = 0;
             int index = 0;
             for(; i < s.length(); i++)
             {
                 pt = "";
                 char c = s.charAt(i);
                 if(c == '&' && temp.length() > 0)
                 {
                     if(begin < index && index <= end)
                         result = result + temp;
                     index++;
                     temp = "";
                 }
                 len = temp.length();
                 switch(len)
                 {
                 case 0: // '\0'
                     if(c != '&')
                         pt = pt + c;
                     else
                         temp = String.valueOf(c);
                     break;

                 case 1: // '\001'
                     if(c != '#')
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 2: // '\002'
                     if(!Character.isDigit(c))
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 3: // '\003'
                     if(!Character.isDigit(c))
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 4: // '\004'
                     if(!Character.isDigit(c))
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 5: // '\005'
                     if(c == ';')
                         pt = temp + c;
                     else
                     if(!Character.isDigit(c))
                         pt = temp + c;
                     else
                         temp = temp + c;
                     break;

                 case 6: // '\006'
                     if(c == ';')
                         pt = temp + c;
                     else
                         pt = temp + c;
                     break;
                 }
                 if(pt.length() == 0)
                     continue;
                 if(++index > end)
                     break;
                 if(begin < index && index <= end)
                     result = result + pt;
                 temp = "";
             }

             return result;
         }
         catch(Exception e)
         {
             System.out.println("Error: [com.cdit.portal.convert][substring]: " + e.getMessage());
         }
         return "";
     }

     public static String tcvn2Utf8(String s)
     {
         if(s == null || s.equals(""))
             return "";
         s = ReplaceAll(s, "  ", " &nbsp;");
         String result = s;
         char tcvn3[] = {
             '\270', '\265', '\271', '\267', '\266', '\251', '\312', '\307', '\310', '\311',
             '\313', '\250', '\276', '\273', '\274', '\275', '\306', '\320', '\314', '\316',
             '\317', '\321', '\252', '\325', '\322', '\323', '\324', '\326', '\335', '\327',
             '\330', '\334', '\336', '\343', '\337', '\341', '\342', '\344', '\253', '\350',
             '\345', '\346', '\347', '\351', '\254', '\355', '\352', '\353', '\354', '\356',
             '\363', '\357', '\361', '\362', '\364', '\255', '\370', '\365', '\366', '\367',
             '\371', '\375', '\372', '\373', '\374', '\376', '\242', '\241', '\243', '\244',
             '\245', '\246', '\256', '\247'
         };
         String unicode[] = {
             "\303\241", "\303\240", "\341\272\241", "\303\243", "\341\272\243", "\303\242", "\341\272\245", "\341\272\247", "\341\272\251", "\341\272\253",
             "\341\272\255", "\304\u0192", "\341\272\257", "\341\272\261", "\341\272\263", "\341\272\265", "\341\272\267", "\303\251", "\303\250", "\341\272\273",
             "\341\272\275", "\341\272\271", "\303\252", "\341\272\277", "\341\273\uFFFD", "\341\273\u0192", "\341\273\u2026", "\341\273\u2021", "\303\255", "\303\254",
             "\341\273\u2030", "\304\251", "\341\273\u2039", "\303\263", "\303\262", "\341\273\uFFFD", "\303\265", "\341\273\uFFFD", "\303\264", "\341\273\u2018",
             "\341\273\u201C", "\341\273\u2022", "\341\273\u2014", "\341\273\u2122", "\306\241", "\341\273\u203A", "\341\273\uFFFD", "\341\273\u0178", "\341\273\241", "\341\273\243",
             "\303\272", "\303\271", "\341\273\247", "\305\251", "\341\273\245", "\306\260", "\341\273\251", "\341\273\253", "\341\273\255", "\341\273\257",
             "\341\273\261", "\303\275", "\341\273\263", "\341\273\267", "\341\273\271", "\341\273\265", "\303\u201A", "\304\u201A", "\303\u0160", "\303\u201D",
             "\306\240", "\306\257", "\304\u2018", "\304\uFFFD"
         };
         int len = result.length();
         if(len == 0)
             return result;
         for(int j = 0; j < len; j++)
         {
             char temp = result.charAt(j);
             for(int i = 0; i < 74; i++)
             {
                 if(temp != tcvn3[i])
                     continue;
                 if(j + 1 < len)
                     result = result.substring(0, j) + unicode[i] + result.substring(j + 1, len);
                 else
                     result = result.substring(0, j) + unicode[i];
                 j = (j + unicode[i].length()) - 1;
             }

             len = result.length();
         }

         return result;
     }

     public static String killHtmltag(String s)
     {
         try
         {
             return ReplaceAll(s, "<", "&lt;");
         }
         catch(Exception e)
         {
             System.out.println("Error: [com.cdit.portal.convert][substring]: " + e.getMessage());
         }
         return "";
     }

     public static void main(String args[])
     {
         String s = "VN8VN3-->: T&#7892;NG C&#212;NG TY B&#431;U CH&#205;NH VI&#7876;N TH&#212;NG VI&#7878;T NAM";
         System.out.println(VN8VN3toNONMARK(W1252toVN8VN3(s)));
     }

     public static int unicode_idx[] = {
         192, 193, 194, 195, 200, 201, 202, 204, 205, 210,
         211, 212, 213, 217, 218, 221, 224, 225, 226, 227,
         232, 233, 234, 236, 237, 242, 243, 244, 245, 249,
         250, 253, 258, 259, 272, 273, 296, 297, 360, 361,
         416, 417, 431, 432, 7840, 7841, 7842, 7843, 7844, 7845,
         7846, 7847, 7848, 7849, 7850, 7851, 7852, 7853, 7854, 7855,
         7856, 7857, 7858, 7859, 7860, 7861, 7862, 7863, 7864, 7865,
         7866, 7867, 7868, 7869, 7870, 7871, 7872, 7873, 7874, 7875,
         7876, 7877, 7878, 7879, 7880, 7881, 7882, 7883, 7884, 7885,
         7886, 7887, 7888, 7889, 7890, 7891, 7892, 7893, 7894, 7895,
         7896, 7897, 7898, 7899, 7900, 7901, 7902, 7903, 7904, 7905,
         7906, 7907, 7908, 7909, 7910, 7911, 7912, 7913, 7914, 7915,
         7916, 7917, 7918, 7919, 7920, 7921, 7922, 7923, 7924, 7925,
         7926, 7927, 7928, 7929
     };
     public static int vn8vn3_on_unicode_idx[] = {
         181, 184, 162, 183, 204, 208, 163, 215, 221, 223,
         227, 164, 226, 239, 243, 253, 181, 184, 169, 183,
         204, 208, 170, 215, 221, 223, 227, 171, 226, 239,
         243, 253, 161, 168, 167, 174, 220, 220, 242, 242,
         165, 172, 166, 173, 185, 185, 182, 182, 202, 202,
         199, 199, 200, 200, 201, 201, 203, 203, 190, 190,
         187, 187, 188, 188, 189, 189, 198, 198, 209, 209,
         206, 206, 207, 207, 213, 213, 210, 210, 211, 211,
         212, 212, 214, 214, 216, 216, 222, 222, 228, 228,
         225, 225, 232, 232, 229, 229, 230, 230, 231, 231,
         233, 233, 237, 237, 234, 234, 235, 235, 236, 236,
         238, 238, 244, 244, 241, 241, 248, 248, 245, 245,
         246, 246, 247, 247, 249, 249, 250, 250, 254, 254,
         251, 251, 252, 252
     };
     public static char vn8vn3_lower[] = {
         ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')',
         '*', '+', ',', '-', '.', '/', '0', '1', '2', '3',
         '4', '5', '6', '7', '8', '9', ':', ';', '<', '=',
         '>', '?', '@', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
         'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
         'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '[',
         '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e',
         'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
         'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
         'z', '{', '|', '}', '~', '\177', '\200', '\201', '\202', '\203',
         '\204', '\205', '\206', '\207', '\210', '\211', '\212', '\213', '\214', '\215',
         '\216', '\217', '\220', '\221', '\222', '\223', '\224', '\225', '\226', '\227',
         '\230', '\231', '\232', '\233', '\234', '\235', '\236', '\237', '\240', '\250',
         '\251', '\252', '\253', '\254', '\255', '\256', '\250', '\251', '\252', '\253',
         '\254', '\255', '\256', '\257', '\260', '\261', '\262', '\263', '\264', '\265',
         '\266', '\267', '\270', '\271', '\272', '\273', '\274', '\275', '\276', '\277',
         '\300', '\301', '\302', '\303', '\304', '\305', '\306', '\307', '\310', '\311',
         '\312', '\313', '\314', '\315', '\316', '\317', '\320', '\321', '\322', '\323',
         '\324', '\325', '\326', '\327', '\330', '\331', '\332', '\333', '\334', '\335',
         '\336', '\337', '\340', '\341', '\342', '\343', '\344', '\345', '\346', '\347',
         '\350', '\351', '\352', '\353', '\354', '\355', '\356', '\357', '\360', '\361',
         '\362', '\363', '\364', '\365', '\366', '\367', '\370', '\371', '\372', '\373',
         '\374', '\375', '\376'
     };
     public static char vn8vn3_upper[] = {
         ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')',
         '*', '+', ',', '-', '.', '/', '0', '1', '2', '3',
         '4', '5', '6', '7', '8', '9', ':', ';', '<', '=',
         '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
         'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
         'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[',
         '\\', ']', '^', '_', '`', 'A', 'B', 'C', 'D', 'E',
         'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
         'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
         'Z', '{', '|', '}', '~', '\177', '\200', '\201', '\202', '\203',
         '\204', '\205', '\206', '\207', '\210', '\211', '\212', '\213', '\214', '\215',
         '\216', '\217', '\220', '\221', '\222', '\223', '\224', '\225', '\226', '\227',
         '\230', '\231', '\232', '\233', '\234', '\235', '\236', '\237', '\240', '\241',
         '\242', '\243', '\244', '\245', '\246', '\247', '\241', '\242', '\243', '\244',
         '\245', '\246', '\247', '\257', '\260', '\261', '\262', '\263', '\264', '\265',
         '\266', '\267', '\270', '\271', '\272', '\273', '\274', '\275', '\276', '\277',
         '\300', '\301', '\302', '\303', '\304', '\305', '\306', '\307', '\310', '\311',
         '\312', '\313', '\314', '\315', '\316', '\317', '\320', '\321', '\322', '\323',
         '\324', '\325', '\326', '\327', '\330', '\331', '\332', '\333', '\334', '\335',
         '\336', '\337', '\340', '\341', '\342', '\343', '\344', '\345', '\346', '\347',
         '\350', '\351', '\352', '\353', '\354', '\355', '\356', '\357', '\360', '\361',
         '\362', '\363', '\364', '\365', '\366', '\367', '\370', '\371', '\372', '\373',
         '\374', '\375', '\376'
     };
     public static char vn8vn3_nonmark[] = {
         '\200', '\201', '\202', '\203', '\204', '\205', '\206', '\207', '\210', '\211',
         '\212', '\213', '\214', '\215', '\216', '\217', '\220', '\221', '\222', '\223',
         '\224', '\225', '\226', '\227', '\230', '\231', '\232', '\233', '\234', '\235',
         '\236', '\237', '\240', 'A', 'A', 'E', 'O', 'O', 'U', 'D',
         'a', 'a', 'e', 'o', 'o', 'u', 'd', '\257', '\260', '\261',
         '\262', '\263', '\264', 'a', 'a', 'a', 'a', 'a', '\272', 'a',
         'a', 'a', 'a', '\277', 'A', 'A', 'A', 'A', 'A', 'A',
         'a', 'a', 'a', 'a', 'a', 'a', 'e', 'I', 'e', 'e',
         'e', 'e', 'e', 'e', 'e', 'e', 'e', 'i', 'i', 'U',
         'U', 'U', 'i', 'i', 'i', 'o', 'a', 'o', 'o', 'o',
         'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o',
         'o', 'u', '\360', 'u', 'u', 'u', 'u', 'u', 'u', 'u',
         'u', 'u', 'y', 'y', 'y', 'y', 'y'
     };
     public static int vn8vn3_unicode[] = {
         32, 33, 34, 35, 36, 37, 38, 39, 40, 41,
         42, 43, 44, 45, 46, 47, 48, 49, 50, 51,
         52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
         62, 63, 64, 65, 66, 67, 68, 69, 70, 71,
         72, 73, 74, 75, 76, 77, 78, 79, 80, 81,
         82, 83, 84, 85, 86, 87, 88, 89, 90, 91,
         92, 93, 94, 95, 96, 97, 98, 99, 100, 101,
         102, 103, 104, 105, 106, 107, 108, 109, 110, 111,
         112, 113, 114, 115, 116, 117, 118, 119, 120, 121,
         122, 123, 124, 125, 126, 127, 8364, 129, 8218, 402,
         8222, 8230, 8224, 8225, 710, 8240, 352, 8249, 338, 141,
         381, 143, 144, 8216, 8217, 8220, 8221, 8226, 8211, 8212,
         732, 8482, 353, 8250, 339, 157, 382, 376, 160, 258,
         194, 202, 212, 416, 431, 272, 259, 226, 234, 244,
         417, 432, 273, 175, 176, 177, 178, 179, 180, 224,
         7843, 227, 225, 7841, 186, 7857, 7859, 7861, 7855, 191,
         192, 193, 194, 195, 196, 197, 7863, 7847, 7849, 7851,
         7845, 7853, 232, 205, 7867, 7869, 233, 7865, 7873, 7875,
         7877, 7871, 7879, 236, 7881, 217, 218, 219, 297, 237,
         7883, 242, 224, 7887, 245, 243, 7885, 7891, 7893, 7895,
         7889, 7897, 7901, 7903, 7905, 7899, 7907, 249, 240, 7911,
         361, 250, 7909, 7915, 7917, 7919, 7913, 7921, 7923, 7927,
         7929, 25, 7925
     };

 }
