/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbfgenerationservice;

/**
 *
 * @author Mobolaji.Aina
 */
public class PbfDataCipher {
    
       private static final String zero ="+~!~~!~$$$$$";
    private static final String one  ="~~!~~!~$$$$";
    private static final String two  = "#=~!~$$$$$";
    private static final String three= "*~!~~!~$$$$$";
    private static final String four = "&~!~~!~$$$$$";
    private static final String five = "~!~~!~^$$$$$";
    private static final String six  = "~!~~!~#%$$$$";
    private static final String seven="~!~~!~#$$$$$";
    private static final String eight="~!~~!~#$$#$$";
    private static final String nine="~!~~!~#$$$!$";
    private static final String a="~!@@£$@#$%^&*()_+~!@@£$@#$%^&*()_+"; 
    private static final String b="!@@£$@#$%^&*()_+~!@@£$@#$%^&*()_+~";
    private static final String c="@@£$@#$%^&*()_+~!@@£$@#$%^&*()_+~!";
    private static final String d="#$%^&*()_+~!@@£$@#$%^&*()_+~!@@£$@";
    private static final String e="$%^&*()_+~!@@£$@#$%^&*()_+~!@@£$@#";
    private static final String f="%^&*()_+~!@@£$@#$%^&*()_+~!@@£$@#$";
    private static final String g="^&*()_+~!@@£$@#$%^&*()_+~!@@£$@#$%";
    private static final String h="&*()_+~!@@£$@#$%^&*()_+~!@@£$@#$%^";
    private static final String i="*()_+~!@@£$@#$%^&*()_+~!@@£$@#$%^&";
    private static final String j="()_+~!@@£$@#$%^&*()_+~!@@£$@#$%^&*";
    private static final String k= ")_+~!@@£$@#$%^&*()_+~!@@£$@#$%^&*(";
    private static final String l="_+~!@@£$@#$%^&*()_+~!@@£$@#$%^&*()";
    private static final String m="+~!@@£$@#$%^&*()_+~!@@£$@#$%^&*()_";
    private static final String n="+_)(*&^%$#@@£$@!~+_)(*&^%$#@@£$@!~";
    private static final String o= "_)(*&^%$#@@£$@!~+_)(*&^%$#@@£$@!~+";
    private static final String p=")(*&^%$#@@£$@!~+_)(*&^%$#@@£$@!~_"; 
    private static final String  q="(*&^%$#@@£$@!~+_)(*&^%$#@@£$@!~+_)";
    private static final String r="*&^%$#@@£$@!~+_)(*&^%$#@@£$@!~+_)(";
    private static final String s="&^%$#@@£$@!~+_)(*&^%$#@@£$@!~+_)(*";
    private static final String t="^%$#@@£$@!~+_)(*&^%$#@@£$@!~+_)(*&";
    private static final String u="%$#@@£$@!~+_)(*&^%$#@@£$@!~+_)(*&^";
    private static final String v="$#@@£$@!~+_)(*&^%$#@@£$@!~+_)(*&^%";
    private static final String w="#@@£$@!~+_)(*&^%$#@@£$@!~+_)(*&^%$";
    private static final String x="@@£$@!~+_)(*&^%$#@@£$@!~+_)(*&^%$#";
    private static final String y="!~+_)(*&^%$#@@£$@!~+_)(*&^%$#@@£$@";
    private static final String z="~+_)(*&^%$#@@£$@!~+_)(*&^%$#@@£$@!";
    
    private static final String A="~!@@£$@#$%^&*()_+.~!@@£$@#$%^&*()_+"; 
    private static final String B="!@@£$@#$%^&*()_+~.!@@£$@#$%^&*()_+~";
    private static final String C="@@£$@#$%^&*()_+~.!@@£$@#$%^&*()_+~!";
    private static final String D="#$%^&*()_+~!@@£$@.#$%^&*()_+~!@@£$@";
    private static final String E="$%^&*()_+~!@@£$@#.$%^&*()_+~!@@£$@#";
    private static final String F="%^&*()_+~!@@£$@#.$%^&*()_+~!@@£$@#$";
    private static final String G="^&*()_+~!@@£$@#$.%^&*()_+~!@@£$@#$%";
    private static final String H="&*()_+~!@@£$@#$%^.&*()_+~!@@£$@#$%^";
    private static final String I="*()_+~!@@£$@#$%^&.*()_+~!@@£$@#$%^&";
    private static final String J="()_+~!@@£$@#$%^&.*()_+~!@@£$@#$%^&*";
    private static final String K= ")_+~!@@£$@#$%^&*(.)_+~!@@£$@#$%^&*(";
    private static final String L="_+~!@@£$@#$%^&*()._+~!@@£$@#$%^&*()";
    private static final String M="+~!@@£$@#$%^&*()_.+~!@@£$@#$%^&*()_";
    private static final String N="+_)(*&^%$#@@£$@!.~+_)(*&^%$#@@£$@!~";
    private static final String O= "_)(*&^%$#@@£$@!~.+_)(*&^%$#@@£$@!~+";
    private static final String P=")(*&^%$#@@£$@!~+._)(*&^%$#@@£$@!~_"; 
    private static final String Q="(*&^%$#@@£$@!~+_).(*&^%$#@@£$@!~+_)";
    private static final String R="*&^%$#@@£$@!~+_)(.*&^%$#@@£$@!~+_)(";
    private static final String S="&^%$#@@£$@!~+_)(.*&^%$#@@£$@!~+_)(*";
    private static final String T="^%$#@@£$@!~+_)(*.&^%$#@@£$@!~+_)(*&";
    private static final String U="%$#@@£$@!~+_)(*&^.%$#@@£$@!~+_)(*&^";
    private static final String V="$#@@£$@!~+_)(*&^%.$#@@£$@!~+_)(*&^%";
    private static final String W="#@@£$@!~+_)(*&^%$.#@@£$@!~+_)(*&^%$";
    private static final String X="@@£$@!~+_)(*&^%$#.@@£$@!~+_)(*&^%$#";
    private static final String Y="!~+_)(*&^%$#@@£$@.!~+_)(*&^%$#@@£$@";
    private static final String Z="~+_)(*&^%$#@@£$@!.~+_)(*&^%$#@@£$@!";
    
    private static final String sep="|";
    
    private static final String exclamation ="[]{}:;?<>,";        
    private static final String asterix="]{}:;?<>,[";    
    private static final String pound="{}:;?<>,[]";
    private static final String $= "}:;?<>,[]{";
    private static final String percentage=":;?<>,[]{}";
    private static final String ampersand=";?<>,[]{}:";
    private static final String singleQuote="?<>,[]{}:;";
    private static final String apostrophe="??<>{}[]:;,";
    private static final String leftParenthesis= "<>,[]{}:;?";
    private static final String rightParenthesis=">,[]{}:;?<";
    private static final String doubleQuote=",[]{}:;?<>";
    private static final String plus= "?>,[]{}:;<";
    private static final String comma="?<,[]{}:;>";
    private static final String hyphen="?[],{}:;<>";
    private static final String dot="?[,{}:;<>]";
    private static final String forSlash=   "?{[;<,>:]}";
    private static final String backSlash="?,:<>[]{};";
    private static final String colon= "<{[;,:]}?>";
    private static final String semicolon= "<{[:?;]}>,";
    private static final String lessThan = ">[;,?{}:]<";
    private static final String greaterThan=">[;},{?:]<";
    private static final String equality=  ":<[]{}>;,?";
    private static final String questionMark=">,}]?[{<";
    private static final String at=",>?{[}]<;:";
    private static final String leftBrace =":{}[,]<>?;";
    private static final String  rightBrace="},[;:}{<>?";
    private static final String index="}?,;<:}][";
    private static final String underscore="},:]{?>[<";
    private static final String leftBracket=";,>{<}[]?:";
    private static final String rightBracket=";]{:}[,<?>";
    private static final String tilde="[?{;:}]<>,";
    private static final String space ="[,<:>}{/]";
    private static final String pipe ="{;:<,?>[]}";
  
   static String charConverter(String raw){
      String converted=sep;
      if (raw.contentEquals("0")){
      converted= zero;
      }
    else  if (raw.contentEquals("1")){
      converted= one;
      }
     else if (raw.contentEquals("2")){
      converted= two;
      }
      else if (raw.contentEquals("3")){
      converted= three;
      }
    else  if (raw.contentEquals("4")){
      converted= four;
      }
    else  if (raw.contentEquals("5")){
      converted= five;
      }
     else if (raw.contentEquals("6")){
      converted= six;
      }
    else  if (raw.contentEquals("7")){
      converted= seven;
      }
    else  if (raw.contentEquals("8")){
      converted= eight;
      }
    else  if (raw.contentEquals("9")){
      converted= nine;
      }
    else  if (raw.contentEquals("a")){
      converted= a;
      }
     else  if (raw.contentEquals("b")){
      converted= b;
      }
     else  if (raw.contentEquals("c")){
      converted= c;
      }
    else   if (raw.contentEquals("d")){
      converted= d;
      }
      else if (raw.contentEquals("e")){
      converted= e;
      }
      else if (raw.contentEquals("f")){
      converted= f;
      }
      else if (raw.contentEquals("g")){
      converted= g;
      }
    else if (raw.contentEquals("h")){
      converted= h;
      }
     else if (raw.contentEquals("i")){
      converted= i;
      }
      else if (raw.contentEquals("j")){
      converted= j;
      }
     else  if (raw.contentEquals("k")){
      converted= k;
      } 
      else if (raw.contentEquals("l")){
      converted= l;
      }
      else if (raw.contentEquals("m")){
      converted= m;
      }
      else if (raw.contentEquals("n")){
      converted= n;
      }
      else if (raw.contentEquals("o")){
      converted= o;
      }
      else if (raw.contentEquals("p")){
      converted= p;
      }
      else if (raw.contentEquals("q")){
      converted= q;
      }
      else if (raw.contentEquals("r")){
      converted= r;
      }
      else if (raw.contentEquals("s")){
      converted= s;
      }
      else if (raw.contentEquals("t")){
      converted= t;
      }
     else if (raw.contentEquals("u")){
      converted= u;
      }
     else if (raw.contentEquals("v")){
      converted= v;
      }
      else if (raw.contentEquals("w")){
      converted= w;
      }
     else  if (raw.contentEquals("x")){
      converted= x;
      }
     else  if (raw.contentEquals("y")){
      converted= y;
      }
     else if (raw.contentEquals("z")){
      converted= z;
      }
      else if (raw.contentEquals("A")){
      converted= A;
      }
     else   if (raw.contentEquals("B")){
      converted= B;
      }
     else  if (raw.contentEquals("C")){
      converted= C;
      }
     else  if (raw.contentEquals("D")){
      converted= D;
      }
     else  if (raw.contentEquals("E")){
      converted= E;
      }
      else if (raw.contentEquals("F")){
      converted= F;
      }
      else if (raw.contentEquals("G")){
      converted= G;
      }
      else if (raw.contentEquals("H")){
      converted= H;
      }
      else if (raw.contentEquals("I")){
      converted= I;
      }
      else if (raw.contentEquals("J")){
      converted= J;
      }
      else if (raw.contentEquals("K")){
      converted= K;
      }
      else if (raw.contentEquals("L")){
      converted= L;
      }
      else if (raw.contentEquals("M")){
      converted= M;
      }
      else if (raw.contentEquals("N")){
      converted= N;
      }
      else if (raw.contentEquals("O")){
      converted= O;
      }
      else if (raw.contentEquals("P")){
      converted= P;
      }
      else if (raw.contentEquals("Q")){
      converted= Q;
      }
      else if (raw.contentEquals("R")){
      converted= R;
      }
      else if (raw.contentEquals("S")){
      converted= S;
      }
      else if (raw.contentEquals("T")){
      converted= T;
      }
     else if (raw.contentEquals("U")){
      converted= U;
      }
      else if (raw.contentEquals("V")){
      converted= V;
      }
      else if (raw.contentEquals("W")){
      converted= W;
      }
      else if (raw.contentEquals("X")){
      converted= X;
      }
      else if (raw.contentEquals("Y")){
      converted= Y;
      }
      else if (raw.contentEquals("Z")){
      converted= Z;
      }
      else if (raw.contentEquals("!")){
      converted= exclamation;
      }
       else if (raw.contentEquals("`")){
      converted= apostrophe;
      }
     else  if (raw.contentEquals("*")){
      converted= asterix;
      }
      else if (raw.contentEquals("#")){
      converted= pound;
      }
      else if (raw.contentEquals("$")){
      converted= $;
      }
      else if (raw.contentEquals("%")){
      converted= percentage;
      }
      else if (raw.contentEquals("&")){
      converted= ampersand;
      }
      else if (raw.contentEquals("\'")){
      converted= singleQuote;
      }
      else if (raw.contentEquals("(")){
      converted= leftParenthesis;
      }
      else if (raw.contentEquals(")")){
      converted= rightParenthesis;
      }
      else if (raw.contentEquals("\"")){
      converted= doubleQuote;
      }
      else if (raw.contentEquals("+")){
      converted=  plus;
      }
      else if (raw.contentEquals(",")){
      converted= comma;
      } 
      else if (raw.contentEquals("-")){
      converted= hyphen;
      }
      else if (raw.contentEquals(".")){
      converted= dot;
      }
       else if (raw.contentEquals("/")){
      converted= forSlash;
      }
      else if (raw.contentEquals("\\")){
      converted= backSlash;
      }
      else if (raw.contentEquals(":")){
      converted= colon;
      }
      else if (raw.contentEquals(";")){
      converted=semicolon;
      }
      else if (raw.contentEquals("<")){
      converted= lessThan;
      }
      else if (raw.contentEquals(">")){
      converted= greaterThan;
      }
      else if (raw.contentEquals("=")){
      converted= equality;
      }
      else if (raw.contentEquals("?")){
      converted= questionMark;
      }
      else if (raw.contentEquals("@")){
      converted= at;
      }
      else if (raw.contentEquals("[")){
      converted= leftBrace;
      }
      else if (raw.contentEquals("]")){
      converted= rightBrace;
      }
     else if(raw.contentEquals("^")){
      converted=index;
      }
      else if(raw.contentEquals("_")){
      converted=underscore;
      }
      else if(raw.contentEquals("{")){
      converted=leftBracket;
      }
     else if(raw.contentEquals("}")){
      converted=rightBracket;
      }
      else if(raw.contentEquals("~")){
      converted=tilde;
      }
      else if(raw.contentEquals(" ")){
      converted=space;
      }
      else if(raw.contentEquals("|")){
      converted=pipe;
      }
      
      return converted;
  
  }
   static String retriever(String coded){
      String converted=sep;
      if (zero.contentEquals(coded)){
      converted= "0";
      }
      else if (one.contentEquals(coded)){
      converted= "1";
      }
      else if (two.contentEquals(coded)){
      converted= "2";
      }
      else if (three.contentEquals(coded)){
      converted= "3";
      }
      else if (four.contentEquals(coded)){
      converted= "4";
      }
      else if (five.contentEquals(coded)){
      converted= "5";
      }
      else if (six.contentEquals(coded)){
      converted= "6";
      }
      else if (seven.contentEquals(coded)){
      converted= "7";
      }
      else if (eight.contentEquals(coded)){
      converted= "8";
      }
      else if (nine.contentEquals(coded)){
      converted= "9";
      }
      else if (a.contentEquals(coded)){
      converted= "a";
      }
      else if (b.contentEquals(coded)){
      converted= "b";
      }
      else if (c.contentEquals(coded)){
      converted= "c";
      }
      else if (d.contentEquals(coded)){
      converted= "d";
      }
      else if (e.contentEquals(coded)){
      converted= "e";
      }
      else if (f.contentEquals(coded)){
      converted= "f";
      }
      else if (g.contentEquals(coded)){
      converted= "g";
      }
      else if (h.contentEquals(coded)){
      converted= "h";
      }
      else if (i.contentEquals(coded)){
      converted= "i";
      }
      else if (j.contentEquals(coded)){
      converted= "j";
      }
      else if (k.contentEquals(coded)){
      converted= "k";
      }
      else if (l.contentEquals(coded)){
      converted= "l";
      }
      else if (m.contentEquals(coded)){
      converted= "m";
      }
      else if (n.contentEquals(coded)){
      converted= "n";
      }
      else if (o.contentEquals(coded)){
      converted= "o";
      }
      else if (p.contentEquals(coded)){
      converted= "p";
      }
      else if (q.contentEquals(coded)){
      converted= "q";
      }
      else if (r.contentEquals(coded)){
      converted= "r";
      }
      else if (s.contentEquals(coded)){
      converted= "s";
      }
      else if (t.contentEquals(coded)){
      converted= "t";
      }
      else if (u.contentEquals(coded)){
      converted= "u";
      }
      else if (v.contentEquals(coded)){
      converted= "v";
      }
      else if (w.contentEquals(coded)){
      converted= "w";
      }
      else if (x.contentEquals(coded)){
      converted= "x";
      }
      else if (y.contentEquals(coded)){
      converted= "y";
      }
      else if (z.contentEquals(coded)){
      converted= "z";
      }
       else if (A.contentEquals(coded)){
      converted= "A";
      }
      else if (B.contentEquals(coded)){
      converted= "B";
      }
      else if (C.contentEquals(coded)){
      converted= "C";
      }
      else if (D.contentEquals(coded)){
      converted= "D";
      }
      else if (E.contentEquals(coded)){
      converted= "E";
      }
      else if (F.contentEquals(coded)){
      converted= "F";
      }
      else if (G.contentEquals(coded)){
      converted= "G";
      }
      else if (H.contentEquals(coded)){
      converted= "H";
      }
      else if (I.contentEquals(coded)){
      converted= "I";
      }
      else if (J.contentEquals(coded)){
      converted= "J";
      }
      else if (K.contentEquals(coded)){
      converted= "K";
      }
      else if (L.contentEquals(coded)){
      converted= "L";
      } 
      else if (M.contentEquals(coded)){
      converted= "M";
      }
      else if (N.contentEquals(coded)){
      converted= "N";
      }
      else if (O.contentEquals(coded)){
      converted= "O";
      }
      else if (P.contentEquals(coded)){
      converted= "P";
      }
      else if (Q.contentEquals(coded)){
      converted= "Q";
      }
      else if (R.contentEquals(coded)){
      converted= "R";
      }
     else  if (S.contentEquals(coded)){
      converted= "S";
      }
     else if (T.contentEquals(coded)){
      converted= "T";
      }
    else  if (U.contentEquals(coded)){
      converted= "U";
      }
    else  if (V.contentEquals(coded)){
      converted= "V";
      }
     else  if (W.contentEquals(coded)){
      converted= "W";
      }
     else  if (X.contentEquals(coded)){
      converted= "X";
      }
     else  if (Y.contentEquals(coded)){
      converted= "Y";
      }
      else if (Z.contentEquals(coded)){
      converted= "Z";
      }
     else if (exclamation.contentEquals(coded)){
      converted= "!";
      }
     else  if (asterix.contentEquals(coded)){
      converted= "*";
      }
      else  if (apostrophe.contentEquals(coded)){
      converted= "`";
      }
     else  if (pound.contentEquals(coded)){
      converted= "#";
      }
      else if ($.contentEquals(coded)){
      converted= "$";
      }
      else if (percentage.contentEquals(coded)){
      converted= "%";
      }
      else if (ampersand.contentEquals(coded)){
      converted= "&";
      }
     else  if (singleQuote.contentEquals(coded)){
      converted= "'";
      }
     else  if (leftParenthesis.contentEquals(coded)){
      converted="(" ;
      }
     else  if (rightParenthesis.contentEquals(coded)){
      converted= ")";
      }
      else if (doubleQuote.contentEquals(coded)){
      converted="\"" ;
      }
      else if (plus.contentEquals(coded)){
      converted="+"  ;
      }
      else if (comma.contentEquals(coded)){
      converted= ",";
      }
      else if (hyphen.contentEquals(coded)){
      converted= "-";
      }
      else if (dot.contentEquals(coded)){
      converted= ".";
      }
     else  if (forSlash.contentEquals(coded)){
      converted= "/";
      }
      else if (backSlash.contentEquals(coded)){
      converted= "\\";
      }
      else if (colon.contentEquals(coded)){
      converted= ":";
      }
      else if (semicolon.contentEquals(coded)){
      converted=";";
      }
     else if (lessThan.contentEquals(coded)){
      converted= "<";
      }
      else if (greaterThan.contentEquals(coded)){
      converted= ">";
      }
     else if (equality.contentEquals(coded)){
      converted= "=";
      }
     else  if (questionMark.contentEquals(coded)){
      converted= "?";
      }
      else if (at.contentEquals(coded)){
      converted= "@@£$@";
      }
     else  if (leftBrace.contentEquals(coded)){
      converted= "[";
      }
     else  if (rightBrace.contentEquals(coded)){
      converted= "]";
      }
      else if(index.contentEquals(coded)){
      converted="^";
      }
      else if(underscore.contentEquals(coded)){
      converted="_";
      }
     else if(leftBracket.contentEquals(coded)){
      converted="{";
      }
     else if(rightBracket.contentEquals(coded)){
      converted="}";
      }
      else if(tilde.contentEquals(coded)){
      converted="~";
      }
     else  if(space.contentEquals(coded)){
      converted=" ";
      }
     else  if(pipe.contentEquals(coded)){
      converted="|";
      }
      
      return converted;
  
  }
   static String scramble(String plain){
      String coded="";
      int span = plain.length();
      for (int cursor=0; cursor< span; cursor++){
      String temp=""+plain.charAt(cursor);
      coded+=charConverter(temp);
      coded+=sep;
      }
      return coded;
  }
   static String decipher(String coded){
      String plain="";
      int span = coded.length();
      String buf="";
      for(int cursor=0; cursor< span; cursor++){
       String temp=""+coded.charAt(cursor);
       if (!temp.contentEquals("|"))buf+=coded.charAt(cursor);
       else{
       plain+=retriever(buf);
       buf="";
       }
  }
  
  return plain;
  }
    
}
