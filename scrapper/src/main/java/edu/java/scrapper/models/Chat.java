package edu.java.scrapper.models;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data
public class Chat {
    private int id;
    private ChatState status;
    private List<Link> links;
    public Chat(){

    }
    public Chat(ChatState status){
        this.status = status;
    }
    public Chat(int id, ChatState status){
        this.id = id;
        this.status = status;
    }
    public Chat(int id, List<Link> list){
        this.id = id;
        this.links = list;
    }
}
