package com.personal.PriceTracker;

public class ConstantText {
    public static final String START_TEXT = """
            Welcome to the Price Tracker Bot. This bot will take flipkart product link and a price that you wish to buy that product at.
            Commands:
            1) /start - To see information regarding this bot.
            
            2) /track - To start the tracking process.
            
            3) /list  - To see all the products this bot is tracking for you.
            
            4) /resume_{id} - To resume tracking of a product. Hit /list to see if there is any product paused for tracking.
            
            5) /delete_{id} - To delete tracking of a product. Hit /list to get the id of the product.
            
            6) /terminate - To terminate the process in between. Use this command before you start a new process to kill some previous ongoing process.
            """;
    public static final String LINK_TEXT = "Please enter a valid product link (Flipkart only).";
    public static final String PRICE_TEXT = "Please enter a valid price (Integer).";
    public static final String TERMINATE_TEXT = "Terminating the operation.";
    public static final String STOP_TEXT = "Great! I have started tracking this product for you.\nHit /track to track another product.";
    public static final String RESUME_TEXT = "Great! I have again started tracking this product for you.";
    public static final String DELETE_TEXT = "Great! I have stopped tracking this product for you.";
    public static final String UNEXPECTED_TEXT = "I didn't get that.";
    public static final String MESSAGE_TEXT = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
    public static final String NO_PRODUCTS_TO_TRACK_TEXT = "I am not tracking any products for you.";
    public static final String TRACKING_PRODUCTS_INITIAL_TEXT = "These are the products I am tracking for you\n\n";
    public static final String PRODUCT_NAME_TEXT = "Product Name: ";
    public static final String PRODUCT_FETCH_FAILURE_TEXT = "Sorry unable to proceed with the request. Please try again after some time.";
    public static final String CURRENT_PRICE_TEXT = "Current Price: ";
    public static final String REQUESTED_PRICE_TEXT = "Requested Price: ";
    public static final String STOP_TRACKING_TEXT = "Stop Tracking it? /delete_";
    public static final String RESUME_TRACKING_TEXT = "Resume Tracking? /resume_";
    public static final String SCHEDULER_TEXT1 = "Hey! The product you requested is currently sold at the price you wanted. ";
    public static final String SCHEDULER_TEXT2 = "Now is the right time to buy it.%0A%0A";
    public static final String SCHEDULER_TEXT3 = "%0A%0ACurrent Price: ";
    public static final String SCHEDULER_TEXT4 = "%0A%0A";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";
    public static final String PRICE_CLASS = "_30jeq3 _16Jk6d";
    public static final String NAME_CLASS = "B_NuCI";

}
