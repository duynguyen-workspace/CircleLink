package vn.edu.rmit.circlelink.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Memory implements Serializable {

    private String id;
    private String name;
    private String path;
    private LocalDate createdDate;
    private String categoryID;

    public Memory(String name, String path, LocalDate createdDate, String categoryID) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.path = path;
        this.createdDate = createdDate;
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
