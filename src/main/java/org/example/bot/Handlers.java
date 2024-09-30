package org.example.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Handlers extends TelegramBot {
    Map<String, Runnable> commands = new LinkedHashMap<>();
    Map<String, String> helpText = new LinkedHashMap<>();
    public String answer = "";
    public void telegramHandlers(String messageText, Long chatId) {
        commands.put("/start", () -> startCommand(chatId));
        helpText.put("start", "Это команда для начала нашего общения");

        commands.put("/authors", () -> authorsCommand(chatId));
        helpText.put("authors", "Это команда, которая расскажет тебе, кто написал этого бота");

        commands.put("/about", () -> aboutCommand(chatId));
        helpText.put("about", "Эта команда описывает бота");

        helpText.put("/help", "Бот имеет следующие команды: \n/start\n/about\n/authors\n/help\nДля подробной информации введите \"/help команда\"");
        helpText.put("help", "Вводя эту команду вы можете узнать какие команды умеет делать бот");

        if (messageText.contains("/help")) {
            if (messageText.length() > 6) {
                messageText = messageText.substring(6);
            }
            answer =helpText.get(messageText);
            sendMessage(chatId, helpText.get(messageText));
        } else {
            if (commands.containsKey(messageText)) {
                commands.get(messageText).run();
            } else {
                answer="Нет такой команды\nЯ вас не понимаю";
                sendMessage(chatId, "Нет такой команды\nЯ вас не понимаю");
            }
        }
    }

    public String getAnswer(){
        return answer;
    }
    private void startCommand(Long chatId) {
        answer = "Привет, ты попал в бот знакомств";
        sendMessage(chatId, answer);
    }


    private void authorsCommand(Long chatId) {
        answer = "Этот бот делали Анастасия Вячеславовна и Тёткин Миша";
        sendMessage(chatId, answer);
    }

    private void aboutCommand(Long chatId) {
        answer = "Здесь вы найдете себе пару)";
        sendMessage(chatId, answer);
    }

    public void sendMessage(Long chatId, String textToSend) {
        SendMessage massage = new SendMessage();
        massage.setChatId(String.valueOf(chatId));
        massage.setText(textToSend);

        try {
            execute(massage);
        } catch (TelegramApiException e) {
            System.out.println("Uncorrect: " + e);
        }
    }

}
