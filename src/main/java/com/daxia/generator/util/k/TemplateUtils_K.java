package com.daxia.generator.util.k;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class TemplateUtils_K {
    private static String _projectPath;
    private static String _basePackage;
	public static void generate(GenerateType_K[] generateTypes, Map<String, Object> paramMap, String projectPath) throws Exception {
	    _projectPath = projectPath;
		for (GenerateType_K type : generateTypes) {
	        generate(type, paramMap, projectPath);
        }
		String Model = (String) paramMap.get("Model");
        String model = (String) paramMap.get("model");
		processBaseController(projectPath, _basePackage, Model, model);
	}
	
	public static void generate(GenerateType_K generateType, Map<String, Object> paramMap, String projectPath) throws Exception {
		String basePackage = (String) paramMap.get("basePackage");
		basePackage = basePackage.replace(".", "/");
		
		_basePackage = basePackage;
		
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

    private static void processBaseController(String projectPath, String basePackage, String Model, String model) throws Exception {
        File baseController = new File(projectPath + "/src/main/java/com/daxia/core/web/controller/BaseController.java");
        File mBaseController = new File(projectPath + "/src/main/java/" + basePackage + "/web/controller/m/MBaseController.java");
        
        String content = "    @Autowired\n    protected {Model}Service  {model}Service;\n";
        content = content.replace("{Model}", Model);
        content = content.replace("{model}", model);
        
        insertService(baseController, content);
        insertService(mBaseController, content);
    }
    
    private static void insertService(File file, String content) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        List<String> list = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            if (line.contains(" class ") && line.contains("public")) {
                list.add(line);
                System.out.println(line);
                list.add(content);
            } else {
                list.add(line);
            }
        }
        reader.close();
        
        FileWriter writer = new FileWriter(file);
        for (String str : list) {
            writer.write(str);
            writer.write("\n");
        }
        writer.close();
         
    }
}
