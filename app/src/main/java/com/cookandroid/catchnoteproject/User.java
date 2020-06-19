package com.cookandroid.catchnoteproject;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String userId;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId) {
        this.userId = userId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", userId);
        return result;
    }

    public String getUserid(){
        return userId;
    }

}