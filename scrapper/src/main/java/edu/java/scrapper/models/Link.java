package edu.java.scrapper.models;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import edu.java.scrapper.models.converters.OffsetDateTimeConverter;
import edu.java.scrapper.models.converters.URIConverter;
import jakarta.persistence.Access;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import static jakarta.persistence.AccessType.FIELD;
import static jakarta.persistence.AccessType.PROPERTY;

@Data
@Entity
@Table(name = "links")
public class Link {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "url")
    @Convert(converter = URIConverter.class)
    private URI url;
    @EqualsAndHashCode.Exclude
    @Column(name = "last_activity")
    @Convert(converter = OffsetDateTimeConverter.class)
    private OffsetDateTime lastActivity;
    @EqualsAndHashCode.Exclude
    @Column(name = "last_check_time")
    @Convert(converter = OffsetDateTimeConverter.class)
    private OffsetDateTime lastCheckTime;

    public Link() {

    }

    public Link(URI link) {
        this.url = link;
        lastActivity = OffsetDateTime.now();
        lastCheckTime = OffsetDateTime.now();
    }

    public Link(URI url, OffsetDateTime lastActivity) {
        this.url = url;
        this.lastActivity = lastActivity;
        lastCheckTime = OffsetDateTime.now();
    }

    public Link(int id, URI url, OffsetDateTime lastActivity, OffsetDateTime lastCheckTime) {
        this.id = id;
        this.url = url;
        this.lastActivity = lastActivity;
        this.lastCheckTime = lastCheckTime;
    }
}
