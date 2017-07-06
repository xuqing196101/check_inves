package ses.formbean;

import java.util.ArrayList;
import java.util.List;

import ses.model.bms.Qualification;

public class QualificationBean {

	private String categoryName;
	private String categoryId;
	
	private  List<Qualification> list=new ArrayList<Qualification>();

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<Qualification> getList() {
		return list;
	}

	public void setList(List<Qualification> list) {
		this.list = list;
	}
	
	
}
