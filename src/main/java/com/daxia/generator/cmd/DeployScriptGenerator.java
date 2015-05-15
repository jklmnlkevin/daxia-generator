package com.daxia.generator.cmd;

import java.util.Scanner;

/**
 * 生成部署脚本
 */
public class DeployScriptGenerator extends BaseCmd {
    
    public static void main(String[] args) {
        try {
            new DeployScriptGenerator().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("生成失败，按任意键退出");
    }

    @Override
    protected void run() {
        System.out.println("************************");
        System.out.println("部署脚本生成程序");
        System.out.println("************************");
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("请输入项目根目录的路径：");
        String codePath = scanner.nextLine();
        notNull(codePath);
        
        System.out.println("请输入war包名，如hello.war就输入hello");
        String warName = scanner.nextLine();
        notNull(warName);
        warName = warName + ".war";
        
        System.out.println("请输入tomcat的根目录：");
        String tomcatPath = scanner.nextLine();
        notNull(tomcatPath);
        
        System.out.println("生成完成");
    }
}
