package com.daxia.generator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HelloTMall {
    public static void main(String bidPage, String itemId, String sbn, String sellerNumId) throws Exception {
        
        /*String bidPage = "2"; // 分页的
        // 以下这三个参数每个商品都不一样
        String itemId = "19435486411";
        String sbn = "7460b531e2d29baa2fe935ba9491b6df";
        String sellerNumId = "1598733288";*/
        
        String url = "http://ext.mdskip.taobao.com/extension/dealRecords.htm?_ksTS=1405060533693_2680&callback=jsonp2681&isOffline=&pageSize=15&isStart=false&itemType=b&ends=1405175406000&starts=1404570606000&itemId={itemId}&soldTotalNum=2383&sellerNumId={sellerNumId}&isFromDetail=yes&totalSQ=22220&sbn={sbn}&isSecKill=false&isOriginPrice=false&bidPage={bidPage}&ua=206tpOWUuXBidH1MRUA99K3ssfit9LnkrZhtg%3D%3D%7CtaBkcTQxZGEEAVRx9LG0wRY%3D%7CtJFV4sbx6YGZsCgMyOypjUltGoJqItqjmr5pvg%3D%3D%7Cs6aDR2N2MzZjZgMGU3bztrPGY2aCt%2BKH0ofS52IXAkcCl5IXIgbR%7Csqcy9kFUkNXQpWEk4MRARcz53Qrd%7CsYRA9%2FI2M%2Ffi15IHw6ajlgPH0ueC5yNWM7ZyZ1I3UpbjxkOUQw%3D%3D%7CsPUxhqKGQjdSF9P2cxZztzIncqVy%7Cv%2Fo%2Bia2JTdi9iExpDGks6K3IvSj%2FKA%3D%3D%7CvusvmLyYXOv%2B%2B967f1o%2Fqo9YnJldeV2ZzNmsKf4p%7Cvfj9%2BDw5%2FdgcCUxZnaj9iEwJzdjdGYxIXSj%2F&";
        
        url = url.replace("{bidPage}", bidPage);
        url = url.replace("{itemId}", itemId);
        url = url.replace("{sbn}", sbn);
        url = url.replace("{sellerNumId}", sellerNumId);
        
        Connection connection = Jsoup.connect(url).ignoreContentType(true);
        connection = connection.header("Accept", "*/*");
        connection = connection.header("Accept-Encoding", "gzip,deflate,sdch");
        connection = connection.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,sq;q=0.2");
        connection = connection.header("Connection", "keep-alive");
        connection = connection.header("Cookie", "cna=IUogDD5zbFMCAXHwCZ3ZtQpw; ali_ab=113.240.251.178.1403078871067.0; lzstat_uv=340629082557287822|1267385@2581762@2945730@2948565@2798379@2043323@3045821@2706024; uc3=nk2=GcAlIhnlDJnjT%2BGUKQ%3D%3D&id2=VAMVf2eJk0rB&vt3=F8dATHwD8tbpNiKLVZA%3D&lg2=VT5L2FSpMGV7TQ%3D%3D; lgc=zhukewen_java; tracknick=zhukewen_java; _cc_=UIHiLt3xSw%3D%3D; tg=0; mt=ci=0_1; l=zhukewen_java::1404749272504::11; v=0; _tb_token_=nvbhciI0oyw6; wud=wud; linezing_session=ECtjY87MLX23C9dgu0RyJugV_14049917451435fpI_1; _m_h5_tk=7bccae60790d3f886f424ce6b0a7e6d3_1405063391267; _m_h5_tk_enc=184df8bf3d9ea95779719bd1c406e1f5; cookie2=d2f36392aa01f77637f42feee9e752b4; t=353e014597f6a53dbe098b4f4ae66af7; isg=DFB8D714B3A5B1F26D3E6218E6C16F2C; uc1=cookie14=UoW3uiifd9XD%2Bw%3D%3D");
        connection = connection.header("Host", "ext.mdskip.taobao.com");
        connection = connection.header("Referer", "http://detail.tmall.com/item.htm?spm=a230r.1.14.2.rRWm47&id=21889091510&ad_id=&am_id=&cm_id=140105335569ed55e27b&pm_id=");
        connection = connection.header("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        
        Document doc = connection.get();
        // System.out.println(doc.html());
        String text = doc.html();
        text = text.substring(text.indexOf("(") + 1);
        text = text.substring(0, text.lastIndexOf(")"));
        text = text.replace("{html:&quot; ", "");
        text = text.replace(" &quot;,type:&quot;list&quot;}", "");
        text = text.replace("\\&quot;", "");
        // System.out.println(text);
        
        //JSONObject jsonObj = JSONObject.parseObject(text);
        doc = Jsoup.parse(text);
        // System.out.println(doc.text());
        Elements table = doc.select("table.table-deal-record");
        Elements trs = table.select("tbody tr:gt(0)");
        for (int i = 0; i < trs.size(); i++) {
            Element tr = trs.get(i);
            Elements tds = tr.select("td");
            String buyer = tds.get(0).text();
            String style = tds.get(1).text();
            String quantity = tds.get(2).text();
            String price = tds.get(3).text();
            String dealtime = tds.get(4).text();
            System.out.println(buyer + "\t" + style + "\t" + quantity + "\t" + price + "\t" + dealtime);
        }
//        System.out.println(trs);
        
    }
}
