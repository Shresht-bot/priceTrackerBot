package com.personal.PriceTracker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import static com.personal.PriceTracker.ConstantText.*;

public class HelperClass {

    public static boolean checkUrl(String message) {
        Document doc = getDom(message);
        if (doc == null) return false;
        int price = fetchCurrentPrice(doc);
        return !(price == -1);
    }

    public static boolean checkPrice(String message){
        int price = fetchNumber(message);
        return !(price == -1);
    }

    public static Document getDom(String message) {
        try {
            return Jsoup
                    .connect(message)
                    .userAgent(USER_AGENT)
                    .header("Accept-Language", "*")
                    .get();
        } catch (IOException e) {
            return null;
        }
    }

    public static int fetchCurrentPrice(Document doc) {
        try {
            Elements elements = doc.getElementsByClass(PRICE_CLASS);
            if (elements.size() == 0) return -1;
            return NumberFormat.getNumberInstance(Locale.US).parse(elements.get(0).text().substring(1)).intValue();
        } catch (ParseException e) {
            return -1;
        }
    }

    public static String fetchName(Document doc) {
        return doc.getElementsByClass(NAME_CLASS).get(0).text();
    }

    public static int fetchNumber(String message) {
        try {
            return Integer.parseInt(message);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String updateUrl(String url){
        String ans = url.replaceAll("!","%21");
        ans = ans.replaceAll("\\s+","%20");
        ans = ans.replaceAll("\\.","%2E");
        ans = ans.replaceAll("\\(","%28");
        ans = ans.replaceAll("\\)","%29");
        ans = ans.replaceAll(",","%2C");
        ans = ans.replaceAll("/","%2F");
        ans = ans.replaceAll("-","%2D");
        ans = ans.replaceAll("\\?","%3F");
        ans = ans.replaceAll("=","%3D");
        ans = ans.replaceAll("&","%26");
        return ans;
    }


}
