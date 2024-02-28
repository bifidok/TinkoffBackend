package edu.java.scrapper.models;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data
public class TelegramChat {
    @NonNull
    private int id;
    @NonNull
    private List<Link> links;
}
