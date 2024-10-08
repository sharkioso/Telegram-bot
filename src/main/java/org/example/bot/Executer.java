package org.example.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public abstract class Executer extends TelegramBot {
    public void sendMessage(Long chatId, String textToSend) {
        SendMessage massage = new SendMessage();
        massage.setChatId(String.valueOf(chatId));
        massage.setText(textToSend);

        try {
            execute(massage);
        } catch (TelegramApiException e) {
            System.out.println("Uncorrected: " + e);
        }
    }
}
