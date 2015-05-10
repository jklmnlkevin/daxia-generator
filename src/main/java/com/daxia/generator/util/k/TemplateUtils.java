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

public class TemplateUtils {
    private static String _projectPath;
    private static String _basePackage;
    private static String _basePackageDot;
	public static void generate(GenerateType[] generateTypes, Map<String, Object> paramMap, String projectPath) throws Exception {
	    _projectPath = projectPath;
		for (GenerateType type : generateTypes) {
	        generate(type, paramMap, projectPath);
        }
		String Model = (String) paramMap.get("Model");
        String model = (String) paramMap.get("model");
		processBaseController(projectPath, _basePackage, Model, model);
	}
	
	public static void generate(GenerateType generateType, Map<String, Object> paramMap, String projectPath) throws Exception {
		String basePackage = (String) paramMap.get("basePackage");
		_basePackageDot = basePackage;
		basePackage = basePackage.replace(".", "/");
		
		_basePackage = basePackage;
		
		String Model = (String) paramMap.get("Model");
		String model = (String) paramMap.get("model");
		String templateName = generateType.name() + ".txt";
		
		//创建一个合,适的Configration对象  
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
        
        String importLine = "import " + _basePackageDot + ".service." + Model + "Service;";
        String defineLine = "protected " + Model + "Service " + model + "Service;";
        String content = "    @Autowired\n    protected {Model}Service {model}Service;\n";
        content = content.replace("{Model}", Model);
        content = content.replace("{model}", model);
        
        insertServiceAndImport(baseController, content, importLine, defineLine, basePackage);
        insertServiceAndImport(mBaseController, content, importLine, defineLine, basePackage);
    }
    
    private static void insertServiceAndImport(File file, String content, String importLine, String defineLine, String basePackage) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        List<String> list = new ArrayList<String>();
        List<String> tmpList = new ArrayList<String>();
        {
            String line = null;
            while ((line = reader.readLine()) != null) {
                tmpList.add(line);
            }
            reader.close();
        }
        boolean hasImport = false;
        boolean hasDefine = false;
        for (String line : tmpList) {
            if (line.trim().equals(importLine)) {
                hasImport = true;
            }
            if (line.trim().equals(defineLine)) {
                hasDefine = true;
            }
            if (line.contains("student")) {
                System.out.println(line);
                System.out.println(defineLine);
            }
        }
        
        for (String line : tmpList) {
            list.add(line);
            if (line.contains(" class ") && line.contains("public")) {
                if (!hasDefine) { // 如果没定义过
                    list.add(content);
                    hasDefine = true;
                }
            }
            
            if (line.trim().startsWith("import " + _basePackageDot)) {
                System.out.println(line);
                if (!hasImport) {
                    list.add(importLine);
                    hasImport = true;
                }
            }
        }
        
        FileWriter writer = new FileWriter(file);
        for (String str : list) {
            writer.write(str);
            writer.write("\n");
        }
        writer.close();
         
    }
}
