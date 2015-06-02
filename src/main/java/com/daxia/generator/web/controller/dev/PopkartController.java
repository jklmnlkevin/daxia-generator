package com.daxia.generator.web.controller.dev;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/p", produces="text/html;charset=UTF-8")
public class PopkartController {
    int i = 4;
    private NameGenerator nameGenerator = new NameGenerator();
    private IDGenerator idGenerator = new IDGenerator();
    
    @ResponseBody
    @RequestMapping("info")
    public String info(String callback) {
        JSONObject json = new JSONObject();
        json.put("username", "zyjk28abc" + i);
        json.put("password", "hello123");
        json.put("realName", nameGenerator.getName());
        i ++;
        json.put("idCard", idGenerator.getIDCard());
        System.out.print("用户名: " + json.getString("username"));
        System.out.print("\t密码：" + json.getString("password"));
        System.out.print("\t姓名：" + json.getString("realName"));
        System.out.println("\t\t身份证：" + json.getString("idCard"));
        return callback + "(" + json.toJSONString() + ");";
    }
    
}
