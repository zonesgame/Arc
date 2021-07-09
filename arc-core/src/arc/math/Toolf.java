package arc.math;

import java.util.regex.Pattern;

/**
 *  zones add class
 */
public class Toolf {

    static private final Pattern integerPattern = Pattern.compile("^-?[0-9]+");
    static private final Pattern floatPattern = Pattern.compile("-?[0-9]+.?[0-9]+");


    public static boolean isNumber (CharSequence value, boolean isInteger) {
        if (isInteger)  return integerPattern.matcher(value).matches();
        return floatPattern.matcher(value).matches();
    }

    public static boolean isNumberInt (CharSequence value) {
        return integerPattern.matcher(value).matches();
    }

    public static boolean isNumberFloat (CharSequence value) {
        return floatPattern.matcher(value).matches();
    }

}
