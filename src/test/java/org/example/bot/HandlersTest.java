package org.example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.example.DB.DBConection.deletePerson;
import static org.example.DB.DBConection.sendPerson;


public class HandlersTest {
    @Test
    public void startTest() {
        Handlers handlerTest = new Handlers();
        String message = "/start";
        long chatId = 749240804;
        handlerTest.telegramHandlers(chatId, message);
        Assertions.assertEquals("Привет, ты попал в бот знакомств", handlerTest.getAnswer());

    }

    @Test
    public void helpTest() {
        Handlers handlerTest = new Handlers();
        String message = "/help start";
        long chatId = 749240804;
        handlerTest.telegramHandlers(chatId, message);
        Assertions.assertEquals("Это команда для начала нашего общения", handlerTest.getAnswer());

    }

    @Test
    public void nonexistentTest() {
        Handlers handlerTest = new Handlers();
        String message = "dsa";
        long chatId = 749240804;
        handlerTest.telegramHandlers(chatId, message);
        Assertions.assertEquals("Нет такой команды\nЯ вас не понимаю", handlerTest.getAnswer());
    }

    @Test
    public void regiterTest() {
        Handlers handlersTest = new Handlers();
        long chatID = 777157210;
        long otherChatId = 1111;
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
        dialogTest.dialogProcess(otherChatId, "меня зовут коля и я вообще не люблю шкибиди туалеты");
        dialogTest.dialogProcess(chatID, description);
        String localAnswer = name + " " + age + " " + town + "\n" + description;
        Assertions.assertEquals(localAnswer, Dialog.answerDialog);
        deletePerson(777157210);
    }
}
