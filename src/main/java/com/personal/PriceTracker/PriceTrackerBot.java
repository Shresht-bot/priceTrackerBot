package com.personal.PriceTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class PriceTrackerBot extends AbilityBot {
    @Autowired
    private ResponseHandler responseHandler;

    public PriceTrackerBot(Environment env) {
        super(env.getProperty("BOT_TOKEN"), "MyOwnPriceTrackerBot");
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = responseHandler.replyToMessage(update.getMessage().getChatId(), update.getMessage().getText());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::onUpdateReceived);
    }

    @Override
    public long creatorId() {
        return 1L;
    }
}
