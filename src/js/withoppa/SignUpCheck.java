package js.withoppa;

import java.util.regex.Pattern;

public class SignUpCheck {
	public static boolean nameCheck(String str){       
		String match = "^[가-힝]{2,4}$";
		Pattern p = Pattern.compile(match);  
	    boolean result = p.matcher(str).matches();  
		return result;
	   }
	public static boolean emailCheck(String str){       
		String match = "^[a-zA-Z0-9]{1,8}+@[_0-9a-zA-Z-.]{1,12}$";
		Pattern p = Pattern.compile(match);  
		boolean result = p.matcher(str).matches();  
		return result;
	   }	
	public static boolean passwordCheck(String str){       
		String match = "^[a-zA-Z0-9]{4,10}$";
		Pattern p = Pattern.compile(match);  
		boolean result = p.matcher(str).matches();  
		return result;
		  }
}
