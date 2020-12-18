package com.example.hamahaki;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class searchGigantti extends Thread{
    private static String url =  "https://www.gigantti.fi/search?SearchTerm=";
    private ArrayList<ParseItem> parseItems = new ArrayList<>();

    public searchGigantti(String word){
            String str = word.replaceAll(" ", "+");
            url = url+str;
    }

    public void run(){
        try {
            Document doc = Jsoup.connect(url).get();

            Log.d("url text:", doc.text());
            if(doc.text().isEmpty()){
                Log.d("DOC:","is empty");
            }
            Elements data = doc.select("div.mini-product-list.no-row.even-flex");

            Elements links = data.select("div.mini-product");

            int size = links.size();
            for(int i = 0;i<size;i++) {
                String price = links.select("div.product-price").eq(i).text();
                String title = links.select("span.table-cell").eq(i).text();
                String img = links.select("img.b-lazy.product-image").eq(i).attr("data-src");

                String imgUrl = "https://www.gigantti.fi" + img;
                String productUrl = links.select("a.product-name").eq(i).attr("href");

                if(price.isEmpty()) {
                    price = "€€€€€€";
                    parseItems.add(new ParseItem(imgUrl, "Tuote ei saatavilla", price,productUrl));
                }else {
                    String str = price.replaceAll(",", ".");

                    //Add decimals for better look
                    if(str.indexOf(".") == -1){
                        price = price + ".00€";
                    }else if(str.length()-str.indexOf(".") == 3){
                        price = price + "€";
                    }
                    parseItems.add(new ParseItem(imgUrl, title, price,productUrl));
                }

                //Log.d("Price", price + " title: " + title + " Imgurl: " + imgUrl);
                //Log.d("Link: ", productUrl);
                //Log.d("Price", price);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ParseItem> getParseItems() {
        return parseItems;
    }
}
