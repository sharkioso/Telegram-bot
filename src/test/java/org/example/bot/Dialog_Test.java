package org.example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.example.DB.DBConection.deletePerson;
import static org.example.DB.DBConection.sendPerson;


public class Dialog_Test {
    @Test
    public void ViewingUserNotExistTest(){
        Handlers handlerTest = new Handlers();
        String message = "/viewing";
        long chatId = 777777;
        handlerTest.telegramHandlers(chatId, message);
        Assertions.assertEquals("Упс, у тебя ещё нет анкеты, давай её заведем?)\n" +
                "Для это отправь команду \"/register\"", handlerTest.getAnswer());
    }

    @Test
    public void ViewingUserExistTest(){
        Handlers handlersTest = new Handlers();
        long chatID = 777;
        handlersTest.telegramHandlers(chatID, "/register");
        Dialog dialogTest = new Dialog();
        String name = "Влад";
        String gender = "Мужчина";
        String town = "Екатерибург";
        String age = "20";
        String description = "skibi tiolet";
        dialogTest.dialogProcess(chatID, name);
        dialogTest.dialogProcess(chatID, gender);
        dialogTest.dialogProcess(chatID, town);
        dialogTest.dialogProcess(chatID, age);
        dialogTest.dialogProcess(chatID, description);
        String message = "/viewing";
        handlersTest.telegramHandlers(chatID,message);
        dialogTest.dialogProcess(chatID,"Ок");
        dialogTest.dialogProcess(chatID,"Начать");
        dialogTest.dialogProcess(chatID,"\uD83D\uDC4E");
        Assertions.assertEquals("Понятно, это тебе не подходит\nДавай смотреть дальше",
                dialogTest.getAnswerDialog());
        deletePerson(chatID);
    }





}
