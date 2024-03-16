/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.repositories.jooq.generated.tables.records;

import edu.java.scrapper.models.ChatState;
import edu.java.scrapper.repositories.jooq.generated.tables.Chats;
import jakarta.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import javax.annotation.processing.Generated;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

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
public class ChatsRecord extends UpdatableRecordImpl<ChatsRecord> implements Record2<Long, ChatState> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>CHATS.ID</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>CHATS.ID</code>.
     */
    @NotNull
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>CHATS.STATUS</code>.
     */
    public void setStatus(ChatState value) {
        set(1, value);
    }

    /**
     * Getter for <code>CHATS.STATUS</code>.
     */
    @NotNull
    public ChatState getStatus() {
        return (ChatState) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, ChatState> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, ChatState> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Chats.CHATS.ID;
    }

    @Override
    public Field<ChatState> field2() {
        return Chats.CHATS.STATUS;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public ChatState component2() {
        return getStatus();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public ChatState value2() {
        return getStatus();
    }

    @Override
    public ChatsRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public ChatsRecord value2(ChatState value) {
        setStatus(value);
        return this;
    }

    @Override
    public ChatsRecord values(Long value1, ChatState value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChatsRecord
     */
    public ChatsRecord() {
        super(Chats.CHATS);
    }

    /**
     * Create a detached, initialised ChatsRecord
     */
    @ConstructorProperties({"id", "status"})
    public ChatsRecord(Long id, ChatState status) {
        super(Chats.CHATS);

        setId(id);
        setStatus(status);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised ChatsRecord
     */
    public ChatsRecord(edu.java.scrapper.repositories.jooq.generated.tables.pojos.Chats value) {
        super(Chats.CHATS);

        if (value != null) {
            setId(value.getId());
            setStatus(value.getStatus());
            resetChangedOnNotNull();
        }
    }
}
