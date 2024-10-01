package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.example.DB.DBConection.insertPerson;


public class FSMHandler {

    Handlers messageList = new Handlers();
    static Map<String, Runnable> FSMCommands = new LinkedHashMap<>();
    public static Map<String, String> registrInfo = new LinkedHashMap<>();
    private static FSM fsm = new FSM();

    public FSMHandler(long chatId, Update update) {
        FSMCommands.put("name", () -> setName(chatId, update));
        FSMCommands.put("gender", () -> setGender(chatId, update));
        FSMCommands.put("town", () -> setTown(chatId, update));
        FSMCommands.put("age", () -> setAge(chatId, update));
        FSMCommands.put("discription", () -> setDiscription(chatId, update));
    }

    public void registration(long chatId) {
        messageList.sendMessage(chatId, "Отлично, давай начнем\nКак тебя зовут?");
        fsm.changeState("name");
    }

    private void setName(long chatId, Update update) {
        registrInfo.put("id", update.getMessage().getFrom().getUserName());
        registrInfo.put("name", update.getMessage().getText());
        messageList.sendMessage(chatId, "Прекрасное имя, какого ты пола?");
        fsm.changeState("gender");
    }

    private void setGender (long chatId, Update update){
        if (update.getMessage().toString() == "Мужчина"){
            registrInfo.put("gender","True");
        }
        else registrInfo.put("gender","");
        messageList.sendMessage(chatId,"из какого ты города?");
        fsm.changeState("town");
    }


    private void setTown(long chatId, Update update) {
        registrInfo.put("town", update.getMessage().getText());
        messageList.sendMessage(chatId, "Отлично, сколько тебе лет?");
        fsm.changeState("age");
    }

    private void setAge(long chatId, Update update) {
        registrInfo.put("age", update.getMessage().getText());
        messageList.sendMessage(chatId, "Опиши себе несколькими словами");
        fsm.changeState("discription");
    }

    private void setDiscription(long chatId, Update update) {
        registrInfo.put("description", update.getMessage().getText());;
        messageList.sendMessage(chatId, "Давай посмотрим что у нас получилось");
        messageList.sendMessage(chatId, registrInfo.get("name") + " " + registrInfo.get("town") +
                " " + registrInfo.get("age") + " " + registrInfo.get("description"));
        insertPerson(registrInfo.get("id"), registrInfo.get("name"), Boolean.valueOf(registrInfo.get("gender")),
                Integer.parseInt(registrInfo.get("age")), registrInfo.get("town"), registrInfo.get("description"));
        fsm.changeState("not working");
    }
}
