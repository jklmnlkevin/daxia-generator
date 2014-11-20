package com.daxia.generator.util.k;

public enum GenerateType_K {
	Model(0, "xxx", "model"),
	ModelDAO(1, "xxx", "dao"),
	ModelService(1, "xxx", "service"),
	AdminModelController(1, "xxx", "web/controller/admin"),
	// ModelController(1, "xxx", "web/controller"),
	model_list(1, "xxx", ""),
	model_search(1, "", ""),
	model_detail(1, "xxx", "");

	private int value;
	private String remark;
	private String packagi;

	private GenerateType_K(int value, String remark, String packagi) {
		this.value = value;
		this.remark = remark;
		this.packagi = packagi;
	}
	
	public String getPackagi() {
		return this.packagi;
	}

	public int getValue() {
		return value;
	}

	public String getRemark() {
		return remark;
	}

	public GenerateType_K getByValue(int value) {
		for (GenerateType_K o : GenerateType_K.values()) {
			if (o.getValue() == value) {
				return o;
			}
		}
		return null;
	}
}
