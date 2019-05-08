package com.likeacat.eventsGeoPositioning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Sequence.COLLECTION_NAME)
public class Sequence {
    public static final String COLLECTION_NAME = "sequences";

    @Id
    private String id;
    private Long sequence;

    public Sequence() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
}
