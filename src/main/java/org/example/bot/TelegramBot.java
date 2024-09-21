package org.example.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        String chatID = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setText(text);

        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getBotToken() {
        return System.getenv("TelegramBotToken");
    }


    @Override
    public String getBotUsername() {
        return System.getenv("TelegramBotName");
    }
}
