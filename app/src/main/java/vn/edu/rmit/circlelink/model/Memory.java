package vn.edu.rmit.circlelink.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Memory implements Serializable {

    private String id;
    private String name;
    private String path;
    private LocalDate uploadDate;
    private LocalDate takenDate;

    public Memory(String name, String path, LocalDate uploadDate, LocalDate takenDate) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.path = path;
        this.uploadDate = uploadDate;
        this.takenDate = takenDate;
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

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public LocalDate getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(LocalDate takenDate) {
        this.takenDate = takenDate;
    }
}
