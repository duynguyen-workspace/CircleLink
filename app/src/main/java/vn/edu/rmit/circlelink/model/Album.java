package vn.edu.rmit.circlelink.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Album implements Parcelable {

    private String name;
    private ArrayList<Memory> memories;

    public Album(String name, ArrayList<Memory> memories) {
        this.name = name;
        this.memories = memories;
    }

    protected Album(Parcel in) {
        name = in.readString();
        memories = in.createTypedArrayList(Memory.CREATOR);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getCoverPhoto() {
        return memories.isEmpty() ? null : memories.get(0).getPath();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Memory> getMemories() {
        return memories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(memories);
    }
}
