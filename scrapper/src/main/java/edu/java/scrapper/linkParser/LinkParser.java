package edu.java.scrapper.linkParser;

import edu.java.scrapper.linkParser.links.LinkParseResponse;
import edu.java.scrapper.linkParser.links.NotSupportedLink;
import java.net.URI;

public abstract class LinkParser {
    private LinkParser next;

    public static LinkParser link(LinkParser first, LinkParser... parsers) {
        LinkParser head = first;
        for (LinkParser parser : parsers) {
            head.next = parser;
            head = parser;
        }
        return first;
    }

    public abstract LinkParseResponse check(URI url);

    protected LinkParseResponse checkNext(URI url) {
        if (next == null) {
            return new NotSupportedLink();
        }
        return next.check(url);
    }
}
