package com.daxia.generator.util.e;

public enum GenerateType_E {
	Entity("ModelEntity.ftl", "src/main/java/com/daxia/hr/entity"),
	ServiceI("ModelServiceI.ftl", "src/main/java/com/daxia/hr/service"),
	ServiceImpl("ModelServiceImpl.ftl", "src/main/java/com/daxia/hr/service/impl"),
	Controller("ModelController.ftl", "src/main/java/com/daxia/hr/controller"),
	Add("model-add.jsp", "src/main/webapp/webpage"),
	Update("model-update.jsp", "src/main/webapp/webpage"),
	Js("model.js", "src/main/webapp/webpage"),
	List("modelList.jsp", "src/main/webapp/webpage"),
	ListJs("modelList.js", "src/main/webapp/webpage");
	
	private String value;
	private String path;

	private GenerateType_E(String value, String path) {
		this.value = value;
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public String getPath() {
		return path;
	}
	
}
