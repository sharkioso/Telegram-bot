package org.example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class HandlersTest {
    @Test
    public void startTest() {
        Handlers handlerTest =  new Handlers();
        String message = "/start";
        long chatId = 749240804;
        handlerTest.telegramHandlers(chatId,message);
        Assertions.assertEquals("Привет, ты попал в бот знакомств",handlerTest.getAnswer());

    }

    @Test
    public void helpTest() {
        Handlers handlerTest =  new Handlers();
        String message = "/help start";
        long chatId = 749240804;
        handlerTest.telegramHandlers(chatId,message);
        Assertions.assertEquals("Это команда для начала нашего общения",handlerTest.getAnswer());

    }

    @Test
    public void nonExistentTest() {
        Handlers handlerTest =  new Handlers();
        String message = "dsa";
        long chatId = 749240804;
        handlerTest.telegramHandlers(chatId,message);
        Assertions.assertEquals("Нет такой команды\nЯ вас не понимаю",handlerTest.getAnswer());

    }

    @Test
    public void registrationTest(){
        Handlers handlerTest =  new Handlers();
        String message = "/register";
        long chatId = 749240804;
        long chatIdOther= 75934878;
        handlerTest.telegramHandlers(chatId,message);
        handlerTest.telegramHandlers(chatId,"Влад");
        handlerTest.telegramHandlers(chatId,"Мужчина");
        handlerTest.telegramHandlers(chatId,"Москва");
        handlerTest.telegramHandlers(chatIdOther,"dssad");
        handlerTest.telegramHandlers(chatId,"20");
        handlerTest.telegramHandlers(chatId,"тесты");
        Assertions.assertEquals("Влад 20 Москва\n тесты",handlerTest.getAnswer());
    }
}
