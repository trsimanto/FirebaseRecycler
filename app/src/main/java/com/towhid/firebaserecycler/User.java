package com.towhid.firebaserecycler;

public class User {
    private String hubName,hubOwnerId,id,image;

    public User() {
    }

    public User(String hubName, String hubOwnerId, String id, String image) {
        this.hubName = hubName;
        this.hubOwnerId = hubOwnerId;
        this.id = id;
        this.image = image;
    }

    public String getHubName() {
        return hubName;
    }

    public void setHubName(String hubName) {
        this.hubName = hubName;
    }

    public String getHubOwnerId() {
        return hubOwnerId;
    }

    public void setHubOwnerId(String hubOwnerId) {
        this.hubOwnerId = hubOwnerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
