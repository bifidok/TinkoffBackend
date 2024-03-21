package edu.java.scrapper.linkParser.links;

public sealed interface LinkParseResponse permits GitHubLink, NotSupportedLink, StackOverflowLink {
}
