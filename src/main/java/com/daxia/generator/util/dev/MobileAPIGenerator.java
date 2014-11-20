package com.daxia.generator.util.dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.daxia.generator.config.AppProperties;

public class MobileAPIGenerator {
	private static String apiDTOPackage = "src/main/java/" + (AppProperties.get("basePackage") + ".dto.api").replace(".", "/");
	private static String apiInfoDTOPackage = "src/main/java/" + (AppProperties.get("basePackage") + ".dto.api.info").replace(".", "/");
	private static String apiControllerPackage = "src/main/java/" + (AppProperties.get("basePackage") + ".web.controller.m").replace(".", "/");
	private static String modelPackage = "src/main/java/" + (AppProperties.get("basePackage") + ".model").replace(".", "/");
	
	public static void main(String[] args) throws Exception {
		File modelDir = new File(modelPackage);
		File[] modelFiles = modelDir.listFiles();
		String _Model = "FriendRequest";
		String _model = "friendRequest";
		// for (File file : modelFiles) {
//	        System.out.println(file.getName());
//	        _Model = file.getName().replace(".java", "");
//	        _model = _Model.substring(0, 1).toLowerCase() + _Model.substring(1);
	        generate(_Model, _model, apiInfoDTOPackage, "ModelInfoAPIDTO.txt");
	        generate(_Model, _model, apiDTOPackage, "ModelAPIDTO.txt");
	        generate(_Model, _model, apiControllerPackage, "MModelController.txt");
        // }
		System.out.println("完成，请刷新项目");
    }
	
	private static void generate(String _Model, String _model, String packagePath, String templateName) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/utils/template/api/" + templateName)));
        String line = null;
        List<String> list = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
        	list.add(line);
        }
        
        File file = new File(packagePath + "/" + (templateName.replace("Model", _Model).replace(".txt", "")) + ".java");
        if (file.exists()) {
        	return;
        }
        FileWriter writer = new FileWriter(file);
        for (String l : list) {
        	l = l.replace("{Model}", _Model);
        	l = l.replace("{model}", _model);
	        writer.write(l + "\n");
        }
        writer.close();
	}
}
