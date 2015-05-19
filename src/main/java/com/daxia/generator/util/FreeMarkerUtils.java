package com.daxia.generator.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtils {

    /**
     * 
     * @param templateFile 模板文件位置，是个相对路径，以项目根目录为起点，（不要以/开头）
     * @param params 参数
     * @param outputFile 输出文件，是个绝对路径
     */
    public static void generate(String templateFile, Map<String, Object> params, String outputFile) {
        try {
            doGenerate(templateFile, params, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doGenerate(String templateFile, Map<String, Object> params, String outputFile) throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/template"));
        configuration.setObjectWrapper(new DefaultObjectWrapper());  
        configuration.setDefaultEncoding("UTF-8");
        Template template = configuration.getTemplate(templateFile);
        
        Writer writer  = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");  
        template.process(params, writer);  
        System.out.println("生成文件至" + outputFile);
        
    }
    
    public static void main(String[] args) {
        String templateFile = "deploy.ftl";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("codePath", "/tmp/code/weiquan");
        params.put("tomcatPath", "/tmp/tomcat7.0.61");
        params.put("warName", "weiquan");
        String outputFile = "/tmp/deploy.bat";
        generate(templateFile, params, outputFile);
    }
}
