package org.example.bot;

import java.util.LinkedHashMap;

public class FSM {
    public String currentState;

    public LinkedHashMap<String, String> switchState = new LinkedHashMap<>();

    public FSM() {
        currentState="";
        switchState.put("name", "gender");
        switchState.put("gender","town");
        switchState.put("town", "age");
        switchState.put("age", "description");
        switchState.put("description", "registred");
    }

    public String getState() {
        return currentState;
    }

    public void changeState(String text) {
        currentState = text;
    }
}
