package com.connect.chat.connectus.model;

public class ItemChat {
    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ItemChat(String name, String content) {

        this.name = name;
        this.content = content;
    }
}
