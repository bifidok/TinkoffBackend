package edu.java.bot.validators;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkValidator {
    private final static Pattern LINK_REGULAR = Pattern.compile("^(http[s]?:\\/\\/)?\\w+\\.\\w+(\\/(\\w|.)+)+$");

    private LinkValidator() {

    }

    public static boolean isValid(URI link) {
        Matcher matcher = LINK_REGULAR.matcher(link.toString());
        return matcher.matches();
    }
}
