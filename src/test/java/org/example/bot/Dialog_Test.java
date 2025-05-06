package org.example.bot;

import org.example.DB.DBConection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.example.DB.DBConection.deletePerson;
import static org.example.DB.DBConection.sendPerson;


public class Dialog_Test {
    @Test
    public void ViewingUserNotExistTest() {
        Handlers handlerTest = new Handlers();
        String message = "/viewing";
        long chatId = 777777;
        handlerTest.telegramHandlers(chatId, message);
        Assertions.assertEquals("Упс, у тебя ещё нет анкеты, давай её заведем?)\n" +
                "Для это отправь команду \"/register\"", handlerTest.getAnswer());
    }

    @Test
    public void ViewingUserExistTest() {
        Handlers handlersTest = new Handlers();
        long chatID = 1999812;
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
        handlersTest.telegramHandlers(chatID, message);
        dialogTest.dialogProcess(chatID, "Ок");
        dialogTest.dialogProcess(chatID, "Начать");
        dialogTest.dialogProcess(chatID, "\uD83D\uDC4E");
        System.out.println("waaaay");
        System.out.println(dialogTest.getAnswerDialog());
        Assertions.assertEquals("Понятно, это тебе не подходит\nДавай смотреть дальше",
                dialogTest.getAnswerDialog());
        deletePerson(chatID);
    }

    @Test
    public void FilterOnUserTest() {
        Handlers handlersTest = new Handlers();
        long chatID = 1999812;
        handlersTest.telegramHandlers(chatID, "/register");
        Dialog dialogTest = new Dialog();
        dialogTest.dialogProcess(chatID, "Шкибиди Владлена");
        dialogTest.dialogProcess(chatID, "Женщина");
        dialogTest.dialogProcess(chatID, "Екатерибург");
        dialogTest.dialogProcess(chatID, "100");
        dialogTest.dialogProcess(chatID, "ШКибиди туалеты!");
        long otherChatID = 1999813;
        handlersTest.telegramHandlers(otherChatID, "/register");
        String name = "влад";
        String gender = "Мужчина";
        String town = "Екатерибург";
        String age = "20";
        String description = "skibi tiolet";
        dialogTest.dialogProcess(otherChatID, name);
        dialogTest.dialogProcess(otherChatID, gender);
        dialogTest.dialogProcess(otherChatID, town);
        dialogTest.dialogProcess(otherChatID, age);
        dialogTest.dialogProcess(otherChatID, description);
        handlersTest.telegramHandlers(otherChatID, "/viewing");
        dialogTest.dialogProcess(otherChatID, "Фильтры");
        handlersTest.telegramHandlers(otherChatID, "/gender_f");
        handlersTest.telegramHandlers(otherChatID, "/age");
        dialogTest.dialogProcess(otherChatID, "101");
        dialogTest.dialogProcess(otherChatID, "99");
        handlersTest.telegramHandlers(otherChatID, "/viewing");
        dialogTest.dialogProcess(otherChatID, "Ок");
        dialogTest.dialogProcess(otherChatID,"Начать");
        Assertions.assertEquals("Шкибиди Владлена\n" +
                "100\n" +
                "Екатерибург\n" +
                "ШКибиди туалеты!",dialogTest.getAnswerDialog());
        deletePerson(chatID);
        deletePerson(otherChatID);

    }

    @Test
    public void OtherFilterOnUserTest() {
        Handlers handlersTest = new Handlers();
        long chatID = 1999812;
        handlersTest.telegramHandlers(chatID, "/register");
        Dialog dialogTest = new Dialog();
        dialogTest.dialogProcess(chatID, "Шкибиди Владлена");
        dialogTest.dialogProcess(chatID, "Женщина");
        dialogTest.dialogProcess(chatID, "Екатерибург");
        dialogTest.dialogProcess(chatID, "100");
        dialogTest.dialogProcess(chatID, "ШКибиди туалеты!");
        long otherChatID = 1999813;
        handlersTest.telegramHandlers(otherChatID, "/register");
        String name = "влад";
        String gender = "Мужчина";
        String town = "Екатерибург";
        String age = "20";
        String description = "skibi tiolet";
        dialogTest.dialogProcess(otherChatID, name);
        dialogTest.dialogProcess(otherChatID, gender);
        dialogTest.dialogProcess(otherChatID, town);
        dialogTest.dialogProcess(otherChatID, age);
        dialogTest.dialogProcess(otherChatID, description);
        handlersTest.telegramHandlers(otherChatID, "/viewing");
        dialogTest.dialogProcess(otherChatID, "Фильтры");
        handlersTest.telegramHandlers(otherChatID, "/gender_f");
        handlersTest.telegramHandlers(otherChatID, "/age");
        dialogTest.dialogProcess(otherChatID, "101");
        dialogTest.dialogProcess(otherChatID, "99");
        handlersTest.telegramHandlers(otherChatID, "/viewing");
        dialogTest.dialogProcess(otherChatID, "Ок");
        dialogTest.dialogProcess(otherChatID, "Начать");
        int agePeson = DBConection.getAgePerson(dialogTest.getReservAnswer());
        Assertions.assertEquals(agePeson,100);
        deletePerson(chatID);
        deletePerson(otherChatID);
    }
}
