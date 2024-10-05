package org.example.bot;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class FSM {
    public static String currentState = "not working";

    public LinkedHashMap<String, String> switchState = new LinkedHashMap<>();

    public FSM() {
        switchState.put("name", "gender");
        switchState.put("gender","town");
        switchState.put("town", "age");
        switchState.put("age", "description");
        switchState.put("description", "photo");
    }

    public String getState() {
        return currentState;
    }

    public void changeState(String text) {
        currentState = text;
    }
}
