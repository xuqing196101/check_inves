package ses.model.sms;

import java.io.Serializable;

/**
 * @Title: SupplierTypeTree
 * @Description: 供应商类型 Tree
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午2:42:00
 */
public class SupplierTypeTree implements Serializable {
	private static final long serialVersionUID = 66250084269502230L;
	private String id;
	private String name;
	private String parentId;
	private boolean checked = false;
	private boolean open = true;
	private boolean chkDisabled = false;
	private boolean isParent = false;

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

    /**
     * @return Returns the isParent.
     */
    public boolean isParent() {
        return isParent;
    }

    /**
     * @param isParent The isParent to set.
     */
    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }
	
	
}
