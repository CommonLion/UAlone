package com.example.youlivealone;

public class ChatRoom {
    private String name;
    private String description;
    private int maxParticipants;
    private String category;

    public ChatRoom(String name, String description, int maxParticipants, String category) {
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.category = category;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getMaxParticipants() { return maxParticipants; }
    public String getCategory() { return category; }
}