/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.repositories.jooq.generated.tables.pojos;

import jakarta.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;

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
public class Repositories implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer linkId;
    private OffsetDateTime lastCommitDate;

    public Repositories() {
    }

    public Repositories(Repositories value) {
        this.id = value.id;
        this.linkId = value.linkId;
        this.lastCommitDate = value.lastCommitDate;
    }

    @ConstructorProperties({"id", "linkId", "lastCommitDate"})
    public Repositories(
        Integer id,
        Integer linkId,
        OffsetDateTime lastCommitDate
    ) {
        this.id = id;
        this.linkId = linkId;
        this.lastCommitDate = lastCommitDate;
    }

    /**
     * Getter for <code>REPOSITORIES.ID</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>REPOSITORIES.ID</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>REPOSITORIES.LINK_ID</code>.
     */
    public Integer getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>REPOSITORIES.LINK_ID</code>.
     */
    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    /**
     * Getter for <code>REPOSITORIES.LAST_COMMIT_DATE</code>.
     */
    @NotNull
    public OffsetDateTime getLastCommitDate() {
        return this.lastCommitDate;
    }

    /**
     * Setter for <code>REPOSITORIES.LAST_COMMIT_DATE</code>.
     */
    public void setLastCommitDate(OffsetDateTime lastCommitDate) {
        this.lastCommitDate = lastCommitDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Repositories other = (Repositories) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.linkId == null) {
            if (other.linkId != null) {
                return false;
            }
        } else if (!this.linkId.equals(other.linkId)) {
            return false;
        }
        if (this.lastCommitDate == null) {
            if (other.lastCommitDate != null) {
                return false;
            }
        } else if (!this.lastCommitDate.equals(other.lastCommitDate)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        result = prime * result + ((this.lastCommitDate == null) ? 0 : this.lastCommitDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Repositories (");

        sb.append(id);
        sb.append(", ").append(linkId);
        sb.append(", ").append(lastCommitDate);

        sb.append(")");
        return sb.toString();
    }
}