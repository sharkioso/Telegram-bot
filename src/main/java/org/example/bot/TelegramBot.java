package org.example.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        Handlers messageList = new Handlers();

        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getText());
            Long chatID = update.getMessage().getChatId();
            String text = update.getMessage().getText().toLowerCase();
            messageList.telegramHandlers(text, chatID);
            String kk = messageList.getAnswer();
            System.out.println(kk);
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
