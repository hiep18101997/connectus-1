package com.connect.chat.connectus.model;

import com.stfalcon.chatkit.commons.models.IUser;

public class Author implements IUser {
    private String id, name, avatar;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
}
