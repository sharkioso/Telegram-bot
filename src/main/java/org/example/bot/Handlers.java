package org.example.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.example.DB.DBConection.insertPerson;


public class Handlers extends TelegramBot {
    Map<String, Consumer<Long>> commands = new LinkedHashMap<>();

    static Map<Long,FSM> usersRegInfo=new LinkedHashMap<>();
    static Map<Long, InfoUser> registerInfo = new LinkedHashMap<>();

    Map<String,BiConsumer<Long,String>> commandsFSM = new LinkedHashMap<>();
    Map<String, String> helpText = new LinkedHashMap<>();

    public String answer = "";


    public Handlers(){
        commands.put("/start",this::startCommand);
        commands.put("/authors",this::authorsCommand);
        commands.put("/about",this::aboutCommand);
        commands.put("/register",this::registrationCommand);

        commandsFSM.put("name", this::setName);
        commandsFSM.put("gender", this::setGender);
        commandsFSM.put("town", this::setTown);
        commandsFSM.put("age", this::setAge);
        commandsFSM.put("discription",this::setDiscription);

        helpText.put("start", "Это команда для начала нашего общения");
        helpText.put("authors", "Это команда, которая расскажет тебе, кто написал этого бота");
        helpText.put("about", "Эта команда описывает бота");
        helpText.put("/help", "Бот имеет следующие команды: \n/start\n/about\n/authors\n/register\n/help\nДля подробной информации введите \"/help команда\"");
        helpText.put("help", "Вводя эту команду вы можете узнать какие команды умеет делать бот");
    }


    public void telegramHandlers(Long chatId,String messageText) {
        if (usersRegInfo.containsKey(chatId)){
            if(usersRegInfo.get(chatId).currentState=="registred"){
                sendMessage(chatId,"А всё заюш, хуй тебе а не регистрация");
            }
            else{
            commandsFSM.get(usersRegInfo.get(chatId).currentState).accept(chatId,messageText);}
        }
        else if (messageText.contains("/help")) {
            if (messageText.length() > 6) {
                messageText = messageText.substring(6);
            }
            answer =helpText.get(messageText);
            sendMessage(chatId, helpText.get(messageText));
        } else {
            if (commands.containsKey(messageText)) {
                commands.get(messageText).accept(chatId);
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


    private void registrationCommand(long chatId) {
        FSM automat= new FSM();
        usersRegInfo.put(chatId,automat);
        sendMessage(chatId,"Отлично, давай знакомиться!\nКак тебя зовут?");
        usersRegInfo.get(chatId).changeState("name");
        System.out.println(usersRegInfo.get(chatId).currentState);
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


    private void setName(long chatId, String messageText) {
        InfoUser user = new InfoUser();
        registerInfo.put(chatId, user);
        registerInfo.get(chatId).setName(messageText);
        sendMessage(chatId, "Прекрасное имя, какого ты пола?");
        usersRegInfo.get(chatId).changeState("gender");
    }


    private void setGender (long chatId, String messageText){
        registerInfo.get(chatId).setGender(messageText);
        sendMessage(chatId,"из какого ты города?");
        usersRegInfo.get(chatId).changeState("town");
    }


    private void setTown(long chatId, String messageText) {
        registerInfo.get(chatId).setTown(messageText);
        sendMessage(chatId, "Отлично, сколько тебе лет?");
        usersRegInfo.get(chatId).changeState("age");
    }


    private void setAge(long chatId, String messageText) {
        registerInfo.get(chatId).setAge(messageText);
        sendMessage(chatId, "Опиши себе несколькими словами");
        usersRegInfo.get(chatId).changeState("discription");
    }


    private void setDiscription(long chatId,String messageText) {
        registerInfo.get(chatId).setAbout(messageText);;
        sendMessage(chatId, "Давай посмотрим что у нас получилось");
        sendMessage(chatId, registerInfo.get(chatId).allInfo());
        usersRegInfo.get(chatId).changeState("registred");
        System.out.println(usersRegInfo.get(chatId).currentState);
        insertPerson(String.valueOf(chatId), registerInfo.get(chatId).getName(), registerInfo.get(chatId).getGender(),
                registerInfo.get(chatId).getAge(), registerInfo.get(chatId).getTown(), registerInfo.get(chatId).getAbout());
    }
}
