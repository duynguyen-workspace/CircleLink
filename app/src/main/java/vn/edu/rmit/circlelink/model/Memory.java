package vn.edu.rmit.circlelink.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Memory implements Parcelable {

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

    protected Memory(Parcel in) {
        id = in.readString();
        name = in.readString();
        path = in.readString();
        categoryID = in.readString();

        // Deserialize createdDate from its string representation
        String createdDateString = in.readString();
        if (createdDateString != null) {
            this.createdDate = LocalDate.parse(createdDateString, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static final Creator<Memory> CREATOR = new Creator<Memory>() {
        @Override
        public Memory createFromParcel(Parcel in) {
            return new Memory(in);
        }

        @Override
        public Memory[] newArray(int size) {
            return new Memory[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(categoryID);

        // Serialize createdDate as a string
        dest.writeString(createdDate != null ? createdDate.format(DateTimeFormatter.ISO_LOCAL_DATE) : null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same reference
        if (obj == null || getClass() != obj.getClass()) return false; // Null or different class
        Memory memory = (Memory) obj;
        return path.equals(memory.path); // Compare paths
    }

    @Override
    public int hashCode() {
        return path.hashCode(); // Hash based on path
    }

}
