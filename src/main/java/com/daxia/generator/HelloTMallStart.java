package com.daxia.generator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HelloTMallStart {
    public static void main(String[] args) throws Exception {
        String id = "19874557980";
        String url = "http://detail.tmall.com/item.htm?spm=a230r.1.14.27.rRWm47&id={id}&ad_id=&am_id=&cm_id=140105335569ed55e27b&pm_id=";
        url = url.replace("{id}", id);
        
        Document doc = Jsoup.connect(url).get();
        
        // System.out.println(doc.html());
        
        Elements elements = doc.select("#J_listBuyerOnView");
        // System.out.println(elements.attr("detail:params"));
        String detailUrl = elements.attr("detail:params");
        String sbn = detailUrl.split(",showBuyerList")[0].split("&sbn=")[1];
        String sellerNumId = detailUrl.split("&seller_num_id=")[1].split("&isFromDetail=")[0];
        // System.out.println("sbn = " + sbn);
        // System.out.println("sellerNumId = " + sellerNumId);
        String bidPage = "1";
        HelloTMall.main(bidPage, id, sbn, sellerNumId);
    }
}
