package com.likeacat.eventsGeoPositioning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = Event.COLLECTION_NAME)
public class Event implements Serializable {
    public static final String COLLECTION_NAME = "events";

    @Id
    private Long id;

    private String name;
    private String address;
    private String coord;

    public Event() {
    }

    public Event(String name, String address, String coord) {
        this.name = name;
        this.address = address;
        this.coord = coord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }
}
