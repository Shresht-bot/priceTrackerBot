package com.personal.PriceTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import static com.personal.PriceTracker.ConstantText.*;
import static com.personal.PriceTracker.UserState.*;

@Component
public class ResponseHandler {
    @Autowired
    private TrackingService trackingService;

    private final Map<Long, UserState> chatStates = new HashMap<>();
    private final Map<Long, String> linkStates = new HashMap<>();
    private final Pattern resumePattern = Pattern.compile("/resume_\\d+");
    private final Pattern deletePattern = Pattern.compile("/delete_\\d+");

    public SendMessage replyToMessage(long chatId, String message) {
        if (message.equals("/terminate")) return replyToTerminate(chatId);

        if (!chatStates.containsKey(chatId)){
            if (message.equals("/start")) return replyToStart(chatId);
            else if (message.equals("/track")) return replyToTrack(chatId);
            else if (message.equals("/list")) return replyToList(chatId);
            else if (resumePattern.matcher(message).matches()) return replyToResume(chatId, message);
            else if (deletePattern.matcher(message).matches()) return replyToDelete(chatId, message);
            else return unexpectedMessage(chatId);
        }
        switch (chatStates.get(chatId)){
            case SEND_URL -> {
                return replyToLink(chatId, message);
            }
            case SEND_PRICE -> {
                return replyToPrice(chatId, message);
            }
            default -> {
                return unexpectedMessage(chatId);
            }
        }
    }

    private SendMessage replyToList(long chatId){
        String message = trackingService.getProductsForUser(chatId);
        return SendMessage.builder().chatId(chatId).text(message).build();
    }

    private SendMessage replyToStart(long chatId) {
        return SendMessage.builder().chatId(chatId).text(START_TEXT).build();
    }

    private SendMessage replyToTrack(long chatId) {
        chatStates.put(chatId, SEND_URL);
        return SendMessage.builder().chatId(chatId).text(LINK_TEXT).build();
    }

    private SendMessage replyToLink(long chatId, String message) {
        if (HelperClass.checkUrl(message)) {
            linkStates.put(chatId, message);
            chatStates.put(chatId, SEND_PRICE);
            return SendMessage.builder().chatId(chatId).text(PRICE_TEXT).build();
        }
        else return unexpectedMessage(chatId);
    }

    private SendMessage replyToPrice(long chatId, String message) {
        if (HelperClass.checkPrice(message)) {
            trackingService.saveToDB(chatId, linkStates.get(chatId), message);
            chatStates.remove(chatId);
            linkStates.remove(chatId);
            return SendMessage.builder().chatId(chatId).text(STOP_TEXT).build();
        }
        else return unexpectedMessage(chatId);
    }

    private SendMessage replyToResume(long chatId, String message){
        String s = message.substring(8);
        trackingService.resumeProduct(chatId, s);
        return SendMessage.builder().chatId(chatId).text(RESUME_TEXT).build();
    }

    private SendMessage replyToDelete(long chatId, String message){
        String s = message.substring(8);
        trackingService.deleteProduct(chatId, s);
        return SendMessage.builder().chatId(chatId).text(DELETE_TEXT).build();
    }

    private SendMessage replyToTerminate(long chatId) {
        chatStates.remove(chatId);
        linkStates.remove(chatId);
        return SendMessage.builder().chatId(chatId).text(TERMINATE_TEXT).build();
    }

    private SendMessage unexpectedMessage(long chatId) {
        return SendMessage.builder().chatId(chatId).text(UNEXPECTED_TEXT).build();
    }
}
