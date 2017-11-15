package extract.model.supplier;

public class ExtractConditionRelation {
	
	/**
	 * 条件id
	 */
	private String conditionId;
	
	/**
	 * 存储条件的名称 例如存储供应商类别 supplierTypeCode
	 */
	private String propertyName;
	
	/**
	 * 存储查询条件所对应的值 id  工程类供应商类别 存储 PROJECT
	 */
	private String propertyValue;
	
	public String getConditionId() {
		return conditionId;
	}
	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public ExtractConditionRelation(String conditionId, String propertyName,
			String propertyValue) {
		super();
		this.conditionId = conditionId;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	public ExtractConditionRelation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
