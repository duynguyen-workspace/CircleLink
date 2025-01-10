package vn.edu.rmit.circlelink.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Album {

    private String id;
    private String name;
    private LocalDate createdDate;
    private ArrayList<Photo> photos;

    public Album(String name, LocalDate createdDate, ArrayList<Photo> photos) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.createdDate = createdDate;
        this.photos = photos;
    }

    public Photo getCoverPhoto() {
        return photos.isEmpty() ? null : photos.get(0);
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

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
