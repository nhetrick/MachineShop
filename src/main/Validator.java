package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public static final int CWID_LENGTH = 8;
	public static final int DEPT_CODE_LENGTH = 4;
	
	public static boolean isValidEmail(String email){
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		try {
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}catch(Exception e) {
			return false;
		}
	}
	
	public static boolean isValidCWID(String cwid){
		if (cwid.length() == CWID_LENGTH){
			return true;
		}
		return false;
	}
	
	public static boolean isValidDeptCode(String deptCode){
		if (deptCode.length() == DEPT_CODE_LENGTH){
			return true;
		}
		return false;
	}
	
	public static boolean isUnixOS(){
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){
			return true;
		}
		return false;
	}

}
