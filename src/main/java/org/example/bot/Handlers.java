package org.example.bot;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.example.DB.DBConection.*;


public class Handlers extends Executer {
    Map<String, Consumer<Long>> commands = new LinkedHashMap<>();
    Map<String, String> helpText = new LinkedHashMap<>();

    public String answer = "";


    public Handlers() {
        commands.put("/start", this::startCommand);
        commands.put("/authors", this::authorsCommand);
        commands.put("/about", this::aboutCommand);
        commands.put("/register", this::registrationCommand);


        helpText.put("start", "Это команда для начала нашего общения");
        helpText.put("authors", "Это команда, которая расскажет тебе, кто написал этого бота");
        helpText.put("about", "Эта команда описывает бота");
        helpText.put("/help", "Бот имеет следующие команды: \n/start\n/about\n/authors\n/register\n/help\nДля подробной информации введите \"/help команда\"");
        helpText.put("help", "Вводя эту команду вы можете узнать какие команды умеет делать бот");
        helpText.put("register", "Введите эту команду для начала регистрации");
    }


    public void telegramHandlers(Long chatId, String messageText) {
        if (messageText.contains("/help")) {
            if (messageText.length() > 6) {
                messageText = messageText.substring(6);
            }
            answer = helpText.get(messageText);
            sendMessage(chatId, answer);
        } else {
            if (commands.containsKey(messageText)) {
                commands.get(messageText).accept(chatId);
            } else {
                answer = "Нет такой команды\nЯ вас не понимаю";
                sendMessage(chatId, answer);
            }
        }
    }


    public String getAnswer() {
        return answer;
    }


    private void startCommand(Long chatId) {
        answer = "Привет, ты попал в бот знакомств";
        sendMessage(chatId, answer);
    }


    private void registrationCommand(long chatId) {
        if (isUserExist(chatId)) {
            sendMessage(chatId, "У тебя уже есть анкета");
        } else {
            addPersonState(chatId, "name");
            sendMessage(chatId, "Отлично, давай знакомиться!\nКак тебя зовут?");
        }
    }


    private void authorsCommand(Long chatId) {
        answer = "Этот бот делали Анастасия Вячеславовна и Тёткин Миша";
        sendMessage(chatId, answer);
    }


    private void aboutCommand(Long chatId) {
        answer = "Здесь вы найдете себе пару)";
        sendMessage(chatId, answer);
    }
}
