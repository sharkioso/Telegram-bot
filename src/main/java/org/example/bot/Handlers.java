package org.example.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class Handlers extends TelegramBot {
    Map<String, Runnable> commands = new HashMap<>();
    Map<String, String> helpText = new HashMap<>();

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
            sendMessage(chatId, helpText.get(messageText));
        } else {
            if (commands.containsKey(messageText)) {
                commands.get(messageText).run();
            } else {
                sendMessage(chatId, "Нет такой команды\nЯ вас не понимаю");
            }
        }
    }

    private void startCommand(Long chatId) {
        String answer = "Привет, ты попал в бот знакомств";
        sendMessage(chatId, answer);
    }

    private void authorsCommand(Long chatId) {
        String answer = "Этот бот делали Анастасия Вячеславовна и Тёткин Миша";
        sendMessage(chatId, answer);
    }

    private void aboutCommand(Long chatId) {
        String answer = "Здесь вы найдете себе пару)";
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
