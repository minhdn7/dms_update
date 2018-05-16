package vn.com.vsc.ptpm.VNPT_DMS.control.valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MinhDN on 16/10/2017.
 */

public class MonthValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String MONTH_PATTERN = "(0?[1-9]|1[012])/((19|20)\\d\\d)";

    public MonthValidator(){
        pattern = Pattern.compile(MONTH_PATTERN);
    }

    /**
     * Validate date format with regular expression
     * @param date date address for validation
     * @return true valid date fromat, false invalid date format
     */
    public boolean validate(final String date){

        matcher = pattern.matcher(date);

        if(matcher.matches()) {

            matcher.reset();
            return true;
        }
        return  false;
    }
}
