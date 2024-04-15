/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.repositories.jooq.generated.tables.records;

import edu.java.scrapper.repositories.jooq.generated.tables.Repositories;
import jakarta.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class RepositoriesRecord extends UpdatableRecordImpl<RepositoriesRecord>
    implements Record3<Integer, Integer, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>REPOSITORIES.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>REPOSITORIES.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>REPOSITORIES.LINK_ID</code>.
     */
    public void setLinkId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>REPOSITORIES.LINK_ID</code>.
     */
    public Integer getLinkId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>REPOSITORIES.LAST_COMMIT_DATE</code>.
     */
    public void setLastCommitDate(OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>REPOSITORIES.LAST_COMMIT_DATE</code>.
     */
    @NotNull
    public OffsetDateTime getLastCommitDate() {
        return (OffsetDateTime) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Integer, OffsetDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, Integer, OffsetDateTime> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Repositories.REPOSITORIES.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Repositories.REPOSITORIES.LINK_ID;
    }

    @Override
    public Field<OffsetDateTime> field3() {
        return Repositories.REPOSITORIES.LAST_COMMIT_DATE;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getLinkId();
    }

    @Override
    public OffsetDateTime component3() {
        return getLastCommitDate();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getLinkId();
    }

    @Override
    public OffsetDateTime value3() {
        return getLastCommitDate();
    }

    @Override
    public RepositoriesRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public RepositoriesRecord value2(Integer value) {
        setLinkId(value);
        return this;
    }

    @Override
    public RepositoriesRecord value3(OffsetDateTime value) {
        setLastCommitDate(value);
        return this;
    }

    @Override
    public RepositoriesRecord values(Integer value1, Integer value2, OffsetDateTime value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RepositoriesRecord
     */
    public RepositoriesRecord() {
        super(Repositories.REPOSITORIES);
    }

    /**
     * Create a detached, initialised RepositoriesRecord
     */
    @ConstructorProperties({"id", "linkId", "lastCommitDate"})
    public RepositoriesRecord(Integer id, Integer linkId, OffsetDateTime lastCommitDate) {
        super(Repositories.REPOSITORIES);

        setId(id);
        setLinkId(linkId);
        setLastCommitDate(lastCommitDate);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised RepositoriesRecord
     */
    public RepositoriesRecord(edu.java.scrapper.repositories.jooq.generated.tables.pojos.Repositories value) {
        super(Repositories.REPOSITORIES);

        if (value != null) {
            setId(value.getId());
            setLinkId(value.getLinkId());
            setLastCommitDate(value.getLastCommitDate());
            resetChangedOnNotNull();
        }
    }
}