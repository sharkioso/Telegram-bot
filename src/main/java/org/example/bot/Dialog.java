package org.example.bot;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.example.DB.DBConection.*;

public class Dialog extends Executer {

    Map<String, BiConsumer<Long, String>> commandsFSM = new LinkedHashMap<>();
    static Map<Long, InfoUser> registerInfo = new LinkedHashMap<>();
    static String answerDialog = "";

    public Dialog() {
        commandsFSM.put("name", this::setName);
        commandsFSM.put("gender", this::setGender);
        commandsFSM.put("town", this::setTown);
        commandsFSM.put("age", this::setAge);
        commandsFSM.put("discription", this::setDiscription);
    }


    public void dialogProcess(long chatId, String messageText) {
        if (getState(chatId) != null) {
            commandsFSM.get(getState(chatId)).accept(chatId, messageText);
        } else {
            sendMessage(chatId, "Извините, я вас не понимаю\nЕсли вы не знаете, " +
                    "что делать отправте \"/help\"");
        }
    }


    private void setName(long chatId, String messageText) {
        InfoUser user = new InfoUser();
        registerInfo.put(chatId, user);
        registerInfo.get(chatId).setName(messageText);
        sendMessage(chatId, "Прекрасное имя, какого ты пола?");
        changeState(chatId, "gender");
    }


    private void setGender(long chatId, String messageText) {
        registerInfo.get(chatId).setGender(messageText);
        sendMessage(chatId, "из какого ты города?");
        changeState(chatId, "town");
    }


    private void setTown(long chatId, String messageText) {
        registerInfo.get(chatId).setTown(messageText);
        sendMessage(chatId, "Отлично, сколько тебе лет?");
        changeState(chatId, "age");
    }


    private void setAge(long chatId, String messageText) {
        registerInfo.get(chatId).setAge(messageText);
        sendMessage(chatId, "Опиши себе несколькими словами");
        changeState(chatId, "discription");
    }


    private void setDiscription(long chatId, String messageText) {
        registerInfo.get(chatId).setAbout(messageText);
        ;
        sendMessage(chatId, "Давай посмотрим что у нас получилось");
        answerDialog=registerInfo.get(chatId).allInfo();
        sendMessage(chatId, answerDialog);
        changeState(chatId, null);
        System.out.println(getState(chatId));
        insertPerson(String.valueOf(chatId), registerInfo.get(chatId).getName(), registerInfo.get(chatId).getGender(),
                registerInfo.get(chatId).getAge(), registerInfo.get(chatId).getTown(), registerInfo.get(chatId).getAbout());
    }
}


