package com.daxia.generator.util.e;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class TemplateUtils_E {
	public static void generate(String projectPath, GenerateType_E[] generateType, Map<String, Object> paramMap) throws Exception {
		for (GenerateType_E type : generateType) {
	        generate(projectPath, type, paramMap);
        }
	}
	
	public static void generate(String projectPath, GenerateType_E generateType, Map<String, Object> paramMap) throws Exception {
		String templateName = generateType.getValue();
		
		//创建一个合适的Configration对象  
        Configuration configuration = new Configuration();  
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/utils/template/k"));  
        configuration.setObjectWrapper(new DefaultObjectWrapper());  
        configuration.setDefaultEncoding("UTF-8");   //这个一定要设置，不然在生成的页面中 会乱码  
        //获取或创建一个模版。  
        Template template = configuration.getTemplate(templateName);  
        String outFileName = projectPath + "/" + generateType.getPath() + "/" + templateName;
        if (generateType.getPath().startsWith("src/main/webapp")) {
        	outFileName = projectPath + "/" + generateType.getPath() + "/" + paramMap.get("model") + "/" + templateName;
        	File dir = new File(projectPath + "/" + generateType.getPath() + "/" + paramMap.get("model"));
        	if (!dir.exists()) {
        		dir.mkdir();
        	}
        }
        outFileName = outFileName.replace("Model", paramMap.get("Model").toString());
        outFileName = outFileName.replace(".ftl", ".java");
        outFileName = outFileName.replace("model", paramMap.get("model").toString());
        Writer writer  = new OutputStreamWriter(new FileOutputStream(outFileName), "UTF-8");  
        template.process(paramMap, writer);  
        System.out.println("Done!");  
    }
	
	/*public static void main(String[] args) throws Exception {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("Model", "Hello");
	    map.put("model", "hello");
	    for (GenerateType_E type : GenerateType_E.values()) {
	    	// main(type, map);
	    }
    }*/
}
