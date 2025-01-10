package vn.edu.rmit.circlelink.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Album {

    private String id;
    private String name;
    private LocalDate createdDate;
    private ArrayList<Memory> memories;

    public Album(String name, LocalDate createdDate, ArrayList<Memory> memories) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.createdDate = createdDate;
        this.memories = memories;
    }

    public Memory getCoverPhoto() {
        return memories.isEmpty() ? null : memories.get(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public ArrayList<Memory> getPhotos() {
        return memories;
    }

    public void setPhotos(ArrayList<Memory> memories) {
        this.memories = memories;
    }
}
