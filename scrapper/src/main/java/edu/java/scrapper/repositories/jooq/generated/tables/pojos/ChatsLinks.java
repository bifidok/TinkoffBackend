/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.repositories.jooq.generated.tables.pojos;

import jakarta.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.io.Serializable;
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
public class ChatsLinks implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long chatId;
    private Integer linkId;

    public ChatsLinks() {
    }

    public ChatsLinks(ChatsLinks value) {
        this.chatId = value.chatId;
        this.linkId = value.linkId;
    }

    @ConstructorProperties({"chatId", "linkId"})
    public ChatsLinks(
        Long chatId,
        Integer linkId
    ) {
        this.chatId = chatId;
        this.linkId = linkId;
    }

    /**
     * Getter for <code>CHATS_LINKS.CHAT_ID</code>.
     */
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>CHATS_LINKS.CHAT_ID</code>.
     */
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    /**
     * Getter for <code>CHATS_LINKS.LINK_ID</code>.
     */
    @NotNull
    public Integer getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>CHATS_LINKS.LINK_ID</code>.
     */
    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
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
        final ChatsLinks other = (ChatsLinks) obj;
        if (this.chatId == null) {
            if (other.chatId != null) {
                return false;
            }
        } else if (!this.chatId.equals(other.chatId)) {
            return false;
        }
        if (this.linkId == null) {
            if (other.linkId != null) {
                return false;
            }
        } else if (!this.linkId.equals(other.linkId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChatsLinks (");

        sb.append(chatId);
        sb.append(", ").append(linkId);

        sb.append(")");
        return sb.toString();
    }
}
