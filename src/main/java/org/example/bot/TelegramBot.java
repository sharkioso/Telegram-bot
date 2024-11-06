package org.example.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.DB.DBConection.changeGeo;


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
        } else if (update.hasMessage() && update.getMessage().hasLocation()){
            Double latitude = update.getMessage().getLocation().getLatitude();
            Double longitude = update.getMessage().getLocation().getLongitude();
            changeGeo(update.getMessage().getChatId(), latitude, longitude);
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
