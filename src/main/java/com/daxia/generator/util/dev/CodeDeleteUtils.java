package com.daxia.generator.util.dev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeDeleteUtils {
	static List<String> deleteList = new ArrayList<String>();
	static {
		deleteList.add("src/main/java/com/daxia/generator/model/{model}.java");
		deleteList.add("src/main/java/com/daxia/generator/dao/{model}DAO.java");
		deleteList.add("src/main/java/com/daxia/generator/service/{model}Service.java");
		deleteList.add("src/main/java/com/daxia/generator/web/controller/admin/Admin{model}Controller.java");
		deleteList.add("src/main/java/com/daxia/generator/dto/{model}DTO.java");
		deleteList.add("src/main/webapp/WEB-INF/jsp/admin/{model}/{model}_list.jsp");
		deleteList.add("src/main/webapp/WEB-INF/jsp/admin/{model}/{model}_detail.jsp");
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要删除的model: ");
		String model = scanner.nextLine();
		// String model = "Kevin";

		File modelDir = new File("src/main/java/com/daxia/generator/model");
		for (File m : modelDir.listFiles() ) {
			model = m.getName().replace(".java", "");
			for (String path : deleteList) {
				File file = new File(path.replace("{model}", model));
				if (file.exists()) {
					file.delete();
				}
			}
		}

	}
}
