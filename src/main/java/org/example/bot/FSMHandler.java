package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;
import java.util.Map;




public class FSMHandler {

    Handlers messageList = new Handlers();
    static Map<String,Runnable> FSMCommands = new LinkedHashMap<>();
    public static String registrInfo="";
    private static FSM fsm= new FSM();

    public FSMHandler(long chatId,Update update){
        FSMCommands.put("name",()-> setName(chatId,update));
        FSMCommands.put("town",() -> setTown(chatId,update));
        FSMCommands.put("age",() -> setAge(chatId,update));
        FSMCommands.put("discription",() -> setDiscription(chatId,update));
    }
    public void registration(long chatId){
        messageList.sendMessage(chatId,"Отлично, давай начнем\n Как тебя зовут?");
        fsm.changeState("name");
    }

    private void setName(long chatId,Update update){//косяк в был в апдейте update

        registrInfo+= update.getMessage().getText();
        messageList.sendMessage(chatId,"Прекрасное имя, из какого ты города?");
        fsm.changeState("town");
    }


    private void setTown(long chatId,Update update) {
        registrInfo+=" "  +update.getMessage().getText();
        messageList.sendMessage(chatId,"Отлично, сколько тебе лет?");
        fsm.changeState("age");
    }

    private void setAge(long chatId,Update update) {
        registrInfo+= " "  +update.getMessage().getText();
        messageList.sendMessage(chatId,"Опиши себе несколькими словами");
        fsm.changeState("discription");
    }

    private void setDiscription(long chatId,Update update){
        registrInfo+="\n "  + update.getMessage().getText();
        messageList.sendMessage(chatId,"Давай посмотрим что у нас получилось");
        messageList.sendMessage(chatId,registrInfo);
        fsm.changeState("not working");
        registrInfo="";
    }

}
