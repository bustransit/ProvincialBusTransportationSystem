/**
 * Validator for common input
*/
package bustransit.utilities;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author DelaTorreNelson
 */
public class Validator {


    // return true if valid email
    public boolean isValidEmail(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }       
    
    // return true if valid text
    public boolean isTextOnly(String t){
        return t.chars().allMatch(Character::isLetter);   
    }

    // return true if number only
    public boolean isNumberOnly(String t){
        return t.matches("[0-9]+");
    }    
    
    // returns true if the input is valid contact number    
    public boolean isValidContactNumber(String n){
        String regexStr = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regexStr);
        java.util.regex.Matcher m = p.matcher(n);
        return m.matches();
    }                          
    

}// end Validator here
