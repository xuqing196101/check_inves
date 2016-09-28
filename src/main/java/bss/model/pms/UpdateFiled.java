package bss.model.pms;
/**
 * 
 * @Title: UpdateFiled
 * @Description: 是否允许字段修改 
 * @author Li Xiaoxiao
 * @date  2016年9月21日,下午5:01:35
 *
 */
public class UpdateFiled {
    private String id;

    private String planNo;

    private String filed;

    private Integer isUpdate;

    private String filedName;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed == null ? null : filed.trim();
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		   this.filedName = filedName == null ? null : filedName.trim();
	}
    
    
}