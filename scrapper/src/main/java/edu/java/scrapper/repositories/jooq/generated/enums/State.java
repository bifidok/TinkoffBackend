/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.repositories.jooq.generated.enums;

import edu.java.scrapper.repositories.jooq.generated.DefaultSchema;
import javax.annotation.processing.Generated;
import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public enum State implements EnumType {

    TRACK("TRACK"),

    UNTRACK("UNTRACK"),

    DEFAULT("DEFAULT");

    private final String literal;

    private State(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public String getName() {
        return "STATE";
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Lookup a value of this EnumType by its literal
     */
    public static State lookupLiteral(String literal) {
        return EnumType.lookupLiteral(State.class, literal);
    }
}
