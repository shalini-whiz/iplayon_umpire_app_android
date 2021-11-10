package com.iplayon.umpire.util;

import java.util.regex.Pattern;

import android.widget.EditText;

public class Validation {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{10}";
    private static final String DOB_REGEX = "[0-9]{1,2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) [0-9]{4}";
    
    
      private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "10 digits";
    private static final String DATE_MSG = "dd mmm yyyy";

    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }
 
    public static boolean isPhoneNumber(EditText editText, boolean required) {
    	boolean result = isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    	System.out.println("phone number result .. "+result+" ... "+required);
        return result;
    }
    
    public static boolean isDate(EditText editText, boolean required) {
        return isValid(editText, DOB_REGEX, DATE_MSG, required);
    }
 
    
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {
 
        String text = editText.getText().toString().trim();
        editText.setError(null);
 
        if ( required && !hasText(editText) ) return false;
        if(!required)
        {
        	if(!Pattern.matches(regex, text))
        	{
        		 editText.setError(errMsg);
                 return false;
        	}
        }
        else if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };
 
        return true;
    }
 
   
    
    public static boolean hasText(EditText editText) {
 
        String text = editText.getText().toString().trim();
        editText.setError(null);
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }
 
        return true;
    }
}
