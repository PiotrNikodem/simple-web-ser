package com.wrox.DAO;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {

    private Map<String, String> userMap;

    public UserDatabase() {

        if(userMap==null) {
            userMap=new HashMap<>();
            userMap.put("Nicholas", "password");
            userMap.put("Sarah", "drowssap");
            userMap.put("Mike", "wordpass");
            userMap.put("John", "green");
        }
    }

    public Map<String, String> getUserMap() {
        return userMap;
    }
}
