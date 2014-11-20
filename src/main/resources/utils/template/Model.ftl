package ${basePackage}.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
{import}
/**
 * ${model}
 */
@Entity // 标识这是一个与数据库映射的实体
@Table(name = "${model}") // 指定与数据库映射的表名
public class ${Model} extends BaseModel {
	@Id
	@GeneratedValue
	private Long id;

{fields}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
{getters/setters}
}
