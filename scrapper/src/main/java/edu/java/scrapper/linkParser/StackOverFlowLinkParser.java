package edu.java.scrapper.linkParser;

import edu.java.scrapper.linkParser.links.LinkParseResponse;
import edu.java.scrapper.linkParser.links.StackOverflowLink;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOverFlowLinkParser extends LinkParser {
    private final static Pattern GITHUB_LINK =
        Pattern.compile("^(http[s]?:\\/\\/)?(?<host>\\w+\\.\\w+)(?<path>\\/(\\w|.)+)+$");
    private final static String HOST = "stackoverflow.com";

    @Override
    public LinkParseResponse check(URI url) {
        Matcher matcher = GITHUB_LINK.matcher(url.toString());
        if (matcher.matches()) {
            String linkHost = matcher.group("host");
            if (HOST.equals(linkHost)) {
                String path = matcher.group("path");
                String[] parts = path.split("/");
                String id = parts[2];
                return new StackOverflowLink(id);
            }
        }
        return checkNext(url);
    }
}
