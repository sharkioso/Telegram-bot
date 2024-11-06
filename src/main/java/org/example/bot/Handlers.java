package org.example.bot;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.example.DB.DBConection.*;


public class Handlers extends Executer {
    LinkedHashMap<String, Consumer<Long>> commands = new LinkedHashMap<>();
    LinkedHashMap<String, String> helpText = new LinkedHashMap<>();
    List<String> startButtonText= new ArrayList<>();
    public String answer = "";


    public Handlers() {
        commands.put("/start", this::startCommand);
        commands.put("/authors", this::authorsCommand);
        commands.put("/about", this::aboutCommand);
        commands.put("/register", this::registrationCommand);
        commands.put("/viewing", this::viewingCommand);
        commands.put("/age", this::ageCommand);
        commands.put("/geo", this::geoCommand);
        commands.put("/gender_w", this::womanCommand);
        commands.put("/gender_m", this::manCommand);
        //    commands.put("/help",this::helpCommand);


        helpText.put("start", "Это команда для начала нашего общения");
        helpText.put("authors", "Это команда, которая расскажет тебе, кто написал этого бота");
        helpText.put("about", "Эта команда описывает бота");
        helpText.put("/help", "Бот имеет следующие команды: \n/start\n/about\n/authors\n/register\n/help\nДля" +
                " подробной информации введите \"/help команда\"");
        helpText.put("help", "Вводя эту команду вы можете узнать какие команды умеет делать бот");
        helpText.put("register", "Введите эту команду для начала регистрации");
        helpText.put("gender","используя эту команду можно задать параетр поиска, мужчины или женщины\n gender_m" +
                " - мужчины\n gender_w - Женщины");
        helpText.put("viewing","команда для начала просмотра анкет");
        helpText.put("age","Команда устанавливает минимальный и максимальный возраст поиска");
        helpText.put("geo","Команда устанавливает критерий поиска по вашему местоположению");

        startButtonText.add("/start");
        startButtonText.add("/register");
        startButtonText.add("/help");
        startButtonText.add("/viewing");

        //buttonText.put("❤\uFE0F","aa");
        //buttonText.put("\uD83D\uDC4E","aa");
    }


    public void telegramHandlers(Long chatId, String messageText) {
        if (messageText.contains("/help")) {
            if (messageText.length() > 6) {
                messageText = messageText.substring(6);
            }
            answer = helpText.get(messageText);
            sendMessage(chatId,answer);
        } else {
            if (commands.containsKey(messageText)) {
                commands.get(messageText).accept(chatId);
            } else {
                answer = "Нет такой команды\nЯ вас не понимаю ❤";
                sendMessage(chatId, answer);
            }
        }
    }


    public String getAnswer() {
        return answer;
    }


    private void startCommand(Long chatId) {
        answer = "Привет, ты попал в бот знакомств";
        startKeyboardCreator(chatId, startButtonText, answer);
    }

    private void registrationCommand(long chatId) {
        if (isUserExist(chatId)) {
            sendMessage(chatId, "У тебя уже есть анкета");
        } else {
            addPersonState(chatId, "name");
            addFilterPerson(chatId);
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


    private void viewingCommand(Long chatId) {
        if (!isUserExist(chatId)) {
            answer = "Упс, у тебя ещё нет анкеты, давай её заведем?)\n" +
                    "Для это отправь команду \"/register\"";
            sendMessage(chatId, answer);
        } else {
            answer = "Отлично, давай приступим к просмотру, если тебе " +
                    "понравится чужая анкета, отправь \"Лайк\", если нет отправь \"Фу\"\n" +
                    "Если все понятно напиши \"Ок\"";
            sendMessage(chatId, answer);
            changeState(chatId, "looking");
        }
    }


    private void ageCommand(Long chatId){
        sendMessage(chatId, "Напиши максимальный возрост который тебе подходит");
        changeState(chatId, "age_max");
    }


    private void geoCommand(Long chatId){
        sendMessage(chatId, "Отправь мне свою геолокацию, и я ее автоматически запомню");
    }


    private void womanCommand(Long chatId){
        changeGender(chatId, false);
        answer = "Отлично, я все запомнил";
        sendMessage(chatId, answer);
    }


    private void manCommand(Long chatId){
        changeGender(chatId, true);
        answer ="Отлично, я все запомнил";
        sendMessage(chatId, answer);
    }
}
