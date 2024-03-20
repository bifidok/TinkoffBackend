package edu.java.scrapper.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @Column(name = "id")
    private long id;
    @EqualsAndHashCode.Exclude
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ChatState status;

    public Chat() {

    }

    public Chat(long id) {
        this.id = id;
        this.status = ChatState.DEFAULT;
    }

    public Chat(long id, ChatState state) {
        this.id = id;
        this.status = state;
    }

}
