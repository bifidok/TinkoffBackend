package edu.java.scrapper.models;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Chat {
    private long id;
    @EqualsAndHashCode.Exclude
    private ChatState status;
    @EqualsAndHashCode.Exclude
    private List<Link> links;

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
