/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.repositories.jooq.generated.tables.pojos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String url;
    private OffsetDateTime lastActivity;
    private OffsetDateTime lastCheckTime;

    public Links() {
    }

    public Links(Links value) {
        this.id = value.id;
        this.url = value.url;
        this.lastActivity = value.lastActivity;
        this.lastCheckTime = value.lastCheckTime;
    }

    @ConstructorProperties({"id", "url", "lastActivity", "lastCheckTime"})
    public Links(
        Integer id,
        String url,
        OffsetDateTime lastActivity,
        OffsetDateTime lastCheckTime
    ) {
        this.id = id;
        this.url = url;
        this.lastActivity = lastActivity;
        this.lastCheckTime = lastCheckTime;
    }

    /**
     * Getter for <code>LINKS.ID</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINKS.ID</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINKS.URL</code>.
     */
    @NotNull
    @Size(max = 1000000000)
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINKS.URL</code>.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINKS.LAST_ACTIVITY</code>.
     */
    @NotNull
    public OffsetDateTime getLastActivity() {
        return this.lastActivity;
    }

    /**
     * Setter for <code>LINKS.LAST_ACTIVITY</code>.
     */
    public void setLastActivity(OffsetDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    /**
     * Getter for <code>LINKS.LAST_CHECK_TIME</code>.
     */
    @NotNull
    public OffsetDateTime getLastCheckTime() {
        return this.lastCheckTime;
    }

    /**
     * Setter for <code>LINKS.LAST_CHECK_TIME</code>.
     */
    public void setLastCheckTime(OffsetDateTime lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
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
        final Links other = (Links) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!this.url.equals(other.url)) {
            return false;
        }
        if (this.lastActivity == null) {
            if (other.lastActivity != null) {
                return false;
            }
        } else if (!this.lastActivity.equals(other.lastActivity)) {
            return false;
        }
        if (this.lastCheckTime == null) {
            if (other.lastCheckTime != null) {
                return false;
            }
        } else if (!this.lastCheckTime.equals(other.lastCheckTime)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.lastActivity == null) ? 0 : this.lastActivity.hashCode());
        result = prime * result + ((this.lastCheckTime == null) ? 0 : this.lastCheckTime.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Links (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(lastActivity);
        sb.append(", ").append(lastCheckTime);

        sb.append(")");
        return sb.toString();
    }
}