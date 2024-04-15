/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.repositories.jooq.generated;

import edu.java.scrapper.repositories.jooq.generated.tables.Chats;
import edu.java.scrapper.repositories.jooq.generated.tables.ChatsLinks;
import edu.java.scrapper.repositories.jooq.generated.tables.Links;
import edu.java.scrapper.repositories.jooq.generated.tables.Questions;
import edu.java.scrapper.repositories.jooq.generated.tables.Repositories;
import javax.annotation.processing.Generated;

/**
 * Convenience access to all tables in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Tables {

    /**
     * The table <code>CHATS</code>.
     */
    public static final Chats CHATS = Chats.CHATS;

    /**
     * The table <code>CHATS_LINKS</code>.
     */
    public static final ChatsLinks CHATS_LINKS = ChatsLinks.CHATS_LINKS;

    /**
     * The table <code>LINKS</code>.
     */
    public static final Links LINKS = Links.LINKS;

    /**
     * The table <code>QUESTIONS</code>.
     */
    public static final Questions QUESTIONS = Questions.QUESTIONS;

    /**
     * The table <code>REPOSITORIES</code>.
     */
    public static final Repositories REPOSITORIES = Repositories.REPOSITORIES;
}