package org.example.bot;

import java.util.*;
import java.util.function.BiConsumer;

import static org.example.DB.DBConection.*;

public class Dialog extends Executer {

    Map<String, BiConsumer<Long, String>> commandsFSM = new LinkedHashMap<>();
    static Map<Long, InfoUser> registerInfo = new LinkedHashMap<>();
    static String answerDialog = "";
    List<String> viewingKeyboardButtons = new ArrayList<>();
    static String answer = " ";
    public Dialog() {
        commandsFSM.put("name", this::setName);
        commandsFSM.put("gender", this::setGender);
        commandsFSM.put("town", this::setTown);
        commandsFSM.put("age", this::setAge);
        commandsFSM.put("discription", this::setDiscription);
        commandsFSM.put("looking", this::getProfile);
        commandsFSM.put("age_max", this::ageMax);
        commandsFSM.put("age_min", this::ageMin);

        viewingKeyboardButtons.add("❤\uFE0F");
        viewingKeyboardButtons.add("👎");
    }


    public void dialogProcess(long chatId, String messageText) {
        if (getState(chatId) != null) {
            commandsFSM.get(getState(chatId)).accept(chatId, messageText);
        } else {
            sendMessage(chatId, "Извините, я вас не понимаю\nЕсли вы не знаете, " +
                    "что делать отправте \"/help\"");
        }
    }


    private void setName(long chatId, String messageText) {
        InfoUser user = new InfoUser();
        registerInfo.put(chatId, user);
        registerInfo.get(chatId).setName(messageText);
        sendMessage(chatId, "Прекрасное имя, какого ты пола?");
        changeState(chatId, "gender");
    }


    private void setGender(long chatId, String messageText) {
        registerInfo.get(chatId).setGender(messageText);
        sendMessage(chatId, "из какого ты города?");
        changeState(chatId, "town");
    }


    private void setTown(long chatId, String messageText) {
        registerInfo.get(chatId).setTown(messageText);
        sendMessage(chatId, "Отлично, сколько тебе лет?");
        changeState(chatId, "age");
    }


    private void setAge(long chatId, String messageText) {
        registerInfo.get(chatId).setAge(messageText);
        sendMessage(chatId, "Опиши себе несколькими словами");
        changeState(chatId, "discription");
    }

    private void setDiscription(long chatId, String messageText) {
        registerInfo.get(chatId).setAbout(messageText);
        ;
        sendMessage(chatId, "Давай посмотрим что у нас получилось");
        answerDialog = registerInfo.get(chatId).allInfo();
        sendMessage(chatId, answerDialog);
        changeState(chatId, null);
        System.out.println(getState(chatId));
        insertPerson(String.valueOf(chatId), registerInfo.get(chatId).getName(), registerInfo.get(chatId).getGender(),
                registerInfo.get(chatId).getAge(), registerInfo.get(chatId).getTown(), registerInfo.get(chatId).getAbout());
    }



    private void getProfile(long chatId, String massageText) {
        if (massageText.equalsIgnoreCase("Ок")) {
            sendMessage(chatId, "Если хочешь добавить какие-нибудь фильтры для поиска напиши \"Фильтры\"" +
                    "\n Если не хочешь ничего добавлять напиши \"Начать\"");
        } else if (massageText.equalsIgnoreCase("Фильтры")){
            sendMessage(chatId, "Ты можешь добавить следующие фильтры:\n/age добавит возрастные ограничения" +
                    "\n/geo будет искать ближайших людей\n/gender_m будет показывать только мужчин" +
                    "\n/gender_w будет показывать только женщин\nПотом чтобы вернуться к просмотру анкет напиши /viewing");
        } else {
            String ans;
            int count_look = getNumPerson(chatId);
            ans = getPersonForNumber(count_look);

            if (ans.equalsIgnoreCase(String.valueOf(chatId))) {
                count_look += 1;
                changeNumPerson(chatId, count_look + 1);
                ans = getPersonForNumber(count_look);
            }

            if (ans.equals("")) {
                sendMessage(chatId, "Ты посмотрел все анкеты");
                changeState(chatId, null);
            }

            String ansWhile = ans;
            while (!profilIsOk(chatId, Long.parseLong(ansWhile)) && !ansWhile.equals("")) {
                if (ans.equalsIgnoreCase(String.valueOf(chatId))) {
                    count_look += 1;
                    changeNumPerson(chatId, count_look + 1);
                    ansWhile = getPersonForNumber(count_look);
                } else {
                    count_look += 1;
                    changeNumPerson(chatId, count_look + 1);
                    ansWhile = getPersonForNumber(count_look);
                }

                if (ansWhile.equals("")) {
                    sendMessage(chatId, "Ты посмотрел все анкеты");
                    changeState(chatId, null);
                }
            }

            ans = ansWhile;
            if (!ans.equals("")) {
                if (massageText.equalsIgnoreCase("Начать")) {
                    startKeyboardCreator(chatId,viewingKeyboardButtons,"Вот первая анкета, если захочешь остановить" +
                                    " знакомство, напиши \"Стоп\"");
                    sendMessage(chatId, sendPerson(Integer.parseInt(ans)));
                    changeNumPerson(chatId, count_look + 1);
                } else if (massageText.equalsIgnoreCase("\uD83D\uDC4E")) {
                    answer = "Понятно, это тебе не подходит\nДавай смотреть дальше";
                    sendMessage(chatId, answer);
                    sendMessage(chatId, sendPerson(Integer.parseInt(ans)));
                    changeNumPerson(chatId, count_look + 1);
                } else if (massageText.equalsIgnoreCase("❤\uFE0F")) {
                    answer = "Отлично, будем надеяться, что это взаимно";
                    sendMessage(chatId, answer);
                    sendMessage(chatId, sendPerson(Integer.parseInt(ans)));
                    changeNumPerson(chatId, count_look + 1);
                } else if (massageText.equalsIgnoreCase("Стоп")) {
                    answer = "Хорошо, давай остановимся, продолжим когда захочешь)";
                    sendMessage(chatId, answer);
                    changeState(chatId, null);
                } else {
                    sendMessage(chatId, "Извини, я не понимаю, что это значит, напиши\n" +
                            "\"Стоп\" если хочешь остановиться\n\"Лайк\" если тебе понравилась анкета\n\"Фу\" " +
                            "если анкета так себе\n\"Фильры\n если хочешь поменять фильтры для поиска");
                }
            } else {
                sendMessage(chatId, "Ты посмотрел все анкеты");
                changeState(chatId, null);
            }
        }

    }


    private void ageMax(long chatId, String massageText) {
        changeMaxAge(chatId, Integer.parseInt(massageText));
        sendMessage(chatId, "Напиши минимальный возраст который тебе подходит");
        changeState(chatId, "age_min");
    }


    private void ageMin(long chatId, String massageText) {
        changeMinAge(chatId, Integer.parseInt(massageText));
        sendMessage(chatId, "Отлично, я все запомнил");
        changeState(chatId, null);
    }


    private boolean profilIsOk(long chatIdFirst, long chatIdSecond) {
        int ageMin = getAgeMin(chatIdFirst);
        int ageMax = getAgeMax(chatIdFirst);
        int age = getAgePerson(chatIdSecond);

        if (ageMin > age) {
            return false;
        }

        if (ageMax < age) {
            return false;
        }

        if ((!Objects.equals(getGenderLike(chatIdFirst), ""))) {
            if (!Objects.equals(getGenderLike(chatIdFirst), getGenderPerson(chatIdSecond))) {
                return false;
            }
        }

        if (!Objects.equals(getGeo(chatIdFirst), null)) {
            return !Objects.equals(getGeo(chatIdSecond), null);
        }

        return true;
    }

    public String getAnswerDialog(){
        return answer;
    }
}
