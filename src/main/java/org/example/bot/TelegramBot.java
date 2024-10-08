package org.example.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getText());
            Long chatID = update.getMessage().getChatId();
            String text = update.getMessage().getText().toLowerCase();
            if (text.charAt(0) == '/') {
                Handlers messageList = new Handlers();
                messageList.telegramHandlers(chatID, text);
            } else {
                Dialog messageList = new Dialog();
                messageList.dialogProcess(chatID, text);
            }
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
