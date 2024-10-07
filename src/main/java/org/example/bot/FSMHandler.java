package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.example.DB.DBConection.insertPerson;


public class FSMHandler {

    Handlers messageList = new Handlers();
    static Map<String, BiConsumer<Long,Update>> commandsFSM = new LinkedHashMap<>();
    public static Map<String, String> registrInfo = new LinkedHashMap<>();

    FSM fsm = new FSM();
    public FSMHandler(long chatId, Update update) {
        commandsFSM.put("name", this::setName);
        commandsFSM.put("gender", this::setGender);
        commandsFSM.put("town", this::setTown);
        commandsFSM.put("age", this::setAge);
        commandsFSM.put("discription",this::setDiscription);
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
