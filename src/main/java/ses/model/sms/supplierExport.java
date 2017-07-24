package ses.model.sms;

import java.io.Serializable;

public class supplierExport implements Serializable {

	private String id;//机构id
	private String name;//机构名称
	private String shortName;//机构简称
	private String depId;//关联表id
	private Integer reg;//注册人数
	private Integer statusOne;//待审核人数
	private Integer statusTwo;//已审核人数
	private Integer statusThree;//退回修改
	private Integer statusFour;//审核不通过
	private Integer expertArmy;//军队专家
	private Integer expertsLocal;//地方专家
	private Integer sums;//总数
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public Integer getReg() {
		return reg;
	}
	public void setReg(Integer reg) {
		this.reg = reg;
	}
	public Integer getStatusOne() {
		return statusOne;
	}
	public void setStatusOne(Integer statusOne) {
		this.statusOne = statusOne;
	}
	public Integer getStatusTwo() {
		return statusTwo;
	}
	public void setStatusTwo(Integer statusTwo) {
		this.statusTwo = statusTwo;
	}
	public Integer getStatusThree() {
		return statusThree;
	}
	public void setStatusThree(Integer statusThree) {
		this.statusThree = statusThree;
	}
	public Integer getStatusFour() {
		return statusFour;
	}
	public void setStatusFour(Integer statusFour) {
		this.statusFour = statusFour;
	}
	public Integer getExpertArmy() {
		return expertArmy;
	}
	public void setExpertArmy(Integer expertArmy) {
		this.expertArmy = expertArmy;
	}
	public Integer getExpertsLocal() {
		return expertsLocal;
	}
	public void setExpertsLocal(Integer expertsLocal) {
		this.expertsLocal = expertsLocal;
	}
  public Integer getSums() {
    return sums;
  }
  public void setSums(Integer sums) {
    this.sums = sums;
  }
	
	
	
}
