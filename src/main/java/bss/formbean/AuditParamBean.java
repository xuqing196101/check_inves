package bss.formbean;

import java.util.LinkedList;
import java.util.List;

public class AuditParamBean {

	private Integer size;
	
	private String name;
	
	private String id;

	private List<TestAudit> list=new LinkedList<TestAudit>();
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TestAudit> getList() {
		return list;
	}

	public void setList(List<TestAudit> list) {
		this.list = list;
	}
	
	
}
