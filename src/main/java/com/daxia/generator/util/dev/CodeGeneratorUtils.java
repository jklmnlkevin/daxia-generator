package com.daxia.generator.util.dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CodeGeneratorUtils {
	public static ModelInfo modelInfo;
	private static String _package = "com.daxia.xxx";
	private static String basePath = "com/daxia/xxx/";
	private static String _Model = null;
	private static String _model = null;
	
	private static String query = "";
	private static String daoImports = ""; 
	private static String pageQueries = "";
	private static List<ModelField> fieldList;
	private static String projectPath;
	
	/**
	 * 初始化
	 * @param basePackage 
	 * @param projectPath 
	 */
	public static void init(String projectPath, String basePackage) {
		modelInfo = null;
		CodeGeneratorUtils.projectPath = projectPath;
		_package = basePackage; 
		basePath = basePackage.replace(".", "/");
		_Model = null;                     
		_model = null;                     
		query = "";                        
		daoImports = "";                   
		pageQueries = "";                  
		fieldList = new ArrayList<ModelField>();
		
		mysqlJavaDataTypeMapping = new HashMap<String, Class<? extends Object>>();
		mysqlJavaDataTypeMapping.put("bigint", Long.class);
		mysqlJavaDataTypeMapping.put("varchar", String.class);
		mysqlJavaDataTypeMapping.put("text", String.class);
		mysqlJavaDataTypeMapping.put("char", String.class);
		mysqlJavaDataTypeMapping.put("int", Integer.class);
		mysqlJavaDataTypeMapping.put("smallint", Integer.class);
		mysqlJavaDataTypeMapping.put("tinyint", Byte.class);
		mysqlJavaDataTypeMapping.put("float", Float.class);
		mysqlJavaDataTypeMapping.put("double", Double.class);
		mysqlJavaDataTypeMapping.put("blob", byte[].class);
		mysqlJavaDataTypeMapping.put("bool", boolean.class);
		mysqlJavaDataTypeMapping.put("boolean", boolean.class);
		mysqlJavaDataTypeMapping.put("datetime", Date.class);
		mysqlJavaDataTypeMapping.put("date", Date.class);
		mysqlJavaDataTypeMapping.put("timestamp", Timestamp.class);
	}
	public static Map<String, Class<? extends Object>> mysqlJavaDataTypeMapping;
	
	private static void getModelInfo() throws Exception {
		for (int i = 0; i < modelInfo.getTypes().length; i++) {
			ModelField mf = new ModelField();
			mf.name = modelInfo.getNames()[i];
			if (mf.name.equals("id")) {
				continue;
			}
			mf.dbName = modelInfo.getDbNames()[i];
			mf.comment = modelInfo.getComments()[i];
			mf.shortComment = modelInfo.getShortComments()[i];
			mf.type = Class.forName(modelInfo.getTypes()[i]);
			mf.asQuery = modelInfo.getAsQueries()[i];
			mf.asLikeQuery = modelInfo.getAsLikeQueries()[i];
			mf.dateFormat = modelInfo.getDateFormats()[i];
			mf.queryType = modelInfo.getQueryTypes()[i];
			fieldList.add(mf);
		}
    }
	
	public static void main(String[] args) throws Exception {
		if (modelInfo != null) {
			getModelInfo();
		}
		_Model = modelInfo.getModel();
		_model = _Model.replace(_Model.charAt(0), (char)(_Model.charAt(0)+32));
		generate();
		System.out.println("代码生成完成， 请刷新整个工程。");
		
	}
	private static void generate() throws IOException {
		
		/*generateModel();
		generateDTO();
		generateDAO();
		generateService();
		generateController();*/
		generatePage();
		
	}
	
    private static void generateModel() throws IOException {
		String path = "src/main/java/" + basePath + "/model";
		
		doGenerate(path, "Model");
		File file = new File(projectPath + "/" + path + "/" + _Model + ".java");
		generateModelFields(file);
    }
	private static void generateModelFields(File file) throws IOException {
		StringBuilder content = new StringBuilder();
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		while ((line = reader.readLine()) != null) {
			content.append(line).append("\n");
		}
		
		String fourSpaces = "    ";
		String define = "";
		
		StringBuilder importStr = new StringBuilder("");
		String getterAndSetter = "";
		for (ModelField mf : fieldList) {
			String getter = "";
			String setter = "";
			if (StringUtils.isNotBlank(mf.comment)) {
				define += fourSpaces + "/**" + "\n";
				define += fourSpaces + " * " + mf.comment + "\n";
				define += fourSpaces + " */" + "\n";

				getter += fourSpaces + "/**" + "\n";
				getter += fourSpaces + " * 获取值 ：" + mf.comment + "\n";
				getter += fourSpaces + " */" + "\n";
				
				setter += fourSpaces + "/**" + "\n";
				setter += fourSpaces + " * 设置值 ：" + mf.comment + "\n";
				setter += fourSpaces + " */" + "\n";
			}
			
			// 如果字段与数据库不一样，要增加column的东西
			if (!mf.dbName.equalsIgnoreCase(mf.name)) {
				if (mf.type.getName().contains(".model.")) {
					// 配置多对一
					define += fourSpaces + "@ManyToOne\n";
				    define += fourSpaces + "@JoinColumn(name = \"" + mf.dbName + "\")\n";
				    buildImportStr(importStr, "import javax.persistence.ManyToOne;");
				    buildImportStr(importStr, "import javax.persistence.JoinColumn;");
				} else {
					define += fourSpaces + "@Column(name = \"" + mf.dbName + "\")\n";
					buildImportStr(importStr, "javax.persistence.Column");
				}
			}
			
			if (mf.type == Date.class) {
				define += fourSpaces + "@DateTimeFormat(pattern = \"" + mf.dateFormat + "\")\n";
				buildImportStr(importStr, "import org.springframework.format.annotation.DateTimeFormat;");
			}
			define += fourSpaces + "private " + mf.type.getSimpleName() + " " + mf.name + ";" + "\n";
			
			getter += fourSpaces + "public " + mf.type.getSimpleName() + " get" + mf.name.toUpperCase().substring(0, 1) + 
					mf.name.substring(1) + "() {\n";
			if (mf.type.equals("BigDecimal")) {
				getter += fourSpaces + fourSpaces + "return BigDecimalUtils.moneyValue(" + mf.name + ");\n";
			} else {
				getter += fourSpaces + fourSpaces + "return " + mf.name + ";\n";
			}
			getter += fourSpaces + "}\n";
			
			setter += fourSpaces + "public void set" + mf.name.toUpperCase().substring(0, 1) + 
					mf.name.substring(1) + "(" + mf.type.getSimpleName() + " " + mf.name + ") {\n";
			setter += fourSpaces + fourSpaces + "this." + mf.name + " = " + mf.name + ";\n";
			setter += fourSpaces + "}\n";
			
			getterAndSetter += getter;
			getterAndSetter += setter;
			
			if (mf.type.getName().startsWith("java.lang.")) {
				
			} else {
				buildImportStr(importStr, "import " + mf.type.getName() + ";");
			}
		}

		if (!"".equals(importStr)) {
			importStr.append("\n"); // 为了换一个空行
		}
		String newContent = content.toString();
		newContent = newContent.replace("{import}", importStr.toString());
		newContent = newContent.replace("{fields}", define);
		newContent = newContent.replace("{getters/setters}", getterAndSetter);
		
		
		
		FileWriter writer = new FileWriter(file);
		writer.write(newContent);
		writer.close();
		
    }
	
	/**
	 * 如果有重要的导入，就不用再导入了
	 * @param importStringBuilder
	 * @param _import
	 * @return
	 */
	private static void buildImportStr(StringBuilder importStr, String _import) {
		if (importStr.toString().contains(_import)) {
		} else {
			importStr.append(_import + "\n");
		}
	}
	private static void generateDTO() throws IOException {
		String path = "src/main/java/" + basePath + "/dto";
		
		doGenerate(path, "ModelDTO");
    }
	private static void generatePage() throws IOException {
		StringBuilder pageQueriesBuilder = new StringBuilder();
		pageQueriesBuilder.append("		<ul class=\"searchContent\">").append("\n");
		int queryCount = 0;
		/*pageQueriesBuilder.append("			<li>").append("\n");
		pageQueriesBuilder.append("				<label>ID：</label>").append("\n");
		pageQueriesBuilder.append("				<input type=\"text\" name=\"id\" value=\"${" + _model + ".id}\"/>").append("\n");
		pageQueriesBuilder.append("			</li>").append("\n");
		queryCount ++;*/
		
		int totalQueryCount = 0;
		for (int i = 0; i < fieldList.size(); i++) {
			ModelField mf = fieldList.get(i);
			if (!mf.asQuery) {
				continue;
			}
			totalQueryCount ++;
		}
		
		for (int i = 0; i < fieldList.size(); i++) {
			ModelField mf = fieldList.get(i);
			if (!mf.asQuery) {
				continue;
			}
			queryCount ++;
			
			pageQueriesBuilder.append("			<li>").append("\n");
			pageQueriesBuilder.append("				<label>" + modelInfo.getComments()[i + 1] + "：</label>").append("\n");
			if (mf.type == Date.class) {
				pageQueriesBuilder.append("				<input type=\"text\"  class=\"date\" dateFmt=\"" + mf.dateFormat + "\" readonly=\"true\" name=\"" + mf.name + "\" value=\"<fmt:formatDate value=\"${" + _model + "." + mf.name + " }\" pattern=\"" + mf.dateFormat + "\"/>\" />").append("\n");
			} else {
				pageQueriesBuilder.append("				<input type=\"text\" name=\"" + mf.name + "\" value=\"${" + _model + "." + mf.name + "}\"/>").append("\n");
			}
			pageQueriesBuilder.append("			</li>").append("\n");
			
			if (queryCount % 3 == 0 && queryCount < totalQueryCount) {
				pageQueriesBuilder.append("		</ul>").append("\n");
				pageQueriesBuilder.append("		<ul class=\"searchContent\">").append("\n");
			}
		}		
		pageQueriesBuilder.append("		</ul>").append("\n");
		if (queryCount > 0) {
			pageQueries = pageQueriesBuilder.toString();
		}
		String path = "src/main/webapp/WEB-INF/jsp/admin/" + _model;
		String fullPath = projectPath + "/" + path;
		File dir = new File(fullPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		doGenerate(path, "model_list", ".jsp");
		doGenerate(path, "model_detail", ".jsp");
		
		File file = new File(fullPath + "/" + _model + "_list.jsp");
		fillFieldsToListPage(file);
		
		file = new File(fullPath + "/" + _model + "_detail.jsp");
		fillFieldsToDetailPage(file);
    }
	
	private static void fillFieldsToDetailPage(File file) throws IOException {

		StringBuilder content = new StringBuilder();
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		while ((line = reader.readLine()) != null) {
			content.append(line).append("\n");
		}
		
		String spaces = "			";
		String spaces2 = "				";
		String fields = "";
		
		for (ModelField mf : fieldList) {
			fields += spaces + "<p>\n";
			fields += spaces2 + "<label>";
			if (StringUtils.isNotBlank(mf.shortComment)) {
				fields += mf.shortComment;
			} else {
				fields += mf.name;
			}
			fields += ":</label>\n";
			
			fields += spaces2;
			if (mf.type == Date.class) {
				fields += "<input type=\"text\" class=\"date\" dateFmt=\"" + mf.dateFormat + "\" readonly=\"true\" name=\"" + mf.name + "\" value=\"<fmt:formatDate value=\"${" + _model + "." + mf.name + " }\" pattern=\"" + mf.dateFormat + "\"/>"
				        + "\" size=\"30\" />";
				fields += "<a class=\"inputDateButton\" href=\"javascript:;\">选择</a>";
			} else {
				fields += "<input type=\"text\" name=\"" + mf.name + "\" value=\"${" + _model + "." + mf.name + " }\" size=\"30\" />";
			}
			fields += "\n";
			fields += spaces + "</p>\n";
		}
		
		String newContent = content.toString();
		newContent = newContent.replace("{fields}", fields);
		
		FileWriter writer = new FileWriter(file);
		writer.write(newContent);
		writer.close();
    
    }
	private static void fillFieldsToListPage(File file) throws IOException {
		StringBuilder content = new StringBuilder();
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		while ((line = reader.readLine()) != null) {
			content.append(line).append("\n");
		}
		
		String spaces = "				";
		String ths = "";
		String tds = "";
		
		for (ModelField mf : fieldList) {
			if (StringUtils.isNotBlank(mf.shortComment)) {
				ths += spaces + "<th align=\"center\" width=\"100\">" + mf.shortComment + "</th>\n";
			} else {
				ths += spaces + "<th align=\"center\" width=\"100\">" + mf.name + "</th>\n";
			}
			
			if (mf.type == Date.class) {
				tds += spaces + "<td><fmt:formatDate value=\"${n." + mf.name + " }\" pattern=\"yyyy-MM-dd HH:mm:ss\"/></td>\n";
			} else {
				tds += spaces + "<td>${n." + mf.name + " }</td>\n";
			}
		}
		
		String newContent = content.toString();
		newContent = newContent.replace("{ths}", ths);
		newContent = newContent.replace("{tds}", tds);
		
		FileWriter writer = new FileWriter(file);
		writer.write(newContent);
		writer.close();
    }
	
	private static void generateController() throws IOException {
		String path = "src/main/java/" + basePath + "/web/controller";
		
		//doGenerate(path, "ModelController");
		
		path = "src/main/java/" + basePath + "/web/controller/admin";
		
		doGenerate(path, "AdminModelController");
    }
	
	private static void generateService() throws IOException {
		String path = "src/main/java/" + basePath + "/service";
		
		doGenerate(path, "ModelService");
	}
	
	private static void generateDAO() throws IOException {
		// 生成查询条件
		StringBuilder daoImportsBuilder = new StringBuilder();
		StringBuilder queries = new StringBuilder();
		for (int i = 0; i < fieldList.size(); i++) {
			ModelField mf = fieldList.get(i);
			if (mf.asQuery) {
				if (!mf.type.getName().startsWith("java.lang.")) {
					if (!daoImportsBuilder.toString().contains(mf.type.getName())) {
//						daoImportsBuilder.append("import " + mf.type.getName() + ";\n");
					}
				}
				
				if (mf.type.getSimpleName().equals("String")) {
					daoImportsBuilder.append("import org.apache.commons.lang3.StringUtils;\n");
					queries.append("        if (StringUtils.isNotBlank(dto.get" + upperCaseFistLetter(mf.name) + "())) {\n");
				} else {
					queries.append("        if (dto.get" + upperCaseFistLetter(mf.name) + "() != null) {\n");
				}
				
				if (mf.asLikeQuery) {
					queries.append("            hql.append(\" and n." + mf.name + " like ? \");\n");
					queries.append("            paras.add(\"%\" + dto.get" + upperCaseFistLetter(mf.name) + "() + \"%\");\n");
				} else {
					queries.append("            hql.append(\" and n." + mf.name + " = ? \");\n");
					queries.append("            paras.add(dto.get" + upperCaseFistLetter(mf.name) + "());\n");
				}
				queries.append("        }\n");
			}
		}
		
		query = queries.toString();
		daoImports = daoImportsBuilder.toString();
		
		String path = "src/main/java/" + basePath + "/dao";
		doGenerate(path, "ModelDAO");
		
    }
	
	private static String upperCaseFistLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	private static void doGenerate(String path, String template) throws IOException {
		doGenerate(path, template, null);
	}
	private static void doGenerate(String path, String template, String suffix) throws IOException {
		path = projectPath + "/" + path;
		if (suffix == null) {
			suffix = ".java";
		}
		InputStream is = CodeGeneratorUtils.class.getClassLoader().getResourceAsStream("utils/template/" + template + ".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
		String fileName = template.replace("Model", _Model).replace("model", _model) + suffix;
		// FileWriter writer = new FileWriter(path + "/" + fileName);
		PrintWriter writer = new PrintWriter(path + "/" + fileName, "utf-8");
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println("line = " + line);
			line = line.replace("{package}", _package);
			line = line.replace("{Model}", _Model);
			line = line.replace("{model}", _model);
			line = line.replace("{moduleChineseName}", modelInfo.getModelChineseName());
			line = line.replace("{queries}", query);
			line = line.replace("{pageQueries}", pageQueries);
			line = line.replace("{daoImports}", daoImports);
			line = line.replace("{basePackage}", _package);
			// System.out.println(line);
			writer.write(line);
			writer.write("\n");
		}
		writer.close();
    }
}

class ModelField {
	Class<? extends Object> type;
	String name;
	String dbName;
	String comment;
	String shortComment;
	boolean asQuery;
	boolean asLikeQuery;
	String dateFormat;
	String queryType;
	@Override
	public String toString() {
		return name + "-" + type.getName() + "-" + comment;
	}
	
}
