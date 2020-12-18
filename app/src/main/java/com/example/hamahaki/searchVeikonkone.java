package com.example.hamahaki;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class searchVeikonkone extends Thread{
    private static String url =  "https://www.veikonkone.fi/catalogsearch/result/?q=";
    private ArrayList<ParseItem> parseItems = new ArrayList<>();

    public searchVeikonkone(String word){
        String str = word.replaceAll(" ", "+");
        url = url+str;
    }

    public void run(){
        Log.d("Url", url);
        try {

            Document doc = Jsoup.connect(url).get();
            //Elements data = doc.select("div.mini-product-list.no-row.even-flex");
            Elements data = doc.select("div.products.wrapper.grid.products-grid");
            Elements links = data.select("div.product-item-info");

            int size = links.size();
            for(int i = 0;i<size;i++) {
                String price = links.select("span.price").eq(i).text();
                String title = links.select("a.product-item-link").eq(i).text();

                String imgUrl = links.select("img.product-image-photo").eq(i).attr("src");

                //String imgUrl = "https://www.gigantti.fi" + img;
                String productUrl = links.select("a.product-item-link").eq(i).attr("href");
                /*
                if(price.isEmpty()) {
                    parseItems.add(new ParseItem(imgUrl, "Tuote ei saatavilla", price,productUrl));
                }else {
                    parseItems.add(new ParseItem(imgUrl, title, price,productUrl));
                }*/

                //Log.d("items", "img" + imgUrl + " title: " + title);
                Log.d("Price", price + " title: " + title + " Imgurl: " + imgUrl);
                Log.d("Link: ", productUrl);
                //Log.d("Price", price);

                parseItems.add(new ParseItem(imgUrl, title, price,productUrl));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ParseItem> getParseItems() {
        return parseItems;
    }


}
