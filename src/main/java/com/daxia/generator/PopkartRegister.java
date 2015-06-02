package com.daxia.generator;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class PopkartRegister {
    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("hi");
        String url = "http://member.tiancity.com/Registration/AccountReg.aspx?acode=gnb&bannerName=member";
        // String url = "http://www.baidu.com";
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        
        HtmlPage page = webClient.getPage(url);
        System.out.println("done");
        DomElement useridInput = page.getElementById("userid");
        useridInput.setAttribute("value", "hello");
        
        // System.out.println(page.asXml());
    }
}
