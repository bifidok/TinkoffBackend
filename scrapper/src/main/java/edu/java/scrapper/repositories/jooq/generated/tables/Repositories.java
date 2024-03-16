/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.repositories.jooq.generated.tables;

import edu.java.scrapper.repositories.jooq.generated.DefaultSchema;
import edu.java.scrapper.repositories.jooq.generated.Keys;
import edu.java.scrapper.repositories.jooq.generated.tables.records.RepositoriesRecord;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

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
public class Repositories extends TableImpl<RepositoriesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>REPOSITORIES</code>
     */
    public static final Repositories REPOSITORIES = new Repositories();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RepositoriesRecord> getRecordType() {
        return RepositoriesRecord.class;
    }

    /**
     * The column <code>REPOSITORIES.ID</code>.
     */
    public final TableField<RepositoriesRecord, Integer> ID =
        createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>REPOSITORIES.LINK_ID</code>.
     */
    public final TableField<RepositoriesRecord, Integer> LINK_ID =
        createField(DSL.name("LINK_ID"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>REPOSITORIES.LAST_COMMIT_DATE</code>.
     */
    public final TableField<RepositoriesRecord, OffsetDateTime> LAST_COMMIT_DATE =
        createField(DSL.name("LAST_COMMIT_DATE"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "");

    private Repositories(Name alias, Table<RepositoriesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Repositories(Name alias, Table<RepositoriesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>REPOSITORIES</code> table reference
     */
    public Repositories(String alias) {
        this(DSL.name(alias), REPOSITORIES);
    }

    /**
     * Create an aliased <code>REPOSITORIES</code> table reference
     */
    public Repositories(Name alias) {
        this(alias, REPOSITORIES);
    }

    /**
     * Create a <code>REPOSITORIES</code> table reference
     */
    public Repositories() {
        this(DSL.name("REPOSITORIES"), null);
    }

    public <O extends Record> Repositories(Table<O> child, ForeignKey<O, RepositoriesRecord> key) {
        super(child, key, REPOSITORIES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<RepositoriesRecord, Integer> getIdentity() {
        return (Identity<RepositoriesRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<RepositoriesRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_81B;
    }

    @Override
    public List<UniqueKey<RepositoriesRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_81);
    }

    @Override
    public List<ForeignKey<RepositoriesRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_8);
    }

    private transient Links _links;

    /**
     * Get the implicit join path to the <code>PUBLIC.LINKS</code> table.
     */
    public Links links() {
        if (_links == null) {
            _links = new Links(this, Keys.CONSTRAINT_8);
        }

        return _links;
    }

    @Override
    public Repositories as(String alias) {
        return new Repositories(DSL.name(alias), this);
    }

    @Override
    public Repositories as(Name alias) {
        return new Repositories(alias, this);
    }

    @Override
    public Repositories as(Table<?> alias) {
        return new Repositories(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Repositories rename(String name) {
        return new Repositories(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Repositories rename(Name name) {
        return new Repositories(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Repositories rename(Table<?> name) {
        return new Repositories(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Integer, OffsetDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super Integer, ? super Integer, ? super OffsetDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(
        Class<U> toType,
        Function3<? super Integer, ? super Integer, ? super OffsetDateTime, ? extends U> from
    ) {
        return convertFrom(toType, Records.mapping(from));
    }
}
