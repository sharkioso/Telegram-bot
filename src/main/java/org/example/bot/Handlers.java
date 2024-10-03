package org.example.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.example.bot.FSMHandler.FSMCommands;


public class Handlers extends TelegramBot {
    Map<String, BiConsumer<Long,String>> commands = new LinkedHashMap<>();

    Map<String, String> helpText = new LinkedHashMap<>();
    public String answer = "";
    public Handlers(){
        commands.put("/start",this::startCommand);
        commands.put("/authors",this::authorsCommand);
        commands.put("about",this::aboutCommand);

        helpText.put("start", "Это команда для начала нашего общения");
        helpText.put("authors", "Это команда, которая расскажет тебе, кто написал этого бота");
        helpText.put("about", "Эта команда описывает бота");
        helpText.put("/help", "Бот имеет следующие команды: \n/start\n/about\n/authors\n/help\nДля подробной информации введите \"/help команда\"");
        helpText.put("help", "Вводя эту команду вы можете узнать какие команды умеет делать бот");
    }
    public void telegramHandlers(Long chatId,String messageText) {
        if (messageText.contains("/help")) {
            if (messageText.length() > 6) {
                messageText = messageText.substring(6);
            }
            answer =helpText.get(messageText);
            sendMessage(chatId, helpText.get(messageText));
        } else {
            if (commands.containsKey(messageText)) {
                commands.get(messageText).accept(chatId,messageText);
            } else {
                answer="Нет такой команды\nЯ вас не понимаю";
                sendMessage(chatId, "Нет такой команды\nЯ вас не понимаю");
            }
        }
    }

    private void reg(Long chatId,Update update) {
        FSMHandler automat = new FSMHandler(chatId,update);
        automat.registration(chatId);
    }

    public String getAnswer(){
        return answer;
    }
    private void startCommand(Long chatId,String text) {
        answer = "Привет, ты попал в бот знакомств";
        sendMessage(chatId, answer);
    }


    private void authorsCommand(Long chatId,String text) {
        answer = "Этот бот делали Анастасия Вячеславовна и Тёткин Миша";
        sendMessage(chatId, answer);
    }

    private void aboutCommand(Long chatId,String text) {
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
