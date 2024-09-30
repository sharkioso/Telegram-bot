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
        long chatId = 123456L;
        handlerTest.telegramHandlers(message,chatId);
        Assertions.assertEquals("Привет, ты попал в бот знакомств",handlerTest.getAnswer());

    }

    @Test
    public void helpTest() {

        Handlers handlerTest =  new Handlers();
        String message = "/help start";
        long chatId = 785838135;
        handlerTest.telegramHandlers(message,chatId);
        Assertions.assertEquals("Это команда для начала нашего общения",handlerTest.getAnswer());

    }

    @Test
    public void nonexistentTest() {

        Handlers handlerTest =  new Handlers();
        String message = "dsa";
        long chatId = 767567988;
        handlerTest.telegramHandlers(message,chatId);
        Assertions.assertEquals("Нет такой команды\nЯ вас не понимаю",handlerTest.getAnswer());

    }

}
