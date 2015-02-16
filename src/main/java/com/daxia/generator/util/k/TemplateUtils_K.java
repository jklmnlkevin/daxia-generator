package com.daxia.generator.util.k;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class TemplateUtils_K {
	public static void generate(GenerateType_K[] generateTypes, Map<String, Object> paramMap, String projectPath) throws Exception {
		for (GenerateType_K type : generateTypes) {
	        generate(type, paramMap, projectPath);
        }
	}
	
	public static void generate(GenerateType_K generateType, Map<String, Object> paramMap, String projectPath) throws Exception {
		String basePackage = (String) paramMap.get("basePackage");
		basePackage = basePackage.replace(".", "/");
		String Model = (String) paramMap.get("Model");
		String model = (String) paramMap.get("model");
		String templateName = generateType.name() + ".txt";
		
		//创建一个合适的Configration对象  
        Configuration configuration = new Configuration();
        String templateType = (String) paramMap.get("templateType");
        if (StringUtils.isNotBlank(templateType)) {
            configuration.setDirectoryForTemplateLoading(new File("src/main/resources/utils/template/" + templateType));
        } else {
            configuration.setDirectoryForTemplateLoading(new File("src/main/resources/utils/template"));
        }
        configuration.setObjectWrapper(new DefaultObjectWrapper());  
        configuration.setDefaultEncoding("UTF-8");   //这个一定要设置，不然在生成的页面中 会乱码  
        //获取或创建一个模版。  
        Template template = configuration.getTemplate(templateName);
        String outFileName = projectPath + "/src/main/java/" + basePackage + "/" + generateType.getPackagi() + "/" + generateType.name().replace("Model", Model) + ".java";
        if (generateType.name().startsWith("model_")) {
        	outFileName = projectPath + "/src/main/webapp/WEB-INF/jsp/admin/" + model + "/" + generateType.name().replace("model", model) + ".jsp";
        	File dir = new File(projectPath + "/src/main/webapp/WEB-INF/jsp/admin/" + model);
        	if (!dir.exists()) {
        		dir.mkdir();
        	}
        }
        Writer writer  = new OutputStreamWriter(new FileOutputStream(outFileName), "UTF-8");  
        template.process(paramMap, writer);  
        System.out.println("Done!");  
        if (writer != null) {
        	writer.close();
        }
    }
}
